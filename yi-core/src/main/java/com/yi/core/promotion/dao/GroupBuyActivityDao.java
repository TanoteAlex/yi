/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.promotion.domain.entity.GroupBuyActivity;

/**
 * 团购活动
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface GroupBuyActivityDao extends JpaRepository<GroupBuyActivity, Integer>, JpaSpecificationExecutor<GroupBuyActivity> {

	/**
	 * 根据审核，发布，终结等状态查询
	 * 
	 * @param audited
	 * @param published
	 * @param finished
	 * @param deleted
	 * @return
	 */
	List<GroupBuyActivity> findByAuditedAndPublishedAndFinishedAndDeleted(Integer audited, Integer published, Integer finished, Integer deleted);
}