export class RescSimple {
    /**
     * 资源ID
     */
    id: number;
    /**
     * GUID
     */
    guid: string;
    /**
     * 上级资源
     */
    parentId: number;
    /**
     * 资源编码
     */
    code: string;
    /**
     * 资源名称
     */
    name: string;
    /**
     * 资源路径
     */
    url: string;
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
