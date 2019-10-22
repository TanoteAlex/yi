/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.IntegralTask;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IntegralTaskDao extends JpaRepository<IntegralTask, Integer>, JpaSpecificationExecutor<IntegralTask> {

	/**
	 * 根据类型获取积分任务
	 * 
	 * @param taskType
	 * @param state
	 * @param deleted
	 * @return
	 */
	IntegralTask findByTaskTypeAndStateAndDeleted(Integer taskType, Integer state, Integer deleted);

}