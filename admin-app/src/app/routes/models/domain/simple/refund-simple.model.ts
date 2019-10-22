export class RefundSimple {
    /**
     *
     */
    refundId: number;
    /**
     *
     */
    orderId: number;
    /**
     * 是否退款
     */
    isRefund: boolean;
    /**
     * 退款原因
     */
    remark: string;
    /**
     * 创建时间
     */
    createTime: string;
    /**
     * 审核时间
     */
    auditTime: string;
    /**
     * 物流单号
     */
    logisticsNo: string;
    /**
     * 是否退货
     */
    isReturn: boolean;
}
