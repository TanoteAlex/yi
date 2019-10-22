import { AreaColumnSimple } from '../simple/area-column-simple.model';
import { CommoditySimple } from '../simple/commodity-simple.model';
import { AreaColumnCommodityId } from '../../original/area-column-commodity-id.model';

export class AreaColumnCommodityVo {
  areaColumnCommodityId: AreaColumnCommodityId;
  /**
   * 专区栏目（area_column表ID）
   */
  areaColumn: AreaColumnSimple;
  /**
   * 商品（commodity表ID）
   */
  commodity: CommoditySimple;
  /**
   * 排序
   */
  sort: number;
}
