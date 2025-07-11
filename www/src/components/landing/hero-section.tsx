"use client";

import { motion } from "motion/react";
import Image from "next/image";
import Link from "next/link";
import { ReactElement } from "react";
import SlideUpAnimation from "~/components/animation/slide-up-animation";
import Branding from "~/components/branding";
import SplitText from "~/components/ui/split-text";

type QuickLink = {
    label: string;
    href: string;
};

const quickLinks: QuickLink[] = [
    {
        label: "Discord",
        href: "https://discord.usetether.rest",
    },
    {
        label: "API Docs",
        href: "https://docs.usetether.rest",
    },
    {
        label: "Source Code",
        href: "https://github.com/Rainnny7/Tether",
    },
];

const HeroSection = (): ReactElement => (
    <div className="relative min-h-screen">
        <Greeting />
        <QuickLinks />
        <Copyright />
    </div>
);

const Greeting = (): ReactElement => (
    <div className="mt-48 flex flex-col gap-2 justify-center items-center">
        {/* Logo */}
        <motion.div
            className="relative mb-32"
            initial={{ scale: 0.8, opacity: 0 }}
            animate={{ scale: 1, opacity: 1 }}
            transition={{ delay: 0.3, duration: 0.5, ease: "easeOut" }}
        >
            <div className="absolute -inset-x-20 -inset-y-26 size-72 bg-radial-[at_center] from-white/8 via-transparent to-transparent blur-sm rounded-full animate-pulse delay-1000 transition-all transform-gpu -z-10" />

            <Branding width={128} height={50} />
        </motion.div>

        {/* Top Text */}
        <motion.div
            className="relative"
            initial={{ scale: 0.8, opacity: 0 }}
            animate={{ scale: 1, opacity: 1 }}
            transition={{ delay: 0.4, duration: 0.5, ease: "easeOut" }}
        >
            {/* Wumpus ;p */}
            <motion.div
                className="absolute -top-27 right-35 z-10"
                initial={{ scale: 0.8, opacity: 0 }}
                animate={{ scale: 1, opacity: 1 }}
                transition={{ delay: 0.7, duration: 0.5, ease: "easeOut" }}
            >
                <motion.div
                    animate={{ y: [-5, 5, -5] }}
                    transition={{
                        duration: 3,
                        repeat: Infinity,
                        ease: "easeInOut",
                    }}
                >
                    <Image
                        src="/media/wumpus/leaf.webp"
                        alt="Wumpus' Leaf"
                        width={128}
                        height={128}
                        unoptimized
                        draggable={false}
                    />
                    <Image
                        src="/media/wumpus/sitting.webp"
                        alt="Wumpus Sitting"
                        width={128}
                        height={128}
                        unoptimized
                        draggable={false}
                    />
                </motion.div>
            </motion.div>
            <SplitText
                className="max-w-screen-md text-6xl font-bold"
                text="Nostrud voluptate ea anim aliqua exercitation velit exercitation velit."
                splitType="words"
                delay={112}
                duration={1}
            />
        </motion.div>

        {/* Bottom Text */}
        <SlideUpAnimation
            className="max-w-lg text-xl text-muted-foreground font-medium text-center"
            delay={1.15}
        >
            Officia in qui reprehenderit ipsum. Dolore sint id mollit occaecat
            est et laborum ullamco ad aute.
        </SlideUpAnimation>
    </div>
);

const QuickLinks = (): ReactElement => (
    <motion.div
        className="absolute bottom-7 left-0 flex flex-col items-start text-left text-sm font-semibold text-muted-foreground uppercase"
        initial={{ scale: 0.8, opacity: 0 }}
        animate={{ scale: 1, opacity: 1 }}
        transition={{ delay: 0.55, duration: 0.5, ease: "easeOut" }}
    >
        {quickLinks.map((link) => (
            <Link
                key={link.label}
                className="hover:translate-x-1 hover:text-white/60 transition-all duration-300 transform-gpu"
                href={link.href}
                target="_blank"
                draggable={false}
            >
                {link.label}
            </Link>
        ))}
    </motion.div>
);

const Copyright = (): ReactElement => (
    <motion.div
        className="w-32 absolute bottom-7 right-0 flex flex-col items-end text-right text-sm font-semibold text-muted-foreground uppercase"
        initial={{ scale: 0.8, opacity: 0 }}
        animate={{ scale: 1, opacity: 1 }}
        transition={{ delay: 0.55, duration: 0.5, ease: "easeOut" }}
    >
        <span>&copy; Copyright</span>
        <span className="flex gap-1 items-center">
            <Link
                className="flex gap-1 items-center hover:text-white/60 transition-colors duration-300 transform-gpu"
                href="https://github.com/Rainnny7"
                target="_blank"
                draggable={false}
            >
                <Image
                    className="rounded-full"
                    src="https://avatars.githubusercontent.com/u/32585528"
                    alt="GitHub Avatar"
                    width={16}
                    height={16}
                    unoptimized
                    draggable={false}
                />
                <span>Braydon</span>
            </Link>{" "}
            {new Date().getFullYear()}
        </span>
    </motion.div>
);

export default HeroSection;
