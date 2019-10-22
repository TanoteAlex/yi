package com.yi.core.payment.weChat;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.vdurmont.emoji.EmojiParser;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IMemberService;
import com.yi.core.payment.PaymentUtils;
import com.yihz.common.exception.BusinessException;

/**
 * 微信认证
 * 
 * @author xuyh
 *
 */
@Component
@Transactional
public class WeChatAuthService {

	private static final Logger LOG = LoggerFactory.getLogger(WeChatAuthService.class);

	/** 授权token */
	private static Map<String, Object> TOKEN_MAP = new ConcurrentHashMap<>();
	/** jsapi_ticket */
	private static Map<String, Object> JSAPI_TICKET_MAP = new ConcurrentHashMap<>();
	/** 用户授权登录token */
	private static Map<String, Object> OAUTH_TOKEN_MAP = new ConcurrentHashMap<>();

	/** app微信授权登录token */
	private static Map<String, Object> APP_ACCESS_TOKEN_MAP = new ConcurrentHashMap<>();

	@Resource
	private IMemberService memberService;

	/**
	 * 微信公众平台 获取权限认证 数据
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getJsapiAuthForSp(String url) throws Exception {
		String token = getTokenForSp();
		if (StringUtils.isBlank(token)) {
			LOG.error("获取token为空");
			throw new BusinessException("权限认证失败，请稍后重试");
		}
		LOG.info("获取的token为{}", token);
		String jsapi_ticket = getJsapiTicketByTokenForSp(token);
		if (StringUtils.isBlank(jsapi_ticket)) {
			LOG.error("获取jsapi_ticket为空");
			throw new BusinessException("权限认证失败，请稍后重试");
		}
		LOG.info("获取的jsapi_ticket为{}", jsapi_ticket);
		String noncestr = PaymentUtils.generateNonceStr();
		long timestamp = PaymentUtils.getCurrentTime();
		// 生成签名 数据
		SortedMap<String, String> paramMap = new TreeMap<>();
		paramMap.put("jsapi_ticket", jsapi_ticket);
		paramMap.put("noncestr", noncestr);
		paramMap.put("timestamp", String.valueOf(timestamp));
		paramMap.put("url", url);
		String signature = PaymentUtils.generateAuthSign(paramMap);
		if (StringUtils.isAnyBlank(signature)) {
			throw new BusinessException("权限认证失败，请稍后重试");
		}
		Map<String, Object> resutlMap = new HashMap<>();
		resutlMap.put("appId", WeChatConfig.SP_APP_ID);
		resutlMap.put("timestamp", timestamp);
		resutlMap.put("nonceStr", noncestr);
		resutlMap.put("signature", signature);
		return resutlMap;
	}

	/**
	 * 微信小程序 获取权限认证 数据
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getJsapiAuthForMp(String url) throws Exception {
		String token = getTokenForMp();
		if (StringUtils.isBlank(token)) {
			LOG.error("获取token为空");
			throw new BusinessException("权限认证失败，请稍后重试");
		}
		LOG.info("获取的token为{}", token);
		String jsapi_ticket = getJsapiTicketByTokenForMp(token);
		if (StringUtils.isBlank(jsapi_ticket)) {
			LOG.error("获取jsapi_ticket为空");
			throw new BusinessException("权限认证失败，请稍后重试");
		}
		LOG.info("获取的jsapi_ticket为{}", jsapi_ticket);
		String noncestr = PaymentUtils.generateNonceStr();
		long timestamp = PaymentUtils.getCurrentTime();
		// 生成签名 数据
		SortedMap<String, String> paramMap = new TreeMap<>();
		paramMap.put("jsapi_ticket", jsapi_ticket);
		paramMap.put("noncestr", noncestr);
		paramMap.put("timestamp", String.valueOf(timestamp));
		paramMap.put("url", url);
		String signature = PaymentUtils.generateAuthSign(paramMap);
		if (StringUtils.isAnyBlank(signature)) {
			throw new BusinessException("权限认证失败，请稍后重试");
		}
		Map<String, Object> resutlMap = new HashMap<>();
		resutlMap.put("appId", WeChatConfig.MP_APP_ID);
		resutlMap.put("timestamp", timestamp);
		resutlMap.put("nonceStr", noncestr);
		resutlMap.put("signature", signature);
		return resutlMap;
	}

	/**
	 * 微信服务号授权登录</br>
	 * 用户授权登录 通过code获取用户信息
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Object authLoginForSp(String code) throws Exception {
		if (StringUtils.isBlank(code)) {
			LOG.error("参数（code）为空");
			throw new BusinessException("请求参数不能为空");
		}
		JSONObject jsonObject = getOauthTokenByCodeForSp(code);
		if (jsonObject == null || StringUtils.isAnyBlank(jsonObject.getString("access_token"), jsonObject.getString("openid"))) {
			LOG.error("微信access_token为空");
			throw new BusinessException("系统异常，请稍后重试");
		}
		// 获取用户数据
		String result = PaymentUtils.getUserinfoByAccessTokenAndOpenId(jsonObject.getString("access_token"), jsonObject.getString("openid"));
		if (StringUtils.isBlank(result)) {
			LOG.error("获取用户信息失败");
			throw new BusinessException("系统异常，请稍后重试");
		}
		JSONObject userInfo = JSONObject.parseObject(result);
		// 校验授权用户是否存在
		Member checkMember = memberService.checkMemberByWeChatForSp(userInfo.getString("unionid"), userInfo.getString("openid"), userInfo.getString("headimgurl"),
				EmojiParser.removeAllEmojis(Optional.ofNullable(userInfo.getString("nickname")).orElse("")));
		if (checkMember != null) {
			userInfo.put("memberId", checkMember.getId());
			userInfo.put("isLogin", Boolean.TRUE);
		} else {
			userInfo.put("memberId", 0);
			userInfo.put("isLogin", Boolean.FALSE);
		}
		return userInfo;
	}

	/**
	 * 微信小程序授权登录</br>
	 * 用户授权登录 通过code获取用户信息
	 * 
	 * @param code
	 * @param encryptedData
	 * @param iv
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public Object authLoginForMp(String code, String encryptedData, String iv, Integer parentId) throws Exception {
		if (StringUtils.isAnyBlank(code, encryptedData, iv)) {
			LOG.error("参数（code，encryptedData，iv）为空");
			throw new BusinessException("请求参数不能为空");
		}
		// 通过code获取session_key
		String session = PaymentUtils.getSessionKeyForMp(code);
		LOG.info("通过code={}，获取session_key，返回数据为{}", code, session);
		JSONObject sessionObject = JSONObject.parseObject(session);
		if (sessionObject == null || StringUtils.isAnyBlank(sessionObject.getString("session_key"), sessionObject.getString("openid"))) {
			LOG.error("获取微信session_key为空");
			throw new BusinessException("系统异常，请稍后重试");
		}
		String session_key = sessionObject.getString("session_key");
		JSONObject userInfo = PaymentUtils.decryptUserInfo(encryptedData, session_key, iv);
		if (userInfo == null) {
			LOG.error("获取微信用户数据为空");
			throw new BusinessException("系统异常，请稍后重试");
		}
		// 校验授权用户是否存在
		Object resultUserInfo = memberService.checkMemberByWeChatForMp(userInfo, parentId);
		return resultUserInfo;
	}

	/**
	 * 微信小程序绑定手机号</br>
	 * 通过code获取用户信息
	 * 
	 * @param code
	 * @param encryptedData
	 * @param iv
	 * @return String
	 * @throws Exception
	 */
	public String getUserInfoByBindPhoneForMp(String code, String encryptedData, String iv) throws Exception {
		if (StringUtils.isAnyBlank(code, encryptedData, iv)) {
			LOG.error("参数（code，encryptedData，iv）为空");
			throw new BusinessException("请求参数不能为空");
		}
		// 通过code获取session_key
		String session = PaymentUtils.getSessionKeyForMp(code);
		LOG.info("通过code={}，获取session_key，返回数据为{}", code, session);
		JSONObject sessionObject = JSONObject.parseObject(session);
		if (sessionObject == null || StringUtils.isAnyBlank(sessionObject.getString("session_key"), sessionObject.getString("openid"))) {
			LOG.error("获取微信session_key为空");
			throw new BusinessException("系统异常，请稍后重试");
		}
		String session_key = sessionObject.getString("session_key");
		JSONObject userInfo = PaymentUtils.decryptUserInfo(encryptedData, session_key, iv);
		if (userInfo == null) {
			LOG.error("获取微信用户数据为空");
			throw new BusinessException("系统异常，请稍后重试");
		}
		return userInfo.getString("purePhoneNumber");
	}

	/**
	 * 用户授权登录 通过code获取网页授权接口调用凭证
	 * 
	 * @param code
	 * @return access_token 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 *         expires_in access_token接口调用凭证超时时间，单位（秒） refresh_token
	 *         用户刷新access_token openid
	 *         用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID scope
	 *         用户授权的作用域，使用逗号（,）分隔 unionid
	 * @throws Exception
	 */
	public JSONObject getOauthTokenByCodeForSp(String code) throws Exception {
		// 从缓存中拿数据
		if (OAUTH_TOKEN_MAP.containsKey(code)) {
			JSONObject jsonObject = (JSONObject) OAUTH_TOKEN_MAP.get(code);
			if (jsonObject != null) {
				// 校验是否失效
				if (!checkExpires(jsonObject.getDate("receipt_time"), jsonObject.getLongValue("expires_in"))) {
					return jsonObject;
					// 失效就刷新数据 TODO
				}
			}
		}
		// 发起请求 获取数据
		String result = PaymentUtils.getOauthTokenForSp(code);
		LOG.info("返回结果result={}");
		if (StringUtils.isNotBlank(result)) {
			// 处理数据
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
				jsonObject.put("receipt_time", new Date());
				OAUTH_TOKEN_MAP.put(code, jsonObject);
				return jsonObject;
			}
		}
		return null;
	}

	/**
	 * 用户授权登录 通过code获取网页授权接口调用凭证
	 * 
	 * @param code
	 * @return access_token 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 *         expires_in access_token接口调用凭证超时时间，单位（秒） refresh_token
	 *         用户刷新access_token openid
	 *         用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID scope
	 *         用户授权的作用域，使用逗号（,）分隔 unionid
	 * @throws Exception
	 */
	// public JSONObject getOauthTokenByCodeForMp(String code) throws Exception {
	// // 从缓存中拿数据
	// if (OAUTH_TOKEN_MAP.containsKey(code)) {
	// JSONObject jsonObject = (JSONObject) OAUTH_TOKEN_MAP.get(code);
	// if (jsonObject != null) {
	// // 校验是否失效
	// if (!checkExpires(jsonObject.getDate("receipt_time"),
	// jsonObject.getLongValue("expires_in"))) {
	// return jsonObject;
	// // 失效就刷新数据 TODO
	// }
	// }
	// }
	// // 发起请求 获取数据
	// String result = PaymentUtils.getOauthTokenForMp(code);
	// LOG.info("返回结果result={}");
	// if (StringUtils.isNotBlank(result)) {
	// // 处理数据
	// JSONObject jsonObject = JSONObject.parseObject(result);
	// if (StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
	// jsonObject.put("receipt_time", new Date());
	// OAUTH_TOKEN_MAP.put(code, jsonObject);
	// return jsonObject;
	// }
	// }
	// return null;
	// }

	/**
	 * 微信公众平台 获取基础支持的access_token不同
	 * 
	 * @return String
	 */
	public String getTokenForSp() throws IOException {
		// 从缓存中拿
		if (TOKEN_MAP.containsKey(WeChatConfig.SP_APP_ID)) {
			JSONObject jsonObject = (JSONObject) TOKEN_MAP.get(WeChatConfig.SP_APP_ID);
			if (jsonObject != null) {
				// 校验是否失效
				if (!checkExpires(jsonObject.getDate("receipt_time"), jsonObject.getLongValue("expires_in"))) {
					return jsonObject.getString("access_token");
				}
			}
		}
		// 发起请求 获取token
		String result = PaymentUtils.getTokenForSp();
		LOG.info("获取access_token，返回的结果是：{}", result);
		if (StringUtils.isNotBlank(result)) {
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
				jsonObject.put("receipt_time", new Date());
				TOKEN_MAP.put(WeChatConfig.SP_APP_ID, jsonObject);
				return jsonObject.getString("access_token");
			}
		}
		return null;
	}

	/**
	 * 微信小程序 获取基础支持的access_token不同
	 * 
	 * @return String
	 */
	public String getTokenForMp() throws IOException {
		// 从缓存中拿
		if (TOKEN_MAP.containsKey(WeChatConfig.MP_APP_ID)) {
			JSONObject jsonObject = (JSONObject) TOKEN_MAP.get(WeChatConfig.MP_APP_ID);
			if (jsonObject != null) {
				// 校验是否失效
				if (!checkExpires(jsonObject.getDate("receipt_time"), jsonObject.getLongValue("expires_in"))) {
					return jsonObject.getString("access_token");
				}
			}
		}
		// 发起请求 获取token
		String result = PaymentUtils.getTokenForMp();
		LOG.info("获取access_token，返回的结果是：{}", result);
		if (StringUtils.isNotBlank(result)) {
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
				jsonObject.put("receipt_time", new Date());
				TOKEN_MAP.put(WeChatConfig.MP_APP_ID, jsonObject);
				return jsonObject.getString("access_token");
			}
		}
		return null;
	}

	/**
	 * 微信公众平台 获取jsapi_ticket
	 * 
	 * @param token
	 * @return
	 * @throws IOException
	 */
	public String getJsapiTicketByTokenForSp(String token) throws IOException {
		// 从缓存中拿
		if (JSAPI_TICKET_MAP.containsKey(WeChatConfig.SP_APP_ID)) {
			JSONObject jsonObject = (JSONObject) JSAPI_TICKET_MAP.get(WeChatConfig.SP_APP_ID);
			if (jsonObject != null) {
				// 校验是否失效
				if (!checkExpires(jsonObject.getDate("receipt_time"), jsonObject.getLongValue("expires_in"))) {
					return jsonObject.getString("ticket");
				}
			}
		}
		// 发起请求 获取jsapi_ticket
		String result = PaymentUtils.getJsapiTicketByToken(token);
		LOG.info("getJsapiTicket，获取jsapi_ticket，返回的结果是：{}", result);
		if (StringUtils.isNotBlank(result)) {
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (StringUtils.isNotBlank(jsonObject.getString("ticket"))) {
				jsonObject.put("receipt_time", new Date());
				JSAPI_TICKET_MAP.put(WeChatConfig.SP_APP_ID, jsonObject);
				return jsonObject.getString("ticket");
			}
		}
		return null;
	}

	/**
	 * 微信小程序 获取jsapi_ticket
	 * 
	 * @param token
	 * @return
	 * @throws IOException
	 */
	public String getJsapiTicketByTokenForMp(String token) throws IOException {
		// 从缓存中拿
		if (JSAPI_TICKET_MAP.containsKey(WeChatConfig.MP_APP_ID)) {
			JSONObject jsonObject = (JSONObject) JSAPI_TICKET_MAP.get(WeChatConfig.MP_APP_ID);
			if (jsonObject != null) {
				// 校验是否失效
				if (!checkExpires(jsonObject.getDate("receipt_time"), jsonObject.getLongValue("expires_in"))) {
					return jsonObject.getString("ticket");
				}
			}
		}
		// 发起请求 获取jsapi_ticket
		String result = PaymentUtils.getJsapiTicketByToken(token);
		LOG.info("获取jsapi_ticket，返回的结果是：{}", result);
		if (StringUtils.isNotBlank(result)) {
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (StringUtils.isNotBlank(jsonObject.getString("ticket"))) {
				jsonObject.put("receipt_time", new Date());
				JSAPI_TICKET_MAP.put(WeChatConfig.MP_APP_ID, jsonObject);
				return jsonObject.getString("ticket");
			}
		}
		return null;
	}

	/**
	 * 校验是否失效
	 * 
	 * @param receipt_time
	 * @return true 失效 false未失效
	 */
	private boolean checkExpires(Date receipt_time, long expires_in) {
		if (receipt_time == null) {
			return true;
		}
		if ((new Date()).getTime() < receipt_time.getTime() + expires_in * 1000) {
			return false;
		}
		return true;
	}

	/**
	 * APP微信授权登录
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Object authLoginForApp(String code) throws Exception {
		if (StringUtils.isBlank(code)) {
			LOG.error("APP授权登录失败，请求参数（code）为空");
			throw new BusinessException("请求参数不能为空");
		}
		JSONObject jsonObject = getAccessTokenByCodeForApp(code);
		if (jsonObject == null) {
			LOG.error("获取access_token为空");
			throw new BusinessException("认证失败，请稍后重试");
		}
		LOG.info("获取的accessToken为{}", jsonObject);
		// TODO 校验暂时屏掉
		// 检验授权凭证（access_token）是否有效
		// boolean flag =
		// PaymentUtils.checkAccessTokenForApp(jsonObject.getString("access_token"),
		// jsonObject.getString("openid"));
		// if (!flag) {
		// LOG.error("getWeChatAuthForApp，认证失效");
		// throw new BusinessException("认证失效，请重新授权");
		// }
		String result = PaymentUtils.getUserInfoByAccessTokenForApp(jsonObject.getString("access_token"), jsonObject.getString("openid"));
		if (StringUtils.isBlank(result)) {
			LOG.error("获取用户信息失败");
			throw new BusinessException("认证失败，请稍后重试");
		}
		JSONObject userInfo = JSONObject.parseObject(result);
		if (StringUtils.isBlank(userInfo.getString("openid"))) {
			LOG.error("获取用户信息失败");
			throw new BusinessException("认证失败，请稍后重试");
		}
		// 校验授权用户是否存在
		Member checkMember = memberService.checkMemberByWeChatForApp(userInfo.getString("unionid"), userInfo.getString("openid"), userInfo.getString("headimgurl"),
				EmojiParser.removeAllEmojis(Optional.ofNullable(userInfo.getString("nickname")).orElse("")));
		if (checkMember != null) {
			userInfo.put("memberId", checkMember.getId());
			userInfo.put("isLogin", Boolean.TRUE);
		} else {
			userInfo.put("memberId", 0);
			userInfo.put("isLogin", Boolean.FALSE);
		}
		return userInfo;
	}

	/**
	 * APP微信授权登录 </br>
	 * 通过code获取access_token的接口
	 * 
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public JSONObject getAccessTokenByCodeForApp(String code) throws IOException {
		// 从缓存中拿
		if (APP_ACCESS_TOKEN_MAP.containsKey(code)) {
			JSONObject jsonObject = (JSONObject) APP_ACCESS_TOKEN_MAP.get(code);
			if (jsonObject != null) {
				// 校验是否失效
				if (!checkExpires(jsonObject.getDate("receipt_time"), jsonObject.getLongValue("expires_in"))) {
					// return jsonObject.getString("access_token");
					return jsonObject;
				}
			}
		}
		// 发起请求 获取access_token
		String result = PaymentUtils.getAccessTokenForApp(code);
		LOG.info("获取access_token，返回的结果是：{}", result);
		if (StringUtils.isNotBlank(result)) {
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (StringUtils.isNotBlank(jsonObject.getString("access_token"))) {
				jsonObject.put("receipt_time", new Date());
				APP_ACCESS_TOKEN_MAP.put(code, jsonObject);
				// return jsonObject.getString("access_token");
				return jsonObject;
			}
		}
		return null;
	}

}
