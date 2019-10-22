import {ShippingAddress} from "./shipping-address.model";
import {ConsumeRecord} from "./consume-record.model";
import {MemberLevel} from "./member-level.model";
import {Account} from "./account.model";
import {AccountRecord} from "./account-record.model";
import {Community} from "./community.model";
import {Member} from "./member.model";
import {SaleOrder} from "./sale-order.model";

export class MemberCommission {

  id: number;
  /**
   * GUID
   */
  guid: string;
  /**
   * 会员（member表ID）
   */
  member: Member;
  /**
   * 订单（sale_order表ID）
   */
  saleOrder: SaleOrder;
  /**
   * 下级（member表ID）
   */
  subordinate: Member;
  /**
   * 佣金类型（1一级佣金，2二级佣金，3小区佣金）
   */
  commissionType: number;
  /**
   * 结算状态（1未结算，2已结算，3已退回）
   */
  settlementState: number;
  /**
   * 佣金金额
   */
  commissionAmount: number;
  /**
   * 备注
   */
  remark: string;
  /**
   * 创建时间
   */

  createTime: string;
  /**
   * 删除（0否1是）
   */
  deleted: number;
  /**
   * 删除时间
   */
  delTime: string;

}
