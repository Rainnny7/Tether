package me.braydon.tether.model.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.entities.RichPresence;

import java.util.Objects;

/**
 * The Spotify activity data
 * of a {@link DiscordUser}.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter
public class SpotifyActivity {
    /**
     * The ID of the currently playing track.
     */
    @NonNull private final String trackId;

    /**
     * The name of the currently playing track.
     */
    @NonNull private final String song;

    /**
     * The currently playing artist.
     */
    @NonNull private final String artist;

    /**
     * The album the song is from.
     */
    @NonNull private final String album;

    /**
     * The URL to the art for the currently playing album.
     */
    @NonNull private final String albumArtUrl;

    /**
     * The URL to the playing track.
     */
    @NonNull private final String trackUrl;

    /**
     * The current progress of the track (in millis).
     */
    private final long trackProgress;

    /**
     * The total length of the track (in millis).
     */
    private final long trackLength;

    /**
     * The unix time of when this track started playing.
     */
    private final long started;

    /**
     * The unix time of when this track stops playing.
     */
    private final long ends;

    /**
     * Construct a Spotify activity for a user.
     *
     * @param richPresence the raw Discord data
     * @return the constructed activity
     */
    @NonNull @SuppressWarnings("DataFlowIssue")
    protected static SpotifyActivity fromActivity(@NonNull RichPresence richPresence) {
        String trackUrl = "https://open.spotify.com/track/" + richPresence.getSyncId();

        // Track progress
        long started = Objects.requireNonNull(richPresence.getTimestamps()).getStart();
        long ends = richPresence.getTimestamps().getEnd();

        long trackLength = ends - started;
        long trackProgress = Math.min(System.currentTimeMillis() - started, trackLength);

        return new SpotifyActivity(
                richPresence.getSyncId(), richPresence.getDetails(), richPresence.getState().replace(";", ","),
                richPresence.getLargeImage().getText(), richPresence.getLargeImage().getUrl(), trackUrl, trackProgress,
                trackLength, started, ends
        );
    }
}