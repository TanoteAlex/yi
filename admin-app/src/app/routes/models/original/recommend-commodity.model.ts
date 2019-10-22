import {Commodity} from "./commodity.model";
import {Recommend} from "./recommend.model";

export class RecommendCommodity {

    /**
       * 推荐位（recommend表ID）
     */
    recommendId: number;

    /**
       * 商品（commodity表ID）
     */
    commodityId: number;

    commodity: Commodity;

    recommend: Recommend;
    /**
       * 排序
     */
    sort: number;
    //此ID为commodity的ID
    id: number;
    
    commodityNo:string;
    commodityName:string;
    currentPrice:number;
    imgPath:string;
    
}
