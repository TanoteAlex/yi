import {Process} from "../../original/process.model";


export class RefundOrderBo {
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
    state:boolean;
    /**
       * 会员（member表ID）
     */
    memberId:number;
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
    refundMode:boolean;
    /**
       * 退款类型（1取消订单（待发货））
     */
    refundType:boolean;
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
    deleted:boolean;
    /**
       * 删除时间
     */
    delTime:string;

    processs:Process;
}
