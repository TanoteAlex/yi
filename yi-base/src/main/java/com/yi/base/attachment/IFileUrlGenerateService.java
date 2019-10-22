package com.yi.base.attachment;

/**
 * 系统文件存储URL生成服务接口
 */
public interface IFileUrlGenerateService {
    /**
     * 根据附件ID生成永久有效的下载URL, 文件扩展名称主要用于设置附件的 MIME 类型<br>
     * 下载时显示用户上传的原始文件名
     *
     * @param attachId 附件ID
     * @param width    缩图缩放后的宽度,允许NULL
     * @param height   缩图缩放后的高度,允许NULL
     * @return
     */
    String genAttachmentUrl(int attachId, Integer width, Integer height);

    /**
     * 根据文件GUID和文件扩展名称生成永久有效的下载URL, 文件扩展名称主要用于设置附件的 MIME 类型<br>
     * 根据不同的文件实现, 可能不支持显示用户上传的原始文件名
     *
     * @param guid    文件存储GUID
     * @param fileExt 文件扩展名称, 允许为NULL
     * @return
     */
    String genFileUrl(String guid, String fileExt);

    /**
     * 根据文件GUID和文件扩展名称生成图片按指定大小裁剪的永久有效下载URL, 文件扩展名称主要用于设置附件的 MIME 类型<br>
     * 根据不同的文件实现, 可能不支持显示用户上传的原始文件名
     *
     * @param guid    文件存储GUID
     * @param fileExt 文件扩展名称, 允许为NULL
     * @param width   缩图缩放后的宽度,允许NULL
     * @param height  缩图缩放后的高度,允许NULL
     * @return
     */
    String genImageFileUrl(String guid, String fileExt, Integer width, Integer height);
}
