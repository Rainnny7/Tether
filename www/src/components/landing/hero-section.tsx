"use client";

import { motion } from "motion/react";
import Image from "next/image";
import Link from "next/link";
import { ReactElement } from "react";
import SlideUpAnimation from "../animation/slide-up-animation";
import { Button } from "../ui/button";
import SplitText from "../ui/split-text";

const HeroSection = (): ReactElement => (
    <div className="relative min-h-screen pb-5 flex flex-col justify-center items-center">
        {/* Top Text */}
        <motion.div
            className="relative"
            initial={{ scale: 0.8, opacity: 0 }}
            animate={{ scale: 1, opacity: 1 }}
            transition={{ delay: 0.4, duration: 0.5, ease: "easeOut" }}
        >
            {/* Wumpus ;p */}
            <motion.div
                className="absolute -top-27.5 right-20 z-10"
                initial={{ scale: 0.8, opacity: 0 }}
                animate={{ scale: 1, opacity: 1 }}
                transition={{ delay: 0.7, duration: 0.5, ease: "easeOut" }}
            >
                <SittingWumpus />
            </motion.div>

            {/* Text */}
            <SplitText
                className="max-w-screen-md text-6xl font-bold"
                text="An API designed to provide real-time access to data on Discord."
                splitType="words"
                delay={112}
                duration={1}
            />

            {/* Join Discord */}
            <motion.div
                className="absolute -bottom-12 -left-16 z-10"
                initial={{ scale: 0.8, opacity: 0 }}
                animate={{ scale: 1, opacity: 1 }}
                transition={{ delay: 1.1, duration: 0.5, ease: "easeOut" }}
            >
                <JoinDiscordButton />
            </motion.div>
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

const SittingWumpus = (): ReactElement => (
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
);

const JoinDiscordButton = (): ReactElement => (
    <motion.div
        animate={{ y: [5, -5, 5] }}
        transition={{
            duration: 3,
            repeat: Infinity,
            ease: "easeInOut",
        }}
    >
        <Link
            href="https://discord.usetether.rest"
            target="_blank"
            draggable={false}
        >
            <Button className="font-semibold">
                <Image
                    src="/media/discord-logo.png"
                    alt="Discord Logo"
                    width={16}
                    height={16}
                    unoptimized
                    draggable={false}
                />
                <span>Join our Discord</span>
            </Button>
        </Link>
    </motion.div>
);

export default HeroSection;
