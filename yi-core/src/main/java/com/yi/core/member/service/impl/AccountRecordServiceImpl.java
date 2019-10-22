/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service.impl;

import java.math.BigDecimal;
import java.util.Date;
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

import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.dao.AccountRecordDao;
import com.yi.core.member.domain.bo.AccountRecordBo;
import com.yi.core.member.domain.entity.AccountRecord;
import com.yi.core.member.domain.entity.AccountRecord_;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.Member_;
import com.yi.core.member.domain.simple.AccountRecordSimple;
import com.yi.core.member.domain.vo.AccountRecordListVo;
import com.yi.core.member.domain.vo.AccountRecordVo;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 会员资金账户记录
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class AccountRecordServiceImpl implements IAccountRecordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AccountRecordServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AccountRecordDao accountRecordDao;

	private EntityListVoBoSimpleConvert<AccountRecord, AccountRecordBo, AccountRecordVo, AccountRecordSimple, AccountRecordListVo> accountRecordConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccountRecord getById(int accountRecordId) {
		return accountRecordDao.getOne(accountRecordId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccountRecordVo getVoById(int accountRecordId) {
		return accountRecordConvert.toVo(this.accountRecordDao.getOne(accountRecordId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AccountRecordListVo getListVoById(int accountRecordId) {
		return accountRecordConvert.toListVo(this.accountRecordDao.getOne(accountRecordId));
	}

	@Override
	public AccountRecord addAccountRecord(AccountRecord accountRecord) {
		return accountRecordDao.save(accountRecord);
	}

	@Override
	public AccountRecordVo addAccountRecord(AccountRecordBo accountRecordBo) {
		return accountRecordConvert.toVo(accountRecordDao.save(accountRecordConvert.toEntity(accountRecordBo)));
	}

	@Override
	public AccountRecord updateAccountRecord(AccountRecord accountRecord) {
		AccountRecord dbAccountRecord = accountRecordDao.getOne(accountRecord.getId());
		AttributeReplication.copying(accountRecord, dbAccountRecord, AccountRecord_.operateType, AccountRecord_.serialNo, AccountRecord_.tradeAmount, AccountRecord_.tradeType,
				AccountRecord_.tradeTime, AccountRecord_.remark);
		return dbAccountRecord;
	}

	@Override
	public AccountRecordVo updateAccountRecord(AccountRecordBo accountRecordBo) {
		AccountRecord dbAccountRecord = this.updateAccountRecord(accountRecordConvert.toEntity(accountRecordBo));
		return accountRecordConvert.toVo(dbAccountRecord);
	}

	@Override
	public void removeAccountRecordById(int accountRecordId) {
		accountRecordDao.deleteById(accountRecordId);
	}

	@Override
	public Page<AccountRecord> query(Pagination<AccountRecord> query) {
		query.setEntityClazz(AccountRecord.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(AccountRecord_.tradeTime)));
		}));
		Page<AccountRecord> page = accountRecordDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AccountRecordListVo> queryListVo(Pagination<AccountRecord> query) {
		query.setEntityClazz(AccountRecord.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(AccountRecord_.tradeTime)));
		}));
		Page<AccountRecord> pages = accountRecordDao.findAll(query, query.getPageRequest());
		List<AccountRecordListVo> vos = accountRecordConvert.toListVos(pages.getContent());
		return new PageImpl<AccountRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AccountRecordListVo> queryListVoForApp(Pagination<AccountRecord> query) {
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// 排序
			list1.add(criteriaBuilder.desc(root.get(AccountRecord_.tradeTime)));
			Object memberId = query.getParams().get("member.id");
			if (memberId == null) {
				throw new BusinessException("请求参数不能为空");
			}
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AccountRecord_.member).get(Member_.id), memberId)));
		}));

		Page<AccountRecord> pages = accountRecordDao.findAll(query, query.getPageRequest());
		List<AccountRecordListVo> vos = accountRecordConvert.toListVos(pages.getContent());
		return new PageImpl<AccountRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.accountRecordConvert = new EntityListVoBoSimpleConvert<AccountRecord, AccountRecordBo, AccountRecordVo, AccountRecordSimple, AccountRecordListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<AccountRecord, AccountRecordVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AccountRecord, AccountRecordVo>(beanConvertManager) {
					@Override
					protected void postConvert(AccountRecordVo AccountRecordVo, AccountRecord AccountRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<AccountRecord, AccountRecordListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AccountRecord, AccountRecordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AccountRecordListVo AccountRecordListVo, AccountRecord AccountRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<AccountRecord, AccountRecordBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AccountRecord, AccountRecordBo>(beanConvertManager) {
					@Override
					protected void postConvert(AccountRecordBo AccountRecordBo, AccountRecord AccountRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<AccountRecordBo, AccountRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AccountRecordBo, AccountRecord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AccountRecord, AccountRecordSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AccountRecord, AccountRecordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AccountRecordSimple, AccountRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AccountRecordSimple, AccountRecord>(beanConvertManager) {
					@Override
					public AccountRecord convert(AccountRecordSimple AccountRecordSimple) throws BeansException {
						return accountRecordDao.getOne(AccountRecordSimple.getId());
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
	 * 根据交易类型 添加账户记录
	 */
	@Override
	public void addAccountRecordByTradeType(SaleOrder saleOrder, Member member, BigDecimal amount, MemberEnum tradeType, Member contributor) {
		if (member != null && amount != null && amount.compareTo(BigDecimal.ZERO) > 0 && tradeType != null) {
			AccountRecord accountRecord = new AccountRecord();
			accountRecord.setMember(member);
			accountRecord.setSaleOrder(saleOrder);
			accountRecord.setContributor(Optional.ofNullable(contributor).map(e -> e.getNickname()).orElse(""));
			accountRecord.setAvater(Optional.ofNullable(contributor).map(e -> e.getAvater()).orElse(""));
			accountRecord.setSerialNo(NumberGenerateUtils.generateSerialNo());
			switch (tradeType) {
			case TRADE_TYPE_COMMISSION:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_INCOME.getCode());
				break;
			case TRADE_TYPE_ONLINE_PAYMENT:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_EXPENDITURE.getCode());
				break;
			case TRADE_TYPE_WITHDRAW_CASH:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_INCOME.getCode());
				break;
			case TRADE_TYPE_COMMUNITY_COMMISSION:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_INCOME.getCode());
				break;
			case TRADE_TYPE_GIFT_PAYMENT:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_EXPENDITURE.getCode());
				break;
			case TRADE_TYPE_REFUND:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_INCOME.getCode());
				break;
			case TRADE_TYPE_RETURN_COMMISSION:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_EXPENDITURE.getCode());
				break;
			case TRADE_TYPE_RECHARGE:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_INCOME.getCode());
				break;
			case TRADE_TYPE_BALANCE_PAYMENT:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_EXPENDITURE.getCode());
				break;
			case TRADE_TYPE_RETURN_BALANCE:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_INCOME.getCode());
				break;
			case TRADE_TYPE_SUBTRACT:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_EXPENDITURE.getCode());
				break;
			case TRADE_TYPE_INTEGRAL_CASH:
				accountRecord.setOperateType(MemberEnum.OPERATE_TYPE_EXPENDITURE.getCode());
				break;
			default:
				break;
			}
			accountRecord.setTradeAmount(amount);
			accountRecord.setTradeType(tradeType.getCode());
			accountRecord.setRemark(tradeType.getValue());
			accountRecord.setTradeTime(new Date());
			accountRecordDao.save(accountRecord);
		}
	}

}
