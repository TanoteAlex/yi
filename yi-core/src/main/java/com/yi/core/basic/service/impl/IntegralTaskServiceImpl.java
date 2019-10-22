/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.Date;
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

import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.dao.IntegralTaskDao;
import com.yi.core.basic.domain.bo.IntegralTaskBo;
import com.yi.core.basic.domain.entity.IntegralTask;
import com.yi.core.basic.domain.entity.IntegralTask_;
import com.yi.core.basic.domain.simple.IntegralTaskSimple;
import com.yi.core.basic.domain.vo.IntegralTaskListVo;
import com.yi.core.basic.domain.vo.IntegralTaskVo;
import com.yi.core.basic.service.IIntegralTaskService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class IntegralTaskServiceImpl implements IIntegralTaskService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(IntegralTaskServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private IntegralTaskDao integralTaskDao;

	private EntityListVoBoSimpleConvert<IntegralTask, IntegralTaskBo, IntegralTaskVo, IntegralTaskSimple, IntegralTaskListVo> integralTaskConvert;

	@Override
	public IntegralTask getIntegralTaskById(int integralTaskId) {
		return integralTaskDao.getOne(integralTaskId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralTaskVo getIntegralTaskVoById(int integralTaskId) {

		return integralTaskConvert.toVo(this.integralTaskDao.getOne(integralTaskId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralTaskVo getIntegralTaskListVoById(int integralTaskId) {
		return integralTaskConvert.toVo(this.integralTaskDao.getOne(integralTaskId));
	}

	@Override
	public IntegralTask addIntegralTask(IntegralTask integralTask) {
		return integralTaskDao.save(integralTask);
	}

	@Override
	public IntegralTaskVo addIntegralTask(IntegralTaskBo integralTaskBo) {
		integralTaskBo.setCreateTime(new Date());
		return integralTaskConvert.toVo(integralTaskDao.save(integralTaskConvert.toEntity(integralTaskBo)));
	}

	@Override
	public IntegralTask updateIntegralTask(IntegralTask integralTask) {

		IntegralTask dbIntegralTask = integralTaskDao.getOne(integralTask.getId());
		AttributeReplication.copying(integralTask, dbIntegralTask, IntegralTask_.guid, IntegralTask_.taskName, IntegralTask_.growthValue, IntegralTask_.state,
				IntegralTask_.createTime, IntegralTask_.deleted, IntegralTask_.delTime);
		return dbIntegralTask;
	}

	@Override
	public IntegralTaskVo updateIntegralTask(IntegralTaskBo integralTaskBo) {
		IntegralTask dbIntegralTask = integralTaskDao.getOne(integralTaskBo.getId());
		AttributeReplication.copying(integralTaskBo, dbIntegralTask, IntegralTask_.guid, IntegralTask_.taskName, IntegralTask_.growthValue, IntegralTask_.state,
				IntegralTask_.createTime, IntegralTask_.deleted, IntegralTask_.delTime);
		return integralTaskConvert.toVo(dbIntegralTask);
	}

	@Override
	public void removeIntegralTaskById(int integralTaskId) {
		integralTaskDao.deleteById(integralTaskId);
	}

	@Override
	public Page<IntegralTask> query(Pagination<IntegralTask> query) {
		query.setEntityClazz(IntegralTask.class);
		Page<IntegralTask> page = integralTaskDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<IntegralTaskListVo> queryListVo(Pagination<IntegralTask> query) {

		Page<IntegralTask> pages = this.query(query);

		List<IntegralTaskListVo> vos = integralTaskConvert.toListVos(pages.getContent());
		return new PageImpl<IntegralTaskListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.integralTaskConvert = new EntityListVoBoSimpleConvert<IntegralTask, IntegralTaskBo, IntegralTaskVo, IntegralTaskSimple, IntegralTaskListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<IntegralTask, IntegralTaskVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralTask, IntegralTaskVo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralTaskVo IntegralTaskVo, IntegralTask IntegralTask) {

					}
				};
			}

			@Override
			protected BeanConvert<IntegralTask, IntegralTaskListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralTask, IntegralTaskListVo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralTaskListVo IntegralTaskListVo, IntegralTask IntegralTask) {

					}
				};
			}

			@Override
			protected BeanConvert<IntegralTask, IntegralTaskBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralTask, IntegralTaskBo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralTaskBo IntegralTaskBo, IntegralTask IntegralTask) {

					}
				};
			}

			@Override
			protected BeanConvert<IntegralTaskBo, IntegralTask> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralTaskBo, IntegralTask>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<IntegralTask, IntegralTaskSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralTask, IntegralTaskSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<IntegralTaskSimple, IntegralTask> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralTaskSimple, IntegralTask>(beanConvertManager) {
					@Override
					public IntegralTask convert(IntegralTaskSimple IntegralTaskSimple) throws BeansException {
						return integralTaskDao.getOne(IntegralTaskSimple.getId());
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
	public IntegralTaskVo updateGrowthValue(int dailyTaskId, int growthValue) {
		IntegralTask integralTask = integralTaskDao.getOne(dailyTaskId);
		integralTask.setGrowthValue(growthValue);
		return integralTaskConvert.toVo(integralTask);
	}

	@Override
	public IntegralTaskVo updateState(int dailyTaskId) {
		IntegralTask integralTask = integralTaskDao.getOne(dailyTaskId);
		Integer state = integralTask.getState();
		if (BasicEnum.STATE_ENABLE.getCode().equals(state)) {
			integralTask.setState(BasicEnum.STATE_DISABLE.getCode());
		}
		if (BasicEnum.STATE_DISABLE.getCode().equals(state)) {
			integralTask.setState(BasicEnum.STATE_ENABLE.getCode());
		}
		return integralTaskConvert.toVo(integralTask);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralTask getIntegralTaskByType(Integer taskType) {
		if (taskType != null) {
			return integralTaskDao.findByTaskTypeAndStateAndDeleted(taskType, BasicEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
		}
		return null;
	}

}
