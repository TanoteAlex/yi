/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.yi.core.commodity.domain.entity.Commodity;

/**
 * 团购活动规则
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
public class GroupBuyActivityRule implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	* 
	*/
	@Max(9999999999L)
	private int id;
	/**
	 * 编号
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 团购活动
	 */
	@NotNull
	private GroupBuyActivity groupBuyActivity;
	/**
	 * 商品ID
	 */
	@NotNull
	private Commodity commodity;
	/**
	 * 商品名称
	 */
	@Length(max = 128)
	private String commodityName;
	/**
	 * 成团数量
	 */
	@NotNull
	@Max(9999999999L)
	private int groupBuyQuantity;
	/**
	 * 注水数
	 */
	@Max(9999999999L)
	private int injectWater;
	// columns END

	public GroupBuyActivityRule() {
	}

	public GroupBuyActivityRule(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public String getCommodityName() {
		return this.commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public int getGroupBuyQuantity() {
		return this.groupBuyQuantity;
	}

	public void setGroupBuyQuantity(int groupBuyQuantity) {
		this.groupBuyQuantity = groupBuyQuantity;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public int getInjectWater() {
		return this.injectWater;
	}

	public void setInjectWater(int injectWater) {
		this.injectWater = injectWater;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COMMODITY_ID", nullable = false) })
	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "GROUP_BUY_ACTIVITY_ID", nullable = false, insertable = false, updatable = false) })
	public GroupBuyActivity getGroupBuyActivity() {
		return groupBuyActivity;
	}

	public void setGroupBuyActivity(GroupBuyActivity groupBuyActivity) {
		this.groupBuyActivity = groupBuyActivity;
	}

}