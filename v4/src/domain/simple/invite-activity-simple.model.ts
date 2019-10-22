
export class InviteActivitySimple {
    /**
       * 邀请有礼活动ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 标题
     */
    title:string;
    /**
       * banner图
     */
    banner:string;
    /**
       * 活动规则
     */
    rule:string;
    /**
       * 开始时间
     */
    startTime:string;
    /**
       * 结束时间
     */
    endTime:string;
    /**
       * 邀请成功标示（1注册，2购买）
     */
    inviteFlag:boolean;
    /**
       * 状态（0启用1禁用）
     */
    state:boolean;
    /**
       * 删除（0否1是）
     */
    deleted:boolean;
    /**
       * 删除时间
     */
    delTime:string;
}
