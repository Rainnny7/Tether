package me.braydon.tether.model.user.collectibles;

import kong.unirest.core.json.JSONObject;
import lombok.*;

/**
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public final class Collectibles {
    /**
     * The nameplate for the user, if any.
     */
    private final Nameplate nameplate;

    /**
     * Construct the collectibles for a user.
     *
     * @param json name collectibles json
     */
    public static Collectibles fromJson(JSONObject json) {
        if (json == null) {
            return new Collectibles(null);
        }
        Nameplate nameplate = json.isNull("nameplate") ? null : Nameplate.fromJson(json.getJSONObject("nameplate"));
        return new Collectibles(nameplate);
    }
}