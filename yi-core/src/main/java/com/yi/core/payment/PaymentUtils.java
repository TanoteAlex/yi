package com.yi.core.payment;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.AlgorithmParameters;
import java.security.KeyStore;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yi.base.utils.HttpUtils;
import com.yi.core.payment.weChat.WeChatConfig;
import com.yi.core.payment.weChat.WeChatVo;
import com.yi.core.payment.weChat.XmlUtils;

/**
 * 支付工具类
 * 
 * @author xuyh
 *
 */
public class PaymentUtils {

	private static final Logger LOG = LoggerFactory.getLogger(PaymentUtils.class);

	/**
	 * 服务号或小程序 微信支付构建统一订单
	 * 
	 * @param weChatVo
	 * @return SortedMap<String, String>
	 * @throws Exception
	 */
	public static SortedMap<String, String> buildUnifiedOrder_jsapi(WeChatVo weChatVo) throws Exception {
		if (weChatVo == null || StringUtils.isAnyBlank(weChatVo.getPayOrderNo(), weChatVo.getTotalFee(), weChatVo.getOpenId(), weChatVo.getAppId(), weChatVo.getMchId(),
				weChatVo.getPartnerKey(), weChatVo.getNotifyUrl())) {
			LOG.error("参数（out_trade_no，total_fee，open_id，app_id，app_secret，mch_id，partner_key，notify_url）为空");
			throw new Exception("请求参数不能为空");
		}
		SortedMap<String, String> orderMap = new TreeMap<String, String>();
		// 公众账号ID
		orderMap.put("appid", weChatVo.getAppId());
		// 商户号
		orderMap.put("mch_id", weChatVo.getMchId());
		// 随机字符串 UUID
		orderMap.put("nonce_str", generateNonceStr());
		// 签名类型
		orderMap.put("sign_type", WeChatConfig.SIGN_TYPE_MD5);
		// 商品描述 ps:美周壹壹优选-订单支付
		orderMap.put("body", WeChatConfig.BODY);
		// 商品详情
		// orderMap.put("detail", "");
		// ++附加数据 由于拆单，附加数据 是订单的id拼成的字符串 以','分割
		if (StringUtils.isNotBlank(weChatVo.getAttach())) {
			orderMap.put("attach", weChatVo.getAttach());
		}
		// ++商户订单号
		orderMap.put("out_trade_no", weChatVo.getPayOrderNo());
		// 标价币种 默认人民币：CNY
		orderMap.put("fee_type", WeChatConfig.FEE_TYPE_CNY);
		// ++标价金额 订单总金额，单位为分
		orderMap.put("total_fee", weChatVo.getTotalFee());
		// 终端IP
		orderMap.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());
		// 交易起始时间
		orderMap.put("time_start", getDateTime());
		// 交易结束时间
		// orderMap.put("time_expire", getDateTime());
		// 通知地址
		orderMap.put("notify_url", weChatVo.getNotifyUrl());
		// 交易类型 JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付
		orderMap.put("trade_type", WeChatConfig.TRADE_TYPE_JSAPI);
		// ++用户标识 trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
		orderMap.put("openid", weChatVo.getOpenId());
		// 生成签名
		String sign = generateSign(orderMap, weChatVo.getPartnerKey());
		orderMap.put("sign", sign);// 签名
		return orderMap;
	}

	/**
	 * APP微信支付 构建统一订单
	 * 
	 * @param out_trade_no
	 *            订单号
	 * @param total_fee
	 *            订单金额
	 * @param trade_type
	 *            交易类型 JSAPI-公众号支付 APP-APP支付
	 * @return SortedMap<String, String>
	 * @throws UnknownHostException
	 */
	public static SortedMap<String, String> buildUnifiedOrder_app(WeChatVo weChatVo) throws Exception {
		if (weChatVo == null || StringUtils.isAnyBlank(weChatVo.getPayOrderNo(), weChatVo.getTotalFee())) {
			LOG.error("参数（out_trade_no，total_fee）为空");
			throw new Exception("请求参数不能为空");
		}
		SortedMap<String, String> orderMap = new TreeMap<String, String>();
		// 应用ID
		orderMap.put("appid", WeChatConfig.APP_ID);
		// 商户号
		orderMap.put("mch_id", WeChatConfig.APP_MCH_ID);
		// 随机字符串 UUID
		orderMap.put("nonce_str", generateNonceStr());
		// 签名类型
		orderMap.put("sign_type", WeChatConfig.SIGN_TYPE_MD5);
		// 商品描述 ps:美周壹壹优选-订单支付
		orderMap.put("body", WeChatConfig.BODY);
		// 商品详情
		// orderMap.put("detail", "");
		// 附加数据 由于拆单，附加数据 是订单的id拼成的字符串 以','分割
		if (StringUtils.isNotBlank(weChatVo.getAttach())) {
			orderMap.put("attach", weChatVo.getAttach());
		}
		// ++商户订单号
		orderMap.put("out_trade_no", weChatVo.getPayOrderNo());
		// 标价币种 默认人民币：CNY
		orderMap.put("fee_type", WeChatConfig.FEE_TYPE_CNY);
		// ++标价金额 订单总金额，单位为分
		orderMap.put("total_fee", weChatVo.getTotalFee());
		// 终端IP
		orderMap.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());
		// 交易起始时间
		orderMap.put("time_start", getDateTime());
		// 交易结束时间
		// orderMap.put("time_expire", getDateTime());
		// 通知地址
		orderMap.put("notify_url", weChatVo.getNotifyUrl());
		// 交易类型 JSAPI-公众号支付，APP-APP支付
		orderMap.put("trade_type", WeChatConfig.TRADE_TYPE_APP);
		// 生成签名
		String sign = generateSign(orderMap, WeChatConfig.APP_PARTNER_KEY);
		orderMap.put("sign", sign);// 签名
		return orderMap;
	}

	/**
	 * 服务号或小程序 构建退款订单
	 * 
	 * @param weChatVo
	 * @return
	 * @throws Exception
	 */
	public static SortedMap<String, String> buildRefundOrder_jsapi(WeChatVo weChatVo) throws Exception {
		if (weChatVo == null || StringUtils.isAnyBlank(weChatVo.getAppId(), weChatVo.getMchId(), weChatVo.getPayTradeNo(), weChatVo.getRefundOrderNo(), weChatVo.getTotalFee(),
				weChatVo.getRefundFee(), weChatVo.getPartnerKey(), weChatVo.getNotifyUrl())) {
			LOG.error("参数（appid，mch_id）为空");
			throw new Exception("请求参数不能为空");
		}
		SortedMap<String, String> orderMap = new TreeMap<String, String>();
		// ++公众账号ID
		orderMap.put("appid", weChatVo.getAppId());
		// ++商户号
		orderMap.put("mch_id", weChatVo.getMchId());
		// 随机字符串 UUID
		orderMap.put("nonce_str", generateNonceStr());
		// 签名类型
		orderMap.put("sign_type", WeChatConfig.SIGN_TYPE_MD5);
		// ++微信订单号-二选一
		orderMap.put("transaction_id", weChatVo.getPayTradeNo());
		// // ++商户订单号-二选一
		// orderMap.put("out_trade_no", weChatVo.getPayOrderNo());
		// ++微信订单号-二选一
		orderMap.put("out_refund_no", weChatVo.getRefundOrderNo());
		// ++订单总金额，单位为分，只能为整数
		orderMap.put("total_fee", weChatVo.getTotalFee());
		// ++退款总金额，订单总金额，单位为分，只能为整数
		orderMap.put("refund_fee", weChatVo.getRefundFee());
		// 退款货币类型，需与支付一致，或者不填 默认人民币：CNY
		orderMap.put("refund_fee_type", WeChatConfig.FEE_TYPE_CNY);
		// ++退款原因 若商户传入，会在下发给用户的退款消息中体现退款原因
		if (StringUtils.isNotBlank(weChatVo.getRefundDesc())) {
			orderMap.put("refund_desc", weChatVo.getRefundDesc());
		}
		// 通知地址
		orderMap.put("notify_url", weChatVo.getNotifyUrl());
		// 生成签名
		String sign = generateSign(orderMap, weChatVo.getPartnerKey());
		orderMap.put("sign", sign);// 签名
		return orderMap;
	}

	/**
	 * 服务号或小程序 提现
	 *
	 * @param weChatVo
	 * @return
	 * @throws Exception
	 */
	public static SortedMap<String, String> buildCashWithdrawalOrder_jsapi(WeChatVo weChatVo) throws Exception {
		/*
		 * if (weChatVo == null || StringUtils.isAnyBlank(weChatVo.getAppId(),
		 * weChatVo.getMchId(), weChatVo.getPayOrderNo(), weChatVo.getRefundOrderNo(),
		 * weChatVo.getTotalFee(), weChatVo.getPartnerKey(), weChatVo.getNotifyUrl())) {
		 * LOG.error("参数（appid，mch_id）为空"); throw new Exception("请求参数不能为空"); }
		 */
		SortedMap<String, String> orderMap = new TreeMap<String, String>();
		// ++公众账号ID
		orderMap.put("mch_appid", weChatVo.getAppId());
		// ++商户号
		orderMap.put("mchid", weChatVo.getMchId());
		// 微信openId
		orderMap.put("openid", weChatVo.getOpenId());
		// 随机字符串 UUID
		orderMap.put("nonce_str", generateNonceStr());
		// 签名类型
		orderMap.put("check_name", WeChatConfig.CHECK_NAME);
		// ++微信订单号-二选一
		orderMap.put("partner_trade_no", weChatVo.getPartnerTradeNo());
		// // ++商户订单号-二选一
		// orderMap.put("out_trade_no", weChatVo.getPayOrderNo());
		// ++退款总金额，订单总金额，单位为分，只能为整数
		orderMap.put("amount", weChatVo.getTotalFee());
		// 提现ip
		orderMap.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());

		orderMap.put("key", weChatVo.getPartnerKey());
		// ++提现说明
		if (StringUtils.isNotBlank(weChatVo.getDesc())) {
			orderMap.put("desc", weChatVo.getDesc());
		}
		// 生成签名
		String sign = generateSign(orderMap, weChatVo.getPartnerKey());
		orderMap.put("sign", sign);// 签名
		return orderMap;
	}

	/**
	 * 构建查询订单参数
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public static SortedMap<String, String> buildOrderQuery(WeChatVo weChatVo) throws Exception {
		if (weChatVo == null || StringUtils.isAnyBlank(weChatVo.getAppId(), weChatVo.getMchId(), weChatVo.getPayOrderNo(), weChatVo.getPartnerKey())) {
			LOG.error("参数（appid，mch_id，out_trade_no）为空");
			throw new Exception("请求参数不能为空");
		}
		SortedMap<String, String> orderMap = new TreeMap<String, String>();
		// 应用ID
		orderMap.put("appid", weChatVo.getAppId());
		// 商户号
		orderMap.put("mch_id", weChatVo.getMchId());
		// // ++微信订单号 二选一
		// orderMap.put("transaction_id", orderNo);
		// ++商户订单号 二选一
		orderMap.put("out_trade_no", weChatVo.getPayOrderNo());
		// 随机字符串 UUID
		orderMap.put("nonce_str", generateNonceStr());
		// 签名类型
		orderMap.put("sign_type", WeChatConfig.SIGN_TYPE_MD5);
		// 生成签名
		String sign = generateSign(orderMap, weChatVo.getPartnerKey());
		orderMap.put("sign", sign);// 签名
		return orderMap;
	}

	/**
	 * 构建查询退款参数
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public static SortedMap<String, String> buildRefundQuery(WeChatVo weChatVo) throws Exception {
		if (weChatVo == null || StringUtils.isAnyBlank(weChatVo.getAppId(), weChatVo.getMchId(), weChatVo.getRefundOrderNo(), weChatVo.getPartnerKey())) {
			LOG.error("参数（appid，mch_id，out_refund_no）为空");
			throw new Exception("请求参数不能为空");
		}
		SortedMap<String, String> orderMap = new TreeMap<String, String>();
		// 应用ID
		orderMap.put("appid", weChatVo.getAppId());
		// 商户号
		orderMap.put("mch_id", weChatVo.getMchId());
		// 随机字符串 UUID
		orderMap.put("nonce_str", generateNonceStr());
		// 签名类型
		orderMap.put("sign_type", WeChatConfig.SIGN_TYPE_MD5);
		// // ++微信订单号 四选一
		// orderMap.put("transaction_id", weChatVo.getPayTradeNo());
		// // ++商户订单号 四选一
		// orderMap.put("out_trade_no", weChatVo.getPayOrderNo());
		// ++商户退款单号 四选一
		orderMap.put("out_refund_no", weChatVo.getRefundOrderNo());
		// // ++微信退款单号 四选一
		// orderMap.put("refund_id", weChatVo.getRefundTradeNo());
		// 生成签名
		String sign = generateSign(orderMap, weChatVo.getPartnerKey());
		orderMap.put("sign", sign);// 签名
		return orderMap;
	}

	/**
	 * 生成签名
	 * 
	 * @param parameters
	 * @param key
	 *            商户号对应的密钥或应用对应的秘钥
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String generateSign(SortedMap<String, String> parameters, String key) throws Exception {
		if (MapUtils.isEmpty(parameters) || StringUtils.isBlank(key)) {
			LOG.error("generateSign--参数parameters，key为空");
			throw new Exception("请求参数不能为空");
		}
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> keySets = parameters.entrySet();
		// 参数名ASCII码从小到大排序
		keySets.stream().sorted();
		Iterator<Entry<String, String>> iterator = keySets.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String tmpKey = (String) entry.getKey();
			String tmpValue = (String) entry.getValue();
			if (StringUtils.isNotBlank(tmpValue) && !"sign".equals(tmpKey) && !"key".equals(tmpKey)) {
				sb.append(tmpKey + "=" + tmpValue + "&");
			}
		}
		// 最后加密时添加商户密钥/应用秘钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
		sb.append("key=" + key);
		LOG.info("签名字符串是{}", sb.toString());
		return EncryptUtils.md5Encode(sb.toString()).toUpperCase();
	}

	/**
	 * 校验签名
	 * 
	 * @param parameters
	 * @param APP_KEY
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean verifySign(SortedMap<String, String> parameters, String key) {
		if (MapUtils.isEmpty(parameters)) {
			LOG.error("verifySign--参数parameters为空");
			return false;
		}
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> keySets = parameters.entrySet();
		// 参数名ASCII码从小到大排序
		keySets.stream().sorted();
		Iterator<Entry<String, String>> iterator = keySets.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String tmpKey = (String) entry.getKey();
			String tmpValue = (String) entry.getValue();
			if (StringUtils.isNotBlank(tmpValue) && !"sign".equalsIgnoreCase(tmpKey)) {
				sb.append(tmpKey + "=" + tmpValue + "&");
			}
		}
		sb.append("key=" + key);
		// 算出签名
		String mysign = EncryptUtils.md5Encode(sb.toString()).toUpperCase();
		String weChatSign = ((String) parameters.get("sign")).toUpperCase();
		return mysign.equals(weChatSign);
	}

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return 返回微信服务器响应的信息
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			MyX509TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes(WeChatConfig.CHARSET_UTF8));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, WeChatConfig.CHARSET_UTF8);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			LOG.error("连接超时：{}", ce);
		} catch (Exception e) {
			LOG.error("https请求异常：{}", e);
		}
		return null;
	}

	/**
	 * map转xml
	 * 
	 * @param parameters
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String mapToXml(SortedMap<String, String> parameters) {
		if (MapUtils.isEmpty(parameters)) {
			LOG.error("mapToXml--参数parameters为空");
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set<Entry<String, String>> keySets = parameters.entrySet();
		Iterator<Entry<String, String>> iterator = keySets.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String tmpKey = (String) entry.getKey();
			String tmpValue = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(tmpKey) || "body".equalsIgnoreCase(tmpKey) || "sign".equalsIgnoreCase(tmpKey)) {
				sb.append("<" + tmpKey + ">" + "<![CDATA[" + tmpValue + "]]></" + tmpKey + ">");
			} else {
				sb.append("<" + tmpKey + ">" + tmpValue + "</" + tmpKey + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据
	 * 
	 * @param xmlStr
	 * @return Map<String, String>
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static SortedMap<String, String> xmlToMap(String xmlData) throws JDOMException, IOException {
		if (StringUtils.isBlank(xmlData)) {
			LOG.error("参数xmlData为空");
			return null;
		}
		xmlData = xmlData.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
		SortedMap<String, String> xmlMap = new TreeMap<>();
		InputStream in = new ByteArrayInputStream(xmlData.getBytes(WeChatConfig.CHARSET_UTF8));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List<Element> elements = root.getChildren();
		Iterator<Element> elementIterator = elements.iterator();
		while (elementIterator.hasNext()) {
			Element e = (Element) elementIterator.next();
			String name = e.getName();
			String value = "";
			List<Element> childElements = e.getChildren();
			if (CollectionUtils.isEmpty(childElements)) {
				value = e.getTextNormalize();
			} else {
				value = getChildrenText(childElements);
			}
			xmlMap.put(name, value);
		}
		// 关闭流
		in.close();
		return xmlMap;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List<Element> children) {
		StringBuffer sb = new StringBuffer();
		if (CollectionUtils.isNotEmpty(children)) {
			Iterator<Element> elementIterator = children.iterator();
			while (elementIterator.hasNext()) {
				Element e = elementIterator.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				sb.append("<" + name + ">");
				List<Element> childElements = e.getChildren();
				// 递归
				if (CollectionUtils.isNotEmpty(childElements)) {
					sb.append(XmlUtils.getChildrenText(childElements));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}

	/**
	 * 获取xml编码字符集
	 * 
	 * @param xmlStr
	 * @return String
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static String getXmlEncoding(String xmlStr) throws JDOMException, IOException {
		InputStream in = new ByteArrayInputStream(xmlStr.getBytes());
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		in.close();
		return (String) doc.getProperty("encoding");
	}

	/**
	 * 支付成功(失败)后返回给微信的参数
	 * 
	 * @param return_code
	 * @param return_msg
	 * @return
	 */
	public static String returnXml(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
	}

	// 微信公众号相关方法
	/**
	 * 生成权限认证签名
	 * 
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String generateAuthSign(SortedMap<String, String> parameters) {
		if (MapUtils.isEmpty(parameters)) {
			LOG.error("generateAuthSign--参数parameters为空");
			return null;
		}
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> keySets = parameters.entrySet();
		// 参数名ASCII码从小到大排序
		keySets.stream().sorted();
		Iterator<Entry<String, String>> iterator = keySets.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if (StringUtils.isNotBlank(value)) {
				sb.append(key.toLowerCase() + "=" + value + "&");
			}
		}
		String str = sb.toString();
		if (str.endsWith("&")) {
			str = str.substring(0, str.length() - 1);
		}
		return EncryptUtils.sha1Encode(str).toLowerCase();
	}

	/**
	 * 获取 jsapi_ticket
	 * 
	 * @param token
	 * @return
	 * @throws IOException
	 */
	public static String getJsapiTicketByToken(String token) throws IOException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", token);
		paramMap.put("type", "jsapi");
		return HttpUtils.doGet(WeChatConfig.JSAPI_TICKET_URL, paramMap);
	}

	/**
	 * 获取token
	 * 
	 * @return
	 */
	public static String getTokenForSp() throws IOException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("grant_type", WeChatConfig.GRANT_TYPE);
		paramMap.put("appid", WeChatConfig.SP_APP_ID);
		paramMap.put("secret", WeChatConfig.SP_APP_SECRET);
		return HttpUtils.doGet(WeChatConfig.TOKEN_URL, paramMap);
	}

	/**
	 * 获取token
	 * 
	 * @return
	 */
	public static String getTokenForMp() throws IOException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("grant_type", WeChatConfig.GRANT_TYPE);
		paramMap.put("appid", WeChatConfig.MP_APP_ID);
		paramMap.put("secret", WeChatConfig.MP_APP_SECRET);
		return HttpUtils.doGet(WeChatConfig.TOKEN_URL, paramMap);
	}

	/**
	 * 获取access_token
	 * 
	 * @return
	 */
	public static String getAccessTokenForMp() throws IOException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("grant_type", WeChatConfig.GRANT_TYPE);
		paramMap.put("appid", WeChatConfig.MP_APP_ID);
		paramMap.put("secret", WeChatConfig.MP_APP_SECRET);
		return HttpUtils.doGet(WeChatConfig.TOKEN_URL, paramMap);
	}

	/**
	 * 微信APP登录 通过code获取access_token
	 * 
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public static String getAccessTokenForApp(String code) throws IOException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("appid", WeChatConfig.APP_ID);
		paramMap.put("secret", WeChatConfig.APP_SECRET);
		paramMap.put("code", code);
		paramMap.put("grant_type", WeChatConfig.OAUTH_GRANT_TYPE);
		return HttpUtils.doGet(WeChatConfig.ACCESS_TOKEN_URL, paramMap);
	}

	/**
	 * 检验授权凭证（access_token）是否有效
	 * 
	 * @param access_token
	 * @param openid
	 * @return
	 * @throws IOException
	 */
	public static boolean checkAccessTokenForApp(String access_token, String openid) throws IOException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", access_token);
		paramMap.put("openid", openid);
		String result = HttpUtils.doGet(WeChatConfig.APP_CHECK_ACCESS_TOKEN_URL, paramMap);
		if (StringUtils.isNotBlank(result)) {
			JSONObject jsonObject = JSONObject.parseObject(result);
			if ("0".equals(jsonObject.get("errcode")) && "ok".equals(jsonObject.get("errmsg"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 微信APP授权登录 获取用户个人信息
	 * 
	 * @param access_token
	 * @param openid
	 * @return
	 * @throws IOException
	 */
	public static String getUserInfoByAccessTokenForApp(String access_token, String openid) throws IOException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", access_token);
		paramMap.put("openid", openid);
		paramMap.put("lang", WeChatConfig.LANG);
		return HttpUtils.doGet(WeChatConfig.USERINFO_URL, paramMap);
	}

	/**
	 * 微信服务号授权登录 通过code获取access_token
	 * 
	 * @return
	 */
	public static String getOauthTokenForSp(String code) throws IOException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("appid", WeChatConfig.SP_APP_ID);
		paramMap.put("secret", WeChatConfig.SP_APP_SECRET);
		paramMap.put("code", code);
		paramMap.put("grant_type", WeChatConfig.OAUTH_GRANT_TYPE);
		return HttpUtils.doGet(WeChatConfig.ACCESS_TOKEN_URL, paramMap);
	}

	/**
	 * 微信小程序授权登录 通过code获取session_key
	 * 
	 * @return
	 */
	public static String getSessionKeyForMp(String code) throws IOException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("appid", WeChatConfig.MP_APP_ID);
		paramMap.put("secret", WeChatConfig.MP_APP_SECRET);
		paramMap.put("js_code", code);
		paramMap.put("grant_type", WeChatConfig.OAUTH_GRANT_TYPE);
		return HttpUtils.doGet(WeChatConfig.SESSION_KEY_URL, paramMap);
	}

	/**
	 * 微信授权登录 获取用户信息
	 * 
	 * @param access_token
	 * @param openid
	 * @return
	 * @throws IOException
	 */
	public static String getUserinfoByAccessTokenAndOpenId(String access_token, String openid) throws IOException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", access_token);
		paramMap.put("openid", openid);
		paramMap.put("lang", WeChatConfig.LANG);
		return HttpUtils.doGet(WeChatConfig.USERINFO_URL, paramMap);
	}

	/**
	 * 将元转成分
	 *
	 * @param money
	 * @return BigDecimal
	 */
	public static String yuanToFen(BigDecimal money) {
		if (money == null) {
			LOG.error("参数（money）为空");
			return "0";
		}
		return String.valueOf(money.multiply(BigDecimal.valueOf(100)).longValue());
	}

	/**
	 * 将元转成分
	 *
	 * @param money
	 * @return BigDecimal
	 */
	public static String yuanToFen(String money) {
		if (StringUtils.isBlank(money)) {
			LOG.error("参数（money）为空");
			return "0";
		}
		return String.valueOf((new BigDecimal(money)).multiply(BigDecimal.valueOf(100)).intValue());
	}

	/**
	 * 生成随机字符串
	 * 
	 * @return
	 */
	public static String generateNonceStr() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	/**
	 * 返回当前时间戳，单位秒如1412000000
	 * 
	 */
	public static long getCurrentTime() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * 
	 * @author hongyang.jiang
	 */
	public static String getDateTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return simpleDateFormat.format(new Date());
	}

	/**
	 * 获取unix时间，从1970-01-01 00:00:00开始的秒数
	 * 
	 * @param date
	 * @return long
	 */
	public static long getUnixTime(Date date) {
		if (null == date) {
			return 0;
		}
		return date.getTime() / 1000;
	}

	// -----------流操作

	/**
	 * 把流转为字符串
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static String inputStreamToString(InputStream inputStream) throws Exception {
		if (inputStream == null) {
			return null;
		}
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer, WeChatConfig.CHARSET_UTF8);
		return writer.toString();
	}

	/**
	 * 解密微信用户信息
	 * 
	 * @param encryptedData
	 * @param sessionKey
	 * @param iv
	 * @return JSONObject
	 * @throws Exception
	 */
	public static JSONObject decryptUserInfo(String encryptedData, String sessionKey, String iv) throws Exception {
		try {
			// 被加密的数据
			byte[] dataByte = com.blade.kit.Base64.decode(encryptedData);
			// 加密秘钥
			byte[] keyByte = com.blade.kit.Base64.decode(sessionKey);
			// 偏移量
			byte[] ivByte = com.blade.kit.Base64.decode(iv);
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, WeChatConfig.CHARSET_UTF8);
				return JSONObject.parseObject(result);
			}
		} catch (Exception e) {
			LOG.error("解密用户信息异常，{}", e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 解密退款返回的数据 </br>
	 * 解密步骤如下： （1）对加密串A做base64解码，得到加密串B </br>
	 * （2）对商户key做md5，得到32位小写key*(key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	 * )</br>
	 * （3）用key*对加密串B做AES-256-ECB解密（PKCS7Padding）
	 * 
	 * @param req_info
	 * @param partner_key
	 * @return
	 * @throws Exception
	 */
	public static SortedMap<String, String> decryptReqInfo(String req_info, String partner_key) throws Exception {
		LOG.info("解密的信息为：{}", req_info);
		// 被加密的数据
		byte[] dataByte = com.blade.kit.Base64.decode(req_info);
		// 加密秘钥
		byte[] password = EncryptUtils.md5Encode(partner_key).toLowerCase().getBytes();
		// 初始化
		Security.addProvider(new BouncyCastleProvider());
		// 创建密码器
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
		// 使用密钥初始化，设置为解密模式
		SecretKeySpec spec = new SecretKeySpec(password, "AES");
		cipher.init(Cipher.DECRYPT_MODE, spec);
		// 执行操作
		byte[] resultByte = cipher.doFinal(dataByte);
		if (ArrayUtils.isNotEmpty(resultByte)) {
			String result = new String(resultByte, WeChatConfig.CHARSET_UTF8);
			return xmlToMap(result);
		}
		return null;
	}

	/**
	 * 得到申请退款的 信息方法
	 * 
	 * @param mchId
	 * @param url
	 * @param xml
	 *            传送到微信服务器的 数据包
	 * @return
	 * @throws Exception
	 */
	public static String refundHttpsRequest(String mchId, String url, String xml, String certPath) throws Exception {
		// 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		LOG.info("--安全证书路径--", certPath);
		// 指向你的证书的绝对路径，带着证书去访问
		FileInputStream instream = new FileInputStream(new File(certPath));
		try {
			// 下载证书时的密码、默认密码是你的MCHID mch_id
			keyStore.load(instream, mchId.toCharArray());
		} finally {
			instream.close();
		}
		// 下载证书时的密码、默认密码是你的MCHID mch_id
		SSLContext sslcontext = org.apache.http.conn.ssl.SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
		SSLConnectionSocketFactory ssl = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssl).build();
		try {
			HttpPost httppost = new HttpPost(url);
			LOG.error("executing request{}", httppost.getRequestLine());
			httppost.setEntity(new StringEntity(xml, WeChatConfig.CHARSET_UTF8));
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				// 返回
				HttpEntity entity = response.getEntity();
				String content = EntityUtils.toString(response.getEntity(), WeChatConfig.CHARSET_UTF8);
				EntityUtils.consume(entity);
				return content;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 得到申请退款的 信息方法
	 * 
	 * @param mchId
	 * @param url
	 * @param xml
	 *            传送到微信服务器的 数据包
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getCashWithdrawalForMpInfo(String mchId, String url, String xml) throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		LOG.error("安全证书---" + "F:/cert/ynwk/apiclient_cert.p12");
		FileInputStream instream = new FileInputStream(new File("F:/cert/ynwk/apiclient_cert.p12"));
		try {
			keyStore.load(instream, mchId.toCharArray());
		} finally {
			instream.close();
		}
		SSLContext sslcontext = org.apache.http.conn.ssl.SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
		SSLConnectionSocketFactory ssl = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssl).build();
		// 返回结果信息
		String content = "";
		try {
			HttpPost httppost = new HttpPost(url);
			LOG.error("executing request" + httppost.getRequestLine());
			httppost.setEntity(new StringEntity(xml, "utf-8"));
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				// 返回
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						content += text;
					}
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		if (StringUtils.isNotBlank(content)) {
			return xmlToMap(content);
		} else {
			return null;
		}
	}

}