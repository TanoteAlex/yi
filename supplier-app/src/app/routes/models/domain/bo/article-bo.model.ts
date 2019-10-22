


export class ArticleBo {
    /**
       * 文章ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 文章标题
     */
    title:string;
    /**
       * 文章副标题
     */
    subtitle:string;
    /**
       * 作者
     */
    author:string;
    /**
       * 文章内容
     */
    content:string;
    /**
       * 商品图片
     */
    imgPath:string;
    /**
       * 商品链接
     */
    url:string;
    /**
       * 显示（0显示1不显示）
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
