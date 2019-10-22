package com.yi.core.payment.weChat;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.gift.service.IGiftBagService;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberCommissionService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.entity.AfterSaleOrder;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.service.IAfterSaleOrderService;
import com.yi.core.order.service.IOrderLogService;
import com.yi.core.order.service.IPayRecordService;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.payment.PaymentUtils;
import com.yi.core.promotion.service.IGroupBuyOrderService;
import com.yi.core.supplier.service.ISupplierAccountService;
import com.yihz.common.utils.date.DateUtils;

/**
 * 微信回调
 * 
 * @author xuyh
 *
 */
@Component
public class WeChatNotifyService {

	private static final Logger LOG = LoggerFactory.getLogger(WeChatNotifyService.class);

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private IAfterSaleOrderService afterSaleOrderService;

	@Resource
	private IOrderLogService orderLogService;

	@Resource
	private IPayRecordService payRecordService;

	@Resource
	private IStockService stockService;

	@Resource
	private IAccountRecordService accountRecordService;

	@Resource
	private ISupplierAccountService supplierAccountService;

	@Resource
	private IGiftBagService giftBagService;

	@Resource
	private IMemberCommissionService memberCommissionService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IGroupBuyOrderService groupBuyOrderService;

	@Resource
	private IAccountService accountService;

	/**
	 * 微信支付回调接口
	 * 
	 * @return
	 */
	@Transactional
	public String payNotify(SortedMap<String, String> resultMap, String partner_key, OrderEnum paymentChannel) throws Exception {
		if (MapUtils.isEmpty(resultMap)) {
			LOG.error("微信支付回调异常，回传数据为空：{}", resultMap);
			return PaymentUtils.returnXml(WeChatConfig.FAIL, WeChatConfig.FAIL_MSG);
		}
		// 检验签名
		if (PaymentUtils.verifySign(resultMap, partner_key)) {
			if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code")) && WeChatConfig.SUCCESS.equals(resultMap.get("result_code"))) {
				// 获取订单
				List<SaleOrder> dbSaleOrders = saleOrderService.getPayOrderByIds(resultMap.get("attach"));
				// 核对支付金额
				BigDecimal payAmount = this.calculatePayAmount(dbSaleOrders);
				if (payAmount.multiply(BigDecimal.valueOf(100)).compareTo(new BigDecimal(resultMap.get("total_fee"))) != 0) {
					LOG.error("weChatNotifyForMp，微信回调接口订单金额{}和后台计算订单金额{}不匹配，请核实", resultMap.get("total_fee"), payAmount);
				}
				// 处理数据
				afterPay(dbSaleOrders, resultMap, paymentChannel);
				return PaymentUtils.returnXml(WeChatConfig.SUCCESS, WeChatConfig.SUCCESS_MSG);
			}
		} else {
			LOG.error("weChat Notify -> weChat Notify fail 校验签名未通过");
			return PaymentUtils.returnXml(WeChatConfig.FAIL, WeChatConfig.FAIL_MSG);
		}
		return PaymentUtils.returnXml(WeChatConfig.FAIL, WeChatConfig.FAIL_MSG);
	}

	/**
	 * 微信退款回调
	 * 
	 * @return
	 */
	@Transactional
	public synchronized String refundNotify(SortedMap<String, String> resultMap, String partner_key) throws Exception {
		if (MapUtils.isEmpty(resultMap)) {
			LOG.error("退款回调异常，回传数据为空：{}", resultMap);
			return PaymentUtils.returnXml(WeChatConfig.FAIL, WeChatConfig.FAIL_MSG);
		}
		// 检验返回状态
		if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code"))) {
			// 解密-微信返回的加密信息
			SortedMap<String, String> reqInfo = PaymentUtils.decryptReqInfo(resultMap.get("req_info"), partner_key);
			if (reqInfo != null && WeChatConfig.SUCCESS.equals(reqInfo.get("refund_status"))) {
				// 处理数据
				AfterSaleOrder dbAfterSaleOrder = afterSaleOrderService.getByRefundOrderNo(reqInfo.get("out_refund_no"));
				// 待回执的售后订单 修正数据
				if (dbAfterSaleOrder != null && OrderEnum.REFUND_PAY_STATE_WAIT_RECEIPT.getCode().equals(dbAfterSaleOrder.getRefundPayState())) {
					LOG.info("待回执售后订单，修改售后状态");
					dbAfterSaleOrder.setRefundPayState(OrderEnum.REFUND_PAY_STATE_ALREADY_RECEIPT.getCode());
					dbAfterSaleOrder.setActualRefundAmount((new BigDecimal(reqInfo.get("settlement_refund_fee"))).divide(BigDecimal.valueOf(100)));
					dbAfterSaleOrder.setRefundTime(DateUtils.parseDate(reqInfo.get("success_time"), "yyyy-MM-dd HH:mm:ss"));
					afterSaleOrderService.updateAfterSaleOrder(dbAfterSaleOrder);
					// 关闭销售订单
					saleOrderService.closeOrderByRefund(dbAfterSaleOrder.getSaleOrder());
					// 修改开团单和参团单
					groupBuyOrderService.updateByAfterRefund(dbAfterSaleOrder.getSaleOrder());
					// 订单记录
					orderLogService.addLogByOrder(dbAfterSaleOrder.getSaleOrder(), OrderEnum.ORDER_LOG_STATE_REFUND, "退款成功");
					// 会员资金账户记录
					accountRecordService.addAccountRecordByTradeType(dbAfterSaleOrder.getSaleOrder(), dbAfterSaleOrder.getMember(), dbAfterSaleOrder.getRefundAmount(),
							MemberEnum.TRADE_TYPE_REFUND, dbAfterSaleOrder.getMember());
					// 收回上级佣金
					memberCommissionService.returnSuperiorCommissionBySubordinateRefund(dbAfterSaleOrder.getSaleOrder());
					// 收回已经发放的代金券
					couponReceiveService.returnVoucherByRefund(dbAfterSaleOrder.getMember(), dbAfterSaleOrder.getSaleOrder());
					// 释放已经使用的代金券
					couponReceiveService.returnUseVoucherByRefund(dbAfterSaleOrder.getMember(), dbAfterSaleOrder.getSaleOrder());
					// 收回已经使用的余额
					accountService.returnBalanceByRefund(dbAfterSaleOrder.getMember(), dbAfterSaleOrder.getSaleOrder().getBalance(), dbAfterSaleOrder.getSaleOrder());
				}
			} else {
				LOG.error("退款回调失败，返传信息是{}", reqInfo);
				return PaymentUtils.returnXml(WeChatConfig.FAIL, WeChatConfig.FAIL_MSG);
			}
		} else {
			LOG.error("退款回调失败，回传信息是{}", resultMap);
			return PaymentUtils.returnXml(WeChatConfig.FAIL, WeChatConfig.FAIL_MSG);
		}
		return PaymentUtils.returnXml(WeChatConfig.FAIL, WeChatConfig.FAIL_MSG);
	}

	/**
	 * 
	 * 支付完成后 调用方法
	 * 
	 * @return
	 */
	@Transactional
	public synchronized void afterPay(List<SaleOrder> saleOrders, SortedMap<String, String> resultMap, OrderEnum paymentChannel) {
		LOG.info("afterPay,wait process order is orderNo={}", resultMap.get("out_trade_no"));
		// 拿到附加数据 处理订单
		if (CollectionUtils.isNotEmpty(saleOrders)) {
			for (SaleOrder tmpOrder : saleOrders) {
				if (tmpOrder != null && OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(tmpOrder.getOrderState())) {
					// 根据订单类型 更新订单状态
					// 1.礼物订单
					if (OrderEnum.ORDER_TYPE_GIFT.getCode().equals(tmpOrder.getOrderType())) {
						// 订单状态 已完成
						tmpOrder.setOrderState(OrderEnum.ORDER_STATE_COMPLETED.getCode());
						// 修改礼物的失效时间
						giftBagService.updateByAfterPay(tmpOrder);

					} // 2.团购订单
					else if (OrderEnum.ORDER_TYPE_GROUP.getCode().equals(tmpOrder.getOrderType())) {
						// 订单状态 待发货
						tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());
						// 修改开/参团单状态
						if (OrderEnum.GROUP_ORDER_TYPE_OPEN.getCode().equals(tmpOrder.getGroupOrderType())) {
							groupBuyOrderService.updateOpenGroupByAfterPay(tmpOrder);
						} else if (OrderEnum.GROUP_ORDER_TYPE_JOIN.getCode().equals(tmpOrder.getGroupOrderType())) {
							groupBuyOrderService.updateJoinGroupByAfterPay(tmpOrder);
						}
					} // 3.普通订单
					else {
						tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());// 订单状态 待发货
					}
					tmpOrder.setPayTradeNo(resultMap.get("transaction_id"));// 微信支付订单号
					tmpOrder.setTradeType(resultMap.get("trade_type"));// 交易类型JSAPI、NATIVE、APP
					tmpOrder.setPayMode(OrderEnum.PAY_MODE_WECHAT_PAY.getCode());
					tmpOrder.setPaymentTime(DateUtils.parseDate(resultMap.get("time_end"), "yyyyMMddHHmmss"));
					tmpOrder.setPaymentChannel(paymentChannel.getCode());
					tmpOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_CAN_APPLY.getCode());
					tmpOrder.setRemark("支付完成");
					// 更新订单
					saleOrderService.updateOrder(tmpOrder);
					// 库存修正+库存记录
					stockService.useStockByPayOrder(tmpOrder);
					// 支付记录
					payRecordService.addPayRecordByOrderForWeChat(tmpOrder, resultMap);
					// 订单记录
					orderLogService.addLogByOrder(tmpOrder, OrderEnum.ORDER_LOG_STATE_PAY_SUCCESS, "支付成功");
					// 会员资金账户记录
					if (OrderEnum.ORDER_TYPE_GIFT.getCode().equals(tmpOrder.getOrderType())) {
						accountRecordService.addAccountRecordByTradeType(tmpOrder, tmpOrder.getMember(), tmpOrder.getPayAmount(), MemberEnum.TRADE_TYPE_GIFT_PAYMENT,
								tmpOrder.getMember());
					} else {
						accountRecordService.addAccountRecordByTradeType(tmpOrder, tmpOrder.getMember(), tmpOrder.getPayAmount(), MemberEnum.TRADE_TYPE_ONLINE_PAYMENT,
								tmpOrder.getMember());
					}
					// 供应商账户
					supplierAccountService.updateSupplierAccountByPayOrder(tmpOrder);
					// 分步发放优惠券
					couponReceiveService.grantVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_ORDER);
					// 解冻代金券
					couponReceiveService.thawVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_ORDER);
					// 使用余额
				//	accountService.useBalanceByAfterPayment(tmpOrder.getMember(), tmpOrder.getBalance(), tmpOrder);
					// 使用积分抵现
				//	accountService.useIntegralCashByAfterPayment(tmpOrder.getMember(), tmpOrder.getIntegralCashAmount(),tmpOrder);
					
				}
			}
		}
	}

	/**
	 * 关闭订单时 查询订单支付状态</br>
	 * 支付 true 未支付false
	 * 
	 * @param saleOrder
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean orderQueryForWeChat(SaleOrder saleOrder) throws Exception {
		if (saleOrder != null && StringUtils.isNotBlank(saleOrder.getPayOrderNo()) && saleOrder.getOrderSource() != null) {
			// 封装查询参数
			WeChatVo weChatVo = new WeChatVo();
			if (OrderEnum.ORDER_SOURCE_SP.getCode().equals(saleOrder.getOrderSource())) {
				weChatVo.setAppId(WeChatConfig.SP_APP_ID);
				weChatVo.setMchId(WeChatConfig.SP_MCH_ID);
				weChatVo.setPayOrderNo(saleOrder.getPayOrderNo());
				weChatVo.setPartnerKey(WeChatConfig.SP_PARTNER_KEY);
				saleOrder.setPaymentChannel(OrderEnum.PAYMENT_CHANNEL_SP.getCode());
			} else if (OrderEnum.ORDER_SOURCE_MP.getCode().equals(saleOrder.getOrderSource())) {
				weChatVo.setAppId(WeChatConfig.MP_APP_ID);
				weChatVo.setMchId(WeChatConfig.MP_MCH_ID);
				weChatVo.setPayOrderNo(saleOrder.getPayOrderNo());
				weChatVo.setPartnerKey(WeChatConfig.MP_PARTNER_KEY);
				saleOrder.setPaymentChannel(OrderEnum.PAYMENT_CHANNEL_MP.getCode());
			} else if (OrderEnum.ORDER_SOURCE_APP.getCode().equals(saleOrder.getOrderSource())) {
				weChatVo.setAppId(WeChatConfig.APP_ID);
				weChatVo.setMchId(WeChatConfig.APP_MCH_ID);
				weChatVo.setPayOrderNo(saleOrder.getPayOrderNo());
				weChatVo.setPartnerKey(WeChatConfig.APP_PARTNER_KEY);
				saleOrder.setPaymentChannel(OrderEnum.PAYMENT_CHANNEL_APP_WECHAT.getCode());
			} else {
				LOG.error("未知的订单来源（{}），无法查询订单", saleOrder.getOrderSource());
				return false;
			}
			// 构建查询数据
			SortedMap<String, String> paramMap = PaymentUtils.buildOrderQuery(weChatVo);
			// 将请求参数转为xml格式
			String requestXml = PaymentUtils.mapToXml(paramMap);
			// 发起请求 获取返回的结果
			String result = PaymentUtils.httpsRequest(WeChatConfig.ORDER_QUERY_URL, WeChatConfig.REQUEST_METHOD_POST, requestXml);
			LOG.info("微信查询订单返回数据为：result={}", result);
			if (StringUtils.isNotBlank(result)) {
				SortedMap<String, String> resultMap = PaymentUtils.xmlToMap(result);
				LOG.info("微信查询订单返回数据为：resultMap={}", resultMap);
				// 支付成功的订单
				if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code")) && WeChatConfig.SUCCESS.equals(resultMap.get("result_code"))
						&& WeChatConfig.SUCCESS.equals(resultMap.get("trade_state"))) {
					// 根据订单类型 更新订单状态
					// 1.礼物订单
					if (OrderEnum.ORDER_TYPE_GIFT.getCode().equals(saleOrder.getOrderType())) {
						// 订单状态 已完成
						saleOrder.setOrderState(OrderEnum.ORDER_STATE_COMPLETED.getCode());
						// 修改礼物的失效时间
						giftBagService.updateByAfterPay(saleOrder);
					} // 2.团购订单
					else if (OrderEnum.ORDER_TYPE_GROUP.getCode().equals(saleOrder.getOrderType())) {
						saleOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());// 订单状态 待发货
						// 订单状态 待发货
						saleOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());
						// 修改开/参团单状态
						if (OrderEnum.GROUP_ORDER_TYPE_OPEN.getCode().equals(saleOrder.getGroupOrderType())) {
							groupBuyOrderService.updateOpenGroupByAfterPay(saleOrder);
						} else if (OrderEnum.GROUP_ORDER_TYPE_JOIN.getCode().equals(saleOrder.getGroupOrderType())) {
							groupBuyOrderService.updateJoinGroupByAfterPay(saleOrder);
						}
					} // 3.普通订单
					else {
						saleOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());// 订单状态 待发货
					}
					saleOrder.setPayTradeNo(resultMap.get("transaction_id"));// 微信支付订单号
					saleOrder.setTradeType(resultMap.get("trade_type"));// 交易类型JSAPI、NATIVE、APP
					saleOrder.setPayMode(OrderEnum.PAY_MODE_WECHAT_PAY.getCode());
					saleOrder.setPaymentTime(DateUtils.parseDate(resultMap.get("time_end"), "yyyyMMddHHmmss"));
					saleOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_CAN_APPLY.getCode());
					saleOrder.setRemark("支付完成");
					// 更新订单
					saleOrderService.updateOrder(saleOrder);
					// 库存修正+库存记录
					stockService.useStockByPayOrder(saleOrder);
					// 支付记录
					payRecordService.addPayRecordByOrderForWeChat(saleOrder, resultMap);
					// 订单记录
					orderLogService.addLogByOrder(saleOrder, OrderEnum.ORDER_LOG_STATE_PAY_SUCCESS, "支付成功");
					// 会员资金账户记录
					if (OrderEnum.ORDER_TYPE_GIFT.getCode().equals(saleOrder.getOrderType())) {
						accountRecordService.addAccountRecordByTradeType(saleOrder, saleOrder.getMember(), saleOrder.getPayAmount(), MemberEnum.TRADE_TYPE_GIFT_PAYMENT,
								saleOrder.getMember());
					} else {
						accountRecordService.addAccountRecordByTradeType(saleOrder, saleOrder.getMember(), saleOrder.getPayAmount(), MemberEnum.TRADE_TYPE_ONLINE_PAYMENT,
								saleOrder.getMember());
					}
					// 供应商账户
					supplierAccountService.updateSupplierAccountByPayOrder(saleOrder);
					// 分步发放优惠券
					couponReceiveService.grantVoucherByStep(saleOrder.getMember(), saleOrder, ActivityEnum.GRANT_NODE_ORDER);
					// 解冻代金券
					couponReceiveService.thawVoucherByStep(saleOrder.getMember(), saleOrder, ActivityEnum.GRANT_NODE_ORDER);
					// 使用余额
					accountService.useBalanceByAfterPayment(saleOrder.getMember(), saleOrder.getBalance(), saleOrder);
					// 使用积分抵现
					accountService.useIntegralCashByAfterPayment(saleOrder.getMember(), saleOrder.getIntegralCashAmount(),
							saleOrder);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 定时处理待回执的退款订单
	 * 
	 * @param afterSaleOrder
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void refundQueryForWeChat(AfterSaleOrder afterSaleOrder) throws Exception {
		if (afterSaleOrder != null && StringUtils.isNotBlank(afterSaleOrder.getRefundOrderNo()) && afterSaleOrder.getSaleOrder().getPaymentChannel() != null) {
			// 封装查询参数
			WeChatVo weChatVo = new WeChatVo();
			if (OrderEnum.PAYMENT_CHANNEL_SP.getCode().equals(afterSaleOrder.getSaleOrder().getPaymentChannel())) {
				weChatVo.setAppId(WeChatConfig.SP_APP_ID);
				weChatVo.setMchId(WeChatConfig.SP_MCH_ID);
				weChatVo.setRefundOrderNo(afterSaleOrder.getRefundOrderNo());
				weChatVo.setPartnerKey(WeChatConfig.SP_PARTNER_KEY);
			} else if (OrderEnum.PAYMENT_CHANNEL_MP.getCode().equals(afterSaleOrder.getSaleOrder().getPaymentChannel())) {
				weChatVo.setAppId(WeChatConfig.MP_APP_ID);
				weChatVo.setMchId(WeChatConfig.MP_MCH_ID);
				weChatVo.setRefundOrderNo(afterSaleOrder.getRefundOrderNo());
				weChatVo.setPartnerKey(WeChatConfig.MP_PARTNER_KEY);
			} else if (OrderEnum.PAYMENT_CHANNEL_APP_WECHAT.getCode().equals(afterSaleOrder.getSaleOrder().getPaymentChannel())) {
				weChatVo.setAppId(WeChatConfig.APP_ID);
				weChatVo.setMchId(WeChatConfig.APP_MCH_ID);
				weChatVo.setRefundOrderNo(afterSaleOrder.getRefundOrderNo());
				weChatVo.setPartnerKey(WeChatConfig.APP_PARTNER_KEY);
			} else {
				LOG.error("支付渠道是：{}，暂不支持微信或支付宝退款", afterSaleOrder.getSaleOrder().getPaymentChannel());
				return;
			}
			// 构建查询数据
			SortedMap<String, String> paramMap = PaymentUtils.buildRefundQuery(weChatVo);
			// 将请求参数转为xml格式
			String requestXml = PaymentUtils.mapToXml(paramMap);
			// 发起请求 获取返回的结果
			String result = PaymentUtils.httpsRequest(WeChatConfig.REFUND_QUERY_URL, WeChatConfig.REQUEST_METHOD_POST, requestXml);
			LOG.info("微信查询退款返回数据为：result={}", result);
			if (StringUtils.isNotBlank(result)) {
				SortedMap<String, String> resultMap = PaymentUtils.xmlToMap(result);
				LOG.info("微信查询退款返回数据为：resultMap={}", resultMap);
				// 退款成功的订单
				if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code")) && WeChatConfig.SUCCESS.equals(resultMap.get("result_code"))
						&& WeChatConfig.SUCCESS.equals(resultMap.get("refund_status_0"))) {
					// 待回执的售后订单 修正数据
					if (OrderEnum.REFUND_PAY_STATE_WAIT_RECEIPT.getCode().equals(afterSaleOrder.getRefundPayState())) {
						LOG.info("待回执售后订单，修改售后状态");
						afterSaleOrder.setRefundPayState(OrderEnum.REFUND_PAY_STATE_ALREADY_RECEIPT.getCode());
						afterSaleOrder.setActualRefundAmount((new BigDecimal(resultMap.get("refund_fee_0"))).divide(BigDecimal.valueOf(100)));
						afterSaleOrder.setRefundTime(DateUtils.parseDate(resultMap.get("refund_success_time_0"), "yyyy-MM-dd HH:mm:ss"));
						afterSaleOrderService.updateAfterSaleOrder(afterSaleOrder);
						// 关闭销售订单
						saleOrderService.closeOrderByRefund(afterSaleOrder.getSaleOrder());
						// 修改开团单和参团单
						groupBuyOrderService.updateByAfterRefund(afterSaleOrder.getSaleOrder());
						// 订单记录
						orderLogService.addLogByOrder(afterSaleOrder.getSaleOrder(), OrderEnum.ORDER_LOG_STATE_REFUND, "退款成功");
						// 会员资金账户记录
						accountRecordService.addAccountRecordByTradeType(afterSaleOrder.getSaleOrder(), afterSaleOrder.getMember(), afterSaleOrder.getRefundAmount(),
								MemberEnum.TRADE_TYPE_REFUND, afterSaleOrder.getMember());
						// 扣除上级佣金
						memberCommissionService.returnSuperiorCommissionBySubordinateRefund(afterSaleOrder.getSaleOrder());
						// 扣除已经发放的代金券
						couponReceiveService.returnVoucherByRefund(afterSaleOrder.getMember(), afterSaleOrder.getSaleOrder());
						// 释放已经使用的代金券
						couponReceiveService.returnUseVoucherByRefund(afterSaleOrder.getMember(), afterSaleOrder.getSaleOrder());
						// 收回已经使用的余额
						accountService.returnBalanceByRefund(afterSaleOrder.getMember(), afterSaleOrder.getSaleOrder().getBalance(), afterSaleOrder.getSaleOrder());
					}
				}
			}
		}
	}

	/**
	 * 根据订单 计算支付金额
	 * 
	 * @param saleOrders
	 * @return
	 */
	private BigDecimal calculatePayAmount(List<SaleOrder> saleOrders) {
		if (CollectionUtils.isEmpty(saleOrders)) {
			return BigDecimal.ZERO;
		}
		try {
			return saleOrders.stream().map(SaleOrder::getPayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
		} catch (Exception e) {
			LOG.error("计算总单金额异常" + e.getMessage());
			return BigDecimal.ZERO;
		}
	}

}
