package com.yi.base.attachment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by aidy on 2017/7/13.
 */
public interface IAttachmentStorageExecutor {

	/**
	 * 保存输入流内容到附件存储系统, 返回存储的UUID
	 *
	 * @param inputStream
	 * @return 附件内容的存储UUID
	 * @throws IOException
	 */
	String save(InputStream inputStream, String fileExt) throws IOException;
	
	String saveImage(InputStream inputStream) throws IOException;

	String genFileUrl(String queryPath, String uuid, String fileExt) throws IOException;

	InputStream open(String uuid) throws IOException;

	File getLocalFileNameAndUuid(String uuid);

	String getLocalFilePath(String uuid);
}
