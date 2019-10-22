import {Process} from "./process.model";
import {ReturnProcesses} from "./return-processes";
import {Member} from "./member.model";
import {Attachment} from "./attachment.model";
import {SaleOrder} from "./sale-order.model";

export class ReturnOrder {

  /**
   * 退货单ID
   */
  id:number;

  /**
   * GUID
   */
  guid:string;

  /**
   * 服务单号
   */
  returnNo:string;

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
   * 联系人
   */
  contact:string;

  /**
   * 联系电话
   */
  contactPhone:string;

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
   * 运费
   */
  freight:number;

  /**
   * 退款金额
   */
  refundAmount:number;

  /**
   * 退货原因
   */
  returnReason:string;

  /**
   * 问题描述
   */
  problemDescription:string;

  /**
   * 凭证照片
   */
  voucherPhoto:string;

  /**
   * 收货人
   */
  consignee:string;

  /**
   * 收货人电话
   */
  consigneePhone:string;

  /**
   * 收货人地址
   */
  consigneeAddr:string;

  /**
   * 备注
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
   * 添加退款退货处理表
   */
  processs:Process;

  username:string;

  returnProcesses:ReturnProcesses[];

  returnProcess:ReturnProcesses;
  /**
   * 处理类型（1确认2拒绝3已收货0无状态）
   */
  processType:number;

  attachmentVos:Attachment;

  saleOrder:SaleOrder

}
