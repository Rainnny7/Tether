import { ReactElement } from "react";
import Image from "next/image";
import { cn } from "../lib/utils";

type BrandingProps = {
    className?: string;
    withText?: boolean;
};

const Branding = ({
    className,
    withText = true,
}: BrandingProps): ReactElement => (
    <div className={cn("flex gap-2 items-center", className)}>
        <Image
            src="/media/logo.png"
            alt="Tether Logo"
            width={32}
            height={32}
            draggable={false}
        />
        {withText && <span>Tether</span>}
    </div>
);
export default Branding;
