package me.braydon.tether.common;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * @author Braydon
 */
@UtilityClass
public final class DiscordUtils {
    public static final long DISCORD_EPOCH = 1420070400000L;
    public static final long TIMESTAMP_OFFSET = 22;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{2,32}$");

    /**
     * Checks if the given username is a valid Discord username.
     *
     * @param username the username to check
     * @return whether the username is valid
     */
    public static boolean isValidUsername(@NonNull String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * Gets the unix creation-time of a Discord-entity by
     * doing the reverse snowflake algorithm on its id.
     *
     * @param entitySnowflake The id of the entity where the creation-time should be determined for
     * @return The creation time of the entity as unix time
     * @see <a href="https://github.com/discord-jda/JDA/blob/master/src/main/java/net/dv8tion/jda/api/utils/TimeUtil.java#L61">Credits</a>
     */
    public static long getTimeCreated(long entitySnowflake) {
        return ((entitySnowflake >>> TIMESTAMP_OFFSET) + DISCORD_EPOCH) / 1000L;
    }

    /**
     * Get the extension of the
     * media with the given id.
     *
     * @param mediaId the media id
     * @return the media extension
     */
    @NonNull
    public static String getMediaExtension(@NonNull String mediaId) {
        return mediaId.startsWith("a_") ? "gif" : "png";
    }
}