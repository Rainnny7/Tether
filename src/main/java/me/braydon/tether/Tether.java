package me.braydon.tether;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * @author Braydon
 */
@SpringBootApplication
@Log4j2(topic = "Tether")
public class Tether {
    @SneakyThrows
    public static void main(@NonNull String[] args) {
        // Load the application.yml configuration file
        File config = new File("application.yml");
        if (!config.exists()) { // Saving the default config if it doesn't exist locally
            Files.copy(Objects.requireNonNull(Tether.class.getResourceAsStream("/application.yml")), config.toPath(), StandardCopyOption.REPLACE_EXISTING);
            log.info("Saved the default configuration to '{}', please re-launch the application",
                    config.getAbsolutePath()
            );
            return;
        }
        log.info("Found configuration at '{}'", config.getAbsolutePath());

        // Start the app
        SpringApplication.run(Tether.class, args);
    }
}