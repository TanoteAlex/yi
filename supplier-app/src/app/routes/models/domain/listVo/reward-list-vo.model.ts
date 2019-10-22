
export class RewardListVo {
    /**
       * 奖励表ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 奖励编码
     */
    code:string;
    /**
       * 奖励名称
     */
    name:string;
    /**
       * 奖励类型（1注册，2评论，3连续签到）
     */
    rewardType:number;
    /**
       * 连续签到天数
     */
    signDays:number;
    /**
       * 状态（0启用1禁用）
     */
    state:number;
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
