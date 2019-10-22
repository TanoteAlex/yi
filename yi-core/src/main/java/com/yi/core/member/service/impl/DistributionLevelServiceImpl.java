/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service.impl;

import java.math.BigDecimal;
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

import com.yi.core.common.Deleted;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.dao.DistributionLevelDao;
import com.yi.core.member.domain.bo.DistributionLevelBo;
import com.yi.core.member.domain.entity.DistributionLevel;
import com.yi.core.member.domain.entity.DistributionLevel_;
import com.yi.core.member.domain.simple.DistributionLevelSimple;
import com.yi.core.member.domain.vo.DistributionLevelListVo;
import com.yi.core.member.domain.vo.DistributionLevelVo;
import com.yi.core.member.service.IDistributionLevelService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 分销等级
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class DistributionLevelServiceImpl implements IDistributionLevelService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(DistributionLevelServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private DistributionLevelDao distributionLevelDao;

	private EntityListVoBoSimpleConvert<DistributionLevel, DistributionLevelBo, DistributionLevelVo, DistributionLevelSimple, DistributionLevelListVo> distributionLevelConvert;

	/**
	 * 分页查询DistributionLevel
	 **/
	@Override
	public Page<DistributionLevel> query(Pagination<DistributionLevel> query) {
		query.setEntityClazz(DistributionLevel.class);
		Page<DistributionLevel> page = distributionLevelDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: DistributionLevel
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<DistributionLevelListVo> queryListVo(Pagination<DistributionLevel> query) {
		Page<DistributionLevel> pages = this.query(query);
		List<DistributionLevelListVo> vos = distributionLevelConvert.toListVos(pages.getContent());
		return new PageImpl<DistributionLevelListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建DistributionLevel
	 **/
	@Override
	public DistributionLevel addDistributionLevel(DistributionLevel distributionLevel) {
		return distributionLevelDao.save(distributionLevel);
	}

	/**
	 * 创建DistributionLevel
	 **/
	@Override
	public DistributionLevelVo addDistributionLevel(DistributionLevelBo distributionLevelBo) {
		return distributionLevelConvert.toVo(distributionLevelDao.save(distributionLevelConvert.toEntity(distributionLevelBo)));
	}

	/**
	 * 更新DistributionLevel
	 **/
	@Override
	public DistributionLevel updateDistributionLevel(DistributionLevel distributionLevel) {
		DistributionLevel dbDistributionLevel = distributionLevelDao.getOne(distributionLevel.getId());
		AttributeReplication.copying(distributionLevel, dbDistributionLevel, DistributionLevel_.guid, DistributionLevel_.name, DistributionLevel_.rank,
				DistributionLevel_.commissionRate, DistributionLevel_.remark, DistributionLevel_.createTime, DistributionLevel_.deleted, DistributionLevel_.delTime);
		return dbDistributionLevel;
	}

	/**
	 * 更新DistributionLevel
	 **/
	@Override
	public DistributionLevelVo updateDistributionLevel(DistributionLevelBo distributionLevelBo) {
		DistributionLevel dbDistributionLevel = this.updateDistributionLevel(distributionLevelConvert.toEntity(distributionLevelBo));
		return distributionLevelConvert.toVo(dbDistributionLevel);
	}

	/**
	 * 删除DistributionLevel
	 **/
	@Override
	public void removeDistributionLevelById(int id) {
		distributionLevelDao.deleteById(id);
	}

	/**
	 * 根据ID得到DistributionLevelBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DistributionLevel getById(int id) {
		return this.distributionLevelDao.getOne(id);
	}

	/**
	 * 根据ID得到DistributionLevelBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DistributionLevelBo getBoById(int id) {
		return distributionLevelConvert.toBo(this.distributionLevelDao.getOne(id));
	}

	/**
	 * 根据ID得到DistributionLevelVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DistributionLevelVo getVoById(int id) {
		return distributionLevelConvert.toVo(this.distributionLevelDao.getOne(id));
	}

	/**
	 * 根据ID得到DistributionLevelListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DistributionLevelListVo getListVoById(int id) {
		return distributionLevelConvert.toListVo(this.distributionLevelDao.getOne(id));
	}

	protected void initConvert() {
		this.distributionLevelConvert = new EntityListVoBoSimpleConvert<DistributionLevel, DistributionLevelBo, DistributionLevelVo, DistributionLevelSimple, DistributionLevelListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<DistributionLevel, DistributionLevelVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<DistributionLevel, DistributionLevelVo>(beanConvertManager) {
					@Override
					protected void postConvert(DistributionLevelVo distributionLevelVo, DistributionLevel distributionLevel) {
					}
				};
			}

			@Override
			protected BeanConvert<DistributionLevel, DistributionLevelListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<DistributionLevel, DistributionLevelListVo>(beanConvertManager) {
					@Override
					protected void postConvert(DistributionLevelListVo distributionLevelListVo, DistributionLevel distributionLevel) {
					}
				};
			}

			@Override
			protected BeanConvert<DistributionLevel, DistributionLevelBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<DistributionLevel, DistributionLevelBo>(beanConvertManager) {
					@Override
					protected void postConvert(DistributionLevelBo distributionLevelBo, DistributionLevel distributionLevel) {
					}
				};
			}

			@Override
			protected BeanConvert<DistributionLevelBo, DistributionLevel> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<DistributionLevelBo, DistributionLevel>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<DistributionLevel, DistributionLevelSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<DistributionLevel, DistributionLevelSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<DistributionLevelSimple, DistributionLevel> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<DistributionLevelSimple, DistributionLevel>(beanConvertManager) {
					@Override
					public DistributionLevel convert(DistributionLevelSimple distributionLevelSimple) throws BeansException {
						return distributionLevelDao.getOne(distributionLevelSimple.getId());
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
	 * 获取一级佣金比例
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal getFirstLevelCommissionRate() {
		DistributionLevel dbDistributionLevel = distributionLevelDao.findByRankAndDeleted(MemberEnum.DISTRIBUTION_LEVEL_FIRST.getCode(), Deleted.DEL_FALSE);
		if (dbDistributionLevel != null && dbDistributionLevel.getCommissionRate() != null) {
			return dbDistributionLevel.getCommissionRate();
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 获取二级佣金比例
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal getSecondLevelCommissionRate() {
		DistributionLevel dbDistributionLevel = distributionLevelDao.findByRankAndDeleted(MemberEnum.DISTRIBUTION_LEVEL_SECOND.getCode(), Deleted.DEL_FALSE);
		if (dbDistributionLevel != null && dbDistributionLevel.getCommissionRate() != null) {
			return dbDistributionLevel.getCommissionRate();
		}
		return BigDecimal.ZERO;
	}
}
