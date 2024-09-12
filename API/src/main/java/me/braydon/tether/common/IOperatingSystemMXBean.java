package me.braydon.tether.common;

/**
 * @author Braydon
 */
public interface IOperatingSystemMXBean {
    /**
     * Get the system's CPU usage for the entire system.
     * If the value is 0.0, all cores are idle. If the
     * value is 1.0, all cores were 100% utilized. If
     * the value is negative, the usage is not available.
     *
     * @return the system cpu load
     */
    double getSystemCpuLoad();
}