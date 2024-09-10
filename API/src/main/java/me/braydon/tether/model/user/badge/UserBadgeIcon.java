package me.braydon.tether.model.user.badge;

import kong.unirest.core.json.JSONObject;
import lombok.*;
import me.braydon.tether.model.user.DiscordUser;

/**
 * A {@link DiscordUser}'s avatar.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class UserBadgeIcon {
    private static final String BADGE_URL = "https://cdn.discordapp.com/badge-icons/%s.png";

    /**
     * The id of the user's badge.
     */
    @NonNull private final String id;

    /**
     * The URL of the user's badge.
     */
    @NonNull private final String url;

    /**
     * Construct a badge for a user.
     *
     * @param badgeJson the badge json
     * @return the constructed user badge
     */
    @NonNull
    protected static UserBadgeIcon fromJson(@NonNull JSONObject badgeJson) {
        String badgeId = badgeJson.getString("icon");
        return new UserBadgeIcon(badgeId, BADGE_URL.formatted(badgeId));
    }
}