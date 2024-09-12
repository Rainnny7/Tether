package me.braydon.tether.metric.impl;

import io.questdb.client.Sender;
import lombok.NonNull;
import me.braydon.tether.metric.Metric;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Braydon
 */
public final class RequestsMetric extends Metric {
    private static final Map<Integer, Integer> codeCounts = new HashMap<>();

    public RequestsMetric() {
        super(TimeUnit.SECONDS.toMillis(10L));
    }

    /**
     * Increment the request
     * count for the given code.
     *
     * @param code the request code
     */
    public static void incrementCodeCount(int code) {
        codeCounts.merge(code, 1, Integer::sum);
    }

    /**
     * Track this metric.
     *
     * @param sender the sender to use
     */
    @Override
    public void track(@NonNull Sender sender) {
        if (codeCounts.isEmpty()) {
            return;
        }
        sender = applyEnvColumn(sender.table("requests"));
        for (Map.Entry<Integer, Integer> entry : codeCounts.entrySet()) {
            sender.longColumn(String.valueOf(entry.getKey()), entry.getValue());
        }
        sender.atNow();
        codeCounts.clear();
    }
}