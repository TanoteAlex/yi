import { InviteActivity } from './invite-activity.model';
import { Product } from './product.model';
import { Coupon } from './coupon.model';

export class InvitePrize {

    /**
       * 邀请奖品ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 邀请有礼活动（invite_activity表id）
     */
    inviteActivity:InviteActivity;

    /**
       * 奖品编码
     */
    prizeNo:string;

    /**
       * 奖品名称
     */
    prizeName:string;

    /**
       * 邀请人数
     */
    inviteNum:number;

    /**
       * 奖品类型（1积分，2商品，3优惠券）
     */
    prizeType:number;

    /**
       * 积分
     */
    integral:number;

    /**
       * 商品（product表ID）
     */
    product:Product;

    /**
       * 优惠券（coupon表ID）
     */
    coupon:Coupon;

    /**
       * 排序
     */
    sort:number;

    /**
       * 状态（0启用1禁用）
     */
    state:number;

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
