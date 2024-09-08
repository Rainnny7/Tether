package me.braydon.tether.controller;

import lombok.NonNull;
import me.braydon.tether.exception.impl.BadRequestException;
import me.braydon.tether.exception.impl.ResourceNotFoundException;
import me.braydon.tether.exception.impl.ServiceUnavailableException;
import me.braydon.tether.model.response.DiscordUserResponse;
import me.braydon.tether.service.DiscordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is responsible for
 * handling Discord user related requests.
 *
 * @author Braydon
 */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public final class UserController {
    @NonNull private final DiscordService discordService;

    @Autowired
    public UserController(@NonNull DiscordService discordService) {
        this.discordService = discordService;
    }

    /**
     * A GET endpoint to get info about a
     * Discord user by their snowflake.
     *
     * @param snowflake the user snowflake
     * @return the retrieved user
     */
    @GetMapping("/{snowflake}") @ResponseBody @NonNull
    public ResponseEntity<DiscordUserResponse> getUserBySnowflake(@PathVariable @NonNull String snowflake)
            throws BadRequestException, ServiceUnavailableException, ResourceNotFoundException
    {
        return ResponseEntity.ok(discordService.getUserBySnowflake(snowflake));
    }
}