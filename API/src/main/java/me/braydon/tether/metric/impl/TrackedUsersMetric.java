package me.braydon.tether.metric.impl;

import io.questdb.client.Sender;
import lombok.NonNull;
import me.braydon.tether.metric.Metric;
import me.braydon.tether.service.DiscordService;

import java.util.concurrent.TimeUnit;

/**
 * @author Braydon
 */
public final class TrackedUsersMetric extends Metric {
    /**
     * The amount of recently watched users.
     */
    private static int recentlyWatchedUsers;

    /**
     * The Discord service to use.
     */
    @NonNull private final DiscordService discordService;

    public TrackedUsersMetric(@NonNull DiscordService discordService) {
        super(TimeUnit.SECONDS.toMillis(10L));
        this.discordService = discordService;
    }

    /**
     * Increment the amount of recently watched users.
     */
    public static void incrementRecentlyWatchedUsers() {
        recentlyWatchedUsers++;
    }

    /**
     * Track this metric.
     *
     * @param sender the sender to use
     */
    @Override
    public void track(@NonNull Sender sender) {
        applyEnvColumn(sender.table("tracked-users")
                .longColumn("current", discordService.getTrackedUsers())
                .longColumn("recent", recentlyWatchedUsers))
                .atNow();
        recentlyWatchedUsers = 0;
    }
}