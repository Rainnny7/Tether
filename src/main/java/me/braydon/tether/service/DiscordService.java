package me.braydon.tether.service;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import me.braydon.tether.exception.impl.BadRequestException;
import me.braydon.tether.exception.impl.ResourceNotFoundException;
import me.braydon.tether.exception.impl.ServiceUnavailableException;
import me.braydon.tether.model.DiscordUser;
import me.braydon.tether.model.response.DiscordUserResponse;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for
 * interacting with the Discord API.
 *
 * @author Braydon
 */
@Service
@Log4j2(topic = "Discord")
public final class DiscordService {
    @Value("${discord.bot-token}")
    private String botToken;

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
     * @throws ResourceNotFoundException if the user is not found
     */
    @NonNull
    public DiscordUserResponse getUserBySnowflake(@NonNull String rawSnowflake) throws BadRequestException, ServiceUnavailableException, ResourceNotFoundException {
        if (jda == null || (jda.getStatus() != JDA.Status.CONNECTED)) { // Ensure bot is connected
            throw new ServiceUnavailableException("Not connected to Discord.");
        }
        long snowflake;
        try {
            snowflake = Long.parseLong(rawSnowflake);
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Not a valid snowflake");
        }
        try {
            // First try to locate the user in a guild
            Member member = null;
            for (Guild guild : jda.getGuilds()) {
                if ((member = guild.retrieveMemberById(snowflake).complete()) != null) {
                    break;
                }
            }
            User user = jda.retrieveUserById(snowflake).complete();
            User.Profile profile = user.retrieveProfile().complete();
            return new DiscordUserResponse(DiscordUser.buildFromEntity(user, profile, member), -1L);
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
}