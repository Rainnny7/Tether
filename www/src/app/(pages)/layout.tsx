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
        <body className={`${ggSans.className} antialiased`}>
            <Navbar />
            <div className="px-5 mx-auto mt-[var(--navbar-offset)] max-w-[var(--max-page-width)] min-h-[calc(100vh-var(--navbar-offset))]">
                <TooltipProvider delayDuration={250}>
                    {children}
                </TooltipProvider>
            </div>
        </body>
    </html>
);
export default RootLayout;
