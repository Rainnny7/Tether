package me.braydon.tether.model.user;

import kong.unirest.core.json.JSONObject;
import lombok.*;

/**
 * A linked connection to a {@link DiscordUser}.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class ConnectedAccount {
    /**
     * The id of this account.
     */
    @NonNull private final String id;

    /**
     * The type of this account.
     */
    @NonNull private final String type;

    /**
     * The name of this account.
     */
    @NonNull private final String name;

    /**
     * Whether this account is verified.
     */
    private final boolean verified;

    /**
     * Construct a connected account for a user.
     *
     * @param accountJson the connected account json
     * @return the constructed account
     */
    @NonNull
    protected static ConnectedAccount fromJson(@NonNull JSONObject accountJson) {
        String id = accountJson.getString("id");
        String type = accountJson.getString("type");
        String name = accountJson.getString("name");
        boolean verified = accountJson.getBoolean("verified");
        return new ConnectedAccount(id, type, name, verified);
    }
}