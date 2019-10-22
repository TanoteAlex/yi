/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.vo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class PaymentConfigListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 支付配置ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 64)
	private String guid;
	/**
	 * 支付类型（1支付宝2微信）
	 */
	@NotNull
	private Integer payType;
	/**
	 * 支付宝分配给开发者的应用ID
	 */
	@NotBlank
	@Length(max = 32)
	private String appId;
	/**
	 * 接口名称
	 */
	@NotBlank
	@Length(max = 128)
	private String method;
	/**
	 * 请求使用的编码格式
	 */
	@NotBlank
	@Length(max = 10)
	private String charset;
	/**
	 * 商户生成签名字符串所使用的签名算法类型
	 */
	@NotBlank
	@Length(max = 10)
	private String signType;
	/**
	 * 商户请求参数的签名串
	 */
	@NotBlank
	@Length(max = 19)
	private String sign;
	/**
	 * 调用的接口版本，固定为：1.0
	 */
	@NotBlank
	@Length(max = 10)
	private String version;
	/**
	 * 支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https
	 */
	@NotBlank
	@Length(max = 10)
	private String notifyUrl;
	/**
	 * 商户收款账号
	 */
	@NotBlank
	@Length(max = 32)
	private String mchId;
	/**
	 * 交易过程生成签名的密钥
	 */
	@NotBlank
	@Length(max = 32)
	private String appKey;
	/**
	 * APPID对应的接口密码
	 */
	@NotBlank
	@Length(max = 32)
	private String appSecret;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 删除时间
	 */

	private Date delTime;
	// columns END

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return this.signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNotifyUrl() {
		return this.notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getMchId() {
		return this.mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

}