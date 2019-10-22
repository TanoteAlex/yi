import {Member} from "./member.model";

export class Community {

  /**
   * 小区ID
   */
  id: number;
  /**
   * 管理员
   */
  member: Member;
  /**
   * GUID
   */
  guid: string;
  /**
   * 省
   */
  province: string;
  /**
   * 市
   */
  city: string;
  /**
   * 区
   */
  district: string;
  /**
   * 小区
   */
  address: string;
  /**
   * 状态（0启用1禁用）
   */
  state: number;

  /**
   * 市第一个字
   */
  initialsCity:string;
  /**
   * 提成比例
   */
  commissionRate: number;

  /**
   * 小区介绍
   */
  description:string;

  /**
   * 小区图片
   */
  imgPath:string;

  /**
   * 小区地址
   */
  receivingAddress:string;

}
