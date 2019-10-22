
export class GroupBuyActivitySimple {
    /**
       * 
     */
    id:number;
    /**
       * 编码
     */
    guid:string;
    /**
       * 团购活动名称
     */
    activityName:string;
    /**
       * 优先级
     */
    priority:number;
    /**
       * 团购活动类型
     */
    type:number;
    /**
       * 优惠方式类型
     */
    promotionType:number;
    /**
       * 团购活动主办方
     */
    sponsor:string;
    /**
       * 团购活动封面
     */
    coverUrl:string;
    /**
       * 是否包邮
     */
    hasPost:boolean;
    /**
       * 是否支持优惠券抵扣
     */
    hasCoupon:boolean;
    /**
       * 审核状态 :0为未审核,1为审核
     */
    audited:boolean;
    /**
       * 发布状态:0为未发布,1为以发布
     */
    published:boolean;
    /**
       * 库存状态
     */
    stockState:number;
    /**
       * 删除状态
     */
    deleted:boolean;
    /**
       * 备注
     */
    remark:string;
    /**
       * 审核人
     */
    auditUserId:number;
    /**
       * 审核时间
     */
    auditDate:string;
    /**
       * 发布人
     */
    publishUserId:number;
    /**
       * 发布时间
     */
    publishDate:string;
    /**
       * 更新人(更新，终结，删除)
     */
    updateUserId:number;
    /**
       * 更新时间
     */
    updateDate:string;
    /**
       * 创建人
     */
    createUserId:number;
    /**
       * 创建时间
     */
    createDate:string;
    /**
       * 终结状态 0:未终结，1：已终结（如果活动有多个时间段的，则必须最后一个时间段结束后，才能变为已终结状态）
     */
    finished:boolean;
}
