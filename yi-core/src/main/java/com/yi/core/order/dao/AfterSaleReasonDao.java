/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.order.domain.entity.AfterSaleReason;

/**
 * 售后原因
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface AfterSaleReasonDao extends JpaRepository<AfterSaleReason, Integer>, JpaSpecificationExecutor<AfterSaleReason> {

	/**
	 * 查询售后原因
	 * 
	 * @param state
	 * @param deleted
	 * @return
	 */
	List<AfterSaleReason> findByStateAndDeletedOrderBySortAsc(Integer state, Integer deleted);

}