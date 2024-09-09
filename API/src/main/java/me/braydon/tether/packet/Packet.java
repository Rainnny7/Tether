package me.braydon.tether.packet;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * A packet that can be
 * sent over the messenger.
 *
 * @author Braydon
 */
@AllArgsConstructor @Getter @ToString
public class Packet {
    /**
     * The Op code of this packet.
     */
    @SerializedName("op") private final int opCode;
}