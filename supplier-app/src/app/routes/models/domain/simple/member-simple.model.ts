
export class MemberSimple {
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
    parentId:number;
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
       * 会员等级（member_level表ID）
     */
    memberLevelId:number;
    /**
       * 会员类型（0普通会员1管理员）
     */
    memberType:boolean;
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
    /**
       * 删除（0否1是）
     */
    deleted:boolean;
    /**
       * 删除时间
     */
    delTime:string;
}
