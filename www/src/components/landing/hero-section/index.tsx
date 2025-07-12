"use client";

import { motion } from "motion/react";
import Image from "next/image";
import Link from "next/link";
import { ReactElement, ReactNode } from "react";
import { HiOutlineCodeBracket } from "react-icons/hi2";
import Greeting from "./greeting";

type QuickLink = {
    label: string;
    logo: string | ReactNode;
    description: string;
    href: string;
};

const quickLinks: QuickLink[] = [
    {
        label: "Discord",
        logo: "/media/discord-logo.png",
        description: "Join our Discord server",
        href: "https://discord.usetether.rest",
    },
    {
        label: "API Docs",
        logo: <HiOutlineCodeBracket className="size-3.5" />,
        description: "View the API documentation",
        href: "https://docs.usetether.rest",
    },
    {
        label: "Source Code",
        logo: "/media/github-logo.png",
        description: "View the source on GitHub",
        href: "https://github.com/Rainnny7/Tether",
    },
];

const HeroSection = (): ReactElement => (
    <div className="relative min-h-screen pb-5 flex flex-col justify-end">
        <Greeting />

        <motion.div
            className="mt-auto pt-16 flex justify-between gap-3 items-center"
            initial={{ y: 20, opacity: 0 }}
            animate={{ y: 0, opacity: 1 }}
            transition={{ delay: 1.7, duration: 0.5, ease: "easeOut" }}
        >
            <QuickLinks />
            <Copyright />
        </motion.div>
    </div>
);

const QuickLinks = (): ReactElement => (
    <div className="flex flex-col gap-0.25 items-start text-left text-xs text-muted-foreground">
        {quickLinks.map((link: QuickLink) => (
            <Link
                key={link.label}
                className="group flex gap-1 items-center hover:translate-x-1 cursor-default hover:text-white/60 transition-all duration-300 transform-gpu"
                href={link.href}
                target="_blank"
                draggable={false}
            >
                {/* Logo */}
                <div className="mr-1">
                    {typeof link.logo === "string" ? (
                        <Image
                            src={link.logo}
                            alt={`${link.label} Logo`}
                            width={14}
                            height={14}
                            unoptimized
                            draggable={false}
                        />
                    ) : (
                        link.logo
                    )}
                </div>

                <span className="font-semibold uppercase">{link.label}</span>
                <span className="opacity-0 group-hover:opacity-100 transition-opacity duration-300 transform-gpu">
                    — {link.description}
                </span>
            </Link>
        ))}
    </div>
);

const Copyright = (): ReactElement => (
    <div className="w-32 mt-auto flex flex-col items-end text-right text-xs font-semibold text-muted-foreground uppercase">
        <span>&copy; Copyright</span>
        <span className="flex gap-1 items-center">
            <Link
                className="flex gap-1 items-center cursor-default hover:text-white/60 transition-colors duration-300 transform-gpu"
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
    </div>
);

export default HeroSection;
