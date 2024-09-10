package me.braydon.tether.model.user.avatar;

import kong.unirest.core.json.JSONObject;
import lombok.*;
import me.braydon.tether.common.DiscordUtils;
import me.braydon.tether.model.user.DiscordUser;

/**
 * A {@link DiscordUser}'s avatar.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class Avatar {
    private static final String DEFAULT_AVATAR_URL = "https://cdn.discordapp.com/embed/avatars/%s.png";
    private static final String AVATAR_URL = "https://cdn.discordapp.com/avatars/%s/%s.%s";

    /**
     * The id of the user's avatar.
     */
    @NonNull private final String id;

    /**
     * The URL of the user's avatar.
     */
    @NonNull private final String url;

    /**
     * Construct an avatar for a user.
     *
     * @param userSnowflake     the snowflake of the user the avatar belongs to
     * @param userDiscriminator the snowflake of the user the avatar belongs to
     * @param isUserLegacy      whether the user is legacy
     * @param detailsJson       the user details json
     * @return the constructed avatar
     */
    @NonNull
    public static Avatar fromJson(long userSnowflake, int userDiscriminator, boolean isUserLegacy, @NonNull JSONObject detailsJson) {
        String avatarId = detailsJson.getString("avatar");
        String avatarUrl;
        if (avatarId == null) { // Fallback to the default avatar
            avatarId = String.valueOf(isUserLegacy ? userDiscriminator % 5 : (userDiscriminator >> 22) % 6);
            avatarUrl = DEFAULT_AVATAR_URL.formatted(avatarId);
        } else {
            avatarUrl = AVATAR_URL.formatted(userSnowflake, avatarId, DiscordUtils.getMediaExtension(avatarId));
        }
        return new Avatar(avatarId, avatarUrl);
    }
}