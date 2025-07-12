import { motion } from "motion/react";
import Image from "next/image";
import Link from "next/link";
import { ReactElement } from "react";
import SlideUpAnimation from "~/components/animation/slide-up-animation";
import Branding from "~/components/branding";
import { Button } from "~/components/ui/button";
import SplitText from "~/components/ui/split-text";

const Greeting = (): ReactElement => (
    <div className="mt-[17vh] flex flex-col gap-2 justify-center items-center">
        {/* Logo */}
        <motion.div
            className="relative mb-32 flex gap-7 items-center"
            initial={{ scale: 0.8, opacity: 0 }}
            animate={{ scale: 1, opacity: 1 }}
            transition={{ delay: 0.3, duration: 0.5, ease: "easeOut" }}
        >
            <div className="absolute -inset-x-20 -inset-y-52 size-[28rem] bg-radial-[at_center] from-white/5 via-transparent to-transparent blur-sm rounded-full -z-10" />

            <Branding width={86} height={50} />
            <Image
                src="/media/logo-text.png"
                alt="Tether Logo"
                width={164}
                height={24}
                unoptimized
                draggable={false}
            />
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
                className="absolute -top-27.5 right-35 z-10"
                initial={{ scale: 0.8, opacity: 0 }}
                animate={{ scale: 1, opacity: 1 }}
                transition={{ delay: 0.7, duration: 0.5, ease: "easeOut" }}
            >
                <SittingWumpus />
            </motion.div>

            {/* Text */}
            <SplitText
                className="max-w-screen-md text-6xl font-bold"
                text="Nostrud voluptate ea anim aliqua exercitation velit exercitation velit."
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

export default Greeting;
