package me.braydon.tether.metric.impl;

import io.questdb.client.Sender;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import me.braydon.tether.common.IOperatingSystemMXBean;
import me.braydon.tether.metric.Metric;

import javax.management.JMX;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

/**
 * This metric is responsible for tracking
 * the statistics of this application.
 *
 * @author Braydon
 */
@Log4j2(topic = "App Statistics Collector")
public final class AppStatisticsMetric extends Metric {
    /**
     * The JMX to use for getting the system's CPU load, if available.
     */
    private IOperatingSystemMXBean jmx;

    public AppStatisticsMetric() {
        super(TimeUnit.SECONDS.toMillis(10L));
        try {
            jmx = JMX.newMXBeanProxy(
                    ManagementFactory.getPlatformMBeanServer(),
                    ObjectName.getInstance("java.lang:type=OperatingSystem"),
                    IOperatingSystemMXBean.class
            );
        } catch (Exception ex) {
            log.error("OperatingSystemMXBean is not supported by the system, the system CPU usage won't be collected", ex);
        }
    }

    /**
     * Track this metric.
     *
     * @param sender the sender to use
     */
    @Override
    public void track(@NonNull Sender sender) {
        Runtime runtime = Runtime.getRuntime();
        applyEnvColumn(sender.table("app-statistics")
                .doubleColumn("systemCpu", jmx.getSystemCpuLoad() * 100D)
                .longColumn("usedMemory", (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024))
                .longColumn("freeMemory", runtime.freeMemory() / (1024 * 1024))
                .longColumn("totalMemory", runtime.maxMemory() / (1024 * 1024))
                .longColumn("uptime", ManagementFactory.getRuntimeMXBean().getUptime()))
                .atNow();
    }
}