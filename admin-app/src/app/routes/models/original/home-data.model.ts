
export class Homepage {
  /**
   * 今日订单数
   */
   todayOrderNum:number;

  /**
   * 昨日订单数
   */
    yesterdayOrderNum:number;

  /**
   * 待发货订单
   */
  waitDeliveryNum:number;

  /**
   * 今日成交额
   */
  todayTradeAmout:number;

  /**
   * 昨日成交额
   */
  yesterdayTradeAmout:number;

  /**
   * 会员数
   */
  memberNum:number;

  /**
   * 商品数
   */
   commodityNum:number;

  /**
   * 供应商
   */
  supplierNum:number;

  /**
   * 售后订单
   */
  afterSaleOrderNum:number;

  /**
   * 用户消费排行
   */
  memberConsumes:Object[][];

  //会员名
  memberName:number;

  /**
   * 商品销量排行
   */
  commoditySales:Object[][];

  //商品售卖数
  saleNum:number;

  //折线图数据数组集合
  dailyAddNums: any[];
}
