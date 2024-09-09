import TetherConfig from "@/types/config";
import DiscordUser from "@/types/user";
import {useEffect, useState} from "react";

export const useTether = (
    snowflake: bigint | string,
    {endpoint, secure, realtime}: TetherConfig = {
        endpoint: "usetether.rest",
        secure: true,
        realtime: false
    }): DiscordUser | undefined => {
    const [user] = useState<DiscordUser | undefined>();

    useEffect(() => {
        let socket: WebSocket; // The current WebSocket connection

        /**
         * Establish a connection with the API.
         */
        const connect = () => {
            socket = new WebSocket(`ws${secure && "s"}://${endpoint}/gateway`); // Connect to the gateway
            socket.addEventListener("open", () => console.log("[Tether] WebSocket connection established!"));
            socket.addEventListener("close", connect);

            socket.addEventListener("message", event => {
                console.log("data:", event.data);
            });
        };
        connect();

        // Cleanup
        return () => {
            socket.removeEventListener("close", connect);
            socket.close();
        };
    }, [endpoint, secure]);

    return user;
}