package me.braydon.tether.packet;

/**
 * Op codes for {@link Packet}'s.
 *
 * @author Braydon
 */
public final class OpCode {
    // User Status
    public static final int LISTEN_TO_USER = 0;
    public static final int USER_STATUS = 1;

    // Misc
    public static final int ERROR_MESSAGE = 99;
}