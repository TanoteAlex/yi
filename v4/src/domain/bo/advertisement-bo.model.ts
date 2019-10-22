export class AdvertisementBo {
    /**
     * 广告位ID
     */
    id: number;
    /**
     * GUID
     */
    guid: string;
    /**
     * 广告位标题
     */
    title: string;
    /**
     * 广告位图片路径
     */
    imgPath: string;
    /**
     * 广告位链接
     */
    url: string;
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
    commodities = [];
}
