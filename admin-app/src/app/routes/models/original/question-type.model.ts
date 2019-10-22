
export class QuestionType {

    /**
       * 问题分类ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 分类名称
     */
    typeName:string;

    /**
       * 排序
     */
    sort:number;

    /**
       * 状态（0启用1禁用）
     */
    state:number;

    /**
       * 创建时间
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
}
