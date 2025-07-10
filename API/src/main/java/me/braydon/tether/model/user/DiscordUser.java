package me.braydon.tether.model.user;

import kong.unirest.core.json.JSONObject;
import lombok.*;
import me.braydon.tether.common.DiscordUtils;
import me.braydon.tether.model.user.avatar.Avatar;
import me.braydon.tether.model.user.avatar.AvatarDecoration;
import me.braydon.tether.model.user.badge.UserBadge;
import me.braydon.tether.model.user.clan.Clan;
import me.braydon.tether.model.user.collectibles.Collectibles;
import me.braydon.tether.model.user.nitro.NitroSubscription;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ClientType;
import net.dv8tion.jda.api.entities.Member;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * A model of a Discord user.
 * <p>
 * This model will contain ALL
 * data returned from Discord.
 * </p>
 *
 * @author Braydon
 */
@AllArgsConstructor @Getter @EqualsAndHashCode @ToString
public final class DiscordUser {
    /**
     * The unique snowflake of this user.
     */
    @EqualsAndHashCode.Include private final long snowflake;

    /**
     * The username of this user.
     */
    @NonNull private final String username;

    /**
     * This user's legacy username, if any.
     */
    private final String legacyUsername;

    /**
     * The display name of this user, if any.
     */
    private final String displayName;

    /**
     * The user's discriminator, 0 if not legacy.
     */
    private final int discriminator;

    /**
     * The flags of this user.
     */
    @NonNull private final UserFlags flags;

    /**
     * The avatar of this user.
     */
    @NonNull private final Avatar avatar;

    /**
     * The avatar decoration of this user, if any.
     */
    private final AvatarDecoration avatarDecoration;

    /**
     * The banner of this user, if any.
     */
    private final Banner banner;

    /**
     * The banner color (hex) of this user, if any.
     */
    private final String bannerColor;

    /**
     * The collectibles this user has, if any.
     */
    private final Collectibles collectibles;

    /**
     * The profile theme of this user, if any.
     */
    private final ProfileTheme profileTheme;

    /**
     * The profile effect this user has, if any.
     */
    private final ProfileEffect profileEffect;

    /**
     * The custom status of this user, if any.
     */
    private final CustomStatus customStatus;

    /**
     * The user's bio, if any.
     */
    private final String bio;

    /**
     * The user's pronouns, if any.
     */
    private final String pronouns;

    /**
     * The accent color (hex) of this user.
     */
    @NonNull private final String accentColor;

    /**
     * The online status of this user.
     */
    @NonNull private final OnlineStatus onlineStatus;

    /**
     * The clients this user is active on, if known.
     */
    @NonNull private final EnumSet<ClientType> activeClients;

    /**
     * The activities of this user, if known.
     */
    private final List<Activity> activities;

    /**
     * The Spotify activity of this user, if known.
     */
    @EqualsAndHashCode.Exclude private final SpotifyActivity spotify;

    /**
     * The badges this user has.
     */
    @NonNull private final Set<UserBadge> badges;

    /**
     * The connected accounts of this user.
     */
    @NonNull private final Set<ConnectedAccount> connectedAccounts;

    /**
     * The clan this user is in, if any.
     */
    private final Clan clan;

    /**
     * This user's Nitro subscription, if any.
     */
    private final NitroSubscription nitroSubscription;

    /**
     * Is this user a bot?
     */
    private final boolean bot;

    /**
     * The unix time of when this user joined Discord.
     */
    private final long createdAt;

    /**
     * Construct a Discord user from the
     * raw entities returned from Discord.
     *
     * @param userJson the Json object for the user's data
     * @param member   the raw member entity, if any
     * @return the constructed user
     */
    @NonNull
    public static DiscordUser buildFromEntity(@NonNull JSONObject userJson, Member member) {
        JSONObject detailsJson = userJson.has("user") ? userJson.getJSONObject("user") : userJson;

        long snowflake = Long.parseLong(detailsJson.getString("id"));
        String legacyUsername = userJson.optString("legacy_username", null);
        int discriminator = Integer.parseInt(detailsJson.getString("discriminator"));

        AvatarDecoration avatarDecoration = detailsJson.isNull("avatar_decoration_data") ? null
                : AvatarDecoration.fromJson(detailsJson.getJSONObject("avatar_decoration_data"));

        String bio = detailsJson.optString("bio", null);
        if (bio != null && (bio = bio.trim()).isEmpty()) {
            bio = null;
        }
        String accentColor = String.format("#%06X", detailsJson.isNull("accent_color") ? 0xFFFFFF
                : 0xFFFFFF & detailsJson.getInt("accent_color"));
        Clan clan = detailsJson.isNull("clan") ? null : Clan.fromJson(detailsJson.getJSONObject("clan"));
        NitroSubscription nitroSubscription = userJson.isNull("premium_type")
                || userJson.isNull("premium_since") ? null : NitroSubscription.fromJson(userJson);

        JSONObject collectiblesJson = !detailsJson.isNull("collectibles") ? detailsJson.getJSONObject("collectibles") : null;

        JSONObject profileJson = userJson.has("user_profile") ? userJson.getJSONObject("user_profile") : null;
        String pronouns = null;
        if (profileJson != null) {
            pronouns = profileJson.optString("pronouns", null);
            if (pronouns != null && (pronouns = pronouns.trim()).isEmpty()) {
                pronouns = null;
            }
        }

        // Get the user's online status
        OnlineStatus onlineStatus = member == null ? OnlineStatus.OFFLINE : member.getOnlineStatus();
        if (onlineStatus == OnlineStatus.UNKNOWN) {
            onlineStatus = OnlineStatus.OFFLINE;
        }

        // Get the user's activities
        List<Activity> activities = member == null ? Collections.emptyList() : member.getActivities();

        // Finally return the constructed user
        return new DiscordUser(
                snowflake, detailsJson.getString("username"), legacyUsername, detailsJson.optString("global_name", null),
                discriminator, UserFlags.fromJson(detailsJson), Avatar.fromJson(snowflake, discriminator, legacyUsername != null, detailsJson),
                avatarDecoration, Banner.fromJson(snowflake, detailsJson), detailsJson.optString("banner_color", null),
                Collectibles.fromJson(collectiblesJson), ProfileTheme.fromJson(profileJson), ProfileEffect.fromJson(profileJson),
                CustomStatus.fromActivities(activities), bio, pronouns, accentColor, onlineStatus,
                member == null ? EnumSet.noneOf(ClientType.class) : member.getActiveClients(), activities, SpotifyActivity.fromActivities(activities),
                UserBadge.fromJson(userJson), ConnectedAccount.fromJson(userJson), clan, nitroSubscription, detailsJson.optBoolean("bot", false),
                DiscordUtils.getTimeCreated(snowflake)
        );
    }
}