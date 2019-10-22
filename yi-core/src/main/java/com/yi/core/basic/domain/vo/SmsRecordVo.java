/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.vo;

import java.util.Date;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;

import com.yihz.common.convert.domain.VoDomain;

/**
 * 短信记录
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class SmsRecordVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 短信记录ID
	 */
	@Max(9999999999L)
	private Integer id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 接收手机号
	 */
	@Length(max = 16)
	private String phone;
	/**
	 * 短信模板
	 */
	@Length(max = 127)
	private String templateCode;
	/**
	 * 短信模板参数
	 */
	@Length(max = 512)
	private String templateParam;
	/**
	 * 短信状态 1：等待回执，2：发送失败，3：发送成功
	 */
	@Max(127)
	private Integer smsState;
	/**
	 * 短信内容
	 */
	@Length(max = 512)
	private String sendContent;
	/**
	 * 发送时间
	 */
	private Date sendDate;
	/**
	 * 状态码-返回OK代表请求成功,其他错误码详见错误码列表
	 */
	@Length(max = 127)
	private String sendCode;
	/**
	 * 状态码的描述
	 */
	@Length(max = 512)
	private String sendMessage;
	/**
	 * 发送回执ID,可根据该ID查询具体的发送状态
	 */
	@Length(max = 127)
	private String sendBizId;
	/**
	 * 接收时间
	 */
	private Date receiveDate;
	/**
	 * 外部流水扩展字段
	 */
	@Length(max = 127)
	private String smsOutId;
	/**
	 * 运营商短信错误码
	 */
	@Length(max = 127)
	private String errorCode;
	// columns END

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTemplateCode() {
		return this.templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateParam() {
		return this.templateParam;
	}

	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
	}

	public Integer getSmsState() {
		return this.smsState;
	}

	public void setSmsState(Integer smsState) {
		this.smsState = smsState;
	}

	public String getSendContent() {
		return this.sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendCode() {
		return this.sendCode;
	}

	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}

	public String getSendMessage() {
		return this.sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	public String getSendBizId() {
		return this.sendBizId;
	}

	public void setSendBizId(String sendBizId) {
		this.sendBizId = sendBizId;
	}

	public Date getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getSmsOutId() {
		return this.smsOutId;
	}

	public void setSmsOutId(String smsOutId) {
		this.smsOutId = smsOutId;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}