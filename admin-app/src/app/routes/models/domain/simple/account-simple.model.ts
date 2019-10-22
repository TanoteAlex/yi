
export class AccountSimple {
    /**
       * 账户ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 会员（member表ID）
     */
    memberId:number;
    /**
       * 成交单数
     */
    orderQuantity:number;
    /**
       * 消费金额
     */
    consumeAmount:number;
    /**
       * 账户余额
     */
    balance:number;
    /**
       * 冻结金额
     */
    freezeAmount:number;
    /**
       * 积分
     */
    totalIntegral:number;
    /**
       * 可用积分
     */
    availableIntegral:number;
    /**
       * 备注
     */
    remark:string;
}
