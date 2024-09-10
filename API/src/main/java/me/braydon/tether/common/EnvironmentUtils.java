package me.braydon.tether.common;

import lombok.Getter;
import lombok.experimental.UtilityClass;

/**
 * @author Braydon
 */
@UtilityClass
public final class EnvironmentUtils {
    /**
     * Is the app running in a production environment?
     */
    @Getter private static final boolean production;

    static {
        String appEnv = System.getenv("APP_ENV");
        production = appEnv != null && (appEnv.equals("production"));
    }
}