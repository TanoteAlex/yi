
export class IntegralRecordVo {
    /**
       * 积分记录ID
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
       * 积分任务（integral_task表ID）
     */
    integralTaskId:number;
    /**
       * 操作类型（0增加1减少）
     */
    operateType:number;
    /**
       * 增/减积分数值
     */
    operateIntegral:number;
    /**
       * 当前积分
     */
    currentIntegral:number;
    /**
       * 创建时间
     */
    createTime:string;
}
