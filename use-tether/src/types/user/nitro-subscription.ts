/**
 * A Nitro subscription for a {@link DiscordUser}.
 */
export type NitroSubscription = {
    /**
     * The type of this subscription.
     */
    type: "CLASSIC" | "NITRO" | "BASIC" | "UNKNOWN";

    /**
     * The unix time this subscription was started.
     */
    subscribed: number;
};
