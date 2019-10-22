import {ProductVo} from "./product-vo.model";
import {CommodityVo} from "./commodity-vo.model";
import {GroupBuyActivityVo} from "./group-buy-activity-vo.model";

export class GroupBuyActivityProductVo {
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
    groupBuyActivity:GroupBuyActivityVo;
    /**
       * 商品编号
     */
    commodity:CommodityVo;
    /**
       * 货品编号
     */
    product:ProductVo;
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
