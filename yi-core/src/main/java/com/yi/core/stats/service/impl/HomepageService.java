package com.yi.core.stats.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.yi.core.finance.service.ISupplierWithdrawService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.commodity.service.ICommodityService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.stats.domain.vo.CommoditySaleVo;
import com.yi.core.stats.domain.vo.DailyAddNumVo;
import com.yi.core.stats.domain.vo.MemberConsumeVo;
import com.yi.core.stats.domain.vo.PlatformDataVo;
import com.yi.core.stats.service.IHomepageService;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.utils.date.DateUtils;

/**
 * 平台数据
 * 
 * @author xuyh
 *
 */
@Service
public class HomepageService implements IHomepageService {

	private static final Logger LOG = LoggerFactory.getLogger(HomepageService.class);

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private IMemberService memberService;

	@Resource
	private ICommodityService commodityService;

	@Resource
	private ISupplierService supplierService;

	@Resource
	private ISupplierWithdrawService supplierWithdrawService;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PlatformDataVo getPlatformData() {

		// 订单状态 2-已付款待发货 3-已发货待收货 4-已收货已完成
		Integer[] state = new Integer[] { OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode(), OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode(),
				OrderEnum.ORDER_STATE_COMPLETED.getCode() };

		int todayOrderNum = saleOrderService.getOrderNumByDate(new Date());
		int yesterdayOrderNum = saleOrderService.getOrderNumByDate(DateUtils.addDays(new Date(), -1));
		int waitDeliveryNum = saleOrderService.getWaitDeliveryNum();
		int todayMemberNum = memberService.getMemberNumByDate(new Date());
		BigDecimal todayTradeAmout = saleOrderService.getTradeAmountByDate(new Date(), state);
		BigDecimal yesterdayTradeAmout = saleOrderService.getTradeAmountByDate(DateUtils.addDays(new Date(), -1), state);

		int memberNum = memberService.getMemberNum();
		int commodityNum = commodityService.getCommodityNum();
		int supplierNum = supplierService.getSupplierNum();
		int afterSaleOrderNum = saleOrderService.getAfterSaleOrderNum();
		// 查询10条
		Pageable pageable = PageRequest.of(0, 10);
		Date endDate = new Date();
		Date startDate = DateUtils.addDays(endDate, -6);
		List<Object[]> tmpMemberConsumes = saleOrderService.getMemberConsumesByDate(pageable, startDate, endDate, state);
		List<Object[]> tmpCommoditySales = saleOrderService.getCommoditySalesByDate(pageable, startDate, endDate, state);
		List<MemberConsumeVo> memberConsumes = new ArrayList<>();
		List<CommoditySaleVo> commoditySales = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(tmpMemberConsumes)) {
			for (Object[] tmp : tmpMemberConsumes) {
				if (ArrayUtils.isNotEmpty(tmp)) {
					MemberConsumeVo memberConsumeVo = new MemberConsumeVo();
					memberConsumeVo.setMemberName(tmp[0] != null ? tmp[0].toString() : "");
					memberConsumeVo.setOrderNum(Integer.valueOf(tmp[1] != null ? tmp[1].toString() : "0"));
					memberConsumeVo.setTradeAmount(new BigDecimal(tmp[2] != null ? tmp[2].toString() : "0.00"));
					memberConsumes.add(memberConsumeVo);
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
		// 整理图表数据
		List<Object[]> dailyAddMemberNums = memberService.getDailyAddNumByDate(startDate, endDate);
		List<Object[]> dailyAddOrderNums = saleOrderService.getDailyAddNumByDate(startDate, endDate);
		List<Object[]> dailyAddSupplierNums = supplierService.getDailyAddNumByDate(startDate, endDate);
		List<String> days = getEveryDay(startDate, endDate);
		SortedMap<String, DailyAddNumVo> dailyAddNumMap = new TreeMap<>();
		if (CollectionUtils.isNotEmpty(days)) {
			for (String tmpDate : days) {
				if (StringUtils.isNotBlank(tmpDate)) {
					DailyAddNumVo dailyAddNumVo = new DailyAddNumVo();
					dailyAddNumVo.setDate(tmpDate);
					dailyAddNumMap.put(tmpDate, dailyAddNumVo);
					// 会员
					if (CollectionUtils.isNotEmpty(dailyAddMemberNums)) {
						for (Object[] tmpObj : dailyAddMemberNums) {
							if (ArrayUtils.isNotEmpty(tmpObj)) {
								if (tmpDate.equals(tmpObj[0] != null ? tmpObj[0].toString() : "")) {
									if (dailyAddNumMap.containsKey(tmpDate)) {
										dailyAddNumMap.get(tmpDate).setDailyAddMemberNum(Integer.valueOf(tmpObj[1] != null ? tmpObj[1].toString() : "0"));
									}
									break;
								}

							}
						}

					}
				}
				// 订单
				if (CollectionUtils.isNotEmpty(dailyAddOrderNums)) {
					for (Object[] tmpObj : dailyAddOrderNums) {
						if (ArrayUtils.isNotEmpty(tmpObj)) {
							if (tmpDate.equals(tmpObj[0] != null ? tmpObj[0].toString() : "")) {
								if (dailyAddNumMap.containsKey(tmpDate)) {
									dailyAddNumMap.get(tmpDate).setDailyAddOrderNum(Integer.valueOf(tmpObj[1] != null ? tmpObj[1].toString() : "0"));
								}
								break;
							}
						}
					}
				}
				// 供应商
				if (CollectionUtils.isNotEmpty(dailyAddSupplierNums)) {
					for (Object[] tmpObj : dailyAddSupplierNums) {
						if (ArrayUtils.isNotEmpty(tmpObj)) {
							if (tmpDate.equals(tmpObj[0] != null ? tmpObj[0].toString() : "")) {
								if (dailyAddNumMap.containsKey(tmpDate)) {
									dailyAddNumMap.get(tmpDate).setDailyAddSupplierNum(Integer.valueOf(tmpObj[1] != null ? tmpObj[1].toString() : "0"));
								}
								break;
							}
						}
					}
				}
				// 供应商
				if (CollectionUtils.isNotEmpty(dailyAddSupplierNums)) {
					for (Object[] tmpObj : dailyAddSupplierNums) {
						if (ArrayUtils.isNotEmpty(tmpObj)) {
							if (tmpDate.equals(tmpObj[0] != null ? tmpObj[0].toString() : "")) {
								if (dailyAddNumMap.containsKey(tmpDate)) {
									dailyAddNumMap.get(tmpDate).setDailyAddSupplierNum(Integer.valueOf(tmpObj[1] != null ? tmpObj[1].toString() : "0"));
								}
								break;
							}
						}
					}
				}
			}
		}
		// 封装数据
		PlatformDataVo platformDataVo = new PlatformDataVo();
		platformDataVo.setTodayOrderNum(todayOrderNum);
		platformDataVo.setYesterdayOrderNum(yesterdayOrderNum);
		platformDataVo.setTodayMemberNum(todayMemberNum);
		platformDataVo.setWaitDeliveryNum(waitDeliveryNum);
		platformDataVo.setTodayTradeAmout(todayTradeAmout != null ? todayTradeAmout : BigDecimal.ZERO);
		platformDataVo.setYesterdayTradeAmout(yesterdayTradeAmout != null ? yesterdayTradeAmout : BigDecimal.ZERO);
		platformDataVo.setMemberNum(memberNum);
		platformDataVo.setSupplierNum(supplierNum);
		platformDataVo.setCommodityNum(commodityNum);
		platformDataVo.setAfterSaleOrderNum(afterSaleOrderNum);
		platformDataVo.setMemberConsumes(memberConsumes);
		platformDataVo.setCommoditySales(commoditySales);
		platformDataVo.setDailyAddNums(new ArrayList<>(dailyAddNumMap.values()));
		return platformDataVo;
	}

	/**
	 * 供应商数据统计
	 *
	 * @param supplierId
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PlatformDataVo getSupplierData(Integer supplierId) {

		// 订单状态 2-已付款待发货 3-已发货待收货 4-已收货已完成
		Integer[] state = new Integer[] { OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode(), OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode(),
				OrderEnum.ORDER_STATE_COMPLETED.getCode() };
		int withdrawNum = supplierWithdrawService.withdrawNum(supplierId);
		int todayOrderNum = saleOrderService.getOrderNumByDateAndSupplier(new Date(), supplierId);
		int yesterdayOrderNum = saleOrderService.getOrderNumByDateAndSupplier(DateUtils.addDays(new Date(), -1), supplierId);
		int waitDeliveryNum = saleOrderService.getWaitDeliveryNumSupplier(supplierId);
		BigDecimal todayTradeAmout = saleOrderService.getTradeAmountByDateAndSupplier(new Date(), state, supplierId);
		BigDecimal yesterdayTradeAmout = saleOrderService.getTradeAmountByDateAndSupplier(DateUtils.addDays(new Date(), -1), state, supplierId);

		int memberNum = memberService.getMemberNum();
		int commodityNum = commodityService.getCommodityNumBySupplier(supplierId);
		int supplierNum = supplierService.getSupplierNum();
		int afterSaleOrderNum = saleOrderService.getAfterSaleOrderNumBySupplier(supplierId);

		// 查询10条
		Pageable pageable = PageRequest.of(0, 10);
		Date endDate = new Date();
		Date startDate = DateUtils.addDays(endDate, -6);
		List<Object[]> tmpMemberConsumes = saleOrderService.getMemberConsumesByDateAndSupplier(pageable, startDate, endDate, state, supplierId);
		List<Object[]> tmpCommoditySales = saleOrderService.getCommoditySalesByDateAndSupplier(pageable, startDate, endDate, state, supplierId);
		List<MemberConsumeVo> memberConsumes = new ArrayList<>();
		List<CommoditySaleVo> commoditySales = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(tmpMemberConsumes)) {
			for (Object[] tmp : tmpMemberConsumes) {
				if (ArrayUtils.isNotEmpty(tmp)) {
					MemberConsumeVo memberConsumeVo = new MemberConsumeVo();
					memberConsumeVo.setMemberName(tmp[0] != null ? tmp[0].toString() : "");
					memberConsumeVo.setOrderNum(Integer.valueOf(tmp[1] != null ? tmp[1].toString() : "0"));
					memberConsumeVo.setTradeAmount(new BigDecimal(tmp[2] != null ? tmp[2].toString() : "0.00"));
					memberConsumes.add(memberConsumeVo);
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
		// 整理图表数据
		List<Object[]> dailyAddMemberNums = memberService.getDailyAddNumByDate(startDate, endDate);
		List<Object[]> dailyAddOrderNums = saleOrderService.getDailyAddNumByDateAndSupplierId(startDate, endDate, supplierId);
		List<Object[]> dailyAddSupplierNums = supplierService.getDailyAddNumByDate(startDate, endDate);
		List<String> days = getEveryDay(startDate, endDate);
		SortedMap<String, DailyAddNumVo> dailyAddNumMap = new TreeMap<>();
		if (CollectionUtils.isNotEmpty(days)) {
			for (String tmpDate : days) {
				if (StringUtils.isNotBlank(tmpDate)) {
					DailyAddNumVo dailyAddNumVo = new DailyAddNumVo();
					dailyAddNumVo.setDate(tmpDate);
					dailyAddNumMap.put(tmpDate, dailyAddNumVo);
					// 会员
					if (CollectionUtils.isNotEmpty(dailyAddMemberNums)) {
						for (Object[] tmpObj : dailyAddMemberNums) {
							if (ArrayUtils.isNotEmpty(tmpObj)) {
								if (tmpDate.equals(tmpObj[0] != null ? tmpObj[0].toString() : "")) {
									if (dailyAddNumMap.containsKey(tmpDate)) {
										dailyAddNumMap.get(tmpDate).setDailyAddMemberNum(Integer.valueOf(tmpObj[1] != null ? tmpObj[1].toString() : "0"));
									}
									break;
								}

							}
						}

					}
				}
				// 订单
				if (CollectionUtils.isNotEmpty(dailyAddOrderNums)) {
					for (Object[] tmpObj : dailyAddOrderNums) {
						if (ArrayUtils.isNotEmpty(tmpObj)) {
							if (tmpDate.equals(tmpObj[0] != null ? tmpObj[0].toString() : "")) {
								if (dailyAddNumMap.containsKey(tmpDate)) {
									dailyAddNumMap.get(tmpDate).setDailyAddOrderNum(Integer.valueOf(tmpObj[1] != null ? tmpObj[1].toString() : "0"));
								}
								break;
							}
						}

					}
				}
				// 供应商
				if (CollectionUtils.isNotEmpty(dailyAddSupplierNums)) {
					for (Object[] tmpObj : dailyAddSupplierNums) {
						if (ArrayUtils.isNotEmpty(tmpObj)) {
							if (tmpDate.equals(tmpObj[0] != null ? tmpObj[0].toString() : "")) {
								if (dailyAddNumMap.containsKey(tmpDate)) {
									dailyAddNumMap.get(tmpDate).setDailyAddSupplierNum(Integer.valueOf(tmpObj[1] != null ? tmpObj[1].toString() : "0"));
								}
								break;
							}
						}
					}
				}
			}
		}
		// 封装数据
		PlatformDataVo platformDataVo = new PlatformDataVo();
		platformDataVo.setWithdrawNum(withdrawNum);
		platformDataVo.setTodayOrderNum(todayOrderNum);
		platformDataVo.setYesterdayOrderNum(yesterdayOrderNum);
		platformDataVo.setWaitDeliveryNum(waitDeliveryNum);
		platformDataVo.setTodayTradeAmout(todayTradeAmout != null ? todayTradeAmout : BigDecimal.ZERO);
		platformDataVo.setYesterdayTradeAmout(yesterdayTradeAmout != null ? yesterdayTradeAmout : BigDecimal.ZERO);
		platformDataVo.setMemberNum(memberNum);
		platformDataVo.setCommodityNum(commodityNum);
		platformDataVo.setSupplierNum(supplierNum);
		platformDataVo.setAfterSaleOrderNum(afterSaleOrderNum);
		platformDataVo.setMemberConsumes(memberConsumes);
		platformDataVo.setCommoditySales(commoditySales);
		platformDataVo.setDailyAddNums(new ArrayList<>(dailyAddNumMap.values()));
		return platformDataVo;
	}

	/**
	 * 获取时间段的每一天
	 * 
	 * @param startDate
	 * @param endDate
	 * @return List<String>
	 */
	public List<String> getEveryDay(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		List<String> dates = new ArrayList<String>();
		// 使用给定的 Date 设置此 Calendar 的时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		dates.add(DateUtils.getFormatDate(calendar.getTime()));
		while (endDate.after(calendar.getTime())) {
			// 加天
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			dates.add(DateUtils.getFormatDate(calendar.getTime()));
		}
		return dates;
	}

}
