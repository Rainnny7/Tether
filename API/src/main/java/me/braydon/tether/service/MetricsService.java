package me.braydon.tether.service;

import io.questdb.client.Sender;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import me.braydon.tether.metric.Metric;
import me.braydon.tether.metric.impl.AppStatisticsMetric;
import me.braydon.tether.metric.impl.RequestsMetric;
import me.braydon.tether.metric.impl.TrackedUsersMetric;
import me.braydon.tether.metric.impl.UserLookupTimingsMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Braydon
 */
@Service @Log4j2(topic = "Metrics")
public final class MetricsService {
    /**
     * The Discord service some metrics use.
     */
    @NonNull private final DiscordService discordService;

    /**
     * The metrics to automatically track.
     */
    @NonNull private final List<Metric> metrics = new LinkedList<>();

    /**
     * Are metrics enabled?
     */
    @Value("${questdb.enabled}")
    @Getter private boolean enabled;

    /**
     * The URI to the metrics server.
     */
    @Value("${questdb.uri}")
    private String dbUrl;

    /**
     * The sender client.
     */
    private Sender sender;

    @Autowired
    public MetricsService(@NonNull DiscordService discordService) {
        this.discordService = discordService;
    }

    @PostConstruct
    public void onInitialize() {
        if (!enabled) {
            return;
        }
        // Initialize the sender, and schedule a task to start tracking
        sender = Sender.fromConfig(dbUrl);
        log.info("Sender Configured!");

        // Register metrics
        metrics.add(new AppStatisticsMetric());
        metrics.add(new TrackedUsersMetric(discordService));
        metrics.add(new RequestsMetric());
        new UserLookupTimingsMetric(sender);
        log.info("Tracking {} metrics...", metrics.size());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                trackMetrics();
            }
        }, 1000L, 1000L);
    }

    /**
     * Track the registered metrics.
     */
    private void trackMetrics() {
        int points = 0;
        for (Metric metric : metrics) {
            long now = System.currentTimeMillis();
            if (metric.getInterval() == -1L || (now - metric.getLastTrack()) < metric.getInterval()) {
                continue;
            }
            metric.setLastTrack(now);
            metric.track(sender);
            points++;
        }
        // Record the amount of points recorded a second
        Metric.applyEnvColumn(sender.table("data-points")
                        .longColumn("value", points))
                .atNow();
    }
}