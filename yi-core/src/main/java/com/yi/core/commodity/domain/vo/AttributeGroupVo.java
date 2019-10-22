/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.vo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.simple.AttributeSimple;
import com.yi.core.commodity.domain.simple.CategorySimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 属性组
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class AttributeGroupVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 属性组ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 商品分类（category表ID）
	 */
	private CategorySimple category;
	/**
	 * 编码
	 */
	@Length(max = 16)
	private String groupNo;
	/**
	 * 名称
	 */
	@Length(max = 64)
	@NotBlank
	private String groupName;
	/**
	 * 图片
	 */
	@Deprecated
	@Length(max = 127)
	private String imgPath;

	/**
	 * 属性显示方式（1纯文字2纯图片3文字+图片）
	 */
	private Integer showMode;

	/**
	 * 排序
	 */
	@Max(127)
	private int sort;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date delTime;
	/**
	 * 属性集合
	 */
	private List<AttributeSimple> attributes;

	// columns END
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public List<AttributeSimple> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeSimple> attributes) {
		this.attributes = attributes;
	}

	public CategorySimple getCategory() {
		return category;
	}

	public void setCategory(CategorySimple category) {
		this.category = category;
	}

	public Integer getShowMode() {
		return showMode;
	}

	public void setShowMode(Integer showMode) {
		this.showMode = showMode;
	}
}