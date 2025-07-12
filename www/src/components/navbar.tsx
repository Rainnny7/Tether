"use client";

import { motion } from "motion/react";
import Image from "next/image";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { ReactElement, ReactNode, useState } from "react";
import {
    HiOutlineHome,
    HiOutlineMagnifyingGlass,
    HiOutlineStar,
} from "react-icons/hi2";
import SimpleTooltip from "~/components/simple-tooltip";
import {
    NavigationMenu,
    NavigationMenuItem,
    NavigationMenuLink,
    NavigationMenuList,
} from "~/components/ui/navigation-menu";
import { cn } from "~/lib/utils";

type NavbarLink = {
    label: string;
    icon: ReactNode;
    tooltip: string;
    href: string;
};

const links: NavbarLink[] = [
    {
        label: "Home",
        icon: <HiOutlineHome />,
        tooltip: "Click to go home",
        href: "/",
    },
    {
        label: "Lookup",
        icon: <HiOutlineMagnifyingGlass />,
        tooltip: "Lookup a user or guild!",
        href: "/#lookup",
    },
    // {
    //     label: "Tools",
    //     icon: <HiOutlineCodeBracket />,
    //     tooltip: "View some other cool tools!",
    //     href: "/#tools",
    // },
];

const Navbar = (): ReactElement => (
    <div className="fixed inset-x-0 top-3.5 mx-auto px-5 py-2 max-w-[var(--max-page-width)] flex justify-between gap-2 items-center z-50">
        {/* Left Home Button */}
        <motion.div
            initial={{ x: -50, opacity: 0 }}
            animate={{ x: 0, opacity: 1 }}
            transition={{ delay: 0.1, duration: 0.5, ease: "easeOut" }}
        >
            <HomeButton />
        </motion.div>

        {/* Right Links */}
        <motion.div
            initial={{ x: 50, opacity: 0 }}
            animate={{ x: 0, opacity: 1 }}
            transition={{ delay: 0.1, duration: 0.5, ease: "easeOut" }}
        >
            <Links />
        </motion.div>
    </div>
);

const HomeButton = (): ReactElement => {
    const [isHovered, setIsHovered] = useState<boolean>(false);

    return (
        <Link
            className="flex items-center gap-3 cursor-default hover:opacity-75 transition-opacity duration-300 transform-gpu"
            href="/"
            draggable={false}
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            <div className="pr-10 relative flex flex-col">
                {/* Logo Text */}
                <motion.div
                    animate={{ y: isHovered ? -7 : 0 }}
                    transition={{ duration: 0.2, ease: "easeOut" }}
                >
                    <Image
                        src="/media/logo-text.png"
                        alt="Tether Logo"
                        width={90}
                        height={16}
                        unoptimized
                        draggable={false}
                    />
                </motion.div>
                <motion.span
                    className="absolute left-0 -bottom-3 text-xs text-muted-foreground"
                    initial={{ opacity: 0 }}
                    animate={{ opacity: isHovered ? 1 : 0 }}
                    transition={{ duration: 0.5, ease: "easeOut" }}
                >
                    Click to go home
                </motion.span>
            </div>
        </Link>
    );
};

const Links = (): ReactElement => {
    const path: string = usePathname();
    return (
        <motion.div
            className="overflow-hidden"
            initial={{ width: 0, opacity: 0 }}
            animate={{ width: "auto", opacity: 1 }}
            transition={{
                duration: 0.525,
                ease: [0.25, 0.46, 0.45, 0.94],
            }}
        >
            <NavigationMenu className="p-1 bg-secondary/55 backdrop-blur-md border border-border rounded-md whitespace-nowrap">
                <NavigationMenuList className="gap-1">
                    {links.map((link: NavbarLink, index: number) => {
                        const isActive: boolean = path === link.href;
                        return (
                            <NavigationMenuItem key={index}>
                                <SimpleTooltip
                                    content={link.tooltip}
                                    side="bottom"
                                >
                                    <NavigationMenuLink asChild>
                                        <Link
                                            className={cn(
                                                "group/link flex-row gap-2 items-center font-semibold",
                                                isActive &&
                                                    "bg-muted-foreground/15 hover:!bg-muted-foreground/25 focus:!bg-muted-foreground/25"
                                            )}
                                            href={link.href}
                                            draggable={false}
                                        >
                                            <span
                                                className={cn(
                                                    "*:stroke-muted-foreground *:transition-colors *:duration-300 *:transform-gpu",
                                                    isActive
                                                        ? "*:stroke-primary"
                                                        : "*:group-hover/link:stroke-white *:group-focus/link:stroke-white"
                                                )}
                                            >
                                                {isActive ? (
                                                    <HiOutlineStar />
                                                ) : (
                                                    link.icon
                                                )}
                                            </span>
                                            <span>{link.label}</span>
                                        </Link>
                                    </NavigationMenuLink>
                                </SimpleTooltip>
                            </NavigationMenuItem>
                        );
                    })}
                </NavigationMenuList>
            </NavigationMenu>
        </motion.div>
    );
};

export default Navbar;
