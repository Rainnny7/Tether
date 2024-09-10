import { UserFlags } from "@/types/user/user-flags";
import { Avatar } from "@/types/user/avatar/avatar";
import { Banner } from "@/types/user/banner";
import { AvatarDecoration } from "@/types/user/avatar/avatar-decoration";
import { CustomStatus } from "@/types/user/custom-status";
import { SpotifyActivity } from "@/types/user/spotify-activity";
import { UserBadge } from "@/types/user/badge/user-badge";
import { ConnectedAccount } from "@/types/user/connected-account";
import { Clan } from "@/types/user/clan/clan";
import { NitroSubscription } from "@/types/user/nitro-subscription";

export type DiscordUser = {
    /**
     * The unique snowflake of this user.
     */
    snowflake: number;

    /**
     * The username of this user.
     */
    username: string;

    /**
     * This user's legacy username, if any.
     */
    legacyUsername: string | undefined;

    /**
     * The display name of this user, if any.
     */
    displayName: string | undefined;

    /**
     * The user's discriminator, 0 if not legacy.
     */
    discriminator: number;

    /**
     * The flags of this user.
     */
    flags: UserFlags;

    /**
     * The avatar of this user.
     */
    avatar: Avatar;

    /**
     * The avatar decoration of this user, if any.
     */
    avatarDecoration: AvatarDecoration | undefined;

    /**
     * The banner of this user, if any.
     */
    banner: Banner | undefined;

    /**
     * The banner color (hex) of this user, if any.
     */
    bannerColor: string | undefined;

    /**
     * The custom status of this user, if any.
     */
    customStatus: CustomStatus | undefined;

    /**
     * The user's bio, if any.
     */
    bio: string | undefined;

    /**
     * The user's pronouns, if any.
     */
    pronouns: string | undefined;

    /**
     * The accent color of this user.
     */
    accentColor: string;

    /**
     * The online status of this user, if known.
     */
    onlineStatus: "ONLINE" | "IDLE" | "DO_NOT_DISTURB" | "OFFLINE";

    /**
     * The clients this user is active on, if known.
     */
    activeClients: "DESKTOP" | "MOBILE" | "WEB" | undefined;

    /**
     * The Spotify activity of this user, if known.
     */
    spotify: SpotifyActivity | undefined;

    /**
     * The badges this user has.
     */
    badges: UserBadge[];

    /**
     * The connected accounts of this user.
     */
    connectedAccounts: ConnectedAccount[];

    /**
     * The clan this user is in, if any.
     */
    clan: Clan | undefined;

    /**
     * This user's Nitro subscription, if any.
     */
    nitroSubscription: NitroSubscription | undefined;

    /**
     * Is this user a bot?
     */
    bot: boolean;

    /**
     * The unix time of when this user joined Discord.
     */
    createdAt: number;
};
