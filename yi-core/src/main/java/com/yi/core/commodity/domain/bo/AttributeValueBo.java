package com.yi.core.commodity.domain.bo;///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.core.commodity.domain.bo;
//
//import java.util.Date;
//import java.util.Set;
//
//import javax.validation.constraints.Max;
//import javax.validation.constraints.NotNull;
//
//import org.hibernate.validator.constraints.Length;
//
//import com.yi.core.commodity.domain.entity.AttributeName;
//import com.yihz.common.convert.domain.BoDomain;
//
///**
// * 属性值
// * 
// * @author lemosen
// * @version 1.0
// * @since 1.0
// *
// */
//public class AttributeValueBo extends BoDomain implements java.io.Serializable {
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
//	/**
//	 * 属性名集合
//	 */
//	private Set<AttributeName> attributeNames;
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
//	public String getAttrValue() {
//		return attrValue;
//	}
//
//	public void setAttrValue(String attrValue) {
//		this.attrValue = attrValue;
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
//	public Set<AttributeName> getAttributeNames() {
//		return attributeNames;
//	}
//
//	public void setAttributeNames(Set<AttributeName> attributeNames) {
//		this.attributeNames = attributeNames;
//	}
//}