package com.yi.webserver.web.payment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.payment.alipay.AlipayConfig;
import com.yi.core.payment.alipay.AlipayService;
import com.yi.core.payment.alipay.AlipayVo;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

/**
 * 支付宝相关服务
 * 
 * @author xuyh
 *
 */
@Api(tags = "支付宝相关服务控制层")
@RestController
@RequestMapping("/alipay")
public class AlipayController {

	private static final Logger LOG = LoggerFactory.getLogger(AlipayController.class);

	@Resource
	private AlipayService alipayService;

	/**
	 * 支付宝支付
	 * 
	 * @param alipayVo
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "支付宝支付", notes = "支付宝支付")
	@ApiImplicitParams({ @ApiImplicitParam(name = "alipayVo", value = "支付对象", required = true, dataType = "AlipayVo") })
	@RequestMapping(value = "/createPayOrder")
	public RestResult createPayOrder(@RequestBody AlipayVo alipayVo, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("支付宝支付，生成APP支付订单");
		try {
			String result = alipayService.createPayOrder(alipayVo);
			return RestUtils.success(result);
		} catch (Exception e) {
			LOG.error("支付宝支付，生成订单失败: ", e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 支付宝支付回调
	 * 
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "支付宝支付回调", notes = "支付宝支付回调")
	@RequestMapping(value = "/payNotify")
	public String payNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 1 将异步通知中收到的待验证所有参数都存放到map中
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> it = requestParams.keySet().iterator(); it.hasNext();) {
				String name = (String) it.next();
				String[] values = (String[]) requestParams.get(name);
				String value = "";
				for (int i = 0; i < values.length; i++) {
					value = (i == values.length - 1) ? value + values[i] : value + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, value);
			}
			// 2 调用支付宝回调方法处理订单数据
			return alipayService.alipayNotify(params);
		} catch (Exception e) {
			LOG.error("支付宝回调失败", e);
			return AlipayConfig.NOTIFY_FAIL;
		}
	}

	/**
	 * 支付宝退款回调
	 * 
	 * @param request
	 * @param response
	 */
	@Deprecated
	@ApiOperation(value = "支付宝退款回调", notes = "支付宝退款回调")
	@RequestMapping(value = "/refundNotify")
	public String refundNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			// // 1 将异步通知中收到的待验证所有参数都存放到map中
			// Map<String, String> params = new HashMap<String, String>();
			// Map<String, String[]> requestParams = request.getParameterMap();
			// for (Iterator<String> it = requestParams.keySet().iterator(); it.hasNext();)
			// {
			// String name = (String) it.next();
			// String[] values = (String[]) requestParams.get(name);
			// String value = "";
			// for (int i = 0; i < values.length; i++) {
			// value = (i == values.length - 1) ? value + values[i] : value + values[i] +
			// ",";
			// }
			// // 乱码解决，这段代码在出现乱码时使用。
			// // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			// params.put(name, value);
			// }
			// 2 调用支付宝回调方法处理订单数据
			return AlipayConfig.NOTIFY_SUCCESS;
		} catch (Exception e) {
			LOG.error("支付宝回调失败", e);
			return AlipayConfig.NOTIFY_FAIL;
		}
	}

}