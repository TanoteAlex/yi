import {Member} from "./member.model";
import {GroupBuyActivityProduct} from "./group-buy-activity-product.model";
import {SaleOrder} from "./sale-order.model";
import {GroupBuyOrder} from "./group-buy-order.model";

export class JoinGroupBuy {

    /**
       * 参团表ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 会员（member表ID）
     */
    member:Member;

    /**
       * 开团（open_group_buy表ID）
     */
    groupBuyOrder:GroupBuyOrder;

    /**
       * 团购商品（group_buy_activity_product表ID）
     */
    groupBuyActivityProduct:GroupBuyActivityProduct;

    /**
       * 订单（订单表ID）
     */
    saleOrder:SaleOrder;

    /**
       * 团购状态（1待付款，2拼团中，3已成团，4已失效）
     */
    state:number;

    /**
       * 开始时间
     */
    startTime:string;

    /**
       * 结束时间
     */
    endTime:string;

    /**
       * 备注
     */
    remark:string;

    /**
       * 创建时间
     */
    createTime:string;

    /**
       * 删除（0否1是）
     */
    deleted:number;

    /**
       * 删除时间
     */
    delTime:string;
}
