package com.yi.core.payment.alipay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.commodity.service.IStockRecordService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.gift.service.IGiftBagService;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberCommissionService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.entity.AfterSaleOrder;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.service.IAfterSaleOrderService;
import com.yi.core.order.service.IOrderLogService;
import com.yi.core.order.service.IPayRecordService;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.promotion.service.IGroupBuyOrderService;
import com.yi.core.supplier.service.ISupplierAccountService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.utils.date.DateUtils;

/**
 * 支付宝 支付服务
 * 
 * @author xuyh
 *
 */
@Component
public class AlipayService {

	private static final Logger LOG = LoggerFactory.getLogger(AlipayService.class);

	@Resource
	private IMemberService memberService;

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private IOrderLogService orderLogService;

	@Resource
	private IPayRecordService payRecordService;

	@Resource
	private IStockService stockService;

	@Resource
	private IStockRecordService stockRecordService;

	@Resource
	private IAccountRecordService accountRecordService;

	@Resource
	private IGiftBagService giftBagService;

	@Resource
	private IGroupBuyOrderService groupBuyOrderService;

	@Resource
	private ISupplierAccountService supplierAccountService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IAccountService accountService;

	@Resource
	private IAfterSaleOrderService afterSaleOrderService;

	@Resource
	private IMemberCommissionService memberCommissionService;

	/** 初始化 支付中心 */
	private static AlipayClient ALIPAY_CLIENT;

	static {
		// 实例化客户端
		ALIPAY_CLIENT = new DefaultAlipayClient(AlipayConfig.SERVER_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET_UTF8,
				AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
	}

	/**
	 * 创建支付宝支付订单
	 * 
	 * @param alipayVo
	 * @return
	 */
	public String createPayOrder(AlipayVo alipayVo) throws Exception {
		// 1-校验基本参数
		if (alipayVo == null || StringUtils.isBlank(alipayVo.getMemberId()) || CollectionUtils.isEmpty(alipayVo.getOrderIds())) {
			LOG.error("创建支付宝支付订单失败，请求参数为空。memberId={}，orderIds={}", alipayVo.getMemberId(), alipayVo.getOrderIds());
			throw new Exception("请求参数不能为空");
		}
		// 2-检查会员
		Member dbMember = memberService.getMemberById(Integer.valueOf(alipayVo.getMemberId()));
		if (dbMember == null) {
			LOG.error("会员{}不存在", alipayVo.getMemberId());
			throw new Exception("支付失败，请稍后重试");
		}
		// 3-获取需要支付的订单
		List<SaleOrder> dbSaleOrders = saleOrderService.getPayOrderByIds(alipayVo.getOrderIds());
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.error("订单{}不存在", alipayVo.getOrderIds());
			throw new Exception("支付失败，请稍后重试");
		}
		// 4-计算支付金额
		BigDecimal payAmount = this.calculatePayAmount(dbSaleOrders);
		if (payAmount.compareTo(new BigDecimal(Optional.ofNullable(alipayVo.getPayAmount()).orElse("0"))) != 0) {
			LOG.error("前台传入支付金额{}和后台计算支付金额{}不匹配，请核实", alipayVo.getPayAmount(), payAmount);
		}
		// 5-封装支付参数
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel bizModel = new AlipayTradeAppPayModel();
		bizModel.setOutTradeNo(dbSaleOrders.get(0).getPayOrderNo());// 商户订单号
		bizModel.setTotalAmount(payAmount.toString());// 支付金额
		bizModel.setSubject(AlipayConfig.SUBJECT);// 商品名称
		bizModel.setBody(AlipayConfig.BODY);// 支付信息
		// model.setTimeoutExpress("30m");
		bizModel.setProductCode(AlipayConfig.PRODUCT_CODE);// 销售产品码
		// 6-实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		request.setBizModel(bizModel);
		if (AlipayConfig.PROD_ENV) {
			request.setNotifyUrl(AlipayConfig.APP_PROD_PAY_NOTIFY_URL);
		} else {
			request.setNotifyUrl(AlipayConfig.APP_TEST_PAY_NOTIFY_URL);
		}
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = ALIPAY_CLIENT.sdkExecute(request);
			if (response.isSuccess()) {
				// orderString 可以直接给客户端请求，无需再做处理。
				return response.getBody();
			} else {
				LOG.error("创建支付订单失败，返回数据为{}", response.getBody());
				return null;
			}
		} catch (AlipayApiException e) {
			LOG.error("创建支付订单失败", e);
			return null;
		}
	}

	/**
	 * 支付宝支付回调 </br>
	 * 通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h
	 * 
	 * @param params
	 */
	public String alipayNotify(Map<String, String> resultMap) throws Exception {
		LOG.info("支付宝回调开始，返回参数为：{}", resultMap);
		// 1-校验基本参数
		if (MapUtils.isEmpty(resultMap)) {
			LOG.error("支付宝回调，参数为空");
			return AlipayConfig.NOTIFY_FAIL;
		}
		// 2-校验签名
		boolean signVerfied = AlipaySignature.rsaCheckV1(resultMap, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET_UTF8, AlipayConfig.SIGN_TYPE);
		// 3-验签成功
		if (signVerfied) {
			// 3-1交易成功
			if (AlipayConfig.TRADE_STATUS_SUCCESS.equals(resultMap.get("trade_status"))) {
				/**
				 * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号</br>
				 * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）</br>
				 * 3、校验通知中的seller_id（或者seller_email)，是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），</br>
				 * 4、验证app_id是否为该商户本身。
				 */
				String out_trade_no = resultMap.get("out_trade_no");
				String total_amount = resultMap.get("total_amount");
				String seller_email = resultMap.get("seller_email");
				String app_id = resultMap.get("app_id");
				// 3-1-1校验 out_trade_no
				List<SaleOrder> dbSaleOrders = saleOrderService.getWaitPayOrdersForAlipay(out_trade_no);
				if (CollectionUtils.isEmpty(dbSaleOrders)) {
					LOG.error("支付宝回调失败，根据out_trade_no查询数据为空");
					return AlipayConfig.NOTIFY_FAIL;
				}
				// 3-1-2校验 total_amount
				BigDecimal payAmount = this.calculatePayAmount(dbSaleOrders);
				if (payAmount.compareTo(new BigDecimal(Optional.ofNullable(total_amount).orElse("0"))) != 0) {
					LOG.error("支付宝回调失败，支付金额{}和后台订单金额不一致", total_amount, payAmount);
					// return AlipayConfig.NOTIFY_SUCCESS;
				}
				// 3-1-3验证 seller_email
				if (!AlipayConfig.SELLER_EMAIL.equals(seller_email)) {
					LOG.error("支付宝回调失败，卖家支付宝账号不一致，原{}，现{}", AlipayConfig.SELLER_EMAIL, seller_email);
					// return AlipayConfig.NOTIFY_SUCCESS;
				}
				// 3-1-4 验证 app_id
				if (!AlipayConfig.APP_ID.equals(app_id)) {
					LOG.error("支付宝回调失败，商户app_id不一致，原{}，现{}", AlipayConfig.APP_ID, app_id);
					return AlipayConfig.NOTIFY_FAIL;
				}
				// 3-1-5修改订单数据并记录
				for (SaleOrder tmpOrder : dbSaleOrders) {
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
						tmpOrder.setPayTradeNo(resultMap.get("trade_no"));// 交易号
						tmpOrder.setTradeType("alipay");
						tmpOrder.setPayMode(OrderEnum.PAY_MODE_ALIPAY.getCode());
						tmpOrder.setPaymentTime(DateUtils.parseDate(resultMap.get("gmt_payment"), "yyyy-MM-dd HH:mm:ss"));
						tmpOrder.setPaymentChannel(OrderEnum.PAYMENT_CHANNEL_APP_ALIPAY.getCode());
						tmpOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_CAN_APPLY.getCode());
						tmpOrder.setRemark("支付完成");
						// 更新订单
						saleOrderService.updateOrder(tmpOrder);
						// 库存修正+库存记录
						stockService.useStockByPayOrder(tmpOrder);
						// 支付记录
						payRecordService.addPayRecordByOrderForAlipay(tmpOrder, resultMap);
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
						accountService.useBalanceByAfterPayment(tmpOrder.getMember(), tmpOrder.getBalance(), tmpOrder);
						// 使用积分抵现
						accountService.useIntegralCashByAfterPayment(tmpOrder.getMember(), tmpOrder.getIntegralCashAmount(),
								tmpOrder);
					}
				}
				return AlipayConfig.NOTIFY_SUCCESS;
			} else {
				LOG.error("支付宝回调失败，返回数据为", resultMap);
				return AlipayConfig.NOTIFY_SUCCESS;
			}
		} else {
			// TODO 验签失败则记录异常日志，并在response中返回failure.
			LOG.error("支付宝回调，签名校验失败");
			return AlipayConfig.NOTIFY_FAIL;
		}
	}

	/**
	 * 支付宝执行退款
	 * 
	 * @param member
	 * @param refundOrder
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public AfterSaleOrder executeRefundForApp(Member member, AfterSaleOrder afterSaleOrder) throws Exception {
		// 1，校验基础参数
		if (member == null || afterSaleOrder == null) {
			LOG.error("提交参数（member，afterSaleOrder）为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 2，封装退款参数
		AlipayTradeRefundModel bizModel = new AlipayTradeRefundModel();
		bizModel.setTradeNo(afterSaleOrder.getSaleOrder().getPayTradeNo());
		bizModel.setOutRequestNo(afterSaleOrder.getRefundOrderNo());
		bizModel.setRefundAmount(afterSaleOrder.getRefundAmount().toString());
		bizModel.setRefundReason("售后退款");
		// 3-实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.refund
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizModel(bizModel);
		// if (AlipayConfig.PROD_ENV) {
		// request.setNotifyUrl(AlipayConfig.APP_PROD_REFUND_NOTIFY_URL);
		// } else {
		// request.setNotifyUrl(AlipayConfig.APP_TEST_REFUND_NOTIFY_URL);
		// }
		AlipayTradeRefundResponse response = ALIPAY_CLIENT.execute(request);
		if (response.isSuccess() && AlipayConfig.SUCCESS_CODE.equals(response.getCode())) {
			// 保存支付宝退款交易号
			afterSaleOrder.setRefundTradeNo(response.getTradeNo());
			afterSaleOrder.setRefundPayState(OrderEnum.REFUND_PAY_STATE_WAIT_RECEIPT.getCode());
			return afterSaleOrder;
		} else {
			LOG.error("退款失败{}", response);
			throw new Exception(response.getSubMsg());
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
	public boolean orderQueryForAlipay(SaleOrder saleOrder) throws Exception {
		// 校验基本参数
		if (saleOrder == null || StringUtils.isBlank(saleOrder.getPayOrderNo()) || saleOrder.getOrderSource() == null) {
			LOG.error("请求参数为空");
			return false;
		}
		if (OrderEnum.ORDER_SOURCE_APP.getCode().equals(saleOrder.getOrderSource())) {
			// 2，封装查询参数
			AlipayTradeQueryModel bizModel = new AlipayTradeQueryModel();
			bizModel.setOutTradeNo(saleOrder.getPayOrderNo());
			// 3-实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.query
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			request.setBizModel(bizModel);
			AlipayTradeQueryResponse response = ALIPAY_CLIENT.execute(request);
			if (response.isSuccess() && AlipayConfig.SUCCESS_CODE.equals(response.getCode())) {
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
				saleOrder.setPayTradeNo(response.getTradeNo());// 支付宝支付交易号
				saleOrder.setTradeType("alipay");// 交易类型alipay
				saleOrder.setPayMode(OrderEnum.PAY_MODE_ALIPAY.getCode());
				saleOrder.setPaymentTime(response.getSendPayDate());
				saleOrder.setPaymentChannel(OrderEnum.PAYMENT_CHANNEL_APP_ALIPAY.getCode());
				saleOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_CAN_APPLY.getCode());
				saleOrder.setRemark("支付完成");
				// 更新订单
				saleOrderService.updateOrder(saleOrder);
				// 库存修正+库存记录
				stockService.useStockByPayOrder(saleOrder);
				// 支付记录
				payRecordService.addPayRecordByOrderForAlipay(saleOrder, response);
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
			} else {
				LOG.error("订单查询失败={}", response);
				return false;
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
	public void refundQueryForAlipay(AfterSaleOrder afterSaleOrder) throws Exception {
		if (afterSaleOrder != null && afterSaleOrder.getSaleOrder() != null) {
			if (OrderEnum.PAYMENT_CHANNEL_APP_ALIPAY.getCode().equals(afterSaleOrder.getSaleOrder().getPaymentChannel())) {
				// 1，封装退款查询参数
				AlipayTradeFastpayRefundQueryModel bizModel = new AlipayTradeFastpayRefundQueryModel();
				bizModel.setTradeNo(afterSaleOrder.getRefundTradeNo());
				bizModel.setOutRequestNo(afterSaleOrder.getRefundOrderNo());
				// 2-实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.fastpay.refund.query
				AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
				request.setBizModel(bizModel);
				AlipayTradeFastpayRefundQueryResponse response = ALIPAY_CLIENT.execute(request);
				if (response.isSuccess() && AlipayConfig.SUCCESS_CODE.equals(response.getCode())) {
					// 待回执的售后订单 修正数据
					if (OrderEnum.REFUND_PAY_STATE_WAIT_RECEIPT.getCode().equals(afterSaleOrder.getRefundPayState())) {
						LOG.info("待回执售后订单，修改售后状态");
						afterSaleOrder.setRefundPayState(OrderEnum.REFUND_PAY_STATE_ALREADY_RECEIPT.getCode());
						afterSaleOrder.setActualRefundAmount(new BigDecimal(response.getRefundAmount()));
						afterSaleOrder.setRefundTime(response.getGmtRefundPay());
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
				} else {
					LOG.error("退款回执失败{}", response);
					throw new Exception(response.getSubMsg());
				}
			} else {
				LOG.error("支付渠道是：{}，暂不支持支付宝退款", afterSaleOrder.getSaleOrder().getPaymentChannel());
				return;
			}
		}
	}

	/**
	 * 查询对账单下载地址接口alipay.data.dataservice.bill.downloadurl.query
	 * 
	 * @param bill_type
	 *            固定传入trade
	 * @param bill_date
	 *            需要下载的账单日期，最晚是当期日期的前一天
	 */
	public String billDownloadUrlQuery(String bill_type, String bill_date) {
		// 创建API对应的request类
		AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
		// 设置业务参数
		Map<String, Object> params = new HashMap<>();
		params.put("bill_type", bill_type);
		params.put("bill_date", bill_date);
		request.setBizContent(JSONObject.toJSONString(params));
		// 通过alipayClient调用API，获得对应的response类
		try {
			AlipayDataDataserviceBillDownloadurlQueryResponse response = ALIPAY_CLIENT.execute(request);
			JSONObject jsonObject = JSONObject.parseObject(response.getBody());
			// 账单文件下载地址，30秒有效
			String bill_download_url = jsonObject.getString("bill_download_url");
			return bill_download_url;
		} catch (AlipayApiException e) {
			LOG.error("查询对账单下载地址失败", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 下载账单文件
	 */
	public void downloadBillFile() {
		// 将接口返回的对账单下载地址传入urlStr
		String urlStr = "http://dwbillcenter.alipay.com/downloadBillFile.resource?bizType=X&userId=X&fileType=X&bizDates=X&downloadFileName=X&fileId=X";
		// 指定希望保存的文件路径
		String filePath = "/Users/fund_bill_20160405.csv";
		URL url = null;
		HttpURLConnection httpUrlConnection = null;
		InputStream fis = null;
		FileOutputStream fos = null;
		try {
			url = new URL(urlStr);
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setConnectTimeout(5 * 1000);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
			httpUrlConnection.connect();
			fis = httpUrlConnection.getInputStream();
			byte[] temp = new byte[1024];
			int b;
			fos = new FileOutputStream(new File(filePath));
			while ((b = fis.read(temp)) != -1) {
				fos.write(temp, 0, b);
				fos.flush();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
				httpUrlConnection.disconnect();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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
