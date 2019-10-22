import {Supplier} from "./supplier.model";


export class FreightTemplateConfig {

  /**
   * 运费模板配置ID
   */
  id: number;

  /**
   * GUID
   */
  guid: string;

  /**
   * 模板配置编号
   */
  configNo: string;

  /**
   * 模板配置名称
   */
  configName: string;

  /**
   * 供应商（supplier表ID）
   */
  supplier: Supplier;

  /**
   * 发货时间（1-12小时内，2-24小时内，3-1天内，4-3天内，5-5天内）
   */
  deliveryTime: number;

  /**
   * 发货时间单位（1小时，2天）
   */
  timeUnit: number;

  /**
   * 运费类型（1自定义运费，2卖家承担运费）
   */
  freightType: number;

  /**
   * 计价方式（1按件数，2按重量，3按体积）
   */
  chargeMode: number;

  /**
   * 运送方式（1快递，2EMS，3平邮）
   */
  deliveryMode: number;

  /**
   * 包邮条件（0非指定条件1指定条件）
   */
  freeCondition: number;

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

  customFreightTemplates: any
  freeFreightTemplates: any
}
