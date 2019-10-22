/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

import com.yi.core.finance.domain.entity.SupplierWithdraw;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.supplier.dao.SupplierAccountDao;
import com.yi.core.supplier.domain.bo.SupplierAccountBo;
import com.yi.core.supplier.domain.entity.SupplierAccount;
import com.yi.core.supplier.domain.entity.SupplierAccount_;
import com.yi.core.supplier.domain.simple.SupplierAccountSimple;
import com.yi.core.supplier.domain.vo.SupplierAccountListVo;
import com.yi.core.supplier.domain.vo.SupplierAccountVo;
import com.yi.core.supplier.service.ISupplierAccountService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 供应商账户
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SupplierAccountServiceImpl implements ISupplierAccountService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SupplierAccountServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SupplierAccountDao supplierAccountDao;

	@Resource
	private ISaleOrderService saleOrderService;

	private EntityListVoBoSimpleConvert<SupplierAccount, SupplierAccountBo, SupplierAccountVo, SupplierAccountSimple, SupplierAccountListVo> supplierAccountConvert;

	/**
	 * 分页查询SupplierAccount
	 **/
	@Override
	public Page<SupplierAccount> query(Pagination<SupplierAccount> query) {
		query.setEntityClazz(SupplierAccount.class);
		Page<SupplierAccount> page = supplierAccountDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 创建SupplierAccount
	 **/
	@Override
	public SupplierAccountVo addSupplierAccount(SupplierAccountBo supplierAccountBo) {
		return supplierAccountConvert.toVo(supplierAccountDao.save(supplierAccountConvert.toEntity(supplierAccountBo)));
	}

	/**
	 * 创建SupplierAccount
	 **/
	@Override
	public SupplierAccountVo addSupplierAccount(SupplierAccount supplierAccountBo) {
		return supplierAccountConvert.toVo(supplierAccountDao.save(supplierAccountBo));
	}

	/**
	 * 更新SupplierAccount
	 **/
	@Override
	public SupplierAccountVo updateSupplierAccount(SupplierAccountBo supplierAccountBo) {
		SupplierAccount dbSupplierAccount = supplierAccountDao.getOne(supplierAccountBo.getId());
		AttributeReplication.copying(supplierAccountBo, dbSupplierAccount, SupplierAccount_.guid, SupplierAccount_.supplier, SupplierAccount_.amount, SupplierAccount_.balance,
				SupplierAccount_.freezeAmount, SupplierAccount_.withdrawAmount, SupplierAccount_.createTime, SupplierAccount_.remark);
		return supplierAccountConvert.toVo(dbSupplierAccount);
	}

	/**
	 * 删除SupplierAccount
	 **/
	@Override
	public void removeSupplierAccountById(int id) {
		supplierAccountDao.deleteById(id);
	}

	/**
	 * 根据ID得到SupplierAccount
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierAccountVo getSupplierAccountVoById(int id) {

		return supplierAccountConvert.toVo(this.supplierAccountDao.getOne(id));
	}

	/**
	 * 根据ID得到SupplierAccountListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierAccountListVo getListVoById(int id) {
		return supplierAccountConvert.toListVo(this.supplierAccountDao.getOne(id));
	}

	/**
	 * 分页查询: SupplierAccount
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierAccountListVo> queryListVo(Pagination<SupplierAccount> query) {

		Page<SupplierAccount> pages = this.query(query);

		List<SupplierAccountListVo> vos = supplierAccountConvert.toListVos(pages.getContent());
		return new PageImpl<SupplierAccountListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.supplierAccountConvert = new EntityListVoBoSimpleConvert<SupplierAccount, SupplierAccountBo, SupplierAccountVo, SupplierAccountSimple, SupplierAccountListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<SupplierAccount, SupplierAccountVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccount, SupplierAccountVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierAccountVo supplierAccountVo, SupplierAccount supplierAccount) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierAccount, SupplierAccountListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccount, SupplierAccountListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierAccountListVo supplierAccountListVo, SupplierAccount supplierAccount) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierAccount, SupplierAccountBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccount, SupplierAccountBo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierAccountBo supplierAccountBo, SupplierAccount supplierAccount) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierAccountBo, SupplierAccount> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccountBo, SupplierAccount>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierAccount, SupplierAccountSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccount, SupplierAccountSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierAccountSimple, SupplierAccount> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccountSimple, SupplierAccount>(beanConvertManager) {
					@Override
					public SupplierAccount convert(SupplierAccountSimple supplierAccountSimple) throws BeansException {
						return supplierAccountDao.getOne(supplierAccountSimple.getId());
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
	 * 查询该供应商的账户
	 *
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierAccount getBySupplier(Integer supplierId) {
		return supplierAccountDao.findBySupplierId(supplierId);
	}

	/**
	 * 查询该供应商的账户
	 *
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierAccountVo getVoBySupplier(Integer supplierId) {
		return supplierAccountConvert.toVo(supplierAccountDao.findBySupplierId(supplierId));
	}

	/**
	 * 支付订单 更新供应商账户
	 */
	@Override
	public void updateSupplierAccountByPayOrder(SaleOrder saleOrder) {
		if (saleOrder != null && saleOrder.getSupplier() != null) {
			// 获取供应商账户
			List<SupplierAccount> dbSupplierAccounts = supplierAccountDao.findBySupplier_id(saleOrder.getSupplier().getId());
			if (CollectionUtils.isEmpty(dbSupplierAccounts)) {
				// 供应商没有账户 创建一个
				SupplierAccount supplierAccount = new SupplierAccount();
				supplierAccount.setSupplier(saleOrder.getSupplier());
				supplierAccount.setAmount(saleOrder.getCostAmount());
				supplierAccount.setBalance(BigDecimal.ZERO);
				supplierAccount.setFreezeAmount(saleOrder.getCostAmount());
				supplierAccount.setWithdrawAmount(BigDecimal.ZERO);
				supplierAccountDao.save(supplierAccount);
			} else {
				SupplierAccount dbSupplierAccount = dbSupplierAccounts.get(0);
				// 账户金额
				dbSupplierAccount.setAmount(Optional.ofNullable(dbSupplierAccount.getAmount()).orElse(BigDecimal.ZERO).add(saleOrder.getCostAmount()));
				// 冻结金额+成本金额
				dbSupplierAccount.setFreezeAmount(Optional.ofNullable(dbSupplierAccount.getFreezeAmount()).orElse(BigDecimal.ZERO).add(saleOrder.getCostAmount()));
			}
		}

	}

	/**
	 * 确认收货 更新供应商账户
	 */
	@Override
	public void updateSupplierAccountByConfirmReceipt(SaleOrder saleOrder) {
		if (saleOrder != null && saleOrder.getSupplier() != null) {
			// 获取供应商账户
			List<SupplierAccount> dbSupplierAccounts = supplierAccountDao.findBySupplier_id(saleOrder.getSupplier().getId());
			if (CollectionUtils.isEmpty(dbSupplierAccounts)) {
				// 供应商没有账户 创建一个
				SupplierAccount supplierAccount = new SupplierAccount();
				supplierAccount.setSupplier(saleOrder.getSupplier());
				supplierAccount.setAmount(saleOrder.getCostAmount());
				supplierAccount.setBalance(saleOrder.getCostAmount());
				supplierAccount.setFreezeAmount(BigDecimal.ZERO);
				supplierAccount.setWithdrawAmount(BigDecimal.ZERO);
				supplierAccountDao.save(supplierAccount);
			} else {
				SupplierAccount dbSupplierAccount = dbSupplierAccounts.get(0);
				// 余额+成本金额
				dbSupplierAccount.setBalance(Optional.ofNullable(dbSupplierAccount.getBalance()).orElse(BigDecimal.ZERO).add(saleOrder.getCostAmount()));
				// 冻结金额-成本金额
				dbSupplierAccount.setFreezeAmount(Optional.ofNullable(dbSupplierAccount.getFreezeAmount()).orElse(BigDecimal.ZERO).subtract(saleOrder.getCostAmount()));
			}
		}
	}

	/**
	 * 确认退款时 更新供应商账户
	 */
	@Override
	public void updateSupplierAccountByConfirmRefund(SaleOrder saleOrder) {
		if (saleOrder != null && saleOrder.getSupplier() != null) {
			// 获取供应商账户
			List<SupplierAccount> dbSupplierAccounts = supplierAccountDao.findBySupplier_id(saleOrder.getSupplier().getId());
			if (CollectionUtils.isEmpty(dbSupplierAccounts)) {
				// 供应商没有账户 创建一个
				SupplierAccount supplierAccount = new SupplierAccount();
				supplierAccount.setSupplier(saleOrder.getSupplier());
				supplierAccount.setAmount(saleOrder.getCostAmount().multiply(BigDecimal.valueOf(-1)));
				supplierAccount.setBalance(saleOrder.getCostAmount().multiply(BigDecimal.valueOf(-1)));
				supplierAccount.setFreezeAmount(BigDecimal.ZERO);
				supplierAccount.setWithdrawAmount(BigDecimal.ZERO);
				supplierAccountDao.save(supplierAccount);
			} else {
				SupplierAccount dbSupplierAccount = dbSupplierAccounts.get(0);
				// 账户金额
				dbSupplierAccount.setAmount(Optional.ofNullable(dbSupplierAccount.getAmount()).orElse(BigDecimal.ZERO).subtract(saleOrder.getCostAmount()));
				// 余额-成本金额
				dbSupplierAccount.setBalance(Optional.ofNullable(dbSupplierAccount.getBalance()).orElse(BigDecimal.ZERO).subtract(saleOrder.getCostAmount()));
			}
		}
	}

	/**
	 * 提现发放 更新供应商账户
	 */
	@Override
	public void updateSupplierAccountByGrant(SupplierWithdraw supplierWithdraw) {
		if (supplierWithdraw != null) {
			// 获取供应商账户
			List<SupplierAccount> dbSupplierAccounts = supplierAccountDao.findBySupplier_id(supplierWithdraw.getSupplier().getId());
			if (CollectionUtils.isNotEmpty(dbSupplierAccounts)) {
				SupplierAccount dbSupplierAccount = dbSupplierAccounts.get(0);
				// 账户金额
				// dbSupplierAccount.setAmount(Optional.ofNullable(dbSupplierAccount.getAmount()).orElse(BigDecimal.ZERO).subtract(saleOrder.getCostAmount()));
				// 余额-提现金额
				dbSupplierAccount.setBalance(Optional.ofNullable(dbSupplierAccount.getBalance()).orElse(BigDecimal.ZERO).subtract(supplierWithdraw.getApplyAmount()));
				// 提现余额+当前提现金额
				dbSupplierAccount.setWithdrawAmount(Optional.ofNullable(dbSupplierAccount.getWithdrawAmount()).orElse(BigDecimal.ZERO).add(supplierWithdraw.getApplyAmount()));
			}
		}
	}

}
