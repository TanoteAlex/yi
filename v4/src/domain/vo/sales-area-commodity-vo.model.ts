import { CommoditySimple } from '../simple/commodity-simple.model';
import {SalesAreaCommodityId} from '../original/sales-area-commodity-id';
import {SalesAreaSimple} from '../simple/sales-area-simple.model';

export class SalesAreaCommodityVo {
  salesAreaCommodityId: SalesAreaCommodityId;
  /**
   * 销售专区（sales_area表ID）
   */
  salesArea: SalesAreaSimple;
  /**
   * 商品（commodity表ID）
   */
  commodity: CommoditySimple;
  /**
   * 排序
   */
  sort: number;
}
