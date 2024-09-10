package me.braydon.tether.model.user.nitro;

import kong.unirest.core.json.JSONObject;
import lombok.*;
import me.braydon.tether.model.user.DiscordUser;

import java.time.ZonedDateTime;

/**
 * A Nitro subscription for a {@link DiscordUser}.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class NitroSubscription {
    /**
     * The type of this subscription.
     */
    @NonNull private final NitroType type;

    /**
     * The unix time this subscription was started.
     */
    private final long subscribed;

    /**
     * Construct a Nitro subscription for a user.
     *
     * @param userJson the user json
     * @return the constructed subscription
     */
    @NonNull
    public static NitroSubscription fromJson(@NonNull JSONObject userJson) {
        NitroType type = NitroType.byType(userJson.getInt("premium_type"));
        long subscribed = ZonedDateTime.parse(userJson.getString("premium_since")).toInstant().toEpochMilli();
        return new NitroSubscription(type, subscribed);
    }
}