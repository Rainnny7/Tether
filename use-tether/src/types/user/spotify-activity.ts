/**
 * The Spotify activity for a {@link DiscordUser}.
 */
export type SpotifyActivity = {
    /**
     * The ID of the currently playing track.
     */
    trackId: string;

    /**
     * The name of the currently playing track.
     */
    song: string;

    /**
     * The currently playing artist.
     */
    artist: string;

    /**
     * The album the song is from.
     */
    album: string;

    /**
     * The URL to the art for the currently playing album.
     */
    albumArtUrl: string;

    /**
     * The URL to the playing track.
     */
    trackUrl: string;

    /**
     * The current progress of the track (in millis).
     */
    trackProgress: number;

    /**
     * The total length of the track (in millis).
     */
    trackLength: number;

    /**
     * The unix time of when this track started playing.
     */
    started: number;

    /**
     * The unix time of when this track stops playing.
     */
    ends: number;
};
