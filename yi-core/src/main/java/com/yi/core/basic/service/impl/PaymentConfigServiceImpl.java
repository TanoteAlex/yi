/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

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

import com.yi.core.basic.dao.PaymentConfigDao;
import com.yi.core.basic.domain.bo.PaymentConfigBo;
import com.yi.core.basic.domain.entity.PaymentConfig;
import com.yi.core.basic.domain.entity.PaymentConfig_;
import com.yi.core.basic.domain.simple.PaymentConfigSimple;
import com.yi.core.basic.domain.vo.PaymentConfigListVo;
import com.yi.core.basic.domain.vo.PaymentConfigVo;
import com.yi.core.basic.service.IPaymentConfigService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.ValueUtils;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class PaymentConfigServiceImpl implements IPaymentConfigService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(PaymentConfigServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private PaymentConfigDao paymentConfigDao;

	private EntityListVoBoSimpleConvert<PaymentConfig, PaymentConfigBo, PaymentConfigVo, PaymentConfigSimple, PaymentConfigListVo> paymentConfigConvert;

	@Override
	public PaymentConfig getPaymentConfigById(int paymentConfigId) {
		return paymentConfigDao.getOne(paymentConfigId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PaymentConfigVo getPaymentConfigVoById(int paymentConfigId) {

		return paymentConfigConvert.toVo(this.paymentConfigDao.getOne(paymentConfigId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PaymentConfigVo getPaymentConfigListVoById(int paymentConfigId) {
		return paymentConfigConvert.toVo(this.paymentConfigDao.getOne(paymentConfigId));
	}

	@Override
	public PaymentConfigVo addPaymentConfig(PaymentConfig paymentConfig) {
		paymentConfig.setCreateTime(new Date());
		paymentConfig.setGuid(ValueUtils.generateGUID());
		return paymentConfigConvert.toVo(paymentConfigDao.save(paymentConfig));
	}

	@Override
	public PaymentConfigVo addPaymentConfig(PaymentConfigBo paymentConfigBo) {
		paymentConfigBo.setCreateTime(new Date());
		paymentConfigBo.setGuid(ValueUtils.generateGUID());
		return paymentConfigConvert.toVo(paymentConfigDao.save(paymentConfigConvert.toEntity(paymentConfigBo)));
	}

	@Override
	public PaymentConfigVo updatePaymentConfig(PaymentConfig paymentConfig) {

		PaymentConfig dbPaymentConfig = paymentConfigDao.getOne(paymentConfig.getId());
		AttributeReplication.copying(paymentConfig, dbPaymentConfig, PaymentConfig_.payType, PaymentConfig_.appId, PaymentConfig_.method, PaymentConfig_.charset,
				PaymentConfig_.signType, PaymentConfig_.sign, PaymentConfig_.version, PaymentConfig_.notifyUrl, PaymentConfig_.mchId, PaymentConfig_.appKey,
				PaymentConfig_.appSecret, PaymentConfig_.createTime);
		return paymentConfigConvert.toVo(dbPaymentConfig);
	}

	@Override
	public PaymentConfigVo updatePaymentConfig(PaymentConfigBo paymentConfigBo) {
		PaymentConfig dbPaymentConfig = paymentConfigDao.getOne(paymentConfigBo.getId());
		AttributeReplication.copying(paymentConfigBo, dbPaymentConfig, PaymentConfig_.payType, PaymentConfig_.appId, PaymentConfig_.method, PaymentConfig_.charset,
				PaymentConfig_.signType, PaymentConfig_.sign, PaymentConfig_.version, PaymentConfig_.notifyUrl, PaymentConfig_.mchId, PaymentConfig_.appKey,
				PaymentConfig_.appSecret, PaymentConfig_.createTime);
		return paymentConfigConvert.toVo(dbPaymentConfig);
	}

	@Override
	public void removePaymentConfigById(int paymentConfigId) {
		paymentConfigDao.deleteById(paymentConfigId);
	}

	@Override
	public Page<PaymentConfig> query(Pagination<PaymentConfig> query) {
		query.setEntityClazz(PaymentConfig.class);
		Page<PaymentConfig> page = paymentConfigDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<PaymentConfigListVo> queryListVo(Pagination<PaymentConfig> query) {

		Page<PaymentConfig> pages = this.query(query);

		List<PaymentConfigListVo> vos = paymentConfigConvert.toListVos(pages.getContent());
		return new PageImpl<PaymentConfigListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.paymentConfigConvert = new EntityListVoBoSimpleConvert<PaymentConfig, PaymentConfigBo, PaymentConfigVo, PaymentConfigSimple, PaymentConfigListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<PaymentConfig, PaymentConfigVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PaymentConfig, PaymentConfigVo>(beanConvertManager) {
					@Override
					protected void postConvert(PaymentConfigVo PaymentConfigVo, PaymentConfig PaymentConfig) {

					}
				};
			}

			@Override
			protected BeanConvert<PaymentConfig, PaymentConfigListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PaymentConfig, PaymentConfigListVo>(beanConvertManager) {
					@Override
					protected void postConvert(PaymentConfigListVo PaymentConfigListVo, PaymentConfig PaymentConfig) {

					}
				};
			}

			@Override
			protected BeanConvert<PaymentConfig, PaymentConfigBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PaymentConfig, PaymentConfigBo>(beanConvertManager) {
					@Override
					protected void postConvert(PaymentConfigBo PaymentConfigBo, PaymentConfig PaymentConfig) {

					}
				};
			}

			@Override
			protected BeanConvert<PaymentConfigBo, PaymentConfig> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PaymentConfigBo, PaymentConfig>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<PaymentConfig, PaymentConfigSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PaymentConfig, PaymentConfigSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<PaymentConfigSimple, PaymentConfig> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PaymentConfigSimple, PaymentConfig>(beanConvertManager) {
					@Override
					public PaymentConfig convert(PaymentConfigSimple PaymentConfigSimple) throws BeansException {
						return paymentConfigDao.getOne(PaymentConfigSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}
}
