/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.yi.core.finance.dao.SupplierCheckAccountDao;
import com.yi.core.finance.domain.bo.SupplierCheckAccountBo;
import com.yi.core.finance.domain.entity.SupplierCheckAccount;
import com.yi.core.finance.domain.entity.SupplierCheckAccount_;
import com.yi.core.finance.domain.simple.SupplierCheckAccountSimple;
import com.yi.core.finance.domain.vo.SupplierCheckAccountListVo;
import com.yi.core.finance.domain.vo.SupplierCheckAccountVo;
import com.yi.core.finance.service.ISupplierCheckAccountService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 供应商对账单
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SupplierCheckAccountServiceImpl implements ISupplierCheckAccountService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SupplierCheckAccountServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SupplierCheckAccountDao supplierCheckAccountDao;

	@Resource
	private ISaleOrderService saleOrderService;

	private EntityListVoBoSimpleConvert<SupplierCheckAccount, SupplierCheckAccountBo, SupplierCheckAccountVo, SupplierCheckAccountSimple, SupplierCheckAccountListVo> supplierCheckAccountConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierCheckAccount> query(Pagination<SupplierCheckAccount> query) {
		query.setEntityClazz(SupplierCheckAccount.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(SupplierCheckAccount_.orderTime)));
		}));
		Page<SupplierCheckAccount> page = supplierCheckAccountDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierCheckAccountListVo> queryListVo(Pagination<SupplierCheckAccount> query) {
		query.setEntityClazz(SupplierCheckAccount.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(SupplierCheckAccount_.orderTime)));
		}));
		Page<SupplierCheckAccount> pages = supplierCheckAccountDao.findAll(query, query.getPageRequest());
		List<SupplierCheckAccountListVo> vos = supplierCheckAccountConvert.toListVos(pages.getContent());
		return new PageImpl<SupplierCheckAccountListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierCheckAccount getById(int supplierCheckAccountId) {
		if (supplierCheckAccountDao.existsById(supplierCheckAccountId)) {
			return supplierCheckAccountDao.getOne(supplierCheckAccountId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierCheckAccountVo getVoById(int supplierCheckAccountId) {
		return supplierCheckAccountConvert.toVo(this.getById(supplierCheckAccountId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierCheckAccountListVo getListVoById(int supplierCheckAccountId) {
		return supplierCheckAccountConvert.toListVo(this.getById(supplierCheckAccountId));
	}

	@Override
	public SupplierCheckAccountVo addSupplierCheckAccount(SupplierCheckAccount supplierCheckAccount) {
		return supplierCheckAccountConvert.toVo(supplierCheckAccountDao.save(supplierCheckAccount));
	}

	@Override
	public SupplierCheckAccountListVo addSupplierCheckAccount(SupplierCheckAccountBo supplierCheckAccountBo) {
		return supplierCheckAccountConvert.toListVo(supplierCheckAccountDao.save(supplierCheckAccountConvert.toEntity(supplierCheckAccountBo)));
	}

	@Override
	public SupplierCheckAccount updateSupplierCheckAccount(SupplierCheckAccount supplierCheckAccount) {
		SupplierCheckAccount dbSupplierCheckAccount = supplierCheckAccountDao.getOne(supplierCheckAccount.getId());
		AttributeReplication.copying(supplierCheckAccount, dbSupplierCheckAccount, SupplierCheckAccount_.supplierName, SupplierCheckAccount_.saleOrderNo,
				SupplierCheckAccount_.orderTime, SupplierCheckAccount_.productNo, SupplierCheckAccount_.productName, SupplierCheckAccount_.supplyPrice,
				SupplierCheckAccount_.quantity, SupplierCheckAccount_.settlementAmount, SupplierCheckAccount_.settlementTime);
		return dbSupplierCheckAccount;
	}

	@Override
	public SupplierCheckAccountListVo updateSupplierCheckAccount(SupplierCheckAccountBo supplierCheckAccountBo) {
		SupplierCheckAccount dbSupplierCheckAccount = supplierCheckAccountDao.getOne(supplierCheckAccountBo.getId());
		AttributeReplication.copying(supplierCheckAccountBo, dbSupplierCheckAccount, SupplierCheckAccount_.supplierName, SupplierCheckAccount_.saleOrderNo,
				SupplierCheckAccount_.orderTime, SupplierCheckAccount_.productNo, SupplierCheckAccount_.productName, SupplierCheckAccount_.supplyPrice,
				SupplierCheckAccount_.quantity, SupplierCheckAccount_.settlementAmount, SupplierCheckAccount_.settlementTime);
		return supplierCheckAccountConvert.toListVo(dbSupplierCheckAccount);
	}

	@Override
	public void removeById(int supplierCheckAccountId) {
		supplierCheckAccountDao.deleteById(supplierCheckAccountId);
	}

	protected void initConvert() {
		this.supplierCheckAccountConvert = new EntityListVoBoSimpleConvert<SupplierCheckAccount, SupplierCheckAccountBo, SupplierCheckAccountVo, SupplierCheckAccountSimple, SupplierCheckAccountListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<SupplierCheckAccount, SupplierCheckAccountVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccount, SupplierCheckAccountVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierCheckAccountVo SupplierCheckAccountVo, SupplierCheckAccount SupplierCheckAccount) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierCheckAccount, SupplierCheckAccountListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccount, SupplierCheckAccountListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierCheckAccountListVo SupplierCheckAccountListVo, SupplierCheckAccount SupplierCheckAccount) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierCheckAccount, SupplierCheckAccountBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccount, SupplierCheckAccountBo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierCheckAccountBo SupplierCheckAccountBo, SupplierCheckAccount SupplierCheckAccount) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierCheckAccountBo, SupplierCheckAccount> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccountBo, SupplierCheckAccount>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierCheckAccount, SupplierCheckAccountSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccount, SupplierCheckAccountSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierCheckAccountSimple, SupplierCheckAccount> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierCheckAccountSimple, SupplierCheckAccount>(beanConvertManager) {
					@Override
					public SupplierCheckAccount convert(SupplierCheckAccountSimple SupplierCheckAccountSimple) throws BeansException {
						return supplierCheckAccountDao.getOne(SupplierCheckAccountSimple.getId());
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
	 * 自动生成对账单
	 */
	@Override
	public void autoCheckAccountByTask() {
		// 查询需要生成对账的销售订单
		List<SaleOrder> dbSaleOrders = saleOrderService.getWaitCheckOrders();
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.info("没有待对账的订单");
			return;
		}
		LOG.info("待对账的订单数为{}个", dbSaleOrders.size());
		List<SupplierCheckAccount> supplierCheckAccounts = new ArrayList<>();
		// 本次对账的销售订单
		Set<SaleOrder> checkSaleOrders = new HashSet<>();
		for (SaleOrder tmpOrder : dbSaleOrders) {
			if (tmpOrder != null && OrderEnum.CHECK_STATE_WAIT.getCode().equals(tmpOrder.getCheckState()) && CollectionUtils.isNotEmpty(tmpOrder.getSaleOrderItems())) {
				for (SaleOrderItem tmpItem : tmpOrder.getSaleOrderItems()) {
					if (tmpItem != null) {
						SupplierCheckAccount supplierCheckAccount = new SupplierCheckAccount();
						supplierCheckAccount.setSupplier(tmpOrder.getSupplier());
						supplierCheckAccount.setSupplierName(tmpOrder.getSupplier().getSupplierName());
						supplierCheckAccount.setSaleOrder(tmpOrder);
						supplierCheckAccount.setSaleOrderNo(tmpOrder.getOrderNo());
						supplierCheckAccount.setSaleOrderItem(tmpItem);
						supplierCheckAccount.setCommodity(tmpItem.getCommodity());
						supplierCheckAccount.setCommodityNo(tmpItem.getCommodity().getCommodityNo());
						supplierCheckAccount.setCommodityName(tmpItem.getCommodity().getCommodityName());
						supplierCheckAccount.setProduct(tmpItem.getProduct());
						supplierCheckAccount.setProductNo(tmpItem.getProduct().getProductNo());
						supplierCheckAccount.setProductName(tmpItem.getProduct().getProductName());
						supplierCheckAccount.setSupplyPrice(tmpItem.getSupplyPrice());
						supplierCheckAccount.setSalePrice(tmpItem.getPrice());
						supplierCheckAccount.setQuantity(tmpItem.getQuantity());
						supplierCheckAccount.setSettlementAmount(tmpItem.getCostAmount());
						supplierCheckAccount.setOrderTime(tmpOrder.getOrderTime());
						supplierCheckAccount.setApplyTime(new Date());
						supplierCheckAccounts.add(supplierCheckAccount);
					}
				}
				checkSaleOrders.add(tmpOrder);
			}
		}
		// 保存对账单数据
		supplierCheckAccountDao.saveAll(supplierCheckAccounts);
		// 修改订单的对账状态
		saleOrderService.updateOrderCheckState(checkSaleOrders);
	}

	/**
	 * 获取导出的对账
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SupplierCheckAccount> getExportCheckAccounts(Pagination<SupplierCheckAccount> query) {
		query.setEntityClazz(SupplierCheckAccount.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(SupplierCheckAccount_.orderTime)));
		}));
		List<SupplierCheckAccount> dbSupplierCheckAccounts = supplierCheckAccountDao.findAll(query);
		return dbSupplierCheckAccounts;
	}

}
