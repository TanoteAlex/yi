
export class OrderSetting {

    /**
       * 订单设置ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 订单设置类型
     */
    setType:number;

    /**
       * 超时时间
     */
    timeout:number;

    /**
     * 时间单位（1天，2小时，3分钟）
     */
    timeUnit:number;

    /**
       * 天
     */
    day:number;

    /**
       * 小时
     */
    hour:number;

    /**
       * 分钟
     */
    minute:number;

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
