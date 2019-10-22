
export class DeptVo {
    /**
       * 部门ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 上级部门
     */
    parentId:number;
    /**
       * 部门编码
     */
    deptNo:string;
    /**
       * 部门名称
     */
    deptName:string;
    /**
       * 部门描述
     */
    description:string;
    /**
       * 创建时间
     */
    createTime:string;
    /**
       * 删除（0否1是）
     */
    deleted:boolean;
    /**
       * 删除时间
     */
    delTime:string;
}
