import {Member} from "./member.model";

export class Message {

    /**
     * 消息ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 消息标题
     */
    title: string;

    /**
     * 消息内容
     */
    content: string;

    /**
     * 消息类型（0系统1..）
     */
    messageType: number;

    /**
     * 排序
     */
    sort: number;

    /**
     * 显示（0显示1不显示）
     */
    state: number;

    /**
     * 创建时间
     */
    createTime: string;

    /**
     * 删除（0否1是）
     */
    deleted: number;

    /**
     * 删除时间
     */
    delTime: string;

    /**
     *推送范围
     */
    pushScope: number;

    /**
     * 推送会员
     */
    members: Member[];

}
