import {MemberListVo} from "./member-list-vo.model";
import {SupplierListVo} from "./supplier-list-vo.model";
import {SaleOrderListVo} from "./sale-order-list-vo.model";

export class AfterSaleOrderListVo {
    /**
       * 售后单ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 会员（member表ID）
     */
    member:MemberListVo;
    /**
       * 供应商（supplier表ID）
     */
    supplier:SupplierListVo;
    /**
       * 订单（sale_order表ID）
     */
    saleOrder:SaleOrderListVo;
    /**
       * 售后类型（1退款-未收到货，2退货退款-已收到货，3换货）
     */
    afterSaleType:number;
    /**
       * 售后单号
     */
    afterSaleNo:string;
    /**
       * 申请状态（1审核中，2处理中，3已完成）
     */
    applyState:number;
    /**
       * 处理状态（1待审核，2待收货，3待退款，4待打款，5已完成，6拒绝退货，7拒绝退款）
     */
    processState:number;
    /**
       * 退款订单号（申请退款使用）
     */
    refundOrderNo:string;
    /**
       * 微信/支付宝退款单号
     */
    refundTradeNo:string;
    /**
       * 退款支付状态（1待回执，2已回执）
     */
    refundPayState:number;
    /**
       * 订单金额
     */
    orderAmount:number;
    /**
       * 支付金额
     */
    payAmount:number;
    /**
       * 退款金额
     */
    refundAmount:number;
    /**
       * 实际退款金额
     */
    actualRefundAmount:number;
    /**
       * 退款方式（1退回原支付渠道）
     */
    refundMode:number;
    /**
       * 售后原因
     */
    afterSaleReason:string;
    /**
       * 问题描述
     */
    problemDesc:string;
    /**
       * 凭证照片
     */
    voucherPhoto:string;
    /**
       * 联系人
     */
    contact:string;
    /**
       * 联系人电话
     */
    contactPhone:string;
    /**
       * 申请时间
     */
    applyTime:string;
    /**
       * 备注
     */
    remark:string;
    /**
       * 创建时间
     */
    createTime:string;
    /**
       * 删除（0否1是）
     */
    deleted:number;
    /**
       * 删除时间
     */
    delTime:string;
}
