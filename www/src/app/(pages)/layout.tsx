import type { Metadata, Viewport } from "next";
import { TooltipProvider } from "~/components/ui/tooltip";
import { ggSans } from "~/lib/font";
import Navbar from "../../components/navbar";
import { env } from "../../lib/env";
import "../styles/globals.css";

/**
 * The metadata for this app.
 */
export const metadata: Metadata = {
    title: {
        default: "Tether",
        template: "%s • Tether",
    },
    description:
        "An API designed to provide real-time access to data on Discord.",
    openGraph: {
        images: [
            {
                url: `${env.NEXT_PUBLIC_BASE_URL}/media/logo.png`,
                width: 128,
                height: 128,
            },
        ],
    },
    twitter: {
        card: "summary",
    },
};
export const viewport: Viewport = {
    themeColor: "#5865F2",
};

const RootLayout = ({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) => (
    <html lang="en">
        <body className={`${ggSans.className} antialiased select-none`}>
            <TooltipProvider delayDuration={250}>
                <Navbar />
                <div className="px-5 mx-auto max-w-[var(--max-page-width)]">
                    {children}
                </div>
            </TooltipProvider>
        </body>
    </html>
);
export default RootLayout;
