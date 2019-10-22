import {MemberLevel} from "./member-level.model";
import {Community} from "./community.model";
import {Account} from "./account.model";

export class Member {

    /**
     * 会员ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 上级ID
     */
    parentId: Member;

    /**
     * 账号
     */
    username: string;

    /**
     * 密码
     */
    password: string;

    /**
     * 会员昵称
     */
    nickname: string;

    /**
     * 会员等级（member_level表ID）
     */
    memberLevel: MemberLevel;

    /**
     * 会员类型（0普通会员1管理员）
     */
    memberType: boolean;

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
     * 小区Id
     */
    communityId: number;

    /**
     * 小区信息
     */
    community: Community;

    /**
     * 注册时间
     */
    createTime: string;

    /**
     * 删除（0否1是）
     */
    deleted: boolean;

    /**
     * 删除时间
     */
    delTime: string;

    /**
     *  手机号
     */
    phone: string;

    /**
     *  性别
     */
    sex: boolean;

    /**
     * 用户头像
     */
    avater: string;

    /**
     * 会员生日
     */
    birthday: string;

    /**
     * 支付密码
     */
    payPassword: string;

    /**
     * 定位信息
     */
    localInfo: string;
    openId: string;
    unionId:string;
    account:Account
    spOpenId
}
