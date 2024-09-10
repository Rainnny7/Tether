import { DiscordUser } from "@/types/user/user";

type SocketPacket = {
    /**
     * The OP code for this packet.
     */
    op: number;
};

export type UserStatusPacket = SocketPacket & {
    /**
     * The user the status is for.
     */
    user: DiscordUser;
};
