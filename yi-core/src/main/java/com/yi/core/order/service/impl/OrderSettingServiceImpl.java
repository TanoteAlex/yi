/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

import com.yi.core.common.Deleted;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.dao.OrderSettingDao;
import com.yi.core.order.domain.bo.OrderSettingBo;
import com.yi.core.order.domain.entity.OrderSetting;
import com.yi.core.order.domain.entity.OrderSetting_;
import com.yi.core.order.domain.simple.OrderSettingSimple;
import com.yi.core.order.domain.vo.OrderSettingListVo;
import com.yi.core.order.domain.vo.OrderSettingVo;
import com.yi.core.order.service.IOrderSettingService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.date.DateUtils;

/**
 * 订单设置
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class OrderSettingServiceImpl implements IOrderSettingService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(OrderSettingServiceImpl.class);

	/** 订单设置 超时类型 订单设置 */
	private static Map<Integer, OrderSetting> ORDER_SET = new ConcurrentHashMap<>();

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private OrderSettingDao orderSettingDao;

	private EntityListVoBoSimpleConvert<OrderSetting, OrderSettingBo, OrderSettingVo, OrderSettingSimple, OrderSettingListVo> orderSettingConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OrderSetting getOrderSettingById(int orderSettingId) {
		if (orderSettingDao.existsById(orderSettingId)) {
			return orderSettingDao.getOne(orderSettingId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OrderSettingVo getOrderSettingVoById(int orderSettingId) {
		return orderSettingConvert.toVo(this.getOrderSettingById(orderSettingId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OrderSettingListVo getOrderSettingListVoById(int orderSettingId) {
		return orderSettingConvert.toListVo(this.getOrderSettingById(orderSettingId));
	}

	@Override
	public OrderSetting addOrderSetting(OrderSetting orderSetting) {
		return orderSettingDao.save(orderSetting);
	}

	@Override
	public OrderSettingVo addOrderSetting(OrderSettingBo orderSettingBo) {
		return orderSettingConvert.toVo(orderSettingDao.save(orderSettingConvert.toEntity(orderSettingBo)));
	}

	@Override
	public OrderSetting updateOrderSetting(OrderSetting orderSetting) {
		OrderSetting dbOrderSetting = orderSettingDao.getOne(orderSetting.getId());
		AttributeReplication.copying(orderSetting, dbOrderSetting, OrderSetting_.timeout);
		return dbOrderSetting;
	}

	@Override
	public OrderSettingVo updateOrderSetting(OrderSettingBo orderSettingBo) {
		return orderSettingConvert.toVo(this.updateOrderSetting(orderSettingConvert.toEntity(orderSettingBo)));
	}

	@Override
	public void removeOrderSettingById(int orderSettingId) {
		OrderSetting dbOrderSetting = this.getOrderSettingById(orderSettingId);
		if (dbOrderSetting != null) {
			dbOrderSetting.setDeleted(Deleted.DEL_TRUE);
			dbOrderSetting.setDelTime(new Date());
		}
	}

	@Override
	public Page<OrderSetting> query(Pagination<OrderSetting> query) {
		query.setEntityClazz(OrderSetting.class);
		Page<OrderSetting> page = orderSettingDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<OrderSettingListVo> queryListVo(Pagination<OrderSetting> query) {
		Page<OrderSetting> pages = this.query(query);
		List<OrderSettingListVo> vos = orderSettingConvert.toListVos(pages.getContent());
		return new PageImpl<OrderSettingListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	protected void initConvert() {
		this.orderSettingConvert = new EntityListVoBoSimpleConvert<OrderSetting, OrderSettingBo, OrderSettingVo, OrderSettingSimple, OrderSettingListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<OrderSetting, OrderSettingVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderSetting, OrderSettingVo>(beanConvertManager) {
					@Override
					protected void postConvert(OrderSettingVo OrderSettingVo, OrderSetting OrderSetting) {

					}
				};
			}

			@Override
			protected BeanConvert<OrderSetting, OrderSettingListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderSetting, OrderSettingListVo>(beanConvertManager) {
					@Override
					protected void postConvert(OrderSettingListVo OrderSettingListVo, OrderSetting OrderSetting) {

					}
				};
			}

			@Override
			protected BeanConvert<OrderSetting, OrderSettingBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderSetting, OrderSettingBo>(beanConvertManager) {
					@Override
					protected void postConvert(OrderSettingBo OrderSettingBo, OrderSetting OrderSetting) {

					}
				};
			}

			@Override
			protected BeanConvert<OrderSettingBo, OrderSetting> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderSettingBo, OrderSetting>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<OrderSetting, OrderSettingSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderSetting, OrderSettingSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<OrderSettingSimple, OrderSetting> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderSettingSimple, OrderSetting>(beanConvertManager) {
					@Override
					public OrderSetting convert(OrderSettingSimple OrderSettingSimple) throws BeansException {
						return orderSettingDao.getOne(OrderSettingSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	@Override
	public OrderSettingVo updateTimeoutValue(Integer id, Integer amount) {
		OrderSetting dbOrderSetting = this.getOrderSettingById(id);
		if (dbOrderSetting != null) {
			dbOrderSetting.setTimeout(amount);
			// 更新缓存数据
			ORDER_SET.put(dbOrderSetting.getSetType(), dbOrderSetting);
			return orderSettingConvert.toVo(dbOrderSetting);
		}
		return null;
	}

	/**
	 * 校验是否超时
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkTimeout(Date startTime, Date endTime, OrderEnum setType) {
		if (startTime != null && endTime != null && setType != null) {
			// 计算失效时间
			Date invalidTime = this.getInvalidTimeBySetType(startTime, setType);
			// 计算出的失效时间 > 结束时间 = 超时
			if (invalidTime != null && invalidTime.before(endTime)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据订单设置 获取失效时间
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Date getInvalidTimeBySetType(Date date, OrderEnum setType) {
		OrderSetting dbOrderSetting = null;
		// 从缓存中获取订单设置数据
		if (ORDER_SET.containsKey(setType.getCode())) {
			dbOrderSetting = ORDER_SET.get(setType.getCode());
		} else {
			dbOrderSetting = orderSettingDao.findBySetTypeAndDeleted(setType.getCode(), Deleted.DEL_FALSE);
			ORDER_SET.put(setType.getCode(), dbOrderSetting);
		}
		if (dbOrderSetting != null) {
			if (OrderEnum.TIME_UNIT_DAY.getCode().equals(dbOrderSetting.getTimeUnit())) {
				return DateUtils.addDays(date, dbOrderSetting.getTimeout());
			} else if (OrderEnum.TIME_UNIT_HOUR.getCode().equals(dbOrderSetting.getTimeUnit())) {
				return DateUtils.addHours(date, dbOrderSetting.getTimeout());
			} else if (OrderEnum.TIME_UNIT_MINUTE.getCode().equals(dbOrderSetting.getTimeUnit())) {
				return DateUtils.addMinutes(date, dbOrderSetting.getTimeout());
			}
		}
		return null;
	}

}
