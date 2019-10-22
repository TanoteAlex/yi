/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.JoinType;

import com.yi.core.activity.dao.SalesAreaDao;
import com.yi.core.commodity.dao.CommodityDao;
import com.yi.core.commodity.domain.entity.Commodity;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.activity.dao.SalesAreaCommodityDao;
import com.yi.core.activity.domain.bo.SalesAreaCommodityBo;
import com.yi.core.activity.domain.entity.SalesArea;
import com.yi.core.activity.domain.entity.SalesAreaCommodity;
import com.yi.core.activity.domain.entity.SalesAreaCommodityId;
import com.yi.core.activity.domain.entity.SalesAreaCommodity_;
import com.yi.core.activity.domain.simple.SalesAreaCommoditySimple;
import com.yi.core.activity.domain.vo.SalesAreaCommodityListVo;
import com.yi.core.activity.domain.vo.SalesAreaCommodityVo;
import com.yi.core.activity.service.ISalesAreaCommodityService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SalesAreaCommodityServiceImpl implements ISalesAreaCommodityService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SalesAreaCommodityServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SalesAreaCommodityDao salesAreaCommodityDao;

	private EntityListVoBoSimpleConvert<SalesAreaCommodity, SalesAreaCommodityBo, SalesAreaCommodityVo, SalesAreaCommoditySimple, SalesAreaCommodityListVo> salesAreaCommodityConvert;

	/**
	 * 分页查询SalesAreaCommodity
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SalesAreaCommodity> query(Pagination<SalesAreaCommodity> query) {
		query.setEntityClazz(SalesAreaCommodity.class);
		Page<SalesAreaCommodity> page = salesAreaCommodityDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: SalesAreaCommodity
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SalesAreaCommodityListVo> queryListVo(Pagination<SalesAreaCommodity> query) {
		Page<SalesAreaCommodity> pages = this.query(query);
		List<SalesAreaCommodityListVo> vos = salesAreaCommodityConvert.toListVos(pages.getContent());
		return new PageImpl<SalesAreaCommodityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: SalesAreaCommodity
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SalesAreaCommodityListVo> queryListVoForApp(Pagination<SalesAreaCommodity> query) {
		query.setEntityClazz(SalesAreaCommodity.class);
		Page<SalesAreaCommodity> pages = salesAreaCommodityDao.findAll(query, query.getPageRequest());
		List<SalesAreaCommodityListVo> vos = salesAreaCommodityConvert.toListVos(pages.getContent());
		return new PageImpl<SalesAreaCommodityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建SalesAreaCommodity
	 **/
	@Override
	public SalesAreaCommodity addSalesAreaCommodity(SalesAreaCommodity salesAreaCommodity) {
		return salesAreaCommodityDao.save(salesAreaCommodity);
	}

	/**
	 * 创建SalesAreaCommodity
	 **/
	@Override
	public SalesAreaCommodityListVo addSalesAreaCommodity(SalesAreaCommodityBo salesAreaCommodityBo) {
		return salesAreaCommodityConvert.toListVo(salesAreaCommodityDao.save(salesAreaCommodityConvert.toEntity(salesAreaCommodityBo)));
	}

	/**
	 * 更新SalesAreaCommodity
	 **/
	@Override
	public SalesAreaCommodity updateSalesAreaCommodity(SalesAreaCommodity salesAreaCommodity) {
		SalesAreaCommodity dbSalesAreaCommodity = salesAreaCommodityDao.getOne(salesAreaCommodity.getSalesAreaCommodityId());
		AttributeReplication.copying(salesAreaCommodity, dbSalesAreaCommodity, SalesAreaCommodity_.sort);
		return dbSalesAreaCommodity;
	}

	/**
	 * 更新SalesAreaCommodity
	 **/
	@Override
	public SalesAreaCommodityListVo updateSalesAreaCommodity(SalesAreaCommodityBo salesAreaCommodityBo) {
		SalesAreaCommodityId id =salesAreaCommodityBo.getSalesAreaCommodityId();
		SalesAreaCommodity dbSalesAreaCommodity = salesAreaCommodityDao.findBySalesArea_IdAndCommodity_Id(id.getSalesAreaId(),id.getCommodityId());
		salesAreaCommodityDao.delete(dbSalesAreaCommodity);

		SalesAreaCommodityId nId=new SalesAreaCommodityId(salesAreaCommodityBo.getSalesArea().getId(),salesAreaCommodityBo.getCommodity().getId());
		SalesAreaCommodity salesAreaCommodity=new SalesAreaCommodity();
		salesAreaCommodity.setSalesAreaCommodityId(nId);
		salesAreaCommodity.setSort(salesAreaCommodityBo.getSort());
		salesAreaCommodity.setSalesArea(new SalesArea(salesAreaCommodityBo.getSalesArea().getId()));
		salesAreaCommodity.setCommodity(new Commodity(salesAreaCommodityBo.getCommodity().getId()));
		dbSalesAreaCommodity = salesAreaCommodityDao.save(salesAreaCommodity);

		return salesAreaCommodityConvert.toListVo(dbSalesAreaCommodity);
	}

	/**
	 * 删除SalesAreaCommodity
	 **/
	@Override
	public void removeSalesAreaCommodityById(SalesAreaCommodityId salesAreaId) {
		salesAreaCommodityDao.deleteById(salesAreaId);
	}

	/**
	 * 根据ID得到SalesAreaCommodity
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SalesAreaCommodity getSalesAreaCommodityById(SalesAreaCommodityId salesAreaId) {
		return this.salesAreaCommodityDao.getOne(salesAreaId);
	}

	/**
	 * 根据ID得到SalesAreaCommodityBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SalesAreaCommodityBo getSalesAreaCommodityBoById(SalesAreaCommodityId salesAreaId) {
		return salesAreaCommodityConvert.toBo(this.salesAreaCommodityDao.getOne(salesAreaId));
	}

	/**
	 * 根据ID得到SalesAreaCommodityVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SalesAreaCommodityVo getSalesAreaCommodityVoById(SalesAreaCommodityId salesAreaId) {
		return salesAreaCommodityConvert.toVo(this.salesAreaCommodityDao.getOne(salesAreaId));
	}

	/**
	 * 根据ID得到SalesAreaCommodityListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SalesAreaCommodityListVo getListVoById(SalesAreaCommodityId salesAreaId) {
		return salesAreaCommodityConvert.toListVo(this.salesAreaCommodityDao.getOne(salesAreaId));
	}

	@Override
	public EntityListVoBoSimpleConvert<SalesAreaCommodity, SalesAreaCommodityBo, SalesAreaCommodityVo, SalesAreaCommoditySimple, SalesAreaCommodityListVo> getSalesAreaCommodityConvert() {
		return salesAreaCommodityConvert;
	}

	protected void initConvert() {
		this.salesAreaCommodityConvert = new EntityListVoBoSimpleConvert<SalesAreaCommodity, SalesAreaCommodityBo, SalesAreaCommodityVo, SalesAreaCommoditySimple, SalesAreaCommodityListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<SalesAreaCommodity, SalesAreaCommodityVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SalesAreaCommodity, SalesAreaCommodityVo>(beanConvertManager) {
					@Override
					protected void postConvert(SalesAreaCommodityVo salesAreaCommodityVo, SalesAreaCommodity salesAreaCommodity) {
					}
				};
			}

			@Override
			protected BeanConvert<SalesAreaCommodity, SalesAreaCommodityListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SalesAreaCommodity, SalesAreaCommodityListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SalesAreaCommodityListVo salesAreaCommodityListVo, SalesAreaCommodity salesAreaCommodity) {
					}
				};
			}

			@Override
			protected BeanConvert<SalesAreaCommodity, SalesAreaCommodityBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SalesAreaCommodity, SalesAreaCommodityBo>(beanConvertManager) {
					@Override
					protected void postConvert(SalesAreaCommodityBo salesAreaCommodityBo, SalesAreaCommodity salesAreaCommodity) {
					}
				};
			}

			@Override
			protected BeanConvert<SalesAreaCommodityBo, SalesAreaCommodity> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SalesAreaCommodityBo, SalesAreaCommodity>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SalesAreaCommodity, SalesAreaCommoditySimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SalesAreaCommodity, SalesAreaCommoditySimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SalesAreaCommoditySimple, SalesAreaCommodity> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SalesAreaCommoditySimple, SalesAreaCommodity>(beanConvertManager) {
					@Override
					public SalesAreaCommodity convert(SalesAreaCommoditySimple salesAreaCommoditySimple) throws BeansException {
						return salesAreaCommodityDao.getOne(salesAreaCommoditySimple.getSalesAreaCommodityId());
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
	public void batchAddSalesAreaCommodity(SalesArea salesArea, Collection<SalesAreaCommodity> salesAreaCommodities) {
		if (salesArea != null && CollectionUtils.isNotEmpty(salesAreaCommodities)) {
			salesAreaCommodities.forEach(tmp -> {
				tmp.setSalesAreaCommodityId(new SalesAreaCommodityId(salesArea.getId(), tmp.getCommodity().getId()));
				tmp.setSalesArea(salesArea);
			});
			salesAreaCommodityDao.saveAll(salesAreaCommodities);
		}
	}

	@Override
	public void batchUpdateSalesAreaCommodity(SalesArea salesArea, Collection<SalesAreaCommodity> salesAreaCommodities) {
		if (salesArea != null && CollectionUtils.isNotEmpty(salesAreaCommodities)) {
			// 整理数据
			salesAreaCommodities.forEach(tmp -> {
				tmp.setSalesAreaCommodityId(new SalesAreaCommodityId(salesArea.getId(), tmp.getCommodity().getId()));
				tmp.setSalesArea(salesArea);
			});
			// 删除旧数据
			List<SalesAreaCommodity> dbSalesAreaCommodities = salesAreaCommodityDao.findBySalesArea_id(salesArea.getId());
			dbSalesAreaCommodities.forEach(tmp -> {
				this.removeSalesAreaCommodityById(tmp.getSalesAreaCommodityId());
			});
			// 保存数据
			salesAreaCommodityDao.saveAll(salesAreaCommodities);
		}

	}

	@Override
	public void removeBySalesArea(Integer salesAreaId) {
		List<SalesAreaCommodity> dbSalesAreaCommodities = salesAreaCommodityDao.findBySalesArea_id(salesAreaId);
		salesAreaCommodityDao.deleteInBatch(dbSalesAreaCommodities);
	}

	@Override
	public void removeByCommodity(Integer commodityId) {
		List<SalesAreaCommodity> dbSalesAreaCommodities = salesAreaCommodityDao.findByCommodity_id(commodityId);
		salesAreaCommodityDao.deleteInBatch(dbSalesAreaCommodities);

	}
}
