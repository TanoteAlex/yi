import {CommunityGroup} from "../../original/community-group.model";
import {Member} from "../../original/member.model";

export class CommunityGroupRecordSimple {
    /**
       * 小区拼团记录ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 小区拼团（community_group表ID）
     */
    communityGroup:CommunityGroup;
    /**
       * 团编号
     */
    groupCode:string;
    /**
       * 团长（member表ID）
     */
    member:Member;
    /**
       * 成团人数
     */
    groupPeople:number;
    /**
       * 参团人数
     */
    joinPeople:number;
    /**
       * 开团时间
     */
    openTime:string;
    /**
       * 状态（1等待开团2开团成功3开团失败）
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
