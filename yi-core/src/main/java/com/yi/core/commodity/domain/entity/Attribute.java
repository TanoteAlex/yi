/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 
 * 属性表
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
public class Attribute implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 属性ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;


	/**
	 * 属性组（attribute_group表表ID）
	 */

	@NotNull
	private AttributeGroup attributeGroup;
	private Integer attributeGroupId;
	/**
	 * 属性名 冗余
	 */
	@NotBlank
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
	private int sort;
	/**
	 * 创建时间
	 */
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
	private Date delTime;
	// columns END
	/**
	 * 货品集合
	 */
	private Set<Product> products;

	/**
	 * 属性图片路径
	 */
	private String imgPath;
	
	/**
	 * 属性显示方式（1纯文字2纯图片3文字+图片）
	 */
	private Integer showMode;


	public Attribute() {
	}

	public Attribute(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, updatable = false, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}



	@Column(unique = false, nullable = true, updatable = false, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ATTRIBUTE_GROUP_ID", nullable = false) })
	public AttributeGroup getAttributeGroup() {
		return attributeGroup;
	}

	public void setAttributeGroup(AttributeGroup attributeGroup) {
		this.attributeGroup = attributeGroup;
	}

	@Column(unique = false, nullable = false, length = 32)
	public String getAttrName() {
		return this.attrName;
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

	@Column(unique = false, nullable = true, length = 3)
	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, updatable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = true, length = 127)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "product_attribute", joinColumns = @JoinColumn(name = "ATTRIBUTE_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getShowMode() {
		return showMode;
	}

	public void setShowMode(Integer showMode) {
		this.showMode = showMode;
	}

	@Transient
	public Integer getAttributeGroupId() {
		return attributeGroupId;
	}

	public void setAttributeGroupId(Integer attributeGroupId) {
		this.attributeGroupId = attributeGroupId;
	}
	}