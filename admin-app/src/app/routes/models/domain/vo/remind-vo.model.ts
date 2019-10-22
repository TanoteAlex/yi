import {ObjectType} from "../../enums/object-type.enum";

/**
 * 提醒VO
 */
export class RemindVo {
    /**
     * 提醒ID
     */
    remindId: number;
    /**
     * 提醒标题
     */
    remindTitle: string;
    /**
     * 提醒内容
     */
    remindContent: string;
    /**
     * 提醒时间
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
