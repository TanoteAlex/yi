/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

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

import com.yi.core.basic.dao.SmsRecordDao;
import com.yi.core.basic.domain.bo.SmsRecordBo;
import com.yi.core.basic.domain.entity.SmsRecord;
import com.yi.core.basic.domain.entity.SmsRecord_;
import com.yi.core.basic.domain.simple.SmsRecordSimple;
import com.yi.core.basic.domain.vo.SmsRecordListVo;
import com.yi.core.basic.domain.vo.SmsRecordVo;
import com.yi.core.basic.service.ISmsRecordService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 短信记录
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SmsRecordServiceImpl implements ISmsRecordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SmsRecordServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SmsRecordDao smsRecordDao;

	private EntityListVoBoSimpleConvert<SmsRecord, SmsRecordBo, SmsRecordVo, SmsRecordSimple, SmsRecordListVo> smsRecordConvert;

	@Override
	public SmsRecordVo getSmsRecordById(int smsRecordId) {
		return smsRecordConvert.toVo(smsRecordDao.getOne(smsRecordId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SmsRecordVo getSmsRecordVoById(int smsRecordId) {

		return smsRecordConvert.toVo(this.smsRecordDao.getOne(smsRecordId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SmsRecordVo getSmsRecordListVoById(int smsRecordId) {
		return smsRecordConvert.toVo(this.smsRecordDao.getOne(smsRecordId));
	}

	@Override
	public SmsRecordVo addSmsRecord(SmsRecord smsRecord) {
		return smsRecordConvert.toVo(smsRecordDao.save(smsRecord));
	}

	@Override
	public SmsRecordVo addSmsRecord(SmsRecordBo smsRecordBo) {
		return smsRecordConvert.toVo(smsRecordDao.save(smsRecordConvert.toEntity(smsRecordBo)));
	}

	@Override
	public SmsRecordVo updateSmsRecord(SmsRecord smsRecord) {

		SmsRecord dbSmsRecord = smsRecordDao.getOne(smsRecord.getId());
		AttributeReplication.copying(smsRecord, dbSmsRecord, SmsRecord_.phone, SmsRecord_.templateCode, SmsRecord_.templateParam, SmsRecord_.smsState, SmsRecord_.sendContent,
				SmsRecord_.sendDate, SmsRecord_.sendCode, SmsRecord_.sendMessage, SmsRecord_.sendBizId, SmsRecord_.receiveDate, SmsRecord_.smsOutId, SmsRecord_.errorCode);
		return smsRecordConvert.toVo(dbSmsRecord);
	}

	@Override
	public SmsRecordVo updateSmsRecord(SmsRecordBo smsRecordBo) {
		SmsRecord dbSmsRecord = smsRecordDao.getOne(smsRecordBo.getId());
		AttributeReplication.copying(smsRecordBo, dbSmsRecord, SmsRecord_.phone, SmsRecord_.templateCode, SmsRecord_.templateParam, SmsRecord_.smsState, SmsRecord_.sendContent,
				SmsRecord_.sendDate, SmsRecord_.sendCode, SmsRecord_.sendMessage, SmsRecord_.sendBizId, SmsRecord_.receiveDate, SmsRecord_.smsOutId, SmsRecord_.errorCode);
		return smsRecordConvert.toVo(dbSmsRecord);
	}

	@Override
	public void removeSmsRecordById(int smsRecordId) {
		smsRecordDao.deleteById(smsRecordId);
	}

	@Override
	public Page<SmsRecord> query(Pagination<SmsRecord> query) {
		query.setEntityClazz(SmsRecord.class);
		Page<SmsRecord> page = smsRecordDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SmsRecordListVo> queryListVo(Pagination<SmsRecord> query) {

		Page<SmsRecord> pages = this.query(query);

		List<SmsRecordListVo> vos = smsRecordConvert.toListVos(pages.getContent());
		return new PageImpl<SmsRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.smsRecordConvert = new EntityListVoBoSimpleConvert<SmsRecord, SmsRecordBo, SmsRecordVo, SmsRecordSimple, SmsRecordListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<SmsRecord, SmsRecordVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SmsRecord, SmsRecordVo>(beanConvertManager) {
					@Override
					protected void postConvert(SmsRecordVo SmsRecordVo, SmsRecord SmsRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<SmsRecord, SmsRecordListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SmsRecord, SmsRecordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SmsRecordListVo SmsRecordListVo, SmsRecord SmsRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<SmsRecord, SmsRecordBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SmsRecord, SmsRecordBo>(beanConvertManager) {
					@Override
					protected void postConvert(SmsRecordBo SmsRecordBo, SmsRecord SmsRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<SmsRecordBo, SmsRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SmsRecordBo, SmsRecord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SmsRecord, SmsRecordSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SmsRecord, SmsRecordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SmsRecordSimple, SmsRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SmsRecordSimple, SmsRecord>(beanConvertManager) {
					@Override
					public SmsRecord convert(SmsRecordSimple SmsRecordSimple) throws BeansException {
						return smsRecordDao.getOne(SmsRecordSimple.getId());
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
	 * 查询大于 给定发送日期的 待回执 短信
	 * 
	 * @param smsRecord.sendDate
	 * @param smsRecord.smsState
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SmsRecord> queryWaitReportSmsRecords(SmsRecord smsRecord) {
		return smsRecordDao.findBySendDateAfterAndSmsStateIs(smsRecord.getSendDate(), smsRecord.getSmsState());
	}
}
