package com.yi.base.attachment.internal;

import com.yi.base.attachment.IAttachmentUrlGenerateExecutor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 基于自身的附件URL生成服务, URL路径为: http://xxx/rest/file/fileDownload/${guid}
 */
@Service
public class AttachmentUrlInternalGenerateExecutor implements IAttachmentUrlGenerateExecutor{

    private final static Logger LOG = LoggerFactory.getLogger(AttachmentUrlInternalGenerateExecutor.class);

    @Value("${yi.attachment.domain:http://localhost:8080}")
    private String domainName;

    @Value("${yi.attachment.fileDownloadPath:/rest/file/fileDownload/}")
    private String fileDownloadPath;

    public AttachmentUrlInternalGenerateExecutor() {
        LOG.debug("附件URL路径生成参数, domainName={}, fileDownloadPath={}", domainName, fileDownloadPath);

        this.domainName = StringUtils.trimToNull(domainName);
        if (domainName != null) {
            if (StringUtils.endsWith(domainName, "/")) {
                domainName = domainName.substring(0, domainName.length() - 1);
            }
        }

        this.fileDownloadPath = StringUtils.trimToNull(fileDownloadPath);
//        Assert.notNull(fileDownloadPath, "fileDownloadPath必须设置");

        // 确保路径前后都有 "/" 字符串, 方便后续的路径拼接
        if (!StringUtils.startsWith(fileDownloadPath, "/")) {
            fileDownloadPath = "/" + fileDownloadPath;
        }
    }

    @Override
    public String generateUrl(String guid, String fileExt) {
        StringBuilder buf = makeBasePath();
        buf.append("?uuid="+guid);

        if (fileExt != null) {
            buf.append("&ext="+fileExt);
            if(fileExt.toLowerCase().equals("jpg") || fileExt.toLowerCase().equals("png") || fileExt.toLowerCase().equals("jpeg")){
                buf.append("&ct=image/webp");
            }else{
                buf.append("&ct=application/octet-stream");
                buf.append("&d=true");
            }
        }
        return buf.toString();
    }

    @Override
    public String genImageUrl(String guid, String fileExt, Integer width, Integer height) {
        StringBuilder buf = makeBasePath();
        buf.append(guid);

        if (width != null && width > 0) {
            buf.append("_w").append(width);
        }

        if (height != null && height > 0) {
            buf.append("_h").append(height);
        }

        if (fileExt != null) {
            if (!fileExt.startsWith(".")) {
                buf.append(".");
            }
            buf.append(fileExt);
        }

        return buf.toString();
    }

    private StringBuilder makeBasePath() {
        StringBuilder buf = new StringBuilder(256);
        if (domainName != null) {
            buf.append(domainName);
        }

        buf.append(fileDownloadPath);
        return buf;
    }

}
