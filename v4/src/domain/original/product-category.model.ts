import {Product} from "./product.model";
import {Category} from "./category.model";

export class ProductCategory {

    /**
     * 货品（product表ID）
     */
    productId: Product;

    /**
     * 分类（category表ID）
     */
    categoryId: Category;
}
