import {GroupBuyActivityListVo} from "./group-buy-activity-list-vo.model";
import {CommodityListVo} from "./commodity-list-vo.model";
import {ProductListVo} from "./product-list-vo.model";

export class GroupBuyActivityProductListVo {
    /**
       *
     */
    id:number;
    /**
       *
     */
    guid:string;
    /**
       * 团购活动编号
     */
    groupBuyActivity:GroupBuyActivityListVo;
    /**
       * 商品编号
     */
    commodity:CommodityListVo;
    /**
       * 货品编号
     */
    product:ProductListVo;
    /**
       * 团购价格
     */
    groupBuyPrice:number;
    /**
       * 备货数
     */
    stockUpQuantity:number;
    /**
       * 注水数
     */
    injectWater:number;
    /**
       * 已购买数
     */
    boughtQuantity:number;
}
