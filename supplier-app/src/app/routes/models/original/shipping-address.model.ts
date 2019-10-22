
export class ShippingAddress {

    /**
       * 收货地址ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * member表ID
     */
    memberId:number;

    /**
       * 姓名
     */
    fullName:string;

    /**
       * 手机号
     */
    phone:string;

    /**
       * 省
     */
    province:string;

    /**
       * 市
     */
    city:string;

    /**
       * 区
     */
    district:string;

    /**
       * 详细地址
     */
    address:string;

    /**
     * 默认地址（0非默认1默认）
     */
    preferred:boolean;

    /**
       * 默认地址（0非默认1默认）
     */
    default:boolean;

    /**
       * 创建时间
     */
    createTime:string;

    /**
       * 删除（0否1是）
     */
    deleted:boolean;

    /**
       * 删除时间
     */
    delTime:string;
}
