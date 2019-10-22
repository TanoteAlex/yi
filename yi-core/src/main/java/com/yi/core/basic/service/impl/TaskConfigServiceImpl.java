/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.basic.dao.TaskConfigDao;
import com.yi.core.basic.domain.bo.TaskConfigBo;
import com.yi.core.basic.domain.entity.TaskConfig;
import com.yi.core.basic.domain.entity.TaskConfig_;
import com.yi.core.basic.domain.simple.TaskConfigSimple;
import com.yi.core.basic.domain.vo.TaskConfigListVo;
import com.yi.core.basic.domain.vo.TaskConfigVo;
import com.yi.core.basic.service.ITaskConfigService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 任务配置
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class TaskConfigServiceImpl implements ITaskConfigService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(TaskConfigServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private TaskConfigDao taskConfigDao;

	private EntityListVoBoSimpleConvert<TaskConfig, TaskConfigBo, TaskConfigVo, TaskConfigSimple, TaskConfigListVo> taskConfigConvert;

	/**
	 * 分页查询TaskConfig
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<TaskConfig> query(Pagination<TaskConfig> query) {
		query.setEntityClazz(TaskConfig.class);
		Page<TaskConfig> page = taskConfigDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: TaskConfig
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<TaskConfigListVo> queryListVo(Pagination<TaskConfig> query) {

		Page<TaskConfig> pages = this.query(query);

		List<TaskConfigListVo> vos = taskConfigConvert.toListVos(pages.getContent());
		return new PageImpl<TaskConfigListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	/**
	 * 创建TaskConfig
	 **/
	@Override
	public TaskConfig addTaskConfig(TaskConfig taskConfig) {
		return taskConfigDao.save(taskConfig);
	}

	/**
	 * 创建TaskConfig
	 **/
	@Override
	public TaskConfigVo addTaskConfig(TaskConfigBo taskConfigBo) {
		return taskConfigConvert.toVo(this.addTaskConfig(taskConfigConvert.toEntity(taskConfigBo)));
	}

	/**
	 * 更新TaskConfig
	 **/
	@Override
	public TaskConfig updateTaskConfig(TaskConfig taskConfig) {
		TaskConfig dbTaskConfig = taskConfigDao.getOne(taskConfig.getId());
		AttributeReplication.copying(taskConfig, dbTaskConfig, TaskConfig_.name, TaskConfig_.clazz, TaskConfig_.method, TaskConfig_.cron, TaskConfig_.site, TaskConfig_.active,
				TaskConfig_.lastExecuteTime, TaskConfig_.description);
		return dbTaskConfig;
	}

	/**
	 * 更新TaskConfig
	 **/
	@Override
	public TaskConfigVo updateTaskConfig(TaskConfigBo taskConfigBo) {
		return taskConfigConvert.toVo(this.updateTaskConfig(taskConfigConvert.toEntity(taskConfigBo)));
	}

	/**
	 * 删除TaskConfig
	 **/
	@Override
	public void removeTaskConfigById(int id) {
		taskConfigDao.deleteById(id);
	}

	/**
	 * 根据ID得到TaskConfigVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TaskConfigVo getTaskConfigVoById(int id) {
		return taskConfigConvert.toVo(this.taskConfigDao.getOne(id));
	}

	/**
	 * 根据ID得到TaskConfigListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TaskConfigListVo getListVoById(int id) {
		return taskConfigConvert.toListVo(this.taskConfigDao.getOne(id));
	}

	protected void initConvert() {
		this.taskConfigConvert = new EntityListVoBoSimpleConvert<TaskConfig, TaskConfigBo, TaskConfigVo, TaskConfigSimple, TaskConfigListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<TaskConfig, TaskConfigVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<TaskConfig, TaskConfigVo>(beanConvertManager) {
					@Override
					protected void postConvert(TaskConfigVo TaskConfigVo, TaskConfig TaskConfig) {

					}
				};
			}

			@Override
			protected BeanConvert<TaskConfig, TaskConfigListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<TaskConfig, TaskConfigListVo>(beanConvertManager) {
					@Override
					protected void postConvert(TaskConfigListVo TaskConfigListVo, TaskConfig TaskConfig) {

					}
				};
			}

			@Override
			protected BeanConvert<TaskConfig, TaskConfigBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<TaskConfig, TaskConfigBo>(beanConvertManager) {
					@Override
					protected void postConvert(TaskConfigBo TaskConfigBo, TaskConfig TaskConfig) {

					}
				};
			}

			@Override
			protected BeanConvert<TaskConfigBo, TaskConfig> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<TaskConfigBo, TaskConfig>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<TaskConfig, TaskConfigSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<TaskConfig, TaskConfigSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<TaskConfigSimple, TaskConfig> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<TaskConfigSimple, TaskConfig>(beanConvertManager) {
					@Override
					public TaskConfig convert(TaskConfigSimple TaskConfigSimple) throws BeansException {
						return taskConfigDao.getOne(TaskConfigSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TaskConfig> queryActiveTaskConfig() {
		return taskConfigDao.findByActive(Boolean.TRUE);
	}
}
