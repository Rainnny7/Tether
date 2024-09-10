import { DecorationAsset } from "@/types/user/avatar/decoration-asset";

/**
 * The decoration for a {@link Avatar}.
 */
export type AvatarDecoration = {
    /**
     * The asset of this decoration.
     */
    asset: DecorationAsset;

    /**
     * The id of the decoration sku.
     */
    skuId: string;

    /**
     * The unix time of when this decorations expires, undefined if permanent.
     */
    expires: number | undefined;
};
