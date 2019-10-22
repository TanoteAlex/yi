/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.IntegralRecord;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IntegralRecordDao extends JpaRepository<IntegralRecord, Integer>, JpaSpecificationExecutor<IntegralRecord> {

	List<IntegralRecord> findByMemberId(int memberId);

	/**
	 * 
	 * @param memberId
	 * @param prizeId
	 * @param operateIntegral
	 * @return
	 */
	int countByMember_idAndPrizeIdAndOperateIntegral(Integer memberId, Integer prizeId, Integer operateIntegral);
}