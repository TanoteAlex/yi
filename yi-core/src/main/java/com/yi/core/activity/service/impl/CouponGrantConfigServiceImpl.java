/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import java.util.Date;
import java.util.List;

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

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.CouponGrantConfigDao;
import com.yi.core.activity.domain.bo.CouponGrantConfigBo;
import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.activity.domain.entity.CouponGrantConfig_;
import com.yi.core.activity.domain.simple.CouponGrantConfigSimple;
import com.yi.core.activity.domain.vo.CouponGrantConfigListVo;
import com.yi.core.activity.domain.vo.CouponGrantConfigVo;
import com.yi.core.activity.service.ICouponGrantConfigService;
import com.yi.core.activity.service.ICouponGrantRecordService;
import com.yi.core.activity.service.ICouponGrantStepService;
import com.yi.core.activity.service.ICouponService;
import com.yi.core.commodity.service.ICommodityService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 优惠券发放配置
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class CouponGrantConfigServiceImpl implements ICouponGrantConfigService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CouponGrantConfigServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CouponGrantConfigDao couponGrantConfigDao;

	@Resource
	private ICouponGrantStepService couponGrantStepService;

	@Resource
	private ICouponGrantRecordService couponGrantRecordService;

	@Resource
	private ICouponService couponService;

	@Resource
	private ICommodityService commodityService;

	private EntityListVoBoSimpleConvert<CouponGrantConfig, CouponGrantConfigBo, CouponGrantConfigVo, CouponGrantConfigSimple, CouponGrantConfigListVo> couponGrantConfigConvert;

	/**
	 * 分页查询CouponGrantConfig
	 **/
	@Override
	public Page<CouponGrantConfig> query(Pagination<CouponGrantConfig> query) {
		query.setEntityClazz(CouponGrantConfig.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponGrantConfig_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(CouponGrantConfig_.createTime)));
		}));
		Page<CouponGrantConfig> page = couponGrantConfigDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: CouponGrantConfig
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponGrantConfigListVo> queryListVo(Pagination<CouponGrantConfig> query) {
		Page<CouponGrantConfig> pages = this.query(query);
		List<CouponGrantConfigListVo> vos = couponGrantConfigConvert.toListVos(pages.getContent());
		return new PageImpl<CouponGrantConfigListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: CouponGrantConfig
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponGrantConfigListVo> queryListVoForApp(Pagination<CouponGrantConfig> query) {
		query.setEntityClazz(CouponGrantConfig.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponGrantConfig_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(CouponGrantConfig_.createTime)));
		}));
		Page<CouponGrantConfig> pages = couponGrantConfigDao.findAll(query, query.getPageRequest());
		List<CouponGrantConfigListVo> vos = couponGrantConfigConvert.toListVos(pages.getContent());
		return new PageImpl<CouponGrantConfigListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建CouponGrantConfig
	 **/
	@Override
	public CouponGrantConfig addCouponGrantConfig(CouponGrantConfig couponGrantConfig) {
		if (couponGrantConfig == null || couponGrantConfig.getCoupon() == null || couponGrantConfig.getGrantStrategy() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		if (couponGrantConfig.getState() == null) {
			couponGrantConfig.setState(ActivityEnum.STATE_ENABLE.getCode());
		}
		// 分批发放
		if (ActivityEnum.GRANT_STRATEGY_STEP.getCode().equals(couponGrantConfig.getGrantStrategy())) {
			if (CollectionUtils.isEmpty(couponGrantConfig.getCouponGrantSteps())) {
				throw new BusinessException("分批发放，发放步骤不能为空");
			}
		} else {
			couponGrantConfig.setCouponGrantSteps(null);
		}
		// 保存发放方案
		CouponGrantConfig dbCouponGrantConfig = couponGrantConfigDao.save(couponGrantConfig);
		// 保存发放步骤
		couponGrantStepService.batchAddGrantStep(dbCouponGrantConfig, couponGrantConfig.getCouponGrantSteps());
		// 更新商品
		commodityService.batchAddForVoucherGrantConfig(couponGrantConfig.getCommodities(), dbCouponGrantConfig);
		return dbCouponGrantConfig;
	}

	/**
	 * 创建CouponGrantConfig
	 **/
	@Override
	public CouponGrantConfigListVo addCouponGrantConfig(CouponGrantConfigBo couponGrantConfigBo) {
		return couponGrantConfigConvert.toListVo(this.addCouponGrantConfig(couponGrantConfigConvert.toEntity(couponGrantConfigBo)));
	}

	/**
	 * 更新CouponGrantConfig
	 **/
	@Override
	public CouponGrantConfig updateCouponGrantConfig(CouponGrantConfig couponGrantConfig) {
		if (couponGrantConfig == null || couponGrantConfig.getId() < 1 || couponGrantConfig.getCoupon() == null || couponGrantConfig.getGrantStrategy() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		CouponGrantConfig dbCouponGrantConfig = this.getCouponGrantConfigById(couponGrantConfig.getId());
		if (dbCouponGrantConfig != null) {
			AttributeReplication.copying(couponGrantConfig, dbCouponGrantConfig, CouponGrantConfig_.coupon, CouponGrantConfig_.grantStrategy, CouponGrantConfig_.grantNode,
					CouponGrantConfig_.state, CouponGrantConfig_.freezeDays, CouponGrantConfig_.thawNode, CouponGrantConfig_.remark);
			// 分批发放
			if (ActivityEnum.GRANT_STRATEGY_STEP.getCode().equals(couponGrantConfig.getGrantStrategy())) {
				if (CollectionUtils.isEmpty(couponGrantConfig.getCouponGrantSteps())) {
					throw new BusinessException("分批发放，发放步骤不能为空");
				}
			} else {
				dbCouponGrantConfig.setCouponGrantSteps(null);
			}
			// 保存发放步骤
			couponGrantStepService.batchUpdateGrantStep(dbCouponGrantConfig, couponGrantConfig.getCouponGrantSteps());
			// 更新商品
			commodityService.batchUpdateForVoucherGrantConfig(couponGrantConfig.getCommodities(), dbCouponGrantConfig);
			return dbCouponGrantConfig;
		}
		return null;
	}

	/**
	 * 更新CouponGrantConfig
	 **/
	@Override
	public CouponGrantConfigListVo updateCouponGrantConfig(CouponGrantConfigBo couponGrantConfigBo) {
		CouponGrantConfig dbCouponGrantConfig = this.updateCouponGrantConfig(couponGrantConfigConvert.toEntity(couponGrantConfigBo));
		return couponGrantConfigConvert.toListVo(dbCouponGrantConfig);
	}

	/**
	 * 删除CouponGrantConfig
	 **/
	@Override
	public void removeCouponGrantConfigById(int id) {
		CouponGrantConfig dbCouponGrantConfig = this.getCouponGrantConfigById(id);
		if (dbCouponGrantConfig != null) {
			dbCouponGrantConfig.setDeleted(Deleted.DEL_TRUE);
			dbCouponGrantConfig.setDelTime(new Date());
			// 删除 发放配置关联的商品
			commodityService.batchDeleteForVoucherGrantConfig(dbCouponGrantConfig.getCommodities());
		}
	}

	/**
	 * 根据ID得到CouponGrantConfig
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantConfig getCouponGrantConfigById(int id) {
		if (couponGrantConfigDao.existsById(id)) {
			return this.couponGrantConfigDao.getOne(id);
		}
		return null;
	}

	/**
	 * 根据ID得到CouponGrantConfigBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantConfigBo getCouponGrantConfigBoById(int id) {
		return couponGrantConfigConvert.toBo(this.couponGrantConfigDao.getOne(id));
	}

	/**
	 * 根据ID得到CouponGrantConfigVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantConfigVo getCouponGrantConfigVoById(int id) {
		return couponGrantConfigConvert.toVo(this.couponGrantConfigDao.getOne(id));
	}

	/**
	 * 根据ID得到CouponGrantConfigListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantConfigListVo getListVoById(int id) {
		return couponGrantConfigConvert.toListVo(this.couponGrantConfigDao.getOne(id));
	}

	/**
	 * 启用禁用
	 * 
	 * @param couponGrantConfigId
	 * @return
	 */
	@Override
	public CouponGrantConfigVo updateState(int couponGrantConfigId) {
		CouponGrantConfig couponGrantConfig = couponGrantConfigDao.getOne(couponGrantConfigId);
		Integer state = couponGrantConfig.getState();
		if (ActivityEnum.STATE_DISABLE.getCode().equals(state)) {
			couponGrantConfig.setState(ActivityEnum.STATE_ENABLE.getCode());
		} else if (ActivityEnum.STATE_ENABLE.getCode().equals(state)) {
			couponGrantConfig.setState(ActivityEnum.STATE_DISABLE.getCode());
		}
		return couponGrantConfigConvert.toVo(couponGrantConfig);
	}

	protected void initConvert() {
		this.couponGrantConfigConvert = new EntityListVoBoSimpleConvert<CouponGrantConfig, CouponGrantConfigBo, CouponGrantConfigVo, CouponGrantConfigSimple, CouponGrantConfigListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<CouponGrantConfig, CouponGrantConfigVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantConfig, CouponGrantConfigVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponGrantConfigVo couponGrantConfigVo, CouponGrantConfig couponGrantConfig) {
					}
				};
			}

			@Override
			protected BeanConvert<CouponGrantConfig, CouponGrantConfigListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantConfig, CouponGrantConfigListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponGrantConfigListVo couponGrantConfigListVo, CouponGrantConfig couponGrantConfig) {
					}
				};
			}

			@Override
			protected BeanConvert<CouponGrantConfig, CouponGrantConfigBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantConfig, CouponGrantConfigBo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponGrantConfigBo couponGrantConfigBo, CouponGrantConfig couponGrantConfig) {
					}
				};
			}

			@Override
			protected BeanConvert<CouponGrantConfigBo, CouponGrantConfig> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantConfigBo, CouponGrantConfig>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponGrantConfig, CouponGrantConfigSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantConfig, CouponGrantConfigSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponGrantConfigSimple, CouponGrantConfig> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantConfigSimple, CouponGrantConfig>(beanConvertManager) {
					@Override
					public CouponGrantConfig convert(CouponGrantConfigSimple couponGrantConfigSimple) throws BeansException {
						return couponGrantConfigDao.getOne(couponGrantConfigSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}
}
