import {NationalGroup} from "../../original/national-group.model";
import {Member} from "../../original/member.model";


export class NationalGroupRecordBo {
    /**
       * 全国拼团记录ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 全国拼团（national_group表ID）
     */
    nationalGroup:NationalGroup;
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
       * 收货人
     */
    consignee:string;
    /**
       * 收货人电话
     */
    consigneePhone:string;
    /**
       * 收货人地址
     */
    consigneeAddr:string;
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
