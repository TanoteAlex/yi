import {ObjectType} from "../../enums/object-type.enum";
import {UserSimple} from "../simple/user-simple.model";

export class BusinessLogVo {
    /**
     * 日志ID
     */
    logId: number;

    /**
     * 要素类型
     */
    objectType: ObjectType;
    /**
     * 要素ID
     */
    objectId: number;
    /**
     * 要素名称
     */
    objectName: string;
    /**
     * 要素路径
     */
    objectPath: string;

    /**
     * 日志内容
     */
    logContent: string;
    /**
     * 操作时间
     */
    createTime: Date;
    /**
     * 操作人
     */
    user: UserSimple;
}
