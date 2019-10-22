/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service.impl;

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

import com.yi.core.member.dao.ConsumeRecordDao;
import com.yi.core.member.domain.bo.ConsumeRecordBo;
import com.yi.core.member.domain.entity.ConsumeRecord;
import com.yi.core.member.domain.entity.ConsumeRecord_;
import com.yi.core.member.domain.simple.ConsumeRecordSimple;
import com.yi.core.member.domain.vo.ConsumeRecordListVo;
import com.yi.core.member.domain.vo.ConsumeRecordVo;
import com.yi.core.member.service.IConsumeRecordService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class ConsumeRecordServiceImpl implements IConsumeRecordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(ConsumeRecordServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private ConsumeRecordDao consumeRecordDao;

	private EntityListVoBoSimpleConvert<ConsumeRecord, ConsumeRecordBo, ConsumeRecordVo, ConsumeRecordSimple, ConsumeRecordListVo> consumeRecordConvert;

	@Override
	public ConsumeRecord getConsumeRecordById(int consumeRecordId) {
		return consumeRecordDao.getOne(consumeRecordId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ConsumeRecordVo getConsumeRecordVoById(int consumeRecordId) {

		return consumeRecordConvert.toVo(this.consumeRecordDao.getOne(consumeRecordId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ConsumeRecordListVo getConsumeRecordListVoById(int consumeRecordId) {
		return consumeRecordConvert.toListVo(this.consumeRecordDao.getOne(consumeRecordId));
	}

	@Override
	public ConsumeRecordVo addConsumeRecord(ConsumeRecord consumeRecord) {
		return consumeRecordConvert.toVo(consumeRecordDao.save(consumeRecord));
	}

	@Override
	public ConsumeRecordListVo addConsumeRecord(ConsumeRecordBo consumeRecordBo) {
		return consumeRecordConvert.toListVo(consumeRecordDao.save(consumeRecordConvert.toEntity(consumeRecordBo)));
	}

	@Override
	public ConsumeRecordVo updateConsumeRecord(ConsumeRecord consumeRecord) {

		ConsumeRecord dbConsumeRecord = consumeRecordDao.getOne(consumeRecord.getId());
		AttributeReplication.copying(consumeRecord, dbConsumeRecord, ConsumeRecord_.tradeNo,
				ConsumeRecord_.orderNo, ConsumeRecord_.consignee, ConsumeRecord_.payAmount, ConsumeRecord_.finishTime,
				ConsumeRecord_.remark);
		return consumeRecordConvert.toVo(dbConsumeRecord);
	}

	@Override
	public ConsumeRecordListVo updateConsumeRecord(ConsumeRecordBo consumeRecordBo) {
		ConsumeRecord dbConsumeRecord = consumeRecordDao.getOne(consumeRecordBo.getId());
		AttributeReplication.copying(consumeRecordBo, dbConsumeRecord, ConsumeRecord_.tradeNo,
				ConsumeRecord_.orderNo, ConsumeRecord_.consignee, ConsumeRecord_.payAmount, ConsumeRecord_.finishTime,
				ConsumeRecord_.remark);
		return consumeRecordConvert.toListVo(dbConsumeRecord);
	}

	@Override
	public void removeConsumeRecordById(int consumeRecordId) {
		consumeRecordDao.deleteById(consumeRecordId);
	}

	@Override
	public Page<ConsumeRecord> query(Pagination<ConsumeRecord> query) {
		query.setEntityClazz(ConsumeRecord.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(ConsumeRecord_.finishTime)));
		}));
		Page<ConsumeRecord> page = consumeRecordDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ConsumeRecordListVo> queryListVo(Pagination<ConsumeRecord> query) {

		Page<ConsumeRecord> pages = this.query(query);

		List<ConsumeRecordListVo> vos = consumeRecordConvert.toListVos(pages.getContent());
		return new PageImpl<ConsumeRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.consumeRecordConvert = new EntityListVoBoSimpleConvert<ConsumeRecord, ConsumeRecordBo, ConsumeRecordVo, ConsumeRecordSimple, ConsumeRecordListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<ConsumeRecord, ConsumeRecordVo> createEntityToVoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ConsumeRecord, ConsumeRecordVo>(beanConvertManager) {
					@Override
					protected void postConvert(ConsumeRecordVo ConsumeRecordVo, ConsumeRecord ConsumeRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<ConsumeRecord, ConsumeRecordListVo> createEntityToListVoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ConsumeRecord, ConsumeRecordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(ConsumeRecordListVo ConsumeRecordListVo, ConsumeRecord ConsumeRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<ConsumeRecord, ConsumeRecordBo> createEntityToBoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ConsumeRecord, ConsumeRecordBo>(beanConvertManager) {
					@Override
					protected void postConvert(ConsumeRecordBo ConsumeRecordBo, ConsumeRecord ConsumeRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<ConsumeRecordBo, ConsumeRecord> createBoToEntityConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ConsumeRecordBo, ConsumeRecord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<ConsumeRecord, ConsumeRecordSimple> createEntityToSimpleConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ConsumeRecord, ConsumeRecordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<ConsumeRecordSimple, ConsumeRecord> createSimpleToEntityConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ConsumeRecordSimple, ConsumeRecord>(beanConvertManager) {
					@Override
					public ConsumeRecord convert(ConsumeRecordSimple ConsumeRecordSimple) throws BeansException {
						return consumeRecordDao.getOne(ConsumeRecordSimple.getId());
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
