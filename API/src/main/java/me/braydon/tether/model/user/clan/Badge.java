package me.braydon.tether.model.user.clan;

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
public class Badge {
    private static final String CLAN_BADGE_URL = "https://cdn.discordapp.com/clan-badges/%s/%s.%s";

    /**
     * The id of the clan badge.
     */
    @NonNull private final String id;

    /**
     * The URL of the clan badge.
     */
    @NonNull private final String url;

    /**
     * Construct a badge for a clan.
     *
     * @param userSnowflake the user's snowflake
     * @param clanJson      the clan json
     * @return the constructed clan badge
     */
    @NonNull
    protected static Badge fromJson(long userSnowflake, @NonNull JSONObject clanJson) {
        String badgeId = clanJson.getString("badge");
        return new Badge(badgeId, CLAN_BADGE_URL.formatted(userSnowflake, badgeId, DiscordUtils.getMediaExtension(badgeId)));
    }
}