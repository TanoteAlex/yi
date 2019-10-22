import {Member} from "./member.model";
import {SaleOrderItem} from "./sale-order-item.model";
import {CommodityItem} from "./commodity-item.model";

export class Order {
    groupBuyOrder
    /**
     * 订单ID
     */
    id: number;
    balance;
    saleOrder;
    groupNum;
    joinNum;
    /**
     * GUID
     */
    guid: string;

    /**
     * 会员（member表ID）
     */
    memberId: Member;

    /**
     * 订单编号
     */
    orderNo: string;

    /**
     * 订单状态（1待付款2待发货3已发货4已完成5已关闭10待评价11已评价）
     */
    orderState;

    /**
     * 买家留言
     */
    buyerMessage: string;

    /**
     * 收货人
     */
    consignee: string;

    /**
     * 收货人电话
     */
    consigneePhone: string;

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
    expressCompany: string;

    /**
     * 快递单号
     */
    expressNo: string;

    /**
     * 订单金额
     */
    orderAmount: number;

    /**
     * 优惠金额
     */
    discountAmount: number;

    /**
     * 运费
     */
    freight: number;

    /**
     * 实付金额
     */
    payAmount: number;

    /**
     * 交易号
     */
    tradeNo: string;

    /**
     * 支付方式（1支付宝2微信3银联）
     */
    payMode: string;

    /**
     * 下单时间
     */
    orderTime: string;

    /**
     * 付款时间
     */
    paymentTime: string;

    /**
     * 发货时间
     */
    deliveryTime: string;

    /**
     * 成交时间
     */
    dealTime: string;

    /**
     * 备注
     */
    remark: string;

    /**
     * 删除（0否1是）
     */
    deleted: boolean;

    /**
     * 删除时间
     */
    delTime: string;

    /**
     * 订单项目列表
     */
    saleOrderItems: SaleOrderItem[];

    /*不符合组件命名，进行命名转化*/
    productList: CommodityItem[];

    /**
     * 售后状态（1可申请2申请中3已申请4已过期）
     */
    afterSaleState;

    /**
     * 售后提醒信息
     */
    afterSaleMsg: string;

    /**
     * 订单状态提醒信息
     */
    orderStateMsg: string;

    /**
     *订单详情物流信息
     */
    orderFollowMsg: string;
    orderFollowMsgTime: string;

    /**
     * 优惠券，储值券优惠金额
     */
    couponAmount;
    voucherAmount

    /**
     * 商品总价
     */
    commoditiesAmount: number;

    /**
     * 订单代付款有效时间点
     */
    closeTime;

    nationalGroupRecord

    /**
     * 未完成交易为null，待评价1，已评价2
     */
    commentState;

    state
}
