/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.yi.core.order.dao.PayRecordDao;
import com.yi.core.order.domain.bo.PayRecordBo;
import com.yi.core.order.domain.entity.PayRecord;
import com.yi.core.order.domain.entity.PayRecord_;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.simple.PayRecordSimple;
import com.yi.core.order.domain.vo.PayRecordListVo;
import com.yi.core.order.domain.vo.PayRecordVo;
import com.yi.core.order.service.IPayRecordService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.date.DateUtils;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class PayRecordServiceImpl implements IPayRecordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(PayRecordServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private PayRecordDao payRecordDao;

	private EntityListVoBoSimpleConvert<PayRecord, PayRecordBo, PayRecordVo, PayRecordSimple, PayRecordListVo> payRecordConvert;

	@Override
	public PayRecord getPayRecordById(int payRecordId) {
		return payRecordDao.getOne(payRecordId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayRecordVo getPayRecordVoById(int payRecordId) {

		return payRecordConvert.toVo(this.payRecordDao.getOne(payRecordId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PayRecordListVo getPayRecordListVoById(int payRecordId) {
		return payRecordConvert.toListVo(this.payRecordDao.getOne(payRecordId));
	}

	@Override
	public PayRecord addPayRecord(PayRecord payRecord) {
		return payRecordDao.save(payRecord);
	}

	@Override
	public PayRecordListVo addPayRecord(PayRecordBo payRecordBo) {
		return payRecordConvert.toListVo(payRecordDao.save(payRecordConvert.toEntity(payRecordBo)));
	}

	@Override
	public PayRecord updatePayRecord(PayRecord payRecord) {

		PayRecord dbPayRecord = payRecordDao.getOne(payRecord.getId());
		AttributeReplication.copying(payRecord, dbPayRecord, PayRecord_.guid, PayRecord_.member, PayRecord_.saleOrder, PayRecord_.orderAmount, PayRecord_.discountAmount,
				PayRecord_.payAmount, PayRecord_.tradeType, PayRecord_.tradeState, PayRecord_.tradeStateDesc, PayRecord_.tradeNo, PayRecord_.paymentTime, PayRecord_.remark,
				PayRecord_.createTime);
		return dbPayRecord;
	}

	@Override
	public PayRecordVo updatePayRecord(PayRecordBo payRecordBo) {
		PayRecord dbPayRecord = payRecordDao.getOne(payRecordBo.getId());
		AttributeReplication.copying(payRecordBo, dbPayRecord, PayRecord_.guid, PayRecord_.member, PayRecord_.saleOrder, PayRecord_.orderAmount, PayRecord_.discountAmount,
				PayRecord_.payAmount, PayRecord_.tradeType, PayRecord_.tradeState, PayRecord_.tradeStateDesc, PayRecord_.tradeNo, PayRecord_.paymentTime, PayRecord_.remark,
				PayRecord_.createTime);
		return payRecordConvert.toVo(dbPayRecord);
	}

	@Override
	public void removePayRecordById(int payRecordId) {
		payRecordDao.deleteById(payRecordId);
	}

	@Override
	public Page<PayRecord> query(Pagination<PayRecord> query) {
		query.setEntityClazz(PayRecord.class);
		Page<PayRecord> page = payRecordDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<PayRecordListVo> queryListVo(Pagination<PayRecord> query) {

		Page<PayRecord> pages = this.query(query);

		List<PayRecordListVo> vos = payRecordConvert.toListVos(pages.getContent());
		return new PageImpl<PayRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.payRecordConvert = new EntityListVoBoSimpleConvert<PayRecord, PayRecordBo, PayRecordVo, PayRecordSimple, PayRecordListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<PayRecord, PayRecordVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PayRecord, PayRecordVo>(beanConvertManager) {
					@Override
					protected void postConvert(PayRecordVo PayRecordVo, PayRecord PayRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<PayRecord, PayRecordListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PayRecord, PayRecordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(PayRecordListVo PayRecordListVo, PayRecord PayRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<PayRecord, PayRecordBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PayRecord, PayRecordBo>(beanConvertManager) {
					@Override
					protected void postConvert(PayRecordBo PayRecordBo, PayRecord PayRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<PayRecordBo, PayRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PayRecordBo, PayRecord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<PayRecord, PayRecordSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PayRecord, PayRecordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<PayRecordSimple, PayRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PayRecordSimple, PayRecord>(beanConvertManager) {
					@Override
					public PayRecord convert(PayRecordSimple PayRecordSimple) throws BeansException {
						return payRecordDao.getOne(PayRecordSimple.getId());
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
	public void addPayRecordByOrderForWeChat(SaleOrder saleOrder, SortedMap<String, String> resultMap) {
		if (saleOrder != null && MapUtils.isNotEmpty(resultMap)) {
			// 支付记录
			PayRecord payRecord = new PayRecord();
			payRecord.setMember(saleOrder.getMember());
			payRecord.setSaleOrder(saleOrder);
			payRecord.setOrderAmount(saleOrder.getOrderAmount());
			payRecord.setPayAmount(saleOrder.getPayAmount());
			payRecord.setActualPayAmount(resultMap.get("total_fee"));
			payRecord.setDiscountAmount(
					Optional.ofNullable(saleOrder.getCouponAmount()).orElse(BigDecimal.ZERO).add(Optional.ofNullable(saleOrder.getVoucherAmount()).orElse(BigDecimal.ZERO)));
			payRecord.setTradeType(resultMap.get("trade_type"));
			payRecord.setTradeState(resultMap.get("trade_state"));
			payRecord.setTradeStateDesc(resultMap.get("trade_state_desc"));
			payRecord.setTradeNo(resultMap.get("transaction_id"));
			payRecord.setOutTradeNo(saleOrder.getPayOrderNo());
			payRecord.setPaymentTime(resultMap.get("time_end"));
			payRecord.setRemark("微信支付成功");
			payRecordDao.save(payRecord);
		}
	}

	@Override
	public void addPayRecordByOrderForBalance(SaleOrder saleOrder) {
		if (saleOrder != null) {
			// 支付记录
			PayRecord payRecord = new PayRecord();
			payRecord.setMember(saleOrder.getMember());
			payRecord.setSaleOrder(saleOrder);
			payRecord.setOrderAmount(saleOrder.getOrderAmount());
			payRecord.setPayAmount(saleOrder.getPayAmount());
			payRecord.setActualPayAmount("0");
			payRecord.setDiscountAmount(Optional.ofNullable(saleOrder.getCouponAmount()).orElse(BigDecimal.ZERO)
					.add(Optional.ofNullable(saleOrder.getVoucherAmount()).orElse(BigDecimal.ZERO)).add(Optional.ofNullable(saleOrder.getBalance()).orElse(BigDecimal.ZERO)));
			payRecord.setTradeType("余额");
			payRecord.setTradeState("SUCCESS");
			payRecord.setTradeStateDesc("支付成功");
			payRecord.setTradeNo("");
			payRecord.setOutTradeNo(saleOrder.getPayOrderNo());
			payRecord.setPaymentTime(DateUtils.getFormatTimestamp(saleOrder.getPaymentTime()));
			payRecord.setRemark("余额支付成功");
			payRecordDao.save(payRecord);
		}
	}

	@Override
	public void addPayRecordByOrderForAlipay(SaleOrder saleOrder, Map<String, String> resultMap) {
		if (saleOrder != null && MapUtils.isNotEmpty(resultMap)) {
			// 支付记录
			PayRecord payRecord = new PayRecord();
			payRecord.setMember(saleOrder.getMember());
			payRecord.setSaleOrder(saleOrder);
			payRecord.setOrderAmount(saleOrder.getOrderAmount());
			payRecord.setPayAmount(saleOrder.getPayAmount());
			payRecord.setActualPayAmount(resultMap.get("total_amount"));
			payRecord.setDiscountAmount(
					Optional.ofNullable(saleOrder.getCouponAmount()).orElse(BigDecimal.ZERO).add(Optional.ofNullable(saleOrder.getVoucherAmount()).orElse(BigDecimal.ZERO)));
			payRecord.setTradeType("alipay");
			payRecord.setTradeState(resultMap.get("trade_status"));
			payRecord.setTradeStateDesc(resultMap.get("trade_status"));
			payRecord.setTradeNo(resultMap.get("trade_no"));
			payRecord.setOutTradeNo(saleOrder.getPayOrderNo());
			payRecord.setPaymentTime(resultMap.get("gmt_payment"));
			payRecord.setRemark("支付宝支付成功");
			payRecordDao.save(payRecord);
		}
	}

	@Override
	public void addPayRecordByOrderForAlipay(SaleOrder saleOrder, AlipayTradeQueryResponse response) {
		if (saleOrder != null && response != null) {
			// 支付记录
			PayRecord payRecord = new PayRecord();
			payRecord.setMember(saleOrder.getMember());
			payRecord.setSaleOrder(saleOrder);
			payRecord.setOrderAmount(saleOrder.getOrderAmount());
			payRecord.setPayAmount(saleOrder.getPayAmount());
			payRecord.setActualPayAmount(response.getReceiptAmount());
			payRecord.setDiscountAmount(
					Optional.ofNullable(saleOrder.getCouponAmount()).orElse(BigDecimal.ZERO).add(Optional.ofNullable(saleOrder.getVoucherAmount()).orElse(BigDecimal.ZERO)));
			payRecord.setTradeType("alipay");
			payRecord.setTradeState(response.getTradeStatus());
			payRecord.setTradeStateDesc(response.getTradeStatus());
			payRecord.setTradeNo(response.getTradeNo());
			payRecord.setOutTradeNo(saleOrder.getPayOrderNo());
			payRecord.setPaymentTime(DateUtils.getFormatTimestamp(response.getSendPayDate()));
			payRecord.setRemark("支付宝支付成功");
			payRecordDao.save(payRecord);
		}
	}
}
