/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.simple;

import java.util.Date;
import java.util.Set;

import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.SimpleDomain;
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
public class AttributeSimple extends SimpleDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 属性ID
	 */
	@Max(9999999999L)
	private Integer id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 属性组（attribute_group表ID）
	 */
	@NotNull
	private AttributeGroupSimple attributeGroup;
	/**
	 * 属性名
	 */
	@Length(max = 32)
	private String attrName;
	/**
	 * 属性值
	 */
	@NotBlank
	@Length(max = 127)
	private String attrValue;
	/**
	 * 排序
	 */
	@Max(127)
	private Integer sort;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
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
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;

	/**
	 * 属性组ID
	 */
	private Integer attributeGroupId;

	/**
	 * 货品集合
	 */
	private Set<ProductSimple> products;
	// columns END

	/**
	 * 属性图片路径
	 */
	private String imgPath;

	/**
	 * 属性显示方式（1纯文字2纯图片3文字+图片）
	 */
	private Integer showMode;


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@JsonIgnore
	public AttributeGroupSimple getAttributeGroup() {
		return this.attributeGroup;
	}

	public void setAttributeGroup(AttributeGroupSimple attributeGroup) {
		this.attributeGroup = attributeGroup;
	}

	public String getAttrName() {
		return this.attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrValue() {
		return this.attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@JsonIgnore
	public Set<ProductSimple> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductSimple> products) {
		this.products = products;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getAttributeGroupId() {
		if (this.getAttributeGroup() != null) {
			attributeGroupId = this.getAttributeGroup().getId();
		}
		return attributeGroupId;
	}

	public void setAttributeGroupId(Integer attributeGroupId) {
		this.attributeGroupId = attributeGroupId;
	}

	public Integer getShowMode() {
		return showMode;
	}

	public void setShowMode(Integer showMode) {
		this.showMode = showMode;
	}
}