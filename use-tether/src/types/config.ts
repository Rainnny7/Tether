export const defaultConfig: TetherConfig = {
    endpoint: "api.usetether.rest",
    secure: true,
    debug: false,
};

export type TetherConfig = {
    /**
     * The API endpoint to connect to.
     */
    endpoint?: string;

    /**
     * Whether the connection should be secure.
     */
    secure?: boolean;

    /**
     * Whether to enable debugging.
     */
    debug?: boolean;
};
