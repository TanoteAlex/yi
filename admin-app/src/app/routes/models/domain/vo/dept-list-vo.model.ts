import {DeptSimple} from "../simple/dept-simple.model";
import {UserSimple} from "../simple/user-simple.model";

export class DeptListVo {
    /**
     * 对象路径
     */
    objectPath: string;
    /**
     * GUID
     */
    guid: string;

    /**
     * 部门员工数量
     */
    userCount: number;
    /**
     * 下级子部门数量
     */
    deptCount: number;

    /**
     * 部门ID
     */
    deptId: number;
    /**
     * 上级部门
     */
    parentDept: DeptSimple;
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
     * 排序
     */
    showOrder: number;
    /**
     * 部门员工仅可见自己
     */
    outerDept: boolean = false;
    /**
     * 部门是否可见
     */
    visibled: boolean = true;
    /***
     * 节点编码
     */
    layerCode: string;
    /**
     * 状态
     */
    enabled: boolean = true;

    /***
     * 是否正式库数据
     */
    official: boolean = true;
    /**
     * 是否删除
     */
    del: boolean;

    /**
     * 部门主管
     */
    managers: Array<UserSimple>;
}
