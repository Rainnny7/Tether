package me.braydon.tether.metric.impl;

import io.questdb.client.Sender;
import lombok.NonNull;
import me.braydon.tether.metric.Metric;

/**
 * @author Braydon
 */
public final class UserLookupTimingsMetric extends Metric {
    private static Sender sender;

    public UserLookupTimingsMetric(@NonNull Sender sender) {
        super(-1);
        UserLookupTimingsMetric.sender = sender;
    }

    /**
     * Track this metric.
     *
     * @param timings the timings
     */
    public static void track(long timings) {
        applyEnvColumn(sender.table("timings")
                .stringColumn("type", "user-lookup")
                .longColumn("value", timings))
                .atNow();
    }

    /**
     * Track this metric.
     *
     * @param sender the sender to use
     */
    @Override
    public void track(@NonNull Sender sender) {}
}