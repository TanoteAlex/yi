package com.yi.base.attachment.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yi.base.attachment.IAttachmentStorageExecutor;
import com.yi.base.utils.ThumbnailatorUtils;
import com.yihz.common.utils.ValueUtils;
import com.yihz.common.utils.file.PlaceholderUtils;
import com.yihz.common.utils.image.ImageConstans;
import com.yihz.common.utils.image.ImageConverter;

/**
 * 基于本地共享文件系统的服务存储服务
 */
@Service
public class AttachmentLocalStorageExecutor implements IAttachmentStorageExecutor {

	@Value("${YI_HOME}/uploads")
	private String rootPath;
	@Value("${yi.attachment.domain:http://localhost:8080}")
	private String domainName;
	private String useRootPath;

	public AttachmentLocalStorageExecutor() {

	}

	public AttachmentLocalStorageExecutor(String fly_starHome) {
		this.rootPath = rootPath.replace("${YI_HOME}", fly_starHome);
	}

	@Override
	public String save(InputStream inputStream, String fileExt) throws IOException {
		String uuid;
		File file;

		while (true) {
			uuid = ValueUtils.nextUUID().toString();
			file = new File(getLocalFileName(uuid));
			if (!file.exists()) {
				break;
			}
		}

		OutputStream out = FileUtils.openOutputStream(file, false);
		try {
			IOUtils.copy(inputStream, out);

			// 生成 大图小图中图
			ThumbnailatorUtils.compressBySmallSize(getLocalFileName(uuid), null);
			ThumbnailatorUtils.compressByMiddleSize(getLocalFileName(uuid), null);
			ThumbnailatorUtils.compressByLargeSize(getLocalFileName(uuid), null);

			return uuid;

		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	@Override
	public String saveImage(InputStream inputStream) throws IOException {
		String uuid;
		File file;
		String filePath;
		File fileThumbnail;

		while (true) {
			uuid = ValueUtils.nextUUID().toString();
			filePath = getLocalFileName(uuid);
			file = new File(getLocalFileName(uuid));
			if (!file.exists()) {
				break;
			}
		}
		OutputStream out = FileUtils.openOutputStream(file, false);
		try {
			IOUtils.copy(inputStream, out);
		} finally {
			IOUtils.closeQuietly(out);
		}
		// 缩略图
		fileThumbnail = new File(filePath + ImageConstans.FALG_SMALL);
		try {
			byte[] imageBytes = ImageConverter.compressImageByte(new FileInputStream(file), 64, 64, "jpg");
			FileUtils.writeByteArrayToFile(fileThumbnail, imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return uuid;
	}

	@Override
	public InputStream open(String uuid) throws IOException {
		File file = new File(getLocalFileName(uuid));
		return new FileInputStream(file);
	}

	@Override
	public File getLocalFileNameAndUuid(String uuid) {
		return new File(getLocalFileName(uuid));
	}

	@Override
	public String getLocalFilePath(String uuid) {
		return getLocalFileName(uuid);
	}

	@Override
	public String genFileUrl(String queryPath, String guid, String fileExt) {
		StringBuilder buf = makeBasePath(queryPath);
		buf.append(guid);

		if (fileExt != null) {
			buf.append(".").append(fileExt);
		}
		return buf.toString();
	}

	private String getLocalFileName(String uuid) {
		if (useRootPath == null) {
			useRootPath = PlaceholderUtils.resolvePlaceholders(rootPath);
		}

		StringBuilder buf = new StringBuilder();
		buf.append(useRootPath).append('/');
		buf.append(uuid.substring(0, 2)).append('/');
		buf.append(uuid.substring(2, 5)).append('/');
		buf.append(uuid.substring(5, 8)).append('/');
		buf.append(uuid);
		return buf.toString();
	}

	private StringBuilder makeBasePath(String basePath) {
		StringBuilder buf = new StringBuilder(256);
		if (StringUtils.isNotBlank(domainName)) {
			buf.append(domainName);
		}
		if (StringUtils.isNotBlank(basePath)) {
			buf.append(basePath);
		}
		return buf;
	}

}
