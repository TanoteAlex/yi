export class AttributeGroupSimple {
    /**
     * 属性组ID
     */
    id: number;
    /**
     * GUID
     */
    guid: string;
    /**
     * 编码
     */
    groupNo: string;
    /**
     * 名称
     */
    groupName: string;
    /**
     * 图片
     */
    imgPath: string;
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
     * 备注
     */
    remark: string;
    /**
     * 删除（0否1是）
     */
    deleted: boolean;
    /**
     * 删除时间
     */
    delTime: string;
}
