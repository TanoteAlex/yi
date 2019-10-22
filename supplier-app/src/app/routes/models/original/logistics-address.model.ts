
export class LogisticsAddress {

    /**
       * 物流地址ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 供应商ID
     */
    supplierId:number;

    /**
       * 地址类型(1、收货地址 2、发货地址)
     */
    addressType:number;

    /**
       * 联系人
     */
    contact:string;

    /**
       * 联系电话
     */
    contactPhone:string;

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
       * 默认状态（0、非默认 1、默认）
     */
    state:number;

    /**
       * 创建时间
     */
    createTime:string;

    /**
       * 删除（0、否1、是）
     */
    deleted:number;

    /**
       * 删除时间
     */
    delTime:string;
}
