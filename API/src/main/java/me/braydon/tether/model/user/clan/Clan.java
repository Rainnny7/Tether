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
    @NonNull private final Badge badge;

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
    @NonNull
    public static Clan fromJson(@NonNull JSONObject clanJson) {
        long snowflake = Long.parseLong(clanJson.getString("identity_guild_id"));
        String tag = clanJson.getString("tag");
        Badge badge = Badge.fromJson(snowflake, clanJson);
        boolean identityEnabled = clanJson.getBoolean("identity_enabled");
        return new Clan(snowflake, tag, badge, identityEnabled);
    }
}