package com.yi.core.payment.weChat;

import java.math.BigDecimal;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yi.core.commodity.service.IStockRecordService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.entity.AfterSaleOrder;
import com.yi.core.order.service.IOrderLogService;
import com.yi.core.order.service.IPayRecordService;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.payment.PaymentUtils;
import com.yihz.common.exception.BusinessException;

/**
 * 微信退款
 * 
 * @author xuyh
 *
 */
@Component
@Transactional
public class WeChatRefundService {

	private static final Logger LOG = LoggerFactory.getLogger(WeChatRefundService.class);

	@Value("${yi.host}")
	private String host;

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
	 * 公众号 执行退款
	 * 
	 * @param member
	 * @param refundOrder
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public AfterSaleOrder executeRefundForSp(Member member, AfterSaleOrder afterSaleOrder) throws Exception {
		// 1，校验基础参数
		if (member == null || afterSaleOrder == null) {
			LOG.error("提交参数（member，afterSaleOrder）为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 2.封装退款参数
		WeChatVo weChatVo = new WeChatVo();
		// 查看订单支付渠道
		weChatVo.setAppId(WeChatConfig.SP_APP_ID);
		weChatVo.setMchId(WeChatConfig.SP_MCH_ID);
		weChatVo.setPayTradeNo(afterSaleOrder.getSaleOrder().getPayTradeNo());
		weChatVo.setRefundOrderNo(afterSaleOrder.getRefundOrderNo());
		weChatVo.setTotalFee(PaymentUtils.yuanToFen(afterSaleOrder.getPayAmount()));
		weChatVo.setRefundFee(PaymentUtils.yuanToFen(afterSaleOrder.getRefundAmount()));
		weChatVo.setRefundDesc("售后退款");
		if (WeChatConfig.PROD_ENV) {
			weChatVo.setNotifyUrl(WeChatConfig.SP_PROD_REFUND_NOTIFY_URL);
		} else {
			weChatVo.setNotifyUrl(WeChatConfig.SP_TEST_REFUND_NOTIFY_URL);
		}
		weChatVo.setPartnerKey(WeChatConfig.SP_PARTNER_KEY);
		// 构建退款订单参数 和 签名
		SortedMap<String, String> orderMap = PaymentUtils.buildRefundOrder_jsapi(weChatVo);
		// 将请求参数转为xml格式
		String requestXml = PaymentUtils.mapToXml(orderMap);
		// 发起请求 获取返回的结果
		// 证书路径
		// String certPath = "F:/cert/ynwk/apiclient_cert.p12";
		String certPath = "/cert/sp/apiclient_cert.p12";
		String result = PaymentUtils.refundHttpsRequest(WeChatConfig.SP_MCH_ID, WeChatConfig.REFUND_URL, requestXml, certPath);
		if (StringUtils.isBlank(result)) {
			LOG.error("退款申请，返回数据为空{}", result);
			throw new Exception("退款失败，请稍后重试");
		}
		SortedMap<String, String> resultMap = PaymentUtils.xmlToMap(result);
		LOG.error("退款返回的数据{}", resultMap);
		// 退款申请成功
		if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code")) && WeChatConfig.SUCCESS.equals(resultMap.get("result_code"))) {
			// 保存微信退款单号
			afterSaleOrder.setRefundTradeNo(resultMap.get("refund_id"));
			// afterSaleOrder.setActualRefundAmount(new
			// BigDecimal(resultMap.get("refund_fee")));
			afterSaleOrder.setRefundPayState(OrderEnum.REFUND_PAY_STATE_WAIT_RECEIPT.getCode());
			return afterSaleOrder;
		} else {
			LOG.error("退款申请失败{}", resultMap);
			throw new Exception(resultMap.get("err_code_des"));
		}
	}

	/**
	 * 执行退款
	 * 
	 * @param member
	 * @param refundOrder
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public AfterSaleOrder executeRefundForMp(Member member, AfterSaleOrder afterSaleOrder) throws Exception {
		// 1，校验基础参数
		if (member == null || afterSaleOrder == null) {
			LOG.error("提交参数（member，afterSaleOrder）为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 2.封装退款参数
		WeChatVo weChatVo = new WeChatVo();
		// 查看订单支付渠道
		weChatVo.setAppId(WeChatConfig.MP_APP_ID);
		weChatVo.setMchId(WeChatConfig.MP_MCH_ID);
		weChatVo.setPayTradeNo(afterSaleOrder.getSaleOrder().getPayTradeNo());
		weChatVo.setRefundOrderNo(afterSaleOrder.getRefundOrderNo());
		weChatVo.setTotalFee(PaymentUtils.yuanToFen(afterSaleOrder.getSaleOrder().getPayAmount()));
		weChatVo.setRefundFee(PaymentUtils.yuanToFen(afterSaleOrder.getRefundAmount()));
		weChatVo.setRefundDesc("售后退款");
		if (WeChatConfig.PROD_ENV) {
			weChatVo.setNotifyUrl(WeChatConfig.MP_PROD_REFUND_NOTIFY_URL);
		} else {
			weChatVo.setNotifyUrl(WeChatConfig.MP_TEST_REFUND_NOTIFY_URL);
		}
		weChatVo.setPartnerKey(WeChatConfig.MP_PARTNER_KEY);
		// 构建退款订单参数 和 签名
		SortedMap<String, String> orderMap = PaymentUtils.buildRefundOrder_jsapi(weChatVo);
		// 将请求参数转为xml格式
		String requestXml = PaymentUtils.mapToXml(orderMap);
		// 发起请求 获取返回的结果
		// 证书路径
		String certPath = "/cert/mp/apiclient_cert.p12";
		String result = PaymentUtils.refundHttpsRequest(WeChatConfig.MP_MCH_ID, WeChatConfig.REFUND_URL, requestXml, certPath);
		// String result = PaymentUtils.httpsRequest(WeChatConfig.REFUND_URL,
		// WeChatConfig.REQUEST_METHOD_POST, requestXml);
		if (StringUtils.isBlank(result)) {
			LOG.error("退款申请，返回数据为空{}", result);
			throw new Exception("退款失败，请稍后重试");
		}
		SortedMap<String, String> resultMap = PaymentUtils.xmlToMap(result);
		// 退款申请成功
		if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code")) && WeChatConfig.SUCCESS.equals(resultMap.get("result_code"))) {
			// 保存微信退款单号
			afterSaleOrder.setRefundTradeNo(resultMap.get("refund_id"));
			afterSaleOrder.setActualRefundAmount(new BigDecimal(resultMap.get("refund_fee")));
			return afterSaleOrder;
		} else {
			LOG.error("退款申请失败{}", resultMap);
			throw new Exception(resultMap.get("err_code_des"));
		}
	}

	/**
	 * 执行退款
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
		// 2.封装退款参数
		WeChatVo weChatVo = new WeChatVo();
		// 查看订单支付渠道
		weChatVo.setAppId(WeChatConfig.APP_ID);
		weChatVo.setMchId(WeChatConfig.APP_MCH_ID);
		weChatVo.setPayTradeNo(afterSaleOrder.getSaleOrder().getPayTradeNo());
		weChatVo.setRefundOrderNo(afterSaleOrder.getRefundOrderNo());
		weChatVo.setTotalFee(PaymentUtils.yuanToFen(afterSaleOrder.getSaleOrder().getPayAmount()));
		weChatVo.setRefundFee(PaymentUtils.yuanToFen(afterSaleOrder.getRefundAmount()));
		weChatVo.setRefundDesc("售后退款");
		if (WeChatConfig.PROD_ENV) {
			weChatVo.setNotifyUrl(WeChatConfig.APP_PROD_REFUND_NOTIFY_URL);
		} else {
			weChatVo.setNotifyUrl(WeChatConfig.APP_TEST_REFUND_NOTIFY_URL);
		}
		weChatVo.setPartnerKey(WeChatConfig.APP_PARTNER_KEY);
		// 构建退款订单参数 和 签名
		SortedMap<String, String> orderMap = PaymentUtils.buildRefundOrder_jsapi(weChatVo);
		// 将请求参数转为xml格式
		String requestXml = PaymentUtils.mapToXml(orderMap);
		// 发起请求 获取返回的结果
		// 证书路径
		String certPath = "/cert/app/apiclient_cert.p12";
		String result = PaymentUtils.refundHttpsRequest(WeChatConfig.APP_MCH_ID, WeChatConfig.REFUND_URL, requestXml, certPath);
		if (StringUtils.isBlank(result)) {
			LOG.error("退款申请，返回数据为空{}", result);
			throw new Exception("退款失败，请稍后重试");
		}
		SortedMap<String, String> resultMap = PaymentUtils.xmlToMap(result);
		// 退款申请成功
		if (WeChatConfig.SUCCESS.equals(resultMap.get("return_code")) && WeChatConfig.SUCCESS.equals(resultMap.get("result_code"))) {
			// 保存微信退款单号
			afterSaleOrder.setRefundTradeNo(resultMap.get("refund_id"));
			afterSaleOrder.setActualRefundAmount(new BigDecimal(resultMap.get("refund_fee")));
			return afterSaleOrder;
		} else {
			LOG.error("退款申请失败{}", resultMap);
			throw new Exception(resultMap.get("err_code_des"));
		}
	}

}
