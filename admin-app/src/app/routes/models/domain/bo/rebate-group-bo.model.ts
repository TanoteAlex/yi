


export class RebateGroupBo {
    /**
       * 返现拼团ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 活动标签
     */
    label:string;
    /**
       * 开始时间
     */
    startTime:string;
    /**
       * 结束时间
     */
    endTime:string;
    /**
       * 活动封面
     */
    activityCover:string;
    /**
       * 分享标题
     */
    shareTitle:string;
    /**
       * 商品（product表ID）
     */
    proudctId:number;
    /**
       * 活动库存（活动库存为独立库存，成交不会减少商品库存）
     */
    activityStock:number;
    /**
       * 成团时限（单位小时）团长发起的组团有效期
     */
    limitGroupTime:number;
    /**
       * 限购数量
     */
    limitQuantity:number;
    /**
       * 支付时限（单位分钟），买家XX分钟内未支付，自动释放库存
     */
    limitPayTime:number;
    /**
       * 奖励类型（1积分2优惠券）
     */
    rewardType:number;
    /**
       * 奖励积分
     */
    rewardIntegral:number;
    /**
       * 优惠券（coupon表ID）
     */
    couponId:number;
    /**
       * 运费设置（1卖家包邮1买家承担运费）
     */
    freightSet:number;
    /**
       * 运费
     */
    freight:number;
    /**
       * 状态（0启用1禁用）
     */
    state:number;
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
