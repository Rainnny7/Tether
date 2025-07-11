"use client";

import Link from "next/link";
import { ReactElement, ReactNode } from "react";
import { HiHome } from "react-icons/hi2";
import {
    NavigationMenu,
    NavigationMenuContent,
    NavigationMenuItem,
    NavigationMenuLink,
    NavigationMenuList,
    NavigationMenuTrigger,
} from "~/components/ui/navigation-menu";
import Branding from "./branding";

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
    <div className="fixed inset-x-0 top-4 mx-auto p-2 w-fit flex justify-between gap-2 items-center">
        {/* Branding */}
        <Link href="/" draggable={false}>
            <Branding />
        </Link>

        {/* Links */}
        <NavigationMenu>
            <NavigationMenuList>
                {links.map((link: NavbarLink) =>
                    link.href ? (
                        <NavigationMenuItem key={link.label}>
                            <NavigationMenuLink asChild>
                                <Link href={link.href} draggable={false}>
                                    {link.label}
                                </Link>
                            </NavigationMenuLink>
                        </NavigationMenuItem>
                    ) : (
                        <NavigationMenuItem key={link.label}>
                            <NavigationMenuTrigger>
                                {link.label}
                            </NavigationMenuTrigger>
                            <NavigationMenuContent>
                                <NavigationMenuLink>
                                    {link.label}
                                </NavigationMenuLink>
                            </NavigationMenuContent>
                        </NavigationMenuItem>
                    )
                )}
            </NavigationMenuList>
        </NavigationMenu>
    </div>
);
export default Navbar;
