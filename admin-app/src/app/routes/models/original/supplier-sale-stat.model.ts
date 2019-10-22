
export class SupplierSaleStat {

    /**
     * 供应商ID
     */
    supplierId: number;

    /**
     * 供应商名称
     */
    supplierName: string;

    /**
     * 订单数
     */
    orderNum: number;

    /**
     * 销售额
     */
    saleAmount: number;

    /**
     * 平台占比-待用
     */
    platformRatio: number;

    /**
     * 成本
     */
    costAmount: number;

    /**
     * 运费
     */
    freightAmount: number;

    /**
     * 利润
     */
    profitAmount: number;

    /**
     * 统计开始时间
     */
    statTime: string;

    /**
     * 统计结束时间
     */
    endTime: string;
}
