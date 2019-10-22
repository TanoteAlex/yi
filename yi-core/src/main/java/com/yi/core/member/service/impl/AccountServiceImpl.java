/*
* Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.domain.entity.IntegralCash;
import com.yi.core.basic.domain.entity.IntegralTask;
import com.yi.core.basic.service.IIntegralCashService;
import com.yi.core.basic.service.IIntegralRecordService;
import com.yi.core.basic.service.IIntegralTaskService;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.dao.AccountDao;
import com.yi.core.member.domain.bo.AccountBo;
import com.yi.core.member.domain.entity.Account;
import com.yi.core.member.domain.entity.Account_;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.simple.AccountSimple;
import com.yi.core.member.domain.vo.AccountListVo;
import com.yi.core.member.domain.vo.AccountVo;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.member.service.IAccountService;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 会员资金账户
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class AccountServiceImpl implements IAccountService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AccountDao accountDao;

	@Resource
	private IAccountRecordService accountRecordService;

	@Resource
	private IIntegralRecordService integralRecordService;

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private IIntegralCashService integralCashService;

	@Resource
	private IIntegralTaskService integralTaskService;

	private EntityListVoBoSimpleConvert<Account, AccountBo, AccountVo, AccountSimple, AccountListVo> accountConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Account getById(int accountId) {
		return accountDao.getOne(accountId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccountVo getVoById(int accountId) {
		return accountConvert.toVo(this.accountDao.getOne(accountId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccountListVo getListVoById(int accountId) {
		return accountConvert.toListVo(this.accountDao.getOne(accountId));
	}

	@Override
	public Account addAccount(Account account) {
		return accountDao.save(account);
	}

	@Override
	public AccountVo addAccount(AccountBo accountBo) {
		return accountConvert.toVo(accountDao.save(accountConvert.toEntity(accountBo)));
	}

	@Override
	public Account updateAccount(Account account) {
		Account dbAccount = accountDao.getOne(account.getId());
		AttributeReplication.copying(account, dbAccount, Account_.orderQuantity, Account_.consumeAmount, Account_.balance, Account_.freezeAmount, Account_.totalIntegral,
				Account_.availableIntegral, Account_.totalCommission, Account_.cashableCommission, Account_.cashedCommission, Account_.unsettledCommission, Account_.remark);
		return dbAccount;
	}

	@Override
	public AccountVo updateAccount(AccountBo accountBo) {
		Account dbAccount = this.updateAccount(accountConvert.toEntity(accountBo));
		return accountConvert.toVo(dbAccount);
	}

	@Override
	public void removeAccountById(int accountId) {
		accountDao.deleteById(accountId);
	}

	@Override
	public Page<Account> query(Pagination<Account> query) {
		query.setEntityClazz(Account.class);
		Page<Account> page = accountDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AccountListVo> queryListVo(Pagination<Account> query) {

		Page<Account> pages = this.query(query);

		List<AccountListVo> vos = accountConvert.toListVos(pages.getContent());
		return new PageImpl<AccountListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.accountConvert = new EntityListVoBoSimpleConvert<Account, AccountBo, AccountVo, AccountSimple, AccountListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Account, AccountVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Account, AccountVo>(beanConvertManager) {
					@Override
					protected void postConvert(AccountVo AccountVo, Account Account) {

					}
				};
			}

			@Override
			protected BeanConvert<Account, AccountListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Account, AccountListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AccountListVo AccountListVo, Account Account) {

					}
				};
			}

			@Override
			protected BeanConvert<Account, AccountBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Account, AccountBo>(beanConvertManager) {
					@Override
					protected void postConvert(AccountBo AccountBo, Account Account) {

					}
				};
			}

			@Override
			protected BeanConvert<AccountBo, Account> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AccountBo, Account>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Account, AccountSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Account, AccountSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AccountSimple, Account> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AccountSimple, Account>(beanConvertManager) {
					@Override
					public Account convert(AccountSimple AccountSimple) throws BeansException {
						return accountDao.getOne(AccountSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	/**
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccountVo getAccountVoByMember(Integer memberId) {
		return accountConvert.toVo(accountDao.findByMember_id(memberId));
	}

	/**
	 * 更新会员佣金并记录
	 */
	@Override
	public void updateMemberCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor) {
		if (memberId != null && commission != null && commission.compareTo(BigDecimal.ZERO) > 0 && tradeType != null) {
			Account dbAccount = accountDao.findByMember_id(memberId);
			if (dbAccount != null) {
				// 冻结金额
				dbAccount.setFreezeAmount(Optional.ofNullable(dbAccount.getFreezeAmount()).orElse(BigDecimal.ZERO).add(commission));
				// 总佣金
				dbAccount.setTotalCommission(Optional.ofNullable(dbAccount.getTotalCommission()).orElse(BigDecimal.ZERO).add(commission));
				// 未结算佣金
				dbAccount.setUnsettledCommission(Optional.ofNullable(dbAccount.getUnsettledCommission()).orElse(BigDecimal.ZERO).add(commission));
				// 用户资金账户记录
				accountRecordService.addAccountRecordByTradeType(saleOrder, dbAccount.getMember(), commission, tradeType, contributor);
			}
		}
	}

	/**
	 * 更新会员可提现佣金并记录
	 */
	@Override
	public void updateCashableCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor) {
		if (memberId != null && commission != null && commission.compareTo(BigDecimal.ZERO) > 0 && tradeType != null) {
			Account dbAccount = accountDao.findByMember_id(memberId);
			if (dbAccount != null) {
				// 冻结金额
				// dbAccount.setFreezeAmount(Optional.ofNullable(dbAccount.getFreezeAmount()).orElse(BigDecimal.ZERO).add(commission));
				// 总佣金
				// dbAccount.setTotalCommission(Optional.ofNullable(dbAccount.getTotalCommission()).orElse(BigDecimal.ZERO).add(commission));
				// 可提现佣金
				dbAccount.setCashableCommission(Optional.ofNullable(dbAccount.getCashableCommission()).orElse(BigDecimal.ZERO).add(commission));
				// 未结算佣金
				dbAccount.setUnsettledCommission(Optional.ofNullable(dbAccount.getUnsettledCommission()).orElse(BigDecimal.ZERO).subtract(commission));
				// 用户资金账户记录
				accountRecordService.addAccountRecordByTradeType(saleOrder, dbAccount.getMember(), commission, tradeType, contributor);
			}
		}
	}

	/**
	 * 更新会员未结算佣金并记录
	 */
	@Override
	public void updateUnsettledCommission(SaleOrder saleOrder, Integer memberId, BigDecimal commission, MemberEnum tradeType, Member contributor) {
		if (memberId != null && commission != null && commission.compareTo(BigDecimal.ZERO) > 0 && tradeType != null) {
			Account dbAccount = accountDao.findByMember_id(memberId);
			if (dbAccount != null) {
				// 冻结金额
				// dbAccount.setFreezeAmount(Optional.ofNullable(dbAccount.getFreezeAmount()).orElse(BigDecimal.ZERO).add(commission));
				// 总佣金
				dbAccount.setTotalCommission(Optional.ofNullable(dbAccount.getTotalCommission()).orElse(BigDecimal.ZERO).subtract(commission));
				// 可提现佣金
				// dbAccount.setCashableCommission(Optional.ofNullable(dbAccount.getCashableCommission()).orElse(BigDecimal.ZERO).add(commission));
				// 未结算佣金
				dbAccount.setUnsettledCommission(Optional.ofNullable(dbAccount.getUnsettledCommission()).orElse(BigDecimal.ZERO).subtract(commission));
				// 用户资金账户记录
				accountRecordService.addAccountRecordByTradeType(saleOrder, dbAccount.getMember(), commission, tradeType, contributor);
			}
		}

	}

	/**
	 * 充值
	 */
	@Override
	public void rechargeBalance(Member member, BigDecimal amount) {
		if (member != null && member.getId() > 0 && amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
			Account dbAccount = accountDao.findByMember_id(member.getId());
			// 用户资金账户记录
			if (dbAccount != null) {
				// 账户总金额 = 原先账户总金额 + 充值金额
				dbAccount.setTotalAmount(Optional.ofNullable(dbAccount.getTotalAmount()).orElse(BigDecimal.ZERO).add(amount));
				// 账户余额 = 原先账户余额 + 充值金额
				dbAccount.setBalance(Optional.ofNullable(dbAccount.getBalance()).orElse(BigDecimal.ZERO).add(amount));
				// 资金账户记录
				accountRecordService.addAccountRecordByTradeType(null, dbAccount.getMember(), amount, MemberEnum.TRADE_TYPE_RECHARGE, null);
			}
		}
	}

	/**
	 * 撤销储值券 扣减余额
	 */
	@Override
	public void subtractBalance(Member member, BigDecimal amount) {
		if (member != null && member.getId() > 0 && amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
			Account dbAccount = accountDao.findByMember_id(member.getId());
			// 用户资金账户记录
			if (dbAccount != null) {
				// 账户总金额 = 原先账户总金额 - 扣减金额
				dbAccount.setTotalAmount(Optional.ofNullable(dbAccount.getTotalAmount()).orElse(BigDecimal.ZERO).add(amount));
				// 账户余额 = 原先账户余额 - 扣减金额
				dbAccount.setBalance(Optional.ofNullable(dbAccount.getBalance()).orElse(BigDecimal.ZERO).add(amount));
				// 资金账户记录
				accountRecordService.addAccountRecordByTradeType(null, dbAccount.getMember(), amount, MemberEnum.TRADE_TYPE_SUBTRACT, null);
			}
		}
	}

	/**
	 * 支付订单后 修正账户余额
	 * 
	 * @param saleOrder
	 * @param member
	 */
	@Override
	public void useBalanceByAfterPayment(Member member, BigDecimal amount, SaleOrder saleOrder) {
		if (member != null && member.getId() > 0 && amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
			Account dbAccount = accountDao.findByMember_id(member.getId());
			// 用户资金账户记录
			if (dbAccount != null) {
				// 账户余额 = 原先账户余额 - 订单使用金额
				dbAccount.setBalance(Optional.ofNullable(dbAccount.getBalance()).orElse(BigDecimal.ZERO).subtract(amount));
				// 使用金额 = 原先使用金额 + 订单使用金额
				dbAccount.setUseAmount(Optional.ofNullable(dbAccount.getUseAmount()).orElse(BigDecimal.ZERO).add(amount));
				// 资金账户记录
				accountRecordService.addAccountRecordByTradeType(saleOrder, dbAccount.getMember(), amount, MemberEnum.TRADE_TYPE_BALANCE_PAYMENT, null);
			}
		}
	}

	/**
	 * 支付订单后 修正账户积分
	 * 
	 * @param saleOrder
	 * @param member
	 */
	@Override
	public void useIntegralCashByAfterPayment(Member member, BigDecimal amount, SaleOrder saleOrder) {
		if (member != null && member.getId() > 0 && amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
			Account dbAccount = accountDao.findByMember_id(member.getId());
			IntegralCash dbIntegralCash = integralCashService.getLatestIntegralCash();
			// 用户资金账户记录
			if (dbAccount != null && dbIntegralCash != null) {
				// 使用的积分 如：0.91/1 * 100
				int usedIntegral = amount.multiply(BigDecimal.valueOf(dbIntegralCash.getIntegral())).divide(dbIntegralCash.getCash()).intValue();
				// 使用积分 = 原先使用积分 + 订单使用积分
				dbAccount.setUsedIntegral(dbAccount.getUsedIntegral() + usedIntegral);
				// 可用积分 = 原先可用积分- 订单使用积分
				dbAccount.setAvailableIntegral(dbAccount.getAvailableIntegral() - usedIntegral);
				// 资金账户记录
				accountRecordService.addAccountRecordByTradeType(saleOrder, dbAccount.getMember(), amount, MemberEnum.TRADE_TYPE_INTEGRAL_CASH, null);
				LOG.error("订单ID：{}，使用积分为：{}", saleOrder.getId(), usedIntegral);
				// 积分使用记录
				integralRecordService.addByOrder(member, usedIntegral, BasicEnum.OPERATE_TYPE_SUBTRACT);
			}
		}
	}

	/**
	 * 1，关闭超时订单</br>
	 * 2，退款退货</br>
	 * 3，关闭未成团订单</br>
	 * 等退回使用的余额
	 * 
	 * @param saleOrder
	 * @param member
	 */
	@Override
	@Transactional
	public void returnBalanceByRefund(Member member, BigDecimal amount, SaleOrder saleOrder) {
		if (member != null && member.getId() > 0 && amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
			Account dbAccount = accountDao.findByMember_id(member.getId());
			// 用户资金账户记录
			if (dbAccount != null) {
				// 账户余额 = 原先账户余额 + 订单使用金额
				dbAccount.setBalance(Optional.ofNullable(dbAccount.getBalance()).orElse(BigDecimal.ZERO).add(amount));
				// 使用金额 = 原先使用金额 - 订单使用金额
				dbAccount.setUseAmount(Optional.ofNullable(dbAccount.getUseAmount()).orElse(BigDecimal.ZERO).subtract(amount));
				// 资金账户记录
				accountRecordService.addAccountRecordByTradeType(saleOrder, dbAccount.getMember(), amount, MemberEnum.TRADE_TYPE_RETURN_BALANCE, null);
			}
		}
	}

	/**
	 * 佣金提至余额
	 */
	@Override
	public boolean withdrawCommission(Integer memberId, BigDecimal withdrawAmount) {
		if (memberId != null && withdrawAmount != null && withdrawAmount.compareTo(BigDecimal.ZERO) > 0) {
			Account dbAccount = accountDao.findByMember_id(memberId);
			// 用户资金账户记录
			if (dbAccount != null) {
				if (withdrawAmount.compareTo(dbAccount.getCashableCommission()) > 0) {
					throw new BusinessException("提现金额不能大于可提现金额");
				}
				// 账户总金额 = 原先账户总金额 + 佣金提取金额
				dbAccount.setTotalAmount(Optional.ofNullable(dbAccount.getTotalAmount()).orElse(BigDecimal.ZERO).add(withdrawAmount));
				// 账户余额 = 原先账户余额 + 佣金提取金额
				dbAccount.setBalance(Optional.ofNullable(dbAccount.getBalance()).orElse(BigDecimal.ZERO).add(withdrawAmount));
				// 可提现佣金= 原先可提现金额- 提取金额
				dbAccount.setCashableCommission(Optional.ofNullable(dbAccount.getCashableCommission()).orElse(BigDecimal.ZERO).subtract(withdrawAmount));
				// 已提现佣金= 原先已提现金额+ 提取金额
				dbAccount.setCashedCommission(Optional.ofNullable(dbAccount.getCashedCommission()).orElse(BigDecimal.ZERO).add(withdrawAmount));
				// 资金账户记录
				accountRecordService.addAccountRecordByTradeType(null, dbAccount.getMember(), withdrawAmount, MemberEnum.TRADE_TYPE_WITHDRAW_CASH, null);
			}
		}
		return false;
	}

	/**
	 * 领取邀请有礼奖品
	 */
	@Override
	public void receiveInvitePrize(Integer memberId, int integral, Integer prizeId) {
		if (memberId != null && integral > 0) {
			boolean flag = integralRecordService.checkReceivePrize(memberId, integral, prizeId);
			if (flag) {
				throw new BusinessException("您已领取过该奖品，请勿重复领取");
			}
			Account dbAccount = accountDao.findByMember_id(memberId);
			// 用户账户
			if (dbAccount != null) {
				// 积分 = 原先积分 + 领取积分
				dbAccount.setTotalIntegral(dbAccount.getTotalIntegral() + integral);
				// 可用积分 = 原先可用积分 + 领取积分
				dbAccount.setAvailableIntegral(dbAccount.getAvailableIntegral() + integral);
				// 积分记录
				integralRecordService.addByReceivePrize(dbAccount.getMember(), integral, prizeId);
			}
		}
	}

	/**
	 * 根据任务类型 更新积分
	 */
	@Override
	public void updateByTaskType(Integer memberId, BasicEnum taskType) {
		if (memberId != null && taskType != null) {
			// 会员资产账户
			Account dbAccount = accountDao.findByMember_id(memberId);
			// 获取积分任务
			IntegralTask dbIntegralTask = integralTaskService.getIntegralTaskByType(taskType.getCode());
			if (dbAccount != null && dbIntegralTask != null && BasicEnum.STATE_ENABLE.getCode().equals(dbIntegralTask.getState())) {
				// 总积分 = 原总积分 + 领取积分
				dbAccount.setTotalIntegral(dbAccount.getTotalIntegral() + dbIntegralTask.getGrowthValue());
				// 可用积分 = 原可用积分 + 领取积分
				dbAccount.setAvailableIntegral(dbAccount.getAvailableIntegral() + dbIntegralTask.getGrowthValue());
				// 增加积分记录
				integralRecordService.addByTaskType(dbAccount.getMember(), dbIntegralTask);
			}
		}
	}

	@Override
	public Account updateByOrder(Integer memberId, int integral) {
		if (memberId != null && integral > 0) {
			// 会员资产账户
			Account dbAccount = accountDao.findByMember_id(memberId);
			if (dbAccount != null) {
				// 总积分 = 原总积分 + 领取积分
				dbAccount.setTotalIntegral(dbAccount.getTotalIntegral() + integral);
				// 可用积分 = 原可用积分 + 领取积分
				dbAccount.setAvailableIntegral(dbAccount.getAvailableIntegral() + integral);
				// 增加积分记录
				integralRecordService.addByOrder(dbAccount.getMember(), integral, BasicEnum.OPERATE_TYPE_ADD);
				return dbAccount;
			}
		}
		return null;
	}

	@Override
	public Account updateByRewrd(Integer memberId, int integral) {
		if (memberId != null && integral > 0) {
			// 会员资产账户
			Account dbAccount = accountDao.findByMember_id(memberId);
			if (dbAccount != null) {
				// 总积分 = 原总积分 + 领取积分
				dbAccount.setTotalIntegral(dbAccount.getTotalIntegral() + integral);
				// 可用积分 = 原可用积分 + 领取积分
				dbAccount.setAvailableIntegral(dbAccount.getAvailableIntegral() + integral);
				// 增加积分记录
				integralRecordService.addByRewrd(dbAccount.getMember(), integral);
				return dbAccount;
			}
		}
		return null;
	}

}
