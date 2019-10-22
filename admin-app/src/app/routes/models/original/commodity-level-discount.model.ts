import {Commodity} from "./commodity.model";
import {MemberLevel} from "./member-level.model";

export class CommodityLevelDiscount {

    /**
       * 商品等级折扣ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 商品（commodity表ID）
     */
    commodity:Commodity;

    /**
       * 会员等级（member_level表ID）
     */
    memberLevel:MemberLevel;

    /**
       * 折扣（0.00-100.00）
     */
    discount:number;

    memberLevelName:string;
}
