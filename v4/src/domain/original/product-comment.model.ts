import {Product} from "./product.model";
import {Member} from "./member.model";

export class ProductComment {

    /**
     * 评论ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 商品（product表ID）
     */
    productId: Product;

    /**
     * 商品名称
     */
    productName: string;

    /**
     * 会员（member表ID）
     */
    member: Member;

    /**
     * 会员昵称
     */
    nickname: string;

    /**
     * 编号
     */
    serialNo: string;

    /**
     * 评价星级
     */
    reviewStar: number;

    /**
     * 评价内容
     */
    reviewContent: string;

    /**
     * 回复内容
     */
    replyContent: string;

    /**
     * 是否显示（0不显示1显示）
     */
    display: boolean;

    /**
     * 评价时间
     */
    reviewTime: string;

    /**
     * 回复时间
     */
    replyTime: string;

    /**
     * 删除（0否1是）
     */
    deleted: boolean;

    /**
     * 删除时间
     */
    delTime: string;

    commentTime: string;

    commentStar: number;

    commentContent: string;
}
