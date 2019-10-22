/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.member.domain.entity.DistributionLevel;

/**
 * 分销等级
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface DistributionLevelDao extends JpaRepository<DistributionLevel, Integer>, JpaSpecificationExecutor<DistributionLevel> {

	/**
	 * 根据级别 获取分销等级
	 * 
	 * @param rank
	 * @param deleted
	 * @return
	 */
	DistributionLevel findByRankAndDeleted(Integer rank, Integer deleted);

}