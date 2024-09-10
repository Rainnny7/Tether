import { useEffect, useState } from "react";
import { Snowflake } from "@/types/snowflake";
import { TetherConfig } from "@/types/config";
import { DiscordUser } from "@/types/user";
import { UserStatusPacket } from "@/types/socket";

export const useTetherWS = (
    snowflake: Snowflake,
    { endpoint, secure }: TetherConfig = {
        endpoint: "usetether.rest",
        secure: true,
    }
): DiscordUser | undefined => {
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
            socket = new WebSocket(url); // Connect to the gateway

            // Track the user when the WS connects
            socket.addEventListener("open", () => {
                socket.send(JSON.stringify({ op: 0, snowflake: snowflake }));
                console.log(
                    "[Tether] WebSocket connection established!",
                    snowflake
                );
            });
            socket.addEventListener("close", connect); // Reconnect on close

            socket.addEventListener("message", (event) => {
                const statusPacket: UserStatusPacket = JSON.parse(
                    event.data
                ) as UserStatusPacket;
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
