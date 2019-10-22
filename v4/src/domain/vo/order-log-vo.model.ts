export class OrderLogVo {
    /**
     * 订单ID
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
     * 订单编号
     */
    orderNo: string;
    /**
     * 订单状态（1创建订单2支付成功3开始配送4确认收货）
     */
    state: boolean;
    /**
     * 操作时间
     */
    operateTime: string;
    /**
     * 操作人
     */
    operator: string;
    /**
     * 备注说明
     */
    remark: string;
}
