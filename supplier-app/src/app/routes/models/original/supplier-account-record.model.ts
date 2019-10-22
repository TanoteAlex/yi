import {Supplier} from "./supplier.model";


export class SupplierAccountRecord {

    /**
       * 账户记录ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 供应商（supplier表ID）
     */
    supplier:Supplier;

    /**
       * 操作类型（1收入2支出）
     */
    operateType:number;

    /**
       * 流水号
     */
    serialNo:string;

    /**
       * 交易金额
     */
    tradeAmount:number;

    /**
       * 账户余额
     */
    balance:number;

    /**
       * 交易类型（1订单收入2提现3退款）
     */
    tradeType:number;

    /**
       * 交易时间
     */
    tradeTime:string;

    /**
       * 备注
     */
    remark:string;
}
