
export class DistributionLevelVo {
    /**
       * 分销等级ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 等级名称
     */
    name:string;
    /**
       * 级别(如1,2,3,4)
     */
    rank:number;
    /**
       * 佣金比例（0.00-100.00）%
     */
    commissionRate:number;
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
    deleted:boolean;
    /**
       * 删除时间
     */
    delTime:string;
}
