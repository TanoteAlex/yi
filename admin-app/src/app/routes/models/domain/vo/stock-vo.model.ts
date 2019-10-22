
export class StockVo {
    /**
       * 库存ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 商品（commodity表ID）
     */
    commodityId:number;
    /**
       * 货品（product表ID）
     */
    productId:number;
    /**
       * 库存量
     */
    stockQuantity:number;
    /**
       * 锁定库存量
     */
    lockQuantity:number;
    /**
       * 使用库存量
     */
    useQuantity:number;
    /**
       * 排序
     */
    sort:number;
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
