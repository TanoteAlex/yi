import {ProductBo} from "./product-bo.model";
import {MemberLevelBo} from "./member-level-bo.model";
import {UserBo} from "./user-bo.model";
import {GroupBuyActivityProductBo} from "./group-buy-activity-product-bo.model";
import {GroupBuyActivityTimeBo} from "./group-buy-activity-time-bo.model";
import {GroupBuyActivityMemberBo} from "./group-buy-activity-member-bo.model";


export class GroupBuyActivityBo {
  /**
   *
   */
  id: number;
  /**
   * 编码
   */
  guid: string;
  /**
   * 团购活动名称
   */
  activityName: string;
  /**
   * 团购活动封面
   */
  coverUrl: string;
  /**
   * 开始时间
   */
  startTime: string;

  /**
   * 结束时间
   */
  endTime: string;
  /**
   * 优先级
   */
  priority: number;
  /**
   * 发布人
   */
  publishUser: UserBo;
  /**
   * 团购活动货品
   */
  product: ProductBo;
  /**
   * 会员类型（1、全部会员  2、等级会员）
   */
  memberType: number;

  /**
   * 等级会员选择
   */
  memberLevels: MemberLevelBo[];
  /**
   * 团购活动库存
   */
  activityStock: number;
  /**
   * 团购价
   */
  groupPrice: number;

  /**
   * 成团人数
   */
  groupPeople: number;
  /**
   * 团购活动类型
   */
  type: number;
  /**
   * 是否包邮
   */
  hasPost: number;
  /**
   * 是否支持优惠券抵扣
   */
  hasCoupon: number;

  groupBuyActivityProducts: GroupBuyActivityProductBo[];

  groupBuyActivityMember: GroupBuyActivityMemberBo;

  groupBuyActivityTimes: GroupBuyActivityTimeBo[];
}
