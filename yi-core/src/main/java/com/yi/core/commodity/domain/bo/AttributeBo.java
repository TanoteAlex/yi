/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.bo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 
 * 属性
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class AttributeBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 属性ID
	 */
	private int id;
	/**
	 * GUID
	 */
	private String guid;
	/**
	 * 属性组（attribute_group表表ID）
	 */
	private AttributeGroupBo attributeGroup;
	/**
	 * 属性名 冗余
	 */
	private String attrName;
	/**
	 * 属性值
	 */
	private String attrValue;
	/**
	 * 排序
	 */
	private int sort;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END
	/**
	 * 货品集合
	 */
	private Set<ProductBo> products = new HashSet<>();

	/**
	 * 属性图片路径
	 */
	private String imgPath;

	/**
	 * 属性显示方式（1纯文字2纯图片3文字+图片）
	 */
	private Integer showMode;

	public AttributeBo() {
	}

	public AttributeBo(int id) {
		this.id = id;
	}

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

	public AttributeGroupBo getAttributeGroup() {
		return attributeGroup;
	}

	public void setAttributeGroup(AttributeGroupBo attributeGroup) {
		this.attributeGroup = attributeGroup;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
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

	public Set<ProductBo> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductBo> products) {
		this.products = products;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getShowMode() {
		return showMode;
	}

	public void setShowMode(Integer showMode) {
		this.showMode = showMode;
	}
}