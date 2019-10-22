


export class FreightTemplateBo {
    /**
       * 运费模板ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 模板编号
     */
    templateNo:string;
    /**
       * 模板名称
     */
    templateName:string;
    /**
       * 计费方式（1按重量2按件数）
     */
    chargeMode:boolean;
    /**
       * 状态（0启用1禁用）
     */
    state:boolean;
    /**
       * 首重
     */
    presetWeight:number;
    /**
       * 首费
     */
    presetFee:number;
    /**
       * 续重
     */
    extraWeight:number;
    /**
       * 续费
     */
    extraFee:number;
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
       * 小区
     */
    address:string;
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
