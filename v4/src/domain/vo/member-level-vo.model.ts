export class MemberLevelVo {
    /**
     * 会员等级ID
     */
    id: number;
    /**
     * GUID
     */
    guid: string;
    /**
     * 等级名称
     */
    name: string;
    /**
     * 会员人数（冗余）
     */
    quantity: number;
    /**
     * 成长值满足点
     */
    growthValue: number;
    /**
     * 折扣（0.00-100.00）
     */
    discount: number;
    /**
     * 默认等级（0非默认1默认）
     */
    initial: boolean;
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
}
