/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import java.util.Collection;
import java.util.Date;
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

import com.yi.core.activity.dao.CouponGrantStepDao;
import com.yi.core.activity.domain.bo.CouponGrantStepBo;
import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.activity.domain.entity.CouponGrantStep;
import com.yi.core.activity.domain.entity.CouponGrantStep_;
import com.yi.core.activity.domain.simple.CouponGrantStepSimple;
import com.yi.core.activity.domain.vo.CouponGrantStepListVo;
import com.yi.core.activity.domain.vo.CouponGrantStepVo;
import com.yi.core.activity.service.ICouponGrantStepService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
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
public class CouponGrantStepServiceImpl implements ICouponGrantStepService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CouponGrantStepServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CouponGrantStepDao couponGrantStepDao;

	private EntityListVoBoSimpleConvert<CouponGrantStep, CouponGrantStepBo, CouponGrantStepVo, CouponGrantStepSimple, CouponGrantStepListVo> couponGrantStepConvert;

	/**
	 * 分页查询CouponGrantStep
	 **/
	@Override
	public Page<CouponGrantStep> query(Pagination<CouponGrantStep> query) {
		query.setEntityClazz(CouponGrantStep.class);
		Page<CouponGrantStep> page = couponGrantStepDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: CouponGrantStep
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponGrantStepListVo> queryListVo(Pagination<CouponGrantStep> query) {
		Page<CouponGrantStep> pages = this.query(query);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponGrantStep_.deleted), Deleted.DEL_FALSE)));
		}));
		List<CouponGrantStepListVo> vos = couponGrantStepConvert.toListVos(pages.getContent());
		return new PageImpl<CouponGrantStepListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: CouponGrantStep
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponGrantStepListVo> queryListVoForApp(Pagination<CouponGrantStep> query) {
		query.setEntityClazz(CouponGrantStep.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponGrantStep_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<CouponGrantStep> pages = couponGrantStepDao.findAll(query, query.getPageRequest());
		List<CouponGrantStepListVo> vos = couponGrantStepConvert.toListVos(pages.getContent());
		return new PageImpl<CouponGrantStepListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建CouponGrantStep
	 **/
	@Override
	public CouponGrantStep addCouponGrantStep(CouponGrantStep couponGrantStep) {
		if (couponGrantStep == null || couponGrantStep.getCouponGrantConfig() == null || couponGrantStep.getGrantRate() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return couponGrantStepDao.save(couponGrantStep);
	}

	/**
	 * 创建CouponGrantStep
	 **/
	@Override
	public CouponGrantStepListVo addCouponGrantStep(CouponGrantStepBo couponGrantStepBo) {
		return couponGrantStepConvert.toListVo(this.addCouponGrantStep(couponGrantStepConvert.toEntity(couponGrantStepBo)));
	}

	/**
	 * 更新CouponGrantStep
	 **/
	@Override
	public CouponGrantStep updateCouponGrantStep(CouponGrantStep couponGrantStep) {
		CouponGrantStep dbCouponGrantStep = couponGrantStepDao.getOne(couponGrantStep.getId());
		AttributeReplication.copying(couponGrantStep, dbCouponGrantStep, CouponGrantStep_.couponGrantConfig, CouponGrantStep_.grantNode, CouponGrantStep_.grantRate,
				CouponGrantStep_.remark);
		return dbCouponGrantStep;
	}

	/**
	 * 更新CouponGrantStep
	 **/
	@Override
	public CouponGrantStepListVo updateCouponGrantStep(CouponGrantStepBo couponGrantStepBo) {
		CouponGrantStep dbCouponGrantStep = this.updateCouponGrantStep(couponGrantStepConvert.toEntity(couponGrantStepBo));
		return couponGrantStepConvert.toListVo(dbCouponGrantStep);
	}

	/**
	 * 删除CouponGrantStep
	 **/
	@Override
	public void removeCouponGrantStepById(int id) {
		CouponGrantStep dbCouponGrantStep = couponGrantStepDao.getOne(id);
		if (dbCouponGrantStep != null) {
			dbCouponGrantStep.setDeleted(Deleted.DEL_TRUE);
			dbCouponGrantStep.setDelTime(new Date());
		}
	}

	/**
	 * 根据ID得到CouponGrantStep
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantStep getCouponGrantStepById(int id) {
		if (couponGrantStepDao.existsById(id)) {
			return this.couponGrantStepDao.getOne(id);
		}
		return null;
	}

	/**
	 * 根据ID得到CouponGrantStepBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantStepBo getCouponGrantStepBoById(int id) {
		return couponGrantStepConvert.toBo(this.getCouponGrantStepById(id));
	}

	/**
	 * 根据ID得到CouponGrantStepVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantStepVo getCouponGrantStepVoById(int id) {
		return couponGrantStepConvert.toVo(this.getCouponGrantStepById(id));
	}

	/**
	 * 根据ID得到CouponGrantStepListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantStepListVo getListVoById(int id) {
		return couponGrantStepConvert.toListVo(this.getCouponGrantStepById(id));
	}

	protected void initConvert() {
		this.couponGrantStepConvert = new EntityListVoBoSimpleConvert<CouponGrantStep, CouponGrantStepBo, CouponGrantStepVo, CouponGrantStepSimple, CouponGrantStepListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<CouponGrantStep, CouponGrantStepVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantStep, CouponGrantStepVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponGrantStepVo couponGrantStepVo, CouponGrantStep couponGrantStep) {
					}
				};
			}

			@Override
			protected BeanConvert<CouponGrantStep, CouponGrantStepListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantStep, CouponGrantStepListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponGrantStepListVo couponGrantStepListVo, CouponGrantStep couponGrantStep) {
					}
				};
			}

			@Override
			protected BeanConvert<CouponGrantStep, CouponGrantStepBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantStep, CouponGrantStepBo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponGrantStepBo couponGrantStepBo, CouponGrantStep couponGrantStep) {
					}
				};
			}

			@Override
			protected BeanConvert<CouponGrantStepBo, CouponGrantStep> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantStepBo, CouponGrantStep>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponGrantStep, CouponGrantStepSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantStep, CouponGrantStepSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponGrantStepSimple, CouponGrantStep> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantStepSimple, CouponGrantStep>(beanConvertManager) {
					@Override
					public CouponGrantStep convert(CouponGrantStepSimple couponGrantStepSimple) throws BeansException {
						return couponGrantStepDao.getOne(couponGrantStepSimple.getId());
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
	public void batchAddGrantStep(CouponGrantConfig grantConfig, Collection<CouponGrantStep> grantSteps) {
		if (grantConfig != null && CollectionUtils.isNotEmpty(grantSteps)) {
			grantSteps.forEach(tmpStep -> {
				tmpStep.setCouponGrantConfig(grantConfig);
			});
			couponGrantStepDao.saveAll(grantSteps);
		}
	}

	@Override
	public void batchUpdateGrantStep(CouponGrantConfig grantConfig, Collection<CouponGrantStep> grantSteps) {
		if (grantConfig != null && CollectionUtils.isNotEmpty(grantSteps)) {
			grantSteps.forEach(tmpStep -> {
				tmpStep.setCouponGrantConfig(grantConfig);
			});
			// 查询当前配置的发放步骤
			List<CouponGrantStep> dbCouponGrantSteps = couponGrantStepDao.findByCouponGrantConfig_idAndDeleted(grantConfig.getId(), Deleted.DEL_FALSE);
			// 需要新增的数据
			Set<CouponGrantStep> saveSets = grantSteps.stream().filter(e1 -> dbCouponGrantSteps.stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 需要更新的数据
			Set<CouponGrantStep> updateSets = grantSteps.stream().filter(e1 -> dbCouponGrantSteps.stream().anyMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 需要删除的数据
			Set<CouponGrantStep> deleteSets = dbCouponGrantSteps.stream().filter(e1 -> grantSteps.stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 保存数据
			couponGrantStepDao.saveAll(saveSets);
			// 修改数据
			for (CouponGrantStep tmpStep : updateSets) {
				if (tmpStep != null) {
					this.updateCouponGrantStep(tmpStep);
				}
			}
			// 删除数据
			deleteSets.forEach(tmpStep -> {
				tmpStep.setDeleted(Deleted.DEL_TRUE);
				tmpStep.setDelTime(new Date());
			});
		}
	}
}
