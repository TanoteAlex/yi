/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import java.math.BigDecimal;
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

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.CouponGrantRecordDao;
import com.yi.core.activity.domain.bo.CouponGrantRecordBo;
import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.activity.domain.entity.CouponGrantRecord;
import com.yi.core.activity.domain.entity.CouponGrantRecord_;
import com.yi.core.activity.domain.entity.CouponGrantStep;
import com.yi.core.activity.domain.simple.CouponGrantRecordSimple;
import com.yi.core.activity.domain.vo.CouponGrantRecordListVo;
import com.yi.core.activity.domain.vo.CouponGrantRecordVo;
import com.yi.core.activity.service.ICouponGrantRecordService;
import com.yi.core.common.Deleted;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
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
public class CouponGrantRecordServiceImpl implements ICouponGrantRecordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CouponGrantRecordServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CouponGrantRecordDao couponGrantRecordDao;

	private EntityListVoBoSimpleConvert<CouponGrantRecord, CouponGrantRecordBo, CouponGrantRecordVo, CouponGrantRecordSimple, CouponGrantRecordListVo> couponGrantRecordConvert;

	/**
	 * 分页查询CouponGrantRecord
	 **/
	@Override
	public Page<CouponGrantRecord> query(Pagination<CouponGrantRecord> query) {
		query.setEntityClazz(CouponGrantRecord.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponGrantRecord_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(CouponGrantRecord_.createTime)));
		}));
		Page<CouponGrantRecord> page = couponGrantRecordDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: CouponGrantRecord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponGrantRecordListVo> queryListVo(Pagination<CouponGrantRecord> query) {
		Page<CouponGrantRecord> pages = this.query(query);
		List<CouponGrantRecordListVo> vos = couponGrantRecordConvert.toListVos(pages.getContent());
		return new PageImpl<CouponGrantRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: CouponGrantRecord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CouponGrantRecordListVo> queryListVoForApp(Pagination<CouponGrantRecord> query) {
		query.setEntityClazz(CouponGrantRecord.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(CouponGrantRecord_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(CouponGrantRecord_.createTime)));
		}));
		Page<CouponGrantRecord> pages = couponGrantRecordDao.findAll(query, query.getPageRequest());
		List<CouponGrantRecordListVo> vos = couponGrantRecordConvert.toListVos(pages.getContent());
		return new PageImpl<CouponGrantRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建CouponGrantRecord
	 **/
	@Override
	public CouponGrantRecord addCouponGrantRecord(CouponGrantRecord couponGrantRecord) {
		if (couponGrantRecord == null || couponGrantRecord.getMember() == null || couponGrantRecord.getCouponGrantConfig() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return couponGrantRecordDao.save(couponGrantRecord);
	}

	/**
	 * 创建CouponGrantRecord
	 **/
	@Override
	public CouponGrantRecordListVo addCouponGrantRecord(CouponGrantRecordBo couponGrantRecordBo) {
		return couponGrantRecordConvert.toListVo(this.addCouponGrantRecord(couponGrantRecordConvert.toEntity(couponGrantRecordBo)));
	}

	/**
	 * 更新CouponGrantRecord
	 **/
	@Override
	public CouponGrantRecord updateCouponGrantRecord(CouponGrantRecord couponGrantRecord) {
		CouponGrantRecord dbCouponGrantRecord = couponGrantRecordDao.getOne(couponGrantRecord.getId());
		AttributeReplication.copying(couponGrantRecord, dbCouponGrantRecord, CouponGrantRecord_.member, CouponGrantRecord_.couponGrantConfig, CouponGrantRecord_.coupon,
				CouponGrantRecord_.grantNode, CouponGrantRecord_.parValue, CouponGrantRecord_.remark);
		return dbCouponGrantRecord;
	}

	/**
	 * 更新CouponGrantRecord
	 **/
	@Override
	public CouponGrantRecordListVo updateCouponGrantRecord(CouponGrantRecordBo couponGrantRecordBo) {
		CouponGrantRecord dbCouponGrantRecord = this.updateCouponGrantRecord(couponGrantRecordConvert.toEntity(couponGrantRecordBo));
		return couponGrantRecordConvert.toListVo(dbCouponGrantRecord);
	}

	/**
	 * 删除CouponGrantRecord
	 **/
	@Override
	public void removeCouponGrantRecordById(int id) {
		CouponGrantRecord dbCouponGrantRecord = this.getCouponGrantRecordById(id);
		if (dbCouponGrantRecord != null) {
			dbCouponGrantRecord.setDeleted(Deleted.DEL_TRUE);
			dbCouponGrantRecord.setDelTime(new Date());
		}
	}

	/**
	 * 根据ID得到CouponGrantRecord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantRecord getCouponGrantRecordById(int id) {
		return this.couponGrantRecordDao.getOne(id);
	}

	/**
	 * 根据ID得到CouponGrantRecordBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantRecordBo getCouponGrantRecordBoById(int id) {
		return couponGrantRecordConvert.toBo(this.couponGrantRecordDao.getOne(id));
	}

	/**
	 * 根据ID得到CouponGrantRecordVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantRecordVo getCouponGrantRecordVoById(int id) {
		return couponGrantRecordConvert.toVo(this.couponGrantRecordDao.getOne(id));
	}

	/**
	 * 根据ID得到CouponGrantRecordListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponGrantRecordListVo getListVoById(int id) {
		return couponGrantRecordConvert.toListVo(this.couponGrantRecordDao.getOne(id));
	}

	protected void initConvert() {
		this.couponGrantRecordConvert = new EntityListVoBoSimpleConvert<CouponGrantRecord, CouponGrantRecordBo, CouponGrantRecordVo, CouponGrantRecordSimple, CouponGrantRecordListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<CouponGrantRecord, CouponGrantRecordVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantRecord, CouponGrantRecordVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponGrantRecordVo couponGrantRecordVo, CouponGrantRecord couponGrantRecord) {
					}
				};
			}

			@Override
			protected BeanConvert<CouponGrantRecord, CouponGrantRecordListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantRecord, CouponGrantRecordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponGrantRecordListVo couponGrantRecordListVo, CouponGrantRecord couponGrantRecord) {
					}
				};
			}

			@Override
			protected BeanConvert<CouponGrantRecord, CouponGrantRecordBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantRecord, CouponGrantRecordBo>(beanConvertManager) {
					@Override
					protected void postConvert(CouponGrantRecordBo couponGrantRecordBo, CouponGrantRecord couponGrantRecord) {
					}
				};
			}

			@Override
			protected BeanConvert<CouponGrantRecordBo, CouponGrantRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantRecordBo, CouponGrantRecord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponGrantRecord, CouponGrantRecordSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantRecord, CouponGrantRecordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CouponGrantRecordSimple, CouponGrantRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CouponGrantRecordSimple, CouponGrantRecord>(beanConvertManager) {
					@Override
					public CouponGrantRecord convert(CouponGrantRecordSimple couponGrantRecordSimple) throws BeansException {
						return couponGrantRecordDao.getOne(couponGrantRecordSimple.getId());
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
	 * 检查是否发放过优惠券
	 */
	@Override
	public boolean checkGrantForOnce(ActivityEnum grantStep, Member member, SaleOrder saleOrder, CouponGrantConfig couponGrantConfig, int buyQuantity) {
		if (grantStep == null || member == null || saleOrder == null || couponGrantConfig == null) {
			return false;
		}
		Set<CouponGrantRecord> dbCouponGrantRecords = couponGrantRecordDao.findByMember_idAndSaleOrder_idAndCouponGrantConfig_idAndCoupon_idAndGrantNodeAndParValueAndDeleted(
				member.getId(), saleOrder.getId(), couponGrantConfig.getId(), couponGrantConfig.getCoupon().getId(), grantStep.getCode(),
				couponGrantConfig.getCoupon().getParValue(), Deleted.DEL_FALSE);
		if (CollectionUtils.isNotEmpty(dbCouponGrantRecords) && dbCouponGrantRecords.size() == buyQuantity) {
			return true;
		}
		return false;
	}

	/**
	 * 检查该步骤是不是发放过优惠券
	 */
	@Override
	public boolean checkGrantForStep(ActivityEnum grantStep, Member member, SaleOrder saleOrder, CouponGrantConfig couponGrantConfig, int buyQuantity) {
		if (grantStep == null || member == null || saleOrder == null || couponGrantConfig == null || CollectionUtils.isEmpty(couponGrantConfig.getCouponGrantSteps())) {
			return false;
		}
		// 遍历该步骤是否发过
		for (CouponGrantStep tmpStep : couponGrantConfig.getCouponGrantSteps()) {
			if (grantStep.getCode().equals(tmpStep.getGrantNode())) {
				Set<CouponGrantRecord> dbCouponGrantRecords = couponGrantRecordDao
						.findByMember_idAndSaleOrder_idAndCouponGrantConfig_idAndCoupon_idAndGrantNodeAndParValueAndDeleted(member.getId(), saleOrder.getId(),
								couponGrantConfig.getId(), couponGrantConfig.getCoupon().getId(), tmpStep.getGrantNode(),
								tmpStep.getGrantRate().multiply(couponGrantConfig.getCoupon().getParValue()).divide(BigDecimal.valueOf(100)), Deleted.DEL_FALSE);
				if (CollectionUtils.isNotEmpty(dbCouponGrantRecords) && dbCouponGrantRecords.size() == buyQuantity) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查是不是第一次发放
	 */
	@Override
	public boolean checkFirstGrant(Member member, SaleOrder saleOrder, CouponGrantConfig couponGrantConfig, int buyQuantity) {
		if (member == null || saleOrder == null || couponGrantConfig == null) {
			return false;
		}
		// 有效步骤
		int validStepNum = couponGrantConfig.getCouponGrantSteps().stream().map(e -> e.getGrantRate().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toSet()).size();
		// 统计发放数量
		int grantNum = couponGrantRecordDao.countByMember_idAndSaleOrder_idAndCouponGrantConfig_idAndCoupon_idAndDeleted(member.getId(), saleOrder.getId(),
				couponGrantConfig.getId(), couponGrantConfig.getCoupon().getId(), Deleted.DEL_FALSE);
		// TODO 待验证
		if (grantNum == 0 || (buyQuantity * validStepNum > grantNum && buyQuantity * validStepNum % grantNum == 0)) {
			return true;
		}
		return false;
	}

	@Override
	public CouponGrantRecord addByGrantStepForOnce(ActivityEnum grantStep, Member member, SaleOrder saleOrder, CouponGrantConfig couponGrantConfig, BigDecimal parValue) {
		if (grantStep != null && member != null && saleOrder != null && couponGrantConfig != null) {
			// 发放优惠券并保存
			CouponGrantRecord grantRecord = new CouponGrantRecord();
			grantRecord.setCouponGrantConfig(couponGrantConfig);
			grantRecord.setMember(member);
			grantRecord.setSaleOrder(saleOrder);
			grantRecord.setCoupon(couponGrantConfig.getCoupon());
			grantRecord.setParValue(parValue);
			switch (grantStep) {
			case GRANT_NODE_ORDER:
				grantRecord.setGrantNode(ActivityEnum.GRANT_NODE_ORDER.getCode());
				grantRecord.setRemark("下单支付时，一次性发放优惠券，面额为：" + parValue.toString());
				break;
			case GRANT_NODE_RECEIPT:
				grantRecord.setGrantNode(ActivityEnum.GRANT_NODE_RECEIPT.getCode());
				grantRecord.setRemark("确认收货时，一次性发放优惠券，面额为：" + parValue.toString());
				break;
			case GRANT_NODE_COMMENT:
				grantRecord.setGrantNode(ActivityEnum.GRANT_NODE_COMMENT.getCode());
				grantRecord.setRemark("评论时，一次性发放优惠券，面额为：" + parValue.toString());
				break;
			case GRANT_NODE_OVER_15_DAY:
				grantRecord.setGrantNode(ActivityEnum.GRANT_NODE_OVER_15_DAY.getCode());
				grantRecord.setRemark("超过三包时效时，一次性发放优惠券，面额为：" + parValue.toString());
				break;
			default:
				break;
			}
			return this.addCouponGrantRecord(grantRecord);
		}
		return null;
	}

	@Override
	public CouponGrantRecord addByGrantStepForStep(ActivityEnum grantStep, Member member, SaleOrder saleOrder, CouponGrantConfig couponGrantConfig, BigDecimal parValue) {
		if (grantStep != null && member != null && saleOrder != null && couponGrantConfig != null) {
			// 发放优惠券并保存
			CouponGrantRecord grantRecord = new CouponGrantRecord();
			grantRecord.setCouponGrantConfig(couponGrantConfig);
			grantRecord.setMember(member);
			grantRecord.setSaleOrder(saleOrder);
			grantRecord.setCoupon(couponGrantConfig.getCoupon());
			grantRecord.setParValue(parValue);
			switch (grantStep) {
			case GRANT_NODE_ORDER:
				grantRecord.setGrantNode(ActivityEnum.GRANT_NODE_ORDER.getCode());
				grantRecord.setRemark("下单支付时，分步发放优惠券，面额为：" + parValue.toString());
				break;
			case GRANT_NODE_RECEIPT:
				grantRecord.setGrantNode(ActivityEnum.GRANT_NODE_RECEIPT.getCode());
				grantRecord.setRemark("确认收货时，分步发放优惠券，面额为：" + parValue.toString());
				break;
			case GRANT_NODE_COMMENT:
				grantRecord.setGrantNode(ActivityEnum.GRANT_NODE_COMMENT.getCode());
				grantRecord.setRemark("评论时，分步发放优惠券，面额为：" + parValue.toString());
				break;
			case GRANT_NODE_OVER_15_DAY:
				grantRecord.setGrantNode(ActivityEnum.GRANT_NODE_OVER_15_DAY.getCode());
				grantRecord.setRemark("超过三包时效，分步发放优惠券，面额为：" + parValue.toString());
				break;
			default:
				break;
			}
			return this.addCouponGrantRecord(grantRecord);
		}
		return null;
	}

	@Override
	public List<CouponGrantRecord> getGrantRecordsByMemberAndOrder(Member member, SaleOrder saleOrder) {
		if (member != null && saleOrder != null) {
			return couponGrantRecordDao.findByMember_idAndSaleOrder_idAndDeleted(member.getId(), saleOrder.getId(), Deleted.DEL_FALSE);
		}
		return null;
	}

}
