export class DeptTreeVo<T> {
    /**
     * 部门ID
     */
    deptId: number;
    /**
     * 部门GUID
     */
    guid: string;
    /**
     * 部门编码
     */
    deptCode: string;
    /**
     * 部门名称
     */
    deptName: string;
    /**
     * 部门简称
     */
    simpleName: string;
    /**
     * 直接下级部门
     */
    children: Array<DeptTreeVo<T>>;
    /**
     * 部门的详情数据
     */
    data: T;
    /**
     * 下级部门是否已折叠
     */
    collapsed: boolean;
}
