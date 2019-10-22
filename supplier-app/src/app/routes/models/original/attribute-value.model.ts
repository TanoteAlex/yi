import {AttributeName} from "./attribute-name.model";
import {Product} from "./product.model";

export class AttributeValue {

    /**
       * 属性值ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 属性值
     */
    attrValue:string;

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

    /**
       * 
     */
    attrNameId:number;

    /**
     * 属性名
     */
    attributeName:AttributeName;
    products:Product[]
}
