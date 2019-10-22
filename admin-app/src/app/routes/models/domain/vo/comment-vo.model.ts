import {AttachmentVo} from "./attachment-vo.model";

export class CommentVo {
    /**
     * 沟通ID
     */
    commentId: number;
    /**
     * 所属业务对象路径(REST——STYLE)
     */
    objectPath: string;

    /**
     * 沟通内容
     */
    content: string;
    /**
     * 创建时间
     */
    createTime: Date;
    /**
     * 沟通人ID
     */
    userId: number;
    /**
     * 沟通人
     */
    userName: string;
    /**
     * 沟通人头像
     */
    avatarImg: string;
    /**
     * 回复人ID
     */
    replyUserId: number;
    /**
     * 回复人名称
     */
    replyUserName: string;
    /**
     * 回复人头像
     */
    replyAvatarImg: string;

    /**
     * 附件列表
     */
    attachments: Array<AttachmentVo>;
}
