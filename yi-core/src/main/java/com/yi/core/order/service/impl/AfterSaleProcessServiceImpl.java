/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.yi.core.order.OrderEnum;
import com.yi.core.order.service.IAfterSaleOrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.order.dao.AfterSaleProcessDao;
import com.yi.core.order.domain.bo.AfterSaleProcessBo;
import com.yi.core.order.domain.entity.AfterSaleProcess;
import com.yi.core.order.domain.entity.AfterSaleProcess_;
import com.yi.core.order.domain.simple.AfterSaleProcessSimple;
import com.yi.core.order.domain.vo.AfterSaleProcessListVo;
import com.yi.core.order.domain.vo.AfterSaleProcessVo;
import com.yi.core.order.service.IAfterSaleProcessService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 售后处理
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class AfterSaleProcessServiceImpl implements IAfterSaleProcessService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AfterSaleProcessServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AfterSaleProcessDao afterSaleProcessDao;

	@Resource
	private IAfterSaleOrderService afterSaleOrderService;

	private EntityListVoBoSimpleConvert<AfterSaleProcess, AfterSaleProcessBo, AfterSaleProcessVo, AfterSaleProcessSimple, AfterSaleProcessListVo> afterSaleProcessConvert;

	/**
	 * 分页查询AfterSaleProcess
	 **/
	@Override
	public Page<AfterSaleProcess> query(Pagination<AfterSaleProcess> query) {
		query.setEntityClazz(AfterSaleProcess.class);
		Page<AfterSaleProcess> page = afterSaleProcessDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: AfterSaleProcess
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AfterSaleProcessListVo> queryListVo(Pagination<AfterSaleProcess> query) {
		Page<AfterSaleProcess> pages = this.query(query);
		List<AfterSaleProcessListVo> vos = afterSaleProcessConvert.toListVos(pages.getContent());
		return new PageImpl<AfterSaleProcessListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: AfterSaleProcess
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AfterSaleProcessListVo> queryListVoForApp(Pagination<AfterSaleProcess> query) {
		query.setEntityClazz(AfterSaleProcess.class);
		Page<AfterSaleProcess> pages = afterSaleProcessDao.findAll(query, query.getPageRequest());
		List<AfterSaleProcessListVo> vos = afterSaleProcessConvert.toListVos(pages.getContent());
		return new PageImpl<AfterSaleProcessListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建AfterSaleProcess
	 **/
	@Override
	public AfterSaleProcess addAfterSaleProcess(AfterSaleProcess afterSaleProcess) {
		return afterSaleProcessDao.save(afterSaleProcess);
	}

	/**
	 * 创建AfterSaleProcess
	 **/
	@Override
	public AfterSaleProcessListVo addAfterSaleProcess(AfterSaleProcessBo afterSaleProcessBo) {
		return afterSaleProcessConvert.toListVo(afterSaleProcessDao.save(afterSaleProcessConvert.toEntity(afterSaleProcessBo)));
	}

	/**
	 * 更新AfterSaleProcess
	 **/
	@Override
	public AfterSaleProcess updateAfterSaleProcess(AfterSaleProcess afterSaleProcess) {
		AfterSaleProcess dbAfterSaleProcess = afterSaleProcessDao.getOne(afterSaleProcess.getId());
		AttributeReplication.copying(afterSaleProcess, dbAfterSaleProcess, AfterSaleProcess_.guid, AfterSaleProcess_.afterSaleOrder, AfterSaleProcess_.processPerson,
				AfterSaleProcess_.processType, AfterSaleProcess_.processInfo, AfterSaleProcess_.processDate, AfterSaleProcess_.remark, AfterSaleProcess_.createTime,
				AfterSaleProcess_.deleted, AfterSaleProcess_.delTime);
		return dbAfterSaleProcess;
	}

	/**
	 * 更新AfterSaleProcess
	 **/
	@Override
	public AfterSaleProcessListVo updateAfterSaleProcess(AfterSaleProcessBo afterSaleProcessBo) {
		AfterSaleProcess dbAfterSaleProcess = afterSaleProcessDao.getOne(afterSaleProcessBo.getId());
		AttributeReplication.copying(afterSaleProcessBo, dbAfterSaleProcess, AfterSaleProcess_.guid, AfterSaleProcess_.afterSaleOrder, AfterSaleProcess_.processPerson,
				AfterSaleProcess_.processType, AfterSaleProcess_.processInfo, AfterSaleProcess_.processDate, AfterSaleProcess_.remark, AfterSaleProcess_.createTime,
				AfterSaleProcess_.deleted, AfterSaleProcess_.delTime);
		return afterSaleProcessConvert.toListVo(dbAfterSaleProcess);
	}

	/**
	 * 删除AfterSaleProcess
	 **/
	@Override
	public void removeAfterSaleProcessById(int id) {
		afterSaleProcessDao.deleteById(id);
	}

	/**
	 * 根据ID得到AfterSaleProcess
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleProcess getAfterSaleProcessById(int id) {
		return this.afterSaleProcessDao.getOne(id);
	}

	/**
	 * 根据ID得到AfterSaleProcessBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleProcessBo getAfterSaleProcessBoById(int id) {
		return afterSaleProcessConvert.toBo(this.afterSaleProcessDao.getOne(id));
	}

	/**
	 * 根据ID得到AfterSaleProcessVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleProcessVo getAfterSaleProcessVoById(int id) {
		return afterSaleProcessConvert.toVo(this.afterSaleProcessDao.getOne(id));
	}

	/**
	 * 根据ID得到AfterSaleProcessListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleProcessListVo getListVoById(int id) {
		return afterSaleProcessConvert.toListVo(this.afterSaleProcessDao.getOne(id));
	}

	protected void initConvert() {
		this.afterSaleProcessConvert = new EntityListVoBoSimpleConvert<AfterSaleProcess, AfterSaleProcessBo, AfterSaleProcessVo, AfterSaleProcessSimple, AfterSaleProcessListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<AfterSaleProcess, AfterSaleProcessVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleProcess, AfterSaleProcessVo>(beanConvertManager) {
					@Override
					protected void postConvert(AfterSaleProcessVo afterSaleProcessVo, AfterSaleProcess afterSaleProcess) {
					}
				};
			}

			@Override
			protected BeanConvert<AfterSaleProcess, AfterSaleProcessListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleProcess, AfterSaleProcessListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AfterSaleProcessListVo afterSaleProcessListVo, AfterSaleProcess afterSaleProcess) {
					}
				};
			}

			@Override
			protected BeanConvert<AfterSaleProcess, AfterSaleProcessBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleProcess, AfterSaleProcessBo>(beanConvertManager) {
					@Override
					protected void postConvert(AfterSaleProcessBo afterSaleProcessBo, AfterSaleProcess afterSaleProcess) {
					}
				};
			}

			@Override
			protected BeanConvert<AfterSaleProcessBo, AfterSaleProcess> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleProcessBo, AfterSaleProcess>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AfterSaleProcess, AfterSaleProcessSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleProcess, AfterSaleProcessSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AfterSaleProcessSimple, AfterSaleProcess> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleProcessSimple, AfterSaleProcess>(beanConvertManager) {
					@Override
					public AfterSaleProcess convert(AfterSaleProcessSimple afterSaleProcessSimple) throws BeansException {
						return afterSaleProcessDao.getOne(afterSaleProcessSimple.getId());
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
	 * 根据 处理状态 保存处理流程
	 */
	@Override
	@Transactional
	public void addByProcessState(AfterSaleProcessBo afterSaleProcessbo) {
		if (afterSaleProcessbo == null || afterSaleProcessbo.getAfterSaleOrder() == null || afterSaleProcessbo.getAfterSaleOrder().getId() < 1
				|| StringUtils.isBlank(afterSaleProcessbo.getProcessPerson()) || afterSaleProcessbo.getProcessType() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		// 处理流程
		AfterSaleProcess afterSaleProcess = afterSaleProcessConvert.toEntity(afterSaleProcessbo);
		afterSaleProcess.setProcessDate(new Date());
		// 已申请待审核
		if (OrderEnum.PROCESS_STATE_WAIT_AUDIT.getCode().equals(afterSaleProcess.getProcessType())) {
			if (StringUtils.isBlank(afterSaleProcess.getProcessInfo())) {
				afterSaleProcess.setProcessInfo("您的服务单已申请成功，待售后审核中");
			}
		} else if (OrderEnum.PROCESS_STATE_WAIT_RECEIPT.getCode().equals(afterSaleProcess.getProcessType())) {
			if (StringUtils.isBlank(afterSaleProcess.getProcessInfo())) {
				afterSaleProcess.setProcessInfo("您的服务单（" + afterSaleProcess.getAfterSaleOrder().getAfterSaleNo() + "）已审核通过，请将商品寄回");
			}
			// 修正售后订单状态 确认退货待收货
			afterSaleOrderService.confirmReturn(afterSaleProcessbo.getAfterSaleOrder().getId());
		} else if (OrderEnum.PROCESS_STATE_WAIT_REFUND.getCode().equals(afterSaleProcess.getProcessType())) {
			if (StringUtils.isBlank(afterSaleProcess.getProcessInfo())) {
				afterSaleProcess.setProcessInfo("您的服务单（" + afterSaleProcess.getAfterSaleOrder().getAfterSaleNo() + "）的商品已收到，待售后检验");
			}
			// 修正售后订单状态 确认收货待退款 同意退款
			afterSaleOrderService.confirmReceipt(afterSaleProcess.getAfterSaleOrder().getId());
		} else if (OrderEnum.PROCESS_STATE_FINISH.getCode().equals(afterSaleProcess.getProcessType())) {
			if (StringUtils.isBlank(afterSaleProcess.getProcessInfo())) {
				afterSaleProcess.setProcessInfo("您的服务单（" + afterSaleProcess.getAfterSaleOrder().getAfterSaleNo() + "）已退款，预计三个工作日内到达原支付账户");
			}
			try {
				// 确认退款已完成
				afterSaleOrderService.confirmRefund(afterSaleProcess.getAfterSaleOrder().getId());
			} catch (Exception e) {
				LOG.error("退款失败", e);
				throw new BusinessException(e.getMessage());
			}
		} else if (OrderEnum.PROCESS_STATE_REFUSE_RETURN.getCode().equals(afterSaleProcess.getProcessType())) {
			if (StringUtils.isBlank(afterSaleProcess.getProcessInfo())) {
				afterSaleProcess.setProcessInfo("您的服务单（" + afterSaleProcess.getAfterSaleOrder().getAfterSaleNo() + "）不符合退货条件，请联系客服处理");
			}
			// 拒绝退货 拒绝收货
			afterSaleOrderService.refuseReturn(afterSaleProcessbo.getAfterSaleOrder().getId());
		} else if (OrderEnum.PROCESS_STATE_REFUSE_REFUND.getCode().equals(afterSaleProcess.getProcessType())) {
			if (StringUtils.isBlank(afterSaleProcess.getProcessInfo())) {
				afterSaleProcess.setProcessInfo("您的服务单（" + afterSaleProcess.getAfterSaleOrder().getAfterSaleNo() + "）不符合退款条件，请联系客服处理");
			}
			// 拒绝退款
			afterSaleOrderService.refuseRefund(afterSaleProcess.getAfterSaleOrder().getId());
		}
		// 保存处理流程
		afterSaleProcessDao.save(afterSaleProcess);
	}
}
