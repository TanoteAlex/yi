package com.yi.core.commodity.domain.entity;///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.core.commodity.domain.entity;
//
//import static javax.persistence.GenerationType.IDENTITY;
//
//import java.util.Date;
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinColumns;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.NotNull;
//
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.validator.constraints.Length;
//
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.yihz.common.json.serializer.JsonTimestampSerializer;
//
///**
// * 属性名
// * 
// * @author lemosen
// * @version 1.0
// * @since 1.0
// *
// */
//@Entity
//@DynamicInsert
//@DynamicUpdate
//@Table
//public class AttributeName implements java.io.Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	// columns START
//	/**
//	 * 属性名ID
//	 */
//	@Max(9999999999L)
//	private int id;
//	/**
//	 * GUID
//	 */
//	@Length(max = 32)
//	private String guid;
//	/**
//	 * 属性组（attribute_group表ID）
//	 */
//	@NotNull
//	private AttributeGroup attributeGroup;
//	/**
//	 * 属性名
//	 */
//	@Length(max = 32)
//	private String attrName;
//	/**
//	 * 状态（0启用1禁用）
//	 */
//	private Integer state;
//	/**
//	 * 创建时间
//	 */
//	private Date createTime;
//	/**
//	 * 删除（0否1是）
//	 */
//	private Integer deleted;
//	/**
//	 * 删除时间
//	 */
//	private Date delTime;
//	// columns END
//
//	private Set<AttributeValue> attributeValues;
//
//	public AttributeName() {
//	}
//
//	public AttributeName(int id) {
//		this.id = id;
//	}
//
//	@Id
//	@GeneratedValue(strategy = IDENTITY)
//	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
//	public int getId() {
//		return this.id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
//	public String getGuid() {
//		return this.guid;
//	}
//
//	public void setGuid(String guid) {
//		this.guid = guid;
//	}
//
//	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "ATTRIBUTE_GROUP_ID", nullable = false) })
//	public AttributeGroup getAttributeGroup() {
//		return attributeGroup;
//	}
//
//	public void setAttributeGroup(AttributeGroup attributeGroup) {
//		this.attributeGroup = attributeGroup;
//	}
//
//	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
//	public String getAttrName() {
//		return this.attrName;
//	}
//
//	public void setAttrName(String attrName) {
//		this.attrName = attrName;
//	}
//
//	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 0)
//	public Integer getState() {
//		return this.state;
//	}
//
//	public void setState(Integer state) {
//		this.state = state;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@JsonSerialize(using = JsonTimestampSerializer.class)
//	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
//	public Date getCreateTime() {
//		return this.createTime;
//	}
//
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
//
//	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 0)
//	public Integer getDeleted() {
//		return this.deleted;
//	}
//
//	public void setDeleted(Integer deleted) {
//		this.deleted = deleted;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@JsonSerialize(using = JsonTimestampSerializer.class)
//	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
//	public Date getDelTime() {
//		return this.delTime;
//	}
//
//	public void setDelTime(Date delTime) {
//		this.delTime = delTime;
//	}
//
//	@ManyToMany(cascade = CascadeType.MERGE)
//	@JoinTable(name = "attribute", joinColumns = @JoinColumn(name = "ATTRIBUTE_NAME_ID"), inverseJoinColumns = @JoinColumn(name = "ATTRIBUTE_VALUE_ID"))
//	public Set<AttributeValue> getAttributeValues() {
//		return attributeValues;
//	}
//
//	public void setAttributeValues(Set<AttributeValue> attributeValues) {
//		this.attributeValues = attributeValues;
//	}
//
//}