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
public class UserBadge {
    /**
     * The id of this user badge.
     */
    @NonNull private final String id;

    /**
     * The description of this user badge.
     */
    @NonNull private final String description;

    /**
     * The icon of this user badge.
     */
    @NonNull private final UserBadgeIcon icon;

    /**
     * The link to this user badge, if any.
     */
    private final String link;

    /**
     * Construct a badge for a user.
     *
     * @param badgeJson the badge json
     * @return the constructed user badge
     */
    @NonNull
    public static UserBadge fromJson(@NonNull JSONObject badgeJson) {
        String id = badgeJson.getString("id");
        String description = badgeJson.getString("description");
        UserBadgeIcon icon = UserBadgeIcon.fromJson(badgeJson);
        String link = badgeJson.optString("link", null);
        return new UserBadge(id, description, icon, link);
    }
}