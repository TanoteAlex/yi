export class AppendixTableBo {
    /**
     * 附件id
     */
    attachId: number;
    /**
     * 业务对象类型
     */
    objectType: string;
    /**
     * 访问路径
     */
    filePath: string;
    /**
     * 业务对象id
     */
    objectId: number;
    /**
     * 所属业务对象路径
     */
    objectPath: string;
    /**
     * 附件名称
     */
    fileName: string;
    /**
     * 扩展文件名
     */
    fileExt: string;
    /**
     * 文件类型
     */
    fileType: string;
    /**
     * 文件大小
     */
    fileSize: number;
    /**
     * 文件存储系统标识
     */
    fsGuid: string;
    /**
     * 上传时间
     */
    createTime: string;
    /**
     * 上传人id
     */
    userId: number;
    /**
     * 长传人
     */
    userName: string;
    /**
     * 描述
     */
    description: string;
}
