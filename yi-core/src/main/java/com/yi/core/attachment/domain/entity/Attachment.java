/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.attachment.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.common.FileType;
import com.yi.core.common.ObjectType;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 附件
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class Attachment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 附件id
	 */
	@Max(9999999999L)
	private Integer attachId;
	/**
	 * 业务对象类型
	 */
	private ObjectType objectType;
	/**
	 * 访问路径
	 */
	@Length(max = 255)
	private String filePath;
	/**
	 * 业务对象id
	 */
	@Max(9999999999L)
	private Integer objectId;
	/**
	 * 所属业务对象路径
	 */
	@Length(max = 255)
	private String objectPath;
	/**
	 * 附件名称
	 */
	@Length(max = 255)
	private String fileName;
	/**
	 * 扩展文件名
	 */
	@Length(max = 64)
	private String fileExt;
	/**
	 * 文件类型
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
	private Date createTime;
	/**
	 * 上传人id
	 */
	@Max(9999999999L)
	private Integer userId;
	/**
	 * 长传人
	 */
	@Length(max = 127)
	private String userName;
	/**
	 * 供应商（supplier表ID）
	 */
	private Integer supplierId;
	/**
	 * 供应商名称
	 */
	private String supplierName;

	/**
	 * 描述
	 */
	@Length(max = 255)
	private String description;
	// columns END

	public Attachment() {
	}

	public Attachment(Integer attachId) {
		this.attachId = attachId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public Integer getAttachId() {
		return this.attachId;
	}

	public void setAttachId(Integer attachId) {
		this.attachId = attachId;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true)
	public ObjectType getObjectType() {
		return this.objectType;
	}

	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Integer getObjectId() {
		return this.objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getObjectPath() {
		return this.objectPath;
	}

	public void setObjectPath(String objectPath) {
		this.objectPath = objectPath;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(unique = false, nullable = true, length = 64)
	public String getFileExt() {
		return this.fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true)
	public FileType getFileType() {
		return this.fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	@Column(unique = false, nullable = true, length = 11)
	public int getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	@Column(unique = false, nullable = true, length = 64)
	public String getFsGuid() {
		return this.fsGuid;
	}

	public void setFsGuid(String fsGuid) {
		this.fsGuid = fsGuid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(unique = false, nullable = true, length = 64)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}