import {User} from "./user.model";
import {RefundOrder} from "./refund-order.model";

export class RefundProcesses{
    /**
     * 退货流程ID
     */

    id:number;

    /**
     * GUID
     */
   guid:number;

    /**
     * 内容
     */
   content:string;

    /**
     * 用户(User表Id)
     */
    user:User;


    /**
     * 处理时间
     */
    handleDate:string;

    /**
     * 退货id(退货表)
     */
    refundOrder:RefundOrder;



    /**
     * 备注
     */
    remark:string
}
