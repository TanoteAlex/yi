import {DeptSimple} from "../simple/dept-simple.model";

export class UserListVo {
    /**
     * 对象路径
     */
    objectPath: string;
    /**
     * GUID
     */
    guid: string;

    /**
     * 员工ID
     */
    userId: number;
    /**
     * 员工编码
     */
    userCode: string;
    /**
     * 员工名称
     */
    userName: string;
    /**
     * 微信号
     */
    weixinId: string;
    /**
     * EMAIL
     */
    email: string;

    /**
     * 启用、停用
     */
    enabled: boolean;
    /**
     * 状态
     */
    state: string;
    /**
     * 办公电话
     */
    telephone: string;
    /**
     * 办公地点
     */
    workPlace: string;
    /**
     * 职位信息
     */
    position: string;
    /**
     * 员工说明
     */
    remark: string;
    /**
     * 手机
     */
    phone: string;
    /**
     * 头像
     */
    avatarImg: string;
    /**
     * 员工所属部门
     */
    workDepts: Array<DeptSimple>;
    /**
     * 是否删除
     */
    del: boolean;
}
