
export class InviteRewardVo {
    /**
       * 邀请奖品ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 邀请有礼活动（invite_activity表id）
     */
    inviteActivityId:number;
    /**
       * 奖励（reward表id）
     */
    rewardId:number;
    /**
       * 排序
     */
    sort:number;
    /**
       * 状态（0启用1禁用）
     */
    state:number;
    /**
       * 删除（0否1是）
     */
    deleted:boolean;
    /**
       * 删除时间
     */
    delTime:string;
}
