import {Category} from "./category.model";
import {Attribute} from "./attribute.model";

export class AttributeGroup {

  /**
   * 属性组ID
   */
  id: number;

  /**
   * GUID
   */
  guid: string;

  /**
   * 商品分类
   */
  category: Category;

  /**
   * 编码
   */
  groupNo: string;

  /**
   * 名称
   */
  groupName: string;

  /**
   * 图片
   */
  imgPath: string;

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
   * 属性集合
   */
  attributes: Attribute[];

  /**
   * 属性显示方式（1纯文字2纯图片3文字+图片）
   */
  showMode: number;
}
