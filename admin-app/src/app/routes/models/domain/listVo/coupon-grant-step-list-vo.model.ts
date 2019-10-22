
export class CouponGrantStepListVo {
    /**
       * 发放步骤ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 优惠券发放配置（coupon_grant_config表ID）
     */
    couponGrantConfigId:number;
    /**
       * 发放节点（1购买，2收货，3评论，4超过三包时效）
     */
    grantNode:number;
    /**
       * 发放比例（0.00-100.00）
     */
    grantRate:number;
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
