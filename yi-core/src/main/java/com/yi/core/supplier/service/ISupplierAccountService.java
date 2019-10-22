/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.service;

import org.springframework.data.domain.Page;

import com.yi.core.finance.domain.entity.SupplierWithdraw;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.supplier.domain.bo.SupplierAccountBo;
import com.yi.core.supplier.domain.entity.SupplierAccount;
import com.yi.core.supplier.domain.vo.SupplierAccountListVo;
import com.yi.core.supplier.domain.vo.SupplierAccountVo;
import com.yihz.common.persistence.Pagination;

/**
 * 供应商账户
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface ISupplierAccountService {

	Page<SupplierAccount> query(Pagination<SupplierAccount> query);

	/**
	 * 创建SupplierAccount
	 **/
	SupplierAccountVo addSupplierAccount(SupplierAccountBo supplierAccount);

	/**
	 * 创建SupplierAccount
	 **/
	SupplierAccountVo addSupplierAccount(SupplierAccount supplierAccount);

	/**
	 * 更新SupplierAccount
	 **/
	SupplierAccountVo updateSupplierAccount(SupplierAccountBo supplierAccount);

	/**
	 * 删除SupplierAccount
	 **/
	void removeSupplierAccountById(int supplierAccountId);

	/**
	 * 根据ID得到SupplierAccountVo
	 **/
	SupplierAccountVo getSupplierAccountVoById(int supplierAccountId);

	/**
	 * 根据ID得到SupplierAccountListVo
	 **/
	SupplierAccountListVo getListVoById(int supplierAccountId);

	/**
	 * 分页查询: SupplierAccount
	 **/
	Page<SupplierAccountListVo> queryListVo(Pagination<SupplierAccount> query);

	/**
	 * 查询该供应商的账户
	 * 
	 * @return
	 */
	SupplierAccount getBySupplier(Integer supplierId);

	/**
	 * 查询该供应商的账户
	 * 
	 * @return
	 */
	SupplierAccountVo getVoBySupplier(Integer supplierId);

	/**
	 * 支付订单 更新供应商账户
	 */
	void updateSupplierAccountByPayOrder(SaleOrder saleOrder);

	/**
	 * 确认收货 更新供应商账户
	 */
	void updateSupplierAccountByConfirmReceipt(SaleOrder saleOrder);

	/**
	 * 确认退款 更新供应商账户
	 */
	void updateSupplierAccountByConfirmRefund(SaleOrder saleOrder);

	/**
	 * 提现发放 更新供应商账户
	 */
	void updateSupplierAccountByGrant(SupplierWithdraw supplierWithdraw);

}
