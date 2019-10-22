
export class RecommendSimple {
    /**
       * 推荐位ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 推荐位标题
     */
    title:string;
    /**
       * 推荐位默认图片路径
     */
    imgPath:string;
    /**
       * 状态（0启用1禁用）
     */
    state:boolean;
    /**
       * 创建时间
     */
    createTime:string;
    /**
       * 删除（0否1是）
     */
    deleted:boolean;
    /**
       * 删除时间
     */
    delTime:string;
}
