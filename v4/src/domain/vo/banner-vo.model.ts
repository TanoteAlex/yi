export class BannerVo {
    /**
     * banner表ID
     */
    id: number;
    /**
     * GUID
     */
    guid: string;
    /**
     * 标题
     */
    title: string;
    /**
     * 内容
     */
    content: string;
    /**
     * 图片路径
     */
    imgPath: string;
    /**
     * 链接路径
     */
    url: string;
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
