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
import org.springframework.util.CollectionUtils;

import com.yi.core.basic.dao.BasicRuleDao;
import com.yi.core.basic.domain.bo.BasicRuleBo;
import com.yi.core.basic.domain.entity.BasicRule;
import com.yi.core.basic.domain.simple.BasicRuleSimple;
import com.yi.core.basic.domain.vo.BasicRuleListVo;
import com.yi.core.basic.domain.vo.BasicRuleVo;
import com.yi.core.basic.service.IBasicRuleService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class BasicRuleServiceImpl implements IBasicRuleService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(BasicRuleServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private BasicRuleDao basicRuleDao;

	private EntityListVoBoSimpleConvert<BasicRule, BasicRuleBo, BasicRuleVo, BasicRuleSimple, BasicRuleListVo> basicRuleConvert;

	/**
	 * 分页查询BasicRule
	 **/
	@Override
	public Page<BasicRule> query(Pagination<BasicRule> query) {
		query.setEntityClazz(BasicRule.class);
		Page<BasicRule> page = basicRuleDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 创建BasicRule
	 **/
	@Override
	public BasicRuleVo addBasicRule(BasicRuleBo basicRuleBo) {
		return basicRuleConvert.toVo(basicRuleDao.save(basicRuleConvert.toEntity(basicRuleBo)));
	}

	/**
	 * 更新BasicRule
	 **/
	@Override
	public BasicRuleVo updateBasicRule(BasicRuleBo basicRuleBo) {
		basicRuleBo.setTitle("标题");
		if (CollectionUtils.isEmpty(basicRuleDao.findAll())) {
			basicRuleBo.setCreateTime(new Date());
			return basicRuleConvert.toVo(basicRuleDao.save(basicRuleConvert.toEntity(basicRuleBo)));
		}
		BasicRule dbBasicRule = basicRuleDao.findAll().get(0);
		dbBasicRule.setContent(basicRuleBo.getContent());

		return basicRuleConvert.toVo(dbBasicRule);
	}

	/**
	 * 删除BasicRule
	 **/
	@Override
	public void removeBasicRuleById(int id) {
		basicRuleDao.deleteById(id);
	}

	/**
	 * 根据ID得到BasicRule
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BasicRuleVo getBasicRuleVoById(int id) {

		return basicRuleConvert.toVo(this.basicRuleDao.getOne(id));
	}

	/**
	 * 根据ID得到BasicRuleListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BasicRuleVo getListVoById(int id) {
		return basicRuleConvert.toVo(this.basicRuleDao.getOne(id));
	}

	/**
	 * 分页查询: BasicRule
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BasicRuleListVo> queryListVo(Pagination<BasicRule> query) {

		Page<BasicRule> pages = this.query(query);

		List<BasicRuleListVo> vos = basicRuleConvert.toListVos(pages.getContent());
		return new PageImpl<BasicRuleListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.basicRuleConvert = new EntityListVoBoSimpleConvert<BasicRule, BasicRuleBo, BasicRuleVo, BasicRuleSimple, BasicRuleListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<BasicRule, BasicRuleVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicRule, BasicRuleVo>(beanConvertManager) {
					@Override
					protected void postConvert(BasicRuleVo BasicRuleVo, BasicRule BasicRule) {

					}
				};
			}

			@Override
			protected BeanConvert<BasicRule, BasicRuleListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicRule, BasicRuleListVo>(beanConvertManager) {
					@Override
					protected void postConvert(BasicRuleListVo BasicRuleListVo, BasicRule BasicRule) {

					}
				};
			}

			@Override
			protected BeanConvert<BasicRule, BasicRuleBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicRule, BasicRuleBo>(beanConvertManager) {
					@Override
					protected void postConvert(BasicRuleBo BasicRuleBo, BasicRule BasicRule) {

					}
				};
			}

			@Override
			protected BeanConvert<BasicRuleBo, BasicRule> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicRuleBo, BasicRule>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<BasicRule, BasicRuleSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicRule, BasicRuleSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<BasicRuleSimple, BasicRule> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicRuleSimple, BasicRule>(beanConvertManager) {
					@Override
					public BasicRule convert(BasicRuleSimple BasicRuleSimple) throws BeansException {
						return basicRuleDao.getOne(BasicRuleSimple.getId());
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
	public BasicRuleVo getAll() {
		if (CollectionUtils.isEmpty(basicRuleDao.findAll())) {
			BasicRule basicRule = basicRuleDao.findAll().get(0);
			basicRule.setContent("暂无数据");
			return basicRuleConvert.toVo(basicRule);
		}
		return basicRuleConvert.toVo(basicRuleDao.findAll().get(0));
	}
}
