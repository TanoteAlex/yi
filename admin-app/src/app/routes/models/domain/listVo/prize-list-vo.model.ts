import {Commodity} from "../../original/commodity.model";
import {Coupon} from "../../original/coupon.model";

export class PrizeListVo {
  /**
   * 奖品表ID
   */
  id: number;
  /**
   * GUID
   */
  guid: string;
  /**
   * 奖品编码
   */
  code: string;
  /**
   * 奖品名称
   */
  name: string;
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
  commodity: Commodity;
  /**
   * 优惠券（coupon表ID）
   */
  coupon: Coupon;
  /**
   * 状态（0启用1禁用）
   */
  state: number;
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
