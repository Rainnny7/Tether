import { defaultConfig, TetherConfig } from "@/types/config";

import { Snowflake } from "@/types/snowflake";
import { DiscordUser } from "@/types/user/discord-user";
import { useState } from "react";
import { useEffect } from "react";

/**
 * Get the Discord user with the given snowflake.
 *
 * @param snowflake the user's snowflake
 * @param config the optional Tether config
 * @returns the Discord user, undefined if not found
 */
export const useTether = (
    snowflake: Snowflake,
    config?: TetherConfig | undefined
): DiscordUser | undefined => {
    const { endpoint, secure, debug } = { ...defaultConfig, ...config };
    const url: string = `https${
        secure ? "s" : ""
    }://${endpoint}/user/${snowflake}`;

    const [user, setUser] = useState<DiscordUser | undefined>();
    useEffect(() => {
        fetch(url)
            .then((response: Response) => response.json())
            .then((data: DiscordUser) => setUser(data));
    }, [url]);
    return user;
};
