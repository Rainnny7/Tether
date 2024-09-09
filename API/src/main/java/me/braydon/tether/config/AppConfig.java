package me.braydon.tether.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Fascinated (fascinated7)
 */
@Configuration
public class AppConfig {
    public static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .create();

    @Bean
    public WebMvcConfigurer configureCors() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                // Allow all origins to access the API
                registry.addMapping("/v*/**")
                        .allowedOrigins("*") // Allow all origins
                        .allowedMethods("*") // Allow all methods
                        .allowedHeaders("*"); // Allow all headers
            }
        };
    }
}
