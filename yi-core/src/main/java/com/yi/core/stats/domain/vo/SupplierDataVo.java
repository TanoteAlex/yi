package com.yi.core.stats.domain.vo;

import java.math.BigDecimal;
import java.util.List;

public class SupplierDataVo {

	/** 待发货订单 */
	private int waitDeliveryNum;
	/** 提现申请 */
	private int withdrawNum;
	/** 供应商数量 */
	private int supplierNum;
	/** 待审核商品 */
	private int waitAuditNum;
	/** 已上架商品数量 */
	private int shelfCommodityNum;
	/** 待结算款 */
	private BigDecimal waitSettlement;

	/** 供应商业绩排行 */
	List<SupplierAchievementVo> supplierAchievements;

	/** 商品销售排行 */
	private List<CommoditySaleVo> commoditySales;

	public int getWaitDeliveryNum() {
		return waitDeliveryNum;
	}

	public void setWaitDeliveryNum(int waitDeliveryNum) {
		this.waitDeliveryNum = waitDeliveryNum;
	}

	public int getWithdrawNum() {
		return withdrawNum;
	}

	public void setWithdrawNum(int withdrawNum) {
		this.withdrawNum = withdrawNum;
	}

	public int getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(int supplierNum) {
		this.supplierNum = supplierNum;
	}

	public int getWaitAuditNum() {
		return waitAuditNum;
	}

	public void setWaitAuditNum(int waitAuditNum) {
		this.waitAuditNum = waitAuditNum;
	}

	public int getShelfCommodityNum() {
		return shelfCommodityNum;
	}

	public void setShelfCommodityNum(int shelfCommodityNum) {
		this.shelfCommodityNum = shelfCommodityNum;
	}

	public BigDecimal getWaitSettlement() {
		return waitSettlement;
	}

	public void setWaitSettlement(BigDecimal waitSettlement) {
		this.waitSettlement = waitSettlement;
	}

	public List<SupplierAchievementVo> getSupplierAchievements() {
		return supplierAchievements;
	}

	public void setSupplierAchievements(List<SupplierAchievementVo> supplierAchievements) {
		this.supplierAchievements = supplierAchievements;
	}

	public List<CommoditySaleVo> getCommoditySales() {
		return commoditySales;
	}

	public void setCommoditySales(List<CommoditySaleVo> commoditySales) {
		this.commoditySales = commoditySales;
	}

}
