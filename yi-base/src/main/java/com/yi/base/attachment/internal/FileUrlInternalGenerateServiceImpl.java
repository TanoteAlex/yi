package com.yi.base.attachment.internal;

import com.yi.base.attachment.IFileUrlGenerateService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 基于自身的附件URL生成服务<br>
 * 附件URL路径为: http://xxx/files/attach/${id}<br>
 * 文件URL路径为: http://xxx/files/data/${guid}.${ext}
 */
@Service
public class FileUrlInternalGenerateServiceImpl implements IFileUrlGenerateService{
    private final static Logger LOG = LoggerFactory.getLogger(FileUrlInternalGenerateServiceImpl.class);

    @Value("${yi.attachment.domain:http://localhost:8080}")
    private String domainName;

    @Value("${yi.attachment.fileAttachPath:/files/attach/}")
    private String fileAttachPath = "/files/attach/";

    @Value("${yi.attachment.fileDataPath:/files/data/}")
    private String fileDataPath = "/files/data/";

    public FileUrlInternalGenerateServiceImpl() {
        LOG.debug("附件URL路径生成参数, domainName={}, fileDownloadPath={}", domainName, fileDataPath);

        this.domainName = StringUtils.trimToNull(domainName);
        if (domainName != null) {
            if (StringUtils.endsWith(domainName, "/")) {
                domainName = domainName.substring(0, domainName.length() - 1);
            }
        }
    }

    @Override
    public String genAttachmentUrl(int attachId, Integer width, Integer height) {
        StringBuilder buf = makeBasePath(fileAttachPath);
        buf.append(attachId);

        if (width != null && width > 0) {
            buf.append("_w").append(width);
        }

        if (height != null && height > 0) {
            buf.append("_h").append(height);
        }

        return buf.toString();
    }

    @Override
    public String genFileUrl(String guid, String fileExt) {
        StringBuilder buf = makeBasePath(fileDataPath);
        buf.append(guid);

        if (fileExt != null) {
            buf.append(".").append(fileExt);
        }
        return buf.toString();
    }

    @Override
    public String genImageFileUrl(String guid, String fileExt, Integer width, Integer height) {
        StringBuilder buf = makeBasePath(fileDataPath);
        buf.append(guid);

        if (width != null && width > 0) {
            buf.append("_w").append(width);
        }

        if (height != null && height > 0) {
            buf.append("_h").append(height);
        }

        if (fileExt != null) {
            buf.append(".").append(fileExt);
        }

        return buf.toString();
    }

    private StringBuilder makeBasePath(String basePath) {
        StringBuilder buf = new StringBuilder(256);
        if (domainName != null) {
            buf.append(domainName);
        }

        buf.append(basePath);
        return buf;
    }

}
