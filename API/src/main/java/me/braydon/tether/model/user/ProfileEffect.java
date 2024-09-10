package me.braydon.tether.model.user;

import kong.unirest.core.json.JSONObject;
import lombok.*;

/**
 * A profile effect for a  {@link DiscordUser}.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class ProfileEffect {
    /**
     * The id of the user's profile effect.
     */
    @NonNull private final String id;

    /**
     * The unix time of when this profile effect expires, null if permanent.
     */
    private final Long expires;

    /**
     * Construct a profile effect for a user.
     *
     * @param userProfileJson the user profile json
     * @return the constructed profile effect, null if none
     */
    protected static ProfileEffect fromJson(JSONObject userProfileJson) {
        JSONObject effectJson;
        if (userProfileJson == null || ((effectJson = userProfileJson.isNull("profile_effect") ? null
                : userProfileJson.getJSONObject("profile_effect")) == null)) {
            return null;
        }
        String id = effectJson.getString("id");
        long expires = effectJson.optLong("expires_at", -1L);
        return new ProfileEffect(id, expires == -1L ? null : expires);
    }
}