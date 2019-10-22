import {SaleOrderListVo} from "./sale-order-list-vo.model";
import {SupplierListVo} from "./supplier-list-vo.model";
import {ProductListVo} from "./product-list-vo.model";
import {MemberListVo} from "./member-list-vo.model";

export class SaleOrderItemListVo {

    /**
       * 订单项ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 订单（order表ID）
     */
    saleOrder:SaleOrderListVo;

    /**
       * 商品（product表ID）
     */
    product:ProductListVo;

    /**
       * 会员（member表ID）
     */
    member:MemberListVo;

    /**
       * 供应商（supplier表ID）
     */
    supplier:SupplierListVo;

    /**
       * 商品图片
     */
    commodityImg:string;

    /**
       * 商品名称
     */
    commodityName:string;

    /**
       *
     */
    commodityShortName:string;

    /**
       * 单价
     */
    price:number;

    /**
       * 数量
     */
    quantity:number;

    /**
       * 优惠
     */
    discount:number;

    /**
       * 小计
     */
    total:number;

    /**
       * 收货人
     */
    consignee:string;

    /**
       * 收货人电话
     */
    consigneePhone:string;

    /**
       * 收货人地址
     */
    consigneeAddr:string;

    /**
       * 配送方式
     */
    deliveryMode:string;

    /**
       * 物流公司
     */
    logisticsCompany:string;

    /**
       * 快递单号
     */
    trackingNo:string;

    /**
       * 删除（0否1是）
     */
    deleted:number;

    /**
       * 删除时间
     */
    delTime:string;
}
