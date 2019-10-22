/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yi.core.finance.domain.entity.SupplierWithdraw;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface SupplierWithdrawDao extends JpaRepository<SupplierWithdraw, Integer>, JpaSpecificationExecutor<SupplierWithdraw> {

	/**
	 * 查询申请状态供应商
	 * 
	 * @param state
	 * @return
	 */
	int countByState(int state);

	/**
	 * 查询供应商待结算资金
	 * 
	 * @param state
	 * @return
	 */

	@Query("select sum(applyAmount) from SupplierWithdraw where state=?1")
	BigDecimal sumApplyAmountByState(Integer state);

	/**
	 * 查询供应商后台提现
	 * 
	 * @param supplierId
	 * @return
	 */
	int countBySupplier_id(int supplierId);

}