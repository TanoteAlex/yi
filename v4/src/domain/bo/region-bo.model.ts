export class RegionBo {
    /**
     * 地区ID
     */
    id: number;
    /**
     * GUID
     */
    guid: string;
    /**
     * 省
     */
    province: string;
    /**
     * 市
     */
    city: string;
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
