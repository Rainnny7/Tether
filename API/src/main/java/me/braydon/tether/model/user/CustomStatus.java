package me.braydon.tether.model.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;

import java.util.List;

/**
 * The custom status of a {@link DiscordUser}.
 *
 * @author Braydon
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Getter
public class CustomStatus {
    /**
     * The value of this status.
     */
    @NonNull private final String value;

    /**
     * The unicode emoji for this status.
     */
    private final String emoji;

    /**
     * Construct a custom status for a user.
     *
     * @param activities the user's activities
     * @return the constructed status, null if none
     */
    protected static CustomStatus fromActivities(@NonNull List<Activity> activities) {
        for (Activity activity : activities) {
            if (activity.getType() != Activity.ActivityType.CUSTOM_STATUS) {
                continue;
            }
            EmojiUnion emoji = activity.getEmoji();
            return new CustomStatus(activity.getName(), emoji == null ? null : emoji.asUnicode().getFormatted());
        }
        return null;
    }
}