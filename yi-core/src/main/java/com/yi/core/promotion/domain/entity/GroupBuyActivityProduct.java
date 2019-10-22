/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;

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
import com.yi.core.commodity.domain.entity.Product;

/**
 * 团购产品
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
public class GroupBuyActivityProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	* 
	*/
	@Max(9999999999L)
	private int id;
	/**
	* 
	*/
	@Length(max = 32)
	private String guid;
	/**
	 * 团购活动
	 */
	@NotNull
	private GroupBuyActivity groupBuyActivity;
	/**
	 * 商品编号
	 */
	@NotNull
	private Commodity commodity;
	/**
	 * 货品编号
	 */
	@NotNull
	private Product product;
	/**
	 * 团购价格
	 */
	@NotNull
	private BigDecimal groupBuyPrice;
	/**
	 * 成团人数
	 */
	@NotNull
	@Max(9999999999L)
	private Integer groupBuyQuantity;
	/**
	 * 备货数
	 */
	@NotNull
	@Max(9999999999L)
	private int stockUpQuantity;
	/**
	 * 注水数
	 */
	@Max(9999999999L)
	private int injectWater;
	/**
	 * 已购买数
	 */
	@NotNull
	@Max(9999999999L)
	private int boughtQuantity;
	// columns END

	public GroupBuyActivityProduct() {
	}

	public GroupBuyActivityProduct(int id) {
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

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public BigDecimal getGroupBuyPrice() {
		return groupBuyPrice;
	}

	public void setGroupBuyPrice(BigDecimal groupBuyPrice) {
		this.groupBuyPrice = groupBuyPrice;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public int getStockUpQuantity() {
		return this.stockUpQuantity;
	}

	public void setStockUpQuantity(int stockUpQuantity) {
		this.stockUpQuantity = stockUpQuantity;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public int getInjectWater() {
		return this.injectWater;
	}

	public void setInjectWater(int injectWater) {
		this.injectWater = injectWater;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public int getBoughtQuantity() {
		return this.boughtQuantity;
	}

	public void setBoughtQuantity(int boughtQuantity) {
		this.boughtQuantity = boughtQuantity;
	}

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "GROUP_BUY_ACTIVITY_ID", nullable = false) })
	public GroupBuyActivity getGroupBuyActivity() {
		return groupBuyActivity;
	}

	public void setGroupBuyActivity(GroupBuyActivity groupBuyActivity) {
		this.groupBuyActivity = groupBuyActivity;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COMMODITY_ID", nullable = false) })
	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "PRODUCT_ID", nullable = false) })
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getGroupBuyQuantity() {
		return groupBuyQuantity;
	}

	public void setGroupBuyQuantity(Integer groupBuyQuantity) {
		this.groupBuyQuantity = groupBuyQuantity;
	}
}