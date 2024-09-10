package me.braydon.tether.model.user;

import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;
import lombok.*;

/**
 * The theme of a {@link DiscordUser}'s profile.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class ProfileTheme {
    /**
     * The primary (hex) color of this theme.
     */
    @NonNull private final String primary;

    /**
     * The accent (hex) color of this theme.
     */
    @NonNull private final String accent;

    /**
     * Construct a profile theme for a user.
     *
     * @param userProfileJson the user profile json
     * @return the constructed profile theme, null if none
     */
    protected static ProfileTheme fromJson(JSONObject userProfileJson) {
        JSONArray themeJson;
        if (userProfileJson == null || ((themeJson = userProfileJson.isNull("theme_colors") ? null
                : userProfileJson.getJSONArray("theme_colors")) == null)) {
            return null;
        }
        String primaryHex = String.format("#%06X", (0xFFFFFF & themeJson.getInt(0)));
        String accentHex = String.format("#%06X", (0xFFFFFF & themeJson.getInt(1)));
        return new ProfileTheme(primaryHex, accentHex);
    }
}