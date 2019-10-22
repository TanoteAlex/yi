import {AttributeGroup} from "./attribute-group.model";
import {Product} from "./product.model";

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
     * 属性组（attribute_group表ID）
     */
    attributeGroupId: AttributeGroup;

    /**
     * 编码
     */
    attrNo: string;

    /**
     * 属性名
     */
    attrName: string;

    /**
     * 属性值
     */
    attrValue: string;

    /**
     * 图片
     */
    imgPath: string;

    /**
     * 排序
     */
    sort: number;

    /**
     * 状态（0启用1禁用）
     */
    state: boolean;

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
    deleted: boolean;

    /**
     * 删除时间
     */
    delTime: string;

    products: Product[];

    disable: boolean = true;
}
