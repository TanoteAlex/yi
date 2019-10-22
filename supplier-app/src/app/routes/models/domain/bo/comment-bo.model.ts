import {AttachmentVo} from "../vo/attachment-vo.model";
import {UserSimple} from "../simple/user-simple.model";

export class CommentBo {
    /**
     * 所属业务对象路径(REST——STYLE)
     */
    objectPath: string;

    /**
     * 沟通内容
     */
    content: string;

    /**
     * 回复人ID
     */
    replyUserId: number;
    /**
     * 回复人名称
     */
    replyUserName: string;

    /**
     * 附件列表
     */
    attachments: Array<AttachmentVo>;

    /**
     * @ 哪些员工
     */
    ats: Array<UserSimple>;
}
