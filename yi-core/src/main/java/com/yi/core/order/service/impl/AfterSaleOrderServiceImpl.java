/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
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

import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.attachment.service.IAttachmentService;
import com.yi.core.common.Deleted;
import com.yi.core.common.FileType;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.common.ObjectType;
import com.yi.core.gift.GiftEnum;
import com.yi.core.gift.domain.entity.GiftBag;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.dao.AfterSaleOrderDao;
import com.yi.core.order.domain.bo.AfterSaleOrderBo;
import com.yi.core.order.domain.entity.AfterSaleOrder;
import com.yi.core.order.domain.entity.AfterSaleOrder_;
import com.yi.core.order.domain.entity.AfterSaleProcess;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.simple.AfterSaleOrderSimple;
import com.yi.core.order.domain.vo.AfterSaleOrderListVo;
import com.yi.core.order.domain.vo.AfterSaleOrderVo;
import com.yi.core.order.service.IAfterSaleOrderService;
import com.yi.core.order.service.IAfterSaleProcessService;
import com.yi.core.order.service.IOrderLogService;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.payment.alipay.AlipayService;
import com.yi.core.payment.weChat.WeChatNotifyService;
import com.yi.core.payment.weChat.WeChatRefundService;
import com.yi.core.promotion.service.IGroupBuyOrderService;
import com.yi.core.supplier.service.ISupplierAccountService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.ValueUtils;

/**
 * 售后单
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class AfterSaleOrderServiceImpl implements IAfterSaleOrderService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AfterSaleOrderServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AfterSaleOrderDao afterSaleOrderDao;

	@Resource
	private ISupplierAccountService supplierAccountService;

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private IMemberService memberService;

	@Resource
	private IAttachmentService attachmentService;

	@Resource
	private IAfterSaleProcessService afterSaleProcessService;

	@Resource
	private WeChatRefundService weChatRefundService;

	@Resource
	private WeChatNotifyService weChatNotifyService;

	@Resource
	private IAccountService accountService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IGroupBuyOrderService groupBuyOrderService;

	@Resource
	private IOrderLogService orderLogService;

	@Resource
	private IAccountRecordService accountRecordService;

	@Resource
	private AlipayService alipayService;

	private EntityListVoBoSimpleConvert<AfterSaleOrder, AfterSaleOrderBo, AfterSaleOrderVo, AfterSaleOrderSimple, AfterSaleOrderListVo> afterSaleOrderConvert;

	/**
	 * 分页查询AfterSaleOrder
	 **/
	@Override
	public Page<AfterSaleOrder> query(Pagination<AfterSaleOrder> query) {
		query.setEntityClazz(AfterSaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AfterSaleOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(AfterSaleOrder_.createTime)));
		}));
		Page<AfterSaleOrder> page = afterSaleOrderDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: AfterSaleOrder
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AfterSaleOrderListVo> queryListVo(Pagination<AfterSaleOrder> query) {
		query.setEntityClazz(AfterSaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AfterSaleOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(AfterSaleOrder_.createTime)));
		}));
		Page<AfterSaleOrder> pages = afterSaleOrderDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.getSaleOrder().setMember(null);
			tmp.getSaleOrder().setSupplier(null);
			tmp.getSaleOrder().setSaleOrderItems(null);
			tmp.getSaleOrder().setCoupons(null);
			tmp.getSaleOrder().setVouchers(null);
		});
		List<AfterSaleOrderListVo> vos = afterSaleOrderConvert.toListVos(pages.getContent());
		return new PageImpl<AfterSaleOrderListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: AfterSaleOrder
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AfterSaleOrderListVo> queryListVoForApp(Pagination<AfterSaleOrder> query) {
		query.setEntityClazz(AfterSaleOrder.class);
		Page<AfterSaleOrder> pages = afterSaleOrderDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.getSaleOrder().setMember(null);
			tmp.getSaleOrder().setSupplier(null);
			// tmp.getSaleOrder().setSaleOrderItems(null);
			tmp.getSaleOrder().setCoupons(null);
			tmp.getSaleOrder().setVouchers(null);
		});
		List<AfterSaleOrderListVo> vos = afterSaleOrderConvert.toListVos(pages.getContent());
		return new PageImpl<AfterSaleOrderListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建AfterSaleOrder
	 **/
	@Override
	public AfterSaleOrder addAfterSaleOrder(AfterSaleOrder afterSaleOrder) {
		return afterSaleOrderDao.save(afterSaleOrder);
	}

	/**
	 * 创建AfterSaleOrder
	 **/
	@Override
	public AfterSaleOrderListVo addAfterSaleOrder(AfterSaleOrderBo afterSaleOrderBo) {
		return afterSaleOrderConvert.toListVo(afterSaleOrderDao.save(afterSaleOrderConvert.toEntity(afterSaleOrderBo)));
	}

	/**
	 * 更新AfterSaleOrder
	 **/
	@Override
	public AfterSaleOrder updateAfterSaleOrder(AfterSaleOrder afterSaleOrder) {
		AfterSaleOrder dbAfterSaleOrder = afterSaleOrderDao.getOne(afterSaleOrder.getId());
		AttributeReplication.copying(afterSaleOrder, dbAfterSaleOrder, AfterSaleOrder_.afterSaleType, AfterSaleOrder_.afterSaleNo, AfterSaleOrder_.applyState,
				AfterSaleOrder_.processState, AfterSaleOrder_.refundOrderNo, AfterSaleOrder_.refundTradeNo, AfterSaleOrder_.refundPayState, AfterSaleOrder_.refundAmount,
				AfterSaleOrder_.actualRefundAmount, AfterSaleOrder_.refundMode, AfterSaleOrder_.afterSaleReason, AfterSaleOrder_.problemDesc, AfterSaleOrder_.voucherPhoto,
				AfterSaleOrder_.contact, AfterSaleOrder_.contactPhone, AfterSaleOrder_.applyTime, AfterSaleOrder_.refundTime, AfterSaleOrder_.remark);
		return dbAfterSaleOrder;
	}

	/**
	 * 更新AfterSaleOrder
	 **/
	@Override
	public AfterSaleOrderListVo updateAfterSaleOrder(AfterSaleOrderBo afterSaleOrderBo) {
		AfterSaleOrder dbAfterSaleOrder = this.updateAfterSaleOrder(afterSaleOrderConvert.toEntity(afterSaleOrderBo));
		return afterSaleOrderConvert.toListVo(dbAfterSaleOrder);
	}

	/**
	 * 删除AfterSaleOrder
	 **/
	@Override
	public void removeAfterSaleOrderById(int id) {
		AfterSaleOrder dbAfterSaleOrder = this.getById(id);
		if (dbAfterSaleOrder != null) {
			dbAfterSaleOrder.setDeleted(Deleted.DEL_TRUE);
			dbAfterSaleOrder.setDelTime(new Date());
		}
	}

	/**
	 * 根据ID得到AfterSaleOrder
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleOrder getById(int id) {
		if (afterSaleOrderDao.existsById(id)) {
			return this.afterSaleOrderDao.getOne(id);
		}
		return null;
	}

	/**
	 * 根据ID得到AfterSaleOrderBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleOrderBo getBoById(int id) {
		return afterSaleOrderConvert.toBo(this.afterSaleOrderDao.getOne(id));
	}

	/**
	 * 根据ID得到AfterSaleOrderVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleOrderVo getVoById(int id) {
		AfterSaleOrder dbAfterSaleOrder = this.getById(id);
		if (dbAfterSaleOrder != null) {
			dbAfterSaleOrder.setSaleOrder(null);
			dbAfterSaleOrder.setSupplier(null);
			AfterSaleOrderVo afterSaleOrderVo = afterSaleOrderConvert.toVo(dbAfterSaleOrder);
			// 获取图片集合
			List<AttachmentVo> attachmentVos = attachmentService.findByObjectIdAndObjectType(dbAfterSaleOrder.getId(), ObjectType.RETURN_ORDER);
			afterSaleOrderVo.setAttachmentVos(attachmentVos);
			return afterSaleOrderVo;
		}
		return null;

	}

	/**
	 * 根据ID得到AfterSaleOrderListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleOrderListVo getListVoById(int id) {
		return afterSaleOrderConvert.toListVo(this.afterSaleOrderDao.getOne(id));
	}

	protected void initConvert() {
		this.afterSaleOrderConvert = new EntityListVoBoSimpleConvert<AfterSaleOrder, AfterSaleOrderBo, AfterSaleOrderVo, AfterSaleOrderSimple, AfterSaleOrderListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<AfterSaleOrder, AfterSaleOrderVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleOrder, AfterSaleOrderVo>(beanConvertManager) {
					@Override
					protected void postConvert(AfterSaleOrderVo afterSaleOrderVo, AfterSaleOrder afterSaleOrder) {
					}
				};
			}

			@Override
			protected BeanConvert<AfterSaleOrder, AfterSaleOrderListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleOrder, AfterSaleOrderListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AfterSaleOrderListVo afterSaleOrderListVo, AfterSaleOrder afterSaleOrder) {
					}
				};
			}

			@Override
			protected BeanConvert<AfterSaleOrder, AfterSaleOrderBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleOrder, AfterSaleOrderBo>(beanConvertManager) {
					@Override
					protected void postConvert(AfterSaleOrderBo afterSaleOrderBo, AfterSaleOrder afterSaleOrder) {
					}
				};
			}

			@Override
			protected BeanConvert<AfterSaleOrderBo, AfterSaleOrder> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleOrderBo, AfterSaleOrder>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AfterSaleOrder, AfterSaleOrderSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleOrder, AfterSaleOrderSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AfterSaleOrderSimple, AfterSaleOrder> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleOrderSimple, AfterSaleOrder>(beanConvertManager) {
					@Override
					public AfterSaleOrder convert(AfterSaleOrderSimple afterSaleOrderSimple) throws BeansException {
						return afterSaleOrderDao.getOne(afterSaleOrderSimple.getId());
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleOrder getByRefundOrderNo(String refundOrderNo) {
		if (StringUtils.isNotBlank(refundOrderNo)) {
			return afterSaleOrderDao.findByRefundOrderNoAndDeleted(refundOrderNo, Deleted.DEL_FALSE);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleOrderVo getVoBySaleOrderId(int saleOrderId) {
		AfterSaleOrder dbAfterSaleOrder = afterSaleOrderDao.findBySaleOrder_idAndDeleted(saleOrderId, Deleted.DEL_FALSE);
		if (dbAfterSaleOrder != null) {
			dbAfterSaleOrder.setSaleOrder(null);
			dbAfterSaleOrder.setSupplier(null);
			AfterSaleOrderVo afterSaleOrderVo = afterSaleOrderConvert.toVo(dbAfterSaleOrder);
			List<AttachmentVo> attachmentVos = attachmentService.findByObjectIdAndObjectType(dbAfterSaleOrder.getId(), ObjectType.RETURN_ORDER);
			afterSaleOrderVo.setAttachmentVos(attachmentVos);
			return afterSaleOrderVo;
		}
		return null;

	}

	/**
	 * 申请售后
	 */
	@Override
	public AfterSaleOrder applyAfterSale(AfterSaleOrderBo afterSaleOrderBo) {
		if (afterSaleOrderBo == null || afterSaleOrderBo.getMember() == null || afterSaleOrderBo.getMember().getId() < 1 || afterSaleOrderBo.getSaleOrder() == null
				|| afterSaleOrderBo.getSaleOrder().getId() < 1 || afterSaleOrderBo.getAfterSaleType() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		// 检查订单
		SaleOrder dbSaleOrder = saleOrderService.getOrderById(afterSaleOrderBo.getSaleOrder().getId());
		if (dbSaleOrder == null) {
			throw new BusinessException("提交数据不能为空");
		}
		if (OrderEnum.ORDER_TYPE_PRIZE.getCode().equals(dbSaleOrder.getOrderType())) {
			throw new BusinessException("奖品订单，咱不支持申请售后");
		}
		if (OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(dbSaleOrder.getOrderState()) || OrderEnum.ORDER_STATE_CLOSED.getCode().equals(dbSaleOrder.getOrderState())) {
			throw new BusinessException("该订单未付款或已关闭，暂不支持售后服务");
		}
		// 退款
		if (OrderEnum.AFTER_SALE_TYPE_REFUND.getCode().equals(afterSaleOrderBo.getAfterSaleType())) {
			if (OrderEnum.ORDER_STATE_COMPLETED.getCode().equals(dbSaleOrder.getOrderState())) {
				throw new BusinessException("该订单已收货，请选择正确的售后服务");
			}
		}
		// 退货退款
		if (OrderEnum.AFTER_SALE_TYPE_RETURN.getCode().equals(afterSaleOrderBo.getAfterSaleType())) {
			if (!OrderEnum.ORDER_STATE_COMPLETED.getCode().equals(dbSaleOrder.getOrderState())) {
				throw new BusinessException("该订单未收货，请选择正确的售后服务");
			}
			// 根据申请类型 查看是否查过三包时效
			Date now = new Date();
			// 查看是否超过售后时间
			if (now.after(Optional.ofNullable(dbSaleOrder.getReturnInvalidTime()).orElse(now))) {
				throw new BusinessException("该订单已超过三包时效期限");
			}
		}
		// 检查会员
		Member dbMember = memberService.getMemberById(afterSaleOrderBo.getMember().getId());
		if (dbMember == null) {
			throw new BusinessException("提交数据不能为空");
		}
		// 封装售后单数据
		AfterSaleOrder afterSaleOrder = afterSaleOrderConvert.toEntity(afterSaleOrderBo);
		// 退款金额
		BigDecimal refundAmount = dbSaleOrder.getPayAmount();
		// 已发货待收货、已收货已完成 减去运费
		if (OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode().equals(dbSaleOrder.getOrderState()) || OrderEnum.ORDER_STATE_COMPLETED.getCode().equals(dbSaleOrder.getOrderState())) {
			refundAmount = refundAmount.subtract(dbSaleOrder.getFreight());
		}
		afterSaleOrder.setOrderNo(dbSaleOrder.getOrderNo());
		afterSaleOrder.setCommunity(dbSaleOrder.getCommunity());
		afterSaleOrder.setSupplier(dbSaleOrder.getSupplier());
		afterSaleOrder.setAfterSaleNo(NumberGenerateUtils.generateAfterSaleOrderNo());
		afterSaleOrder.setApplyState(OrderEnum.APPLY_STATE_AUDIT.getCode());
		afterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_WAIT_AUDIT.getCode());
		afterSaleOrder.setRefundOrderNo(ValueUtils.generateGUID());
		afterSaleOrder.setOrderAmount(dbSaleOrder.getOrderAmount());
		afterSaleOrder.setPayAmount(dbSaleOrder.getPayAmount());
		afterSaleOrder.setRefundAmount(refundAmount);
		afterSaleOrder.setRefundMode(OrderEnum.REFUND_MODE_ORIGINAL_CHANNEL.getCode());
		afterSaleOrder.setApplyTime(new Date());
		// 保存售后单
		AfterSaleOrder dbAfterSaleOrder = this.afterSaleOrderDao.save(afterSaleOrder);
		// 保存凭证图片
		if (CollectionUtils.isNotEmpty(afterSaleOrderBo.getAttachmentVos())) {
			for (AttachmentVo tmp : afterSaleOrderBo.getAttachmentVos()) {
				tmp.setObjectId(dbAfterSaleOrder.getId());
				tmp.setFileType(FileType.PICTURES);
				tmp.setObjectType(ObjectType.RETURN_ORDER);
				tmp.setDescription("退货凭证图片");
				tmp.setFilePath(tmp.getUrl());
				tmp.setSupplierId(Optional.ofNullable(dbSaleOrder.getSupplier()).map(e -> e.getId()).orElse(null));
				tmp.setSupplierName(Optional.ofNullable(dbSaleOrder.getSupplier()).map(e -> e.getSupplierName()).orElse(null));
			}
			attachmentService.saveAll(afterSaleOrderBo.getAttachmentVos());
		}
		// 修改订单的售后状态
		dbSaleOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_APPLY.getCode());
		// 添加一条处理流程
		AfterSaleProcess afterSaleProcess = new AfterSaleProcess();
		afterSaleProcess.setAfterSaleOrder(dbAfterSaleOrder);
		afterSaleProcess.setProcessType(OrderEnum.PROCESS_STATE_WAIT_AUDIT.getCode());
		afterSaleProcess.setProcessPerson("系统");
		afterSaleProcess.setProcessInfo("您的服务单已申请成功，待售后审核中");
		afterSaleProcess.setProcessDate(new Date());
		afterSaleProcessService.addAfterSaleProcess(afterSaleProcess);
		return null;
	}

	/**
	 * 确认退货
	 */
	@Override
	public AfterSaleOrder confirmReturn(Integer afterSaleOrderId) {
		AfterSaleOrder dbAfterSaleOrder = this.getById(afterSaleOrderId);
		if (dbAfterSaleOrder != null) {
			// 修正申请状态
			dbAfterSaleOrder.setApplyState(OrderEnum.APPLY_STATE_PROCESS.getCode());
			// 修正处理状态
			dbAfterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_WAIT_RECEIPT.getCode());
		}
		return null;
	}

	/**
	 * 拒绝退货
	 */
	@Override
	public AfterSaleOrder refuseReturn(Integer afterSaleOrderId) {
		AfterSaleOrder dbAfterSaleOrder = this.getById(afterSaleOrderId);
		if (dbAfterSaleOrder != null) {
			// 修正申请状态
			dbAfterSaleOrder.setApplyState(OrderEnum.APPLY_STATE_FINISH.getCode());
			// 修正处理状态
			dbAfterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_REFUSE_RETURN.getCode());
			// 修改订单的售后状态
			dbAfterSaleOrder.getSaleOrder().setAfterSaleState(OrderEnum.AFTER_SALE_STATE_COMPLETED.getCode());
		}
		return null;
	}

	/**
	 * 确认收货
	 */
	@Override
	public AfterSaleOrder confirmReceipt(Integer afterSaleOrderId) {
		AfterSaleOrder dbAfterSaleOrder = this.getById(afterSaleOrderId);
		if (dbAfterSaleOrder != null) {
			// 修正处理状态
			dbAfterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_WAIT_REFUND.getCode());
			dbAfterSaleOrder.setReceiptTime(new Date());
		}
		return null;
	}

	/**
	 * 确认退款
	 */
	@Override
	public AfterSaleOrder confirmRefund(Integer afterSaleOrderId) throws Exception {
		AfterSaleOrder dbAfterSaleOrder = this.getById(afterSaleOrderId);
		if (dbAfterSaleOrder != null) {
			// 如果退款金额为正数 执行正常的退款流程
			if (dbAfterSaleOrder.getRefundAmount().compareTo(BigDecimal.ZERO) > 0) {
				// 根据订单支付渠道 执行退款
				if (OrderEnum.PAYMENT_CHANNEL_SP.getCode().equals(dbAfterSaleOrder.getSaleOrder().getPaymentChannel())) {
					weChatRefundService.executeRefundForSp(dbAfterSaleOrder.getMember(), dbAfterSaleOrder);
				} else if (OrderEnum.PAYMENT_CHANNEL_MP.getCode().equals(dbAfterSaleOrder.getSaleOrder().getPaymentChannel())) {
					weChatRefundService.executeRefundForMp(dbAfterSaleOrder.getMember(), dbAfterSaleOrder);
				} else if (OrderEnum.PAYMENT_CHANNEL_APP_WECHAT.getCode().equals(dbAfterSaleOrder.getSaleOrder().getPaymentChannel())) {
					weChatRefundService.executeRefundForApp(dbAfterSaleOrder.getMember(), dbAfterSaleOrder);
				} else if (OrderEnum.PAYMENT_CHANNEL_APP_ALIPAY.getCode().equals(dbAfterSaleOrder.getSaleOrder().getPaymentChannel())) {
					alipayService.executeRefundForApp(dbAfterSaleOrder.getMember(), dbAfterSaleOrder);
				} else {
					throw new BusinessException("找不到订单支付渠道，无法退款");
				}
				// 修正申请状态
				dbAfterSaleOrder.setApplyState(OrderEnum.APPLY_STATE_FINISH.getCode());
				// 修正处理状态
				dbAfterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_FINISH.getCode());
				// 修正回执状态
				dbAfterSaleOrder.setRefundPayState(OrderEnum.REFUND_PAY_STATE_WAIT_RECEIPT.getCode());
				// 修改订单的售后状态
				dbAfterSaleOrder.getSaleOrder().setAfterSaleState(OrderEnum.AFTER_SALE_STATE_COMPLETED.getCode());
			} // 如果退款金额为负数或为0 则不执行退款直接结束流程
			else {
				// 修正申请状态
				dbAfterSaleOrder.setApplyState(OrderEnum.APPLY_STATE_FINISH.getCode());
				// 修正处理状态
				dbAfterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_FINISH.getCode());
				// 修正回执状态
				dbAfterSaleOrder.setRefundPayState(OrderEnum.REFUND_PAY_STATE_ALREADY_RECEIPT.getCode());
				dbAfterSaleOrder.setActualRefundAmount(BigDecimal.ZERO);
				dbAfterSaleOrder.setRefundTime(new Date());
				// 修改订单的售后状态
				dbAfterSaleOrder.getSaleOrder().setAfterSaleState(OrderEnum.AFTER_SALE_STATE_COMPLETED.getCode());
				// 修改订单的状态
				dbAfterSaleOrder.getSaleOrder().setOrderState(OrderEnum.ORDER_STATE_CLOSED.getCode());
				dbAfterSaleOrder.getSaleOrder().setCloseTime(new Date());
				// 关闭销售订单
				// saleOrderService.closeOrderByRefund(dbAfterSaleOrder.getSaleOrder());
				// 修改开团单和参团单
				groupBuyOrderService.updateByAfterRefund(dbAfterSaleOrder.getSaleOrder());
				// 订单记录
				orderLogService.addLogByOrder(dbAfterSaleOrder.getSaleOrder(), OrderEnum.ORDER_LOG_STATE_REFUND, "退款成功");
				// 会员资金账户记录
				accountRecordService.addAccountRecordByTradeType(dbAfterSaleOrder.getSaleOrder(), dbAfterSaleOrder.getMember(),
						dbAfterSaleOrder.getSaleOrder().getBalance().add(dbAfterSaleOrder.getRefundAmount()), MemberEnum.TRADE_TYPE_REFUND, dbAfterSaleOrder.getMember());
				// // 扣除上级佣金
				// memberCommissionService.returnSuperiorCommissionBySubordinateRefund(dbAfterSaleOrder.getSaleOrder());
				// 扣除已经发放的代金券
				couponReceiveService.returnVoucherByRefund(dbAfterSaleOrder.getMember(), dbAfterSaleOrder.getSaleOrder());
				// 释放已经使用的代金券
				couponReceiveService.returnUseVoucherByRefund(dbAfterSaleOrder.getMember(), dbAfterSaleOrder.getSaleOrder());
				// 将会员使用的余额返回会员账户里 返还的余额 = 当前使用余额 + 退款金额
				accountService.returnBalanceByRefund(dbAfterSaleOrder.getMember(), dbAfterSaleOrder.getSaleOrder().getBalance().add(dbAfterSaleOrder.getRefundAmount()),
						dbAfterSaleOrder.getSaleOrder());
				// 修正供应商账户
				supplierAccountService.updateSupplierAccountByConfirmRefund(dbAfterSaleOrder.getSaleOrder());
			}

		}
		return null;
	}

	/**
	 * 拒绝退款
	 */
	@Override
	public AfterSaleOrder refuseRefund(Integer afterSaleOrderId) {
		AfterSaleOrder dbAfterSaleOrder = this.getById(afterSaleOrderId);
		if (dbAfterSaleOrder != null) {
			// 修正申请状态
			dbAfterSaleOrder.setApplyState(OrderEnum.APPLY_STATE_FINISH.getCode());
			// 修正处理状态
			dbAfterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_REFUSE_REFUND.getCode());
			// 修改订单的售后状态
			dbAfterSaleOrder.getSaleOrder().setAfterSaleState(OrderEnum.AFTER_SALE_STATE_COMPLETED.getCode());
		}
		return null;
	}

	/**
	 * 为失效的礼包生成退款单
	 */
	@Override
	public AfterSaleOrder addByInvalidGiftBag(GiftBag giftBag) {
		if (giftBag != null && giftBag.getId() > 0) {
			// 查询礼包对应的订单
			SaleOrder dbSaleOrder = giftBag.getSaleOrder();
			// 订单不为空且 已经付款
			if (dbSaleOrder != null && OrderEnum.ORDER_STATE_COMPLETED.getCode().equals(dbSaleOrder.getOrderState())) {
				// 检查该礼包单有么有生成过退款单
				AfterSaleOrder checkAfterSaleOrder = afterSaleOrderDao.findBySaleOrder_idAndDeleted(dbSaleOrder.getId(), Deleted.DEL_FALSE);
				if (checkAfterSaleOrder == null) {
					// 根据礼包计算退款金额
					BigDecimal refundAmount1 = giftBag.getPrice().multiply(BigDecimal.valueOf((giftBag.getGiftNum() - giftBag.getReceiveGiftNum())));
					// 根据礼物计算退款金额
					BigDecimal refundAmount2 = giftBag.getTotal().subtract(giftBag.getGifts().stream().map(e -> {
						if (GiftEnum.RECEIVE_STATE_ALREADY.getCode().equals(e.getState())) {
							return e.getPrice();
						}
						return BigDecimal.ZERO;
					}).reduce(BigDecimal.ZERO, BigDecimal::add));
					String remark = null;
					// 对比金额 确认退款金额
					if (!(refundAmount1.compareTo(refundAmount2) == 0)) {
						LOG.error("礼包计算的退款金额{}和礼物计算的退款金额{}不一致，请核查", refundAmount1, refundAmount2);
						remark = "礼包计算的退款金额" + refundAmount1.toString() + "和礼物计算的退款金额" + refundAmount2.toString() + "不一致，请核查";
					}
					// 退款金额大于0 生成退款单
					if (refundAmount2.compareTo(BigDecimal.ZERO) > 0) {
						// 生成退款单
						AfterSaleOrder afterSaleOrder = new AfterSaleOrder();
						afterSaleOrder.setMember(giftBag.getMember());
						afterSaleOrder.setSupplier(dbSaleOrder.getSupplier());
						afterSaleOrder.setSaleOrder(dbSaleOrder);
						afterSaleOrder.setAfterSaleType(OrderEnum.AFTER_SALE_TYPE_REFUND.getCode());
						afterSaleOrder.setAfterSaleNo(NumberGenerateUtils.generateAfterSaleOrderNo());
						afterSaleOrder.setApplyState(OrderEnum.APPLY_STATE_AUDIT.getCode());
						afterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_WAIT_AUDIT.getCode());
						afterSaleOrder.setRefundOrderNo(ValueUtils.generateGUID());
						afterSaleOrder.setOrderAmount(dbSaleOrder.getOrderAmount());
						afterSaleOrder.setPayAmount(dbSaleOrder.getPayAmount());
						afterSaleOrder.setRefundAmount(refundAmount2);
						afterSaleOrder.setRefundMode(OrderEnum.REFUND_MODE_ORIGINAL_CHANNEL.getCode());
						afterSaleOrder.setAfterSaleReason("失效礼包退款");
						afterSaleOrder.setContact(giftBag.getMember().getNickname());
						afterSaleOrder.setContactPhone(giftBag.getMember().getPhone());
						afterSaleOrder.setApplyTime(new Date());
						afterSaleOrder.setRemark(remark);
						return this.addAfterSaleOrder(afterSaleOrder);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 为失效的开团单退款
	 */
	@Override
	@Transactional
	public void refundByInvalidOpenGroupBuy(SaleOrder saleOrder) {
		// 订单不为空且 已经付款
		if (saleOrder != null && OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode().equals(saleOrder.getOrderState())) {
			// 检查该团购单有么有生成过退款单
			AfterSaleOrder checkAfterSaleOrder = afterSaleOrderDao.findBySaleOrder_idAndDeleted(saleOrder.getId(), Deleted.DEL_FALSE);
			if (checkAfterSaleOrder == null) {
				// 支付金额大于0 生成退款单
				if (Optional.ofNullable(saleOrder.getPayAmount()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) > 0) {
					// 生成退款单
					AfterSaleOrder afterSaleOrder = new AfterSaleOrder();
					afterSaleOrder.setMember(saleOrder.getMember());
					afterSaleOrder.setCommunity(saleOrder.getMember().getCommunity());
					afterSaleOrder.setSupplier(saleOrder.getSupplier());
					afterSaleOrder.setSaleOrder(saleOrder);
					afterSaleOrder.setOrderNo(saleOrder.getOrderNo());
					afterSaleOrder.setAfterSaleType(OrderEnum.AFTER_SALE_TYPE_REFUND.getCode());
					afterSaleOrder.setAfterSaleNo(NumberGenerateUtils.generateAfterSaleOrderNo());
					afterSaleOrder.setApplyState(OrderEnum.APPLY_STATE_AUDIT.getCode());
					afterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_WAIT_AUDIT.getCode());
					afterSaleOrder.setRefundOrderNo(ValueUtils.generateGUID());
					afterSaleOrder.setOrderAmount(saleOrder.getOrderAmount());
					afterSaleOrder.setPayAmount(saleOrder.getPayAmount());
					afterSaleOrder.setRefundAmount(saleOrder.getPayAmount());
					afterSaleOrder.setRefundMode(OrderEnum.REFUND_MODE_ORIGINAL_CHANNEL.getCode());
					afterSaleOrder.setAfterSaleReason("未成团退款");
					afterSaleOrder.setProblemDesc("未成团退款");
					afterSaleOrder.setContact(saleOrder.getMember().getNickname());
					afterSaleOrder.setContactPhone(saleOrder.getMember().getPhone());
					afterSaleOrder.setApplyTime(new Date());
					afterSaleOrder.setRemark("开团单退款");
					AfterSaleOrder dbAfterSaleOrder = this.addAfterSaleOrder(afterSaleOrder);
					try {
						// 根据订单支付渠道 执行退款
						if (OrderEnum.PAYMENT_CHANNEL_SP.getCode().equals(saleOrder.getPaymentChannel())) {
							weChatRefundService.executeRefundForSp(dbAfterSaleOrder.getMember(), dbAfterSaleOrder);
						} else if (OrderEnum.PAYMENT_CHANNEL_MP.getCode().equals(saleOrder.getPaymentChannel())) {
							weChatRefundService.executeRefundForMp(dbAfterSaleOrder.getMember(), dbAfterSaleOrder);
						} else if (OrderEnum.PAYMENT_CHANNEL_APP_WECHAT.getCode().equals(saleOrder.getPaymentChannel())) {
							weChatRefundService.executeRefundForApp(dbAfterSaleOrder.getMember(), dbAfterSaleOrder);
						} else {
							LOG.error("找不到订单支付渠道，无法退款");
						}
						// 修正申请状态
						dbAfterSaleOrder.setApplyState(OrderEnum.APPLY_STATE_FINISH.getCode());
						// 修正处理状态
						dbAfterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_FINISH.getCode());
						// 修正回执状态
						dbAfterSaleOrder.setRefundPayState(OrderEnum.REFUND_PAY_STATE_WAIT_RECEIPT.getCode());
						// 修改订单的售后状态
						saleOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_COMPLETED.getCode());
					} catch (Exception e) {
						LOG.error("退款失败：{}", e.getMessage(), e);
					}
				} else {
					// 修改订单的售后状态
					saleOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_COMPLETED.getCode());
					// 释放已经使用的代金券
					couponReceiveService.returnUseVoucherByRefund(saleOrder.getMember(), saleOrder);
					// 将会员使用的余额返回会员账户里
					accountService.returnBalanceByRefund(saleOrder.getMember(), saleOrder.getBalance(), saleOrder);
				}
			}
		}
	}

	/**
	 * 为失效的参团单退款
	 */
	@Override
	@Transactional
	public void refundByInvalidJoinGroupBuy(SaleOrder saleOrder) {
		// 订单不为空且 已经付款
		if (saleOrder != null && OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode().equals(saleOrder.getOrderState())) {
			// 检查该团购单有么有生成过退款单
			AfterSaleOrder checkAfterSaleOrder = afterSaleOrderDao.findBySaleOrder_idAndDeleted(saleOrder.getId(), Deleted.DEL_FALSE);
			if (checkAfterSaleOrder == null) {
				// 支付金额大于0 生成退款单
				if (Optional.ofNullable(saleOrder.getPayAmount()).orElse(BigDecimal.ZERO).compareTo(BigDecimal.ZERO) > 0) {
					// 生成退款单
					AfterSaleOrder afterSaleOrder = new AfterSaleOrder();
					afterSaleOrder.setMember(saleOrder.getMember());
					afterSaleOrder.setSupplier(saleOrder.getSupplier());
					afterSaleOrder.setSaleOrder(saleOrder);
					afterSaleOrder.setAfterSaleType(OrderEnum.AFTER_SALE_TYPE_REFUND.getCode());
					afterSaleOrder.setAfterSaleNo(NumberGenerateUtils.generateAfterSaleOrderNo());
					afterSaleOrder.setApplyState(OrderEnum.APPLY_STATE_AUDIT.getCode());
					afterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_WAIT_AUDIT.getCode());
					afterSaleOrder.setRefundOrderNo(ValueUtils.generateGUID());
					afterSaleOrder.setOrderAmount(saleOrder.getOrderAmount());
					afterSaleOrder.setPayAmount(saleOrder.getPayAmount());
					afterSaleOrder.setRefundAmount(saleOrder.getPayAmount());
					afterSaleOrder.setRefundMode(OrderEnum.REFUND_MODE_ORIGINAL_CHANNEL.getCode());
					afterSaleOrder.setAfterSaleReason("未成团退款");
					afterSaleOrder.setContact(saleOrder.getMember().getNickname());
					afterSaleOrder.setContactPhone(saleOrder.getMember().getPhone());
					afterSaleOrder.setApplyTime(new Date());
					afterSaleOrder.setRemark("参团单退款");
					AfterSaleOrder dbAfterSaleOrder = this.addAfterSaleOrder(afterSaleOrder);
					try {
						// 根据订单支付渠道 执行退款
						if (OrderEnum.PAYMENT_CHANNEL_SP.getCode().equals(saleOrder.getPaymentChannel())) {
							weChatRefundService.executeRefundForSp(dbAfterSaleOrder.getMember(), dbAfterSaleOrder);
						} else if (OrderEnum.PAYMENT_CHANNEL_MP.getCode().equals(saleOrder.getPaymentChannel())) {
							weChatRefundService.executeRefundForMp(dbAfterSaleOrder.getMember(), dbAfterSaleOrder);
						} else if (OrderEnum.PAYMENT_CHANNEL_APP_WECHAT.getCode().equals(saleOrder.getPaymentChannel())) {
							weChatRefundService.executeRefundForApp(dbAfterSaleOrder.getMember(), dbAfterSaleOrder);
						} else {
							LOG.error("找不到订单支付渠道，无法退款");
						}
						// 修正申请状态
						dbAfterSaleOrder.setApplyState(OrderEnum.APPLY_STATE_FINISH.getCode());
						// 修正处理状态
						dbAfterSaleOrder.setProcessState(OrderEnum.PROCESS_STATE_FINISH.getCode());
						// 修正回执状态
						dbAfterSaleOrder.setRefundPayState(OrderEnum.REFUND_PAY_STATE_WAIT_RECEIPT.getCode());
						// 修改订单的售后状态
						saleOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_COMPLETED.getCode());
					} catch (Exception e) {
						LOG.error("退款失败：{}", e.getMessage(), e);
					}
				} else {
					// 修改订单的售后状态
					saleOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_COMPLETED.getCode());
					// 释放已经使用的代金券
					couponReceiveService.returnUseVoucherByRefund(saleOrder.getMember(), saleOrder);
					// 将会员使用的余额返回会员账户里
					accountService.returnBalanceByRefund(saleOrder.getMember(), saleOrder.getBalance(), saleOrder);
				}
			}
		}
	}

	/**
	 * 退款回执
	 */
	@Override
	public void refundReceiptByTask() throws Exception {
		// 查询待回执的退款单
		List<AfterSaleOrder> dbAfterSaleOrders = afterSaleOrderDao.findByRefundPayStateAndDeleted(OrderEnum.REFUND_PAY_STATE_WAIT_RECEIPT.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(dbAfterSaleOrders)) {
			LOG.info("没有待回执的退款单");
		}
		LOG.info("待回执的退款单为{}个", dbAfterSaleOrders.size());
		for (AfterSaleOrder afterSaleOrder : dbAfterSaleOrders) {
			// 根据订单支付渠道 执行退款回执
			if (OrderEnum.PAYMENT_CHANNEL_SP.getCode().equals(afterSaleOrder.getSaleOrder().getPaymentChannel())
					|| OrderEnum.PAYMENT_CHANNEL_MP.getCode().equals(afterSaleOrder.getSaleOrder().getPaymentChannel())
					|| OrderEnum.PAYMENT_CHANNEL_APP_WECHAT.getCode().equals(afterSaleOrder.getSaleOrder().getPaymentChannel())) {
				weChatNotifyService.refundQueryForWeChat(afterSaleOrder);
			} else if (OrderEnum.PAYMENT_CHANNEL_APP_ALIPAY.getCode().equals(afterSaleOrder.getSaleOrder().getPaymentChannel())) {
				alipayService.refundQueryForAlipay(afterSaleOrder);
			} else {
				LOG.error("售后订单ID={}，未知的支付渠道（{}），无法退款", afterSaleOrder.getId(), afterSaleOrder.getSaleOrder().getPaymentChannel());
			}
		}
	}

}
