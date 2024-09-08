package me.braydon.tether.controller;

import lombok.NonNull;
import me.braydon.tether.common.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * The root controller for this app.
 *
 * @author Braydon
 */
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public final class AppController {
    /**
     * The build properties for this app, null if not available.
     */
    private final BuildProperties buildProperties;

    @Autowired
    public AppController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    /**
     * A GET endpoint to get info about this app.
     *
     * @return the info response
     */
    @GetMapping @ResponseBody @NonNull
    public ResponseEntity<Map<String, String>> getAppInfo() {
        return ResponseEntity.ok(Map.of(
                "app", buildProperties == null ? "N/A" : buildProperties.getName(),
                "version", buildProperties == null ? "N/A" : buildProperties.getVersion(),
                "environment", EnvironmentUtils.isProduction() ? "production" : "staging"
        ));
    }
}