import {User} from "./user.model";
import {ReturnOrder} from "./return-order.model";

export class ReturnProcesses{
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
    returnOrder:ReturnOrder;

    /**
     * 备注
     */
    remark:string;
}
