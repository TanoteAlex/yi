/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service.impl;

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

import com.yi.core.common.Deleted;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.dao.MemberCommissionDao;
import com.yi.core.member.domain.bo.MemberCommissionBo;
import com.yi.core.member.domain.entity.MemberCommission;
import com.yi.core.member.domain.entity.MemberCommission_;
import com.yi.core.member.domain.simple.MemberCommissionSimple;
import com.yi.core.member.domain.vo.MemberCommissionListVo;
import com.yi.core.member.domain.vo.MemberCommissionVo;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberCommissionService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.date.DateUtils;

/**
 * 会员佣金
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class MemberCommissionServiceImpl implements IMemberCommissionService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(MemberCommissionServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private MemberCommissionDao memberCommissionDao;

	@Resource
	private IAccountService accountService;

	private EntityListVoBoSimpleConvert<MemberCommission, MemberCommissionBo, MemberCommissionVo, MemberCommissionSimple, MemberCommissionListVo> memberCommissionConvert;

	/**
	 * 分页查询MemberCommission
	 **/
	@Override
	public Page<MemberCommission> query(Pagination<MemberCommission> query) {
		query.setEntityClazz(MemberCommission.class);
		Page<MemberCommission> page = memberCommissionDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: MemberCommission
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MemberCommissionListVo> queryListVo(Pagination<MemberCommission> query) {
		Page<MemberCommission> pages = this.query(query);
		List<MemberCommissionListVo> vos = memberCommissionConvert.toListVos(pages.getContent());
		return new PageImpl<MemberCommissionListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: MemberCommission
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MemberCommissionListVo> queryListVoForApp(Pagination<MemberCommission> query) {
		query.setEntityClazz(MemberCommission.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(MemberCommission_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(MemberCommission_.createTime)));
		}));
		Page<MemberCommission> pages = memberCommissionDao.findAll(query, query.getPageRequest());
		List<MemberCommissionListVo> vos = memberCommissionConvert.toListVos(pages.getContent());
		return new PageImpl<MemberCommissionListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建MemberCommission
	 **/
	@Override
	public MemberCommission addMemberCommission(MemberCommission memberCommission) {
		return memberCommissionDao.save(memberCommission);
	}

	/**
	 * 创建MemberCommission
	 **/
	@Override
	public MemberCommissionListVo addMemberCommission(MemberCommissionBo memberCommissionBo) {
		return memberCommissionConvert.toListVo(memberCommissionDao.save(memberCommissionConvert.toEntity(memberCommissionBo)));
	}

	/**
	 * 更新MemberCommission
	 **/
	@Override
	public MemberCommission updateMemberCommission(MemberCommission memberCommission) {
		MemberCommission dbMemberCommission = memberCommissionDao.getOne(memberCommission.getId());
		AttributeReplication.copying(memberCommission, dbMemberCommission, MemberCommission_.member, MemberCommission_.saleOrder, MemberCommission_.subordinate,
				MemberCommission_.commissionType, MemberCommission_.settlementState, MemberCommission_.commissionAmount, MemberCommission_.remark);
		return dbMemberCommission;
	}

	/**
	 * 更新MemberCommission
	 **/
	@Override
	public MemberCommissionListVo updateMemberCommission(MemberCommissionBo memberCommissionBo) {
		MemberCommission dbMemberCommission = this.updateMemberCommission(memberCommissionConvert.toEntity(memberCommissionBo));
		return memberCommissionConvert.toListVo(dbMemberCommission);
	}

	/**
	 * 删除MemberCommission
	 **/
	@Override
	public void removeMemberCommissionById(int id) {
		// 不能删除数据
		// memberCommissionDao.deleteById(id);
	}

	/**
	 * 根据ID得到MemberCommission
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberCommission getMemberCommissionById(int id) {
		return this.memberCommissionDao.getOne(id);
	}

	/**
	 * 根据ID得到MemberCommissionBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberCommissionBo getMemberCommissionBoById(int id) {
		return memberCommissionConvert.toBo(this.memberCommissionDao.getOne(id));
	}

	/**
	 * 根据ID得到MemberCommissionVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberCommissionVo getMemberCommissionVoById(int id) {
		return memberCommissionConvert.toVo(this.memberCommissionDao.getOne(id));
	}

	/**
	 * 根据ID得到MemberCommissionListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberCommissionListVo getListVoById(int id) {
		return memberCommissionConvert.toListVo(this.memberCommissionDao.getOne(id));
	}

	protected void initConvert() {
		this.memberCommissionConvert = new EntityListVoBoSimpleConvert<MemberCommission, MemberCommissionBo, MemberCommissionVo, MemberCommissionSimple, MemberCommissionListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<MemberCommission, MemberCommissionVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberCommission, MemberCommissionVo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberCommissionVo memberCommissionVo, MemberCommission memberCommission) {
					}
				};
			}

			@Override
			protected BeanConvert<MemberCommission, MemberCommissionListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberCommission, MemberCommissionListVo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberCommissionListVo memberCommissionListVo, MemberCommission memberCommission) {
					}
				};
			}

			@Override
			protected BeanConvert<MemberCommission, MemberCommissionBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberCommission, MemberCommissionBo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberCommissionBo memberCommissionBo, MemberCommission memberCommission) {
					}
				};
			}

			@Override
			protected BeanConvert<MemberCommissionBo, MemberCommission> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberCommissionBo, MemberCommission>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<MemberCommission, MemberCommissionSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberCommission, MemberCommissionSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<MemberCommissionSimple, MemberCommission> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberCommissionSimple, MemberCommission>(beanConvertManager) {
					@Override
					public MemberCommission convert(MemberCommissionSimple memberCommissionSimple) throws BeansException {
						return memberCommissionDao.getOne(memberCommissionSimple.getId());
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
	 * 自动将未结算佣金转入可提现佣金
	 */
	@Override
	public void autoUnsettledCommissionToCashableCommissionByTask() {
		List<MemberCommission> dbMemberCommissions = memberCommissionDao.findBySettlementStateAndDeleted(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isNotEmpty(dbMemberCommissions)) {
			// 如果订单已收货超过三包时效 且未申请售后服务 把未结算佣金转到可提现佣金
			for (MemberCommission tmp : dbMemberCommissions) {
				if (tmp != null && MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode().equals(tmp.getSettlementState())) {
					// 符合条件的订单
					if (OrderEnum.ORDER_STATE_COMPLETED.getCode().equals(tmp.getSaleOrder().getOrderState())
							&& (OrderEnum.AFTER_SALE_STATE_APPLY.getCode().equals(tmp.getSaleOrder().getAfterSaleState())
									|| OrderEnum.AFTER_SALE_STATE_EXPIRE.getCode().equals(tmp.getSaleOrder().getAfterSaleState()))
							&& DateUtils.addDays(tmp.getSaleOrder().getReceiptTime(), 15).before(new Date())) {
						// 计算账户可提现佣金
						accountService.updateCashableCommission(tmp.getSaleOrder(), tmp.getMember().getId(), tmp.getCommissionAmount(), MemberEnum.TRADE_TYPE_COMMISSION,
								tmp.getSaleOrder().getMember());
						// 修改会员佣金结算状态
						tmp.setSettlementState(MemberEnum.SETTLEMENT_STATE_SETTLED.getCode());
					}
				}
			}
		}
	}

	/**
	 * 下级退款时 将该笔订单产生的佣金退回
	 */
	@Override
	public void returnSuperiorCommissionBySubordinateRefund(SaleOrder saleOrder) {
		if (saleOrder != null && saleOrder.getId() > 0) {
			// 查询该笔订单产生的佣金
			List<MemberCommission> dbMemberCommissions = memberCommissionDao.findBySaleOrder_idAndDeleted(saleOrder.getId(), Deleted.DEL_FALSE);
			if (CollectionUtils.isNotEmpty(dbMemberCommissions)) {
				// 将该笔订单产生的佣金退回
				for (MemberCommission tmp : dbMemberCommissions) {
					if (tmp != null) {
						// 计算账户未结算佣金
						accountService.updateUnsettledCommission(tmp.getSaleOrder(), tmp.getMember().getId(), tmp.getCommissionAmount(), MemberEnum.TRADE_TYPE_RETURN_COMMISSION,
								tmp.getSaleOrder().getMember());
						// 修改会员佣金结算状态
						tmp.setSettlementState(MemberEnum.SETTLEMENT_STATE_RETURN.getCode());
					}
				}
			}
		}
	}
}
