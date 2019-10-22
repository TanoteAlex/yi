/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.basic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.IntegralCash;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IntegralCashDao extends JpaRepository<IntegralCash, Integer>, JpaSpecificationExecutor<IntegralCash> {

	IntegralCash findFirstByStateAndDeletedOrderByCreateTimeDesc(Integer state, Integer deleted);

//	@Modifying
//	@Query(value = "update integral_cash set status=0 where user_id=?1\"",nativeQuery = true)
//	int addUserToOrg(Long orgId,Long userId);

}