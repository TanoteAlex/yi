export class ArticleCommentVo {
    /**
     * 文章ID
     */
    id: number;
    /**
     * GUID
     */
    guid: string;
    /**
     * 文章（article表ID）
     */
    articleId: number;
    /**
     * 评论人
     */
    commentator: string;
    /**
     * 评论内容
     */
    commentContent: string;
    /**
     * 评论时间
     */
    commentTime: string;
    /**
     * 回复内容
     */
    replyContent: string;
    /**
     * 回复时间
     */
    replyTime: string;
    /**
     * 显示（0不显示1显示）
     */
    state: boolean;
    /**
     * 删除（0否1是）
     */
    deleted: boolean;
    /**
     * 删除时间
     */
    delTime: string;
}
