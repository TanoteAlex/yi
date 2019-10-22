/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.attachment.domain.vo;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.common.FileType;
import com.yi.core.common.ObjectInfo;
import com.yi.core.common.ObjectType;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class AttachmentVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 附件id
	 */
	@Max(9999999999L)
	private Integer attachId;
	/**
	 * 业务对象类型(REST——STYLE)
	 */
	private ObjectType objectType;
	/**
	 * 访问路径
	 */
	@Length(max = 64)
	private String filePath;
	/**
	 * 业务对象id
	 */
	@Max(9999999999L)
	private Integer objectId;
	/**
	 * 所属业务对象路径
	 */
	@Length(max = 64)
	private String objectPath;
	/**
	 * 附件名称
	 */
	@Length(max = 64)
	private String fileName;
	/**
	 * 扩展文件名
	 */
	@Length(max = 64)
	private String fileExt;
	/**
	 * 文件类型(文档.压缩.图片,视频,其他)
	 */
	private FileType fileType;
	/**
	 * 文件大小
	 */

	private int fileSize;
	/**
	 * 文件存储系统标识
	 */
	@Length(max = 64)
	private String fsGuid;
	/**
	 * 上传时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 上传人id
	 */
	@Max(9999999999L)
	private Integer userId;
	/**
	 * 长传人
	 */
	@Length(max = 255)
	private String userName;
	/**
	 * 描述
	 */
	@Length(max = 255)
	private String description;
	/**
	 * 供应商（supplier表ID）
	 */
	private Integer supplierId;
	/**
	 * 供应商名称
	 */
	private String supplierName;
	// columns END

	/**
	 * 附件URL
	 */
	private String url;

	/**
	 * 附件缩略图, 可能为NULL
	 */
	private String thumbnailUrl;
	/**
	 * 所属业务要素信息, 该属性只在query方法获取
	 */
	private Collection<ObjectInfo> objectInfos;

	public Integer getAttachId() {
		return this.attachId;
	}

	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}

	public ObjectType getObjectType() {
		return this.objectType;
	}

	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getObjectId() {
		return this.objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getObjectPath() {
		return this.objectPath;
	}

	public void setObjectPath(String objectPath) {
		this.objectPath = objectPath;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExt() {
		return this.fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public FileType getFileType() {
		return this.fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public int getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getFsGuid() {
		return this.fsGuid;
	}

	public void setFsGuid(String fsGuid) {
		this.fsGuid = fsGuid;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public Collection<ObjectInfo> getObjectInfos() {
		return objectInfos;
	}

	public void setObjectInfos(Collection<ObjectInfo> objectInfos) {
		this.objectInfos = objectInfos;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}