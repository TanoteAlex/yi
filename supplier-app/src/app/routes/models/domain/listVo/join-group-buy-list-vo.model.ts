import {SaleOrderListVo} from "./sale-order-list-vo.model";
import {GroupBuyActivityProductListVo} from "./group-buy-activity-product-list-vo.model";
import {MemberListVo} from "./member-list-vo.model";
import {OpenGroupBuyListVo} from "./open-group-buy-list-vo.model";

export class JoinGroupBuyListVo {
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
    member:MemberListVo;
    /**
       * 开团（open_group_buy表ID）
     */
    openGroupBuy:OpenGroupBuyListVo;
    /**
       * 团购商品（group_buy_activity_product表ID）
     */
    groupBuyActivityProduct:GroupBuyActivityProductListVo;
    /**
       * 订单（订单表ID）
     */
    saleOrder:SaleOrderListVo;
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
