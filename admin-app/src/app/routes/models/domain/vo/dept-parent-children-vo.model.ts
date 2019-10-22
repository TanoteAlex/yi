import {DeptSimple} from "../simple/dept-simple.model";

/**
 * 部门, 所有上级部门, 直接下级部门信息
 */
export class DeptParentChildrenVo {
    dept: DeptSimple;
    parents: Array<DeptSimple>;
    children: Array<DeptSimple>;
}
