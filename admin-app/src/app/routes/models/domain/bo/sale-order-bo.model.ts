export class SaleOrderBo {
    /**
     * 订单ID
     */
    id: number;
    /**
     * GUID
     */
    guid: string;
    /**
     * 会员（member表ID）
     */
    memberId: number;
    /**
     * 订单编号
     */
    orderNo: string;
    /**
     * 订单状态（1待付款2待发货3已发货4已完成5已关闭）
     */
    state: boolean;
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
    logisticsCompany: string;
    /**
     * 快递单号
     */
    trackingNo: string;
    /**
     * 订单金额
     */
    orderAmount: number;
    /**
     * 优惠金额
     */
    discountAmount: number;
    /**
     * 代金券金额
     */
    voucherAmount: number;
    /**
     * 满减券金额
     */
    couponAmount: number;
    /**
     * 积分抵扣金额
     */
    integralCashAmount: number;
    /**
     * 余额抵扣金额
     */
    balance: number;
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
    payMode: boolean;
    /**
     * 下单时间
     */
    createTime: string;
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
}
