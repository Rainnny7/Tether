package me.braydon.tether.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;

/**
 * A model representing a cached Discord user.
 *
 * @author Braydon
 */
@AllArgsConstructor @Setter @Getter
public final class CachedDiscordUser {
    /**
     * The cached user.
     */
    @NonNull private final User user;

    /**
     * The cached user profile.
     */
    @NonNull private final User.Profile profile;

    /**
     * The unix time of when this user was cached.
     */
    private long cached;
}