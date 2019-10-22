import {Member} from "./member.model";

export class LoginRecord {

    /**
       * 登录记录ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 会员（member表ID）
     */
    member:Member;

    /**
       * 登录IP
     */
    loginIp:string;

    /**
       * 登录来源
     */
    loginSource:string;

    /**
       * session
     */
    sessionId:string;

    /**
       * 登录时间
     */
    loginTime:string;

    /**
       * 登出时间
     */
    logoutTime:string;
}
