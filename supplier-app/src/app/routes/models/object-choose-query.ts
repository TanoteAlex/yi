export class ChooseQueryBase {
    queryText: string;
}

export class ObjectChooseQuery extends ChooseQueryBase {
    userId: number;
    deptId: number;
    roleType: SearchRole;
    state: string;
}

export class SearchRole {
    //条件1
    condition1: boolean;
    //条件2
    condition2: boolean;
}

export class DimensionChooseQuery extends ChooseQueryBase {
    categoryId: number;
    parentId: number;
    dimensionId: number;
}


