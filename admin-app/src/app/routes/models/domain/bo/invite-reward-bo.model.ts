import { InviteActivitySimple } from '../simple/invite-activity-simple.model';
import { RewardSimple } from '../simple/reward-simple.model';


export class InviteRewardBo {
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
    inviteActivity:InviteActivitySimple;
    /**
       * 奖励（reward表id）
     */
    reward:RewardSimple;
    /**
       * 排序
     */
    sort:number;
    /**
       * 状态（0启用1禁用）
     */
    state:number;
}
