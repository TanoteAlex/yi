package com.yi.core.stats.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.commodity.CommodityEnum;
import com.yi.core.commodity.service.ICommodityService;
import com.yi.core.common.Deleted;
import com.yi.core.finance.service.ISupplierWithdrawService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.stats.StatsEnum;
import com.yi.core.stats.domain.vo.CommoditySaleVo;
import com.yi.core.stats.domain.vo.SupplierAchievementVo;
import com.yi.core.stats.domain.vo.SupplierDataVo;
import com.yi.core.stats.service.ISupplierStatsService;
import com.yi.core.supplier.SupplierEnum;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.utils.date.DateUtils;

@Service
public class SupplierStatsServiceImpl implements ISupplierStatsService {

	private static final Logger LOG = LoggerFactory.getLogger(HomepageService.class);

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private ISupplierWithdrawService supplierWithdrawService;

	@Resource
	private ISupplierService supplierService;

	@Resource
	private ICommodityService commodityService;

	/**
	 * 供应商平台统计
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierDataVo getSupplierData() {
		// 订单状态 2-已付款待发货 3-已发货待收货 4-已收货已完成
		Integer[] state = new Integer[] { OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode(), OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode(), OrderEnum.ORDER_STATE_COMPLETED.getCode() };
		int waitDeliveryNum = saleOrderService.countByDeletedAndState(Deleted.DEL_FALSE, OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());
		int withdrawNum = supplierWithdrawService.countByState(SupplierEnum.APPLY_STATE_WAIT_GRANT.getCode());
		int supplierNum = supplierService.getSupplierNum();
		int shelfCommodityNum = commodityService.countByDeletedAndStateAndShelf(Deleted.DEL_FALSE, CommodityEnum.AUDIT_STATE_PASS.getCode(),
				CommodityEnum.SHELF_STATE_ON.getCode());
		int waitAuditNum = commodityService.countByDeletedAndStateAndShelf(Deleted.DEL_FALSE, CommodityEnum.AUDIT_STATE_WAIT.getCode(), CommodityEnum.SHELF_STATE_ON.getCode());
		BigDecimal waitSettlement = supplierWithdrawService.waitSettlement(SupplierEnum.APPLY_STATE_WAIT_GRANT.getCode());

		Pageable pageable = PageRequest.of(0, 10);
		Date endDate = new Date();
		Date startDate = DateUtils.addDays(endDate, -29);
		List<SupplierAchievementVo> supplierAchievementVos = new ArrayList<>();
		List<CommoditySaleVo> commoditySales = new ArrayList<>();
		List<Object[]> supplierAchievements = saleOrderService.getSupplierAchievements(pageable, startDate, endDate, state);
		List<Object[]> tmpCommoditySales = saleOrderService.getCommoditySalesByDate(pageable, startDate, endDate, state);
		if (CollectionUtils.isNotEmpty(supplierAchievements)) {
			for (Object[] objects : supplierAchievements) {
				if (ArrayUtils.isNotEmpty(objects)) {
					SupplierAchievementVo supplierAchievementVo = new SupplierAchievementVo();
					supplierAchievementVo.setSupplierName(objects[0] != null ? objects[0].toString() : "");
					supplierAchievementVo.setSaleAmount(new BigDecimal(objects[1] != null ? objects[1].toString() : "0.00"));
					supplierAchievementVo.setOrderNum(Integer.valueOf(objects[2] != null ? objects[2].toString() : "0"));
					supplierAchievementVos.add(supplierAchievementVo);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(tmpCommoditySales)) {
			for (Object[] tmp : tmpCommoditySales) {
				if (ArrayUtils.isNotEmpty(tmp)) {
					CommoditySaleVo commoditySaleVo = new CommoditySaleVo();
					commoditySaleVo.setCommodityName(tmp[0] != null ? tmp[0].toString() : "");
					commoditySaleVo.setSaleNum(Integer.valueOf(tmp[1] != null ? tmp[1].toString() : "0"));
					commoditySaleVo.setSaleAmount(new BigDecimal(tmp[2] != null ? tmp[2].toString() : "0.00"));
					commoditySales.add(commoditySaleVo);
				}
			}
		}
		SupplierDataVo supplierDataVo = new SupplierDataVo();
		supplierDataVo.setWaitDeliveryNum(waitDeliveryNum);
		supplierDataVo.setWithdrawNum(withdrawNum);
		supplierDataVo.setSupplierNum(supplierNum);
		supplierDataVo.setShelfCommodityNum(shelfCommodityNum);
		supplierDataVo.setWaitAuditNum(waitAuditNum);
		supplierDataVo.setWaitSettlement(Optional.ofNullable(waitSettlement).orElse(BigDecimal.ZERO));
		supplierDataVo.setCommoditySales(commoditySales);
		supplierDataVo.setSupplierAchievements(supplierAchievementVos);
		return supplierDataVo;
	}

}
