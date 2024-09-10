/**
 * The flags of a {@link DiscordUser}.
 */
export type UserFlags = {
    /**
     * The list of flags the user has.
     */
    list: (
        | "STAFF"
        | "PARTNER"
        | "HYPESQUAD"
        | "BUG_HUNTER_LEVEL_1"
        | "HYPESQUAD_BRAVERY"
        | "HYPESQUAD_BRILLIANCE"
        | "HYPESQUAD_BALANCE"
        | "EARLY_SUPPORTER"
        | "TEAM_USER"
        | "BUGHUNTER_LEVEL_2"
        | "VERIFIED_BOT"
        | "VERIFIED_DEVELOPER"
        | "CERTIFIED_MODERATOR"
        | "BOT_HTTP_INTERACTIONS"
        | "ACTIVE_DEVELOPER"
        | "UNKNOWN"
    )[];

    /**
     * The raw flags the user has.
     */
    raw: number;
};
