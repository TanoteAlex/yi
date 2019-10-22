/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.cms.domain.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.yi.core.commodity.domain.entity.Commodity;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class RecommendCommodity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private RecommendCommodityId recommendCommodityId;
	private int sort;

	private Commodity commodity;

	private Recommend recommend;

	public RecommendCommodity() {
	}

	public RecommendCommodity(RecommendCommodityId recommendCommodityId) {
		this.recommendCommodityId = recommendCommodityId;
	}

	@EmbeddedId
	public RecommendCommodityId getRecommendCommodityId() {
		return this.recommendCommodityId;
	}

	public void setRecommendCommodityId(RecommendCommodityId recommendCommodityId) {
		this.recommendCommodityId = recommendCommodityId;
	}

	@Column(unique = false, nullable = true, length = 3)
	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COMMODITY_ID", nullable = false, insertable = false, updatable = false) })
	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "RECOMMEND_ID", nullable = false, insertable = false, updatable = false) })
	public Recommend getRecommend() {
		return recommend;
	}

	public void setRecommend(Recommend recommend) {
		this.recommend = recommend;
	}

}