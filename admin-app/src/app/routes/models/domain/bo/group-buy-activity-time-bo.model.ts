import {GroupBuyActivityBo} from "./group-buy-activity-bo.model";


export class GroupBuyActivityTimeBo {
    /**
       *
     */
    id:number;
    /**
       *
     */
    guid:string;
    /**
       * 团购ID
     */
    groupBuyActivity:GroupBuyActivityBo;
    /**
       * 开始时间
     */
    startTime:string;
    /**
       * 结束时间
     */
    endTime:string;
}
