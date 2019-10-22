/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.order.OrderEnum;
import com.yi.core.order.dao.OrderLogDao;
import com.yi.core.order.domain.bo.OrderLogBo;
import com.yi.core.order.domain.entity.OrderLog;
import com.yi.core.order.domain.entity.OrderLog_;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.simple.OrderLogSimple;
import com.yi.core.order.domain.vo.OrderLogListVo;
import com.yi.core.order.domain.vo.OrderLogVo;
import com.yi.core.order.service.IOrderLogService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.ValueUtils;

/**
 * 订单日志
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class OrderLogServiceImpl implements IOrderLogService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(OrderLogServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private OrderLogDao orderLogDao;

	private EntityListVoBoSimpleConvert<OrderLog, OrderLogBo, OrderLogVo, OrderLogSimple, OrderLogListVo> orderLogConvert;

	@Override
	public OrderLog getOrderLogById(int orderLogId) {
		return orderLogDao.getOne(orderLogId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OrderLogVo getOrderLogVoById(int orderLogId) {

		return orderLogConvert.toVo(this.orderLogDao.getOne(orderLogId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OrderLogListVo getOrderLogListVoById(int orderLogId) {
		return orderLogConvert.toListVo(this.orderLogDao.getOne(orderLogId));
	}

	@Override
	public OrderLogVo addOrderLog(OrderLog orderLog) {
		orderLog.setGuid(ValueUtils.generateGUID());
		orderLog.setOperateTime(new Date());
		return orderLogConvert.toVo(orderLogDao.save(orderLog));
	}

	@Override
	public OrderLogListVo addOrderLog(OrderLogBo orderLogBo) {
		orderLogBo.setGuid(ValueUtils.generateGUID());
		orderLogBo.setOperateTime(new Date());
		return orderLogConvert.toListVo(orderLogDao.save(orderLogConvert.toEntity(orderLogBo)));
	}

	@Override
	public OrderLogVo updateOrderLog(OrderLog orderLog) {

		OrderLog dbOrderLog = orderLogDao.getOne(orderLog.getId());
		AttributeReplication.copying(orderLog, dbOrderLog, OrderLog_.orderNo, OrderLog_.state, OrderLog_.operateTime, OrderLog_.operator, OrderLog_.remark);
		return orderLogConvert.toVo(dbOrderLog);
	}

	@Override
	public OrderLogListVo updateOrderLog(OrderLogBo orderLogBo) {
		OrderLog dbOrderLog = orderLogDao.getOne(orderLogBo.getId());
		AttributeReplication.copying(orderLogBo, dbOrderLog, OrderLog_.orderNo, OrderLog_.state, OrderLog_.operateTime, OrderLog_.operator, OrderLog_.remark);
		return orderLogConvert.toListVo(dbOrderLog);
	}

	@Override
	public void removeOrderLogById(int orderLogId) {
		orderLogDao.deleteById(orderLogId);
	}

	@Override
	public Page<OrderLog> query(Pagination<OrderLog> query) {
		query.setEntityClazz(OrderLog.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderLog_.operateTime)));
			// list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(MemberLevel_.deleted),
			// Deleted.DEL_FALSE)));
			// list1.add(criteriaBuilder.asc(root.get(MemberLevel_.growthValue)));
		}));

		Page<OrderLog> page = orderLogDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<OrderLogListVo> queryListVo(Pagination<OrderLog> query) {
		query.setEntityClazz(OrderLog.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get(OrderLog_.operateTime)));
		}));
		Page<OrderLog> pages = orderLogDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.getOrder().setMember(null);
			tmp.getOrder().setSaleOrderItems(null);
			tmp.getOrder().setSupplier(null);
		});
		List<OrderLogListVo> vos = orderLogConvert.toListVos(pages.getContent());
		return new PageImpl<OrderLogListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.orderLogConvert = new EntityListVoBoSimpleConvert<OrderLog, OrderLogBo, OrderLogVo, OrderLogSimple, OrderLogListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<OrderLog, OrderLogVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderLog, OrderLogVo>(beanConvertManager) {
					@Override
					protected void postConvert(OrderLogVo OrderLogVo, OrderLog OrderLog) {

					}
				};
			}

			@Override
			protected BeanConvert<OrderLog, OrderLogListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderLog, OrderLogListVo>(beanConvertManager) {
					@Override
					protected void postConvert(OrderLogListVo OrderLogListVo, OrderLog OrderLog) {

					}
				};
			}

			@Override
			protected BeanConvert<OrderLog, OrderLogBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderLog, OrderLogBo>(beanConvertManager) {
					@Override
					protected void postConvert(OrderLogBo OrderLogBo, OrderLog OrderLog) {

					}
				};
			}

			@Override
			protected BeanConvert<OrderLogBo, OrderLog> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderLogBo, OrderLog>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<OrderLog, OrderLogSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderLog, OrderLogSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<OrderLogSimple, OrderLog> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OrderLogSimple, OrderLog>(beanConvertManager) {
					@Override
					public OrderLog convert(OrderLogSimple OrderLogSimple) throws BeansException {
						return orderLogDao.getOne(OrderLogSimple.getId());
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
	@Async
	public void batchAddByOrders(Integer logState, SaleOrder... saleOrders) {
		if (ArrayUtils.isNotEmpty(saleOrders)) {
			List<OrderLog> saveLogs = new ArrayList<>();
			for (SaleOrder order : saleOrders) {
				if (order != null) {
					OrderLog tmp = new OrderLog();
					tmp.setOrder(order);
					tmp.setOrderNo(order.getOrderNo());
					tmp.setState(logState);
					tmp.setOperator(order.getMember().getUsername());
					tmp.setOperateTime(new Date());
					tmp.setRemark("系统创建日志");
					saveLogs.add(tmp);
				}
			}
			orderLogDao.saveAll(saveLogs);
		}
	}

	/**
	 * 根据订单创建日志
	 */
	@Override
	public void addLogByOrder(SaleOrder saleOrder, OrderEnum logState, String remark) {
		if (saleOrder != null) {
			OrderLog tmpLog = new OrderLog();
			tmpLog.setOrder(saleOrder);
			tmpLog.setOrderNo(saleOrder.getOrderNo());
			tmpLog.setState(logState.getCode());
			tmpLog.setOperateTime(new Date());
			tmpLog.setOperator("系统");
			if (StringUtils.isBlank(remark)) {
				tmpLog.setRemark("系统创建日志");
			} else {
				tmpLog.setRemark(remark);
			}
			orderLogDao.save(tmpLog);
		}
	}

}
