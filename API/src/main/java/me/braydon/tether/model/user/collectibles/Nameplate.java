package me.braydon.tether.model.user.collectibles;

import kong.unirest.core.json.JSONObject;
import lombok.*;

import java.util.Date;

/**
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class Nameplate {
    /**
     * The label for this nameplate.
     */
    @NonNull private final String label;

    /**
     * The palette the nameplate is from.
     */
    @NonNull private final String palette;

    /**
     * The asset path for this nameplate.
     */
    @NonNull private final String asset;

    /**
     * The date this nameplate expires at, or null if none.
     */
    private final Date expiresAt;

    /**
     * Construct a nameplate for a user's collectibles.
     *
     * @param json the nameplate json
     * @return the constructed nameplate
     */
    protected static Nameplate fromJson(@NonNull JSONObject json) {
        return new Nameplate(json.getString("label"), json.getString("palette"),
                json.getString("asset"), json.isNull("expires_at") ? null : new Date(json.getLong("expires_at"))
        );
    }
}