/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.domain.vo.RewardVo;
import com.yi.core.activity.service.IRewardService;
import com.yi.core.basic.BasicEnum;
import com.yi.core.member.dao.SignTimeDao;
import com.yi.core.member.domain.bo.SignTimeBo;
import com.yi.core.member.domain.entity.SignTime;
import com.yi.core.member.domain.entity.SignTime_;
import com.yi.core.member.domain.simple.SignTimeSimple;
import com.yi.core.member.domain.vo.SignTimeListVo;
import com.yi.core.member.domain.vo.SignTimeVo;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.member.service.ISignTimeService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.date.DateUtils;

/**
 * 签到时间
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SignTimeServiceImpl implements ISignTimeService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SignTimeServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SignTimeDao signTimeDao;

	@Resource
	private IMemberService memberService;

	@Resource
	private IRewardService rewardService;

	@Resource
	private IAccountService accountService;

	private EntityListVoBoSimpleConvert<SignTime, SignTimeBo, SignTimeVo, SignTimeSimple, SignTimeListVo> signTimeConvert;

	@Override
	public SignTime getSignTimeById(int signTimeId) {
		return signTimeDao.getOne(signTimeId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SignTimeVo getSignTimeVoById(int signTimeId) {

		return signTimeConvert.toVo(this.signTimeDao.getOne(signTimeId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SignTimeListVo getSignTimeListVoById(int signTimeId) {
		return signTimeConvert.toListVo(this.signTimeDao.getOne(signTimeId));
	}

	@Override
	public SignTimeVo addSignTime(SignTime signTime) {
		signTime.setSignTime(new Date());
		return signTimeConvert.toVo(signTimeDao.save(signTime));
	}

	@Override
	public SignTimeListVo addSignTime(SignTimeBo signTimeBo) {
		signTimeBo.setSignTime(new Date());
		return signTimeConvert.toListVo(signTimeDao.save(signTimeConvert.toEntity(signTimeBo)));
	}

	@Override
	public SignTimeVo updateSignTime(SignTime signTime) {

		SignTime dbSignTime = signTimeDao.getOne(signTime.getId());
		AttributeReplication.copying(signTime, dbSignTime, SignTime_.member, SignTime_.signTime, SignTime_.signDays);
		return signTimeConvert.toVo(dbSignTime);
	}

	@Override
	public SignTimeListVo updateSignTime(SignTimeBo signTimeBo) {
		SignTime dbSignTime = signTimeDao.getOne(signTimeBo.getId());
		AttributeReplication.copying(signTimeBo, dbSignTime, SignTime_.member, SignTime_.signTime, SignTime_.signDays);
		return signTimeConvert.toListVo(dbSignTime);
	}

	@Override
	public void removeSignTimeById(int signTimeId) {
		signTimeDao.deleteById(signTimeId);
	}

	@Override
	public Page<SignTime> query(Pagination<SignTime> query) {
		query.setEntityClazz(SignTime.class);
		Page<SignTime> page = signTimeDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SignTimeListVo> queryListVo(Pagination<SignTime> query) {

		Page<SignTime> pages = this.query(query);

		List<SignTimeListVo> vos = signTimeConvert.toListVos(pages.getContent());
		return new PageImpl<SignTimeListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.signTimeConvert = new EntityListVoBoSimpleConvert<SignTime, SignTimeBo, SignTimeVo, SignTimeSimple, SignTimeListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<SignTime, SignTimeVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SignTime, SignTimeVo>(beanConvertManager) {
					@Override
					protected void postConvert(SignTimeVo SignTimeVo, SignTime SignTime) {

					}
				};
			}

			@Override
			protected BeanConvert<SignTime, SignTimeListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SignTime, SignTimeListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SignTimeListVo SignTimeListVo, SignTime SignTime) {

					}
				};
			}

			@Override
			protected BeanConvert<SignTime, SignTimeBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SignTime, SignTimeBo>(beanConvertManager) {
					@Override
					protected void postConvert(SignTimeBo SignTimeBo, SignTime SignTime) {

					}
				};
			}

			@Override
			protected BeanConvert<SignTimeBo, SignTime> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SignTimeBo, SignTime>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SignTime, SignTimeSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SignTime, SignTimeSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SignTimeSimple, SignTime> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SignTimeSimple, SignTime>(beanConvertManager) {
					@Override
					public SignTime convert(SignTimeSimple SignTimeSimple) throws BeansException {
						return signTimeDao.getOne(SignTimeSimple.getId());
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
	 * 查询用户签到信息
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SignTimeVo getSignInfo(int memberId) {
		// 查询是否有签到记录
		SignTime dbSignTime = signTimeDao.findByMemberId(memberId);
		// 查询签到奖励
		List<RewardVo> rewards = rewardService.getByRewardType(ActivityEnum.REWARD_TYPE_SIGN);
		if (dbSignTime != null) {
			SignTimeVo signTimeVo = signTimeConvert.toVo(dbSignTime);
			// 检查今天是否签到过 -只对比 年月日
			boolean flag = DateUtils.isSameDay(dbSignTime.getSignTime(), new Date());
			if (flag) {
				signTimeVo.setSigned(true);
			} else {
				// 如果今天没有签到过 检查上一次签到是否是昨天签到 考虑跨年问题
				int days = this.getApartDays(dbSignTime.getSignTime(), new Date());
				if (days != 1) {// 非连续签到 签到天数从新计算
					signTimeVo.setSignDays(0);
				}
				signTimeVo.setSigned(false);
			}
			signTimeVo.setRewards(rewards);
			return signTimeVo;
		} else {
			SignTimeVo signTimeVo = new SignTimeVo();
			signTimeVo.setSignDays(0);
			signTimeVo.setSigned(false);
			signTimeVo.setRewards(rewards);
			return signTimeVo;
		}
	}

	@Override
	public SignTimeVo clickSign(int memberId) {
		// 查询是否有签到记录
		SignTime signTime = signTimeDao.findByMemberId(memberId);
		// 有签到记录 计算是否连续签到
		if (signTime != null) {
			// 校验今天是否签到过 -只对比 年月日
			boolean flag = DateUtils.isSameDay(signTime.getSignTime(), new Date());
			if (flag) {
				throw new RuntimeException("已签到");
			}
			// 如果今天没有签到过 检查上一次签到是否是昨天签到 考虑跨年问题
			int days = this.getApartDays(signTime.getSignTime(), new Date());
			signTime.setSignTime(new Date());
			if (days == 1) {// 相隔一天 签到天数连续
				signTime.setSignDays(signTime.getSignDays() + 1);
			} else {// 非连续签到 重新计算签到天数
				signTime.setSignDays(1);
			}
			// 查询连续签到奖励
			List<RewardVo> rewards = rewardService.getBySignDays(signTime.getSignDays());
			SignTimeVo signTimeVo = signTimeConvert.toVo(signTime);
			signTimeVo.setSigned(true);
			signTimeVo.setRewards(rewards);
			// 签到积分
			accountService.updateByTaskType(memberId, BasicEnum.TASK_TYPE_SIGN);
			// 签到奖励
			rewardService.grantRewardByRewardType(ActivityEnum.REWARD_TYPE_SIGN, signTime.getMember(), signTime.getSignDays());
			return signTimeVo;
		} else {
			// 没有签到记录
			signTime = new SignTime();
			signTime.setMember(memberService.getMemberById(memberId));
			signTime.setSignTime(new Date());
			signTime.setSignDays(1);
			SignTimeVo signTimeVo = signTimeConvert.toVo(signTimeDao.save(signTime));
			// 查询连续签到奖励
			List<RewardVo> rewards = rewardService.getBySignDays(signTime.getSignDays());
			signTimeVo.setSigned(true);
			signTimeVo.setRewards(rewards);
			// 签到积分
			accountService.updateByTaskType(memberId, BasicEnum.TASK_TYPE_SIGN);
			// 签到奖励
			rewardService.grantRewardByRewardType(ActivityEnum.REWARD_TYPE_SIGN, signTime.getMember(), signTime.getSignDays());
			return signTimeVo;
		}
	}

	@Override
	public Boolean checkSign(int memberId) {
		// 查询是否有签到记录
		SignTime dbSignTime = signTimeDao.findByMemberId(memberId);
		if (dbSignTime != null) {
			// 是否今天签到
			boolean flag = DateUtils.isSameDay(dbSignTime.getSignTime(), new Date());
			if (flag) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * 
	 * TODO 暂时 放到该方法内 待移到 DateUtils 工具类内 计算两个时间之间相差的天数(取头不取尾)
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public int getApartDays(Date startDate, Date endDate) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) { // 不同一年
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				// 闰年
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
					timeDistance += 366;
					// 不是闰年
				} else {
					timeDistance += 365;
				}
			}
			return timeDistance + (day2 - day1);
		} else { // 同年
			return day2 - day1;
		}
	}

}