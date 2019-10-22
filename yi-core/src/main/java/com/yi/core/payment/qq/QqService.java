package com.yi.core.payment.qq;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.vdurmont.emoji.EmojiParser;
import com.yi.base.utils.HttpUtils;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IMemberService;
import com.yihz.common.exception.BusinessException;

/**
 * QQ相关服务
 * 
 * @author xuyh
 *
 */
@Component
public class QqService {

	private static final Logger LOG = LoggerFactory.getLogger(QqService.class);

	@Resource
	private QqConfig qqConfig;

	@Resource
	private IMemberService memberService;

	/**
	 * QQ授权登录
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public Object authLoginForQq(String accessToken) throws Exception {
		// 获取open_id
		String openId = this.getOpenIdByAccessToken(accessToken);
		if (StringUtils.isBlank(openId)) {
			LOG.error("获取open_id为空");
			throw new BusinessException("系统异常，请稍后重试");
		}
		// 获取union_id
		String unionId = this.getUnionIdByAccessToken(accessToken);
		if (StringUtils.isBlank(unionId)) {
			LOG.error("获取union_id为空");
			// throw new BusinessException("系统异常，请稍后重试");
		}
		// 获取user_info
		JSONObject userInfo = this.getUserInfo(accessToken, openId);
		if (userInfo == null) {
			LOG.error("获取user_info为空");
			throw new BusinessException("系统异常，请稍后重试");
		}
		// 校验授权用户是否存在
		Member checkMember = memberService.checkMemberByQq(unionId, openId, userInfo.getString("figureurl_2"), EmojiParser.removeAllEmojis(userInfo.getString("nickname")));
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
	 * 根据accessToken获取openId </br>
	 * 错误示例：callback({"error":100016,"error_description":"access token check
	 * failed"} );</br>
	 * 正确示例：callback( {"client_id":"10XXXXX49","openid":"CF2XXXXXXXX9F4C"} );
	 * 
	 * @param accessToken
	 * @return
	 */
	private String getOpenIdByAccessToken(String accessToken) throws Exception {
		if (StringUtils.isBlank(accessToken)) {
			LOG.error("accessToken 不能为空");
			return null;
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", accessToken);
		String result = HttpUtils.doGet(qqConfig.getOpenIdUrl(), paramMap);
		JSONObject resultJson = JSONObject.parseObject(result);
		if (resultJson != null && StringUtils.isNotBlank(resultJson.getString("openid"))) {
			return resultJson.getString("openid");
		} else {
			LOG.error("获取open_id失败，原因如下：{}", result);
		}
		return null;
	}

	/**
	 * 获取用户信息</br>
	 * 参数说明 描述 ret 返回码 </br>
	 * msg 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。</br>
	 * nickname 用户在QQ空间的昵称。</br>
	 * figureurl 大小为30×30像素的QQ空间头像URL。</br>
	 * figureurl_1 大小为50×50像素的QQ空间头像URL。</br>
	 * figureurl_2 大小为100×100像素的QQ空间头像URL。</br>
	 * figureurl_qq_1 大小为40×40像素的QQ头像URL。</br>
	 * figureurl_qq_2
	 * 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。</br>
	 * gender 性别。 如果获取不到则默认返回"男"
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	private JSONObject getUserInfo(String accessToken, String openId) throws Exception {
		if (StringUtils.isBlank(accessToken)) {
			LOG.error("accessToken 不能为空");
			return null;
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", accessToken);
		paramMap.put("oauth_consumer_key", qqConfig.getAppId());
		paramMap.put("openid", openId);
		String result = HttpUtils.doGet(qqConfig.getUserInfoUrl(), paramMap);
		JSONObject resultJson = JSONObject.parseObject(result);
		if (resultJson != null && qqConfig.getSuccessCode().equals(resultJson.getString("ret"))) {
			return resultJson;
		} else {
			LOG.error("获取user_info失败，原因如下：{}", result);
		}
		return null;
	}

	/**
	 * 获取unionid </br>
	 * callback({</br>
	 * 成功： </br>
	 * "client_id":"YOUR_APPID", </br>
	 * "openid":"YOUR_OPENID",</br>
	 * "unionid":"YOUR_UNIONID" </br>
	 * 失败： </br>
	 * "error":100016, </br>
	 * "error_description":"access token check failed",</br>
	 * });
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	private String getUnionIdByAccessToken(String accessToken) throws Exception {
		if (StringUtils.isBlank(accessToken)) {
			LOG.error("accessToken 不能为空");
			return null;
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("access_token", accessToken);
		String result = HttpUtils.doGet(qqConfig.getUnionIdUrl(), paramMap);
		JSONObject resultJson = JSONObject.parseObject(result);
		if (resultJson != null && StringUtils.isNotBlank(resultJson.getString("unionid"))) {
			return resultJson.getString("unionid");
		} else {
			LOG.error("获取union_id失败，原因如下：{}", result);
		}
		return null;
	}

}
