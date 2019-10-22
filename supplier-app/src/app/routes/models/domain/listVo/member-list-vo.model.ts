

export class MemberListVo {

    /**
       * 会员ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
     * 上级ID
     */
    parent:MemberListVo;
    /**
       * 账号
     */
    username:string;

    /**
       * 密码
     */
    password:string;

    /**
       * 会员昵称
     */
    nickname:string;

    /**
       * 会员类型（0普通会员1管理员）
     */
    memberType:number;

    /**
       * 省
     */
    province:string;

    /**
       * 市
     */
    city:string;

    /**
       * 区
     */
    district:string;

    /**
       * 小区
     */
    address:string;

    /**
       * 注册时间
     */
    createTime:string;

}
