/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import com.yi.core.basic.BasicEnum;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.domain.bo.AccountBo;
import com.yi.core.member.domain.entity.Account;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.vo.AccountListVo;
import com.yi.core.member.domain.vo.AccountVo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;

/**
 * 会员资金账户
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
public interface IAccountService {

	/**
	 * 根据ID得到Account
	 * 
	 * @param accountId
	 * @return
	 */
	Account getById(int accountId);

	/**
	 * 根据ID得到AccountVo
	 * 
	 * @param accountId
	 * @return
	 */
	AccountVo getVoById(int accountId);

	/**
	 * 根据ID得到AccountListVo
	 * 
	 * @param accountId
	 * @return
	 */
	AccountListVo getListVoById(int accountId);

	/**
	 * 根据Entity创建Account
	 * 
	 * @param account
	 * @return
	 */
	Account addAccount(Account account);

	/**
	 * 根据BO创建Account
	 * 
	 * @param accountBo
	 * @return
	 */
	AccountVo addAccount(AccountBo accountBo);

	/**
	 * 根据Entity更新Account
	 * 
	 * @param account
	 * @return
	 */
	Account updateAccount(Account account);

	/**
	 * 根据BO更新Account
	 * 
	 * @param accountBo
	 * @return
	 */
	AccountVo updateAccount(AccountBo accountBo);

	/**
	 * 删除Account
	 * 
	 * @param accountId
	 */
	void removeAccountById(int accountId);

	/**
	 * 分页查询: Account
	 * 
	 * @param query
	 * @return
	 */
	Page<Account> query(Pagination<Account> query);

	/**
	 * 分页查询: AccountListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<AccountListVo> queryListVo(Pagination<Account> query);

	/**
	 * 通过会员查询会员账户信息
	 * 
	 * @return
	 */
	AccountVo getAccountVoByMember(Integer memberId);

	/**
	 * 更新会员佣金并记录
	 * 
	 * @param memberId
	 * @param commission
	 * @param tradeType
	 */
	void updateMemberCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor);

	/**
	 * 更新可提现佣金
	 * 
	 * @param saleOrder
	 * @param memberId
	 * @param commission
	 * @param tradeType
	 * @param contributor
	 */
	void updateCashableCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor);

	/**
	 * 更新未结算佣金
	 * 
	 * @param saleOrder
	 * @param memberId
	 * @param commission
	 * @param tradeType
	 * @param contributor
	 */
	void updateUnsettledCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor);

	/**
	 * 充值余额
	 * 
	 * @param member
	 * @param amount
	 */
	void rechargeBalance(Member member, BigDecimal amount);

	/**
	 * 撤销储值券 扣减余额
	 * 
	 * @param member
	 * @param amount
	 */
	void subtractBalance(Member member, BigDecimal amount);

	/**
	 * 支付后使用余额
	 * 
	 * @param member
	 * @param amount
	 * 
	 */
	void useBalanceByAfterPayment(Member member, BigDecimal amount, SaleOrder saleOrder);

	/**
	 * 支付后使用积分抵现
	 * 
	 * @param member
	 * @param amount
	 * 
	 */
	void useIntegralCashByAfterPayment(Member member, BigDecimal amount, SaleOrder saleOrder);

	/**
	 * 1，关闭超时订单</br>
	 * 2，退款退货</br>
	 * 3，关闭未成团订单</br>
	 * 等退回使用的余额
	 *
	 * @param member
	 * @param amount
	 */
	void returnBalanceByRefund(Member member, BigDecimal amount, SaleOrder saleOrder);

	/**
	 * 佣金提至余额
	 * 
	 * @param memberId
	 * @param withdrawAmount
	 * @return
	 */
	boolean withdrawCommission(Integer memberId, BigDecimal withdrawAmount);

	/**
	 * 领取邀请有礼奖品
	 * 
	 * @param memberId
	 * @param integral
	 * @param prizeId
	 */
	void receiveInvitePrize(Integer memberId, int integral, Integer prizeId);

	/**
	 * 根据积分任务 更新账户积分
	 * 
	 * @param memberId
	 * @param taskType
	 */
	void updateByTaskType(Integer memberId, BasicEnum taskType);

	/**
	 * 下单时 更新账户积分
	 * 
	 * @param memberId
	 * @param integral
	 */
	Account updateByOrder(Integer memberId, int integral);

	/**
	 * 领取奖励时 更新账号积分
	 * 
	 * @param memberId
	 * @param integral
	 */
	Account updateByRewrd(Integer memberId, int integral);

}
