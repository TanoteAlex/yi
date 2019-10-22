/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.vo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.serializer.JsonTimestampSerializer;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.yihz.common.convert.domain.ListVoDomain;

/**
 * 品牌
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class BrandListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 品牌ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * 编码
	 */
	@NotBlank
	@Length(max = 16)
	private String brandNo;
	/**
	 * 品牌名
	 */
	@NotBlank
	@Length(max = 32)
	private String cnName;
	/**
	 * 英文名
	 */
	@Length(max = 32)
	private String enName;
	/**
	 * 图片路径
	 */
	@NotBlank
	@Length(max = 255)
	private String imgPath;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	private Integer state;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */

	private Date delTime;
	// columns END

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrandNo() {
		return this.brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getCnName() {
		return this.cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

}