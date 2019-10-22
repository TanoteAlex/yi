import {Member} from "./member.model";
import {Commodity} from "./commodity.model";

export class Comment {

    /**
       * 评论ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 商品（commodity表ID）
     */
    commodity:Commodity;

    /**
       * 商品名称
     */
    commodityName:string;

    /**
       * 会员（member表ID）
     */
    member:Member;

    /**
       * 会员昵称
     */
    nickname:string;

    /**
       * 编号
     */
    serialNo:string;

    /**
       * 评价星级
     */
    commentStar:number;

    /**
       * 评价内容
     */
    commentContent:string;

    /**
       * 回复内容
     */
    replyContent:string;

    /**
       * 是否显示（0不显示1显示）
     */
    display:number;

    /**
       * 评价时间
     */
    commentTime:string;

    /**
       * 回复时间
     */
    replyTime:string;

    /**
       * 删除（0否1是）
     */
    deleted:number;

    /**
       * 删除时间
     */
    delTime:string;

    username:string;
}
