
/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
@Embeddable
public class SalesAreaCommodityId implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer salesAreaId;
	private java.lang.Integer commodityId;

	public SalesAreaCommodityId() {
	}

	public SalesAreaCommodityId(java.lang.Integer salesAreaId, java.lang.Integer commodityId) {
		this.salesAreaId = salesAreaId;
		this.commodityId = commodityId;
	}

	public void setSalesAreaId(java.lang.Integer value) {
		this.salesAreaId = value;
	}

	@Column(name = "SALES_AREA_ID", unique = false, nullable = false, length = 10)
	public java.lang.Integer getSalesAreaId() {
		return this.salesAreaId;
	}

	public void setCommodityId(java.lang.Integer value) {
		this.commodityId = value;
	}

	@Column(name = "COMMODITY_ID", unique = false, nullable = false, length = 10)
	public java.lang.Integer getCommodityId() {
		return this.commodityId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}
}