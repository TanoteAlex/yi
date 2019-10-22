import {Commodity} from "./commodity.model";
import {Attribute} from "./attribute.model";

export class CommodityAttribute {

    /**
     * 商品（commodity表ID）
     */
    commodityId: Commodity;

    /**
     * 属性（attribute表ID）
     */
    attributeId: Attribute;
}
