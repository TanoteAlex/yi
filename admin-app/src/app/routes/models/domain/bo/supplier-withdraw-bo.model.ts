
export class SupplierWithdrawBo {
    /**
       * 供应商提现ID
     */
    id: number;
    /**
       * GUID
     */
    guid: string;
    /**
       * 供应商（supplier表id）
     */
    supplierId: number;
    /**
       * 供应商名称
     */
    supplierName: string;
    /**
       * 收款人
     */
    payee: string;
    /**
       * 收款账号
     */
    receiptsNo: string;
    /**
       * 收款名称
     */
    receiptsName: string;
    /**
       * 申请提现金额
     */
    applyAmount: number;
    /**
       * 实际到账金额
     */
    actualAmount: number;
    /**
       * 手续费
     */
    serviceCharge: number;
    /**
       * 付款人
     */
    drawee: string;
    /**
       * 付款账号
     */
    paymentsNo: string;
    /**
       * 付款名称
     */
    paymentsName: string;
    /**
       * 付款方式（1线下转账2线上转账）
     */
    paymentMethod: number;
    /**
       * 提现状态（1待发放，2，驳回，3已发放，4发放异常）
     */
    state: number;
    /**
       * 错误说明
     */
    errorDesc: string;
    /**
       * 申请时间
     */
    applyTime: string;
    /**
       * 发放时间
     */
    grantTime: string;
    /**
       * 删除（0否1是）
     */
    deleted: number;
    /**
       * 删除时间
     */
    delTime: string;
}
