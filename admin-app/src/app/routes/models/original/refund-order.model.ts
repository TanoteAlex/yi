import {Process} from "./process.model";
import {User} from "./user.model";
import {RefundProcesses} from "./refund-processes";
import {Member} from "./member.model";


export class RefundOrder {

    /**
       * 退款单ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 服务单号
     */
    refundNo:string;

    /**
       * 申请时间
     */
    applyTime:string;

    /**
       * 申请状态（1待处理2）
     */
    state:number;

    /**
       * 会员（member表ID）
     */
    memberId:number;

    /**
     * 会员
     */
    member:Member;

    /**
       * 会员账号
     */
    memberPhone:string;

    /**
       * 订单（order表ID）
     */
    orderId:number;

    /**
       * 订单编号
     */
    orderNo:string;

    /**
       * 订单金额
     */
    orderAmount:number;

    /**
       * 退款金额
     */
    refundAmount:number;

    /**
       * 退款方式（1退回原支付渠道）
     */
    refundMode:number;

    /**
       * 退款类型（1取消订单（待发货））
     */
    refundType:number;

    /**
       * 退款原因
     */
    refundReason:string;

    /**
       * 处理备注
     */
    remark:string;

    /**
       * 删除（0否1是）
     */
    deleted:number;

    /**
       * 删除时间
     */
    delTime:string;

    /**
     * 处理时间
     */
    handleTime:string;

    /**
     * 退款退货处理表
     */
    processs:Process;

    user:User;

    username:string;


    refundProcess:RefundProcesses;

    refundProcesses:RefundProcesses[];

}
