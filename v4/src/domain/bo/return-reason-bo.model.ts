export class ReturnReasonBo {
    /**
     * 退货原因ID
     */
    id: number;
    /**
     * GUID
     */
    guid: string;
    /**
     * 原因类型
     */
    reasonType: string;
    /**
     * 排序
     */
    sort: number;
    /**
     * 状态（0启用1禁用）
     */
    state: boolean;
    /**
     * 创建时间
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
