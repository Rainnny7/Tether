package me.braydon.tether.service.websocket;

import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import me.braydon.tether.config.AppConfig;
import me.braydon.tether.exception.impl.BadRequestException;
import me.braydon.tether.exception.impl.ResourceNotFoundException;
import me.braydon.tether.exception.impl.ServiceUnavailableException;
import me.braydon.tether.model.user.DiscordUser;
import me.braydon.tether.packet.Packet;
import me.braydon.tether.packet.PacketRegistry;
import me.braydon.tether.packet.impl.websocket.misc.ErrorMessagePacket;
import me.braydon.tether.packet.impl.websocket.user.ListenToUserPacket;
import me.braydon.tether.packet.impl.websocket.user.UserStatusPacket;
import me.braydon.tether.service.DiscordService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Braydon
 */
@Log4j2(topic = "WebSocket Gateway") @Getter
public class WebSocket extends TextWebSocketHandler {
    /**
     * The discord service to use.
     */
    @NonNull private final DiscordService discordService;

    /**
     * Mapped clients for each connected session.
     */
    private final Map<String, WebSocketClient> activeSessions = Collections.synchronizedMap(new HashMap<>());

    /**
     * The unix time of when the last time stats were logged.
     */
    private long lastStat;

    protected WebSocket(@NonNull DiscordService discordService) {
        this.discordService = discordService;

        // Schedule a task to send statuses to listening clients
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override @SneakyThrows
            public void run() {
                if (!activeSessions.isEmpty() && (System.currentTimeMillis() - lastStat) >= TimeUnit.SECONDS.toMillis(30L)) {
                    lastStat = System.currentTimeMillis();
                    log.info("Active Sessions: {}", activeSessions.size());
                }
                for (WebSocketClient client : activeSessions.values()) {
                    String sessionId = client.getSession().getId();
                    System.out.println("client.getSession().getId() = " + sessionId);

                    // Disconnect users that have not been active for 30 seconds
                    if (client.getListeningTo() == null && ((System.currentTimeMillis() - client.getConnected()) >= TimeUnit.SECONDS.toMillis(30L))) {
                        log.info("Disconnecting session {} for being inactive", sessionId);
                        client.getSession().close(CloseStatus.NOT_ACCEPTABLE.withReason("Client is inactive"));
                        continue;
                    }
                    if (client.getListeningTo() == null) {
                        System.err.println("NOT LISTENING!!!");
                        continue;
                    }
                    // Notify the listening client of the user's status if it has changed
                    try {
                        DiscordUser user = discordService.getUserBySnowflake(client.getListeningTo()).getUser();
                        System.out.println("user = " + user);
                        if (!user.equals(client.getLastUser())) {
                            client.setLastUser(user);
                            dispatch(client.getSession(), new UserStatusPacket(user));
                        }
                    } catch (BadRequestException | ServiceUnavailableException | ResourceNotFoundException ex) {
                        System.err.println(ex.getLocalizedMessage());
                        System.err.println("STOPPED LISTENING TO USER!!!!!!!!!!!!!!");
                        client.setListeningTo(null);
                        dispatch(client.getSession(), new ErrorMessagePacket(ex.getLocalizedMessage()));
                    }
                }
            }
        }, 1000L, 1000L);
    }

    /**
     * Received a new session, store it.
     *
     * @param session the received session
     */
    @Override
    public final void afterConnectionEstablished(@NonNull WebSocketSession session) {
        String sessionId = session.getId();
        log.info("New session established: {}", sessionId);
        activeSessions.put(sessionId, new WebSocketClient(session));
    }

    /**
     * Handle receiving a string
     * message from a session.
     *
     * @param session the session
     * @param message the message
     */
    @Override @SneakyThrows
    protected final void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
        String sessionId = session.getId();
        WebSocketClient client = activeSessions.get(sessionId);
        if (client == null) { // No active client for the session
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("No active session"));
            return;
        }
        try {
            Packet received = AppConfig.GSON.fromJson(message.getPayload(), Packet.class); // Parse the received packet
            int opCode = received.getOpCode();
            Class<? extends Packet> packetClass = PacketRegistry.get(opCode);

            // Received packet is not valid, ignore it
            if (packetClass == null) {
                return;
            }
            Packet packet = AppConfig.GSON.fromJson(message.getPayload(), packetClass);
            log.info("Received packet (SID: {}, Op: {}): {}", sessionId, opCode, packetClass.getName());

            // Handle the packet
            if (packet instanceof ListenToUserPacket listenToUserPacket) {
                client.setListeningTo(listenToUserPacket.getSnowflake());
                log.info("Session {} is listening to user updates for {}", sessionId, client.getListeningTo());
            }
        } catch (JsonSyntaxException ex) { // The syntax provided is invalid, close the session
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Invalid payload"));
            log.warn("Rejected invalid payload: {}", sessionId);
        }
    }

    /**
     * A session has closed, remove it.
     *
     * @param session the closed session
     * @param status  the close status
     */
    @Override
    public final void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        String sessionId = session.getId();
        activeSessions.remove(sessionId);
        log.info("Session closed ({}): {}", status, sessionId);
    }

    /**
     * Send a packet to the given session.
     *
     * @param session the session to send to
     * @param packet  the packet to send
     */
    @SneakyThrows
    private void dispatch(@NonNull WebSocketSession session, @NonNull Packet packet) {
        session.sendMessage(new TextMessage(AppConfig.GSON.toJson(packet)));
    }
}