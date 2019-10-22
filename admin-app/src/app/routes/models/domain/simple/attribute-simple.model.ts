
export class AttributeSimple {
    /**
       * 属性ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 属性名（attribute_name表ID）
     */
    attributeNameId:number;
    /**
       * 属性值（attribute_value表ID）
     */
    attributeValueId:number;
    /**
       * 编码
     */
    attrName:string;
    /**
       * 属性名
     */
    attrValue:string;
    /**
       * 图片
     */
    imgPath:string;
    /**
       * 排序
     */
    sort:number;
    /**
       * 状态（0启用1禁用）
     */
    state:number;
    /**
       * 创建时间
     */
    createTime:string;
    /**
       * 备注
     */
    remark:string;
    /**
       * 删除（0否1是）
     */
    deleted:boolean;
    /**
       * 删除时间
     */
    delTime:string;
}
