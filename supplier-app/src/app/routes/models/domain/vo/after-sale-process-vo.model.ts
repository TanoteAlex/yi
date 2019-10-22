import {AfterSaleOrderVo} from "./after-sale-order-vo.model";
import {UserVo} from "./user-vo.model";

export class AfterSaleProcessVo {
    /**
       * 售后处理ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 售后单（after_sale_order表ID）
     */
    afterSaleOrder:AfterSaleOrderVo;
    /**
       * 处理人（user表ID）
     */
    user:UserVo;
    /**
       * 处理人
     */
    processPerson:string;
    /**
       * 处理类型（1待审核，2待收货，3待退款，4待打款，5已完成，6拒绝退货，7拒绝退款）
     */
    processType:number;
    /**
       * 处理信息
     */
    processInfo:string;
    /**
       * 处理时间
     */
    processDate:string;
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
