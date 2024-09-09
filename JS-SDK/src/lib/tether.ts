import TetherConfig from "@/types/config";
import DiscordUser from "@/types/user";
import { useEffect, useState } from "react";
import Snowflake from "@/types/snowflake";

export const useTetherWS = (
    snowflake: Snowflake,
    { endpoint, secure }: TetherConfig = {
        endpoint: "usetether.rest",
        secure: true,
    }
): DiscordUser | undefined => {
    const [user] = useState<DiscordUser | undefined>();

    const url: string = `ws${secure && "s"}://${endpoint}/gateway`;
    useEffect(() => {
        let socket: WebSocket; // The current WebSocket connection

        /**
         * Establish a connection with the API.
         */
        function connect() {
            socket = new WebSocket(url); // Connect to the gateway
            socket.addEventListener("open", () =>
                console.log("[Tether] WebSocket connection established!")
            );
            socket.addEventListener("close", connect);

            socket.addEventListener("message", (event) => {
                console.log("data:", event.data);
            });
        }
        connect();

        // Cleanup
        return () => {
            socket.removeEventListener("close", connect);
            socket.close();
        };
    }, [url]);

    return user;
};
