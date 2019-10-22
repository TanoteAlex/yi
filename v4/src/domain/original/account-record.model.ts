import {Member} from "./member.model";

export class AccountRecord {

    /**
     * 账户记录ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 会员（member表ID）
     */
    memberId: Member;

    /**
     * 操作类型（1收入2支出）
     */
    operateType: number;

    /**
     * 流水号
     */
    serialNo: string;

    /**
     * 交易金额
     */
    tradeAmount: number;

    /**
     * 账户余额
     */
    balance: number;

    /**
     * 交易类型（1佣金转入2在线支付3提现4小区提成）
     */
    tradeType;

    /**
     * 交易方式（1店铺佣金2余额）
     */
    tradeMode: number;

    /**
     * 交易时间
     */
    tradeTime: string;

    /**
     * 备注
     */
    remark: string;
}
