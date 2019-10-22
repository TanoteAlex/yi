import { CommoditySimple } from '../simple/commodity-simple.model';
import { SalesAreaSimple } from '../simple/sales-area-simple.model';
import { SalesAreaCommodityIdBo } from '../bo/sales-area-commodity-id-bo';

export class SalesAreaCommodityListVo {
  salesAreaCommodityId: SalesAreaCommodityIdBo;
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
