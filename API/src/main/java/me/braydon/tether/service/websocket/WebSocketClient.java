package me.braydon.tether.service.websocket;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.braydon.tether.model.user.DiscordUser;
import org.springframework.web.socket.WebSocketSession;

/**
 * A client currently connected
 * to the {@link WebSocket}.
 *
 * @author Braydon
 */
@Setter @Getter
public class WebSocketClient {
    /**
     * The session this client is for.
     */
    @NonNull private final WebSocketSession session;
    /**
     * The unix time this client connected.
     */
    private final long connected;
    /**
     * The snowflake of the user this client
     * is listening to for updates, if any.
     */
    private Long listeningTo;
    /**
     * The last user this client
     * has been sent a status for.
     * <p>
     * This is kept so we only notify
     * the client if the user has changed.
     * </p>
     */
    private DiscordUser lastUser;

    protected WebSocketClient(@NonNull WebSocketSession session) {
        this.session = session;
        connected = System.currentTimeMillis();
    }
}