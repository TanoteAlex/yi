/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.member.domain.entity.Account;

/**
 * 会员资金账户
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface AccountDao extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

	/**
	 * 查询会员的资金账户
	 * 
	 * @param memberId
	 * @return
	 */
	Account findByMember_id(Integer memberId);
}
