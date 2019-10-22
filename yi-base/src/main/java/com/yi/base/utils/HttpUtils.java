package com.yi.base.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http 工具类
 * 
 * @author xuyh
 *
 */
public class HttpUtils {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

	private static final int CONNECT_TIMEOUT = 5000;

	private static final int SOCKET_TIMEOUT = 3000;

	private static final String CHARSET = "UTF-8";

	private static CloseableHttpClient httpclient;

	/** 初始化 */
	static {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}

	/**
	 * 发送GET请求
	 * 
	 * @param url
	 *            请求路径
	 * @param params
	 *            参数
	 * @param suffix
	 *            参数追加字符
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> params) throws IOException {
		if (StringUtils.isBlank(url)) {
			LOG.error("has an error,this parameter url is null");
			return null;
		}
		if (MapUtils.isNotEmpty(params)) {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
			for (String key : params.keySet()) {
				pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
			url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs), CHARSET);
		}

		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			httpGet.abort();
			throw new RuntimeException("HttpClient,error status code :" + statusCode);
		}
		HttpEntity entity = response.getEntity();
		String result = null;
		if (entity != null) {
			result = EntityUtils.toString(entity, CHARSET);
		}
		EntityUtils.consume(entity);
		response.close();
		return result;
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 *            请求路径
	 * @param params
	 *            参数
	 * @param suffix
	 *            参数追加字符
	 * @return
	 */
	public static String doPost(String url, SortedMap<String, String> params) throws IOException {
		if (StringUtils.isBlank(url)) {
			LOG.error("has an error,this parameter url is null");
			return null;
		}
		List<NameValuePair> pairs = null;
		if (MapUtils.isNotEmpty(params)) {
			pairs = new ArrayList<NameValuePair>(params.size());
			for (String key : params.keySet()) {
				pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
		}
		HttpPost httpPost = new HttpPost(url);
		if (CollectionUtils.isNotEmpty(pairs)) {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
		}
		CloseableHttpResponse response = httpclient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			httpPost.abort();
			throw new RuntimeException("HttpClient,error status code :" + statusCode);
		}
		HttpEntity entity = response.getEntity();
		String result = null;
		if (entity != null) {
			result = EntityUtils.toString(entity, CHARSET);
		}
		EntityUtils.consume(entity);
		response.close();
		return result;
	}

}
