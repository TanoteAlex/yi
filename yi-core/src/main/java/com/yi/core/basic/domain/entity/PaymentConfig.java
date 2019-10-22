/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 支付配置
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class PaymentConfig implements java.io.Serializable {

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

	public PaymentConfig() {
	}

	public PaymentConfig(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 128)
	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public String getSignType() {
		return this.signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public String getSign() {
		return this.sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public String getNotifyUrl() {
		return this.notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getMchId() {
		return this.mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

}