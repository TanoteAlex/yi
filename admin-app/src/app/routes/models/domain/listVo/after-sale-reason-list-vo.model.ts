
export class AfterSaleReasonListVo {
    /**
       * 售后原因ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 售后类型（1退款-未收到货，2退货退款-已收到货，3换货）
     */
    afterSaleType:number;
    /**
       * 售后原因
     */
    reason:string;
    /**
       * 排序
     */
    sort:number;
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
