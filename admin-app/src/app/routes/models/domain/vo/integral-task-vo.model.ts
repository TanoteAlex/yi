
export class IntegralTaskVo {
    /**
       * 任务ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 任务名称
     */
    taskName:string;
    /**
       * 成长值
     */
    growthValue:number;
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
