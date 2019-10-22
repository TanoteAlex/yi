
export class AttributeNameVo {
    /**
       * 属性名ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 属性组（attribute_group表ID）
     */
    attributeGroupId:number;
    /**
       * 属性名
     */
    attrName:string;
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
