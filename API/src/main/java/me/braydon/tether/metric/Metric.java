package me.braydon.tether.metric;

import io.questdb.client.Sender;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.braydon.tether.common.EnvironmentUtils;

/**
 * @author Braydon
 */
@RequiredArgsConstructor @Setter @Getter
public abstract class Metric {
    /**
     * The interval (in millis) at which
     * this metric should be tracked.
     * <p>
     * If the interval is -1, it will not
     * be automatically tracked.
     * </p>
     */
    private final long interval;

    /**
     * The unix time of when this metric was last tracked.
     */
    private long lastTrack;

    @NonNull
    public static Sender applyEnvColumn(@NonNull Sender sender) {
        return sender.stringColumn("env", EnvironmentUtils.isProduction() ? "production" : "staging");
    }

    /**
     * Track this metric.
     *
     * @param sender the sender to use
     */
    public abstract void track(@NonNull Sender sender);
}