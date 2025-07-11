"use client";

import { motion } from "motion/react";
import { ReactNode } from "react";
import { cn } from "~/lib/utils";

type SlideUpAnimationProps = {
    className?: string | undefined;
    duration?: number;
    delay?: number;
    children: ReactNode;
};

const SlideUpAnimation = ({
    className,
    duration = 0.5,
    delay = 0.1,
    children,
}: SlideUpAnimationProps) => (
    <motion.div
        className={cn(className)}
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration, delay, ease: "easeOut" }}
        style={{ willChange: "transform, opacity" }}
    >
        {children}
    </motion.div>
);
export default SlideUpAnimation;
