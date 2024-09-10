package me.braydon.tether.model.user.avatar;

import kong.unirest.core.json.JSONObject;
import lombok.*;
import me.braydon.tether.model.user.DiscordUser;

/**
 * The decoration for a {@link DiscordUser}'s {@link Avatar}.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class DecorationAsset {
    private static final String DECORATION_URL = "https://cdn.discordapp.com/avatar-decoration-presets/%s.png";

    /**
     * The id of the clan badge.
     */
    @NonNull private final String id;

    /**
     * The URL of the clan badge.
     */
    @NonNull private final String url;

    /**
     * Construct an avatar decoration asset for a user.
     *
     * @param decorationJson the clan json
     * @return the constructed asset
     */
    @NonNull
    protected static DecorationAsset fromJson(@NonNull JSONObject decorationJson) {
        String badgeId = decorationJson.getString("asset");
        return new DecorationAsset(badgeId, DECORATION_URL.formatted(badgeId));
    }
}