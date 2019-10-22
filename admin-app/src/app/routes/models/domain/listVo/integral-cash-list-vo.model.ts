
export class IntegralCashListVo {
    /**
       * 积分抵现ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 积分
     */
    integral:number;
    /**
       * 抵现金额
     */
    cash:number;
    /**
       * 状态（0启用1禁用）
     */
    state:boolean;
    /**
       * 创建时间
     */
    createTime:string;
    /**
       * 删除（0否1是）
     */
    deleted:boolean;
    /**
       * 删除时间
     */
    delTime:string;
}
