import {Member} from "./member.model";

export class Account {

    /**
     * 账户ID
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
     * 成交单数
     */
    orderQuantity: number;

    /**
     * 消费金额
     */
    consumeAmount: number;

    /**
     * 账户余额
     */
    balance: number;

    /**
     * 冻结金额
     */
    freezeAmount: number;

    /**
     * 总积分
     */
    totalIntegral: number;
    /**
     * 可用积分
     */
    availableIntegral: number;

    /**
     * 备注
     */
    remark: string;
    unsettledCommission;
    cashedCommission;
    cashableCommission;
    totalCommission;
}
