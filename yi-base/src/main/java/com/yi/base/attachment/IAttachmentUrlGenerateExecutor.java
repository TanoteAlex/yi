package com.yi.base.attachment;

/**
 * 附件URL生成服务接口
 */
public interface IAttachmentUrlGenerateExecutor {

    /**
     * 根据附件GUID和文件扩展名称生成永久有效的下载URL, 文件扩展名称主要用于设置附件的 MIME 类型
     *
     * @param guid    附件存储GUID
     * @param fileExt 文件扩展名称, 允许为NULL
     * @return
     */
    String generateUrl(String guid, String fileExt);

    /**
     * 根据附件GUID和文件扩展名称生成图片按指定大小裁剪的永久有效下载URL, 文件扩展名称主要用于设置附件的 MIME 类型
     *
     * @param guid    附件存储GUID
     * @param fileExt 文件扩展名称, 允许为NULL
     * @param width   缩图缩放后的宽度,允许NULL
     * @param height  缩图缩放后的高度,允许NULL
     * @return
     */
    String genImageUrl(String guid, String fileExt, Integer width, Integer height);
}
