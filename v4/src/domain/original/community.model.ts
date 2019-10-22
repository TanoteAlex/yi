import {Member} from "./member.model";

export class Community {

    /**
     * 小区ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 管理员（member表ID）
     */
    member: Member;

    /**
     * 省
     */
    province: string;

    /**
     * 市
     */
    city: string;

    /**
     * 区
     */
    district: string;

    /**
     * 小区
     */
    address: string;

    /**
     * 状态（0启用1禁用）
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
     * 小区介绍
     */
    description: any;

    /**
     * 小区图片
     */
    imgPath: string;

    /**
     * 收货地址
     */
    receivingAddress: string;
}
