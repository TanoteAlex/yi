package com.yi.core.attachment.service;

import com.yi.core.attachment.domain.entity.Attachment;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.common.FileType;
import com.yi.core.common.ObjectType;
import com.yihz.common.persistence.Pagination;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public interface IAttachmentService {

	/**
	 * 分页查询附件列表, 所有查询条件都放在 data 这个MAP中, 当前支持objectPath,fileTypes,fileName三个参数
	 *
	 * @param query
	 * @return
	 */
	// Page<AttachmentVo> query(Pagination<AttachmentVo> query);

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return
	 */
	Page<Attachment> query(Pagination<Attachment> query);

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return
	 */
	Page<AttachmentVo> queryVo(Pagination<Attachment> query);

	/**
	 * 获取附件信息
	 *
	 * @param attachId
	 * @return
	 */
	Attachment getById(int attachId);

	/**
	 * 获取附件信息VO
	 *
	 * @param attachId
	 * @param includeObjectInfos
	 *            是否解析所属业务要素信息
	 * @return
	 */
	AttachmentVo getVoById(int attachId, boolean includeObjectInfos);

	/**
	 * 将附件转换成VO对象, 用于附件上传但没有保存到数据库的场景
	 *
	 * @param attachment
	 * @return
	 */
	AttachmentVo toVo(Attachment attachment);

	/**
	 * 根据文件扩展名获取对应的文件类型
	 *
	 * @param fileExt
	 * @return
	 */
	FileType getFileType(String fileExt);

	/**
	 * 查找某个某个业务要素目录下的附件数量
	 *
	 * @param objectPath
	 * @param includeSubPath
	 *            是否包括子目录的文件
	 * @return
	 */
	int getCount(String objectPath, boolean includeSubPath);

	/**
	 * 查找某个某个业务要素目录下的附件列表
	 *
	 * @param objectPath
	 * @param includeSubPath
	 *            是否包括子目录的文件
	 * @return
	 */
	List<AttachmentVo> findByPath(String objectPath, boolean includeSubPath);

	/**
	 * 保存某个业务要素目录的附件列表
	 *
	 * @param objectPath
	 *            保存的对象路径
	 * @param attachments
	 *            附件列表
	 * @param deleteOldFiles
	 *            是否删除目录已有的附件
	 */
	void save(String objectPath, Collection<AttachmentVo> attachments, boolean deleteOldFiles);

	/**
	 * 增加某个业务要素目录的附件
	 *
	 * @param attachment
	 *            附件
	 */
	AttachmentVo add(AttachmentVo attachment);

	/**
	 * 删除某个附件
	 *
	 * @param attachmentId
	 */
	void remove(int attachmentId);

	/**
	 * 删除某个某个业务要素目录下的附件列表
	 *
	 * @param objectPath
	 * @param includeSubPath
	 *            是否包括子目录的文件
	 */
	void removeByPath(String objectPath, boolean includeSubPath);

	/**
	 * 打开附件对应内容的输入流
	 *
	 * @param attachmentId
	 * @return
	 * @throws IOException
	 */
	InputStream getInputStream(int attachmentId) throws IOException;

	/**
	 * 删除指定业务对象的附件
	 * 
	 * @param objectId
	 */
	void deleteByObjectId(Integer objectId);

	/**
	 * 删除指定业务对象和业务类型的附件
	 * 
	 * @param objectId
	 * @param objectType
	 */
	void deleteByObjectIdAndObjectType(Integer objectId, ObjectType objectType);

	/**
	 * 批量保存 业务对象附件
	 * 
	 * @param objectId
	 */
	void saveAll(List<AttachmentVo> list);

	/**
	 * 查找某个某个业务要素目录下的附件列表
	 *
	 * @param objectId
	 * @return List
	 */
	List<AttachmentVo> findByObjectIdAndObjectType(Integer objectId, ObjectType objectType);

	/**
	 * 删除集合
	 * 
	 * @param list
	 */
	void deleteList(List<AttachmentVo> list);
}
