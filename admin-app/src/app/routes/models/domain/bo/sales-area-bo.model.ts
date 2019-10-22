


export class SalesAreaBo {
    /**
       * 销售专区ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 标题
     */
    title:string;
    /**
       * banner图
     */
    banner:string;
    /**
       * 排列方式（1一排展一个，2一排展两个）
     */
    showMode:number;
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
