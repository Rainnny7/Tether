package me.braydon.tether.model.user.avatar;

import kong.unirest.core.json.JSONObject;
import lombok.*;
import me.braydon.tether.model.user.DiscordUser;

/**
 * The avatar decoration
 * of a {@link DiscordUser}.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter @EqualsAndHashCode
public class AvatarDecoration {
    /**
     * The asset of this decoration.
     */
    @NonNull private final DecorationAsset asset;

    /**
     * The id of the decoration sku.
     */
    @NonNull private final String skuId;

    /**
     * The unix time of when this decorations expires, null if permanent.
     */
    private final Long expires;

    /**
     * Construct an avatar decoration for a user.
     *
     * @param decorationJson the decoration json
     * @return the constructed decoration
     */
    @NonNull
    public static AvatarDecoration fromJson(@NonNull JSONObject decorationJson) {
        DecorationAsset asset = DecorationAsset.fromJson(decorationJson);
        String skuId = decorationJson.getString("sku_id");
        long expires = decorationJson.optLong("expires_at", -1L);
        return new AvatarDecoration(asset, skuId, expires == -1L ? null : expires);
    }
}