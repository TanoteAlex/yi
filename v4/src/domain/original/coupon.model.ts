export class Coupon {

    /**
     * 用户ID
     */
    id: number = null;

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
     * 优惠券类型（1满减券2买送券3储值券）
     */
    couponType: number;

    /**
     * 面值
     */
    parValue: number = 0;

    /**
     * 发放数量
     */
    quantity: number;

    /**
     * 使用数量
     */
    useQuantity: number;

    /**
     * 使用条件（满xx可用，不限制为空）
     */
    useCondition: number;

    /**
     * 领取方式（1手工发放2自助领取）
     */
    receiveMode: boolean;

    /**
     * 会员等级（member_level表ID）
     */
    memberLevelId: number;

    /**
     * 每人限领（不限制为空）
     */
    limit: number;

    /**
     * 有效期类型（1时间段2固定天数）
     */
    validType: boolean;

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
    deleted: boolean;

    /**
     * 删除时间
     */
    delTime: string;

    /**
     * 是否已选择
     */
    isSelected: boolean;

    /**
     * 可用优惠券总数，用于writeOrder页面的统计
     */
    length: number;

    useConditionType;

    fullMoney;

    fullQuantity;


    /**
     * 购物车中的的实体
     */

    /*coupon:Coupon;
    storage:Coupon;

    /!*选中优惠券的优惠金额*!/
    couponValue:number =0;
    storageValue:number =0;

    /!*选中优惠券id数组*!/
    couponRecives = [];

    /!*优惠券可用数量*!/
    couponNum;
    storageNum;*/
}
