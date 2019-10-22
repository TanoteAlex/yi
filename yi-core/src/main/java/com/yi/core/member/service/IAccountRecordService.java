/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import com.yi.core.member.MemberEnum;
import com.yi.core.member.domain.bo.AccountRecordBo;
import com.yi.core.member.domain.entity.AccountRecord;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.vo.AccountRecordListVo;
import com.yi.core.member.domain.vo.AccountRecordVo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;

/**
 * 会员资金账户记录
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
public interface IAccountRecordService {

	/**
	 * 根据ID得到AccountRecord
	 * 
	 * @param accountRecordId
	 * @return
	 */
	AccountRecord getById(int accountRecordId);

	/**
	 * 根据ID得到AccountRecordVo
	 * 
	 * @param accountRecordId
	 * @return
	 */
	AccountRecordVo getVoById(int accountRecordId);

	/**
	 * 根据ID得到AccountRecordListVo
	 * 
	 * @param accountRecordId
	 * @return
	 */
	AccountRecordListVo getListVoById(int accountRecordId);

	/**
	 * 根据Entity创建AccountRecord
	 * 
	 * @param accountRecord
	 * @return
	 */
	AccountRecord addAccountRecord(AccountRecord accountRecord);

	/**
	 * 根据BO创建AccountRecord
	 * 
	 * @param accountRecordBo
	 * @return
	 */
	AccountRecordVo addAccountRecord(AccountRecordBo accountRecordBo);

	/**
	 * 根据Entity更新AccountRecord
	 * 
	 * @param accountRecord
	 * @return
	 */
	AccountRecord updateAccountRecord(AccountRecord accountRecord);

	/**
	 * 根据BO更新AccountRecord
	 * 
	 * @param accountRecord
	 * @return
	 */
	AccountRecordVo updateAccountRecord(AccountRecordBo accountRecord);

	/**
	 * 删除AccountRecord
	 * 
	 * @param accountRecordId
	 */
	void removeAccountRecordById(int accountRecordId);

	/**
	 * 分页查询: AccountRecord
	 * 
	 * @param query
	 * @return
	 */
	Page<AccountRecord> query(Pagination<AccountRecord> query);

	/**
	 * 分页查询: AccountRecordListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<AccountRecordListVo> queryListVo(Pagination<AccountRecord> query);

	/**
	 * 移动端查询会员资金账户记录
	 * 
	 * @param query
	 * @return
	 */
	Page<AccountRecordListVo> queryListVoForApp(Pagination<AccountRecord> query);

	/**
	 * 根据交易类型 增加账户记录
	 * 
	 * @param member
	 * @param money
	 * @param tradeType
	 */
	void addAccountRecordByTradeType(SaleOrder saleOrder, Member member, BigDecimal amount, MemberEnum tradeType, Member contributor);
}
