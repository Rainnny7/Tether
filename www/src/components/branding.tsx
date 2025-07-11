import Image from "next/image";

type BrandingProps = {
    width: number;
    height: number;
};

const Branding = ({ width, height }: BrandingProps) => (
    <Image
        src="/media/logo.png"
        alt="Tether Logo"
        width={width}
        height={height}
        unoptimized
        draggable={false}
    />
);
export default Branding;
