import localFont from "next/font/local";

export const ggSans = localFont({
    src: [
        {
            path: "../app/fonts/gg-sans-Regular.ttf",
            weight: "400",
            style: "normal",
        },
        {
            path: "../app/fonts/gg-sans-Medium.ttf",
            weight: "500",
            style: "normal",
        },
        {
            path: "../app/fonts/gg-sans-Semibold.ttf",
            weight: "600",
            style: "normal",
        },
        {
            path: "../app/fonts/gg-sans-Bold.ttf",
            weight: "700",
            style: "normal",
        },
    ],
    variable: "--font-gg-sans",
    display: "swap",
});
