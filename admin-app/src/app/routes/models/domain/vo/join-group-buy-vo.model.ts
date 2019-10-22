import {MemberVo} from "./member-vo.model";
import {SaleOrderVo} from "./sale-order-vo.model";
import {GroupBuyActivityProductVo} from "./group-buy-activity-product-vo.model";
import {GroupBuyOrderVo} from "./group-buy-order-vo.model";

export class JoinGroupBuyVo {
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
    member:MemberVo;
    /**
       * 开团（open_group_buy表ID）
     */
    groupBuyOrder:GroupBuyOrderVo;
    /**
       * 团购商品（group_buy_activity_product表ID）
     */
    groupBuyActivityProduct:GroupBuyActivityProductVo;
    /**
       * 订单（订单表ID）
     */
    saleOrder:SaleOrderVo;
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
