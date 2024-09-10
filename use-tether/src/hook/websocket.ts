import { useEffect, useState } from "react";
import { Snowflake } from "@/types/snowflake";
import { defaultConfig, TetherConfig } from "@/types/config";
import { UserStatusPacket } from "@/types/socket";
import { DiscordUser } from "@/types/user/discord-user";

/**
 * Connect to the WebSocket and listen
 * for status updates for the user with
 * the given snowflake.
 *
 * @param snowflake the user's snowflake
 * @param config the optional Tether config
 */
export const useTetherWS = (
    snowflake: Snowflake,
    config?: TetherConfig | undefined
): DiscordUser | undefined => {
    const { endpoint, secure, debug } = { ...defaultConfig, ...config };
    const url: string = `ws${secure && "s"}://${endpoint}/gateway`;
    const [user, setUser] = useState<DiscordUser | undefined>();

    useEffect(() => {
        // Prevent from running on the server
        if (typeof window === "undefined") {
            return;
        }
        let socket: WebSocket; // The current WebSocket connection

        /**
         * Establish a connection with the API.
         */
        const connect = () => {
            console.log("[Tether] Connecting to the WebSocket server...");
            if (debug) {
                console.debug("[Tether] Endpoint:", url);
            }
            socket = new WebSocket(url); // Connect to the gateway

            // Track the user when the WS connects
            socket.addEventListener("open", () => {
                if (debug) {
                    console.debug("[Tether] Sending listen to user packet...");
                }
                socket.send(JSON.stringify({ op: 0, snowflake: snowflake }));
                console.log("[Tether] WebSocket connection established!");
            });
            socket.addEventListener("close", () => {
                if (debug) {
                    console.debug(
                        "[Tether] Connection to the WS server was lost, reconnecting..."
                    );
                }
                connect();
            }); // Reconnect on close

            socket.addEventListener("message", (event) => {
                const json = JSON.parse(event.data);
                if (debug) {
                    console.debug("[Tether] Received Packet:", json);
                }
                const statusPacket: UserStatusPacket = json as UserStatusPacket;
                if (statusPacket.op === 1) {
                    setUser(statusPacket.user);
                }
            });
        };
        connect();

        // Cleanup
        return () => {
            socket.removeEventListener("close", connect);
            socket.close();
        };
    }, [url]);

    return user;
};
