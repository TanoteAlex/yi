import {Member} from "./member.model";
import {Product} from "./product.model";

export class ShoppingCartProduct {

    /**
     * 购物车商品ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 会员（member表ID）
     */
    memberId: Member;

    /**
     * 商品（product表ID）
     */
    product: Product;

    /**
     * 商品名称
     */
    productName: string;

    /**
     * 商品简称
     */
    productShortName: string;

    /**
     * 购买数量
     */
    quantity: number;

    /**
     * 商品价格
     */
    price: string;

    /**
     * 优惠金额
     */
    discount: string;

    /**
     * 优惠信息
     */
    discountInfo: string;

    /**
     * 是否选择（数据库没有存
     */
    select: boolean;

    imgPath;

    state;
}