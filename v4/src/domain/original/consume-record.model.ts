import {Member} from "./member.model";

export class ConsumeRecord {

    /**
     * 消费记录ID
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
     * 交易号
     */
    tradeNo: string;

    /**
     * 订单号
     */
    orderNo: string;

    /**
     * 收货人
     */
    consignee: string;

    /**
     * 实付金额
     */
    payAmount: number;

    /**
     * 完成时间
     */
    finishTime: string;

    /**
     * 备注
     */
    remark: string;
}
