import {UserSimple} from "../simple/user-simple.model";

export class LoginLogVo {
    /**
     * 日志ID
     */
    loginId: number;
    /**
     * 登录员工
     */
    user: UserSimple;
    /**
     * 登录结果内容
     */
    logContent: string;
    /**
     * 登录结果状态
     */
    state: string;
    /**
     * 登录时间
     */
    loginTime: Date;
}
