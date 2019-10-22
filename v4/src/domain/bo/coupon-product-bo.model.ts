export class CouponProductBo {
    /**
     * ID
     */
    id: number;
    /**
     * GUID
     */
    guid: string;
    /**
     * 优惠券（coupon表ID）
     */
    couponId: number;
    /**
     * 商品（product表ID）
     */
    productId: number;
    /**
     * 商品编码
     */
    productNo: string;
    /**
     * 商品名称
     */
    productName: string;
    /**
     * 删除（0否1是）
     */
    deleted: boolean;
    /**
     * 删除时间
     */
    delTime: string;
}
