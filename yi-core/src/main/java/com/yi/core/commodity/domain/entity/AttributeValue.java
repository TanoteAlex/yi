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
//import javax.persistence.*;
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
// * * 属性值
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
//public class AttributeValue implements java.io.Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	// columns START
//	/**
//	 * 属性值ID
//	 */
//	@Max(9999999999L)
//	private int id;
//	/**
//	 * GUID
//	 */
//	@Length(max = 32)
//	private String guid;
//	/**
//	 * 属性值
//	 */
//	@Length(max = 32)
//	private String attrValue;
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
//
//	@NotNull
//	private AttributeName attributeName;
//	// columns END
//	
//	/**
//	 * 属性名集合
//	 */
//	private Set<AttributeName> attributeNames;
//
//	public AttributeValue() {
//	}
//
//	public AttributeValue(int id) {
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
//	@Column( length = 32)
//	public String getGuid() {
//		return this.guid;
//	}
//
//	public void setGuid(String guid) {
//		this.guid = guid;
//	}
//
//	@Column( length = 32)
//	public String getAttrValue() {
//		return this.attrValue;
//	}
//
//	public void setAttrValue(String attrValue) {
//		this.attrValue = attrValue;
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
//	@Column( length = 19)
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
//	@Column( length = 19)
//	public Date getDelTime() {
//		return this.delTime;
//	}
//
//	public void setDelTime(Date delTime) {
//		this.delTime = delTime;
//	}
//
//	@ManyToMany(cascade = CascadeType.MERGE)
//	@JoinTable(name = "attribute", joinColumns = @JoinColumn(name = "ATTRIBUTE_VALUE_ID"), inverseJoinColumns = @JoinColumn(name = "ATTRIBUTE_NAME_ID"))
//	public Set<AttributeName> getAttributeNames() {
//		return attributeNames;
//	}
//
//	public void setAttributeNames(Set<AttributeName> attributeNames) {
//		this.attributeNames = attributeNames;
//	}
//
//	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
//	@JoinColumns({ @JoinColumn(name = "ATTR_NAME_ID", nullable = false) })
//	public AttributeName getAttributeName() {
//		return attributeName;
//	}
//
//	public void setAttributeName(AttributeName attributeName) {
//		this.attributeName = attributeName;
//	}
//}