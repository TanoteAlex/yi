export class Category {

    /**
     * 分类ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 上级分类ID
     */
    parentId: Category;

    /**
     * 分类编码
     */
    categoryNo: string;

    /**
     * 分类名称
     */
    categoryName: string;

    children: Category[];

    /**
     * 图片路径
     */
    imgPath: string;

    /**
     * 排序
     */
    sort: number;

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

    /**
     * 是否展示标题名字
     */
    showName: number;
}
