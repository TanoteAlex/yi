//package com.yi.core.common;
//
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.collections.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONObject;
//import com.aliyuncs.CommonRequest;
//import com.aliyuncs.CommonResponse;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
//import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
//import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse.SmsSendDetailDTO;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.exceptions.ServerException;
//import com.aliyuncs.http.MethodType;
//import com.aliyuncs.profile.DefaultProfile;
//import com.aliyuncs.profile.IClientProfile;
//import com.yi.core.basic.BasicEnum;
//import com.yi.core.basic.domain.entity.SmsRecord;
//import com.yi.core.basic.service.ISmsRecordService;
//import com.yihz.common.exception.BusinessException;
//import com.yihz.common.utils.date.DateUtils;
//
///**
// * 短信服务
// * 
// * @author xuyh
// *
// */
//@Component
//public class NewSmsService {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(NewSmsService.class);
//
//	/** 存储发送的短信次数 */
//	private static Map<String, Integer> SMS_MAP = new ConcurrentHashMap<>(0);
//	/** 存储发送的短信 */
//	private static Map<String, Long> SMS_CODE_MAP = new ConcurrentHashMap<>(0);
//	/** 失效时间 毫秒 */
//	private static final int INVALID_TIME = 5 * 60 * 1000;
//
//	/** 当日发送次数 */
//	private static final int SEND_TIME = 6;
//
//	/** 云通信产品-短信API服务产品名称 */
//	private static final String PRODUCT = "Dysmsapi";
//	/** 云通信产品-短信API服务产品域名 */
//	private static final String DOMAIN = "dysmsapi.aliyuncs.com";
//	/** 云通信产品-版本 */
//	private static final String VERSION = "2017-05-25";
//	/** 阿里云短信发送状态 */
//	private static final String SUCCESS = "OK";
//	/** 阿里云 accessKey */
//	private static final String ACCESS_KEY_ID = "LTAIevctXMoHxG3j";
//	/** 阿里云 accessSecret */
//	private static final String ACCESS_KEY_SECRET = "BBODv7ugOOZKdXaGSd0rZmthZxXwtp";
//	/** 阿里云签名 */
//	private static final String SIGN_NAME = "美周壹壹优选";
//	/** 默认地区 */
//	private static final String REGION_DEFAULT = "default";
//	/** 杭州地区 */
//	private static final String REGION_HANG_ZHOU = "cn-hangzhou";
//	/** 端点名称 */
//	private static final String ENDPOINT_NAME = "cn-hangzhou";
//	/** 连接超时 */
//	private static final String CONNECT_TIMEOUT_KEY = "sun.net.client.defaultConnectTimeout";
//	/** 读取超时 */
//	private static final String READ_TIMEOUT_KEY = "sun.net.client.defaultReadTimeout";
//	/** 连接超时 */
//	private static final String DEFAULT_CONNECT_TIMEOUT = "10000";
//	/** 读取超时 */
//	private static final String DEFAULT_READ_TIMEOUT = "10000";
//	/** 短信服务 */
//	private static IAcsClient ACS_CLIENT;
//
//	@Resource
//	private ISmsRecordService smsRecordService;
//
//	static {
//		// 初始化ascClient
//		DefaultProfile profile = DefaultProfile.getProfile(REGION_DEFAULT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
//		ACS_CLIENT = new DefaultAcsClient(profile);
//	}
//
//	public void sendSms1(String templateCode, String phone, Map<String, String> params) {
//		CommonRequest request = new CommonRequest();
//		// request.setProtocol(ProtocolType.HTTPS);
//		request.setMethod(MethodType.POST);
//		request.setDomain(DOMAIN);
//		request.setVersion(VERSION);
//		request.setAction("SendSms");
//		request.putQueryParameter("PhoneNumbers", "15219468283");
//		request.putQueryParameter("SignName", "美周壹壹优选");
//		request.putQueryParameter("TemplateCode", "SMS_143925155");
//		request.putQueryParameter("TemplateParam", "{\"code\":\"5442\"}");
//		try {
//			CommonResponse response = ACS_CLIENT.getCommonResponse(request);
//			System.out.println(response.getData());
//		} catch (ServerException e) {
//			e.printStackTrace();
//		} catch (ClientException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 发送短信 异步处理
//	 * 
//	 * @param templateCode
//	 * @param phone
//	 * @param params
//	 */
//	@Async
//	public void sendSms(String templateCode, String phone, Map<String, String> params) {
//		try {
//			if (StringUtils.isBlank(templateCode)) {
//				LOGGER.error("短信模板有误：templateCode={}", templateCode);
//				throw new BusinessException("无效的短信模板类型：templateCode=" + templateCode);
//			}
//			// 组装请求对象-具体描述见控制台-文档部分内容
//			SendSmsRequest request = new SendSmsRequest();
//			// 使用post提交
//			request.setMethod(MethodType.POST);
//			// 必填:待发送手机号
//			request.setPhoneNumbers(phone.trim());
//			// 必填:短信签名-可在短信控制台中找到
//			request.setSignName(SIGN_NAME);
//			// 必填:短信模板-可在短信控制台中找到
//			request.setTemplateCode(templateCode);
//			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//			// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
//			request.setTemplateParam(JSONObject.toJSONString(params));
//			// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
//			// request.setSmsUpExtendCode("90997");
//			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//			String outId = UUID.randomUUID().toString();
//			request.setOutId(outId);
//			// 请求失败这里会抛ServerException,ClientException异常
//			SendSmsResponse sendSmsResponse = ACS_CLIENT.getAcsResponse(request);
//			/* 短信发送完毕 保存发送日志 */
//			SmsRecord smsRecord = new SmsRecord();
//			smsRecord.setPhone(phone.trim());
//			smsRecord.setTemplateCode(templateCode);
//			smsRecord.setTemplateParam(JSONObject.toJSONString(params));
//			smsRecord.setSendDate(new Date());
//			smsRecord.setSendCode(sendSmsResponse.getCode());
//			smsRecord.setSendMessage(sendSmsResponse.getMessage());
//			smsRecord.setSendBizId(sendSmsResponse.getBizId());
//			smsRecord.setSmsOutId(outId);
//			// 发送内容 留待线程从发送明细中更新
//			// smsLog.setSendContent(null);
//			if (sendSmsResponse.getCode() != null && SUCCESS.equals(sendSmsResponse.getCode())) {
//				// 发送成功 发送状态 留待线程从发送明细中 更新成功状态
//				// 1：等待回执，2：发送失败，3：发送成功
//				smsRecord.setSmsState(BasicEnum.SEND_STATUS_WAIT_REPORT.getCode());
//				// 添加临时存储数据
//				addCode(phone.trim(), params);
//				LOGGER.info("短信发送成功，发送回执ID为：{}", sendSmsResponse.getBizId());
//			} else {
//				// 发送失败的数据 直接记录
//				// 1：等待回执，2：发送失败，3：发送成功
//				smsRecord.setSmsState(BasicEnum.SEND_STATUS_FAILURE.getCode());
//				LOGGER.error("短信发送失败，手机号为{}，失败原因为：{}", phone, sendSmsResponse.getMessage());
//			}
//			smsRecordService.addSmsRecord(smsRecord);
//		} catch (Exception e) {
//			LOGGER.error("短信发送失败，系统异常", e);
//		}
//	}
//
//	/**
//	 * 校验短信验证码
//	 * 
//	 * @param code
//	 * @param phone
//	 * @return
//	 */
//	public boolean checkCode(String phone, String code) {
//		if (StringUtils.isAnyBlank(phone, code)) {
//			return false;
//		}
//		if (SMS_CODE_MAP.containsKey(phone.trim() + code.trim())) {
//			if (System.currentTimeMillis() - SMS_CODE_MAP.get(phone.trim() + code.trim()).longValue() < INVALID_TIME) {
//				return true;
//			}
//		}
//		// 清除失效的验证码
//		clearCode();
//		return false;
//	}
//
//	/**
//	 * 添加短信验证码
//	 * 
//	 * @param code
//	 * @param phone
//	 * @return
//	 */
//	@Async
//	public void addCode(String phone, Map<String, String> params) {
//		if (MapUtils.isNotEmpty(params)) {
//			if (params.containsKey("code") && StringUtils.isNotBlank(params.get("code"))) {
//				SMS_CODE_MAP.put(phone + params.get("code"), System.currentTimeMillis());
//			}
//		}
//		// 清除失效的验证码
//		clearCode();
//	}
//
//	/**
//	 * 将过期验证码删除
//	 */
//	@Async
//	public void clearCode() {
//		if (MapUtils.isNotEmpty(SMS_CODE_MAP)) {
//			for (Iterator<Map.Entry<String, Long>> it = SMS_CODE_MAP.entrySet().iterator(); it.hasNext();) {
//				Map.Entry<String, Long> item = it.next();
//				if (System.currentTimeMillis() - item.getValue().longValue() > INVALID_TIME) {
//					it.remove();
//				}
//			}
//		}
//	}
//
//	/**
//	 * 定时器任务调度 更新等待回执 的短信
//	 */
//	public void updateSmsSendState() {
//		try {
//			// 查询3天内待回执的短信
//			SmsRecord smsRecord = new SmsRecord();
//			smsRecord.setSendDate(DateUtils.addDays(new Date(), -3));
//			smsRecord.setSmsState(BasicEnum.SEND_STATUS_WAIT_REPORT.getCode());
//			List<SmsRecord> dbSmsRecords = smsRecordService.queryWaitReportSmsRecords(smsRecord);
//			if (CollectionUtils.isEmpty(dbSmsRecords)) {
//				LOGGER.info("没有待回执的短信,本次执行时间为:{}", DateUtils.getFormatTimestamp(new Date()));
//				return;
//			}
//			// 组装请求对象
//			QuerySendDetailsRequest request = new QuerySendDetailsRequest();
//			// 遍历等待回执的短信
//			for (SmsRecord tmp : dbSmsRecords) {
//				if (tmp != null) {
//					// 组装请求参数
//					// 必填-号码
//					request.setPhoneNumber(tmp.getPhone());
//					// 可选-调用发送短信接口时返回的BizId
//					request.setBizId(tmp.getSendBizId());
//					// 必填-短信发送的日期 支持30天内记录查询（可查其中一天的发送数据），格式yyyyMMdd
//					request.setSendDate(DateUtils.getFormatDate(tmp.getSendDate(), "yyyyMMdd"));
//					// 必填-页大小
//					request.setPageSize(10L);
//					// 必填-当前页码从1开始计数
//					request.setCurrentPage(1L);
//					// hint 此处可能会抛出异常，注意catch
//					QuerySendDetailsResponse querySendDetailsResponse = ACS_CLIENT.getAcsResponse(request);
//					// 获取返回结果
//					if (CollectionUtils.isNotEmpty(querySendDetailsResponse.getSmsSendDetailDTOs())) {
//						SmsSendDetailDTO smsSendDetailDTO = querySendDetailsResponse.getSmsSendDetailDTOs().get(0);
//						if (smsSendDetailDTO != null) {
//							// 更新短信记录 回执数据
//							// 1：等待回执，2：发送失败，3：发送成功
//							tmp.setSmsState(smsSendDetailDTO.getSendStatus().intValue());
//							tmp.setSendContent(smsSendDetailDTO.getContent());
//							tmp.setErrorCode(smsSendDetailDTO.getErrCode());
//							if (StringUtils.isNotBlank(smsSendDetailDTO.getReceiveDate())) {
//								tmp.setReceiveDate(DateUtils.parseDate(smsSendDetailDTO.getReceiveDate()));
//							}
//						}
//					}
//					smsRecordService.updateSmsRecord(tmp);
//				}
//			}
//		} catch (Exception e) {
//			LOGGER.error("更新短信状态时，系统异常！", e);
//		}
//	}
//
//}
