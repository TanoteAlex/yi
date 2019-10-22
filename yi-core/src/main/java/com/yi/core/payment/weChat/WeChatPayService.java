package com.yi.core.payment.weChat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yi.core.commodity.service.IStockRecordService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.service.IOrderLogService;
import com.yi.core.order.service.IPayRecordService;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.payment.PaymentUtils;

/**
 * 微信支付
 * 
 * @author xuyh
 *
 */
@Component
@Transactional
public class WeChatPayService {

	private static final Logger LOG = LoggerFactory.getLogger(WeChatPayService.class);

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

	/**
	 * 微信服务号支付 统一下单
	 * 
	 * @param weChatVo
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public SortedMap<String, String> getUnifiedOrderForSp(WeChatVo weChatVo) throws Exception {
		// 1-校验基础参数
		if (weChatVo == null || StringUtils.isAnyBlank(weChatVo.getMemberId(), weChatVo.getOpenId())) {
			LOG.error("请求参数memberId={},orderIds={},openId={}为空", weChatVo.getMemberId(), weChatVo.getOrderIds(), weChatVo.getOpenId());
			throw new Exception("请求参数不能为空");
		}
		// 2-检查会员
		Member dbMember = memberService.getMemberById(Integer.valueOf(weChatVo.getMemberId()));
		if (dbMember == null) {
			LOG.error("会员{}不存在", weChatVo.getMemberId());
			throw new Exception("支付失败，请稍后重试");
		}
		// 获取需要支付的订单
		List<SaleOrder> dbSaleOrders = saleOrderService.getPayOrderByIds(weChatVo.getOrderIds());
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.error("订单{}不存在", weChatVo.getOrderIds());
			throw new Exception("支付失败，请稍后重试");
		}
		// 计算 支付金额
		BigDecimal payAmount = this.calculatePayAmount(dbSaleOrders);
		if (payAmount.compareTo(new BigDecimal(Optional.ofNullable(weChatVo.getTotalFee()).orElse("0"))) != 0) {
			LOG.error("前台传入支付金额{}和后台计算支付金额{}不匹配，请核实", weChatVo.getTotalFee(), payAmount);
		}
		// 进一步封装参数
		weChatVo.setPayOrderNo(dbSaleOrders.get(0).getPayOrderNo());
		weChatVo.setTradeType(WeChatConfig.TRADE_TYPE_JSAPI);
		weChatVo.setAppId(WeChatConfig.SP_APP_ID);
		weChatVo.setMchId(WeChatConfig.SP_MCH_ID);
		weChatVo.setPartnerKey(WeChatConfig.SP_PARTNER_KEY);
		// 生产环境
		if (WeChatConfig.PROD_ENV) {
			// weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
			weChatVo.setNotifyUrl(WeChatConfig.SP_PROD_PAY_NOTIFY_URL);
		} else {
			// weChatVo.setTotalFee("1");
			weChatVo.setNotifyUrl(WeChatConfig.SP_TEST_PAY_NOTIFY_URL);
		}
		weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
		// 附件参数
		weChatVo.setMemberId(String.valueOf(dbMember.getId()));
		weChatVo.setAttach(StringUtils.join(weChatVo.getOrderIds(), ","));
		// 构建订单参数 和 签名
		SortedMap<String, String> orderMap = PaymentUtils.buildUnifiedOrder_jsapi(weChatVo);
		// 将请求参数转为xml格式
		String requestXml = PaymentUtils.mapToXml(orderMap);
		// 发起请求 获取返回的结果
		String result = PaymentUtils.httpsRequest(WeChatConfig.UNIFIED_ORDER_URL, WeChatConfig.REQUEST_METHOD_POST, requestXml);
		if (StringUtils.isBlank(result)) {
			LOG.error("统一下单返回数据为空{}", result);
			throw new Exception("支付失败，请稍后重试");
		}
		SortedMap<String, String> resultMap = PaymentUtils.xmlToMap(result);
		// 成功
		if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code")) && WeChatConfig.SUCCESS.equals(resultMap.get("result_code"))) {
			// 构建支付签名
			SortedMap<String, String> returnMap = new TreeMap<>();
			returnMap.put("appId", resultMap.get("appid"));
			returnMap.put("timeStamp", String.valueOf(PaymentUtils.getCurrentTime()));
			returnMap.put("nonceStr", PaymentUtils.generateNonceStr());
			returnMap.put("package", "prepay_id=" + resultMap.get("prepay_id"));
			returnMap.put("signType", WeChatConfig.SIGN_TYPE_MD5);
			String paySign = PaymentUtils.generateSign(returnMap, weChatVo.getPartnerKey());
			returnMap.put("paySign", paySign);
			return returnMap;
			// 失败
		} else {
			LOG.error("统一下单失败{}", resultMap);
			throw new Exception("支付失败，请稍后重试");
		}
	}

	/**
	 * 微信小程序支付 统一下单
	 * 
	 * @param weChatVo
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public SortedMap<String, String> getUnifiedOrderForMp(WeChatVo weChatVo) throws Exception {
		// 1-校验基础参数
		if (weChatVo == null || StringUtils.isAnyBlank(weChatVo.getMemberId(), weChatVo.getOpenId())) {
			LOG.error("请求参数memberId={},orderIds={},openId={}为空", weChatVo.getMemberId(), weChatVo.getOrderIds(), weChatVo.getOpenId());
			throw new Exception("请求参数不能为空");
		}
		// 2-检查会员
		Member dbMember = memberService.getMemberById(Integer.valueOf(weChatVo.getMemberId()));
		if (dbMember == null) {
			LOG.error("会员{}不存在", weChatVo.getMemberId());
			throw new Exception("支付失败，请稍后重试");
		}
		// 获取需要支付的订单
		List<SaleOrder> dbSaleOrders = saleOrderService.getPayOrderByIds(weChatVo.getOrderIds());
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.error("订单{}不存在", weChatVo.getOrderIds());
			throw new Exception("支付失败，请稍后重试");
		}
		// 计算 支付金额
		BigDecimal payAmount = this.calculatePayAmount(dbSaleOrders);
		if (payAmount.compareTo(new BigDecimal(Optional.ofNullable(weChatVo.getTotalFee()).orElse("0"))) != 0) {
			LOG.error("前台传入支付金额{}和后台计算支付金额{}不匹配，请核实", weChatVo.getTotalFee(), payAmount);
		}
		// 进一步封装参数
		weChatVo.setPayOrderNo(dbSaleOrders.get(0).getPayOrderNo());
		weChatVo.setTradeType(WeChatConfig.TRADE_TYPE_JSAPI);
		weChatVo.setAppId(WeChatConfig.MP_APP_ID);
		weChatVo.setMchId(WeChatConfig.MP_MCH_ID);
		weChatVo.setPartnerKey(WeChatConfig.MP_PARTNER_KEY);
		// 生产环境
		if (WeChatConfig.PROD_ENV) {
			// weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
			weChatVo.setNotifyUrl(WeChatConfig.MP_PROD_PAY_NOTIFY_URL);
		} else {
			// weChatVo.setTotalFee("1");
			weChatVo.setNotifyUrl(WeChatConfig.MP_TEST_PAY_NOTIFY_URL);
		}
		weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
		weChatVo.setMemberId(String.valueOf(dbMember.getId()));
		weChatVo.setAttach(StringUtils.join(weChatVo.getOrderIds(), ","));
		// 构建订单参数 和 签名
		SortedMap<String, String> orderMap = PaymentUtils.buildUnifiedOrder_jsapi(weChatVo);
		// 将请求参数转为xml格式
		String requestXml = PaymentUtils.mapToXml(orderMap);
		// 发起请求 获取返回的结果
		String result = PaymentUtils.httpsRequest(WeChatConfig.UNIFIED_ORDER_URL, WeChatConfig.REQUEST_METHOD_POST, requestXml);
		if (StringUtils.isBlank(result)) {
			LOG.error("统一下单返回数据为空{}", result);
			throw new Exception("支付失败，请稍后重试");
		}
		SortedMap<String, String> resultMap = PaymentUtils.xmlToMap(result);
		// 成功
		if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code")) && WeChatConfig.SUCCESS.equals(resultMap.get("result_code"))) {
			// 构建支付签名
			SortedMap<String, String> returnMap = new TreeMap<>();
			returnMap.put("appId", resultMap.get("appid"));
			returnMap.put("timeStamp", String.valueOf(PaymentUtils.getCurrentTime()));
			returnMap.put("nonceStr", PaymentUtils.generateNonceStr());
			returnMap.put("package", "prepay_id=" + resultMap.get("prepay_id"));
			returnMap.put("signType", WeChatConfig.SIGN_TYPE_MD5);
			String paySign = PaymentUtils.generateSign(returnMap, weChatVo.getPartnerKey());
			returnMap.put("paySign", paySign);
			return returnMap;
			// 失败
		} else {
			LOG.error("统一下单失败{}", resultMap);
			throw new Exception("支付失败，请稍后重试");
		}
	}

	/**
	 * 微信APP支付 统一下单
	 * 
	 * @param weChatVo
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public SortedMap<String, String> getUnifiedOrderForApp(WeChatVo weChatVo) throws Exception {
		if (weChatVo == null || StringUtils.isAnyBlank(weChatVo.getMemberId()) || CollectionUtils.isEmpty(weChatVo.getOrderIds())) {
			LOG.error("请求参数memberId={},orderIds={}为空", weChatVo.getMemberId(), weChatVo.getOrderIds());
			throw new Exception("请求参数不能为空");
		}
		// 检查会员
		Member dbMember = memberService.getMemberById(Integer.valueOf(weChatVo.getMemberId()));
		if (dbMember == null) {
			LOG.error("会员{}不存在", weChatVo.getMemberId());
			throw new Exception("支付失败，请稍后重试");
		}
		// 获取需要支付的订单
		List<SaleOrder> dbSaleOrders = saleOrderService.getPayOrderByIds(weChatVo.getOrderIds());
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.error("订单{}不存在", weChatVo.getOrderIds());
			throw new Exception("支付失败，请稍后重试");
		}
		// 计算 支付金额
		BigDecimal payAmount = this.calculatePayAmount(dbSaleOrders);
		if (payAmount.compareTo(new BigDecimal(Optional.ofNullable(weChatVo.getTotalFee()).orElse("0"))) != 0) {
			LOG.error("前台传入支付金额{}和后台计算支付金额{}不匹配，请核实", weChatVo.getTotalFee(), payAmount);
		}
		// 进一步封装参数
		weChatVo.setPayOrderNo(dbSaleOrders.get(0).getPayOrderNo());
		weChatVo.setTradeType(WeChatConfig.TRADE_TYPE_APP);
		weChatVo.setAppId(WeChatConfig.APP_ID);
		weChatVo.setMchId(WeChatConfig.APP_MCH_ID);
		weChatVo.setPartnerKey(WeChatConfig.APP_PARTNER_KEY);
		// 生产环境
		if (WeChatConfig.PROD_ENV) {
			// weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
			weChatVo.setNotifyUrl(WeChatConfig.APP_PROD_NOTIFY_URL);
		} else {
			// weChatVo.setTotalFee("1");
			weChatVo.setNotifyUrl(WeChatConfig.APP_TEST_NOTIFY_URL);
		}
		weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
		weChatVo.setMemberId(String.valueOf(dbMember.getId()));
		weChatVo.setAttach(StringUtils.join(weChatVo.getOrderIds(), ","));
		// 构建订单参数 和 签名
		SortedMap<String, String> orderMap = PaymentUtils.buildUnifiedOrder_app(weChatVo);
		// 将请求参数转为xml格式
		String requestXml = PaymentUtils.mapToXml(orderMap);
		// 发起请求 获取返回的结果
		String result = PaymentUtils.httpsRequest(WeChatConfig.UNIFIED_ORDER_URL, WeChatConfig.REQUEST_METHOD_POST, requestXml);
		if (StringUtils.isBlank(result)) {
			LOG.error("统一下单返回数据为空{}", result);
			throw new Exception("支付失败，请稍后重试");
		}
		SortedMap<String, String> resultMap = PaymentUtils.xmlToMap(result);
		// 校验签名
		if (PaymentUtils.verifySign(resultMap, WeChatConfig.APP_PARTNER_KEY)) {
			if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code")) && WeChatConfig.SUCCESS.equals(resultMap.get("result_code"))) {
				LOG.info("微信APP统一下单成功");
				// 二次签名
				SortedMap<String, String> returnMap = new TreeMap<>();
				returnMap.put("appid", WeChatConfig.APP_ID);
				returnMap.put("partnerid", WeChatConfig.APP_MCH_ID);
				returnMap.put("prepayid", resultMap.get("prepay_id"));
				returnMap.put("package", WeChatConfig.PACKAGE);
				returnMap.put("noncestr", PaymentUtils.generateNonceStr());
				returnMap.put("timestamp", String.valueOf(PaymentUtils.getCurrentTime()));
				String sign = PaymentUtils.generateSign(returnMap, WeChatConfig.APP_PARTNER_KEY);
				returnMap.put("sign", sign);
				return returnMap;
			} else {
				LOG.error("统一下单失败{}", resultMap);
				throw new Exception("支付失败，请稍后重试");
			}
		} else {
			LOG.error("校验签名失败", resultMap);
			throw new Exception("支付失败，请稍后重试");
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

	/**
	 * 公众号 创建礼包预支付订单
	 * 
	 * @param giftOrder
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public SortedMap<String, String> createGiftBagPayOrderForSp(SaleOrder saleOrder) throws Exception {
		// 1-校验基础参数
		if (saleOrder == null || saleOrder.getMember() == null) {
			LOG.error("请求参数（giftOrder）为空");
			throw new Exception("请求参数不能为空");
		}
		// 进一步封装参数
		WeChatVo weChatVo = new WeChatVo();
		weChatVo.setOpenId(saleOrder.getMember().getSpOpenId());
		weChatVo.setPayOrderNo(saleOrder.getPayOrderNo());
		weChatVo.setTradeType(WeChatConfig.TRADE_TYPE_JSAPI);
		weChatVo.setAppId(WeChatConfig.SP_APP_ID);
		weChatVo.setMchId(WeChatConfig.SP_MCH_ID);
		weChatVo.setPartnerKey(WeChatConfig.SP_PARTNER_KEY);
		// 生产环境
		if (WeChatConfig.PROD_ENV) {
			// weChatVo.setTotalFee(PaymentUtils.yuanToFen(saleOrder.getPayAmount()));
			weChatVo.setNotifyUrl(WeChatConfig.SP_PROD_PAY_NOTIFY_URL);
		} else {
			// weChatVo.setTotalFee("1");
			weChatVo.setNotifyUrl(WeChatConfig.SP_TEST_PAY_NOTIFY_URL);
		}
		weChatVo.setTotalFee(PaymentUtils.yuanToFen(saleOrder.getPayAmount()));
		weChatVo.setMemberId(String.valueOf(saleOrder.getMember().getId()));
		weChatVo.setAttach(StringUtils.join(weChatVo.getOrderIds(), ","));
		// 构建订单参数 和 签名
		SortedMap<String, String> orderMap = PaymentUtils.buildUnifiedOrder_jsapi(weChatVo);
		// 将请求参数转为xml格式
		String requestXml = PaymentUtils.mapToXml(orderMap);
		// 发起请求 获取返回的结果
		String result = PaymentUtils.httpsRequest(WeChatConfig.UNIFIED_ORDER_URL, WeChatConfig.REQUEST_METHOD_POST, requestXml);
		if (StringUtils.isBlank(result)) {
			LOG.error("统一下单返回数据为空{}", result);
			throw new Exception("支付失败，请稍后重试");
		}
		SortedMap<String, String> resultMap = PaymentUtils.xmlToMap(result);
		// 成功
		if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code")) && WeChatConfig.SUCCESS.equals(resultMap.get("result_code"))) {
			// 构建支付签名
			SortedMap<String, String> returnMap = new TreeMap<>();
			returnMap.put("appId", resultMap.get("appid"));
			returnMap.put("timeStamp", String.valueOf(PaymentUtils.getCurrentTime()));
			returnMap.put("nonceStr", PaymentUtils.generateNonceStr());
			returnMap.put("package", "prepay_id=" + resultMap.get("prepay_id"));
			returnMap.put("signType", WeChatConfig.SIGN_TYPE_MD5);
			String paySign = PaymentUtils.generateSign(returnMap, weChatVo.getPartnerKey());
			returnMap.put("paySign", paySign);
			return returnMap;
			// 失败
		} else {
			LOG.error("统一下单失败{}", resultMap);
			throw new Exception("支付失败，请稍后重试");
		}
	}

	/**
	 * 小程序 创建礼包预支付订单
	 * 
	 * @param giftOrder
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public SortedMap<String, String> createGiftBagPayOrderForMp(SaleOrder saleOrder) throws Exception {
		// 1-校验基础参数
		if (saleOrder == null || saleOrder.getMember() == null) {
			LOG.error("请求参数（saleOrder，member）为空");
			throw new Exception("请求参数不能为空");
		}
		WeChatVo weChatVo = new WeChatVo();
		weChatVo.setOpenId(saleOrder.getMember().getMpOpenId());
		// 进一步封装参数
		weChatVo.setPayOrderNo(saleOrder.getPayOrderNo());
		weChatVo.setTradeType(WeChatConfig.TRADE_TYPE_JSAPI);
		weChatVo.setAppId(WeChatConfig.MP_APP_ID);
		weChatVo.setMchId(WeChatConfig.MP_MCH_ID);
		weChatVo.setPartnerKey(WeChatConfig.MP_PARTNER_KEY);
		// 生产环境
		if (WeChatConfig.PROD_ENV) {
			// weChatVo.setTotalFee(PaymentUtils.yuanToFen(saleOrder.getPayAmount()));
			weChatVo.setNotifyUrl(WeChatConfig.MP_PROD_PAY_NOTIFY_URL);
		} else {
			// weChatVo.setTotalFee("1");
			weChatVo.setNotifyUrl(WeChatConfig.MP_TEST_PAY_NOTIFY_URL);
		}
		weChatVo.setTotalFee(PaymentUtils.yuanToFen(saleOrder.getPayAmount()));
		weChatVo.setMemberId(String.valueOf(saleOrder.getMember().getId()));
		weChatVo.setAttach(StringUtils.join(weChatVo.getOrderIds(), ","));
		// 构建订单参数 和 签名
		SortedMap<String, String> orderMap = PaymentUtils.buildUnifiedOrder_jsapi(weChatVo);
		// 将请求参数转为xml格式
		String requestXml = PaymentUtils.mapToXml(orderMap);
		// 发起请求 获取返回的结果
		String result = PaymentUtils.httpsRequest(WeChatConfig.UNIFIED_ORDER_URL, WeChatConfig.REQUEST_METHOD_POST, requestXml);
		if (StringUtils.isBlank(result)) {
			LOG.error("统一下单返回数据为空{}", result);
			throw new Exception("支付失败，请稍后重试");
		}
		SortedMap<String, String> resultMap = PaymentUtils.xmlToMap(result);
		// 成功
		if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code")) && WeChatConfig.SUCCESS.equals(resultMap.get("result_code"))) {
			// 构建支付签名
			SortedMap<String, String> returnMap = new TreeMap<>();
			returnMap.put("appId", resultMap.get("appid"));
			returnMap.put("timeStamp", String.valueOf(PaymentUtils.getCurrentTime()));
			returnMap.put("nonceStr", PaymentUtils.generateNonceStr());
			returnMap.put("package", "prepay_id=" + resultMap.get("prepay_id"));
			returnMap.put("signType", WeChatConfig.SIGN_TYPE_MD5);
			String paySign = PaymentUtils.generateSign(returnMap, weChatVo.getPartnerKey());
			returnMap.put("paySign", paySign);
			return returnMap;
			// 失败
		} else {
			LOG.error("统一下单失败{}", resultMap);
			throw new Exception("支付失败，请稍后重试");
		}
	}

}
