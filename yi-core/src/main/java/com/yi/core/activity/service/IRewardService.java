/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.domain.bo.RewardBo;
import com.yi.core.activity.domain.entity.Reward;
import com.yi.core.activity.domain.vo.RewardListVo;
import com.yi.core.activity.domain.vo.RewardVo;
import com.yi.core.member.domain.entity.Member;
import com.yihz.common.persistence.Pagination;

/**
 * 奖励
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IRewardService {

	/**
	 * 分页查询: Reward
	 **/
	Page<Reward> query(Pagination<Reward> query);

	/**
	 * 分页查询: Reward
	 **/
	Page<RewardListVo> queryListVo(Pagination<Reward> query);

	/**
	 * 分页查询: Reward
	 **/
	Page<RewardListVo> queryListVoForApp(Pagination<Reward> query);

	/**
	 * 创建Reward
	 **/
	Reward addReward(Reward reward);

	/**
	 * 创建Reward
	 **/
	RewardListVo addReward(RewardBo reward);

	/**
	 * 更新Reward
	 **/
	Reward updateReward(Reward reward);

	/**
	 * 更新Reward
	 **/
	RewardListVo updateReward(RewardBo reward);

	/**
	 * 删除Reward
	 **/
	void removeRewardById(int rewardId);

	/**
	 * 根据ID得到Reward
	 **/
	Reward getRewardById(int rewardId);

	/**
	 * 根据ID得到RewardBo
	 **/
	RewardBo getRewardBoById(int rewardId);

	/**
	 * 根据ID得到RewardVo
	 **/
	RewardVo getRewardVoById(int rewardId);

	/**
	 * 根据ID得到RewardListVo
	 **/
	RewardListVo getListVoById(int rewardId);

	/**
	 * 改变禁用启用状态
	 * 
	 * @param rewardId
	 * @return
	 */
	RewardListVo updateState(int rewardId);

	/**
	 * 根据奖励类型查询奖励集合
	 * 
	 * @param rewardType
	 * @return
	 */
	List<RewardVo> getByRewardType(ActivityEnum rewardType);

	/**
	 * 根据签到天数 查询签到奖励
	 * 
	 * @param rewardType
	 * @param signDays
	 * @return
	 */
	List<RewardVo> getBySignDays(Integer signDays);

	/**
	 * 根据奖励类型 发放奖励
	 * 
	 * @param rewardType
	 * @param member
	 * @param signDays
	 */
	void grantRewardByRewardType(ActivityEnum rewardType, Member member, Integer signDays);

//	/**
//	 * 根据邀请人数 查询邀请奖励
//	 * 
//	 * @param inviteNum
//	 * @return
//	 */
//	List<Reward> getRewardByInviteNum(int inviteNum);

	/**
	 * 根据奖励类型查询奖励
	 * 
	 * @param rewardType
	 * @return
	 */
	List<RewardVo> getRewardsByRewardType(Integer rewardType);

}
