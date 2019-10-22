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
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.CouponDao;
import com.yi.core.activity.dao.CouponReceiveDao;
import com.yi.core.activity.domain.bo.CouponBo;
import com.yi.core.activity.domain.entity.Coupon;
import com.yi.core.activity.domain.entity.Coupon_;
import com.yi.core.activity.domain.simple.CouponSimple;
import com.yi.core.activity.domain.vo.CouponListVo;
import com.yi.core.activity.domain.vo.CouponVo;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.activity.service.ICouponService;
import com.yi.core.commodity.dao.ProductDao;
import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.member.service.IMemberService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 优惠券
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
// @CacheConfig(cacheNames = "coupon")
@Service
@Transactional
public class CouponServiceImpl implements ICouponService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CouponServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CouponDao couponDao;

	@Resource
	private CouponReceiveDao couponReceiveDao;

	@Resource
	private ICouponReceiveService couponReceiveService;

	private EntityListVoBoSimpleConvert<Coupon, CouponBo, CouponVo, CouponSimple, CouponListVo> couponConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<Coupon> query(Pagination<Coupon> query) {
		query.setEntityClazz(Coupon.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.deleted), Deleted.DEL_FALSE)));
			Object endTime = query.getParams().get("endTime");
			if (endTime != null) {
				list.add(criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.validType), ActivityEnum.VALID_TYPE_PERIOD.getCode()),
						criteriaBuilder.greaterThanOrEqualTo(root.get(Coupon_.endTime), new Date()), criteriaBuilder.lessThanOrEqualTo(root.get(Coupon_.startTime), new Date())),
						criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.validType), ActivityEnum.VALID_TYPE_FIXED_DAY.getCode()))));
			}
			Object couponType = query.getParams().get("couponType");
			if (couponType != null) {
				list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.couponType), couponType)));
			}
			list1.add(criteriaBuilder.desc(root.get(Coupon_.startTime)));
		}));
		Page<Coupon> page = couponDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponListVo> queryListVo(Pagination<Coupon> query) {
		query.setEntityClazz(Coupon.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Coupon_.createTime)));
			Object endTime = query.getParams().get("endTime");
			if (endTime != null) {
				list.add(criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.validType), ActivityEnum.VALID_TYPE_PERIOD.getCode()),
						criteriaBuilder.greaterThanOrEqualTo(root.get(Coupon_.endTime), new Date()), criteriaBuilder.lessThanOrEqualTo(root.get(Coupon_.startTime), new Date())),
						criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.validType), ActivityEnum.VALID_TYPE_FIXED_DAY.getCode()))));
			}
			Object couponType = query.getParams().get("couponType");
			if (couponType != null) {
				list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.couponType), couponType)));
			}
		}));
		Page<Coupon> pages = couponDao.findAll(query, query.getPageRequest());
		List<CouponListVo> vos = couponConvert.toListVos(pages.getContent());
		return new PageImpl<CouponListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponListVo> queryListVoForApp(Pagination<Coupon> query) {
		query.setEntityClazz(Coupon.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.deleted), Deleted.DEL_FALSE)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.receiveMode), ActivityEnum.RECEIVE_MODE_SELF.getCode())));
			list.add(criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.validType), ActivityEnum.VALID_TYPE_PERIOD.getCode()),
					criteriaBuilder.greaterThanOrEqualTo(root.get(Coupon_.endTime), new Date()), criteriaBuilder.lessThanOrEqualTo(root.get(Coupon_.startTime), new Date())),
					criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.validType), ActivityEnum.VALID_TYPE_FIXED_DAY.getCode()))));
			list.add(criteriaBuilder.greaterThan(root.get(Coupon_.quantity), root.get(Coupon_.receiveQuantity)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Coupon_.couponType), ActivityEnum.COUPON_TYPE_COUPON.getCode())));
			list1.add(criteriaBuilder.desc(root.get(Coupon_.startTime)));
		}));
		Page<Coupon> pages = couponDao.findAll(query, query.getPageRequest());
		List<CouponListVo> vos = couponConvert.toListVos(pages.getContent());
		Object memberId = query.getParams().get("member.id");
		if (memberId != null) {
			List<Object[]> checkReceive = couponReceiveDao.checkReceiveNum(memberId);
			for (CouponListVo tmpListVo : vos) {
				if (tmpListVo != null) {
					tmpListVo.setReceiveState(ActivityEnum.RECEIVE_STATE_NO.getCode());
					for (Object[] tmpObj : checkReceive) {
						if (ArrayUtils.isNotEmpty(tmpObj)) {
							if (tmpListVo.getId() == Integer.valueOf(tmpObj[0].toString()).intValue() && tmpListVo.getLimited() != null
									&& tmpListVo.getLimited() <= Integer.valueOf(tmpObj[1].toString()).intValue()) {
								tmpListVo.setReceiveState(ActivityEnum.RECEIVE_STATE_YES.getCode());
								break;
							}
						}
					}
				}
			}
		}
		return new PageImpl<CouponListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	public Coupon addCoupon(Coupon coupon) {
		if (coupon == null || (ActivityEnum.COUPON_TYPE_COUPON.getCode().equals(coupon.getCouponType()) && CollectionUtils.isEmpty(coupon.getCommodities()))
				|| CollectionUtils.isEmpty(coupon.getMemberLevels()) || StringUtils.isAnyBlank(coupon.getCouponName())) {
			throw new BusinessException("提交数据不能为空");
		}
		coupon.setCouponName(coupon.getCouponName().trim());
		int checkName = couponDao.countByCouponNameAndCouponTypeAndDeleted(coupon.getCouponName(), coupon.getCouponType(), Deleted.DEL_FALSE);
		if (checkName > 0) {
			LOG.error("该券名称已存在，couponName={}", coupon.getCouponName());
			throw new BusinessException("该券名称已存在");
		}
		// 有效期类型 -时间段
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(coupon.getValidType())) {
			if (coupon.getStartTime() == null || coupon.getEndTime() == null) {
				throw new BusinessException("请输入时间段");
			}
			coupon.setFixedDay(null);
		} // 有效期类型 -固定天数
		else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(coupon.getValidType())) {
			if (coupon.getFixedDay() == null) {
				throw new BusinessException("请输入固定天数");
			}
			coupon.setStartTime(null);
			coupon.setEndTime(null);
		}
		// 使用门槛
		if (coupon.getUseConditionType() == null) {
			coupon.setUseConditionType(ActivityEnum.USE_CONDITION_TYPE_UNLIMITED.getCode());
		}
		coupon.setCouponNo(NumberGenerateUtils.generateCouponNo());
		return couponDao.save(coupon);
	}

	@Override
	public CouponListVo addCoupon(CouponBo couponBo) {
		return couponConvert.toListVo(this.addCoupon(couponConvert.toEntity(couponBo)));
	}

	@Override
	public Coupon updateCoupon(Coupon coupon) {
		if (coupon == null || coupon.getId() < 1 || (ActivityEnum.COUPON_TYPE_COUPON.getCode().equals(coupon.getCouponType()) && CollectionUtils.isEmpty(coupon.getCommodities()))
				|| StringUtils.isAnyBlank(coupon.getCouponName())) {
			throw new BusinessException("提交数据不能为空");
		}
		coupon.setCouponName(coupon.getCouponName().trim());
		int checkName = couponDao.countByCouponNameAndCouponTypeAndDeletedAndIdNot(coupon.getCouponName(), coupon.getCouponType(), coupon.getId(), Deleted.DEL_FALSE);
		if (checkName > 0) {
			LOG.error("该券名称已存在，couponName={}", coupon.getCouponName());
			throw new BusinessException("该券名称已存在");
		}
		// 有效期类型 -时间段
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(coupon.getValidType())) {
			if (coupon.getStartTime() == null || coupon.getEndTime() == null) {
				throw new BusinessException("请输入时间段");
			}
			coupon.setFixedDay(null);
		} // 有效期类型 -固定天数
		else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(coupon.getValidType())) {
			if (coupon.getFixedDay() == null) {
				throw new BusinessException("请输入固定天数");
			}
			coupon.setStartTime(null);
			coupon.setEndTime(null);
		}
		// 使用门槛
		if (coupon.getUseConditionType() == null) {
			coupon.setUseConditionType(ActivityEnum.USE_CONDITION_TYPE_UNLIMITED.getCode());
		}
		Coupon dbCoupon = this.getById(coupon.getId());
		if (dbCoupon != null) {
			AttributeReplication.copying(coupon, dbCoupon, Coupon_.couponName, Coupon_.couponType, Coupon_.parValue, Coupon_.quantity, Coupon_.useConditionType, Coupon_.fullMoney,
					Coupon_.fullQuantity, Coupon_.receiveMode, Coupon_.limited, Coupon_.validType, Coupon_.startTime, Coupon_.endTime, Coupon_.fixedDay, Coupon_.feelWord);
			// 优惠券更新商品数据
			if (ActivityEnum.COUPON_TYPE_COUPON.getCode().equals(coupon.getCouponType())) {
				dbCoupon.setCommodities(coupon.getCommodities());
			}
			// 更新优惠券会员等级
			dbCoupon.setMemberLevels(coupon.getMemberLevels());
		}
		return dbCoupon;
	}

	// @CacheEvict(key = "#couponBo.getId()")
	@Override
	public CouponListVo updateCoupon(CouponBo couponBo) {
		return couponConvert.toListVo(this.updateCoupon(couponConvert.toEntity(couponBo)));
	}

	// @CacheEvict(key = "#couponId")
	@Override
	public void removeCouponById(int couponId) {
		Coupon dbCoupon = this.getById(couponId);
		if (dbCoupon != null) {
			dbCoupon.setDeleted(Deleted.DEL_TRUE);
			dbCoupon.setDelTime(new Date());
			// 删除优惠券商品中间表数据
			dbCoupon.setCommodities(null);
			// 删除优惠券会员等级中间表数据
			dbCoupon.setMemberLevels(null);
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Coupon getById(int couponId) {
		if (couponDao.existsById(couponId)) {
			return couponDao.getOne(couponId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponVo getVoById(int couponId) {
		return couponConvert.toVo(this.getById(couponId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponVo getListVoById(int couponId) {
		return couponConvert.toVo(this.getById(couponId));
	}

	@Override
	public EntityListVoBoSimpleConvert<Coupon, CouponBo, CouponVo, CouponSimple, CouponListVo> getCouponConvert() {
		return couponConvert;
	}

	protected void initConvert() {
		this.couponConvert = new EntityListVoBoSimpleConvert<Coupon, CouponBo, CouponVo, CouponSimple, CouponListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Coupon, CouponVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Coupon, CouponVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponVo CouponVo, Coupon Coupon) {

					}
				};
			}

			@Override
			protected BeanConvert<Coupon, CouponListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Coupon, CouponListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponListVo CouponListVo, Coupon Coupon) {

					}
				};
			}

			@Override
			protected BeanConvert<Coupon, CouponBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Coupon, CouponBo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponBo CouponBo, Coupon Coupon) {

					}
				};
			}

			@Override
			protected BeanConvert<CouponBo, Coupon> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponBo, Coupon>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Coupon, CouponSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Coupon, CouponSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponSimple, Coupon> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponSimple, Coupon>(beanConvertManager) {
					@Override
					public Coupon convert(CouponSimple CouponSimple) throws BeansException {
						return couponDao.getOne(CouponSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	// @Cacheable(key = "#couponId", unless = "#result eq null")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponVo getCouponDetail(int couponId) {
		Coupon dbCoupon = this.getById(couponId);
		if (dbCoupon != null) {
			dbCoupon.getCommodities().forEach(tmp -> {
				tmp.setProducts(null);
			});
			return couponConvert.toVo(dbCoupon);
		}
		return null;
	}

	// @CacheEvict(key = "#p0")
	@Override
	public void updateReceiveQuantity(Integer couponId, Integer receiveQuantity) {
		if (couponId != null && receiveQuantity != null) {
			Coupon dbCoupon = this.getById(couponId);
			if (dbCoupon != null) {
				dbCoupon.setReceiveQuantity(dbCoupon.getReceiveQuantity() + receiveQuantity);
			}
		}
	}

	// @CacheEvict(key = "#p0")
	@Override
	public void updateUseQuantity(Integer couponId, Integer useQuantity) {
		if (couponId != null && useQuantity != null) {
			Coupon dbCoupon = this.getById(couponId);
			if (dbCoupon != null) {
				dbCoupon.setUseQuantity(dbCoupon.getUseQuantity() + useQuantity);
			}
		}
	}

}
