import { useEffect, useState } from "react";
import { Snowflake } from "@/types/snowflake";
import { TetherConfig } from "@/types/config";

export const useTetherWS = (
    snowflake: Snowflake,
    { endpoint, secure }: TetherConfig = {
        endpoint: "usetether.rest",
        secure: true,
    }
): Snowflake => {
    const [user] = useState<Snowflake>(snowflake);

    const url: string = `ws${secure && "s"}://${endpoint}/gateway`;
    useEffect(() => {
        // Prevent from running on the server
        if (typeof window === "undefined") {
            return;
        }
        let socket: WebSocket; // The current WebSocket connection

        /**
         * Establish a connection with the API.
         */
        function connect() {
            console.log("connecting to:", url);
            socket = new WebSocket("wss://usetether.rest/gateway"); // Connect to the gateway
            socket.addEventListener("open", () =>
                console.log("[Tether] WebSocket connection established!")
            );
            // socket.addEventListener("close", connect);
            //
            // socket.addEventListener("message", (event) => {
            //     console.log("data:", event.data);
            // });
        }
        connect();

        // Cleanup
        return () => {
            // socket.removeEventListener("close", connect);
            socket.close();
        };
    }, [url]);

    return user;
};
export default useTetherWS;
