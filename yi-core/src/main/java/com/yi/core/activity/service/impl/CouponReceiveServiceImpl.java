/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;

import com.yi.core.activity.domain.entity.*;
import com.yi.core.activity.domain.simple.CouponGrantRecordSimple;
import com.yi.core.activity.domain.simple.CouponSimple;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yi.core.order.domain.simple.SaleOrderSimple;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.CouponReceiveDao;
import com.yi.core.activity.domain.bo.CouponBo;
import com.yi.core.activity.domain.bo.CouponReceiveBo;
import com.yi.core.activity.domain.simple.CouponReceiveSimple;
import com.yi.core.activity.domain.vo.CouponReceiveListVo;
import com.yi.core.activity.domain.vo.CouponReceiveVo;
import com.yi.core.activity.service.ICouponGrantConfigService;
import com.yi.core.activity.service.ICouponGrantRecordService;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.activity.service.ICouponService;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.common.Deleted;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.MemberLevel;
import com.yi.core.member.domain.entity.Member_;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.date.CalendarUtils;
import com.yihz.common.utils.date.DateUtils;

/**
 * 优惠券领取表
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
// @CacheConfig(cacheNames = "couponReceive")
@Service
@Transactional
public class CouponReceiveServiceImpl implements ICouponReceiveService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CouponReceiveServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CouponReceiveDao couponReceiveDao;

	@Resource
	private IMemberService memberService;

	@Resource
	private ICouponService couponService;

	@Resource
	private ICouponGrantConfigService couponGrantConfigService;

	@Resource
	private ICouponGrantRecordService couponGrantRecordService;

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private IAccountService accountService;

	private EntityListVoBoSimpleConvert<CouponReceive, CouponReceiveBo, CouponReceiveVo, CouponReceiveSimple, CouponReceiveListVo> couponReceiveConvert;

	@Override
	public Page<CouponReceive> query(Pagination<CouponReceive> query) {
		query.setEntityClazz(CouponReceive.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponReceive_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(CouponReceive_.receiveTime)));
		}));
		Page<CouponReceive> page = couponReceiveDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponReceiveListVo> queryListVo(Pagination<CouponReceive> query) {
		Page<CouponReceive> pages = this.query(query);
		List<CouponReceiveListVo> vos = couponReceiveConvert.toListVos(pages.getContent());
		return new PageImpl<CouponReceiveListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponReceiveListVo> queryListVoForApp(Pagination<CouponReceive> query) {
		query.setEntityClazz(CouponReceive.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponReceive_.deleted), Deleted.DEL_FALSE)));
			if (CollectionUtils.isEmpty(query.getSort())) {
				list1.add(criteriaBuilder.desc(root.get(CouponReceive_.receiveTime)));
			}
		}));
		Page<CouponReceive> pages = couponReceiveDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.setMember(null);
			tmp.setSaleOrder(null);
			tmp.setCouponGrantRecord(null);
			tmp.setCouponUses(null);
		});
		/**List<CouponReceive> content = pages.getContent();  if (content==null){return  null;}
		CouponReceiveListVo couponReceiveListVo = new CouponReceiveListVo();
		List<CouponReceiveListVo> vos = new ArrayList<CouponReceiveListVo>();
		SaleOrderSimple saleOrderSimple = new SaleOrderSimple();
		MemberSimple memberSimple = new MemberSimple();
		CouponSimple couponSimple = new CouponSimple();
		CouponGrantRecordSimple couponGrantRecordSimple = new CouponGrantRecordSimple();
		for (CouponReceive  coupon: content) {
			BeanUtils.copyProperties(coupon,couponReceiveListVo);
			if (coupon.getCoupon()!=null){BeanUtils.copyProperties(coupon.getCoupon(),couponSimple); couponReceiveListVo.setCoupon(couponSimple);}
			if (coupon.getSaleOrder()!=null){BeanUtils.copyProperties(coupon.getSaleOrder(),saleOrderSimple); couponReceiveListVo.setSaleOrder(saleOrderSimple);}
			if (coupon.getMember()!=null){BeanUtils.copyProperties(coupon.getMember(),memberSimple); couponReceiveListVo.setMember(memberSimple);}
			vos.add(couponReceiveListVo);
		}
		 */List<CouponReceive> content = pages.getContent();
		List<CouponReceiveListVo> vos = new ArrayList<>(content.size());
	//	vos.add(couponReceiveConvert.toListVo(pages.getContent().get(content.size()-1)));
		for (int i = 0; i<content.size() ; i++) {
			CouponReceiveListVo couponReceiveListVo = couponReceiveConvert.toListVo(pages.getContent().get(i));
			vos.add(couponReceiveListVo);
		}
	//	vos.add(couponReceiveConvert.toListVo(pages.getContent().get(content.size()-1)));

	//	CouponReceiveListVo couponReceiveListVo = couponReceiveConvert.toListVo(pages.getContent().get(0));
	//	List<CouponReceiveListVo> vos = couponReceiveConvert.toListVos(pages.getContent());
		vos.forEach(tmp -> {
			if (tmp != null && tmp.getEndTime() != null) {
				long validDays = CalendarUtils.diffDays(new Date(), tmp.getEndTime());
				if (validDays > 0) {
					tmp.setValidDays(validDays);
				}
			}
		});
		System.out.println("---------+++++---------");
		return new PageImpl<CouponReceiveListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponReceive getById(int couponReceiveId) {
		if (couponReceiveDao.existsById(couponReceiveId)) {
			return couponReceiveDao.getOne(couponReceiveId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponReceiveVo getVoById(int couponReceiveId) {
		return couponReceiveConvert.toVo(this.getById(couponReceiveId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponReceiveListVo getListVoById(int couponReceiveId) {
		return couponReceiveConvert.toListVo(this.getById(couponReceiveId));
	}

	@Override
	public CouponReceive addCouponReceive(CouponReceive couponReceive) {
		if (couponReceive == null || couponReceive.getMember() == null || couponReceive.getCoupon() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return couponReceiveDao.save(couponReceive);
	}

	@Override
	public CouponReceiveListVo addCouponReceive(CouponReceiveBo couponReceiveBo) {
		return couponReceiveConvert.toListVo(couponReceiveDao.save(couponReceiveConvert.toEntity(couponReceiveBo)));
	}

	@Override
	public CouponReceive updateCouponReceive(CouponReceive couponReceive) {
		CouponReceive dbCouponReceive = couponReceiveDao.getOne(couponReceive.getId());
		AttributeReplication.copying(couponReceive, dbCouponReceive, CouponReceive_.used, CouponReceive_.surplus, CouponReceive_.memberPhone, CouponReceive_.startTime,
				CouponReceive_.endTime, CouponReceive_.state, CouponReceive_.useTime, CouponReceive_.saleOrder, CouponReceive_.orderNo);
		return dbCouponReceive;
	}

	// @CacheEvict(key = "#couponReceiveBo.getCoupon().getId()")
	@Override
	public CouponReceiveListVo updateCouponReceive(CouponReceiveBo couponReceiveBo) {
		CouponReceive dbCouponReceive = this.updateCouponReceive(couponReceiveConvert.toEntity(couponReceiveBo));
		return couponReceiveConvert.toListVo(dbCouponReceive);
	}

	// @CacheEvict(allEntries = true)
	@Override
	public void removeCouponReceiveById(int couponReceiveId) {
		CouponReceive dbCouponReceive = this.getById(couponReceiveId);
		if (dbCouponReceive != null) {
			dbCouponReceive.setDeleted(Deleted.DEL_TRUE);
			dbCouponReceive.setDelTime(new Date());
		}
	}

	protected void initConvert() {
		this.couponReceiveConvert = new EntityListVoBoSimpleConvert<CouponReceive, CouponReceiveBo, CouponReceiveVo, CouponReceiveSimple, CouponReceiveListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<CouponReceive, CouponReceiveVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceive, CouponReceiveVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponReceiveVo couponReceiveVo, CouponReceive couponReceive) {
					}
				};
			}

			@Override
			protected BeanConvert<CouponReceive, CouponReceiveListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceive, CouponReceiveListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponReceiveListVo couponReceiveListVo, CouponReceive couponReceive) {

					}
				};
			}

			@Override
			protected BeanConvert<CouponReceive, CouponReceiveBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceive, CouponReceiveBo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponReceiveBo couponReceiveBo, CouponReceive couponReceive) {

					}
				};
			}

			@Override
			protected BeanConvert<CouponReceiveBo, CouponReceive> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceiveBo, CouponReceive>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponReceive, CouponReceiveSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceive, CouponReceiveSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponReceiveSimple, CouponReceive> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponReceiveSimple, CouponReceive>(beanConvertManager) {
					@Override
					public CouponReceive convert(CouponReceiveSimple CouponReceiveSimple) throws BeansException {
						return couponReceiveDao.getOne(CouponReceiveSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	// @Cacheable(key = "#couponReceiveId",unless = "#result eq null")
	@Override
	public CouponReceiveVo getCouponReceiveDetail(Integer couponReceiveId) {
		if (couponReceiveId != null) {
			CouponReceive dbCouponReceive = this.getById(couponReceiveId);
			if (dbCouponReceive != null) {
				// dbCouponReceive.setCoupon(null);
				// dbCouponReceive.setMember(null);
				dbCouponReceive.setSaleOrder(null);
				dbCouponReceive.setCouponGrantRecord(null);
				return couponReceiveConvert.toVo(dbCouponReceive);
			}
		}
		return null;
	}

	/**
	 * 领取优惠券
	 */
	// @Cacheable(key = "#couponId",unless = "#result eq null")
	@Override
	public CouponReceiveListVo receiveCoupon(Integer couponId, Integer memberId) {
		if (couponId == null || memberId == null) {
			throw new BusinessException("提交数据不能为空");
		}
		Coupon dbCoupon = couponService.getById(couponId);
		Member dbMember = memberService.getMemberById(memberId);
		if (dbCoupon == null || dbMember == null) {
			throw new BusinessException("提交数据不能为空");
		}
		// 检查优惠券领取数量
		if ((dbCoupon.getReceiveQuantity() + 1) > dbCoupon.getQuantity()) {
			throw new BusinessException("哎呀，已经被领完了，下次早点来哦");
		}
		// 有效类型-时间段，检查是否失效
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
			if (DateUtils.parseDate(DateUtils.getFormatDate(dbCoupon.getEndTime()), "yyyy-MM-dd").before(DateUtils.parseDate(DateUtils.getFormatDate(new Date()), "yyyy-MM-dd"))) {
				throw new BusinessException("该优惠券已失效，暂不支持领取");
			}
		}
		// 判断会员等级是否符合标准
		boolean levelFlag = this.checkMemberLevel(dbMember.getMemberLevel(), dbCoupon.getMemberLevels());
		if (!levelFlag) {
			throw new BusinessException("该等级暂不支持领取，请您升级会员等级");
		}
		// 检查该会员领取该优惠券的数量 为空的话是不限制领取
		if (dbCoupon.getLimited() != null) {
			int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(couponId, memberId);
			if ((receivedQuantity + 1) > dbCoupon.getLimited()) {
				throw new BusinessException("不要贪心哦，您已经领取了该优惠券");
			}
		}
		// 领取优惠券
		CouponReceive couponReceive = new CouponReceive();
		couponReceive.setCoupon(dbCoupon);
		couponReceive.setCouponNo(dbCoupon.getCouponNo());
		couponReceive.setCouponType(dbCoupon.getCouponType());
		couponReceive.setParValue(dbCoupon.getParValue());
		couponReceive.setUsed(BigDecimal.ZERO);
		couponReceive.setSurplus(dbCoupon.getParValue());
		couponReceive.setMember(dbMember);
		couponReceive.setMemberPhone(dbMember.getPhone());
		couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_SELF.getCode());
		couponReceive.setReceiveTime(new Date());
		couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
		couponReceive.setFeelWord(dbCoupon.getFeelWord());
		// 有效类型 时间段
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
			couponReceive.setStartTime(new Date());
			couponReceive.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(dbCoupon.getEndTime()) + " 23:59:59"));
			// 有效类型 固定天数
		} else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(dbCoupon.getValidType())) {
			Date startDate = new Date();
			couponReceive.setStartTime(startDate);
			couponReceive.setEndTime(DateUtils.addDays(startDate, dbCoupon.getFixedDay()));
		}
		CouponReceive dbCouponReceive = couponReceiveDao.save(couponReceive);
		// 减去 优惠券领取数量
		// dbCoupon.setReceiveQuantity(dbCoupon.getReceiveQuantity() + 1);
		couponService.updateReceiveQuantity(dbCoupon.getId(), 1);
		return couponReceiveConvert.toListVo(dbCouponReceive);
	}

	/**
	 * 领取优惠券
	 */
	@Override
	public void receiveCouponForGrantReward(Integer couponId, Integer memberId) {
		if (couponId == null || memberId == null) {
			return;
		}
		Coupon dbCoupon = couponService.getById(couponId);
		Member dbMember = memberService.getMemberById(memberId);
		if (dbCoupon == null || dbMember == null) {
			return;
		}
		// 非优惠券不发放
		if (!ActivityEnum.COUPON_TYPE_COUPON.getCode().equals(dbCoupon.getCouponType())) {
			return;
		}
		// 检查优惠券领取数量
		if ((dbCoupon.getReceiveQuantity() + 1) > dbCoupon.getQuantity()) {
			return;
		}
		// 有效类型-时间段，检查是否失效
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
			if (DateUtils.parseDate(DateUtils.getFormatDate(dbCoupon.getEndTime()), "yyyy-MM-dd").before(DateUtils.parseDate(DateUtils.getFormatDate(new Date()), "yyyy-MM-dd"))) {
				return;
			}
		}
		// 判断会员等级是否符合标准
		boolean levelFlag = this.checkMemberLevel(dbMember.getMemberLevel(), dbCoupon.getMemberLevels());
		if (!levelFlag) {
			return;
		}
		// 检查该会员领取该优惠券的数量 为空的话是不限制领取
		if (dbCoupon.getLimited() != null) {
			int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(couponId, memberId);
			if ((receivedQuantity + 1) > dbCoupon.getLimited()) {
				return;
			}
		}
		// 领取优惠券
		CouponReceive couponReceive = new CouponReceive();
		couponReceive.setCoupon(dbCoupon);
		couponReceive.setCouponNo(dbCoupon.getCouponNo());
		couponReceive.setCouponType(dbCoupon.getCouponType());
		couponReceive.setParValue(dbCoupon.getParValue());
		couponReceive.setUsed(BigDecimal.ZERO);
		couponReceive.setSurplus(dbCoupon.getParValue());
		couponReceive.setMember(dbMember);
		couponReceive.setMemberPhone(dbMember.getPhone());
		couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_SELF.getCode());
		couponReceive.setReceiveTime(new Date());
		couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
		couponReceive.setFeelWord(dbCoupon.getFeelWord());
		// 有效类型 时间段
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
			couponReceive.setStartTime(new Date());
			couponReceive.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(dbCoupon.getEndTime()) + " 23:59:59"));
			// 有效类型 固定天数
		} else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(dbCoupon.getValidType())) {
			Date startDate = new Date();
			couponReceive.setStartTime(startDate);
			couponReceive.setEndTime(DateUtils.addDays(startDate, dbCoupon.getFixedDay()));
		}
		couponReceiveDao.save(couponReceive);
		// 减去 优惠券领取数量
		// dbCoupon.setReceiveQuantity(dbCoupon.getReceiveQuantity() + 1);
		couponService.updateReceiveQuantity(dbCoupon.getId(), 1);
	}

	/**
	 * 领取转赠的代金券
	 */
	@Override
	public CouponReceiveListVo receiveVoucher(Integer couponReceiveId, Integer memberId) {
		if (couponReceiveId == null || memberId == null) {
			throw new BusinessException("领取失败");
		}
		CouponReceive dbCouponReceive = this.getById(couponReceiveId);
		if (dbCouponReceive == null) {
			throw new BusinessException("领取失败");
		}
		// 非未使用的 不可领取
		if (!ActivityEnum.COUPON_USE_STATE_NO_USED.getCode().equals(dbCouponReceive.getState())) {
			throw new BusinessException("领取失败");
		}
		Member dbMember = memberService.getMemberById(memberId);
		if (dbMember == null) {
			throw new BusinessException("领取失败");
		}
		if (dbCouponReceive.getMember().getId() == dbMember.getId()) {
			throw new BusinessException("无法领取自己赠送的代金券");
		}
		// 领取代金券
		CouponReceive receiveVoucher = new CouponReceive();
		receiveVoucher.setMember(dbMember);
		receiveVoucher.setMemberPhone(dbMember.getPhone());
		receiveVoucher.setCoupon(dbCouponReceive.getCoupon());
		receiveVoucher.setCouponNo(dbCouponReceive.getCouponNo());
		receiveVoucher.setCouponGrantRecord(dbCouponReceive.getCouponGrantRecord());
		receiveVoucher.setCouponType(dbCouponReceive.getCouponType());
		receiveVoucher.setParValue(dbCouponReceive.getParValue());
		receiveVoucher.setUsed(dbCouponReceive.getUsed());
		receiveVoucher.setSurplus(dbCouponReceive.getSurplus());
		receiveVoucher.setReceiveMode(ActivityEnum.RECEIVE_MODE_PRESENT.getCode());
		receiveVoucher.setReceiveTime(new Date());
		receiveVoucher.setStartTime(new Date());
		receiveVoucher.setEndTime(dbCouponReceive.getEndTime());
		receiveVoucher.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
		receiveVoucher.setFeelWord(dbCouponReceive.getFeelWord());
		CouponReceive dbReceiveVoucher = this.addCouponReceive(receiveVoucher);
		// 修改被赠送代金券的状态和结束时间
		dbCouponReceive.setState(ActivityEnum.COUPON_USE_STATE_INVALID.getCode());
		dbCouponReceive.setEndTime(new Date());
		return couponReceiveConvert.toListVo(dbReceiveVoucher);
	}

	/**
	 * 获取会员的优惠券
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CouponReceive> getMemberCoupons(Integer memberId, SaleOrder... saleOrders) {
		if (memberId == null) {
			return null;
		}
		Integer[] couponType = new Integer[] { ActivityEnum.COUPON_TYPE_COUPON.getCode() };
		// 当前时间
		Date now = new Date();
		List<CouponReceive> couponReceives = couponReceiveDao.findByMember_idAndCoupon_CouponTypeInAndStateAndDeletedAndStartTimeBeforeAndEndTimeAfterOrderByParValueDesc(memberId,
				couponType, ActivityEnum.COUPON_USE_STATE_NO_USED.getCode(), Deleted.DEL_FALSE, now, now);
		// 购买的商品
		Set<Integer> buyCommodities = new HashSet<>();
		for (SaleOrder tmpOrder : saleOrders) {
			if (tmpOrder != null && CollectionUtils.isNotEmpty(tmpOrder.getSaleOrderItems())) {
				for (SaleOrderItem tmpItem : tmpOrder.getSaleOrderItems()) {
					if (tmpItem != null && tmpItem.getCommodity() != null) {
						buyCommodities.add(tmpItem.getCommodity().getId());
					}
				}
			}
		}
		// 遍历购买商品可用的优惠券
		List<CouponReceive> result = new ArrayList<>();
		for (CouponReceive tmpReveive : couponReceives) {
			boolean flag = false;
			if (tmpReveive != null && tmpReveive.getCoupon() != null && CollectionUtils.isNotEmpty(tmpReveive.getCoupon().getCommodities())) {
				for (Commodity tmpCommodity : tmpReveive.getCoupon().getCommodities()) {
					if (tmpCommodity != null && buyCommodities.contains(tmpCommodity.getId())) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				result.add(tmpReveive);
			}
		}
		return result;
	}

	/**
	 * 获取会员的代金券
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CouponReceive> getMemberVouchers(Integer memberId) {
		if (memberId == null) {
			return null;
		}
		Integer[] couponType = new Integer[] { ActivityEnum.COUPON_TYPE_VOUCHER.getCode() };
		// 当前时间
		Date now = new Date();
		List<CouponReceive> couponReceives = couponReceiveDao.findByMember_idAndCoupon_CouponTypeInAndStateAndDeletedAndStartTimeBeforeAndEndTimeAfterOrderByParValueDescEndTimeAsc(
				memberId, couponType, ActivityEnum.COUPON_USE_STATE_NO_USED.getCode(), Deleted.DEL_FALSE, now, now);
		return couponReceives;
	}

	/**
	 * 下单使用优惠券
	 */
	@Override
	public void useCouponsByOrder(SaleOrder... saleOrders) {
		if (ArrayUtils.isNotEmpty(saleOrders)) {
			for (SaleOrder saleOrder : saleOrders) {
				if (saleOrder != null) {
					if (CollectionUtils.isNotEmpty(saleOrder.getCoupons())) {
						for (CouponReceive couponReceive : saleOrder.getCoupons()) {
							if (ActivityEnum.COUPON_USE_STATE_NO_USED.getCode().equals(couponReceive.getState())) {
								couponReceive.setSaleOrder(saleOrder);
								couponReceive.setOrderNo(saleOrder.getOrderNo());
								couponReceive.setState(ActivityEnum.COUPON_USE_STATE_ALREADY_USED.getCode());
								couponReceive.setUseTime(saleOrder.getCreateTime());
								CouponReceive dbCouponReceive = this.updateCouponReceive(couponReceive);
								// 更新使用数量
								couponService.updateUseQuantity(dbCouponReceive.getCoupon().getId(), 1);
							}
						}
					}
					if (CollectionUtils.isNotEmpty(saleOrder.getVouchers())) {
						for (CouponReceive couponReceive : saleOrder.getVouchers()) {
							if (ActivityEnum.COUPON_USE_STATE_NO_USED.getCode().equals(couponReceive.getState())) {
								couponReceive.setSaleOrder(saleOrder);
								couponReceive.setOrderNo(saleOrder.getOrderNo());
								couponReceive.setState(ActivityEnum.COUPON_USE_STATE_ALREADY_USED.getCode());
								couponReceive.setUseTime(saleOrder.getCreateTime());
								CouponReceive dbCouponReceive = this.updateCouponReceive(couponReceive);
								// 更新使用数量
								couponService.updateUseQuantity(dbCouponReceive.getCoupon().getId(), 1);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 手工发放优惠券
	 */
	@Override
	public void grantCoupon(CouponReceiveBo couponReceiveBo) {
		if (CollectionUtils.isEmpty(couponReceiveBo.getMembers()) || CollectionUtils.isEmpty(couponReceiveBo.getCoupons())) {
			throw new BusinessException("提交数据不能为空");
		}
		List<CouponReceive> saveCouponReceives = new ArrayList<>();
		for (MemberBo member : couponReceiveBo.getMembers()) {
			if (member != null && member.getId() > 0) {
				Member dbMember = memberService.getMemberById(member.getId());
				if (dbMember == null) {
					continue;
				}
				for (CouponBo tmpCoupon : couponReceiveBo.getCoupons()) {
					if (tmpCoupon != null && tmpCoupon.getId() > 0) {
						Coupon dbCoupon = couponService.getById(tmpCoupon.getId());
						if (dbCoupon == null) {
							continue;
						}
						// 检查优惠券领取数量
						if ((dbCoupon.getReceiveQuantity() + 1) > dbCoupon.getQuantity()) {
							continue;
						}
						// 有效类型-时间段，检查是否失效
						if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
							if (DateUtils.parseDate(DateUtils.getFormatDate(dbCoupon.getEndTime()), "yyyy-MM-dd")
									.before(DateUtils.parseDate(DateUtils.getFormatDate(new Date()), "yyyy-MM-dd"))) {
								continue;
							}
						}
						// 判断会员等级是否符合标准
						boolean levelFlag = checkMemberLevel(dbMember.getMemberLevel(), dbCoupon.getMemberLevels());
						if (!levelFlag) {
							continue;
						}
						// 检查该会员领取该优惠券的数量 为空的话是不限制领取
						if (dbCoupon.getLimited() != null) {
							int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(dbCoupon.getId(), dbMember.getId());
							if ((receivedQuantity + 1) > dbCoupon.getLimited()) {
								continue;
							}
						}
						// 领取优惠券
						CouponReceive couponReceive = new CouponReceive();
						couponReceive.setCoupon(dbCoupon);
						couponReceive.setCouponNo(dbCoupon.getCouponNo());
						couponReceive.setCouponType(dbCoupon.getCouponType());
						couponReceive.setParValue(dbCoupon.getParValue());
						couponReceive.setUsed(BigDecimal.ZERO);
						couponReceive.setSurplus(dbCoupon.getParValue());
						couponReceive.setMember(dbMember);
						couponReceive.setMemberPhone(dbMember.getPhone());
						couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_MANUAL.getCode());
						couponReceive.setReceiveTime(new Date());
						couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
						// 有效类型 时间段
						if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
							couponReceive.setStartTime(new Date());
							couponReceive.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(dbCoupon.getEndTime()) + " 23:59:59"));
						} // 有效类型 固定天数
						else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(dbCoupon.getValidType())) {
							Date startDate = new Date();
							couponReceive.setStartTime(startDate);
							couponReceive.setEndTime(DateUtils.addDays(startDate, dbCoupon.getFixedDay()));
						}
						saveCouponReceives.add(couponReceive);
						// 减去 优惠券领取数量
						couponService.updateReceiveQuantity(dbCoupon.getId(), 1);
					}
				}
			}
		}
		couponReceiveDao.saveAll(saveCouponReceives);
	}

	/**
	 * 手工发放代金券
	 */
	@Override
	public void grantVoucher(CouponReceiveBo couponReceiveBo) {
		if (CollectionUtils.isEmpty(couponReceiveBo.getMembers()) || CollectionUtils.isEmpty(couponReceiveBo.getCoupons())) {
			throw new BusinessException("提交数据不能为空");
		}
		List<CouponReceive> saveCouponReceives = new ArrayList<>();
		for (MemberBo member : couponReceiveBo.getMembers()) {
			if (member != null && member.getId() > 0) {
				Member dbMember = memberService.getMemberById(member.getId());
				if (dbMember == null) {
					continue;
				}
				for (CouponBo tmpCoupon : couponReceiveBo.getCoupons()) {
					if (tmpCoupon != null && tmpCoupon.getId() > 0) {
						Coupon dbCoupon = couponService.getById(tmpCoupon.getId());
						if (dbCoupon == null) {
							continue;
						}
						// 检查代金券领取数量
						if ((dbCoupon.getReceiveQuantity() + 1) > dbCoupon.getQuantity()) {
							continue;
						}
						// 判断会员等级是否符合标准
						boolean levelFlag = checkMemberLevel(dbMember.getMemberLevel(), dbCoupon.getMemberLevels());
						if (!levelFlag) {
							continue;
						}
						// 检查该会员领取该优惠券的数量 为空的话是不限制领取
						if (dbCoupon.getLimited() != null) {
							int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(dbCoupon.getId(), dbMember.getId());
							if ((receivedQuantity + 1) > dbCoupon.getLimited()) {
								continue;
							}
						}
						// 领取代金券
						CouponReceive couponReceive = new CouponReceive();
						couponReceive.setCoupon(dbCoupon);
						couponReceive.setCouponNo(dbCoupon.getCouponNo());
						couponReceive.setCouponType(dbCoupon.getCouponType());
						couponReceive.setParValue(dbCoupon.getParValue());
						couponReceive.setUsed(BigDecimal.ZERO);
						couponReceive.setSurplus(dbCoupon.getParValue());
						couponReceive.setMember(dbMember);
						couponReceive.setMemberPhone(dbMember.getPhone());
						couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_MANUAL.getCode());
						couponReceive.setReceiveTime(new Date());
						couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
						// 有效类型 时间段
						if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
							couponReceive.setStartTime(new Date());
							couponReceive.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(dbCoupon.getEndTime()) + " 23:59:59"));
						} // 有效类型 固定天数
						else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(dbCoupon.getValidType())) {
							Date startDate = new Date();
							couponReceive.setStartTime(startDate);
							couponReceive.setEndTime(DateUtils.addDays(startDate, dbCoupon.getFixedDay()));
						}
						saveCouponReceives.add(couponReceive);
						// 减去 代金券领取数量
						couponService.updateReceiveQuantity(dbCoupon.getId(), 1);
					}
				}
			}
		}
		// 保存发放的代金券
		couponReceiveDao.saveAll(saveCouponReceives);
	}

	/**
	 * <p>
	 * 撤回发放的代金券
	 * </p>
	 */
	@Override
	public void revokeVoucher(CouponReceiveBo couponReceiveBo) {
		if (CollectionUtils.isEmpty(couponReceiveBo.getMembers()) || CollectionUtils.isEmpty(couponReceiveBo.getCoupons())) {
			throw new BusinessException("提交数据不能为空");
		}
		// 获取已经发放的代金券
		Pagination<CouponReceive> query = new Pagination<>();
		query.setEntityClazz(CouponReceive.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponReceive_.deleted),
			// Deleted.DEL_FALSE)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponReceive_.couponType), ActivityEnum.COUPON_TYPE_VOUCHER.getCode())));
			// 会员
			Path<Integer> memberPath = root.get(CouponReceive_.member).get(Member_.id);
			CriteriaBuilder.In<Integer> memberIn = criteriaBuilder.in(memberPath);
			couponReceiveBo.getMembers().forEach(tmp -> {
				memberIn.value(tmp.getId());
			});
			list.add(criteriaBuilder.and(memberIn));

			// 代金券
			Path<Integer> voucherPath = root.get(CouponReceive_.coupon).get(Coupon_.id);
			CriteriaBuilder.In<Integer> voucherIn = criteriaBuilder.in(voucherPath);
			couponReceiveBo.getCoupons().forEach(tmp -> {
				voucherIn.value(tmp.getId());
			});
			list.add(criteriaBuilder.and(voucherIn));
		}));
		List<CouponReceive> grantList = couponReceiveDao.findAll(query);
		if (CollectionUtils.isNotEmpty(grantList)) {
			grantList.forEach(tmp -> {
				// 收回未使用的代金券
				if (ActivityEnum.COUPON_USE_STATE_NO_USED.getCode().equals(tmp.getState())) {
					tmp.setState(ActivityEnum.COUPON_USE_STATE_REGAIN.getCode());
				}
			});
		}
	}

	/**
	 * 手工发放储值券
	 */
	@Override
	public void grantStored(CouponReceiveBo couponReceiveBo) {
		if (CollectionUtils.isEmpty(couponReceiveBo.getMembers()) || CollectionUtils.isEmpty(couponReceiveBo.getCoupons())) {
			throw new BusinessException("提交数据不能为空");
		}
		List<CouponReceive> saveCouponReceives = new ArrayList<>();
		for (MemberBo member : couponReceiveBo.getMembers()) {
			if (member != null && member.getId() > 0) {
				Member dbMember = memberService.getMemberById(member.getId());
				if (dbMember == null) {
					continue;
				}
				for (CouponBo tmpCoupon : couponReceiveBo.getCoupons()) {
					if (tmpCoupon != null && tmpCoupon.getId() > 0) {
						Coupon dbCoupon = couponService.getById(tmpCoupon.getId());
						if (dbCoupon == null) {
							continue;
						}
						// 检查储值券领取数量
						if ((dbCoupon.getReceiveQuantity() + 1) > dbCoupon.getQuantity()) {
							continue;
						}
						// 判断会员等级是否符合标准
						boolean levelFlag = checkMemberLevel(dbMember.getMemberLevel(), dbCoupon.getMemberLevels());
						if (!levelFlag) {
							continue;
						}
						// 检查该会员领取该优惠券的数量 为空的话是不限制领取
						if (dbCoupon.getLimited() != null) {
							int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(dbCoupon.getId(), dbMember.getId());
							if ((receivedQuantity + 1) > dbCoupon.getLimited()) {
								continue;
							}
						}
						// 领取优惠券
						CouponReceive couponReceive = new CouponReceive();
						couponReceive.setCoupon(dbCoupon);
						couponReceive.setCouponNo(dbCoupon.getCouponNo());
						couponReceive.setCouponType(dbCoupon.getCouponType());
						couponReceive.setParValue(dbCoupon.getParValue());
						couponReceive.setUsed(BigDecimal.ZERO);
						couponReceive.setSurplus(dbCoupon.getParValue());
						couponReceive.setMember(dbMember);
						couponReceive.setMemberPhone(dbMember.getPhone());
						couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_MANUAL.getCode());
						couponReceive.setReceiveTime(new Date());
						couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
						// 有效类型 时间段
						if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
							couponReceive.setStartTime(new Date());
							couponReceive.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(dbCoupon.getEndTime()) + " 23:59:59"));
						} // 有效类型 固定天数
						else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(dbCoupon.getValidType())) {
							Date startDate = new Date();
							couponReceive.setStartTime(startDate);
							couponReceive.setEndTime(DateUtils.addDays(startDate, dbCoupon.getFixedDay()));
						}
						saveCouponReceives.add(couponReceive);
						// 减去 储值券领取数量
						couponService.updateReceiveQuantity(dbCoupon.getId(), 1);
					}
				}
			}
		}
		List<CouponReceive> dbCouponReceives = couponReceiveDao.saveAll(saveCouponReceives);
		// 将储值券 发放到会员的余额中
		dbCouponReceives.forEach(tmp -> {
			accountService.rechargeBalance(tmp.getMember(), tmp.getParValue());
			tmp.setState(ActivityEnum.COUPON_USE_STATE_RECHARGE.getCode());
			tmp.setUseTime(new Date());
		});
	}

	/**
	 * <p>
	 * 撤回发放的储值券
	 * </p>
	 */
	@Override
	public void revokeStored(CouponReceiveBo couponReceiveBo) {
		if (CollectionUtils.isEmpty(couponReceiveBo.getMembers()) || CollectionUtils.isEmpty(couponReceiveBo.getCoupons())) {
			throw new BusinessException("提交数据不能为空");
		}
		// 获取已经发放的储值券
		Pagination<CouponReceive> query = new Pagination<>();
		query.setEntityClazz(CouponReceive.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponReceive_.deleted),
			// Deleted.DEL_FALSE)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponReceive_.couponType), ActivityEnum.COUPON_TYPE_STORED.getCode())));
			// 会员
			Path<Integer> memberPath = root.get(CouponReceive_.member).get(Member_.id);
			CriteriaBuilder.In<Integer> memberIn = criteriaBuilder.in(memberPath);
			couponReceiveBo.getMembers().forEach(tmp -> {
				memberIn.value(tmp.getId());
			});
			list.add(criteriaBuilder.and(memberIn));

			// 储值券
			Path<Integer> storedPath = root.get(CouponReceive_.coupon).get(Coupon_.id);
			CriteriaBuilder.In<Integer> storedIn = criteriaBuilder.in(storedPath);
			couponReceiveBo.getCoupons().forEach(tmp -> {
				storedIn.value(tmp.getId());
			});
			list.add(criteriaBuilder.and(storedIn));

		}));
		List<CouponReceive> grantList = couponReceiveDao.findAll(query);
		if (CollectionUtils.isNotEmpty(grantList)) {
			grantList.forEach(tmp -> {
				// 收回储值券 并减去会员余额
				tmp.setState(ActivityEnum.COUPON_USE_STATE_REGAIN.getCode());
				accountService.subtractBalance(tmp.getMember(), tmp.getParValue());
			});
		}
	}

	/**
	 * 分步发放代金券
	 */
	@Override
	public List<Object> grantVoucherByStep(Member member, SaleOrder saleOrder, ActivityEnum grantStep) {
		List<Object> grantAmounts = new ArrayList<>();
		if (member == null || saleOrder == null || CollectionUtils.isEmpty(saleOrder.getSaleOrderItems()) || grantStep == null) {
			LOG.error("发放代金券，参数为空");
			return grantAmounts;
		}
		for (SaleOrderItem tmpItem : saleOrder.getSaleOrderItems()) {
			if (tmpItem != null && tmpItem.getCommodity() != null && tmpItem.getCommodity().getCouponGrantConfig() != null
					&& tmpItem.getCommodity().getCouponGrantConfig().getCoupon() != null) {
				// 检查会员等级是否符合要求
				boolean levelFlag = this.checkMemberLevel(member.getMemberLevel(), tmpItem.getCommodity().getCouponGrantConfig().getCoupon().getMemberLevels());
				if (levelFlag) {
					CouponGrantConfig tmpGrantConfig = tmpItem.getCommodity().getCouponGrantConfig();
					Coupon tmpCoupon = tmpGrantConfig.getCoupon();
					// 代金券已经发放完
					if (tmpCoupon.getQuantity() - tmpCoupon.getReceiveQuantity() <= 0) {
						LOG.error("会员：{}，订单：{}，发放步骤：{}，发放代金券时，代金券已经发放完", member.getId(), saleOrder.getId(), grantStep.getValue());
						continue;
					}
					// 有效类型 时间段-检查是否失效
					if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(tmpCoupon.getValidType())) {
						if (DateUtils.parseTimestamp(DateUtils.getFormatDate(tmpCoupon.getEndTime()) + " 23:59:59").before(new Date())) {
							LOG.error("会员：{}，订单：{}，发放步骤：{}，发放代金券时，代金券失效", member.getId(), saleOrder.getId(), grantStep.getValue());
							continue;
						}
					}
					// 检查该会员领取该优惠券的数量 为空的话是不限制领取
					if (tmpCoupon.getLimited() != null) {
						// 统计会员已经领取的数量
						int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(tmpCoupon.getId(), member.getId());
						if ((receivedQuantity + 1) > tmpCoupon.getLimited()) {
							LOG.error("会员：{}，订单：{}，发放步骤：{}，发放代金券时，超过领取限制", member.getId(), saleOrder.getId(), grantStep.getValue());
							continue;
						}
					}

					// 购买商品数量
					for (int i = 0; i < tmpItem.getQuantity(); i++) {
						JSONObject jsonObject = new JSONObject();
						// 发放策略-一次性发放
						if (ActivityEnum.GRANT_STRATEGY_ONCE.getCode().equals(tmpGrantConfig.getGrantStrategy())) {
							if (grantStep.getCode().equals(tmpGrantConfig.getGrantNode())) {
								// 检查该步骤是否发放过该优惠券
								boolean grantFlag = couponGrantRecordService.checkGrantForOnce(grantStep, member, saleOrder, tmpGrantConfig, tmpItem.getQuantity());
								if (!grantFlag) {
									// 发放面额
									BigDecimal parValue = tmpGrantConfig.getCoupon().getParValue();
									// 发放优惠券并保存
									CouponGrantRecord dbCouponGrantRecord = couponGrantRecordService.addByGrantStepForOnce(grantStep, member, saleOrder, tmpGrantConfig, parValue);
									// 将发放的代金券的同步到优惠券领取表
									this.addByGrantVoucherForOnce(tmpCoupon, member, tmpGrantConfig, grantStep, dbCouponGrantRecord, parValue);
									jsonObject.put("grant", parValue);
									jsonObject.put("surplus", BigDecimal.ZERO);
									jsonObject.put("remind", grantStep.getValue() + "后获得" + parValue + "元代金券");
									grantAmounts.add(jsonObject);
								}
							}
						} // 发放策略-分批发放
						else if (ActivityEnum.GRANT_STRATEGY_STEP.getCode().equals(tmpGrantConfig.getGrantStrategy())) {
							// 根据发放步骤 发放优惠券
							for (CouponGrantStep tmpStep : tmpItem.getCommodity().getCouponGrantConfig().getCouponGrantSteps()) {
								if (grantStep.getCode().equals(tmpStep.getGrantNode())) {
									// 检查该步骤是否发放过该优惠券
									boolean grantFlag = couponGrantRecordService.checkGrantForStep(grantStep, member, saleOrder, tmpGrantConfig, tmpItem.getQuantity());
									if (!grantFlag) {
										// 是否第一次发放
										boolean firstGrant = couponGrantRecordService.checkFirstGrant(member, saleOrder, tmpGrantConfig, tmpItem.getQuantity());
										// 发放面额
										BigDecimal parValue = tmpStep.getGrantRate().multiply(tmpGrantConfig.getCoupon().getParValue()).divide(BigDecimal.valueOf(100)).setScale(2,
												BigDecimal.ROUND_UP);
										if (parValue.compareTo(BigDecimal.ZERO) > 0) {
											// 发放优惠券并保存
											CouponGrantRecord dbCouponGrantRecord = couponGrantRecordService.addByGrantStepForStep(grantStep, member, saleOrder, tmpGrantConfig,
													parValue);
											// 将发放的代金券同步到优惠券领取表
											this.addByGrantVoucherForStep(tmpCoupon, member, tmpGrantConfig, grantStep, dbCouponGrantRecord, parValue, firstGrant);
											jsonObject.put("grant", parValue);
											jsonObject.put("remind", grantStep.getValue() + "后获得" + parValue + "元代金券 ");
											jsonObject = getSurplusVoucher(jsonObject, grantStep, tmpItem.getCommodity().getCouponGrantConfig().getCouponGrantSteps());
											grantAmounts.add(jsonObject);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return grantAmounts;
	}

	/**
	 * 根据释放节点 解冻代金券
	 */
	@Override
	public void thawVoucherByStep(Member member, SaleOrder saleOrder, ActivityEnum grantStep) {
		if (member == null || saleOrder == null || CollectionUtils.isEmpty(saleOrder.getSaleOrderItems()) || grantStep == null) {
			LOG.error("解冻代金券，请求参数（member，saleOrder，grantStep）为空");
			return;
		}
		// 查询该会员该订单 代金券发放记录
		List<CouponGrantRecord> dbCouponGrantRecords = couponGrantRecordService.getGrantRecordsByMemberAndOrder(member, saleOrder);
		if (CollectionUtils.isNotEmpty(dbCouponGrantRecords)) {
			for (CouponGrantRecord grantRecord : dbCouponGrantRecords) {
				// 解冻节点=当前节点
				if (grantRecord != null && grantRecord.getCouponGrantConfig() != null && grantRecord.getCouponGrantConfig().getThawNode() <= grantStep.getCode()) {
					CouponReceive dbCouponReceive = couponReceiveDao.findByMember_idAndCouponGrantRecord_idAndDeleted(member.getId(), grantRecord.getId(), Deleted.DEL_FALSE);
					if (dbCouponReceive != null && ActivityEnum.COUPON_USE_STATE_NO_USED.getCode().equals(dbCouponReceive.getState())
							&& dbCouponReceive.getStartTime().after(new Date())) {
						// 有效类型 时间段
						if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCouponReceive.getCoupon().getValidType())) {
							dbCouponReceive.setStartTime(new Date());
							// 有效类型 固定天数
						} else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(dbCouponReceive.getCoupon().getValidType())) {
							Date startDate = new Date();
							// 如果发放节点 <= 解冻节点
							dbCouponReceive.setStartTime(startDate);
							dbCouponReceive.setEndTime(DateUtils.addDays(startDate, dbCouponReceive.getCoupon().getFixedDay()));
						}
					}
				}
			}
		}
	}

	/**
	 * 下单时 查询 需要发放的代金券
	 */
	@Override
	public List<Object> getGrantVoucherByPaid(String orderIds) {
		List<Object> grantAmounts = new ArrayList<>();
		if (StringUtils.isBlank(orderIds)) {
			LOG.error("获取发放的代金券，orderIds为空");
			return grantAmounts;
		}
		List<SaleOrder> dbSaleOrders = saleOrderService.getPayOrderByIds(orderIds);
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.error("获取发放的代金券，根据orderIds查询数据为空");
			return grantAmounts;
		}
		// 获取需要发放的代金券
		for (SaleOrder tmpOrder : dbSaleOrders) {
			if (tmpOrder != null && CollectionUtils.isNotEmpty(tmpOrder.getSaleOrderItems())) {
				for (SaleOrderItem tmpItem : tmpOrder.getSaleOrderItems()) {
					if (tmpItem != null && tmpItem.getCommodity() != null && tmpItem.getCommodity().getCouponGrantConfig() != null
							&& tmpItem.getCommodity().getCouponGrantConfig().getCoupon() != null) {
						// 检查会员等级是否符合要求
						boolean levelFlag = this.checkMemberLevel(tmpOrder.getMember().getMemberLevel(),
								tmpItem.getCommodity().getCouponGrantConfig().getCoupon().getMemberLevels());
						if (levelFlag) {
							CouponGrantConfig tmpGrantConfig = tmpItem.getCommodity().getCouponGrantConfig();
							Coupon tmpCoupon = tmpGrantConfig.getCoupon();
							// 代金券已经发放完
							if (tmpCoupon.getQuantity() - tmpCoupon.getReceiveQuantity() <= 0) {
								LOG.error("会员：{}，订单：{}，发放步骤：{}，发放代金券时，代金券已经发放完", tmpOrder.getMember().getId(), tmpOrder.getId(), ActivityEnum.GRANT_NODE_ORDER.getValue());
								continue;
							}
							// 有效类型 时间段-检查是否失效
							if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(tmpCoupon.getValidType())) {
								if (DateUtils.parseTimestamp(DateUtils.getFormatDate(tmpCoupon.getEndTime()) + " 23:59:59").before(new Date())) {
									LOG.error("会员：{}，订单：{}，发放步骤：{}，发放代金券时，代金券失效", tmpOrder.getMember().getId(), tmpOrder.getId(), ActivityEnum.GRANT_NODE_ORDER.getValue());
									continue;
								}
							}
							// 检查该会员领取该优惠券的数量 为空的话是不限制领取
							if (tmpCoupon.getLimited() != null) {
								// 统计会员已经领取的数量
								int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(tmpCoupon.getId(), tmpOrder.getMember().getId());
								if ((receivedQuantity + 1) > tmpCoupon.getLimited()) {
									LOG.error("会员：{}，订单：{}，发放步骤：{}，发放代金券时，超过领取限制", tmpOrder.getMember().getId(), tmpOrder.getId(), ActivityEnum.GRANT_NODE_ORDER.getValue());
									continue;
								}
							}
							// 购买商品数量
							for (int i = 0; i < tmpItem.getQuantity(); i++) {
								JSONObject jsonObject = new JSONObject();
								// 发放策略-一次性发放
								if (ActivityEnum.GRANT_STRATEGY_ONCE.getCode().equals(tmpGrantConfig.getGrantStrategy())) {
									if (ActivityEnum.GRANT_NODE_ORDER.getCode().equals(tmpGrantConfig.getGrantNode())) {
										// 发放面额
										BigDecimal parValue = tmpGrantConfig.getCoupon().getParValue();
										jsonObject.put("grant", parValue);
										jsonObject.put("surplus", BigDecimal.ZERO);
										jsonObject.put("remind", ActivityEnum.GRANT_NODE_ORDER.getValue() + "后获得" + parValue + "元代金券");
										grantAmounts.add(jsonObject);
									}
								} // 发放策略-分批发放
								else if (ActivityEnum.GRANT_STRATEGY_STEP.getCode().equals(tmpGrantConfig.getGrantStrategy())) {
									// 根据发放步骤 发放优惠券
									for (CouponGrantStep tmpStep : tmpItem.getCommodity().getCouponGrantConfig().getCouponGrantSteps()) {
										if (ActivityEnum.GRANT_NODE_ORDER.getCode().equals(tmpStep.getGrantNode())) {
											// 发放面额
											BigDecimal parValue = tmpStep.getGrantRate().multiply(tmpGrantConfig.getCoupon().getParValue()).divide(BigDecimal.valueOf(100))
													.setScale(2, BigDecimal.ROUND_UP);
											if (parValue.compareTo(BigDecimal.ZERO) > 0) {
												jsonObject.put("grant", parValue);
												jsonObject.put("remind", ActivityEnum.GRANT_NODE_ORDER.getValue() + "后获得" + parValue + "元代金券 ");
												jsonObject = getSurplusVoucher(jsonObject, ActivityEnum.GRANT_NODE_ORDER,
														tmpItem.getCommodity().getCouponGrantConfig().getCouponGrantSteps());
												grantAmounts.add(jsonObject);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return grantAmounts;
	}

	/**
	 * 获取发放的剩余金额 并生成提示语
	 * 
	 * @param currentNode
	 * @param grantSteps
	 * @return
	 */
	@Transactional
	public JSONObject getSurplusVoucher(JSONObject jsonObject, ActivityEnum currentNode, List<CouponGrantStep> grantSteps) {
		if (currentNode == null || CollectionUtils.isEmpty(grantSteps)) {
			return jsonObject;
		}
		BigDecimal surplus = BigDecimal.ZERO;
		for (CouponGrantStep tmpStep : grantSteps) {
			if (tmpStep != null) {
				if (ActivityEnum.GRANT_NODE_ORDER.getCode().equals(currentNode.getCode())) {
					if (ActivityEnum.GRANT_NODE_RECEIPT.getCode().equals(tmpStep.getGrantNode())) {
						// 发放面额
						BigDecimal parValue = tmpStep.getGrantRate().multiply(tmpStep.getCouponGrantConfig().getCoupon().getParValue()).divide(BigDecimal.valueOf(100)).setScale(2,
								BigDecimal.ROUND_UP);
						if (parValue.compareTo(BigDecimal.ZERO) > 0) {
							surplus = surplus.add(parValue);
							jsonObject.put("remind",
									Optional.ofNullable(jsonObject.getString("remind")).orElse("") + ActivityEnum.GRANT_NODE_RECEIPT.getValue() + "后获得" + parValue + "元代金券 ");
						}
					} else if (ActivityEnum.GRANT_NODE_COMMENT.getCode().equals(tmpStep.getGrantNode())) {
						// 发放面额
						BigDecimal parValue = tmpStep.getGrantRate().multiply(tmpStep.getCouponGrantConfig().getCoupon().getParValue()).divide(BigDecimal.valueOf(100)).setScale(2,
								BigDecimal.ROUND_UP);
						if (parValue.compareTo(BigDecimal.ZERO) > 0) {
							surplus = surplus.add(parValue);
							jsonObject.put("remind",
									Optional.ofNullable(jsonObject.getString("remind")).orElse("") + ActivityEnum.GRANT_NODE_COMMENT.getValue() + "后获得" + parValue + "元代金券 ");
						}
					} else if (ActivityEnum.GRANT_NODE_OVER_15_DAY.getCode().equals(tmpStep.getGrantNode())) {
						// 发放面额
						BigDecimal parValue = tmpStep.getGrantRate().multiply(tmpStep.getCouponGrantConfig().getCoupon().getParValue()).divide(BigDecimal.valueOf(100)).setScale(2,
								BigDecimal.ROUND_UP);
						if (parValue.compareTo(BigDecimal.ZERO) > 0) {
							surplus = surplus.add(parValue);
							jsonObject.put("remind",
									Optional.ofNullable(jsonObject.getString("remind")).orElse("") + ActivityEnum.GRANT_NODE_OVER_15_DAY.getValue() + "后获得" + parValue + "元代金券 ");
						}
					}
				} else if (ActivityEnum.GRANT_NODE_RECEIPT.getCode().equals(currentNode.getCode())) {
					if (ActivityEnum.GRANT_NODE_COMMENT.getCode().equals(tmpStep.getGrantNode())) {
						// 发放面额
						BigDecimal parValue = tmpStep.getGrantRate().multiply(tmpStep.getCouponGrantConfig().getCoupon().getParValue()).divide(BigDecimal.valueOf(100)).setScale(2,
								BigDecimal.ROUND_UP);
						if (parValue.compareTo(BigDecimal.ZERO) > 0) {
							surplus = surplus.add(parValue);
							jsonObject.put("remind",
									Optional.ofNullable(jsonObject.getString("remind")).orElse("") + ActivityEnum.GRANT_NODE_COMMENT.getValue() + "后获得" + parValue + "元代金券 ");
						}
					} else if (ActivityEnum.GRANT_NODE_OVER_15_DAY.getCode().equals(tmpStep.getGrantNode())) {
						// 发放面额
						BigDecimal parValue = tmpStep.getGrantRate().multiply(tmpStep.getCouponGrantConfig().getCoupon().getParValue()).divide(BigDecimal.valueOf(100)).setScale(2,
								BigDecimal.ROUND_UP);
						if (parValue.compareTo(BigDecimal.ZERO) > 0) {
							surplus = surplus.add(parValue);
							jsonObject.put("remind",
									Optional.ofNullable(jsonObject.getString("remind")).orElse("") + ActivityEnum.GRANT_NODE_OVER_15_DAY.getValue() + "后获得" + parValue + "元代金券 ");
						}
					}
				} else if (ActivityEnum.GRANT_NODE_COMMENT.getCode().equals(currentNode.getCode())) {
					if (ActivityEnum.GRANT_NODE_OVER_15_DAY.getCode().equals(tmpStep.getGrantNode())) {
						// 发放面额
						BigDecimal parValue = tmpStep.getGrantRate().multiply(tmpStep.getCouponGrantConfig().getCoupon().getParValue()).divide(BigDecimal.valueOf(100)).setScale(2,
								BigDecimal.ROUND_UP);
						if (parValue.compareTo(BigDecimal.ZERO) > 0) {
							surplus = surplus.add(parValue);
							jsonObject.put("remind",
									Optional.ofNullable(jsonObject.getString("remind")).orElse("") + ActivityEnum.GRANT_NODE_OVER_15_DAY.getValue() + "后获得" + parValue + "元代金券 ");
						}
					}
				} else if (ActivityEnum.GRANT_NODE_OVER_15_DAY.getCode().equals(currentNode.getCode())) {
					jsonObject.put("remind", Optional.ofNullable(jsonObject.getString("remind")).orElse(""));
				}
			}
		}
		jsonObject.put("surplus", surplus);
		// if (StringUtils.isNotBlank(jsonObject.getString("remind"))) {
		// jsonObject.put("remind", jsonObject.getString("remind"));
		// }
		return jsonObject;
	}

	/**
	 * 退款时 收回已经发放的代金券
	 */
	@Override
	public void returnVoucherByRefund(Member member, SaleOrder saleOrder) {
		if (member != null && saleOrder != null) {
			// 查询已经发放的记录
			List<CouponGrantRecord> dbGrantRecords = couponGrantRecordService.getGrantRecordsByMemberAndOrder(member, saleOrder);
			if (CollectionUtils.isNotEmpty(dbGrantRecords)) {
				List<Integer> grantRecordIds = dbGrantRecords.stream().map(e -> e.getId()).collect(Collectors.toList());
				List<CouponReceive> dbCouponReceives = couponReceiveDao.findByCouponGrantRecord_idInAndDeleted(grantRecordIds, Deleted.DEL_FALSE);
				if (CollectionUtils.isNotEmpty(dbCouponReceives)) {
					dbCouponReceives.forEach(tmpCouponReceive -> {
						if (tmpCouponReceive != null && ActivityEnum.COUPON_USE_STATE_NO_USED.getCode().equals(tmpCouponReceive.getState())) {
							tmpCouponReceive.setState(ActivityEnum.COUPON_USE_STATE_REGAIN.getCode());
						}
					});
				}
			}
		}
	}

	/**
	 * 退款时 释放已经使用的代金券
	 */
	@Override
	@Transactional
	public void returnUseVoucherByRefund(Member member, SaleOrder saleOrder) {
		if (member != null && saleOrder != null) {
			// 查询已经使用的代金券
			List<CouponReceive> dbCouponReceives = couponReceiveDao.findByMember_idAndSaleOrder_idAndStateAndDeleted(member.getId(), saleOrder.getId(),
					ActivityEnum.COUPON_USE_STATE_ALREADY_USED.getCode(), Deleted.DEL_FALSE);
			dbCouponReceives.forEach(tmpCouponReceive -> {
				if (tmpCouponReceive != null && ActivityEnum.COUPON_USE_STATE_ALREADY_USED.getCode().equals(tmpCouponReceive.getState())) {
					tmpCouponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
					tmpCouponReceive.setSaleOrder(null);
					tmpCouponReceive.setOrderNo(null);
					tmpCouponReceive.setUseTime(null);
					// 更新使用数量
					couponService.updateUseQuantity(tmpCouponReceive.getCoupon().getId(), -1);
				}
			});
		}
	}

	/**
	 * 一次性发放代金券
	 * 
	 * @param coupon
	 * @param member
	 * @param grantConfig
	 * @param grantStep
	 * @param couponGrantRecord
	 * @param parValue
	 * @return
	 */
	@Transactional
	public CouponReceive addByGrantVoucherForOnce(Coupon coupon, Member member, CouponGrantConfig grantConfig, ActivityEnum grantStep, CouponGrantRecord couponGrantRecord,
			BigDecimal parValue) {
		if (coupon == null || member == null || couponGrantRecord == null) {
			LOG.error("代金券领取时，参数为空");
			return null;
		}
		// 有效类型-时间段，检查是否失效
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(coupon.getValidType())) {
			if (DateUtils.parseDate(DateUtils.getFormatDate(coupon.getEndTime()), "yyyy-MM-dd").before(DateUtils.parseDate(DateUtils.getFormatDate(new Date()), "yyyy-MM-dd"))) {
				LOG.error("会员：{}，代金券：{}，领取代金券时，代金券失效", member.getId(), coupon.getId());
				return null;
			}
		}
		// 检查优惠券领取数量
		if ((coupon.getReceiveQuantity() + 1) > coupon.getQuantity()) {
			LOG.error("会员：{}，代金券：{}，领取代金券时，代金券数量不足", member.getId(), coupon.getId());
			return null;
		}
		// 检查该会员领取该优惠券的数量 为空的话是不限制领取
		if (coupon.getLimited() != null) {
			// 统计会员已经领取的数量
			int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(coupon.getId(), member.getId());
			if ((receivedQuantity + 1) > coupon.getLimited()) {
				LOG.error("会员：{}，代金券：{}，领取代金券时，超过领取限制", member.getId(), coupon.getId());
				return null;
			}
		}
		// 领取优惠券
		CouponReceive couponReceive = new CouponReceive();
		couponReceive.setCoupon(coupon);
		couponReceive.setCouponNo(coupon.getCouponNo());
		couponReceive.setCouponType(coupon.getCouponType());
		couponReceive.setParValue(parValue);
		couponReceive.setUsed(BigDecimal.ZERO);
		couponReceive.setSurplus(parValue);
		couponReceive.setMember(member);
		couponReceive.setMemberPhone(member.getPhone());
		couponReceive.setCouponGrantRecord(couponGrantRecord);
		couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_REWARD.getCode());
		couponReceive.setReceiveTime(new Date());
		couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
		// 冻结天数
		int freezeDays = Optional.ofNullable(grantConfig.getFreezeDays()).orElse(0);
		// 有效类型 时间段
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(coupon.getValidType())) {
			// 如果发放节点 >= 解冻节点
			if (grantStep.getCode() >= grantConfig.getThawNode()) {
				couponReceive.setStartTime(new Date());
			} else {
				couponReceive.setStartTime(DateUtils.addDays(new Date(), freezeDays));
			}
			couponReceive.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(coupon.getEndTime()) + " 23:59:59"));
			// 有效类型 固定天数
		} else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(coupon.getValidType())) {
			Date startDate = new Date();
			// 如果发放节点 >= 解冻节点
			if (grantStep.getCode() >= grantConfig.getThawNode()) {
				couponReceive.setStartTime(startDate);
				couponReceive.setEndTime(DateUtils.addDays(startDate, coupon.getFixedDay()));
			} else {
				couponReceive.setStartTime(DateUtils.addDays(startDate, freezeDays));
				couponReceive.setEndTime(DateUtils.addDays(startDate, coupon.getFixedDay() + freezeDays));
			}
		}
		// 保存数据
		CouponReceive dbCouponReceive = couponReceiveDao.save(couponReceive);
		// 减去 优惠券领取数量
		// coupon.setReceiveQuantity(coupon.getReceiveQuantity() + 1);
		couponService.updateReceiveQuantity(coupon.getId(), 1);
		return dbCouponReceive;
	}

	/**
	 * 分步发放代金券
	 * 
	 * @param coupon
	 * @param member
	 * @param grantConfig
	 * @param grantStep
	 * @param couponGrantRecord
	 * @param parValue
	 * @param firstGrant
	 * @return
	 */
	@Transactional
	public CouponReceive addByGrantVoucherForStep(Coupon coupon, Member member, CouponGrantConfig grantConfig, ActivityEnum grantStep, CouponGrantRecord couponGrantRecord,
			BigDecimal parValue, boolean firstGrant) {
		if (coupon != null && member != null && couponGrantRecord != null) {
			// 有效类型-时间段，检查是否失效
			if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(coupon.getValidType())) {
				if (DateUtils.parseDate(DateUtils.getFormatDate(coupon.getEndTime()), "yyyy-MM-dd")
						.before(DateUtils.parseDate(DateUtils.getFormatDate(new Date()), "yyyy-MM-dd"))) {
					LOG.error("会员：{}，代金券：{}，领取代金券时，代金券失效", member.getId(), coupon.getId());
					return null;
				}
			}
			if (firstGrant) {
				// 检查优惠券领取数量
				if ((coupon.getReceiveQuantity() + 1) > coupon.getQuantity()) {
					LOG.error("会员：{}，代金券：{}，领取代金券时，代金券数量不足", member.getId(), coupon.getId());
					return null;
				}
				// 检查该会员领取该优惠券的数量 为空的话是不限制领取
				if (coupon.getLimited() != null) {
					// 统计会员已经领取的数量
					int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(coupon.getId(), member.getId());
					if ((receivedQuantity + 1) > coupon.getLimited()) {
						LOG.error("会员：{}，代金券：{}，领取代金券时，超过领取限制", member.getId(), coupon.getId());
						return null;
					}
				}
			}
			// 领取优惠券
			CouponReceive couponReceive = new CouponReceive();
			couponReceive.setCoupon(coupon);
			couponReceive.setCouponNo(coupon.getCouponNo());
			couponReceive.setCouponType(coupon.getCouponType());
			couponReceive.setParValue(parValue);
			couponReceive.setUsed(BigDecimal.ZERO);
			couponReceive.setSurplus(parValue);
			couponReceive.setMember(member);
			couponReceive.setMemberPhone(member.getPhone());
			couponReceive.setCouponGrantRecord(couponGrantRecord);
			couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_REWARD.getCode());
			couponReceive.setReceiveTime(new Date());
			couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
			// 冻结天数
			int freezeDays = Optional.ofNullable(grantConfig.getFreezeDays()).orElse(0);
			// 有效类型 时间段
			if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(coupon.getValidType())) {
				// 如果发放节点 >= 解冻节点
				if (grantStep.getCode() >= grantConfig.getThawNode()) {
					couponReceive.setStartTime(new Date());
				} else {
					couponReceive.setStartTime(DateUtils.addDays(new Date(), freezeDays));
				}
				couponReceive.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(coupon.getEndTime()) + " 23:59:59"));
				// 有效类型 固定天数
			} else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(coupon.getValidType())) {
				Date startDate = new Date();
				// 如果发放节点 >= 解冻节点
				if (grantStep.getCode() >= grantConfig.getThawNode()) {
					couponReceive.setStartTime(startDate);
					couponReceive.setEndTime(DateUtils.addDays(startDate, coupon.getFixedDay()));
				} else {
					couponReceive.setStartTime(DateUtils.addDays(startDate, freezeDays));
					couponReceive.setEndTime(DateUtils.addDays(startDate, coupon.getFixedDay() + freezeDays));
				}
			}
			// 保存数据
			CouponReceive dbCouponReceive = couponReceiveDao.save(couponReceive);
			if (firstGrant) {
				// 减去 优惠券领取数量
				// coupon.setReceiveQuantity(coupon.getReceiveQuantity() + 1);
				couponService.updateReceiveQuantity(coupon.getId(), 1);
			}
			return dbCouponReceive;
		}
		return null;
	}

	/**
	 * 核验 会员等级是否符合
	 *
	 * @param memberLevel
	 * @param memberLevels
	 * @return
	 */
	private boolean checkMemberLevel(MemberLevel memberLevel, Collection<MemberLevel> memberLevels) {
		if (memberLevel == null || CollectionUtils.isEmpty(memberLevels)) {
			return false;
		}
		for (MemberLevel tmpLevel : memberLevels) {
			if (tmpLevel != null && tmpLevel.getId() == memberLevel.getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 该优惠券所有情况
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByCouponId(int couponId) {
		return couponReceiveDao.countByCouponId(couponId);
	}

	/**
	 * 统计会员优惠券领取数量
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByMemberId(int memberId) {
		Integer[] states = new Integer[] { ActivityEnum.COUPON_USE_STATE_NO_USED.getCode() };
		return couponReceiveDao.countByMember_idAndStateInAndDeleted(memberId, states, Deleted.DEL_FALSE);
	}

	@Override
	public void autoCancelCouponByTask() {
		List<CouponReceive> couponReceives = couponReceiveDao.findByStateAndEndTimeLessThanAndDeleted(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode(), new Date(),
				Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(couponReceives)) {
			LOG.info("没有需要执行失效的优惠券");
			return;
		}
		LOG.info("需要执行失效的优惠券有{}个", couponReceives.size());
		couponReceives.forEach(tmpReceive -> {
			if (tmpReceive.getEndTime().before(new Date())) {
				tmpReceive.setState(ActivityEnum.COUPON_USE_STATE_INVALID.getCode());
			}
		});
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkReceivePrizeForCoupon(Integer memberId, Integer couponId){
		int count = couponReceiveDao.countByCoupon_idAndMember_id(couponId, memberId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void receiveInvitePrize(Integer memberId, Integer couponId) {
		if (couponId == null || memberId == null) {
			throw new BusinessException("提交数据不能为空");
		}
		// 检查是否领取过该奖品
	/**	boolean flag = checkReceivePrizeForCoupon(memberId,couponId);
		if (flag) {
			throw new BusinessException("您已领取过该奖品，请勿重复领取");
		}   */
		Coupon dbCoupon = couponService.getById(couponId);
		Member dbMember = memberService.getMemberById(memberId);
		if (dbCoupon == null || dbMember == null) {
			throw new BusinessException("提交数据不能为空");
		}
		// 检查优惠券领取数量
		if ((dbCoupon.getReceiveQuantity() + 1) > dbCoupon.getQuantity()) {
			throw new BusinessException("哎呀，已经被领完了，下次早点来哦");
		}
		// 有效类型-时间段，检查是否失效
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
			if (DateUtils.parseDate(DateUtils.getFormatDate(dbCoupon.getEndTime()), "yyyy-MM-dd").before(DateUtils.parseDate(DateUtils.getFormatDate(new Date()), "yyyy-MM-dd"))) {
				throw new BusinessException("该优惠券已失效，暂不支持领取");
			}
		}
		// 判断会员等级是否符合标准
		boolean levelFlag = this.checkMemberLevel(dbMember.getMemberLevel(), dbCoupon.getMemberLevels());
		if (!levelFlag) {
			throw new BusinessException("该等级暂不支持领取，请您升级会员等级");
		}
		// 检查该会员领取该优惠券的数量 为空的话是不限制领取
		if (dbCoupon.getLimited() != null) {
			int receivedQuantity = couponReceiveDao.countByCoupon_idAndMember_id(couponId, memberId);
			if ((receivedQuantity + 1) > dbCoupon.getLimited()) {
				throw new BusinessException("不要贪心哦，您已经领取了该优惠券");
			}
		}
		// 领取优惠券
		CouponReceive couponReceive = new CouponReceive();
		couponReceive.setCoupon(dbCoupon);
		couponReceive.setCouponNo(dbCoupon.getCouponNo());
		couponReceive.setCouponType(dbCoupon.getCouponType());
		couponReceive.setParValue(dbCoupon.getParValue());
		couponReceive.setUsed(BigDecimal.ZERO);
		couponReceive.setSurplus(dbCoupon.getParValue());
		couponReceive.setMember(dbMember);
		couponReceive.setMemberPhone(dbMember.getPhone());
		couponReceive.setReceiveMode(ActivityEnum.RECEIVE_MODE_SELF.getCode());
		couponReceive.setReceiveTime(new Date());
		couponReceive.setState(ActivityEnum.COUPON_USE_STATE_NO_USED.getCode());
		couponReceive.setFeelWord(dbCoupon.getFeelWord());
		// 有效类型 时间段
		if (ActivityEnum.VALID_TYPE_PERIOD.getCode().equals(dbCoupon.getValidType())) {
			couponReceive.setStartTime(new Date());
			couponReceive.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(dbCoupon.getEndTime()) + " 23:59:59"));
			// 有效类型 固定天数
		} else if (ActivityEnum.VALID_TYPE_FIXED_DAY.getCode().equals(dbCoupon.getValidType())) {
			Date startDate = new Date();
			couponReceive.setStartTime(startDate);
			couponReceive.setEndTime(DateUtils.addDays(startDate, dbCoupon.getFixedDay()));
		}
		couponReceiveDao.save(couponReceive);
		// 减去 优惠券领取数量
		// dbCoupon.setReceiveQuantity(dbCoupon.getReceiveQuantity() + 1);
		couponService.updateReceiveQuantity(dbCoupon.getId(), 1);
	}

}
