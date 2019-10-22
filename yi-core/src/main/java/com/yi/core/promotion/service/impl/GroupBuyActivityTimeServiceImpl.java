/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.promotion.dao.GroupBuyActivityTimeDao;
import com.yi.core.promotion.domain.bo.GroupBuyActivityTimeBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.entity.GroupBuyActivityTime;
import com.yi.core.promotion.domain.entity.GroupBuyActivityTime_;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityTimeListVo;
import com.yi.core.promotion.domain.simple.GroupBuyActivityTimeSimple;
import com.yi.core.promotion.domain.vo.GroupBuyActivityTimeVo;
import com.yi.core.promotion.service.IGroupBuyActivityTimeService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.date.DateUtils;

/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class GroupBuyActivityTimeServiceImpl implements IGroupBuyActivityTimeService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityTimeServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private GroupBuyActivityTimeDao groupBuyActivityTimeDao;

	private EntityListVoBoSimpleConvert<GroupBuyActivityTime, GroupBuyActivityTimeBo, GroupBuyActivityTimeVo, GroupBuyActivityTimeSimple, GroupBuyActivityTimeListVo> groupBuyActivityTimeConvert;

	/**
	 * 分页查询GroupBuyActivityTime
	 **/
	@Override
	public Page<GroupBuyActivityTime> query(Pagination<GroupBuyActivityTime> query) {
		query.setEntityClazz(GroupBuyActivityTime.class);
		Page<GroupBuyActivityTime> page = groupBuyActivityTimeDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: GroupBuyActivityTime
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GroupBuyActivityTimeListVo> queryListVo(Pagination<GroupBuyActivityTime> query) {
		Page<GroupBuyActivityTime> pages = this.query(query);
		List<GroupBuyActivityTimeListVo> vos = groupBuyActivityTimeConvert.toListVos(pages.getContent());
		return new PageImpl<GroupBuyActivityTimeListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建GroupBuyActivityTime
	 **/
	@Override
	public GroupBuyActivityTimeListVo addGroupBuyActivityTime(GroupBuyActivityTimeBo groupBuyActivityTimeBo) {
		return groupBuyActivityTimeConvert.toListVo(groupBuyActivityTimeDao.save(groupBuyActivityTimeConvert.toEntity(groupBuyActivityTimeBo)));
	}

	/**
	 * 创建GroupBuyActivityTime
	 **/
	@Override
	public GroupBuyActivityTime addGroupBuyActivityTime(GroupBuyActivityTime groupBuyActivityTime) {
		return groupBuyActivityTimeDao.save(groupBuyActivityTime);
	}

	/**
	 * 更新GroupBuyActivityTime
	 **/
	@Override
	public GroupBuyActivityTime updateGroupBuyActivityTime(GroupBuyActivityTime groupBuyActivityTime) {
		GroupBuyActivityTime dbGroupBuyActivityTime = groupBuyActivityTimeDao.getOne(groupBuyActivityTime.getId());
		groupBuyActivityTime.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(groupBuyActivityTime.getEndTime()) + " 23:59:59"));
		AttributeReplication.copying(groupBuyActivityTime, dbGroupBuyActivityTime, GroupBuyActivityTime_.groupBuyActivity, GroupBuyActivityTime_.startTime,
				GroupBuyActivityTime_.endTime);
		return dbGroupBuyActivityTime;
	}

	/**
	 * 更新GroupBuyActivityTime
	 **/
	@Override
	public GroupBuyActivityTimeListVo updateGroupBuyActivityTime(GroupBuyActivityTimeBo groupBuyActivityTimeBo) {
		GroupBuyActivityTime dbGroupBuyActivityTime = groupBuyActivityTimeDao.getOne(groupBuyActivityTimeBo.getId());
		AttributeReplication.copying(groupBuyActivityTimeBo, dbGroupBuyActivityTime, GroupBuyActivityTime_.guid, GroupBuyActivityTime_.groupBuyActivity,
				GroupBuyActivityTime_.startTime, GroupBuyActivityTime_.endTime);
		return groupBuyActivityTimeConvert.toListVo(dbGroupBuyActivityTime);
	}

	/**
	 * 删除GroupBuyActivityTime
	 **/
	@Override
	public void removeGroupBuyActivityTimeById(int id) {
		groupBuyActivityTimeDao.deleteById(id);
	}

	/**
	 * 根据ID得到GroupBuyActivityTimeBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityTime getById(int id) {
		return this.groupBuyActivityTimeDao.getOne(id);
	}

	/**
	 * 根据ID得到GroupBuyActivityTimeBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityTimeBo getBoById(int id) {
		return groupBuyActivityTimeConvert.toBo(this.groupBuyActivityTimeDao.getOne(id));
	}

	/**
	 * 根据ID得到GroupBuyActivityTimeVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityTimeVo getVoById(int id) {
		return groupBuyActivityTimeConvert.toVo(this.groupBuyActivityTimeDao.getOne(id));
	}

	/**
	 * 根据ID得到GroupBuyActivityTimeListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityTimeListVo getListVoById(int id) {
		return groupBuyActivityTimeConvert.toListVo(this.groupBuyActivityTimeDao.getOne(id));
	}

	protected void initConvert() {
		this.groupBuyActivityTimeConvert = new EntityListVoBoSimpleConvert<GroupBuyActivityTime, GroupBuyActivityTimeBo, GroupBuyActivityTimeVo, GroupBuyActivityTimeSimple, GroupBuyActivityTimeListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<GroupBuyActivityTime, GroupBuyActivityTimeVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityTime, GroupBuyActivityTimeVo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityTimeVo groupBuyActivityTimeVo, GroupBuyActivityTime groupBuyActivityTime) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityTime, GroupBuyActivityTimeListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityTime, GroupBuyActivityTimeListVo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityTimeListVo groupBuyActivityTimeListVo, GroupBuyActivityTime groupBuyActivityTime) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityTime, GroupBuyActivityTimeBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityTime, GroupBuyActivityTimeBo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityTimeBo groupBuyActivityTimeBo, GroupBuyActivityTime groupBuyActivityTime) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityTimeBo, GroupBuyActivityTime> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityTimeBo, GroupBuyActivityTime>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityTime, GroupBuyActivityTimeSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityTime, GroupBuyActivityTimeSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityTimeSimple, GroupBuyActivityTime> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityTimeSimple, GroupBuyActivityTime>(beanConvertManager) {
					@Override
					public GroupBuyActivityTime convert(GroupBuyActivityTimeSimple groupBuyActivityTimeSimple) throws BeansException {
						return groupBuyActivityTimeDao.getOne(groupBuyActivityTimeSimple.getId());
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
	public void batchAddGroupBuyActivityTime(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityTime> groupBuyActivityTimes) {
		if (groupBuyActivity != null && CollectionUtils.isNotEmpty(groupBuyActivityTimes)) {
			groupBuyActivityTimes.forEach(tmp -> {
				tmp.setGroupBuyActivity(groupBuyActivity);
//				tmp.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(tmp.getEndTime()) + " 23:59:59"));
			});
			groupBuyActivityTimeDao.saveAll(groupBuyActivityTimes);
		}
	}

	@Override
	public void batchUpdateGroupBuyActivityTime(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityTime> groupBuyActivityTimes) {
		// TODO Auto-generated method stub
		if (groupBuyActivity != null && CollectionUtils.isNotEmpty(groupBuyActivityTimes)) {
			// 查询当前活动的会员集合
			List<GroupBuyActivityTime> dbGroupBuyActivityTimes = groupBuyActivityTimeDao.findByGroupBuyActivity_Id(groupBuyActivity.getId());
			// 需要新增的数据
			Set<GroupBuyActivityTime> saveSets = groupBuyActivityTimes.stream().filter(e1 -> dbGroupBuyActivityTimes.stream().noneMatch(e2 -> e1.getId() == e2.getId()))
					.collect(Collectors.toSet());
			// 需要更新的数据
			Set<GroupBuyActivityTime> updateSets = groupBuyActivityTimes.stream().filter(e1 -> dbGroupBuyActivityTimes.stream().anyMatch(e2 -> e1.getId() == e2.getId()))
					.collect(Collectors.toSet());
			// 需要删除的数据
			Set<GroupBuyActivityTime> deleteSets = dbGroupBuyActivityTimes.stream().filter(e1 -> groupBuyActivityTimes.stream().noneMatch(e2 -> e1.getId() == e2.getId()))
					.collect(Collectors.toSet());
			// 保存数据
			saveSets.forEach(tmp -> {
				tmp.setGroupBuyActivity(groupBuyActivity);
				tmp.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(tmp.getEndTime()) + " 23:59:59"));
			});
			groupBuyActivityTimeDao.saveAll(saveSets);
			// 删除数据
			deleteSets.forEach(e -> {
				if (e != null && e.getId() > 0) {
					groupBuyActivityTimeDao.delete(e);
				}
			});
			// 修改数据
			updateSets.forEach(e -> {
				if (e != null && e.getId() > 0) {
					e.setGroupBuyActivity(groupBuyActivity);
					this.updateGroupBuyActivityTime(e);
				}
			});
		}

	}
}
