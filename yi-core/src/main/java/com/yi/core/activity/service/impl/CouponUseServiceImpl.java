/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

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

import com.yi.core.activity.dao.CouponUseDao;
import com.yi.core.activity.domain.bo.CouponUseBo;
import com.yi.core.activity.domain.entity.CouponUse;
import com.yi.core.activity.domain.entity.CouponUse_;
import com.yi.core.activity.domain.simple.CouponUseSimple;
import com.yi.core.activity.domain.vo.CouponUseListVo;
import com.yi.core.activity.domain.vo.CouponUseVo;
import com.yi.core.activity.service.ICouponUseService;
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
public class CouponUseServiceImpl implements ICouponUseService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CouponUseServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CouponUseDao couponUseDao;

	private EntityListVoBoSimpleConvert<CouponUse, CouponUseBo, CouponUseVo, CouponUseSimple, CouponUseListVo> couponUseConvert;

	@Override
	public EntityListVoBoSimpleConvert<CouponUse, CouponUseBo, CouponUseVo, CouponUseSimple, CouponUseListVo> getCouponUseConvert() {
		return this.couponUseConvert;
	}

	@Override
	public CouponUse getCouponUseById(int couponUseId) {
		return couponUseDao.getOne(couponUseId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponUseVo getCouponUseVoById(int couponUseId) {

		return couponUseConvert.toVo(this.couponUseDao.getOne(couponUseId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponUseVo getCouponUseListVoById(int couponUseId) {
		return couponUseConvert.toVo(this.couponUseDao.getOne(couponUseId));
	}

	@Override
	public CouponUse addCouponUse(CouponUse couponUse) {
		// couponUse.setSupplier(new Supplier(1));
		return couponUseDao.save(couponUse);
	}

	@Override
	public CouponUseVo addCouponUse(CouponUseBo couponUseBo) {
		// couponUseBo.setSupplier(new Supplier(1));
		return couponUseConvert.toVo(couponUseDao.save(couponUseConvert.toEntity(couponUseBo)));
	}

	@Override
	public CouponUse updateCouponUse(CouponUse couponUse) {

		CouponUse dbCouponUse = couponUseDao.getOne(couponUse.getId());
		AttributeReplication.copying(couponUse, dbCouponUse, CouponUse_.couponNo, CouponUse_.parValue, CouponUse_.use, CouponUse_.surplus, CouponUse_.memberPhone,
				CouponUse_.useTime, CouponUse_.orderNo);
		return dbCouponUse;
	}

	@Override
	public CouponUseVo updateCouponUse(CouponUseBo couponUseBo) {
		CouponUse dbCouponUse = couponUseDao.getOne(couponUseBo.getId());
		AttributeReplication.copying(couponUseBo, dbCouponUse, CouponUse_.couponNo, CouponUse_.parValue, CouponUse_.use, CouponUse_.surplus, CouponUse_.memberPhone,
				CouponUse_.useTime, CouponUse_.orderNo);
		return couponUseConvert.toVo(dbCouponUse);
	}

	@Override
	public void removeCouponUseById(int couponUseId) {
		couponUseDao.deleteById(couponUseId);
	}

	@Override
	public Page<CouponUse> query(Pagination<CouponUse> query) {
		query.setEntityClazz(CouponUse.class);
		Page<CouponUse> page = couponUseDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponUseListVo> queryListVo(Pagination<CouponUse> query) {

		Page<CouponUse> pages = this.query(query);

		List<CouponUseListVo> vos = couponUseConvert.toListVos(pages.getContent());
		return new PageImpl<CouponUseListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.couponUseConvert = new EntityListVoBoSimpleConvert<CouponUse, CouponUseBo, CouponUseVo, CouponUseSimple, CouponUseListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<CouponUse, CouponUseVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponUse, CouponUseVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponUseVo CouponUseVo, CouponUse CouponUse) {

					}
				};
			}

			@Override
			protected BeanConvert<CouponUse, CouponUseListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponUse, CouponUseListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponUseListVo CouponUseListVo, CouponUse CouponUse) {

					}
				};
			}

			@Override
			protected BeanConvert<CouponUse, CouponUseBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponUse, CouponUseBo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponUseBo CouponUseBo, CouponUse CouponUse) {

					}
				};
			}

			@Override
			protected BeanConvert<CouponUseBo, CouponUse> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponUseBo, CouponUse>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponUse, CouponUseSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponUse, CouponUseSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponUseSimple, CouponUse> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponUseSimple, CouponUse>(beanConvertManager) {
					@Override
					public CouponUse convert(CouponUseSimple CouponUseSimple) throws BeansException {
						return couponUseDao.getOne(CouponUseSimple.getId());
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
