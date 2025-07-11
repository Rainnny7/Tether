import { ReactElement } from "react";
import HeroSection from "~/components/landing/hero-section";

const LandingPage = (): ReactElement => (
    <main className="flex flex-col gap-10">
        <HeroSection />
    </main>
);
export default LandingPage;
