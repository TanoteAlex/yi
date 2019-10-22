export class OrderItemBo {
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
    orderId: number;
    /**
     * 商品（product表ID）
     */
    productId: number;
    /**
     * 会员（member表ID）
     */
    memberId: number;
    /**
     * 供应商（supplier表ID）
     */
    supplierId: number;
    /**
     * 商品图片
     */
    commodityImg: string;
    /**
     * 商品名称
     */
    commodityname: string;
    /**
     * 单价
     */
    price: number;
    /**
     * 数量
     */
    quantity: number;
    /**
     * 优惠
     */
    discount: number;
    /**
     * 小计
     */
    total: number;
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
     * 删除（0否1是）
     */
    deleted: boolean;
    /**
     * 删除时间
     */
    delTime: string;
}
