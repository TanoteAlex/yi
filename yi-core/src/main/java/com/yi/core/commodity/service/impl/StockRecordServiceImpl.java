package com.yi.core.commodity.service.impl;/*
											* Powered By [yihz-framework]
											* Web Site: yihz
											* Since 2018 - 2018
											*/

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

import com.yi.core.commodity.dao.StockRecordDao;
import com.yi.core.commodity.domain.bo.StockRecordBo;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.domain.entity.StockRecord;
import com.yi.core.commodity.domain.entity.StockRecord_;
import com.yi.core.commodity.domain.simple.StockRecordSimple;
import com.yi.core.commodity.domain.vo.StockRecordListVo;
import com.yi.core.commodity.domain.vo.StockRecordVo;
import com.yi.core.commodity.service.IStockRecordService;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
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
public class StockRecordServiceImpl implements IStockRecordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(StockRecordServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private StockRecordDao stockRecordDao;

	private EntityListVoBoSimpleConvert<StockRecord, StockRecordBo, StockRecordVo, StockRecordSimple, StockRecordListVo> stockRecordConvert;

	@Override
	public StockRecord getStockRecordById(int stockRecordId) {
		return stockRecordDao.getOne(stockRecordId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public StockRecordVo getStockRecordVoById(int stockRecordId) {

		return stockRecordConvert.toVo(this.stockRecordDao.getOne(stockRecordId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public StockRecordListVo getStockRecordListVoById(int stockRecordId) {
		return stockRecordConvert.toListVo(this.stockRecordDao.getOne(stockRecordId));
	}

	@Override
	public StockRecord addStockRecord(StockRecord stockRecord) {
		return stockRecordDao.save(stockRecord);
	}

	@Override
	public StockRecordListVo addStockRecord(StockRecordBo stockRecordBo) {
		return stockRecordConvert.toListVo(stockRecordDao.save(stockRecordConvert.toEntity(stockRecordBo)));
	}

	@Override
	public StockRecord updateStockRecord(StockRecord stockRecord) {

		StockRecord dbStockRecord = stockRecordDao.getOne(stockRecord.getId());
		AttributeReplication.copying(stockRecord, dbStockRecord, StockRecord_.guid, StockRecord_.member, StockRecord_.saleOrder, StockRecord_.product, StockRecord_.useQuantity,
				StockRecord_.remark, StockRecord_.createTime);
		return dbStockRecord;
	}

	@Override
	public StockRecordListVo updateStockRecord(StockRecordBo stockRecordBo) {
		StockRecord dbStockRecord = stockRecordDao.getOne(stockRecordBo.getId());
		AttributeReplication.copying(stockRecordBo, dbStockRecord, StockRecord_.guid, StockRecord_.member, StockRecord_.saleOrder, StockRecord_.product, StockRecord_.useQuantity,
				StockRecord_.remark, StockRecord_.createTime);
		return stockRecordConvert.toListVo(dbStockRecord);
	}

	@Override
	public void removeStockRecordById(int stockRecordId) {
		stockRecordDao.delete(stockRecordDao.getOne(stockRecordId));
	}

	@Override
	public Page<StockRecord> query(Pagination<StockRecord> query) {
		query.setEntityClazz(StockRecord.class);
		Page<StockRecord> page = stockRecordDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<StockRecordListVo> queryListVo(Pagination<StockRecord> query) {

		Page<StockRecord> pages = this.query(query);

		List<StockRecordListVo> vos = stockRecordConvert.toListVos(pages.getContent());
		return new PageImpl<StockRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.stockRecordConvert = new EntityListVoBoSimpleConvert<StockRecord, StockRecordBo, StockRecordVo, StockRecordSimple, StockRecordListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<StockRecord, StockRecordVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<StockRecord, StockRecordVo>(beanConvertManager) {
					@Override
					protected void postConvert(StockRecordVo StockRecordVo, StockRecord StockRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<StockRecord, StockRecordListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<StockRecord, StockRecordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(StockRecordListVo StockRecordListVo, StockRecord StockRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<StockRecord, StockRecordBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<StockRecord, StockRecordBo>(beanConvertManager) {
					@Override
					protected void postConvert(StockRecordBo StockRecordBo, StockRecord StockRecord) {

					}
				};
			}

			@Override
			protected BeanConvert<StockRecordBo, StockRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<StockRecordBo, StockRecord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<StockRecord, StockRecordSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<StockRecord, StockRecordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<StockRecordSimple, StockRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<StockRecordSimple, StockRecord>(beanConvertManager) {
					@Override
					public StockRecord convert(StockRecordSimple StockRecordSimple) throws BeansException {
						return stockRecordDao.getOne(StockRecordSimple.getId());
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
	public void addStockRecordByOrder(Member member, SaleOrder saleOrder, Product product, Integer useQuantity, String remark) {
		if (member != null && saleOrder != null & product != null) {
			StockRecord stockRecord = new StockRecord();
			stockRecord.setMember(member);
			stockRecord.setSaleOrder(saleOrder);
			stockRecord.setProduct(product);
			stockRecord.setUseQuantity(useQuantity.intValue());
			stockRecord.setRemark(remark);
			stockRecordDao.save(stockRecord);
		}
	}
}
