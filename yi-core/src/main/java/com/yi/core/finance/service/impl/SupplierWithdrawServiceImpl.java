/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service.impl;

import java.math.BigDecimal;
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

import com.yi.core.common.Deleted;
import com.yi.core.finance.FinanceEnum;
import com.yi.core.finance.dao.SupplierWithdrawDao;
import com.yi.core.finance.domain.bo.SupplierWithdrawBo;
import com.yi.core.finance.domain.entity.SupplierWithdraw;
import com.yi.core.finance.domain.entity.SupplierWithdraw_;
import com.yi.core.finance.domain.simple.SupplierWithdrawSimple;
import com.yi.core.finance.domain.vo.SupplierWithdrawListVo;
import com.yi.core.finance.domain.vo.SupplierWithdrawVo;
import com.yi.core.finance.service.ISupplierWithdrawService;
import com.yi.core.supplier.domain.entity.SupplierAccount;
import com.yi.core.supplier.domain.entity.Supplier_;
import com.yi.core.supplier.service.ISupplierAccountService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 供应商提现
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SupplierWithdrawServiceImpl implements ISupplierWithdrawService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SupplierWithdrawServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SupplierWithdrawDao supplierWithdrawDao;

	@Resource
	private ISupplierAccountService supplierAccountService;

	private EntityListVoBoSimpleConvert<SupplierWithdraw, SupplierWithdrawBo, SupplierWithdrawVo, SupplierWithdrawSimple, SupplierWithdrawListVo> supplierWithdrawConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierWithdraw> query(Pagination<SupplierWithdraw> query) {
		query.setEntityClazz(SupplierWithdraw.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SupplierWithdraw_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SupplierWithdraw_.applyTime)));
		}));
		Page<SupplierWithdraw> page = supplierWithdrawDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierWithdrawListVo> queryListVo(Pagination<SupplierWithdraw> query) {
		query.setEntityClazz(SupplierWithdraw.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SupplierWithdraw_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SupplierWithdraw_.applyTime)));
		}));
		Page<SupplierWithdraw> pages = supplierWithdrawDao.findAll(query, query.getPageRequest());
		List<SupplierWithdrawListVo> vos = supplierWithdrawConvert.toListVos(pages.getContent());
		return new PageImpl<SupplierWithdrawListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierWithdrawListVo> queryListVoForSupplier(Pagination<SupplierWithdraw> query) {
		query.setEntityClazz(SupplierWithdraw.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SupplierWithdraw_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SupplierWithdraw_.applyTime)));
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.equal(root.get(SupplierWithdraw_.supplier).get(Supplier_.id), supplierId));
			} else {
				list.add(criteriaBuilder.equal(root.get(SupplierWithdraw_.supplier).get(Supplier_.id), null));
			}
		}));
		Page<SupplierWithdraw> pages = supplierWithdrawDao.findAll(query, query.getPageRequest());
		List<SupplierWithdrawListVo> vos = supplierWithdrawConvert.toListVos(pages.getContent());
		return new PageImpl<SupplierWithdrawListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	public SupplierWithdraw addSupplierWithdraw(SupplierWithdraw supplierWithdraw) {
		if (supplierWithdraw == null || supplierWithdraw.getSupplier() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return supplierWithdrawDao.save(supplierWithdraw);
	}

	@Override
	public SupplierWithdrawListVo addSupplierWithdraw(SupplierWithdrawBo supplierWithdrawBo) {
		SupplierWithdraw dbSupplierWithdraw = this.addSupplierWithdraw(supplierWithdrawConvert.toEntity(supplierWithdrawBo));
		return supplierWithdrawConvert.toListVo(dbSupplierWithdraw);
	}

	@Override
	public SupplierWithdrawListVo addForSupplier(SupplierWithdrawBo supplierWithdrawBo) {
		SupplierAccount dbSupplierAccount = supplierAccountService.getBySupplier(supplierWithdrawBo.getSupplier().getId());
		// 判断提现金额是否超过供应商可提现金额
		if (dbSupplierAccount.getBalance().compareTo(supplierWithdrawBo.getApplyAmount()) < 0) {
			throw new BusinessException("提现金额不能超过可提现金额（" + dbSupplierAccount.getBalance() + "）");
		}
		supplierWithdrawBo.setSupplierName(dbSupplierAccount.getSupplier().getSupplierName());
		supplierWithdrawBo.setState(FinanceEnum.APPLY_STATE_WAIT_GRANT.getCode());
		supplierWithdrawBo.setApplyTime(new Date());
		SupplierWithdraw dbSupplierWithdraw = this.addSupplierWithdraw(supplierWithdrawConvert.toEntity(supplierWithdrawBo));
		return supplierWithdrawConvert.toListVo(dbSupplierWithdraw);
	}

	@Override
	public SupplierWithdraw updateSupplierWithdraw(SupplierWithdraw supplierWithdraw) {
		SupplierWithdraw dbSupplierWithdraw = this.getById(supplierWithdraw.getId());
		AttributeReplication.copying(supplierWithdraw, dbSupplierWithdraw, SupplierWithdraw_.payee, SupplierWithdraw_.receiptsNo, SupplierWithdraw_.receiptsName,
				SupplierWithdraw_.applyAmount, SupplierWithdraw_.actualAmount, SupplierWithdraw_.serviceCharge, SupplierWithdraw_.drawee, SupplierWithdraw_.paymentsNo,
				SupplierWithdraw_.paymentsName, SupplierWithdraw_.paymentMethod, SupplierWithdraw_.state, SupplierWithdraw_.errorDesc, SupplierWithdraw_.applyTime,
				SupplierWithdraw_.grantTime);
		return dbSupplierWithdraw;
	}

	@Override
	public SupplierWithdrawListVo updateSupplierWithdraw(SupplierWithdrawBo supplierWithdrawBo) {
		SupplierWithdraw dbSupplierWithdraw = this.updateSupplierWithdraw(supplierWithdrawConvert.toEntity(supplierWithdrawBo));
		return supplierWithdrawConvert.toListVo(dbSupplierWithdraw);
	}

	@Override
	public SupplierWithdrawListVo updateForSupplier(SupplierWithdrawBo supplierWithdrawBo) {
		SupplierAccount dbSupplierAccount = supplierAccountService.getBySupplier(supplierWithdrawBo.getSupplier().getId());
		// 判断提现金额是否超过供应商可提现金额
		if (dbSupplierAccount.getBalance().compareTo(supplierWithdrawBo.getApplyAmount()) < 0) {
			throw new BusinessException("提现金额不能超过可提现金额（" + dbSupplierAccount.getBalance() + "）");
		}
		supplierWithdrawBo.setState(FinanceEnum.APPLY_STATE_WAIT_GRANT.getCode());
		SupplierWithdraw dbSupplierWithdraw = this.getById(supplierWithdrawBo.getId());
		AttributeReplication.copying(supplierWithdrawConvert.toEntity(supplierWithdrawBo), dbSupplierWithdraw, SupplierWithdraw_.payee, SupplierWithdraw_.receiptsNo,
				SupplierWithdraw_.receiptsName, SupplierWithdraw_.applyAmount, SupplierWithdraw_.state);
		return supplierWithdrawConvert.toListVo(dbSupplierWithdraw);
	}

	@Override
	public void removeSupplierWithdrawById(int supplierWithdrawId) {
		SupplierWithdraw dbSupplierWithdraw = this.getById(supplierWithdrawId);
		if (dbSupplierWithdraw != null) {
			dbSupplierWithdraw.setDeleted(Deleted.DEL_TRUE);
			dbSupplierWithdraw.setDelTime(new Date());
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierWithdraw getById(int supplierWithdrawId) {
		if (supplierWithdrawDao.existsById(supplierWithdrawId)) {
			return supplierWithdrawDao.getOne(supplierWithdrawId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierWithdrawVo getVoById(int supplierWithdrawId) {
		return supplierWithdrawConvert.toVo(this.getById(supplierWithdrawId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierWithdrawListVo getListVoById(int supplierWithdrawId) {
		return supplierWithdrawConvert.toListVo(this.getById(supplierWithdrawId));
	}

	/**
	 * 供应商提现发放
	 * 
	 * @param supplierWithdrawId
	 */
	@Override
	public SupplierWithdrawListVo grant(SupplierWithdrawBo supplierWithdrawBo) {
		SupplierAccount dbSupplierAccount = supplierAccountService.getBySupplier(supplierWithdrawBo.getSupplier().getId());
		// 判断发放金额是否超过供应商可提现金额
		if (dbSupplierAccount.getBalance().compareTo(supplierWithdrawBo.getApplyAmount()) < 0) {
			throw new BusinessException("发放金额不能超过可提现金额（" + dbSupplierAccount.getBalance() + "）");
		}
		SupplierWithdraw dbSupplierWithdraw = this.getById(supplierWithdrawBo.getId());
		AttributeReplication.copying(supplierWithdrawConvert.toEntity(supplierWithdrawBo), dbSupplierWithdraw, SupplierWithdraw_.actualAmount, SupplierWithdraw_.serviceCharge,
				SupplierWithdraw_.drawee, SupplierWithdraw_.paymentsNo, SupplierWithdraw_.paymentsName, SupplierWithdraw_.paymentMethod);
		dbSupplierWithdraw.setState(FinanceEnum.APPLY_STATE_ALREADY_GRANT.getCode());
		dbSupplierWithdraw.setGrantTime(new Date());
		// 发放完毕 修正供应商账户
		supplierAccountService.updateSupplierAccountByGrant(dbSupplierWithdraw);
		return supplierWithdrawConvert.toListVo(dbSupplierWithdraw);
	}

	/**
	 * 供应商提现驳回
	 * 
	 * @param supplierWithdrawId
	 */
	@Override
	public void reject(int supplierWithdrawId) {
		SupplierWithdraw dbSupplierWithdraw = this.getById(supplierWithdrawId);
		if (dbSupplierWithdraw != null) {
			dbSupplierWithdraw.setState(FinanceEnum.APPLY_STATE_EXCEPTION.getCode());
		}
	}

	protected void initConvert() {
		this.supplierWithdrawConvert = new EntityListVoBoSimpleConvert<SupplierWithdraw, SupplierWithdrawBo, SupplierWithdrawVo, SupplierWithdrawSimple, SupplierWithdrawListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<SupplierWithdraw, SupplierWithdrawVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdraw, SupplierWithdrawVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierWithdrawVo SupplierWithdrawVo, SupplierWithdraw SupplierWithdraw) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierWithdraw, SupplierWithdrawListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdraw, SupplierWithdrawListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierWithdrawListVo SupplierWithdrawListVo, SupplierWithdraw SupplierWithdraw) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierWithdraw, SupplierWithdrawBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdraw, SupplierWithdrawBo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierWithdrawBo SupplierWithdrawBo, SupplierWithdraw SupplierWithdraw) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierWithdrawBo, SupplierWithdraw> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdrawBo, SupplierWithdraw>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierWithdraw, SupplierWithdrawSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdraw, SupplierWithdrawSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierWithdrawSimple, SupplierWithdraw> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierWithdrawSimple, SupplierWithdraw>(beanConvertManager) {
					@Override
					public SupplierWithdraw convert(SupplierWithdrawSimple SupplierWithdrawSimple) throws BeansException {
						return supplierWithdrawDao.getOne(SupplierWithdrawSimple.getId());
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
	 * 查询申请状态供应商
	 * 
	 * @param state
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByState(Integer state) {
		return supplierWithdrawDao.countByState(state);
	}

	/**
	 * 查询供应商待结算资金
	 * 
	 * @param state
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal waitSettlement(Integer state) {
		return supplierWithdrawDao.sumApplyAmountByState(state);
	}

	/**
	 * 查询供应商后台提现数量
	 * 
	 * @param supplierId
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int withdrawNum(int supplierId) {
		return supplierWithdrawDao.countBySupplier_id(supplierId);
	}
}
