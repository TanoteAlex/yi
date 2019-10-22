
export class Dept {

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
    parent:Dept;

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
    deleted:number;

    /**
       * 删除时间
     */
    delTime:string;

    parentDeptName:string;
}
