import {Member} from "./member.model";
import {Order} from "./order.model";
import {Product} from "./product.model";
import {Supplier} from "./supplier.model";

export class SaleOrderItem {

    /**
     * 订单项ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 订单（order表ID）
     */
    orderId: Order;

    /**
     * 商品（product表ID）
     */
    product: Product;

    /**
     * 会员（member表ID）
     */
    memberId: Member;

    /**
     * 供应商（supplier表ID）
     */
    supplier: Supplier;

    /**
     * 商品图片
     */
    commodityImg: string;

    /**
     * 商品名称
     */
    commodityName: string;

    /**
     * 商品简称
     */
    commodityShortName: string;

    /**
     * 单价
     */
    price: number;
    /**
    * 销售价
    */
    salePrice: number;

    /**
    * 供货价
    */
    supplyPrice: number;

    /**
     * 数量
     */
    quantity: number;

    /**
     * 优惠
     */
    discount: number;

    /**
     * 成本金额
     */
    costAmount: number;

    /**
     * 利润金额
     */
    profitAmount: number;

    /**
     * 小计
     */
    subTotal: number;

    /**
     * 收货人
     */
    consignee: string;

    /**
     * 收货人电话
     */
    consigneePhone: number;

    /**
     * 收货人地址
     */
    consigneeAddr: string;

    /**
     * 配送方式
     */
    deliveryMode: string;

    /**
     * 物流公司
     */
    logisticsCompany: string;

    /**
     * 快递单号
     */
    trackingNo: string;

    /**
     * 结算状态
     */
    settlementState: number;

    /**
     * 删除（0否1是）
     */
    deleted: boolean;

    /**
     * 删除时间
     */
    delTime: string;
}
