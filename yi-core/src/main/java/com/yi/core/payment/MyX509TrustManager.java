package com.yi.core.payment;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 证书信任管理器（用于https请求）
 * 
 * @author xuyh
 *
 */
public class MyX509TrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

	// /**
	// * 得到申请退款的 信息方法
	// *
	// * @param mchId
	// * @param url
	// * @param xml
	// * 传送到微信服务器的 数据包
	// * @return
	// * @throws Exception
	// */
	// public static String refundHttpsRequest(String mchId, String url, String xml,
	// String certPath) throws Exception {
	// // 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
	// KeyStore keyStore = KeyStore.getInstance("PKCS12");
	// // LOG.info("--安全证书路径--", certPath);
	// // F:/cert/ynwk/apiclient_cert.p12
	// // 指向你的证书的绝对路径，带着证书去访问
	// FileInputStream instream = new FileInputStream(new File(certPath));
	// try {
	// // 下载证书时的密码、默认密码是你的MCHID mch_id
	// keyStore.load(instream, mchId.toCharArray());
	// } finally {
	// instream.close();
	// }
	// // 下载证书时的密码、默认密码是你的MCHID mch_id
	// SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,
	// mchId.toCharArray()).build();
	// SSLConnectionSocketFactory ssl = new SSLConnectionSocketFactory(sslcontext,
	// new String[] { "TLSv1" }, null,
	// SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	// CloseableHttpClient httpclient =
	// HttpClients.custom().setSSLSocketFactory(ssl).build();
	// // 返回结果信息
	// // String content = "";
	// try {
	// HttpPost httppost = new HttpPost(url);
	// // LOG.error("executing request{}", httppost.getRequestLine());
	// httppost.setEntity(new StringEntity(xml, WeChatConfig.CHARSET_UTF8));
	// CloseableHttpResponse response = httpclient.execute(httppost);
	// try {
	// // 返回
	// HttpEntity entity = response.getEntity();
	// String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	// // if (entity != null) {
	// // BufferedReader bufferedReader = new BufferedReader(new
	// // InputStreamReader(entity.getContent(), "utf-8"));
	// // String text;
	// // while ((text = bufferedReader.readLine()) != null) {
	// // content += text;
	// // }
	// // }
	// EntityUtils.consume(entity);
	// return jsonStr;
	// } finally {
	// response.close();
	// }
	// } finally {
	// httpclient.close();
	// }
	// // return content;
	// }

}
