
export class CouponUse {

    /**
       * ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 优惠券（coupon表ID）
     */
    couponId:number;

    /**
       * 优惠码
     */
    couponNo:string;

    /**
       * 优惠券领取（coupon_receive表ID）
     */
    couponReceiveId:number;

    /**
       * 面值
     */
    parValue:number;

    /**
       * 使用
     */
    use:number;

    /**
       * 剩余
     */
    surplus:number;

    /**
       * 会员（member表ID）
     */
    memberId:number;

    /**
       * 会员账号
     */
    memberPhone:string;

    /**
       * 使用时间
     */
    useTime:string;

    /**
       * 订单（order表ID）
     */
    orderId:number;

    /**
       * 订单编号
     */
    orderNo:string;

    /**
       * 删除（0否1是）
     */
    deleted:boolean;

    /**
       * 删除时间
     */
    delTime:string;
}
