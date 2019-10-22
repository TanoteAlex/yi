import {Position} from "./position.model";
import {Commodity} from "./commodity.model";
import {Article} from "./article.model";

export class Advertisement {

  /**
   * 广告位ID
   */
  id: number;

  /**
   * GUID
   */
  guid: string;

  /**
   * 广告位标题
   */
  title: string;

  /**
   * 广告位图片路径
   */
  imgPath: string;
  /**
   * 广告类型
   */
  linkType: number;

  /**
   * 链接ID
   */
  linkId: number;


  /**
   * 广告位链接
   */
  url: string;

  /**
   * 位置表id
   */
  position: Position;
  /**
   * 位置名称
   */
  positionName: string;

  /**
   * 状态（0启用1禁用）
   */
  state: number;

  /**
   * 创建时间
   */
  createTime: string;

  /**
   * 删除（0否1是）
   */
  deleted: number;

  /**
   * 删除时间
   */
  delTime: string;

  /**
   * 内容
   */
  content: string;

  /**
   * 排序
   */
  sort: number;

  commodity: Commodity;

  article: Article;

}
