import {GroupBuyActivityBo} from "./group-buy-activity-bo.model";


export class GroupBuyActivityMemberBo {
    /**
       *
     */
    id:number;
    /**
       *
     */
    guid:string;
    /**
       * 活动编号
     */
    groupBuyActivity:GroupBuyActivityBo;
    /**
       * 会员类型:全部会员,等级会员
     */
    memberType:number;
    /**
       * 会员级别:普通会员,Vip会员...
     */
    memberLevel:string;
}
