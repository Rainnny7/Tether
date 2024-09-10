package me.braydon.tether.model.user.nitro;

import lombok.NonNull;

/**
 * Different types of Nitro subscriptions.
 *
 * @author Braydon
 */
public enum NitroType {
    CLASSIC, NITRO, BASIC, UNKNOWN;

    /**
     * Get the nitro type
     * from the given raw type.
     *
     * @param type the raw type
     * @return the nitro type
     */
    @NonNull
    public static NitroType byType(int type) {
        for (NitroType nitroType : values()) {
            if (type == nitroType.ordinal() + 1) {
                return nitroType;
            }
        }
        return UNKNOWN;
    }
}