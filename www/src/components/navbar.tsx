"use client";

import { motion } from "motion/react";
import Image from "next/image";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { ReactElement, ReactNode, useState } from "react";
import { HiHome } from "react-icons/hi2";
import {
    NavigationMenu,
    NavigationMenuContent,
    NavigationMenuItem,
    NavigationMenuLink,
    NavigationMenuList,
    NavigationMenuTrigger,
} from "~/components/ui/navigation-menu";

type NavbarLink = {
    label: string;
    icon: ReactNode;
    href?: string;
    content?: ReactNode;
};

const links: NavbarLink[] = [
    {
        label: "Home",
        icon: <HiHome />,
        href: "/",
    },
    {
        label: "Tools",
        icon: <HiHome />,
        content: <div>hello</div>,
    },
];

const Navbar = (): ReactElement => (
    <div className="fixed inset-x-0 top-5 mx-auto px-5 py-2 max-w-[var(--max-page-width)] flex justify-between gap-2 items-center">
        {/* Left Home Button */}
        <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ delay: 0.1, duration: 0.5, ease: "easeOut" }}
        >
            <HomeButton />
        </motion.div>

        {/* Center Links */}
        <CenterLinks />

        {/* Right - Other Links */}
        <div className="flex gap-2 items-center">sdfsd</div>
    </div>
);

const HomeButton = (): ReactElement => {
    const [isHovered, setIsHovered] = useState<boolean>(false);

    return (
        <Link
            className="flex items-center gap-3 hover:opacity-75 transition-opacity duration-300 transform-gpu"
            href="/"
            draggable={false}
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            {/* Logo */}
            <Image
                src="/media/logo.png"
                alt="Tether Logo"
                width={36}
                height={20}
                draggable={false}
            />

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

const CenterLinks = (): ReactElement => {
    const path: string = usePathname();
    return (
        <NavigationMenu className="absolute left-1/2 -translate-x-1/2 px-1 py-0.5 bg-secondary/65 backdrop-blur-md border border-border rounded-lg">
            <NavigationMenuList className="gap-1">
                {links.map((link: NavbarLink) => {
                    const isActive: boolean = path === link.href;
                    return (
                        <NavigationMenuItem key={link.label}>
                            {link.href ? (
                                <NavigationMenuLink asChild>
                                    <Link
                                        className="group/link flex-row gap-2 items-center font-semibold"
                                        href={link.href}
                                        draggable={false}
                                    >
                                        {isActive && (
                                            <div className="p-1 bg-primary group-hover/link:bg-white rounded-full transition-colors duration-300 transform-gpu" />
                                        )}
                                        <span>{link.label}</span>
                                    </Link>
                                </NavigationMenuLink>
                            ) : (
                                <>
                                    <NavigationMenuTrigger className="font-semibold">
                                        {link.label}
                                    </NavigationMenuTrigger>
                                    <NavigationMenuContent>
                                        <NavigationMenuLink>
                                            {link.label}
                                        </NavigationMenuLink>
                                    </NavigationMenuContent>
                                </>
                            )}
                        </NavigationMenuItem>
                    );
                })}
            </NavigationMenuList>
        </NavigationMenu>
    );
};

export default Navbar;
