"use client";

import { gsap } from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";
import { SplitText as GSAPSplitText } from "gsap/SplitText";
import { useEffect, useRef } from "react";

gsap.registerPlugin(ScrollTrigger, GSAPSplitText);

interface SplitTextProps {
    text: string;
    className?: string;
    delay?: number;
    duration?: number;
    ease?: string;
    splitType?: "chars" | "words" | "lines";
    from?: gsap.TweenVars;
    to?: gsap.TweenVars;
    threshold?: number;
    rootMargin?: string;
    textAlign?: "left" | "center" | "right" | "justify";
    onLetterAnimationComplete?: () => void;
}

const SplitText = ({
    text,
    className = "",
    delay = 100,
    duration = 0.6,
    ease = "power3.out",
    splitType = "chars",
    from = { opacity: 0, y: 40 },
    to = { opacity: 1, y: 0 },
    threshold = 0.1,
    rootMargin = "-100px",
    textAlign = "center",
    onLetterAnimationComplete,
}: SplitTextProps) => {
    const ref = useRef<HTMLParagraphElement>(null);
    const animationCompletedRef = useRef(false);
    const scrollTriggerRef = useRef<ScrollTrigger | null>(null);

    useEffect(() => {
        if (typeof window === "undefined" || !ref.current || !text) return;

        const el = ref.current;

        animationCompletedRef.current = false;

        const absoluteLines = splitType === "lines";
        if (absoluteLines) el.style.position = "relative";

        let splitter: GSAPSplitText;
        try {
            splitter = new GSAPSplitText(el, {
                type: splitType,
                absolute: absoluteLines,
                linesClass: "split-line",
            });
        } catch (error) {
            console.error("Failed to create SplitText:", error);
            return;
        }

        let targets: HTMLElement[];
        switch (splitType) {
            case "lines":
                targets = splitter.lines as HTMLElement[];
                break;
            case "words":
                targets = splitter.words as HTMLElement[];
                break;
            case "chars":
                targets = splitter.chars as HTMLElement[];
                break;
            default:
                targets = splitter.chars as HTMLElement[];
        }

        if (!targets || targets.length === 0) {
            console.warn("No targets found for SplitText animation");
            splitter.revert();
            return;
        }

        targets.forEach((t) => {
            t.style.willChange = "transform, opacity";
        });

        const startPct = (1 - threshold) * 100;
        const marginMatch = /^(-?\d+(?:\.\d+)?)(px|em|rem|%)?$/.exec(
            rootMargin
        );
        const marginValue = marginMatch ? parseFloat(marginMatch[1]) : 0;
        const marginUnit = marginMatch ? marginMatch[2] || "px" : "px";
        const sign =
            marginValue < 0
                ? `-=${Math.abs(marginValue)}${marginUnit}`
                : `+=${marginValue}${marginUnit}`;
        const start = `top ${startPct}%${sign}`;

        const tl = gsap.timeline({
            scrollTrigger: {
                trigger: el,
                start,
                toggleActions: "play none none none",
                once: true,
                onToggle: (self) => {
                    scrollTriggerRef.current = self;
                },
            },
            smoothChildTiming: true,
            onComplete: () => {
                animationCompletedRef.current = true;
                gsap.set(targets, {
                    ...to,
                    clearProps: "willChange",
                    immediateRender: true,
                });
                onLetterAnimationComplete?.();
            },
        });

        tl.set(targets, { ...from, immediateRender: false, force3D: true });
        tl.to(targets, {
            ...to,
            duration,
            ease,
            stagger: delay / 1000,
            force3D: true,
        });

        return () => {
            tl.kill();
            if (scrollTriggerRef.current) {
                scrollTriggerRef.current.kill();
                scrollTriggerRef.current = null;
            }
            gsap.killTweensOf(targets);
            if (splitter) {
                splitter.revert();
            }
        };
    }, [
        text,
        delay,
        duration,
        ease,
        splitType,
        from,
        to,
        threshold,
        rootMargin,
        onLetterAnimationComplete,
    ]);

    return (
        <p
            ref={ref}
            className={`split-parent overflow-hidden inline-block whitespace-normal ${className}`}
            style={{
                textAlign,
                wordWrap: "break-word",
            }}
        >
            {text}
        </p>
    );
};

export default SplitText;
