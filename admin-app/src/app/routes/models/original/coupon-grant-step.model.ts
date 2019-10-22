import {CouponGrantConfig} from "./coupon-grant-config.model";

export class CouponGrantStep {

  /**
   * 发放步骤ID
   */
  id: number;

  /**
   * GUID
   */
  guid: string;

  /**
   * 优惠券发放配置（coupon_grant_config表ID）
   */
  couponGrantConfig: CouponGrantConfig;

  /**
   * 发放节点（1购买，2收货，3评论，4超过三包时效）
   */
  grantNode: number;

  /**
   * 发放比例（0.00-100.00）
   */
  grantRate: number;

  grantTitle: string;
}
