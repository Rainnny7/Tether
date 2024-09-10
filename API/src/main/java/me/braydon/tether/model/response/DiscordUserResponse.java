package me.braydon.tether.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import me.braydon.tether.model.user.DiscordUser;

/**
 * A response for a successful Discord user request.
 *
 * @author Braydon
 */
@AllArgsConstructor @Getter
public final class DiscordUserResponse {
    /**
     * The user that was retrieved.
     */
    @NonNull private final DiscordUser user;

    /**
     * The unix timestamp of when this
     * user was cached, -1 if fresh.
     */
    private final long cached;
}