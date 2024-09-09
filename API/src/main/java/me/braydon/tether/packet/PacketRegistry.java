package me.braydon.tether.packet;

import me.braydon.tether.packet.impl.websocket.user.ListenToUserPacket;
import me.braydon.tether.packet.impl.websocket.user.UserStatusPacket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry of {@link Packet}'s.
 *
 * @author Braydon
 */
public final class PacketRegistry {
    /**
     * A registry of packets, identified by their op code.
     */
    private static final Map<Integer, Class<? extends Packet>> REGISTRY = Collections.synchronizedMap(new HashMap<>());
    static {
        register(OpCode.LISTEN_TO_USER, ListenToUserPacket.class);
        register(OpCode.USER_STATUS, UserStatusPacket.class);
    }

    /**
     * Register a packet.
     *
     * @param opCode the packet op code
     * @param packet the packet
     */
    public static void register(int opCode, Class<? extends Packet> packet) {
        REGISTRY.put(opCode, packet);
    }

    /**
     * Get a packet from the registry by its op code.
     *
     * @param opCode the packet op code
     * @return the packet, null if none
     */
    public static Class<? extends Packet> get(int opCode) {
        return REGISTRY.get(opCode);
    }
}