/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.service.impl;

import com.yi.core.supplier.dao.SupplierAccountRecordDao;
import com.yi.core.supplier.domain.bo.SupplierAccountRecordBo;
import com.yi.core.supplier.domain.entity.SupplierAccountRecord;
import com.yi.core.supplier.domain.entity.SupplierAccountRecord_;
import com.yi.core.supplier.domain.simple.SupplierAccountRecordSimple;
import com.yi.core.supplier.domain.vo.SupplierAccountRecordListVo;
import com.yi.core.supplier.domain.vo.SupplierAccountRecordVo;
import com.yi.core.supplier.service.ISupplierAccountRecordService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.beans.BeansException;
import com.yihz.common.persistence.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.InitializingBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yihz.common.convert.BeanConvertManager;

import javax.annotation.Resource;
import java.util.List;

/**
 * 供应商账户记录
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SupplierAccountRecordServiceImpl implements ISupplierAccountRecordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SupplierAccountRecordServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SupplierAccountRecordDao supplierAccountRecordDao;

	private EntityListVoBoSimpleConvert<SupplierAccountRecord, SupplierAccountRecordBo, SupplierAccountRecordVo, SupplierAccountRecordSimple, SupplierAccountRecordListVo> supplierAccountRecordConvert;

	/**
	 * 分页查询SupplierAccountRecord
	 **/
	@Override
	public Page<SupplierAccountRecord> query(Pagination<SupplierAccountRecord> query) {
		query.setEntityClazz(SupplierAccountRecord.class);
		Page<SupplierAccountRecord> page = supplierAccountRecordDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 创建SupplierAccountRecord
	 **/
	@Override
	public SupplierAccountRecordListVo addSupplierAccountRecord(SupplierAccountRecordBo supplierAccountRecordBo) {
		return supplierAccountRecordConvert.toListVo(supplierAccountRecordDao.save(supplierAccountRecordConvert.toEntity(supplierAccountRecordBo)));
	}

	/**
	 * 更新SupplierAccountRecord
	 **/
	@Override
	public SupplierAccountRecordListVo updateSupplierAccountRecord(SupplierAccountRecordBo supplierAccountRecordBo) {
		SupplierAccountRecord dbSupplierAccountRecord = supplierAccountRecordDao.getOne(supplierAccountRecordBo.getId());
		AttributeReplication.copying(supplierAccountRecordBo, dbSupplierAccountRecord, SupplierAccountRecord_.guid, SupplierAccountRecord_.supplier,
				SupplierAccountRecord_.operateType, SupplierAccountRecord_.serialNo, SupplierAccountRecord_.tradeAmount, SupplierAccountRecord_.balance,
				SupplierAccountRecord_.tradeType, SupplierAccountRecord_.tradeTime, SupplierAccountRecord_.remark);
		return supplierAccountRecordConvert.toListVo(dbSupplierAccountRecord);
	}

	/**
	 * 删除SupplierAccountRecord
	 **/
	@Override
	public void removeSupplierAccountRecordById(int id) {
		supplierAccountRecordDao.deleteById(id);
	}

	/**
	 * 根据ID得到SupplierAccountRecordBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierAccountRecordBo getSupplierAccountRecordBoById(int id) {
		return supplierAccountRecordConvert.toBo(this.supplierAccountRecordDao.getOne(id));
	}

	/**
	 * 根据ID得到SupplierAccountRecordVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierAccountRecordVo getSupplierAccountRecordVoById(int id) {
		return supplierAccountRecordConvert.toVo(this.supplierAccountRecordDao.getOne(id));
	}

	/**
	 * 根据ID得到SupplierAccountRecordListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierAccountRecordListVo getListVoById(int id) {
		return supplierAccountRecordConvert.toListVo(this.supplierAccountRecordDao.getOne(id));
	}

	/**
	 * 分页查询: SupplierAccountRecord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierAccountRecordListVo> queryListVo(Pagination<SupplierAccountRecord> query) {
		Page<SupplierAccountRecord> pages = this.query(query);
		List<SupplierAccountRecordListVo> vos = supplierAccountRecordConvert.toListVos(pages.getContent());
		return new PageImpl<SupplierAccountRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	protected void initConvert() {
		this.supplierAccountRecordConvert = new EntityListVoBoSimpleConvert<SupplierAccountRecord, SupplierAccountRecordBo, SupplierAccountRecordVo, SupplierAccountRecordSimple, SupplierAccountRecordListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<SupplierAccountRecord, SupplierAccountRecordVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccountRecord, SupplierAccountRecordVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierAccountRecordVo supplierAccountRecordVo, SupplierAccountRecord supplierAccountRecord) {
					}
				};
			}

			@Override
			protected BeanConvert<SupplierAccountRecord, SupplierAccountRecordListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccountRecord, SupplierAccountRecordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierAccountRecordListVo supplierAccountRecordListVo, SupplierAccountRecord supplierAccountRecord) {
					}
				};
			}

			@Override
			protected BeanConvert<SupplierAccountRecord, SupplierAccountRecordBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccountRecord, SupplierAccountRecordBo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierAccountRecordBo supplierAccountRecordBo, SupplierAccountRecord supplierAccountRecord) {
					}
				};
			}

			@Override
			protected BeanConvert<SupplierAccountRecordBo, SupplierAccountRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccountRecordBo, SupplierAccountRecord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierAccountRecord, SupplierAccountRecordSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccountRecord, SupplierAccountRecordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierAccountRecordSimple, SupplierAccountRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierAccountRecordSimple, SupplierAccountRecord>(beanConvertManager) {
					@Override
					public SupplierAccountRecord convert(SupplierAccountRecordSimple supplierAccountRecordSimple) throws BeansException {
						return supplierAccountRecordDao.getOne(supplierAccountRecordSimple.getId());
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
