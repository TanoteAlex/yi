
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
       * 上级分类
     */
    parent:Category;
    /**
      * 下级分类
    */
    children: Category[];

    /**
       * 分类编码
     */
    categoryNo: string;

    /**
       * 分类名称
     */
    categoryName: string;

    /**
       * 图片路径
     */
    imgPath: string;

    /**
       * 排序
     */
    sort: number;

    /**
       * 备注
     */
    remark: string;

}
