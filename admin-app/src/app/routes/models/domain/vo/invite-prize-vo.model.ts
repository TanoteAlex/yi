import { CommoditySimple } from '../simple/commodity-simple.model';
import { ProductSimple } from '../simple/product-simple.model';
import { CouponSimple } from '../simple/coupon-simple.model';
import { InviteActivitySimple } from '../simple/invite-activity-simple.model';

export class InvitePrizeVo {
  /**
   * 邀请奖品ID
   */
  id: number;
  /**
   * GUID
   */
  guid: string;
  /**
   * 邀请有礼活动（invite_activity表id）
   */
  inviteActivity: InviteActivitySimple;
  /**
   * 奖品编码
   */
  prizeNo: string;
  /**
   * 奖品名称
   */
  prizeName: string;
  /**
   * 邀请人数
   */
  inviteNum: number;
  /**
   * 奖品类型（1积分，2商品，3优惠券）
   */
  prizeType: number;
  /**
   * 积分
   */
  integral: number;
  /**
   * 商品（commodity表ID）
   */
  commodity: CommoditySimple;
  /**
   * 商品（product表ID）
   */
  product: ProductSimple;
  /**
   * 优惠券（coupon表ID）
   */
  coupon: CouponSimple;
  /**
   * 排序
   */
  sort: number;
  /**
   * 状态（0启用1禁用）
   */
  state: number;
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
