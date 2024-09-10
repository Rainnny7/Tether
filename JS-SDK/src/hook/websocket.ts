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

    useEffect(() => {
        console.log("HELLO WORLD", endpoint, secure);
    }, [snowflake]);

    return user;
};
