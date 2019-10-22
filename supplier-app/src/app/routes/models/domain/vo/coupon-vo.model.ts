
export class CouponVo {
    /**
       * 用户ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 优惠券编码
     */
    couponNo:string;
    /**
       * 优惠券名称
     */
    couponName:string;
    /**
       * 优惠券类型（1满减券2买送券3储值券）
     */
    couponType:boolean;
    /**
       * 面值
     */
    parValue:number;
    /**
       * 发放数量
     */
    quantity:number;
    /**
       * 使用数量
     */
    useQuantity:number;
    /**
       * 使用条件（满xx可用，不限制为空）
     */
    useCondition:number;
    /**
       * 领取方式（1手工发放2自助领取）
     */
    receiveMode:boolean;
    /**
       * 会员等级（member_level表ID）
     */
    memberLevelId:number;
    /**
       * 每人限领（不限制为空）
     */
    limit:number;
    /**
       * 有效期类型（1时间段2固定天数）
     */
    validType:boolean;
    /**
       * 开始时间
     */
    startTime:string;
    /**
       * 结束时间
     */
    endTime:string;
    /**
       * 固定天数（领取后到期天数）
     */
    fixedDay:number;
    /**
       * 删除（0否1是）
     */
    deleted:boolean;
    /**
       * 删除时间
     */
    delTime:string;
}
