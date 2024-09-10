import { ClanBadge } from "@/types/user/clan/clan-badge";

/**
 * A clan a {@link DiscordUser} is in.
 */
export type Clan = {
    /**
     * The snowflake of the Guild this clan belongs to.
     */
    guildSnowflake: number;

    /**
     * The tag of this clan.
     */
    tag: string;

    /**
     * The badge for this clan.
     */
    clanBadge: ClanBadge;

    /**
     * Whether the identity is enabled for this clan.
     */
    identityEnabled: boolean;
};
