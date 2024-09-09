package me.braydon.tether.packet.impl.websocket.misc;

import lombok.NonNull;
import me.braydon.tether.packet.OpCode;
import me.braydon.tether.packet.Packet;

/**
 * @author Braydon
 */
public final class ErrorMessagePacket extends Packet {
    /**
     * The error message.
     */
    @NonNull private final String message;

    public ErrorMessagePacket(@NonNull String message) {
        super(OpCode.ERROR_MESSAGE);
        this.message = message;
    }
}