import {RefundOrder} from "./refund-order.model";
import {ReturnOrder} from "./return-order.model";
import {User} from "./user.model";

export class Process {

    /**
     * 退款处理ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 退款单（refund_order表ID）
     */
    refundOrderId: RefundOrder;

    /**
     * 退货单（return_order表ID）
     */
    returnOrderId: ReturnOrder;

    /**
     * 处理人（user表ID）
     */
    userId: User;

    /**
     * 处理人
     */
    username: string;

    /**
     * 处理类型（1确认2拒绝）
     */
    processType: boolean;

    /**
     * 处理时间
     */
    processTime: string;

    /**
     * 处理备注
     */
    remark: string;

    /**
     * 删除（0否1是）
     */
    deleted: boolean;

    /**
     * 删除时间
     */
    delTime: string;
}
