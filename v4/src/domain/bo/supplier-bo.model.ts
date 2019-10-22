export class SupplierBo {
    /**
     * 供应商ID
     */
    id: number;
    /**
     * GUID
     */
    guid: string;
    /**
     * 用户（user表ID）
     */
    userId: number;
    /**
     * 供应商编号
     */
    supplierNo: string;
    /**
     * 供应商名称
     */
    supplierName: string;
    /**
     * 手机号码
     */
    phone: string;
    /**
     * 密码
     */
    password: string;
    /**
     * 状态（0可用，1禁用）
     */
    state: boolean;
    /**
     * 联系人
     */
    contact: string;
    /**
     * 联系人电话
     */
    contactPhone: string;
    /**
     * 省
     */
    province: string;
    /**
     * 市
     */
    city: string;
    /**
     * 区
     */
    district: string;
    /**
     * 详细地址
     */
    address: string;
    /**
     * 备注
     */
    remark: string;
    /**
     * 创建时间
     */
    createTime: string;
    /**
     * 删除（0否1是）
     */
    deleted: boolean;
    /**
     * 删除时间
     */
    delTime: string;
}
