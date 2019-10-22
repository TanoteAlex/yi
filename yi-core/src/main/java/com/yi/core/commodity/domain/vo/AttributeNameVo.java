package com.yi.core.commodity.domain.vo;///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.core.commodity.domain.vo;
//
//import java.util.Date;
//import java.util.Set;
//
//import javax.validation.constraints.Max;
//import javax.validation.constraints.NotNull;
//
//import org.hibernate.validator.constraints.Length;
//
//import com.yi.core.commodity.domain.entity.AttributeGroup;
//import com.yi.core.commodity.domain.entity.AttributeValue;
//import com.yihz.common.convert.domain.VoDomain;
//
///**
// * 属性名
// * 
// * @author lemosen
// * @version 1.0
// * @since 1.0
// *
// */
//public class AttributeNameVo extends VoDomain implements java.io.Serializable {
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
//	@NotNull
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
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getGuid() {
//		return guid;
//	}
//
//	public void setGuid(String guid) {
//		this.guid = guid;
//	}
//
//	public AttributeGroup getAttributeGroup() {
//		return attributeGroup;
//	}
//
//	public void setAttributeGroup(AttributeGroup attributeGroup) {
//		this.attributeGroup = attributeGroup;
//	}
//
//	public String getAttrName() {
//		return attrName;
//	}
//
//	public void setAttrName(String attrName) {
//		this.attrName = attrName;
//	}
//
//	public Integer isState() {
//		return state;
//	}
//
//	public void setState(Integer state) {
//		this.state = state;
//	}
//
//	public Date getCreateTime() {
//		return createTime;
//	}
//
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
//
//	public Integer getDeleted() {
//		return deleted;
//	}
//
//	public void setDeleted(Integer deleted) {
//		this.deleted = deleted;
//	}
//
//	public Date getDelTime() {
//		return delTime;
//	}
//
//	public void setDelTime(Date delTime) {
//		this.delTime = delTime;
//	}
//
//	public Set<AttributeValue> getAttributeValues() {
//		return attributeValues;
//	}
//
//	public void setAttributeValues(Set<AttributeValue> attributeValues) {
//		this.attributeValues = attributeValues;
//	}
//}