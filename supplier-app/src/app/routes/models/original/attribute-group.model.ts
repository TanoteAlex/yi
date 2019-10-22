import {Commodity} from "./commodity.model";
import {AttributeValue} from "./attribute-value.model";

export class AttributeGroup {

    /**
       * 属性组ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 编码
     */
    groupNo:string;

    /**
       * 名称
     */
    groupName:string;

    /**
       * 图片
     */
    imgPath:string;

    /**
       * 排序
     */
    sort:number;

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
    deleted:number;

    /**
       * 删除时间
     */
    delTime:string;

    /**
     * 商品表
     */
    commodity:Commodity;

    attributeValues:any

    attributes:AttributeValue[]
}
