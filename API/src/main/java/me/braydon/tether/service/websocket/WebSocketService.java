package me.braydon.tether.service.websocket;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import me.braydon.tether.service.DiscordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Braydon
 */
@Service @EnableWebSocket @Log4j2(topic = "WebSockets")
public class WebSocketService implements WebSocketConfigurer {
    private static final String WS_PATH = "/gateway";

    /**
     * The WebSocket to use.
     */
    @NonNull private final WebSocket webSocket;

    @Autowired
    public WebSocketService(@NonNull DiscordService discordService) {
        webSocket = new WebSocket(discordService);
    }

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocket, WS_PATH).setAllowedOrigins("*");
        log.info("Added WebSocket on path {}", WS_PATH);
    }
}