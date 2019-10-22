


export class ExpressTemplateBo {
    /**
       * 模板ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 单据编号
     */
    templateNo:string;
    /**
       * 单据名称
     */
    templateName:string;
    /**
       * 打印宽
     */
    printWidth:number;
    /**
       * 打印高
     */
    printHigh:number;
    /**
       * 状态（0启用1禁用）
     */
    state:boolean;
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
