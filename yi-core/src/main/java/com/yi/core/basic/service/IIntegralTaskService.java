/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.IntegralTaskBo;
import com.yi.core.basic.domain.entity.IntegralTask;
import com.yi.core.basic.domain.vo.IntegralTaskListVo;
import com.yi.core.basic.domain.vo.IntegralTaskVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IIntegralTaskService {

	/**
	 * 根据ID得到IntegralTask
	 * 
	 * @param integralTaskId
	 * @return
	 */
	IntegralTask getIntegralTaskById(int integralTaskId);

	/**
	 * 根据ID得到IntegralTaskVo
	 * 
	 * @param integralTaskId
	 * @return
	 */
	IntegralTaskVo getIntegralTaskVoById(int integralTaskId);

	/**
	 * 根据ID得到IntegralTaskListVo
	 * 
	 * @param integralTaskId
	 * @return
	 */
	IntegralTaskVo getIntegralTaskListVoById(int integralTaskId);

	/**
	 * 根据Entity创建IntegralTask
	 * 
	 * @param integralTask
	 * @return
	 */
	IntegralTask addIntegralTask(IntegralTask integralTask);

	/**
	 * 根据BO创建IntegralTask
	 * 
	 * @param integralTaskBo
	 * @return
	 */
	IntegralTaskVo addIntegralTask(IntegralTaskBo integralTaskBo);

	/**
	 * 根据Entity更新IntegralTask
	 * 
	 * @param integralTask
	 * @return
	 */
	IntegralTask updateIntegralTask(IntegralTask integralTask);

	/**
	 * 根据BO更新IntegralTask
	 * 
	 * @param integralTaskBo
	 * @return
	 */
	IntegralTaskVo updateIntegralTask(IntegralTaskBo integralTaskBo);

	/**
	 * 删除IntegralTask
	 * 
	 * @param integralTaskId
	 */
	void removeIntegralTaskById(int integralTaskId);

	/**
	 * 分页查询: IntegralTask
	 * 
	 * @param query
	 * @return
	 */
	Page<IntegralTask> query(Pagination<IntegralTask> query);

	/**
	 * 分页查询: IntegralTaskListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<IntegralTaskListVo> queryListVo(Pagination<IntegralTask> query);

	/**
	 * 修改成长值: updateGrowthValue
	 *
	 * @param dailyTaskId
	 * @param growthValue
	 * @return DailyTask
	 */
	IntegralTaskVo updateGrowthValue(int dailyTaskId, int growthValue);

	IntegralTaskVo updateState(int integralTaskId);

	/**
	 * 根据任务类型 积分任务
	 * 
	 * @param taskType
	 * @return
	 */
	IntegralTask getIntegralTaskByType(Integer taskType);

}
