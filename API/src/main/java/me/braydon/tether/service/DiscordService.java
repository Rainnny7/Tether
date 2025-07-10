package me.braydon.tether.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.questdb.cutlass.line.LineSenderException;
import jakarta.annotation.PostConstruct;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.HttpStatus;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONObject;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import me.braydon.tether.common.DiscordUtils;
import me.braydon.tether.exception.impl.BadRequestException;
import me.braydon.tether.exception.impl.ResourceNotFoundException;
import me.braydon.tether.exception.impl.ServiceUnavailableException;
import me.braydon.tether.metric.impl.TrackedUsersMetric;
import me.braydon.tether.metric.impl.UserLookupTimingsMetric;
import me.braydon.tether.model.response.DiscordUserResponse;
import me.braydon.tether.model.user.CachedDiscordUser;
import me.braydon.tether.model.user.DiscordUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * This service is responsible for
 * interacting with the Discord API.
 *
 * @author Braydon
 */
@Service
@Log4j2(topic = "Discord")
public final class DiscordService extends ListenerAdapter {
    /**
     * A cache of users retrieved from Discord.
     */
    private final Cache<Long, CachedDiscordUser> cachedUsers = Caffeine.newBuilder()
            .expireAfterWrite(3L, TimeUnit.MINUTES)
            .build();

    /**
     * The token to use for the bot.
     */
    @Value("${discord.bot-token}")
    private String botToken;

    /**
     * The token to use for the user account.
     */
    @Value("${discord.user-account-token}")
    private String userAccountToken;

    /**
     * Are metrics enabled?
     */
    @Value("${questdb.enabled}")
    private boolean metricsEnabled;

    /**
     * The current instance of the Discord bot.
     */
    private JDA jda;

    @PostConstruct
    public void onInitialize() {
        connectBot();
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if (metricsEnabled) {
            TrackedUsersMetric.incrementRecentlyWatchedUsers();
        }
    }

    /**
     * Get a Discord user by their snowflake.
     *
     * @param query the user snowflake
     * @return the user response
     * @throws ServiceUnavailableException if the bot is not connected
     * @throws ResourceNotFoundException   if the user is not found
     */
    @NonNull
    public DiscordUserResponse getUser(@NonNull String query) throws BadRequestException, ServiceUnavailableException, ResourceNotFoundException {
        long snowflake = -1L;
        try {
            snowflake = Long.parseLong(query);
        } catch (NumberFormatException ignore) {
            // Safely ignore, we'll try to find the user by username
        }
        if (snowflake == -1L) {
            // Check to ensure the username is valid before continuing the request
            if (!DiscordUtils.isValidUsername(query)) {
                throw new BadRequestException("The given username is not valid.");
            }
            // Try and get the user by username
            User user;
            if (query.contains("#")) { // Handle legacy usernames with discriminator
                String[] parts = query.split("#");
                if (parts.length != 2) {
                    throw new BadRequestException("The given username is not valid.");
                }
                // Try to find the user by username and discriminator
                user = jda.getUserByTag(parts[0], parts[1]);
            } else {
                user = jda.getUserByTag(query, "0000");
            }
            if (user == null) {
                throw new ResourceNotFoundException("User not found.");
            }
            return getUser(user.getIdLong());
        }
        return getUser(snowflake);
    }

    /**
     * Get a Discord user by their snowflake.
     *
     * @param snowflake the user snowflake
     * @return the user response
     * @throws ServiceUnavailableException if the bot is not connected
     * @throws ResourceNotFoundException   if the user is not found
     */
    @NonNull
    public DiscordUserResponse getUser(long snowflake) throws BadRequestException, ServiceUnavailableException, ResourceNotFoundException {
        if (jda == null || (jda.getStatus() != JDA.Status.CONNECTED)) { // Ensure bot is connected
            throw new ServiceUnavailableException("Not connected to Discord.");
        }
        try {
            Member member = getMember(snowflake); // First try to locate the member in a guild

            // Then retrieve the user (first try the cache)
            CachedDiscordUser cachedUser = cachedUsers.getIfPresent(snowflake);
            boolean fromCache = cachedUser != null;
            if (cachedUser == null) { // No cache, retrieve fresh data
                long before = System.currentTimeMillis();
                cachedUser = new CachedDiscordUser(getUser(snowflake, member != null), System.currentTimeMillis());
                if (metricsEnabled) {
                    try {
                        UserLookupTimingsMetric.track(System.currentTimeMillis() - before);
                    } catch (LineSenderException | IllegalStateException ignored) {
                        // This can happen due to no metrics server being
                        // available, we can safely ignore it and continue
                    }
                }
                cachedUsers.put(snowflake, cachedUser);
            }

            // Finally build the response and respond with it
            return new DiscordUserResponse(
                    DiscordUser.buildFromEntity(cachedUser.getUserJson(), member),
                    fromCache ? cachedUser.getCached() : -1L
            );
        } catch (ErrorResponseException ex) {
            // Failed to lookup the user, handle appropriately
            if (ex.getErrorCode() == 10013) {
                throw new ResourceNotFoundException("User not found.");
            }
            throw ex;
        }
    }

    /**
     * Return the amount of users
     * the bot is tracking.
     *
     * @return the tracked user count
     */
    public int getTrackedUsers() {
        int tracked = 0;
        for (Guild guild : jda.getGuilds()) {
            tracked += guild.getMemberCount();
        }
        return tracked;
    }

    /**
     * Connects the bot to the Discord API.
     */
    @SneakyThrows
    private void connectBot() {
        log.info("Connecting bot...");
        jda = JDABuilder.createDefault(botToken, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MEMBERS)
                .enableCache(CacheFlag.getPrivileged())
                .setActivity(Activity.watching("you"))
                .build();
        jda.getRestPing().queue(ping -> {
            log.info("The latency to Discord is {}ms", ping);
        });
        jda.awaitReady();

        SelfUser self = jda.getSelfUser();
        String inviteUrl = "https://discord.com/oauth2/authorize?client_id=" + self.getId() + "&permissions=8&integration_type=0&scope=bot+applications.commands";
        log.info("Bot connected! Logged in as {}, invite me using {}",
                self.getAsTag(), inviteUrl
        );
    }

    /**
     * Get the user with the given snowflake from Discord.
     *
     * @param snowflake      the user snowflake
     * @param includeProfile whether to include the user's profile
     * @return the user json object
     * @throws BadRequestException if the request fails
     */
    @NonNull
    private JSONObject getUser(long snowflake, boolean includeProfile) throws BadRequestException {
        HttpResponse<JsonNode> response = Unirest.get("https://discord.com/api/v10/users/" + snowflake + (includeProfile ? "/profile?with_mutual_guilds=false" : ""))
                .header(HttpHeaders.AUTHORIZATION, userAccountToken).asJson();
        JSONObject json = response.getBody().getObject();
        if (response.getStatus() == HttpStatus.OK) {
            return json;
        }
        throw new BadRequestException(json.getInt("code") + ": " + json.getString("message"));
    }

    /**
     * Get a member from a guild by their snowflake.
     *
     * @param snowflake the user's snowflake
     * @return the member, null if none
     */
    private Member getMember(long snowflake) {
        Member member = null;
        try {
            for (Guild guild : jda.getGuilds()) {
                if ((member = guild.retrieveMemberById(snowflake).complete()) != null) {
                    break;
                }
            }
        } catch (ErrorResponseException ex) {
            // Ignore if the member is not in the guild
            if (ex.getErrorCode() != 10007) {
                throw ex;
            }
        }
        return member;
    }
}