package me.braydon.tether.model.user;

import kong.unirest.core.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * A model representing a cached Discord user.
 *
 * @author Braydon
 */
@AllArgsConstructor @Getter
public final class CachedDiscordUser {
    /**
     * The Json object for the user's data.
     */
    @NonNull private final JSONObject userJson;

    /**
     * The unix time of when this user was cached.
     */
    private final long cached;
}