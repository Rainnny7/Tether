package me.braydon.tether.model.user;

import kong.unirest.core.json.JSONObject;
import lombok.*;
import net.dv8tion.jda.api.entities.User;

import java.util.EnumSet;

/**
 * The flag's of a {@link DiscordUser}.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class UserFlags {
    /**
     * The list of flags the user has.
     */
    @NonNull private final EnumSet<User.UserFlag> list;

    /**
     * The raw flags the user has.
     */
    private final int raw;

    /**
     * Construct the flags for a user.
     *
     * @param detailsJson the user details json
     * @return the constructed flags
     */
    @NonNull
    protected static UserFlags fromJson(@NonNull JSONObject detailsJson) {
        int flags = detailsJson.getInt("flags");
        return new UserFlags(User.UserFlag.getFlags(flags), flags);
    }
}