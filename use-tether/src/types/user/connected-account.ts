/**
 * A connected account of a {@link DiscordUser}.
 */
export type ConnectedAccount = {
    /**
     * The id of this account.
     */
    id: string;

    /**
     * The type of this account.
     */
    type: string;

    /**
     * The name of this account.
     */
    name: string;

    /**
     * The metadata for this account.
     */
    metadata: {
        [key: string]: string;
    };

    /**
     * Whether this account is verified.
     */
    verified: boolean;
};
