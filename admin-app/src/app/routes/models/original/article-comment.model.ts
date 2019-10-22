import {Article} from "./article.model";
import {Attachment} from "./attachment.model";

export class ArticleComment {

    /**
       * 文章ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 文章（article表ID）
     */
    article:Article;

    /**
       * 评论人
     */
    commentator:string;

    /**
       * 评论内容
     */
    commentContent:string;

    /**
       * 评论时间
     */
    commentTime:string;

    /**
     * 商品图片
     */
    imgPath:string;

    //图片集合
    attachmentVos: Attachment[];

    /**
       * 回复内容
     */
   replyContent:string;

    /**
       * 回复时间
     */
    replyTime:string;

    /**
       * 显示（0不显示1显示）
     */
    state:number;

    /**
       * 删除（0否1是）
     */
    deleted:boolean;

    /**
       * 删除时间
     */
    delTime:string;
}
