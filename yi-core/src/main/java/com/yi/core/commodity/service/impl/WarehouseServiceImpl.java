/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;

import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yihz.common.exception.BusinessException;
import org.hibernate.criterion.CriteriaQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.commodity.dao.WarehouseDao;
import com.yi.core.commodity.domain.bo.WarehouseBo;
import com.yi.core.commodity.domain.entity.Warehouse;
import com.yi.core.commodity.domain.entity.Warehouse_;
import com.yi.core.commodity.domain.simple.WarehouseSimple;
import com.yi.core.commodity.domain.vo.WarehouseListVo;
import com.yi.core.commodity.domain.vo.WarehouseVo;
import com.yi.core.commodity.service.IWarehouseService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 仓库
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class WarehouseServiceImpl implements IWarehouseService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(WarehouseServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private WarehouseDao warehouseDao;

	private EntityListVoBoSimpleConvert<Warehouse, WarehouseBo, WarehouseVo, WarehouseSimple, WarehouseListVo>
			warehouseConvert;

	/**
	 * 分页查询Warehouse
	 **/
	@Override
	public Page<Warehouse> query(Pagination<Warehouse> query) {
		query.setEntityClazz(Warehouse.class);
		query.setPredicate((root, CriteriaQuery, CriteriaBuilder, list, list1) -> {
			list.add(CriteriaBuilder.and(CriteriaBuilder.equal(root.get(Warehouse_.deleted), Deleted.DEL_FALSE)));
		});
		Page<Warehouse> page = warehouseDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: Warehouse
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<WarehouseListVo> queryListVo(Pagination<Warehouse> query) {
		Page<Warehouse> pages = this.query(query);
		List<WarehouseListVo> vos = warehouseConvert.toListVos(pages.getContent());
		return new PageImpl<WarehouseListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建Warehouse
	 **/
	@Override
	public Warehouse addWarehouse(Warehouse warehouse) {
		return warehouseDao.save(warehouse);
	}

	/**
	 * 创建Warehouse
	 **/
	@Override
	public WarehouseVo addWarehouse(WarehouseBo warehouseBo) {
		Warehouse warehouse = warehouseDao.findByNameAndDeleted(warehouseBo.getName().trim(),Deleted.DEL_FALSE);
		if(warehouse!=null){
			throw new BusinessException("该仓库已添加,请添加其他仓库");
		}
		warehouseBo.setCode(NumberGenerateUtils.generateWarehouse());
		return warehouseConvert.toVo(warehouseDao.save(warehouseConvert.toEntity(warehouseBo)));
	}

    /**
     * 更新Warehouse
     **/
    @Override
    public Warehouse updateWarehouse(Warehouse warehouse) {
        Warehouse dbWarehouse = warehouseDao.getOne(warehouse.getId());
        AttributeReplication.copying(warehouse, dbWarehouse, Warehouse_.name, Warehouse_.province, Warehouse_.city,
                Warehouse_.district, Warehouse_.address, Warehouse_.state);
        return dbWarehouse;
    }

	/**
	 * 更新Warehouse
	 **/
	@Override
	public WarehouseVo updateWarehouse(WarehouseBo warehouseBo) {
		Warehouse dbWarehouse = this.updateWarehouse(warehouseConvert.toEntity(warehouseBo));
		return warehouseConvert.toVo(dbWarehouse);
	}

	/**
	 * 删除Warehouse
	 **/
	@Override
	public void removeWarehouseById(int id) {
		Warehouse warehouse = warehouseDao.getOne(id);
		if (warehouse != null) {
			warehouse.setDeleted(Deleted.DEL_TRUE);
			warehouse.setDelTime(new Date());
		}
	}

	/**
	 * 根据ID得到WarehouseBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WarehouseBo getWarehouseBoById(int id) {
		return warehouseConvert.toBo(this.warehouseDao.getOne(id));
	}

	/**
	 * 根据ID得到WarehouseVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WarehouseVo getWarehouseVoById(int id) {
		return warehouseConvert.toVo(this.warehouseDao.getOne(id));
	}

	/**
	 * 根据ID得到WarehouseListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WarehouseListVo getListVoById(int id) {
		return warehouseConvert.toListVo(this.warehouseDao.getOne(id));
	}

	protected void initConvert() {
		this.warehouseConvert = new EntityListVoBoSimpleConvert<Warehouse, WarehouseBo, WarehouseVo, WarehouseSimple,
				WarehouseListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Warehouse, WarehouseVo> createEntityToVoConvert(BeanConvertManager
																						  beanConvertManager) {
				return new AbstractBeanConvert<Warehouse, WarehouseVo>(beanConvertManager) {
					@Override
					protected void postConvert(WarehouseVo warehouseVo, Warehouse warehouse) {
					}
				};
			}

			@Override
			protected BeanConvert<Warehouse, WarehouseListVo> createEntityToListVoConvert(BeanConvertManager
																								  beanConvertManager) {
				return new AbstractBeanConvert<Warehouse, WarehouseListVo>(beanConvertManager) {
					@Override
					protected void postConvert(WarehouseListVo warehouseListVo, Warehouse warehouse) {
					}
				};
			}

			@Override
			protected BeanConvert<Warehouse, WarehouseBo> createEntityToBoConvert(BeanConvertManager
																						  beanConvertManager) {
				return new AbstractBeanConvert<Warehouse, WarehouseBo>(beanConvertManager) {
					@Override
					protected void postConvert(WarehouseBo warehouseBo, Warehouse warehouse) {
					}
				};
			}

			@Override
			protected BeanConvert<WarehouseBo, Warehouse> createBoToEntityConvert(BeanConvertManager
																						  beanConvertManager) {
				return new AbstractBeanConvert<WarehouseBo, Warehouse>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Warehouse, WarehouseSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Warehouse, WarehouseSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<WarehouseSimple, Warehouse> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<WarehouseSimple, Warehouse>(beanConvertManager) {
					@Override
					public Warehouse convert(WarehouseSimple warehouseSimple) throws BeansException {
						return warehouseDao.getOne(warehouseSimple.getId());
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