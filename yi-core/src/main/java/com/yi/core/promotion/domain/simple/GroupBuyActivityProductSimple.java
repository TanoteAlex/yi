/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.domain.simple;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yi.core.promotion.domain.listVo.GroupBuyOrderListVo;
import com.yihz.common.convert.domain.SimpleDomain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 团购商品
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public class GroupBuyActivityProductSimple extends SimpleDomain {

	/**
	 * 
	 */
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
	 * 团购活动编号
	 */
	@NotNull
	private GroupBuyActivitySimple groupBuyActivity;
	/**
	 * 商品编号
	 */
	@NotNull
	private CommoditySimple commodity;
	/**
	 * 货品编号
	 */
	@NotNull
	private ProductSimple product;
	/**
	 * 团购价格
	 */
	@NotNull
	@Max(9999999999L)
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

	/**
	 * 开团集合
	 */
	private List<GroupBuyOrderListVo> openGroupBuys = new ArrayList<>();

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@JsonIgnore
	public GroupBuyActivitySimple getGroupBuyActivity() {
		return groupBuyActivity;
	}

	public void setGroupBuyActivity(GroupBuyActivitySimple groupBuyActivity) {
		this.groupBuyActivity = groupBuyActivity;
	}

	public CommoditySimple getCommodity() {
		return commodity;
	}

	public void setCommodity(CommoditySimple commodity) {
		this.commodity = commodity;
	}

	public ProductSimple getProduct() {
		return product;
	}

	public void setProduct(ProductSimple product) {
		this.product = product;
	}

	public BigDecimal getGroupBuyPrice() {
		return this.groupBuyPrice;
	}

	public void setGroupBuyPrice(BigDecimal groupBuyPrice) {
		this.groupBuyPrice = groupBuyPrice;
	}

	public int getStockUpQuantity() {
		return this.stockUpQuantity;
	}

	public void setStockUpQuantity(int stockUpQuantity) {
		this.stockUpQuantity = stockUpQuantity;
	}

	public int getInjectWater() {
		return this.injectWater;
	}

	public void setInjectWater(int injectWater) {
		this.injectWater = injectWater;
	}

	public int getBoughtQuantity() {
		return this.boughtQuantity;
	}

	public void setBoughtQuantity(int boughtQuantity) {
		this.boughtQuantity = boughtQuantity;
	}

	public Integer getGroupBuyQuantity() {
		return groupBuyQuantity;
	}

	public void setGroupBuyQuantity(Integer groupBuyQuantity) {
		this.groupBuyQuantity = groupBuyQuantity;
	}

	public List<GroupBuyOrderListVo> getOpenGroupBuys() {
		return openGroupBuys;
	}

	public void setOpenGroupBuys(List<GroupBuyOrderListVo> openGroupBuys) {
		this.openGroupBuys = openGroupBuys;
	}
}