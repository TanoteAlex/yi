package com.yi.core.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.domain.entity.Coupon;
import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.basic.domain.entity.Area;
import com.yi.core.basic.domain.entity.IntegralCash;
import com.yi.core.basic.domain.entity.Region;
import com.yi.core.basic.service.IIntegralCashService;
import com.yi.core.cart.domain.vo.CartVo;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.CommodityLevelDiscount;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.service.IProductService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.ShippingAddress;
import com.yi.core.member.domain.vo.MemberLevelListVo;
import com.yi.core.member.service.IMemberLevelService;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.service.IFreightTemplateConfigService;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.exception.BusinessException;

/**
 * 订单基础服务类
 * 
 * @author xuyh
 *
 */
@Service
public class BaseOrderService implements InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(BaseOrderService.class);

	@Resource
	private IMemberLevelService memberLevelService;

	@Resource
	private IFreightTemplateConfigService freightTemplateService;

	@Resource
	private IStockService stockService;

	@Resource
	private IProductService productService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IIntegralCashService integralCashService;

	/**
	 * 通过购物车的数据创建订单
	 * 
	 * @param orderMap
	 * @param member
	 * @param cartVo
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<Integer, SaleOrder> createOrderByCart(Map<Integer, SaleOrder> orderMap, Member member, Product product,
			Supplier supplier, CartVo cartVo) {
		// 校验购物车
		if (member == null || product == null || supplier == null || cartVo == null) {
			LOG.error("cartVo参数为空");
			throw new BusinessException("购物车数据不能为空");
		}
		// 校验库存
		boolean flag = stockService.checkStock(product.getId(), cartVo.getQuantity());
		if (!flag) {
			LOG.error("{}库存不足", product.getId());
			throw new BusinessException(product.getProductShortName() + " 库存不足");
		}
		// 拆单
		if (orderMap.containsKey(supplier.getId())) {
			SaleOrder tmpOrder = orderMap.get(supplier.getId());
			// 订单项
			SaleOrderItem tmpItem = new SaleOrderItem();
			// 计算会员价格 四舍五入
			BigDecimal tmpPrice = calculatePriceByLevel(product, member).setScale(2, BigDecimal.ROUND_UP);
			// 小计金额= 价格*数量
			BigDecimal subTotal = tmpPrice.multiply(BigDecimal.valueOf(cartVo.getQuantity()));
			tmpItem.setSaleOrder(tmpOrder);
			tmpItem.setProduct(product);
//			tmpItem.setMember(member);
//			tmpItem.setSupplier(supplier);
			tmpItem.setPrice(tmpPrice);
			tmpItem.setQuantity(cartVo.getQuantity());
			tmpItem.setSubtotal(subTotal);
			tmpOrder.getSaleOrderItems().add(tmpItem);
			tmpOrder.setOrderAmount(tmpOrder.getOrderAmount().add(subTotal));
			tmpOrder.setPayAmount(tmpOrder.getPayAmount().add(subTotal));
		} else {
			SaleOrder tmpOrder = new SaleOrder();
			// 订单项
			SaleOrderItem tmpItem = new SaleOrderItem();
			// 计算会员价格 四舍五入
			BigDecimal tmpPrice = calculatePriceByLevel(product, member).setScale(2, BigDecimal.ROUND_UP);
			// 小计金额= 价格*数量
			BigDecimal subTotal = tmpPrice.multiply(BigDecimal.valueOf(cartVo.getQuantity()));
			tmpItem.setSaleOrder(tmpOrder);
			tmpItem.setCommodity(product.getCommodity());
			tmpItem.setProduct(product);
			tmpItem.setMember(member);
			tmpItem.setSupplier(supplier);
			tmpItem.setPrice(tmpPrice);
			tmpItem.setQuantity(cartVo.getQuantity());
			tmpItem.setSubtotal(subTotal);
			// 订单赋值订单项
			tmpOrder.getSaleOrderItems().add(tmpItem);
			tmpOrder.setOrderAmount(subTotal);// 订单金额
			tmpOrder.setPayAmount(subTotal);// 支付金额
			tmpOrder.setFreight(BigDecimal.ZERO);
			tmpOrder.setCouponAmount(BigDecimal.ZERO);
			tmpOrder.setVoucherAmount(BigDecimal.ZERO);
			tmpOrder.setBalance(BigDecimal.ZERO);
			orderMap.put(supplier.getId(), tmpOrder);
		}
		return orderMap;
	}

	/**
	 * 计算运费-- 根据整单计算运费
	 * 
	 * @param saleOrder
	 * @param address
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal calculateFreightByOrder(SaleOrder saleOrder, String province, String city) {
		if (saleOrder == null || CollectionUtils.isEmpty(saleOrder.getSaleOrderItems())
				|| StringUtils.isAnyBlank(province, city)) {
			LOG.error("calculateFreightByOrder， 计算运费参数为空");
			return BigDecimal.ZERO;
		}
		// 运费 = 统一运费 + 模板运费
		BigDecimal freight = BigDecimal.ZERO;
		for (SaleOrderItem tmpItem : saleOrder.getSaleOrderItems()) {
			if (tmpItem != null) {
				// 计算 统一运费
				if (CommodityEnum.FREIGHT_SET_UNIFIED.getCode()
						.equals(tmpItem.getProduct().getCommodity().getFreightSet())) {
					freight = freight.add(tmpItem.getProduct().getCommodity().getUnifiedFreight());
					// 计算模板运费
				} else {
					BigDecimal tplFreight = freightTemplateService.calculateFreight(
							tmpItem.getProduct().getCommodity().getFreightTemplate(), tmpItem, province, city);
					freight = freight.add(tplFreight);
				}
			}
		}
		return freight;
	}

	/**
	 * 根据等级 计算会员价格
	 * 
	 * @param product
	 * @param member
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal calculatePriceByLevel(Product product, Member member) {
		if (product == null || member == null) {
			return BigDecimal.ZERO;
		}
		// 如果该商品设置自定义的会员等级折扣 采用商品的
		if (CollectionUtils.isNotEmpty(product.getCommodity().getCommodityLevelDiscounts())) {
			for (CommodityLevelDiscount tmp : product.getCommodity().getCommodityLevelDiscounts()) {
				if (tmp != null && tmp.getDiscount() != null && member.getMemberLevel() != null
						&& tmp.getMemberLevel().getId() == member.getMemberLevel().getId()) {
					return product.getCurrentPrice().multiply(tmp.getDiscount()).divide(BigDecimal.valueOf(100));
				}
			}
		}
		// 如果商品没有设置自定义的会员等级折扣 则采用平台设置的会员折扣计算价格
		List<MemberLevelListVo> memberLevels = memberLevelService.queryAll();
		if (CollectionUtils.isNotEmpty(memberLevels)) {
			for (MemberLevelListVo tmp : memberLevels) {
				if (tmp != null && tmp.getDiscount() != null && member.getMemberLevel() != null
						&& tmp.getId() == member.getMemberLevel().getId()) {
					return product.getCurrentPrice().multiply(tmp.getDiscount()).divide(BigDecimal.valueOf(100));
				}
			}
		}
		// 如果都没有 返回商品 现价
		return product.getCurrentPrice();
	}

	/**
	 * 计算优惠券
	 * 
	 * @param saleOrder
	 *            订单
	 * @param usedCoupons
	 *            锁定的优惠券
	 * @param usableCoupons
	 *            可用优惠券
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CouponReceive> calculateCoupons(SaleOrder saleOrder, Set<CouponReceive> lockCoupons,
			List<CouponReceive> usableCoupons) {
		List<CouponReceive> useCoupons = new ArrayList<>();
		if (saleOrder != null && CollectionUtils.isNotEmpty(usableCoupons)) {
			// 遍历可用的优惠券 查询最优的优惠券
			boolean checked = true;
			for (CouponReceive tmpReceive : usableCoupons) {
				if (tmpReceive != null && !lockCoupons.contains(tmpReceive)) {
					// 满减卷
					if (ActivityEnum.COUPON_TYPE_COUPON.getCode().equals(tmpReceive.getCoupon().getCouponType())) {
						// 使用条件-无限制 面值小于支付金额
						if (ActivityEnum.USE_CONDITION_TYPE_UNLIMITED.getCode()
								.equals(tmpReceive.getCoupon().getUseConditionType())
								&& tmpReceive.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
							if (checked) {
								tmpReceive.setChecked(Boolean.TRUE);
								checked = false;
							} else {
								tmpReceive.setChecked(Boolean.FALSE);
							}
							useCoupons.add(tmpReceive);
							// 使用条件-满XX元可用
						} else if (ActivityEnum.USE_CONDITION_TYPE_FULL_MONEY.getCode()
								.equals(tmpReceive.getCoupon().getUseConditionType())) {
							// 满足优惠券条件且面值小于支付金额
							if (checkBuyAmount(saleOrder.getSaleOrderItems(), tmpReceive.getCoupon())
									&& tmpReceive.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
								if (checked) {
									tmpReceive.setChecked(Boolean.TRUE);
									checked = false;
								} else {
									tmpReceive.setChecked(Boolean.FALSE);
								}
								useCoupons.add(tmpReceive);
							}
							// 使用条件-满XX件可用
						} else if (ActivityEnum.USE_CONDITION_TYPE_FULL_PIECE.getCode()
								.equals(tmpReceive.getCoupon().getUseConditionType())) {
							if (checkBuyQuantity(saleOrder.getSaleOrderItems(), tmpReceive.getCoupon())
									&& tmpReceive.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
								if (checked) {
									tmpReceive.setChecked(Boolean.TRUE);
									checked = false;
								} else {
									tmpReceive.setChecked(Boolean.FALSE);
								}
								useCoupons.add(tmpReceive);
							}
						}
					}
				}
			}
		}
		return useCoupons;
	}

	/**
	 * 计算代金券
	 * 
	 * @param saleOrder
	 *            订单
	 * @param  usedCoupons
	 *            锁定的代金券
	 * @param usableCoupons
	 *            可用代金券
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CouponReceive> calculateVouchers(SaleOrder saleOrder, Set<CouponReceive> lockVouchers,
			List<CouponReceive> usableCoupons) {
		List<CouponReceive> useVouchers = new ArrayList<>();
		if (saleOrder != null && CollectionUtils.isNotEmpty(usableCoupons)) {
			Set<JSONObject> optimalSet = new HashSet<>();
			// 遍历可用的代金券 查询最优的代金券组合
			BigDecimal payAmount = saleOrder.getPayAmount();
			for (CouponReceive voucher1 : usableCoupons) {
				if (voucher1 != null && !lockVouchers.contains(voucher1)) {
					// 临时存储符合的代金券集合
					JSONObject jsonObject = new JSONObject();
					List<CouponReceive> optimalList = new ArrayList<>();
					BigDecimal optimalAmount = voucher1.getParValue();
					optimalList.add(voucher1);
					for (CouponReceive voucher2 : usableCoupons) {
						if (voucher2 != null && !lockVouchers.contains(voucher2)
								&& voucher1.getId() != voucher2.getId()) {
							if (optimalAmount.add(voucher2.getParValue()).compareTo(payAmount) < 0) {
								optimalAmount = optimalAmount.add(voucher2.getParValue());
								optimalList.add(voucher2);
							}
						}
					}
					if (payAmount.compareTo(optimalAmount) >= 0) {
						jsonObject.put("optimalAmount", optimalAmount);
						jsonObject.put("optimalList", optimalList);
						optimalSet.add(jsonObject);
					}
				}
			}
			BigDecimal tmpAmount = BigDecimal.ZERO;
			for (JSONObject tmpJson : optimalSet) {
				if (tmpJson != null) {
					if (tmpJson.getBigDecimal("optimalAmount").compareTo(tmpAmount) > 0) {
						tmpAmount = tmpJson.getBigDecimal("optimalAmount");
						useVouchers = (List<CouponReceive>) tmpJson.get("optimalList");
					}
				}
			}
			useVouchers.forEach(tmp -> {
				tmp.setChecked(Boolean.TRUE);
			});
		}
		return useVouchers;
	}

	/**
	 * 检查优惠券
	 * 
	 * @param saleOrder
	 * @param usableCoupons
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CouponReceive> checkCoupons(SaleOrder saleOrder, List<CouponReceive> useCoupons) {
		List<CouponReceive> usedCoupons = new ArrayList<>();
		if (saleOrder != null && CollectionUtils.isNotEmpty(useCoupons)) {
			// 检查优惠券 是否符合使用要求
			for (CouponReceive pageCouponReceive : useCoupons) {
				if (pageCouponReceive != null && pageCouponReceive.getId() > 0) {
					CouponReceive dbCoupon = couponReceiveService.getById(pageCouponReceive.getId());
					if (dbCoupon != null && pageCouponReceive.isChecked()) {
						// 使用条件-无限制 面值小于支付金额
						if (ActivityEnum.USE_CONDITION_TYPE_UNLIMITED.getCode()
								.equals(dbCoupon.getCoupon().getUseConditionType())
								&& dbCoupon.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
							usedCoupons.add(dbCoupon);
							break;
							// 使用条件-满XX元可用
						} else if (ActivityEnum.USE_CONDITION_TYPE_FULL_MONEY.getCode()
								.equals(dbCoupon.getCoupon().getUseConditionType())) {
							// 满足条件且面值小于支付金额
							if (checkBuyAmount(saleOrder.getSaleOrderItems(), dbCoupon.getCoupon())
									&& dbCoupon.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
								usedCoupons.add(dbCoupon);
								break;
							}
							// 使用条件-满XX件可用
						} else if (ActivityEnum.USE_CONDITION_TYPE_FULL_PIECE.getCode()
								.equals(dbCoupon.getCoupon().getUseConditionType())) {
							// 满足条件且面值小于支付金额
							if (checkBuyQuantity(saleOrder.getSaleOrderItems(), dbCoupon.getCoupon())
									&& dbCoupon.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
								usedCoupons.add(dbCoupon);
								break;
							}
						}
					}
				}
			}
		}
		return usedCoupons;
	}

	/**
	 * 检查代金券
	 * 
	 * @param saleOrder
	 * @param usableCoupons
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CouponReceive> checkVouchers(SaleOrder saleOrder, List<CouponReceive> useVouchers) {
		if (saleOrder != null && CollectionUtils.isNotEmpty(useVouchers)) {
			// 检查代金券 是否符合使用要求
			List<CouponReceive> usedVouchers = new ArrayList<>();
			BigDecimal voucherAmount = BigDecimal.ZERO;
			for (CouponReceive pageVoucher : useVouchers) {
				if (pageVoucher != null && pageVoucher.getId() > 0) {
					CouponReceive dbVoucher = couponReceiveService.getById(pageVoucher.getId());
					if (dbVoucher != null && pageVoucher.isChecked()) {
						voucherAmount = voucherAmount.add(dbVoucher.getParValue());
						usedVouchers.add(dbVoucher);
					}
				}
			}
			// 如果代金券金额 > 支付金额 代金券停止使用
			if (voucherAmount.compareTo(saleOrder.getPayAmount()) > 0) {
				usedVouchers.clear();
			}
			return usedVouchers;
		}
		return null;
	}

	/**
	 * 遍历订单项 查看购买数量是否符合优惠券要求
	 *
	 * @param saleOrderItems
	 * @param coupon
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkBuyQuantity(List<SaleOrderItem> saleOrderItems, Coupon coupon) {
		boolean flag = false;
		if (CollectionUtils.isEmpty(saleOrderItems) || coupon == null) {
			return flag;
		}
		// 统计订单项中商品购买数量
		Map<Integer, BigDecimal> buyQuantity = new HashMap<>();
		saleOrderItems.forEach(tmp -> {
			if (tmp != null) {
				if (buyQuantity.containsKey(tmp.getProduct().getCommodity().getId())) {
					buyQuantity.put(tmp.getProduct().getCommodity().getId(), buyQuantity
							.get(tmp.getProduct().getCommodity().getId()).add(BigDecimal.valueOf(tmp.getQuantity())));
				} else {
					buyQuantity.put(tmp.getProduct().getCommodity().getId(), BigDecimal.valueOf(tmp.getQuantity()));
				}
			}
		});
		// 检验是否符合优惠券的使用条件
		for (Commodity tmp : coupon.getCommodities()) {
			if (tmp != null) {
				if (buyQuantity.containsKey(tmp.getId())
						&& buyQuantity.get(tmp.getId()).intValue() >= coupon.getFullQuantity()) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 遍历订单项 查看购买金额是否符合优惠券要求
	 *
	 * @param saleOrderItems
	 * @param coupon
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkBuyAmount(List<SaleOrderItem> saleOrderItems, Coupon coupon) {
		boolean flag = false;
		if (CollectionUtils.isEmpty(saleOrderItems) || coupon == null) {
			return flag;
		}
		// 统计订单项中商品购买金额
		Map<Integer, BigDecimal> buyAmount = new HashMap<>();
		saleOrderItems.forEach(tmp -> {
			if (tmp != null) {
				if (buyAmount.containsKey(tmp.getProduct().getCommodity().getId())) {
					buyAmount.put(tmp.getProduct().getCommodity().getId(),
							buyAmount.get(tmp.getProduct().getCommodity().getId()).add(tmp.getSubtotal()));
				} else {
					buyAmount.put(tmp.getProduct().getCommodity().getId(), tmp.getSubtotal());
				}
			}
		});
		// 检验是否符合优惠券的使用条件
		for (Commodity tmp : coupon.getCommodities()) {
			if (tmp != null) {
				if (buyAmount.containsKey(tmp.getId())
						&& buyAmount.get(tmp.getId()).compareTo(coupon.getFullMoney()) >= 0) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 检查是否在销售地区内
	 * 
	 * @param commodity
	 * @param address
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkSaleRegion(Commodity commodity, ShippingAddress address) {
		if (commodity != null && CollectionUtils.isNotEmpty(commodity.getRegions()) && address != null) {
			for (Region region : commodity.getRegions()) {
				if (Optional.ofNullable(region.getArea()).map(Area::getName).orElse("").equals(address.getProvince())
						|| Optional.ofNullable(region.getArea()).map(Area::getAreaCode).orElse("")
								.equals(address.getProvince())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取会员可用的积分抵现金额
	 * 
	 * @param member
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal getUsableIntegralCashAmount(Member member) {
		if (member != null) {
			IntegralCash dbIntegralCash = integralCashService.getLatestIntegralCash();
			if (dbIntegralCash != null) {
				if (member.getAccount().getAvailableIntegral() >= dbIntegralCash.getIntegral().intValue()) {
					int usableIntegral = member.getAccount().getAvailableIntegral()
							/ dbIntegralCash.getIntegral().intValue();
					if(usableIntegral>=dbIntegralCash.getLimitCash().intValue()){
						usableIntegral= dbIntegralCash.getLimitCash().intValue();
					}
					return dbIntegralCash.getCash().multiply(BigDecimal.valueOf(usableIntegral));
				}
			}
		}
		return BigDecimal.ZERO;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}

}
