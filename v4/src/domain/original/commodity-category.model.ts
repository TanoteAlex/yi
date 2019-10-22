import {Commodity} from "./commodity.model";
import {Category} from "./category.model";

export class CommodityCategory {

    /**
     * 商品（commodity表ID）
     */
    commodityId: Commodity;

    /**
     * 属性（category表ID）
     */
    categoryId: Category;
}
