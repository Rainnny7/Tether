package me.braydon.tether.packet.impl.websocket.user;

import lombok.NonNull;
import me.braydon.tether.model.user.DiscordUser;
import me.braydon.tether.packet.OpCode;
import me.braydon.tether.packet.Packet;

/**
 * This packet is sent from the server to the
 * client to indicate the status of the user
 * that the client is listening to.
 *
 * @author Braydon
 */
public final class UserStatusPacket extends Packet {
    /**
     * The user to send the status of.
     */
    @NonNull private final DiscordUser user;

    public UserStatusPacket(@NonNull DiscordUser user) {
        super(OpCode.USER_STATUS);
        this.user = user;
    }
}