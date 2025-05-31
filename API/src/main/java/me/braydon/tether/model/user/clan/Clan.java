package me.braydon.tether.model.user.clan;

import kong.unirest.core.json.JSONObject;
import lombok.*;
import me.braydon.tether.model.user.DiscordUser;

/**
 * The clan of a {@link DiscordUser}.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class Clan {
    /**
     * The snowflake of the Guild this clan belongs to.
     */
    private final long guildSnowflake;

    /**
     * The tag of this clan.
     */
    @NonNull private final String tag;

    /**
     * The badge for this clan.
     */
    @NonNull private final ClanBadge clanBadge;

    /**
     * Whether the identity is enabled for this clan.
     */
    private final boolean identityEnabled;

    /**
     * Construct a clan for a user.
     *
     * @param clanJson the user details json
     * @return the constructed clan
     */
    public static Clan fromJson(@NonNull JSONObject clanJson) {
        Object identityGuildId = clanJson.get("identity_guild_id");
        if (identityGuildId == null) {
            return null;
        }
        long snowflake = Long.parseLong(identityGuildId.toString());
        String tag = clanJson.getString("tag");
        ClanBadge clanBadge = ClanBadge.fromJson(snowflake, clanJson);
        boolean identityEnabled = clanJson.getBoolean("identity_enabled");
        return new Clan(snowflake, tag, clanBadge, identityEnabled);
    }
}