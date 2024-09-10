export type DiscordUser = {
    /**
     * The unique snowflake of this user.
     */
    snowflake: number;

    /**
     * The username of this user.
     */
    username: string;

    /**
     * The display name of this user, if any.
     */
    displayName: string | undefined;

    /**
     * The flags of this user.
     */
    flags: UserFlags;

    /**
     * The avatar of this user.
     */
    avatar: Avatar;

    /**
     * The banner of this user, if any.
     */
    banner: Banner | undefined;

    /**
     * The accent color of this user.
     */
    accentColor: string;

    /**
     * The online status of this user, if known.
     */
    onlineStatus: "ONLINE" | "IDLE" | "DO_NOT_DISTURB" | "OFFLINE";

    /**
     * The clients this user is active on, if known.
     */
    activeClients: "DESKTOP" | "MOBILE" | "WEB" | undefined;

    /**
     * The Spotify activity of this user, if known.
     */
    spotify: SpotifyActivity | undefined;

    /**
     * Is this user a bot?
     */
    bot: boolean;

    /**
     * The unix time of when this user joined Discord.
     */
    createdAt: number;
};

/**
 * A user's flags.
 */
export type UserFlags = {
    /**
     * The list of flags the user has.
     */
    list: (
        | "STAFF"
        | "PARTNER"
        | "HYPESQUAD"
        | "BUG_HUNTER_LEVEL_1"
        | "HYPESQUAD_BRAVERY"
        | "HYPESQUAD_BRILLIANCE"
        | "HYPESQUAD_BALANCE"
        | "EARLY_SUPPORTER"
        | "TEAM_USER"
        | "BUGHUNTER_LEVEL_2"
        | "VERIFIED_BOT"
        | "VERIFIED_DEVELOPER"
        | "CERTIFIED_MODERATOR"
        | "BOT_HTTP_INTERACTIONS"
        | "ACTIVE_DEVELOPER"
        | "UNKNOWN"
    )[];

    /**
     * The raw flags the user has.
     */
    raw: number;
};

/**
 * A user's avatar.
 */
export type Avatar = {
    /**
     * The id of the user's avatar.
     */
    id: string;

    /**
     * The URL of the user's avatar.
     */
    url: string;
};

/**
 * A user's banner.
 */
export type Banner = {
    /**
     * The id of the user's avatar.
     */
    id: string;

    /**
     * The URL of the user's avatar.
     */
    url: string;
};

/**
 * A user's Spotify activity data.
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
