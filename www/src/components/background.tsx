"use client";

import { motion } from "motion/react";
import { ReactElement } from "react";

const Background = (): ReactElement => (
    <motion.div
        className="fixed inset-0 h-screen w-screen bg-neutral-950 bg-[radial-gradient(ellipse_80%_80%_at_50%_-20%,rgba(88,101,242,0.15),rgba(255,255,255,0))] z-[-2]"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.7, duration: 0.8, ease: "easeOut" }}
    />
);
export default Background;
