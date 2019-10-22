import {Member} from "./member.model";
import {Order} from "./order.model";
import {ReturnProcess} from "./return-process";

export class ReturnOrder {

    /**
     * 退货单ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 服务单号
     */
    returnNo: string;

    /**
     * 申请时间
     */
    applyTime: string;

    /**
     * 申请状态（1待处理2）
     */
    state: boolean;

    /**
     * 会员（member表ID）
     */
    memberId: Member;

    /**
     * 会员账号
     */
    memberPhone: string;

    /**
     * 联系人
     */
    contact: string;

    /**
     * 联系电话
     */
    contactPhone: string;

    /**
     * 订单（order表ID）
     */
    orderId: Order;

    /**
     * 订单编号
     */
    orderNo: string;

    /**
     * 订单金额
     */
    orderAmount: number;

    /**
     * 运费
     */
    freight: number;

    /**
     * 退款金额
     */
    refundAmount: number;

    /**
     * 退货原因
     */
    returnReason: string;

    /**
     * 问题描述
     */
    problemDescription: string;

    /**
     * 凭证照片
     */
    voucherPhoto: string;

    /**
     * 收货人
     */
    consignee: string;

    /**
     * 收货人电话
     */
    consigneePhone: string;

    /**
     * 收货人地址
     */
    consigneeAddr: string;

    /**
     * 备注
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

    /**
     * 退货进度查看
     */
    returnProcesses: ReturnProcess[];

    attachmentVos: any;

    afterSaleProcesses

    afterSaleNo
    problemDesc
}
