package me.braydon.tether.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.HttpStatus;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONObject;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import me.braydon.tether.exception.impl.BadRequestException;
import me.braydon.tether.exception.impl.ResourceNotFoundException;
import me.braydon.tether.exception.impl.ServiceUnavailableException;
import me.braydon.tether.model.response.DiscordUserResponse;
import me.braydon.tether.model.user.CachedDiscordUser;
import me.braydon.tether.model.user.DiscordUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
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
public final class DiscordService {
    /**
     * A cache of users retrieved from Discord.
     */
    private final Cache<Long, CachedDiscordUser> cachedUsers = Caffeine.newBuilder()
            .expireAfterWrite(3L, TimeUnit.MINUTES)
            .build();

    @Value("${discord.bot-token}")
    private String botToken;

    @Value("${discord.user-account-token}")
    private String userAccountToken;

    /**
     * The current instance of the Discord bot.
     */
    private JDA jda;

    @PostConstruct
    public void onInitialize() {
        connectBot();
    }

    /**
     * Get a Discord user by their snowflake.
     *
     * @param rawSnowflake the user snowflake
     * @return the user response
     * @throws ServiceUnavailableException if the bot is not connected
     * @throws ResourceNotFoundException   if the user is not found
     */
    @NonNull
    public DiscordUserResponse getUserBySnowflake(@NonNull String rawSnowflake) throws BadRequestException, ServiceUnavailableException, ResourceNotFoundException {
        long snowflake;
        try {
            snowflake = Long.parseLong(rawSnowflake);
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Not a valid snowflake.");
        }
        return getUserBySnowflake(snowflake);
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
    public DiscordUserResponse getUserBySnowflake(long snowflake) throws BadRequestException, ServiceUnavailableException, ResourceNotFoundException {
        if (jda == null || (jda.getStatus() != JDA.Status.CONNECTED)) { // Ensure bot is connected
            throw new ServiceUnavailableException("Not connected to Discord.");
        }
        try {
            Member member = getMember(snowflake); // First try to locate the member in a guild

            // Then retrieve the user (first try the cache)
            CachedDiscordUser cachedUser = cachedUsers.getIfPresent(snowflake);
            boolean fromCache = cachedUser != null;
            if (cachedUser == null) { // No cache, retrieve fresh data
                cachedUser = new CachedDiscordUser(getUser(snowflake, member != null), System.currentTimeMillis());
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