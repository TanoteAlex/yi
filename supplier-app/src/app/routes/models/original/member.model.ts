import {ShippingAddress} from "./shipping-address.model";
import {ConsumeRecord} from "./consume-record.model";
import {MemberLevel} from "./member-level.model";
import {Account} from "./account.model";
import {AccountRecord} from "./account-record.model";

export class Member {

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
    parent:Member;
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

  /*  /!**memberLevelId
       * 会员等级（member_level表ID）
     *!/
    memberLevelId:number;
*/
    /**
     * 会员等级（member_level表ID）
     */
    memberLevel:MemberLevel;


    /*
    * 支付密码
    * */
    payPassword:string;

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

    /**
       * 删除（0否1是）
     */
    deleted:number;

    /**
       * 删除时间
     */
    delTime:string;

    /*
    * 收货地址
    * */
    shippingAddresss:ShippingAddress;


    /*
    * 消费明细
    * */
    consumeRecords:ConsumeRecord;

    account:Account;


    accountRecords:AccountRecord;

    /**
     * 会员状态
     */
    state:number;

    parentName:string;
}
