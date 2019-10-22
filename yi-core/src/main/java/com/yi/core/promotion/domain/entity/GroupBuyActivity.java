/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.auth.domain.entity.User;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 团购活动
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class GroupBuyActivity implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	* 
	*/
	@Max(9999999999L)
	private int id;
	/**
	 * 编码
	 */
	@NotBlank
	@Length(max = 32)
	private String guid;
	/**
	 * 团购活动名称
	 */
	@NotBlank
	@Length(max = 128)
	private String activityName;
	/**
	 * 优先级
	 */
	@NotNull
	@Max(127)
	private int priority;
	/**
	 * 团购活动类型
	 */
	@NotNull
	@Max(127)
	private int type;
	/**
	 * 优惠方式类型
	 */
	@Max(127)
	private int promotionType;
	/**
	 * 团购活动主办方
	 */
	@Length(max = 32)
	private String sponsor;
	/**
	 * 团购活动封面
	 */
	@NotBlank
	@Length(max = 255)
	private String coverUrl;
	/**
	 * 是否包邮
	 */
	@NotNull
	private Integer hasPost;
	/**
	 * 是否支持优惠券抵扣
	 */
	@NotNull
	private Integer hasCoupon;
	/**
	 * 审核状态 :0为未审核,1为审核
	 */
	@NotNull
	private Integer audited;
	/**
	 * 发布状态:0为未发布,1为以发布
	 */
	@NotNull
	private Integer published;
	/**
	 * 库存状态
	 */
	@NotNull
	@Max(127)
	private int stockState;
	/**
	 * 删除状态
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 备注
	 */
	@Length(max = 255)
	private String remark;
	/**
	 * 审核人
	 */
	private User auditUser;
	/**
	 * 审核时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date auditDate;
	/**
	 * 发布人
	 */
	private User publishUser;
	/**
	 * 发布时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date publishDate;
	/**
	 * 更新人(更新，终结，删除)
	 */
	private User updateUser;
	/**
	 * 更新时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date updateDate;
	/**
	 * 创建人
	 */
	private User createUser;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 终结状态 0:未终结，1：已终结（如果活动有多个时间段的，则必须最后一个时间段结束后，才能变为已终结状态）
	 */
	@NotNull
	private Integer finished;

	private Date delTime;
	// columns END

	private Set<GroupBuyActivityProduct> groupBuyActivityProducts = new HashSet<>(0);

	private Set<GroupBuyActivityRule> groupBuyActivityRules = new HashSet<>(0);

	private Set<GroupBuyActivityMember> groupBuyActivityMembers = new HashSet<>(0);

	private GroupBuyActivityMember groupBuyActivityMember;

	private Set<GroupBuyActivityTime> groupBuyActivityTimes = new HashSet<>(0);

	public GroupBuyActivity() {
	}

	public GroupBuyActivity(int id) {
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

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 128)
	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 3)
	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 3)
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public int getPromotionType() {
		return this.promotionType;
	}

	public void setPromotionType(int promotionType) {
		this.promotionType = promotionType;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getSponsor() {
		return this.sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	public String getCoverUrl() {
		return this.coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	@Column(unique = false, nullable = false)
	public Integer getHasPost() {
		return this.hasPost;
	}

	public void setHasPost(Integer hasPost) {
		this.hasPost = hasPost;
	}

	@Column(unique = false, nullable = false)
	public Integer getHasCoupon() {
		return this.hasCoupon;
	}

	public void setHasCoupon(Integer hasCoupon) {
		this.hasCoupon = hasCoupon;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public Integer getAudited() {
		return this.audited;
	}

	public void setAudited(Integer audited) {
		this.audited = audited;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public Integer getPublished() {
		return this.published;
	}

	public void setPublished(Integer published) {
		this.published = published;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 3)
	public int getStockState() {
		return this.stockState;
	}

	public void setStockState(int stockState) {
		this.stockState = stockState;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public Integer getFinished() {
		return this.finished;
	}

	public void setFinished(Integer finished) {
		this.finished = finished;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "groupBuyActivity")
	public Set<GroupBuyActivityProduct> getGroupBuyActivityProducts() {
		return groupBuyActivityProducts;
	}

	public void setGroupBuyActivityProducts(Set<GroupBuyActivityProduct> groupBuyActivityProduct) {
		this.groupBuyActivityProducts = groupBuyActivityProduct;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "groupBuyActivity")
	public Set<GroupBuyActivityRule> getGroupBuyActivityRules() {
		return groupBuyActivityRules;
	}

	public void setGroupBuyActivityRules(Set<GroupBuyActivityRule> groupBuyActivityRule) {
		this.groupBuyActivityRules = groupBuyActivityRule;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "groupBuyActivity")
	public Set<GroupBuyActivityTime> getGroupBuyActivityTimes() {
		return groupBuyActivityTimes;
	}

	public void setGroupBuyActivityTimes(Set<GroupBuyActivityTime> groupBuyActivityTime) {
		this.groupBuyActivityTimes = groupBuyActivityTime;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "AUDIT_USER_ID", nullable = false) })
	public User getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(User auditUser) {
		this.auditUser = auditUser;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "PUBLISH_USER_ID", nullable = false) })
	public User getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(User publishUser) {
		this.publishUser = publishUser;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "UPDATE_USER_ID", nullable = false) })
	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CREATE_USER_ID") })
	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "groupBuyActivity")
	public Set<GroupBuyActivityMember> getGroupBuyActivityMembers() {
		return groupBuyActivityMembers;
	}

	public void setGroupBuyActivityMembers(Set<GroupBuyActivityMember> groupBuyActivityMembers) {
		if (groupBuyActivityMember != null) {
			groupBuyActivityMembers.add(groupBuyActivityMember);
		} else {
			groupBuyActivityMembers.add(groupBuyActivityMember);
		}
		this.groupBuyActivityMembers = groupBuyActivityMembers;
	}

	@Transient
	public GroupBuyActivityMember getGroupBuyActivityMember() {
		return groupBuyActivityMember;
	}

	public void setGroupBuyActivityMember(GroupBuyActivityMember groupBuyActivityMember) {
		this.groupBuyActivityMember = groupBuyActivityMember;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}
}