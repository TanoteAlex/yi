import {Commodity} from "./commodity.model";
import {MemberLevel} from "./member-level.model";
import {CouponReceive} from "./coupon-receive.model";

export class Coupon {

    /**
       * 用户ID
     */
    id: number;

    /**
       * GUID
     */
    guid: string;

    /**
       * 优惠券编码
     */
    couponNo: string;

    /**
       * 优惠券名称
     */
    couponName: string;

    /**
       * 优惠券类型（1满减券2代金券3储值券）
     */
    couponType: number;

    /**
       * 面值
     */
    parValue: number;

    /**
     * 情怀语
     */
    feelWord: string;

    /**
       * 发放数量
     */
    quantity: number;
    /**
      * 领取数量
    */
    receiveQuantity: number;

    /**
       * 使用数量
     */
    useQuantity: number;


    /**
     * 使用条件类型（0无限制，1满XX元可用，2满XX件可用）
     */
    useConditionType: number;


    /**
       * 使用条件（满xx元可用，不限制为空）
     */
    fullMoney: number;

    /**
     * 使用条件（满xx件可用，不限制为空）
     */
    fullQuantity: number;

    /**
       * 领取方式（1手工发放2自助领取）
     */
    receiveMode: number;

    /**
       * 会员等级（member_level表ID）
     */
    memberLevels: MemberLevel[];

    /**
       * 每人限领（不限制为空）
     */
    limited: number;

    /**
       * 有效期类型（1时间段2固定天数）
     */
    validType: number;

    /**
       * 开始时间
     */
    startTime: string;

    /**
       * 结束时间
     */
    endTime: string;

    /**
       * 固定天数（领取后到期天数）
     */
    fixedDay: number;

    /**
       * 删除（0否1是）
     */
    deleted: number;

    /**
       * 删除时间
     */
    delTime: string;

    //货品
    commodities: Commodity[];

    memberLevel: MemberLevel;

    couponReceive: CouponReceive;

}
