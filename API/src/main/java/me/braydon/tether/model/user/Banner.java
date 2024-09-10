package me.braydon.tether.model.user;

import kong.unirest.core.json.JSONObject;
import lombok.*;
import me.braydon.tether.common.DiscordUtils;

/**
 * A {@link DiscordUser}'s banner.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class Banner {
    private static final String BANNER_URL = "https://cdn.discordapp.com/banners/%s/%s.%s";

    /**
     * The id of the user's banner.
     */
    @NonNull private final String id;

    /**
     * The URL of the user's banner.
     */
    @NonNull private final String url;

    /**
     * Construct a banner for a user.
     *
     * @param userSnowflake the snowflake of the user the avatar belongs to
     * @param detailsJson   the user details json
     * @return the constructed banner, if any
     */
    protected static Banner fromJson(long userSnowflake, @NonNull JSONObject detailsJson) {
        String bannerId = detailsJson.optString("banner", null);
        if (bannerId == null) {
            return null;
        }
        return new Banner(bannerId, BANNER_URL.formatted(userSnowflake, bannerId, DiscordUtils.getMediaExtension(bannerId)));
    }
}