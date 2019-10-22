/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.activity.domain.entity.Reward;

/**
 * 奖励
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface RewardDao extends JpaRepository<Reward, Integer>, JpaSpecificationExecutor<Reward> {

	/**
	 * 根据奖励类型 查询奖励集合
	 * 
	 * @param rewardType
	 * @param state
	 * @param deleted
	 * @return
	 */
	List<Reward> findByRewardTypeAndStateAndDeleted(Integer rewardType, Integer state, Integer deleted);

	/**
	 * 根据签到天数 查询签到奖励
	 * 
	 * @param rewardType
	 * @param state
	 * @param signDays
	 * @param deleted
	 * @return
	 */
	List<Reward> findByRewardTypeAndStateAndSignDaysAndDeleted(Integer rewardType, Integer state, Integer signDays, Integer deleted);

//	/**
//	 * 根据邀请人数 查询签到奖励
//	 * 
//	 * @param rewardType
//	 * @param inviteNum
//	 * @param state
//	 * @param deleted
//	 * @return
//	 */
//	List<Reward> findByRewardTypeAndInviteNumGreaterThanEqualAndStateAndDeletedOrderByInviteNumAsc(Integer rewardType, Integer inviteNum, Integer state, Integer deleted);

}