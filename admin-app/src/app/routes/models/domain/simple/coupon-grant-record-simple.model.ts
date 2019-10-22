
export class CouponGrantRecordSimple {
    /**
       * 发放记录ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 会员（member表ID）
     */
    memberId:number;
    /**
       * 发放配置（coupon_grant_config表ID）
     */
    couponGrantConfigId:number;
    /**
       * 优惠券（coupon表ID）
     */
    couponId:number;
    /**
       * 发放节点（1购买，2收货，3评论，4超过三包时效）
     */
    grantNode:number;
    /**
       * 面值
     */
    parValue:number;
    /**
       * 备注
     */
    remark:string;
    /**
       * 创建时间
     */
    createTime:string;
    /**
       * 删除（0否1是）
     */
    deleted:number;
    /**
       * 删除时间
     */
    delTime:string;
}
