
/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.cms.domain.entity;

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
public class RecommendCommodityId implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private int recommendId;
	private int commodityId;

	public RecommendCommodityId() {
	}

	public RecommendCommodityId(int recommendId, int commodityId) {
		this.recommendId = recommendId;
		this.commodityId = commodityId;
	}

	public void setRecommendId(int value) {
		this.recommendId = value;
	}

	@Column(name = "RECOMMEND_ID", unique = false, nullable = false, length = 10)
	public int getRecommendId() {
		return this.recommendId;
	}

	public void setCommodityId(int value) {
		this.commodityId = value;
	}

	@Column(name = "COMMODITY_ID", unique = false, nullable = false, length = 10)
	public int getCommodityId() {
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