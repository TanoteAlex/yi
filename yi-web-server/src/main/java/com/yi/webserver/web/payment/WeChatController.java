package com.yi.webserver.web.payment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.order.OrderEnum;
import com.yi.core.payment.PaymentUtils;
import com.yi.core.payment.weChat.WeChatAuthService;
import com.yi.core.payment.weChat.WeChatCashWithdrawal;
import com.yi.core.payment.weChat.WeChatConfig;
import com.yi.core.payment.weChat.WeChatNotifyService;
import com.yi.core.payment.weChat.WeChatPayService;
import com.yi.core.payment.weChat.WeChatVo;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

/**
 * 微信相关服务
 *
 * @author xuyh
 */
@Api(tags = "微信相关服务控制层")
@RestController
@RequestMapping("/weChat")
public class WeChatController {

	private static final Logger LOG = LoggerFactory.getLogger(WeChatController.class);

	@Resource
	private WeChatAuthService weChatAuthService;

	@Resource
	private WeChatPayService weChatPayService;

	@Resource
	private WeChatNotifyService weChatNotifyService;

	@Resource
	private WeChatCashWithdrawal weChatCashWithdrawal;

	/**
	 * 微信公众号获取JSAPI微信认证
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "微信公众号获取JSAPI微信认证", notes = "微信公众号获取JSAPI微信认证")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "url", value = "路径", required = true, dataType = "String")
	})
	@RequestMapping(value = "/getJsapiAuthForSp", method = RequestMethod.GET)
	public RestResult getJsapiAuthForSp(@RequestParam("url") String url, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(url)) {
			return RestUtils.error("请求参数不能为空");
		}
		LOG.info("--- 获取微信jspai权限认证");
		try {
			Map<String, Object> resultMap = weChatAuthService.getJsapiAuthForSp(url);
			return RestUtils.success(resultMap);
		} catch (Exception e) {
			LOG.error("getJsapiAuthForSp error: {}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	// /**
	// * 微信小程序 获取JSAPI微信认证
	// *
	// * @param request
	// * @param response
	// * @return
	// */
	// @RequestMapping(value = "/getJsapiAuthForMp", method = RequestMethod.GET)
	// public RestResult getJsapiAuthForMp(@RequestParam("url") String url,
	// HttpServletRequest request, HttpServletResponse response) {
	// if (StringUtils.isBlank(url)) {
	// return RestUtils.error("请求参数不能为空");
	// }
	// LOG.info("--- 获取微信jspai权限认证");
	// try {
	// Map<String, Object> resultMap = weChatAuthService.getJsapiAuthForMp(url);
	// return RestUtils.success(resultMap);
	// } catch (Exception e) {
	// LOG.error("getJsapiAuthForMp error: {}", e.getMessage(), e);
	// return RestUtils.error(e.getMessage());
	// }
	// }

	/**
	 * 微信公众号授权登录
	 *
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "微信公众号授权登录", notes = "微信公众号授权登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "code", value = "code码", required = true, dataType = "String")
	})
	@RequestMapping(value = "/authLoginForSp", method = RequestMethod.GET)
	public RestResult authLoginForSp(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("--- 根据code获取用户信息 ---");
		try {
			Object result = weChatAuthService.authLoginForSp(code);
			return RestUtils.success(result);
		} catch (Exception e) {
			LOG.error("authLoginForSp error: {}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 微信小程序 授权登录
	 *
	 * @param code
	 * @param encryptedData
	 * @param iv
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "微信小程序 授权登录", notes = "微信小程序 授权登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "code", value = "code码", required = true, dataType = "String"),
			@ApiImplicitParam(name = "encryptedData", value = "encryptedData", required = true, dataType = "String"),
			@ApiImplicitParam(name = "iv", value = "iv", required = true, dataType = "String")
	})
	@RequestMapping(value = "/authLoginForMp", method = RequestMethod.GET)
	public RestResult authLoginForMp(@RequestParam("code") String code, @RequestParam("encryptedData") String encryptedData, @RequestParam("iv") String iv,
			@RequestParam(name = "parentId", required = false) Integer parentId, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("--- 根据code获取用户信息 ---");
		try {
			Object result = weChatAuthService.authLoginForMp(code, encryptedData, iv, parentId);
			return RestUtils.success(result);
		} catch (Exception e) {
			LOG.error("authLoginForMp error: {}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * app 微信授权登录
	 *
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "app 微信授权登录", notes = "app 微信授权登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "code", value = "code码", required = true, dataType = "String")
	})
	@RequestMapping(value = "/authLoginForApp", method = RequestMethod.GET)
	public RestResult authLoginForApp(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("--- 获取APP微信权限认证");
		try {
			Object result = weChatAuthService.authLoginForApp(code);
			return RestUtils.success(result);
		} catch (Exception e) {
			LOG.error("authLoginForApp error: {}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 微信服务号支付 统一下单
	 *
	 * @param weChatVo
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "微信服务号支付 统一下单", notes = "微信服务号支付 统一下单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "weChatVo", value = "支付信息", required = true, dataType = "WeChatVo")
	})
	@RequestMapping(value = "/getUnifiedOrderForSp", method = RequestMethod.POST)
	public RestResult getUnifiedOrderForSp(@RequestBody WeChatVo weChatVo, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("--- 微信服务号支付，统一下单开始");
		try {
			SortedMap<String, String> resultMap = weChatPayService.getUnifiedOrderForSp(weChatVo);
			return RestUtils.success(resultMap);
		} catch (Exception e) {
			LOG.error("微信支付 异常{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 微信小程序支付 统一下单
	 *
	 * @param weChatVo
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "微信小程序支付 统一下单", notes = "微信小程序支付 统一下单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "weChatVo", value = "支付信息", required = true, dataType = "WeChatVo")
	})
	@RequestMapping(value = "/getUnifiedOrderForMp", method = RequestMethod.POST)
	public RestResult getUnifiedOrderForMp(@RequestBody WeChatVo weChatVo, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("--- 微信服务号支付，统一下单开始");
		try {
			SortedMap<String, String> resultMap = weChatPayService.getUnifiedOrderForMp(weChatVo);
			return RestUtils.success(resultMap);
		} catch (Exception e) {
			LOG.error("微信支付 异常{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 微信APP支付 统一下单
	 *
	 * @param weChatVo
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "微信APP支付 统一下单", notes = "微信APP支付 统一下单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "weChatVo", value = "支付信息", required = true, dataType = "WeChatVo")
	})
	@RequestMapping(value = "/getUnifiedOrderForApp", method = RequestMethod.POST)
	public RestResult getUnifiedOrderForApp(@RequestBody WeChatVo weChatVo, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("--- 微信APP支付，统一下单开始");
		try {
			SortedMap<String, String> resultMap = weChatPayService.getUnifiedOrderForApp(weChatVo);
			return RestUtils.success(resultMap);
		} catch (Exception e) {
			LOG.error("微信支付 异常{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 服务号 微信支付回调
	 *
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "服务号 微信支付回调", notes = "服务号 微信支付回调")
	@RequestMapping(value = "/payNotifyForSp")
	public void payNotifyForSp(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("微信支付，回调开始");
		response.setCharacterEncoding(WeChatConfig.CHARSET_UTF8);
		response.setContentType(WeChatConfig.CONTENT_TYPE_XML);
		try (BufferedReader bufferedReader = request.getReader()) {
			String line = "";
			StringBuffer notityXml = new StringBuffer("");
			while ((line = bufferedReader.readLine()) != null) {
				notityXml.append(line);
			}
			LOG.info("微信回调返回数据--->", notityXml.toString());
			String returnXml = weChatNotifyService.payNotify(PaymentUtils.xmlToMap(notityXml.toString()), WeChatConfig.SP_PARTNER_KEY, OrderEnum.PAYMENT_CHANNEL_SP);
			response.getWriter().write(returnXml);
		} catch (Exception e) {
			LOG.error("微信支付，回调失败{}", e.getMessage(), e);
		}
	}

	/**
	 * 小程序 微信支付回调
	 *
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "小程序 微信支付回调", notes = "小程序 微信支付回调")
	@RequestMapping(value = "/payNotifyForMp")
	public void payNotifyForMp(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("微信支付，回调开始");
		response.setCharacterEncoding(WeChatConfig.CHARSET_UTF8);
		response.setContentType(WeChatConfig.CONTENT_TYPE_XML);
		try (BufferedReader bufferedReader = request.getReader()) {
			String line = "";
			StringBuffer notityXml = new StringBuffer("");
			while ((line = bufferedReader.readLine()) != null) {
				notityXml.append(line);
			}
			LOG.info("微信回调返回数据--->", notityXml.toString());
			String returnXml = weChatNotifyService.payNotify(PaymentUtils.xmlToMap(notityXml.toString()), WeChatConfig.MP_PARTNER_KEY, OrderEnum.PAYMENT_CHANNEL_MP);
			response.getWriter().write(returnXml);
		} catch (Exception e) {
			LOG.error("微信支付，回调失败{}", e.getMessage(), e);
		}
	}

	/**
	 * APP 微信支付回调
	 *
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "APP 微信支付回调", notes = "APP 微信支付回调")
	@RequestMapping(value = "/payNotifyForApp")
	public void payNotifyForApp(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("微信支付回调开始");
		response.setCharacterEncoding(WeChatConfig.CHARSET_UTF8);
		response.setContentType(WeChatConfig.CONTENT_TYPE_XML);
		try (BufferedReader bufferedReader = request.getReader()) {
			String line = "";
			StringBuffer notityXml = new StringBuffer("");
			while ((line = bufferedReader.readLine()) != null) {
				notityXml.append(line);
			}
			LOG.info("微信回调返回数据--->", notityXml.toString());
			String returnXml = weChatNotifyService.payNotify(PaymentUtils.xmlToMap(notityXml.toString()), WeChatConfig.APP_PARTNER_KEY, OrderEnum.PAYMENT_CHANNEL_APP_WECHAT);
			response.getWriter().write(returnXml);
		} catch (Exception e) {
			LOG.error("微信支付，回调失败{}", e.getMessage(), e);
		}
	}

	/**
	 * 服务号 微信退款回调
	 *
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "服务号 微信退款回调", notes = "服务号 微信退款回调")
	@RequestMapping(value = "/refundNotifyForSp")
	public void refundNotifyForSp(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("服务号微信退款，回调开始");
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), WeChatConfig.CHARSET_UTF8))) {
			String line = "";
			StringBuffer notityXml = new StringBuffer("");
			while ((line = bufferedReader.readLine()) != null) {
				notityXml.append(line);
			}
			LOG.info("微信回调返回数据--->{}", notityXml.toString());
			String returnXml = weChatNotifyService.refundNotify(PaymentUtils.xmlToMap(notityXml.toString()), WeChatConfig.SP_PARTNER_KEY);
			response.getWriter().write(returnXml);
		} catch (Exception e) {
			LOG.error("微信退款，回调失败{}", e.getMessage(), e);
		}
	}

	/**
	 * 小程序 微信退款回调
	 *
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "小程序 微信退款回调", notes = "小程序 微信退款回调")
	@RequestMapping(value = "/refundNotifyForMp")
	public void refundNotifyForMp(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("微信退款，回调开始");
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), WeChatConfig.CHARSET_UTF8))) {
			String line = "";
			StringBuffer notityXml = new StringBuffer("");
			while ((line = bufferedReader.readLine()) != null) {
				notityXml.append(line);
			}
			LOG.info("微信回调返回数据--->", notityXml.toString());
			String returnXml = weChatNotifyService.refundNotify(PaymentUtils.xmlToMap(notityXml.toString()), WeChatConfig.MP_PARTNER_KEY);
			response.getWriter().write(returnXml);
		} catch (Exception e) {
			LOG.error("微信退款，回调失败{}", e.getMessage(), e);
		}
	}

	/**
	 * APP 微信退款回调
	 *
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "APP 微信退款回调", notes = "APP 微信退款回调")
	@RequestMapping(value = "/refundNotifyForApp")
	public void refundNotifyForApp(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("微信退款，回调开始");
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), WeChatConfig.CHARSET_UTF8))) {
			String line = "";
			StringBuffer notityXml = new StringBuffer("");
			while ((line = bufferedReader.readLine()) != null) {
				notityXml.append(line);
			}
			LOG.info("微信回调返回数据--->", notityXml.toString());
			String returnXml = weChatNotifyService.refundNotify(PaymentUtils.xmlToMap(notityXml.toString()), WeChatConfig.APP_PARTNER_KEY);
			response.getWriter().write(returnXml);
		} catch (Exception e) {
			LOG.error("微信退款，回调失败{}", e.getMessage(), e);
		}
	}

	/**
	 * 微信公众号提现
	 *
	 * @param money
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "微信公众号提现", notes = "微信公众号提现")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "money", value = "金额", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "memberId", value = "会员ID", required = true, dataType = "Int"),
	})
	@RequestMapping(value = "/cashWithdrawalForSp")
	public RestResult cashWithdrawalForSp(Integer money, Integer memberId) {
		LOG.info("微信公众号提现开始");
		try {
			return RestUtils.successWhenNotNull(weChatCashWithdrawal.cashWithdrawalForSp(money, memberId));
		} catch (Exception e) {
			LOG.error("微信公众号提现，提现失败{}", e.getMessage(), e);
			return RestUtils.error("提现失败！");
		}
	}

	/**
	 * 微信小程序提现
	 *
	 * @param money
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "微信小程序提现", notes = "微信小程序提现")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "money", value = "金额", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "memberId", value = "会员ID", required = true, dataType = "Int"),
	})
	@RequestMapping(value = "/cashWithdrawalForMp")
	public RestResult cashWithdrawalForMp(Integer money, Integer memberId) {
		LOG.info("小程序微信提现开始");
		try {
			return RestUtils.successWhenNotNull(weChatCashWithdrawal.cashWithdrawalForMp(money, memberId));
		} catch (Exception e) {
			LOG.error("微信小程序提现，提现失败{}", e.getMessage(), e);
			return RestUtils.error("提现失败！");
		}
	}

	/**
	 * APP微信提现
	 *
	 * @param money
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "APP微信提现", notes = "APP微信提现")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "money", value = "金额", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "memberId", value = "会员ID", required = true, dataType = "Int"),
	})
	@RequestMapping(value = "/cashWithdrawalForApp")
	public RestResult cashWithdrawalForApp(Integer money, Integer memberId) {
		LOG.info("APP微信提现开始");
		try {
			return RestUtils.successWhenNotNull(weChatCashWithdrawal.cashWithdrawalForApp(money, memberId));
		} catch (Exception e) {
			LOG.error("APP微信小程序提现，提现失败{}", e.getMessage(), e);
			return RestUtils.error("提现失败！");
		}
	}

}
