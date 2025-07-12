import type { Metadata, Viewport } from "next";
import Background from "~/components/background";
import Footer from "~/components/footer";
import Navbar from "~/components/navbar";
import { TooltipProvider } from "~/components/ui/tooltip";
import { env } from "~/lib/env";
import { ggSans } from "~/lib/font";
import "./styles/globals.css";

/**
 * The metadata for this app.
 */
export const metadata: Metadata = {
    title: {
        default: "Tether - Discord User & Guild Lookup",
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
        <body
            className={`relative ${ggSans.className} antialiased select-none`}
        >
            <TooltipProvider delayDuration={250}>
                {/* Background */}
                <Background />

                <Navbar />
                <div className="relative px-5 mx-auto max-w-[var(--max-page-width)]">
                    {children}
                </div>
                <Footer />
            </TooltipProvider>
        </body>
    </html>
);
export default RootLayout;
