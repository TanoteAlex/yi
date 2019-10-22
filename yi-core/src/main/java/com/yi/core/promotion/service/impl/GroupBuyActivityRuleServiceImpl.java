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

import com.yi.core.promotion.dao.GroupBuyActivityRuleDao;
import com.yi.core.promotion.domain.bo.GroupBuyActivityRuleBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.entity.GroupBuyActivityRule;
import com.yi.core.promotion.domain.entity.GroupBuyActivityRule_;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityRuleListVo;
import com.yi.core.promotion.domain.simple.GroupBuyActivityRuleSimple;
import com.yi.core.promotion.domain.vo.GroupBuyActivityRuleVo;
import com.yi.core.promotion.service.IGroupBuyActivityRuleService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class GroupBuyActivityRuleServiceImpl implements IGroupBuyActivityRuleService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityRuleServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private GroupBuyActivityRuleDao groupBuyActivityRuleDao;

	private EntityListVoBoSimpleConvert<GroupBuyActivityRule, GroupBuyActivityRuleBo, GroupBuyActivityRuleVo, GroupBuyActivityRuleSimple, GroupBuyActivityRuleListVo> groupBuyActivityRuleConvert;

	/**
	 * 分页查询GroupBuyActivityRule
	 **/
	@Override
	public Page<GroupBuyActivityRule> query(Pagination<GroupBuyActivityRule> query) {
		query.setEntityClazz(GroupBuyActivityRule.class);
		Page<GroupBuyActivityRule> page = groupBuyActivityRuleDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: GroupBuyActivityRule
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GroupBuyActivityRuleListVo> queryListVo(Pagination<GroupBuyActivityRule> query) {
		Page<GroupBuyActivityRule> pages = this.query(query);
		List<GroupBuyActivityRuleListVo> vos = groupBuyActivityRuleConvert.toListVos(pages.getContent());
		return new PageImpl<GroupBuyActivityRuleListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建GroupBuyActivityRule
	 **/
	@Override
	public GroupBuyActivityRuleListVo addGroupBuyActivityRule(GroupBuyActivityRuleBo groupBuyActivityRuleBo) {
		return groupBuyActivityRuleConvert.toListVo(groupBuyActivityRuleDao.save(groupBuyActivityRuleConvert.toEntity(groupBuyActivityRuleBo)));
	}

	/**
	 * 创建GroupBuyActivityRule
	 **/
	@Override
	public GroupBuyActivityRule addGroupBuyActivityRule(GroupBuyActivityRule groupBuyActivityRule) {
		return groupBuyActivityRuleDao.save(groupBuyActivityRule);
	}

	/**
	 * 更新GroupBuyActivityRule
	 **/
	@Override
	public GroupBuyActivityRule updateGroupBuyActivityRule(GroupBuyActivityRule groupBuyActivityRule) {
		GroupBuyActivityRule dbGroupBuyActivityRule = groupBuyActivityRuleDao.getOne(groupBuyActivityRule.getId());
		AttributeReplication.copying(groupBuyActivityRule, dbGroupBuyActivityRule, GroupBuyActivityRule_.groupBuyActivity,
				GroupBuyActivityRule_.commodity, GroupBuyActivityRule_.commodityName, GroupBuyActivityRule_.groupBuyQuantity);
		return dbGroupBuyActivityRule;
	}

	/**
	 * 更新GroupBuyActivityRule
	 **/
	@Override
	public GroupBuyActivityRuleListVo updateGroupBuyActivityRule(GroupBuyActivityRuleBo groupBuyActivityRuleBo) {
		GroupBuyActivityRule dbGroupBuyActivityRule = groupBuyActivityRuleDao.getOne(groupBuyActivityRuleBo.getId());
		AttributeReplication.copying(groupBuyActivityRuleBo, dbGroupBuyActivityRule, GroupBuyActivityRule_.guid, GroupBuyActivityRule_.groupBuyActivity,
				GroupBuyActivityRule_.commodity, GroupBuyActivityRule_.commodityName, GroupBuyActivityRule_.groupBuyQuantity, GroupBuyActivityRule_.injectWater);
		return groupBuyActivityRuleConvert.toListVo(dbGroupBuyActivityRule);
	}

	/**
	 * 删除GroupBuyActivityRule
	 **/
	@Override
	public void removeGroupBuyActivityRuleById(int id) {
		groupBuyActivityRuleDao.deleteById(id);
	}

	/**
	 * 根据ID得到GroupBuyActivityRuleBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityRule getById(int id) {
		return this.groupBuyActivityRuleDao.getOne(id);
	}

	/**
	 * 根据ID得到GroupBuyActivityRuleBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityRuleBo getBoById(int id) {
		return groupBuyActivityRuleConvert.toBo(this.groupBuyActivityRuleDao.getOne(id));
	}

	/**
	 * 根据ID得到GroupBuyActivityRuleVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityRuleVo getVoById(int id) {
		return groupBuyActivityRuleConvert.toVo(this.groupBuyActivityRuleDao.getOne(id));
	}

	/**
	 * 根据ID得到GroupBuyActivityRuleListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityRuleListVo getListVoById(int id) {
		return groupBuyActivityRuleConvert.toListVo(this.groupBuyActivityRuleDao.getOne(id));
	}

	protected void initConvert() {
		this.groupBuyActivityRuleConvert = new EntityListVoBoSimpleConvert<GroupBuyActivityRule, GroupBuyActivityRuleBo, GroupBuyActivityRuleVo, GroupBuyActivityRuleSimple, GroupBuyActivityRuleListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<GroupBuyActivityRule, GroupBuyActivityRuleVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityRule, GroupBuyActivityRuleVo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityRuleVo groupBuyActivityRuleVo, GroupBuyActivityRule groupBuyActivityRule) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityRule, GroupBuyActivityRuleListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityRule, GroupBuyActivityRuleListVo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityRuleListVo groupBuyActivityRuleListVo, GroupBuyActivityRule groupBuyActivityRule) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityRule, GroupBuyActivityRuleBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityRule, GroupBuyActivityRuleBo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityRuleBo groupBuyActivityRuleBo, GroupBuyActivityRule groupBuyActivityRule) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityRuleBo, GroupBuyActivityRule> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityRuleBo, GroupBuyActivityRule>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityRule, GroupBuyActivityRuleSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityRule, GroupBuyActivityRuleSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityRuleSimple, GroupBuyActivityRule> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityRuleSimple, GroupBuyActivityRule>(beanConvertManager) {
					@Override
					public GroupBuyActivityRule convert(GroupBuyActivityRuleSimple groupBuyActivityRuleSimple) throws BeansException {
						return groupBuyActivityRuleDao.getOne(groupBuyActivityRuleSimple.getId());
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
	public void batchAddGroupBuyActivityRule(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityRule> groupBuyActivityRules) {
		if (groupBuyActivity != null && CollectionUtils.isNotEmpty(groupBuyActivityRules)) {
			groupBuyActivityRules.forEach(tmp -> {
				tmp.setGroupBuyActivity(groupBuyActivity);
			});
			groupBuyActivityRuleDao.saveAll(groupBuyActivityRules);
		}
	}

	@Override
	public void batchUpdateGroupBuyActivityRule(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityRule> groupBuyActivityRules) {
		// TODO Auto-generated method stub
		if (groupBuyActivity != null && CollectionUtils.isNotEmpty(groupBuyActivityRules)) {
			// 查询当前活动的会员集合
			List<GroupBuyActivityRule> dbGroupBuyActivityRules = groupBuyActivityRuleDao.findByGroupBuyActivity_Id(groupBuyActivity.getId());
			// 需要新增的数据
			Set<GroupBuyActivityRule> saveSets = groupBuyActivityRules.stream().filter(e1 -> dbGroupBuyActivityRules.stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 需要更新的数据
			Set<GroupBuyActivityRule> updateSets = groupBuyActivityRules.stream().filter(e1 -> dbGroupBuyActivityRules.stream().anyMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 需要删除的数据
			Set<GroupBuyActivityRule> deleteSets = dbGroupBuyActivityRules.stream().filter(e1 -> groupBuyActivityRules.stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 保存数据
			saveSets.forEach(e -> {
				e.setGroupBuyActivity(groupBuyActivity);
			});
			groupBuyActivityRuleDao.saveAll(saveSets);
			// 删除数据
			deleteSets.forEach(e -> {
				if (e != null && e.getId() > 0) {
					groupBuyActivityRuleDao.delete(e);
				}
			});
			// 修改数据
			updateSets.forEach(e -> {
				if (e != null && e.getId() > 0) {
					e.setGroupBuyActivity(groupBuyActivity);
					this.updateGroupBuyActivityRule(e);
				}
			});
		}
	}
}
