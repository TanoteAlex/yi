
export class RecommendProduct {

    /**
       * 推荐位ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 推荐位（recommend表ID）
     */
    recommendId:number;

    /**
       * 商品（product表ID）
     */
    productId:number;

    /**
       * 商品编码（冗余）
     */
    productCode:string;

    /**
       * 商品名称（冗余）
     */
    productName:string;

    /**
       * 商品图片（冗余）
     */
    imgPath:string;
}
