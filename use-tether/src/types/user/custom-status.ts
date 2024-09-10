/**
 * The custom status of a {@link DiscordUser}.
 */
export type CustomStatus = {
    /**
     * The value of this status.
     */
    value: string;

    /**
     * The unicode emoji for this status, undefined if none.
     */
    emoji: string | undefined;
};
