type TetherConfig = {
    /**
     * The API endpoint to connect to.
     */
    endpoint?: string,

    /**
     * Whether the connection should be secure.
     */
    secure?: boolean,

    /**
     * Whether the data should be fetched in real-time.
     */
    realtime?: boolean,
};
export default TetherConfig;