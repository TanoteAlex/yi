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

import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.dao.IntegralRecordDao;
import com.yi.core.basic.domain.bo.IntegralRecordBo;
import com.yi.core.basic.domain.entity.IntegralRecord;
import com.yi.core.basic.domain.entity.IntegralRecord_;
import com.yi.core.basic.domain.entity.IntegralTask;
import com.yi.core.basic.domain.simple.IntegralRecordSimple;
import com.yi.core.basic.domain.vo.IntegralRecordListVo;
import com.yi.core.basic.domain.vo.IntegralRecordVo;
import com.yi.core.basic.service.IIntegralRecordService;
import com.yi.core.basic.service.IIntegralTaskService;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IMemberService;
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
public class IntegralRecordServiceImpl implements IIntegralRecordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(IntegralRecordServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private IntegralRecordDao integralRecordDao;

	@Resource
	private IMemberService memberService;

	@Resource
	private IIntegralTaskService integralTaskService;

	private EntityListVoBoSimpleConvert<IntegralRecord, IntegralRecordBo, IntegralRecordVo, IntegralRecordSimple, IntegralRecordListVo> integralRecordConvert;

	/**
	 * 分页查询IntegralRecord
	 **/
	@Override
	public Page<IntegralRecord> query(Pagination<IntegralRecord> query) {
		query.setEntityClazz(IntegralRecord.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(IntegralRecord_.createTime)));
		}));
		Page<IntegralRecord> page = integralRecordDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 创建IntegralRecord
	 **/
	@Override
	public IntegralRecordVo addIntegralRecord(IntegralRecordBo integralRecordBo) {
		return integralRecordConvert.toVo(integralRecordDao.save(integralRecordConvert.toEntity(integralRecordBo)));
	}

	/**
	 * 创建IntegralRecord
	 **/
	@Override
	public IntegralRecordVo addIntegralRecord(IntegralRecord integralRecord) {
		return integralRecordConvert.toVo(integralRecordDao.save(integralRecord));
	}

	/**
	 * 更新IntegralRecord
	 **/
	@Override
	public IntegralRecordVo updateIntegralRecord(IntegralRecordBo integralRecordBo) {
		IntegralRecord dbIntegralRecord = integralRecordDao.getOne(integralRecordBo.getId());
		AttributeReplication.copying(integralRecordBo, dbIntegralRecord, IntegralRecord_.guid, IntegralRecord_.member, IntegralRecord_.operateType, IntegralRecord_.operateIntegral,
				IntegralRecord_.currentIntegral, IntegralRecord_.createTime);
		return integralRecordConvert.toVo(dbIntegralRecord);
	}

	/**
	 * 删除IntegralRecord
	 **/
	@Override
	public void removeIntegralRecordById(int id) {
		integralRecordDao.deleteById(id);
	}

	/**
	 * 根据ID得到IntegralRecord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralRecordVo getIntegralRecordVoById(int id) {

		return integralRecordConvert.toVo(this.integralRecordDao.getOne(id));
	}

	/**
	 * 根据ID得到IntegralRecordListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralRecordVo getListVoById(int id) {
		return integralRecordConvert.toVo(this.integralRecordDao.getOne(id));
	}

	/**
	 * 分页查询: IntegralRecord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<IntegralRecordListVo> queryListVo(Pagination<IntegralRecord> query) {

		Page<IntegralRecord> pages = this.query(query);

		List<IntegralRecordListVo> vos = integralRecordConvert.toListVos(pages.getContent());
		return new PageImpl<IntegralRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.integralRecordConvert = new EntityListVoBoSimpleConvert<IntegralRecord, IntegralRecordBo, IntegralRecordVo, IntegralRecordSimple, IntegralRecordListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<IntegralRecord, IntegralRecordVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecord, IntegralRecordVo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralRecordVo IntegralRecordVo, IntegralRecord IntegralRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<IntegralRecord, IntegralRecordListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecord, IntegralRecordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralRecordListVo IntegralRecordListVo, IntegralRecord IntegralRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<IntegralRecord, IntegralRecordBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecord, IntegralRecordBo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralRecordBo IntegralRecordBo, IntegralRecord IntegralRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<IntegralRecordBo, IntegralRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecordBo, IntegralRecord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<IntegralRecord, IntegralRecordSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecord, IntegralRecordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<IntegralRecordSimple, IntegralRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralRecordSimple, IntegralRecord>(beanConvertManager) {
					@Override
					public IntegralRecord convert(IntegralRecordSimple IntegralRecordSimple) throws BeansException {
						return integralRecordDao.getOne(IntegralRecordSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	/**
	 * 根据任务类型增加记录
	 * 
	 * @param member
	 * @param taskType
	 */
	@Override
	public void addByTaskType(Member member, IntegralTask integralTask) {
		if (member != null && integralTask != null) {
			// 封装积分记录数据
			IntegralRecord integralRecord = new IntegralRecord();
			integralRecord.setMember(member);
			integralRecord.setTaskType(integralTask.getTaskType());
			integralRecord.setTaskName(integralTask.getTaskName());
			integralRecord.setOperateType(BasicEnum.OPERATE_TYPE_ADD.getCode());
			integralRecord.setOperateIntegral(integralTask.getGrowthValue());
			integralRecord.setCurrentIntegral(member.getAccount().getAvailableIntegral());
			integralRecordDao.save(integralRecord);
		}
	}

	/**
	 * 
	 * 下单增加记录
	 * 
	 * @param member
	 * @param integral
	 */
	@Override
	public void addByOrder(Member member, int integral, BasicEnum operateType) {
		if (member != null && integral > 0) {
			// 封装积分记录数据
			IntegralRecord integralRecord = new IntegralRecord();
			integralRecord.setMember(member);
			integralRecord.setTaskType(BasicEnum.TASK_TYPE_ORDER.getCode());
			integralRecord.setTaskName(BasicEnum.TASK_TYPE_ORDER.getValue());
			integralRecord.setOperateType(operateType.getCode());
			integralRecord.setOperateIntegral(integral);
			integralRecord.setCurrentIntegral(member.getAccount().getAvailableIntegral());
			integralRecordDao.save(integralRecord);
		}
	}

	/**
	 * 奖励增加记录
	 */
	@Override
	public void addByRewrd(Member member, int integral) {
		if (member != null && integral > 0) {
			// 封装积分记录数据
			IntegralRecord integralRecord = new IntegralRecord();
			integralRecord.setMember(member);
			integralRecord.setTaskType(BasicEnum.TASK_TYPE_REWARD.getCode());
			integralRecord.setTaskName(BasicEnum.TASK_TYPE_REWARD.getValue());
			integralRecord.setOperateType(BasicEnum.OPERATE_TYPE_ADD.getCode());
			integralRecord.setOperateIntegral(integral);
			integralRecord.setCurrentIntegral(member.getAccount().getAvailableIntegral());
			integralRecordDao.save(integralRecord);
		}
	}

	/**
	 * 领取邀请奖品
	 */
	@Override
	public void addByReceivePrize(Member member, int integral, Integer prizeId) {
		if (member != null && integral > 0) {
			// 封装积分记录数据
			IntegralRecord integralRecord = new IntegralRecord();
			integralRecord.setMember(member);
			integralRecord.setTaskType(BasicEnum.TASK_TYPE_REWARD.getCode());
			integralRecord.setTaskName(BasicEnum.TASK_TYPE_REWARD.getValue());
			integralRecord.setOperateType(BasicEnum.OPERATE_TYPE_ADD.getCode());
			integralRecord.setOperateIntegral(integral);
			integralRecord.setCurrentIntegral(member.getAccount().getTotalIntegral());
			integralRecord.setPrizeId(prizeId);
			integralRecordDao.save(integralRecord);
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkReceivePrize(Integer memberId, int integral, Integer prizeId) {
		int count = integralRecordDao.countByMember_idAndPrizeIdAndOperateIntegral(memberId, prizeId, integral);
		if (count > 0) {
			return true;
		}
		return false;
	}

}
