export class WithdrawBo {
    /**
     *
     */
    withdrawId: number;
    /**
     * 卡号
     */
    cardNo: string;
    /**
     * 是否允许
     */
    isWithdraw: boolean;
    /**
     * 创建时间
     */
    createTime: string;
    /**
     * 收款人
     */
    name: string;
    /**
     * 提现金额
     */
    price: number;
    /**
     * 审核时间
     */
    auditTime: string;
}
