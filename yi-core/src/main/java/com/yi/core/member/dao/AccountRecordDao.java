/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yi.core.member.domain.entity.AccountRecord;

/**
 * 会员资金账户
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public interface AccountRecordDao extends JpaRepository<AccountRecord, Integer>, JpaSpecificationExecutor<AccountRecord> {

	/**
	 * 查询小区提成
	 * 
	 * @param memberId
	 * @return
	 */
	@Query(value = "select sum(a.tradeAmount) from AccountRecord a where a.tradeType='4' and a.member=?1")
	BigDecimal tradeAmount(Integer memberId);

	/**
	 * 查询 会员消费金额
	 * 
	 * @param memberId
	 * @param operateType
	 * @return
	 */
	@Query(value = "SELECT SUM(tradeAmount) FROM AccountRecord a WHERE a.member.id=?1 AND a.operateType=?2")
	String countByMemberIdAndOperateType(Integer memberId, Integer operateType);

	/**
	 * 查询会员账户记录
	 * 
	 * @param memberId
	 * @return
	 */
	List<AccountRecord> findByMemberId(Integer memberId);
}