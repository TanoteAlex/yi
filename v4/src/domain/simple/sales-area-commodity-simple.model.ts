import {SalesAreaSimple} from './sales-area-simple.model';
import {CommoditySimple} from './commodity-simple.model';
import {SalesAreaCommodityId} from '../original/sales-area-commodity-id';

export class SalesAreaCommoditySimple {
    salesAreaCommodityId:SalesAreaCommodityId;
    /**
       * 销售专区（sales_area表ID）
     */
    salesArea:SalesAreaSimple;
    /**
       * 商品（commodity表ID）
     */
    commodity:CommoditySimple;
    /**
       * 排序
     */
    sort:number;
}
