import {Product} from "./product.model";
import {Attribute} from "./attribute.model";

export class ProductAttribute {

    /**
     * 货品（product表ID）
     */
    productId: Product;

    /**
     * 属性（attribute表ID）
     */
    attributeId: Attribute;
}
