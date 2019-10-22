import {ObjectType} from "../../enums/object-type.enum";

export class NoticeVo {
    /**
     * 通知ID
     */
    noticeId: number;
    /**
     * 通知标题
     */
    noticeTitle: string;
    /**
     * 通知内容
     */
    noticeContent: string;
    /**
     * 通知时间
     */
    createTime: Date;

    /**
     * 是否已读
     */
    readed: boolean;

    /**
     * 业务要素类型
     */
    objectType: ObjectType;
    /**
     * 业务要素ID
     */
    objectId: number;
    /**
     * 战略要素名称
     */
    objectName: string;
    /**
     * 战略要素对象路径
     */
    objectPath: string;
}
