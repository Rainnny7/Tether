package me.braydon.tether.model.user;

import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;
import lombok.*;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
     * The metadata for this account.
     */
    @NonNull private final Map<String, String> metadata;

    /**
     * Whether this account is verified.
     */
    private final boolean verified;

    /**
     * Construct the connected accounts for a user.
     *
     * @param userJson the user's json
     * @return the constructed accounts
     */
    @NonNull
    public static Set<ConnectedAccount> fromJson(@NonNull JSONObject userJson) {
        Set<ConnectedAccount> connectedAccounts = new LinkedHashSet<>();
        if (userJson.has("connected_accounts")) {
            JSONArray accountsArray = userJson.getJSONArray("connected_accounts");
            for (int i = 0; i < accountsArray.length(); i++) {
                JSONObject accountJson = accountsArray.getJSONObject(i);
                String id = accountJson.getString("id");
                String type = accountJson.getString("type");
                String name = accountJson.getString("name");

                Map<String, String> metadata = new HashMap<>();
                if (accountJson.has("metadata")) {
                    for (Map.Entry<String, Object> entry : accountJson.getJSONObject("metadata").toMap().entrySet()) {
                        metadata.put(entry.getKey(), entry.getValue().toString());
                    }
                }

                boolean verified = accountJson.getBoolean("verified");
                connectedAccounts.add(new ConnectedAccount(id, type, name, metadata, verified));
            }
        }
        return connectedAccounts;
    }
}