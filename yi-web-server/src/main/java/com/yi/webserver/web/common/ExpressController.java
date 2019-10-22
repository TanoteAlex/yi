package com.yi.webserver.web.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.base.utils.HttpUtils;

/**
 * 快递查询
 *
 * @author xuyh
 *
 */
@Api(tags = "快递查询控制层")
@RestController
@RequestMapping("express")
public class ExpressController {


	private String key; //授权key

	private String customer;//实时查询公司编号

	public ExpressController(String key, String customer) {
		this.key = key;
		this.customer = customer;
	}

	public ExpressController() {
	}

	private final Logger LOG = LoggerFactory.getLogger(ExpressController.class);

	/**
	 * 实时查询请求地址
	 */
	private static final String SYNQUERY_URL = "http://poll.kuaidi100.com/poll/query.do";


	/**
	 * 快递100 查询快递进度
	 *
	 * @param type
	 * @param postid
	 * @return
	 */
	@ApiOperation(value = "快递100 查询快递进度", notes = "快递100 查询快递进度")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "快递类型", required = true, dataType = "String"),
			@ApiImplicitParam(name = "postid", value = "快递单号", required = true, dataType = "String"),
	})
	@RequestMapping(value = "query", method = RequestMethod.GET)
	public String query(@RequestParam("type") String type, @RequestParam("postid") String postid) {
		if (type.equals("baishisudi")){  // huitongkuaidi是百世速递的type
			type = "huitongkuaidi";
		}
	    	String key = "ZkQvEPum3457";    //贵司的授权key
	    	String customer = "B66934FB54FD711E20A663DDF56E03C3";  	//贵司的查询公司编号
			String com = type;			//快递公司编码
			String num = postid;	//快递单号
			String phone = "";				//手机号码后四位
			String from = "";				//出发地
			String to = "";					//目的地
			int resultv2 = 0;				//开启行政规划解析

			ExpressController demo = new ExpressController(key, customer);
			String result = demo.synQueryData(com, num, phone, from, to, resultv2);
			System.out.println(result);

			return result;
		}


		/**
		 * 实时查询快递单号
		 * @param com			快递公司编码
		 * @param num			快递单号
		 * @param phone			手机号
		 * @param from			出发地城市
		 * @param to			目的地城市
		 * @param resultv2		开通区域解析功能：0-关闭；1-开通
		 * @return
		 */
		public String synQueryData(String com, String num, String phone, String from, String to, int resultv2) {

			StringBuilder param = new StringBuilder("{");
			param.append("\"com\":\"").append(com).append("\"");
			param.append(",\"num\":\"").append(num).append("\"");
			param.append(",\"phone\":\"").append(phone).append("\"");
			param.append(",\"from\":\"").append(from).append("\"");
			param.append(",\"to\":\"").append(to).append("\"");
			if(1 == resultv2) {
				param.append(",\"resultv2\":1");
			} else {
				param.append(",\"resultv2\":0");
			}
			param.append("}");

			Map<String, String> params = new HashMap<String, String>();
			params.put("customer", this.customer);
			String sign = MD5Utils.encode(param + this.key + this.customer);
			params.put("sign", sign);
			params.put("param", param.toString());

			return this.post(params);
		}

		/**
		 * 发送post请求
		 */
		public String post(Map<String, String> params) {
			StringBuffer response = new StringBuffer("");

			BufferedReader reader = null;
			try {
				StringBuilder builder = new StringBuilder();
				for (Map.Entry<String, String> param : params.entrySet()) {
					if (builder.length() > 0) {
						builder.append('&');
					}
					builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
					builder.append('=');
					builder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
				}
				byte[] bytes = builder.toString().getBytes("UTF-8");

				URL url = new URL(SYNQUERY_URL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setReadTimeout(3000);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
				conn.setDoOutput(true);
				conn.getOutputStream().write(bytes);

				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

				String line = "";
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != reader) {
						reader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return response.toString();
		}
	}
