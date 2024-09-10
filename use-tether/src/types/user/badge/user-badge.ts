import { UserBadgeIcon } from "@/types/user/badge/user-badge-icon";

/**
 * The badge of a {@link DiscordUser}.
 */
export type UserBadge = {
    /**
     * The id of this user badge.
     */
    id: string;

    /**
     * The description of this user badge.
     */
    description: string;

    /**
     * The icon of this user badge.
     */
    icon: UserBadgeIcon;

    /**
     * The link to this user badge, if any.
     */
    link: string | undefined;
};
