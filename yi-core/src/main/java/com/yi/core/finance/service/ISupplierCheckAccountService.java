/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.finance.domain.bo.SupplierCheckAccountBo;
import com.yi.core.finance.domain.entity.SupplierCheckAccount;
import com.yi.core.finance.domain.vo.SupplierCheckAccountListVo;
import com.yi.core.finance.domain.vo.SupplierCheckAccountVo;
import com.yihz.common.persistence.Pagination;

/**
 * 供应商对账单
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ISupplierCheckAccountService {

	/**
	 * 分页查询: SupplierCheckAccount
	 * 
	 * @param query
	 * @return
	 */
	Page<SupplierCheckAccount> query(Pagination<SupplierCheckAccount> query);

	/**
	 * 分页查询: SupplierCheckAccountListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<SupplierCheckAccountListVo> queryListVo(Pagination<SupplierCheckAccount> query);

	/**
	 * 根据ID得到SupplierCheckAccount
	 * 
	 * @param supplierCheckAccountId
	 * @return
	 */
	SupplierCheckAccount getById(int supplierCheckAccountId);

	/**
	 * 根据ID得到SupplierCheckAccountVo
	 * 
	 * @param supplierCheckAccountId
	 * @return
	 */
	SupplierCheckAccountVo getVoById(int supplierCheckAccountId);

	/**
	 * 根据ID得到SupplierCheckAccountListVo
	 * 
	 * @param supplierCheckAccountId
	 * @return
	 */
	SupplierCheckAccountListVo getListVoById(int supplierCheckAccountId);

	/**
	 * 根据Entity创建SupplierCheckAccount
	 * 
	 * @param supplierCheckAccount
	 * @return
	 */
	SupplierCheckAccountVo addSupplierCheckAccount(SupplierCheckAccount supplierCheckAccount);

	/**
	 * 根据BO创建SupplierCheckAccount
	 * 
	 * @param supplierCheckAccountBo
	 * @return
	 */
	SupplierCheckAccountListVo addSupplierCheckAccount(SupplierCheckAccountBo supplierCheckAccountBo);

	/**
	 * 根据Entity更新SupplierCheckAccount
	 * 
	 * @param supplierCheckAccount
	 * @return
	 */
	SupplierCheckAccount updateSupplierCheckAccount(SupplierCheckAccount supplierCheckAccount);

	/**
	 * 根据BO更新SupplierCheckAccount
	 * 
	 * @param supplierCheckAccountBo
	 * @return
	 */
	SupplierCheckAccountListVo updateSupplierCheckAccount(SupplierCheckAccountBo supplierCheckAccountBo);

	/**
	 * 删除SupplierCheckAccount
	 * 
	 * @param supplierCheckAccountId
	 */
	void removeById(int supplierCheckAccountId);

	/**
	 * 自动生成对账单
	 */
	void autoCheckAccountByTask();

	/**
	 * 获取导出的对账单明细
	 * 
	 * @param query
	 * @return
	 */
	List<SupplierCheckAccount> getExportCheckAccounts(Pagination<SupplierCheckAccount> query);

}
