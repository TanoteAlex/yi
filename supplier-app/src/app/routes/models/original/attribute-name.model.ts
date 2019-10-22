import {AttributeGroup} from "./attribute-group.model";

export class AttributeName {

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
    attributeGroup:AttributeGroup;

    /**
       * 属性名
     */
    attrName:string;

    /**
       * 状态（0启用1禁用）
     */
    state:number;

    /**
       * 创建时间
     */
    createTime:string;

    attributeValues:any

}
