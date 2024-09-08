package me.braydon.tether.model;

import lombok.*;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

/**
 * A model of a Discord user.
 *
 * @author Braydon
 */
@AllArgsConstructor @Getter @EqualsAndHashCode(onlyExplicitlyIncluded = true) @ToString
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
     * The display name of this user, if any.
     */
    private final String displayName;

    /**
     * The flags of this user.
     */
    @NonNull private final UserFlags flags;

    /**
     * The avatar of this user.
     */
    @NonNull private final Avatar avatar;

    /**
     * The banner of this user, if any.
     */
    private final Banner banner;

    /**
     * The accent color of this user.
     */
    @NonNull private final String accentColor;

    /**
     * The online status of this user, if known.
     */
    private final OnlineStatus onlineStatus;

    /**
     * The clients this user is active on, if known.
     */
    private final EnumSet<ClientType> activeClients;

    /**
     * The activities of this user, if known.
     */
    private final List<Activity> activities;

    /**
     * The Spotify activity of this user, if known.
     */
    private final SpotifyActivity spotify;

    /**
     * Is this user a bot?
     */
    private final boolean bot;

    /**
     * The user creation date.
     */
    @NonNull private final OffsetDateTime createdAt;

    /**
     * Builds a Discord user from the
     * raw entities returned from Discord.
     *
     * @param user the raw user entity
     * @param profile the raw profile entity
     * @param member the raw member entity, if any
     * @return the built user
     */
    @NonNull
    public static DiscordUser buildFromEntity(@NonNull User user, @NonNull User.Profile profile, Member member) {
        Avatar avatar = new Avatar(user.getAvatarId() == null ? user.getDefaultAvatarId() : user.getAvatarId(), user.getEffectiveAvatarUrl());
        Banner banner = profile.getBannerId() == null || profile.getBannerUrl() == null ? null : new Banner(profile.getBannerId(), profile.getBannerUrl());
        String accentColor = String.format("#%06X", (0xFFFFFF & profile.getAccentColorRaw()));

        OnlineStatus onlineStatus = member == null ? null : member.getOnlineStatus();
        EnumSet<ClientType> activeClients = member == null ? null : member.getActiveClients();
        List<Activity> activities = member == null ? null : member.getActivities();
        SpotifyActivity spotify = null;
        if (activities != null) {
            for (Activity activity : activities) {
                if (!activity.getName().equals("Spotify") || !activity.isRich()) {
                    continue;
                }
                spotify = SpotifyActivity.fromActivity(Objects.requireNonNull(activity.asRichPresence()));
                break;
            }
        }
        return new DiscordUser(
                user.getIdLong(), user.getName(), user.getGlobalName(), new UserFlags(user.getFlags(), user.getFlagsRaw()),
                avatar, banner, accentColor, onlineStatus, activeClients, activities, spotify, user.isBot(), user.getTimeCreated()
        );
    }

    /**
     * A user's flags.
     */
    @AllArgsConstructor @Getter
    public static class UserFlags {
        private final EnumSet<User.UserFlag> list;
        private final int raw;
    }

    /**
     * A user's avatar.
     */
    @AllArgsConstructor @Getter
    public static class Avatar {
        @NonNull private final String id;
        @NonNull private final String url;
    }

    /**
     * A user's banner.
     */
    @AllArgsConstructor @Getter
    public static class Banner {
        @NonNull private final String id;
        @NonNull private final String url;
    }

    /**
     * A user's Spotify activity data.
     */
    @AllArgsConstructor @Getter
    public static class SpotifyActivity {
        @NonNull private final String song;
        @NonNull private final String artist;
        @NonNull private final String trackProgress;
        @NonNull private final String trackLength;
        private final long started;
        private final long ends;

        /**
         * Build a Spotify activity from the raw Discord data.
         *
         * @param richPresence the raw Discord data
         * @return the built Spotify activity
         */
        @NonNull
        public static SpotifyActivity fromActivity(@NonNull RichPresence richPresence) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("m:ss");
            long started = Objects.requireNonNull(richPresence.getTimestamps()).getStart();
            long ends = richPresence.getTimestamps().getEnd();

            long trackLength = ends - started;
            long trackProgress = Math.min(System.currentTimeMillis() - started, trackLength);

            return new SpotifyActivity(
                    Objects.requireNonNull(richPresence.getDetails()), Objects.requireNonNull(richPresence.getState()).replace(";", ","),
                    dateFormat.format(trackProgress), dateFormat.format(trackLength),
                    started, ends
            );
        }
    }
}