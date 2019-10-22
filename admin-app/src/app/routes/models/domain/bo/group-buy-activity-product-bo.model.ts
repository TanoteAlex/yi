import {GroupBuyActivityBo} from "./group-buy-activity-bo.model";
import {CommodityBo} from "./commodity-bo.model";
import {ProductBo} from "./product-bo.model";


export class GroupBuyActivityProductBo {
  /**
   *
   */
  id: number;
  /**
   *
   */
  guid: string;
  /**
   * 团购活动编号
   */
  groupBuyActivity: GroupBuyActivityBo;
  /**
   * 商品编号
   */
  commodity: CommodityBo;
  /**
   * 货品编号
   */
  product: ProductBo;
  /**
   * 团购价格
   */
  groupBuyPrice: number;
  /**
   * 成团人数
   */
  groupBuyQuantity: number;
  /**
   * 备货数
   */
  stockUpQuantity: number;
  /**
   * 注水数
   */
  injectWater: number;
  /**
   * 已购买数
   */
  boughtQuantity: number;
}
