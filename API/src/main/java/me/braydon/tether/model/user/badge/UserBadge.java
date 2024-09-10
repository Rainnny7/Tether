package me.braydon.tether.model.user.badge;

import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;
import lombok.*;
import me.braydon.tether.model.user.DiscordUser;

import java.util.HashSet;
import java.util.Set;

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
     * Construct the badges for a user.
     *
     * @param userJson the user's json
     * @return the constructed badges
     */
    @NonNull
    public static Set<UserBadge> fromJson(@NonNull JSONObject userJson) {
        Set<UserBadge> badges = new HashSet<>();
        if (!userJson.has("badges")) {
            return badges;
        }
        JSONArray badgesArray = userJson.getJSONArray("badges");
        for (int i = 0; i < badgesArray.length(); i++) {
            JSONObject badgeJson = badgesArray.getJSONObject(i);
            String id = badgeJson.getString("id");
            String description = badgeJson.getString("description");
            UserBadgeIcon icon = UserBadgeIcon.fromJson(badgeJson);
            String link = badgeJson.optString("link", null);
            badges.add(new UserBadge(id, description, icon, link));
        }
        return badges;
    }
}