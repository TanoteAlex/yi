import {AttributeGroup} from "./attribute-group.model";

export class Attribute {

    /**
     * 属性ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 属性组（attribute_group表表ID）
     */
    attributeGroup: AttributeGroup;

    /**
     * 属性名 冗余
     */
    attrName: string;

    /**
     * 属性值
     */
    attrValue: string;

    /**
     * 排序
     */
    sort: number;

    /**
     * 创建时间
     */
    createTime: string;

    /**
     * 备注
     */
    remark: string;

    /**
     * 删除（0否1是）
     */
    deleted: number;

    /**
     * 删除时间
     */
    delTime: string;

    /**
     * 图片链接
     */
    imgPath:number;
}
