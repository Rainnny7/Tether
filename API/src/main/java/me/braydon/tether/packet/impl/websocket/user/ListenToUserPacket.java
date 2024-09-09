package me.braydon.tether.packet.impl.websocket.user;

import lombok.Getter;
import lombok.Setter;
import me.braydon.tether.packet.OpCode;
import me.braydon.tether.packet.Packet;

/**
 * This packet is sent from the client to the
 * server to indicate that the client wants to
 * listen to a specific user and get their status.
 *
 * @author Braydon
 */
@Setter @Getter
public final class ListenToUserPacket extends Packet {
    /**
     * The snowflake of the user to listen to.
     */
    private long snowflake;

    public ListenToUserPacket() {
        super(OpCode.LISTEN_TO_USER);
    }
}