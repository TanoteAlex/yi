/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.domain.bo;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.yi.core.auth.domain.bo.UserBo;
import com.yi.core.member.domain.bo.MemberLevelBo;
import com.yihz.common.convert.domain.BoDomain;

/**
 * 团购活动
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class GroupBuyActivityBo extends BoDomain {

	/**
	 *
	 */
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
	 * 团购活动封面
	 */
	@NotBlank
	@Length(max = 255)
	private String coverUrl;
	/**
	 * 优先级
	 */
	@NotNull
	@Max(127)
	private int priority;
	/**
	 * 发布人
	 */
	private UserBo publishUser;
	/**
	 * 会员类型
	 */
	private int memberType;
	/**
	 * 会员等级
	 */
	private MemberLevelBo[] memberLevels;

	/**
	 * 团购活动类型
	 */
	@NotNull
	@Max(127)
	private int type;
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
	// columns END

	private Set<GroupBuyActivityProductBo> groupBuyActivityProducts = new HashSet<>(0);

	private Set<GroupBuyActivityRuleBo> groupBuyActivityRules = new HashSet<>(0);

	private GroupBuyActivityMemberBo groupBuyActivityMember;

	private Set<GroupBuyActivityTimeBo> groupBuyActivityTimes = new HashSet<>(0);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public UserBo getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(UserBo publishUser) {
		this.publishUser = publishUser;
	}

	public int getMemberType() {
		return memberType;
	}

	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}

	public MemberLevelBo[] getMemberLevels() {
		return memberLevels;
	}

	public void setMemberLevels(MemberLevelBo[] memberLevels) {
		this.memberLevels = memberLevels;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getHasPost() {
		return hasPost;
	}

	public void setHasPost(Integer hasPost) {
		this.hasPost = hasPost;
	}

	public Integer getHasCoupon() {
		return hasCoupon;
	}

	public void setHasCoupon(Integer hasCoupon) {
		this.hasCoupon = hasCoupon;
	}

	public Set<GroupBuyActivityProductBo> getGroupBuyActivityProducts() {
		return groupBuyActivityProducts;
	}

	public void setGroupBuyActivityProducts(Set<GroupBuyActivityProductBo> groupBuyActivityProducts) {
		this.groupBuyActivityProducts = groupBuyActivityProducts;
	}

	public Set<GroupBuyActivityRuleBo> getGroupBuyActivityRules() {
		return groupBuyActivityRules;
	}

	public void setGroupBuyActivityRules(Set<GroupBuyActivityRuleBo> groupBuyActivityRules) {
		this.groupBuyActivityRules = groupBuyActivityRules;
	}

	public GroupBuyActivityMemberBo getGroupBuyActivityMember() {
		return groupBuyActivityMember;
	}

	public void setGroupBuyActivityMember(GroupBuyActivityMemberBo groupBuyActivityMember) {
		this.groupBuyActivityMember = groupBuyActivityMember;
	}

	public Set<GroupBuyActivityTimeBo> getGroupBuyActivityTimes() {
		return groupBuyActivityTimes;
	}

	public void setGroupBuyActivityTimes(Set<GroupBuyActivityTimeBo> groupBuyActivityTimes) {
		this.groupBuyActivityTimes = groupBuyActivityTimes;
	}
}