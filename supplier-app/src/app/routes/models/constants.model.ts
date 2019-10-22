/**
 * 实际值,目标值没有数据时显示的内容
 */
export const EMPTY_VALUE = "-";

/**
 * 用于设置各选项的值
 */
export const SUCCESS = 'SUCCESS';
export const FAILURE = 'FAILURE';
export const INFO = 'INFO';
export const WARNING = 'WARNING';

//业务要素类型
export const USER = "USER";

export const ROOT_LAYER_CODE = '000';

/****
 * 业务要素类型
 * @type {{user: string}}
 */
export const OBJECT_TYPES = {
    dept: "DEPT",
    user: "USER"
};


/***
 * 业务要素类型
 * @type {{text: string; value: string}[]}
 */
export const OBJECT_TYPE_ITEMS: Array<{ text: string, value: string }> = [
    {text: "部门", value: 'DEPT'},
    {text: "用户", value: 'USER'},
];

export const UNIT: Array<{ text: string, value: string }> = [
    {text: "数量", value: 'NUMBER'},
    {text: "金额", value: 'UNIT_MONEY'},
    {text: "百分比", value: 'UNIT_PERCENT'},
    {text: "千万", value: 'UNIT_THOUSAND_MONEY'},
    {text: "亿", value: 'UNIT_HUNDRED_MILLION_MONEY'},
    {text: "等级", value: 'SCALE'},
    {text: "公斤", value: 'UNIT_KILOGRAM'},
];
