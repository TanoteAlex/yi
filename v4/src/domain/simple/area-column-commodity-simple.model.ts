import {AreaColumnCommodityId} from '../original/area-column-commodity-id.model';
import {CommoditySimple} from './commodity-simple.model';
import {AreaColumnSimple} from './area-column-simple.model';

export class AreaColumnCommoditySimple {

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

    id: number;

    commodityNo: string;

    commodityName: string;

    currentPrice: number;

    imgPath: string;
}
