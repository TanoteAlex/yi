
export class Position {

    /**
       * 位置ID
     */
    id:number;

    /**
       * 名称
     */
    name:string;

    /**
       * 位置类型（1广告2推荐）
     */
    positionType:number;

    /**
       * 备注
     */
    remark:string;

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
