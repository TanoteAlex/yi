/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.TaskConfigBo;
import com.yi.core.basic.domain.entity.TaskConfig;
import com.yi.core.basic.domain.vo.TaskConfigListVo;
import com.yi.core.basic.domain.vo.TaskConfigVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface ITaskConfigService {

	/**
	 * 分页查询: TaskConfig
	 **/
	Page<TaskConfig> query(Pagination<TaskConfig> query);

	/**
	 * 分页查询: TaskConfig
	 **/
	Page<TaskConfigListVo> queryListVo(Pagination<TaskConfig> query);

	/**
	 * 创建TaskConfig
	 **/
	TaskConfig addTaskConfig(TaskConfig taskConfig);

	/**
	 * 创建TaskConfig
	 **/
	TaskConfigVo addTaskConfig(TaskConfigBo taskConfig);

	/**
	 * 更新TaskConfig
	 **/
	TaskConfig updateTaskConfig(TaskConfig taskConfig);

	/**
	 * 更新TaskConfig
	 **/
	TaskConfigVo updateTaskConfig(TaskConfigBo taskConfig);

	/**
	 * 删除TaskConfig
	 **/
	void removeTaskConfigById(int taskConfigId);

	/**
	 * 根据ID得到TaskConfigVo
	 **/
	TaskConfigVo getTaskConfigVoById(int taskConfigId);

	/**
	 * 根据ID得到TaskConfigListVo
	 **/
	TaskConfigListVo getListVoById(int taskConfigId);

	/**
	 * 查询激活的定时任务
	 * 
	 * @return
	 */
	List<TaskConfig> queryActiveTaskConfig();

}
