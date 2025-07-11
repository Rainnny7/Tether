import { createEnv } from "@t3-oss/env-core";
import { z } from "zod";

export const env = createEnv({
    server: {
        // App
        NODE_ENV: z.enum(["development", "production"]).default("development"),
    },

    client: {
        // App
        NEXT_PUBLIC_BASE_URL: z.string(),
    },

    runtimeEnv: {
        // App
        NODE_ENV: process.env.NODE_ENV,
        NEXT_PUBLIC_BASE_URL: process.env.NEXT_PUBLIC_BASE_URL,
    },

    /**
     * This is the prefix for the environment variables that are available on the client.
     */
    clientPrefix: "NEXT_PUBLIC_",

    /**
     * Makes it so that empty strings are treated as undefined.
     * `SOME_VAR: z.string()` and `SOME_VAR=''` will throw an error.
     */
    emptyStringAsUndefined: true,
});
