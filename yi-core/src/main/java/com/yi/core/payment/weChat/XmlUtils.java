package com.yi.core.payment.weChat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XML工具类
 * 
 * @author xuyh
 *
 */
public class XmlUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);

	/** 字符集 */
	public static final String CHARSET_NAME = "UTF-8";

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据
	 * 
	 * @param xmlStr
	 * @return Map<String, String>
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, String> doXmlParse(String xmlStr) throws JDOMException, IOException {
		if (StringUtils.isBlank(xmlStr)) {
			LOGGER.error("参数xmlStr为空");
			return null;
		}
		xmlStr = xmlStr.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
		Map<String, String> xmlMap = new HashMap<>();
		InputStream in = new ByteArrayInputStream(xmlStr.getBytes(CHARSET_NAME));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List<Element> elements = root.getChildren();
		Iterator<Element> elementIterator = elements.iterator();
		while (elementIterator.hasNext()) {
			Element e = (Element) elementIterator.next();
			String k = e.getName();
			String v = "";
			List<Element> childElements = e.getChildren();
			if (CollectionUtils.isEmpty(childElements)) {
				v = e.getTextNormalize();
			} else {
				v = XmlUtils.getChildrenText(childElements);
			}
			xmlMap.put(k, v);
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

	// public static String createXml(Map<String, Object> map) {
	// Set<Entry<String, Object>> set = map.entrySet();
	// set.iterator();
	// return null;
	// }

	// public static void main(String[] args) {
	// StringBuffer xmlStr = new StringBuffer();
	// xmlStr.append("<xml>");
	// xmlStr.append("<appid>wx2421b1c4370ec43b</appid>");
	// xmlStr.append("<attach>支付测试</attach>");
	// xmlStr.append("<body>APP支付测试</body>");
	// xmlStr.append("<mch_id>10000100</mch_id>");
	// xmlStr.append("<nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>");
	// xmlStr.append("<notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>");
	// xmlStr.append("<out_trade_no>1415659990</out_trade_no>");
	// xmlStr.append("<spbill_create_ip>14.23.150.211</spbill_create_ip>");
	// xmlStr.append("<total_fee>1</total_fee>");
	// xmlStr.append("<trade_type>APP</trade_type>");
	// xmlStr.append("<sign>0CB01533B8C1EF103065174F50BCA001</sign>");
	// xmlStr.append("</xml>");
	// System.out.println("解析前==" + xmlStr);
	// Map<String, String> doXMLParse = new HashMap<>();
	// try {
	// doXMLParse = XmlUtils.doXmlParse(xmlStr.toString());
	// } catch (JDOMException | IOException e) {
	// e.printStackTrace();
	// System.out.println("解析Map失败");
	// }
	// System.out.println("解析后==" + doXMLParse);
	// }

}
