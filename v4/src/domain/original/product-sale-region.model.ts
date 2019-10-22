import {Product} from "./product.model";
import {Region} from "./region.model";

export class ProductSaleRegion {

    /**
     * 商品（product表ID）
     */
    productId: Product;

    /**
     * 地区（region表ID）
     */
    regionId: Region;
}
