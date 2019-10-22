import {Supplier} from "./supplier.model";

export class SupplierAccount {

    /**
       * 账户ID
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
       * 账户金额-待定字段
     */
    amount:number;

    /**
       * 账户余额
     */
    balance:number;

    /**
       * 冻结金额
     */
    freezeAmount:number;

    /**
       * 提现金额
     */
    withdrawAmount:number;

    /**
       * 创建时间
     */
    createTime:string;

    /**
       * 备注
     */
    remark:string;
}
