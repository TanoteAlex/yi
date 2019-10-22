/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import com.yi.core.activity.domain.entity.*;
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

import com.yi.core.activity.dao.AreaColumnCommodityDao;
import com.yi.core.activity.domain.bo.AreaColumnCommodityBo;
import com.yi.core.activity.domain.simple.AreaColumnCommoditySimple;
import com.yi.core.activity.domain.vo.AreaColumnCommodityListVo;
import com.yi.core.activity.domain.vo.AreaColumnCommodityVo;
import com.yi.core.activity.service.IAreaColumnCommodityService;
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
public class AreaColumnCommodityServiceImpl implements IAreaColumnCommodityService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AreaColumnCommodityServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AreaColumnCommodityDao areaColumnCommodityDao;

	private EntityListVoBoSimpleConvert<AreaColumnCommodity, AreaColumnCommodityBo, AreaColumnCommodityVo, AreaColumnCommoditySimple, AreaColumnCommodityListVo> areaColumnCommodityConvert;

	/**
	 * 分页查询AreaColumnCommodity
	 **/
	@Override
	public Page<AreaColumnCommodity> query(Pagination<AreaColumnCommodity> query) {
		query.setEntityClazz(AreaColumnCommodity.class);
		Page<AreaColumnCommodity> page = areaColumnCommodityDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: AreaColumnCommodity
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AreaColumnCommodityListVo> queryListVo(Pagination<AreaColumnCommodity> query) {
		Page<AreaColumnCommodity> pages = this.query(query);
		List<AreaColumnCommodityListVo> vos = areaColumnCommodityConvert.toListVos(pages.getContent());
		return new PageImpl<AreaColumnCommodityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: AreaColumnCommodity
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AreaColumnCommodityListVo> queryListVoForApp(Pagination<AreaColumnCommodity> query) {
		query.setEntityClazz(AreaColumnCommodity.class);
		Page<AreaColumnCommodity> pages = areaColumnCommodityDao.findAll(query, query.getPageRequest());
		List<AreaColumnCommodityListVo> vos = areaColumnCommodityConvert.toListVos(pages.getContent());
		return new PageImpl<AreaColumnCommodityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建AreaColumnCommodity
	 **/
	@Override
	public AreaColumnCommodity addAreaColumnCommodity(AreaColumnCommodity areaColumnCommodity) {
		return areaColumnCommodityDao.save(areaColumnCommodity);
	}

	/**
	 * 创建AreaColumnCommodity
	 **/
	@Override
	public AreaColumnCommodityListVo addAreaColumnCommodity(AreaColumnCommodityBo areaColumnCommodityBo) {
		return areaColumnCommodityConvert.toListVo(areaColumnCommodityDao.save(areaColumnCommodityConvert.toEntity(areaColumnCommodityBo)));
	}

	/**
	 * 更新AreaColumnCommodity
	 **/
	@Override
	public AreaColumnCommodity updateAreaColumnCommodity(AreaColumnCommodity areaColumnCommodity) {
		AreaColumnCommodity dbAreaColumnCommodity = areaColumnCommodityDao.getOne(areaColumnCommodity.getAreaColumnCommodityId());
		AttributeReplication.copying(areaColumnCommodity, dbAreaColumnCommodity, AreaColumnCommodity_.sort);
		return dbAreaColumnCommodity;
	}

	/**
	 * 更新AreaColumnCommodity
	 **/
	@Override
	public AreaColumnCommodityListVo updateAreaColumnCommodity(AreaColumnCommodityBo areaColumnCommodityBo) {
		AreaColumnCommodityId id=areaColumnCommodityBo.getAreaColumnCommodityId();
		AreaColumnCommodity dbAreaColumnCommodity = areaColumnCommodityDao.findByAreaColumn_IdAndCommodity_Id(id.getAreaColumnId(),id.getCommodityId());
		areaColumnCommodityDao.delete(dbAreaColumnCommodity);

		AreaColumnCommodityId nId=new AreaColumnCommodityId(areaColumnCommodityBo.getAreaColumn().getId(),areaColumnCommodityBo.getCommodity().getId());
		AreaColumnCommodity areaColumnCommodity=new AreaColumnCommodity();
		areaColumnCommodity.setAreaColumnCommodityId(nId);
		areaColumnCommodity.setSort(areaColumnCommodityBo.getSort());
		areaColumnCommodity.setAreaColumn(new AreaColumn(areaColumnCommodityBo.getAreaColumn().getId()));
		areaColumnCommodity.setCommodity(new Commodity(areaColumnCommodityBo.getCommodity().getId()));

		dbAreaColumnCommodity = areaColumnCommodityDao.save(areaColumnCommodity);
		return areaColumnCommodityConvert.toListVo(dbAreaColumnCommodity);
	}

	/**
	 * 删除AreaColumnCommodity
	 **/
	@Override
	public void removeAreaColumnCommodityById(AreaColumnCommodityId areaColumnId) {
		areaColumnCommodityDao.deleteById(areaColumnId);
	}

	/**
	 * 根据ID得到AreaColumnCommodity
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AreaColumnCommodity getAreaColumnCommodityById(AreaColumnCommodityId areaColumnId) {
		return this.areaColumnCommodityDao.getOne(areaColumnId);
	}

	/**
	 * 根据ID得到AreaColumnCommodityBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AreaColumnCommodityBo getAreaColumnCommodityBoById(AreaColumnCommodityId areaColumnId) {
		return areaColumnCommodityConvert.toBo(this.areaColumnCommodityDao.getOne(areaColumnId));
	}

	/**
	 * 根据ID得到AreaColumnCommodityVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AreaColumnCommodityVo getAreaColumnCommodityVoById(AreaColumnCommodityId areaColumnId) {
		return areaColumnCommodityConvert.toVo(this.areaColumnCommodityDao.getOne(areaColumnId));
	}

	/**
	 * 根据ID得到AreaColumnCommodityListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AreaColumnCommodityListVo getListVoById(AreaColumnCommodityId areaColumnId) {
		return areaColumnCommodityConvert.toListVo(this.areaColumnCommodityDao.getOne(areaColumnId));
	}

	@Override
	public EntityListVoBoSimpleConvert<AreaColumnCommodity, AreaColumnCommodityBo, AreaColumnCommodityVo, AreaColumnCommoditySimple, AreaColumnCommodityListVo> getAreaColumnCommodityConvert() {
		return areaColumnCommodityConvert;
	}

	protected void initConvert() {
		this.areaColumnCommodityConvert = new EntityListVoBoSimpleConvert<AreaColumnCommodity, AreaColumnCommodityBo, AreaColumnCommodityVo, AreaColumnCommoditySimple, AreaColumnCommodityListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<AreaColumnCommodity, AreaColumnCommodityVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumnCommodity, AreaColumnCommodityVo>(beanConvertManager) {
					@Override
					protected void postConvert(AreaColumnCommodityVo areaColumnCommodityVo, AreaColumnCommodity areaColumnCommodity) {
					}
				};
			}

			@Override
			protected BeanConvert<AreaColumnCommodity, AreaColumnCommodityListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumnCommodity, AreaColumnCommodityListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AreaColumnCommodityListVo areaColumnCommodityListVo, AreaColumnCommodity areaColumnCommodity) {
					}
				};
			}

			@Override
			protected BeanConvert<AreaColumnCommodity, AreaColumnCommodityBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumnCommodity, AreaColumnCommodityBo>(beanConvertManager) {
					@Override
					protected void postConvert(AreaColumnCommodityBo areaColumnCommodityBo, AreaColumnCommodity areaColumnCommodity) {
					}
				};
			}

			@Override
			protected BeanConvert<AreaColumnCommodityBo, AreaColumnCommodity> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumnCommodityBo, AreaColumnCommodity>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AreaColumnCommodity, AreaColumnCommoditySimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumnCommodity, AreaColumnCommoditySimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AreaColumnCommoditySimple, AreaColumnCommodity> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumnCommoditySimple, AreaColumnCommodity>(beanConvertManager) {
					@Override
					public AreaColumnCommodity convert(AreaColumnCommoditySimple areaColumnCommoditySimple) throws BeansException {
						return areaColumnCommodityDao.getOne(areaColumnCommoditySimple.getAreaColumnCommodityId());
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
	public void batchAddAreaColumnCommodity(AreaColumn areaColumn, Collection<AreaColumnCommodity> areaColumnCommodities) {
		if (areaColumn != null && CollectionUtils.isNotEmpty(areaColumnCommodities)) {
			areaColumnCommodities.forEach(tmp -> {
				tmp.setAreaColumnCommodityId(new AreaColumnCommodityId(areaColumn.getId(), tmp.getCommodity().getId()));
				tmp.setAreaColumn(areaColumn);
			});
			areaColumnCommodityDao.saveAll(areaColumnCommodities);
		}

	}

	@Override
	public void batchUpdateAreaColumnCommodity(AreaColumn areaColumn, Collection<AreaColumnCommodity> areaColumnCommodities) {
		if (areaColumn != null && CollectionUtils.isNotEmpty(areaColumnCommodities)) {
			// 整理数据
			areaColumnCommodities.forEach(tmp -> {
				tmp.setAreaColumnCommodityId(new AreaColumnCommodityId(areaColumn.getId(), tmp.getCommodity().getId()));
				tmp.setAreaColumn(areaColumn);
			});
			// 删除旧数据
			List<AreaColumnCommodity> dbAreaColumnCommodities = areaColumnCommodityDao.findByAreaColumn_id(areaColumn.getId());
			dbAreaColumnCommodities.forEach(tmp -> {
				this.removeAreaColumnCommodityById(tmp.getAreaColumnCommodityId());
			});
			// 保存数据
			areaColumnCommodityDao.saveAll(areaColumnCommodities);
		}
	}

	@Override
	public void removeByAreaColumn(Integer areaColumnId) {
		List<AreaColumnCommodity> dbAreaColumnCommodities = areaColumnCommodityDao.findByAreaColumn_id(areaColumnId);
		areaColumnCommodityDao.deleteInBatch(dbAreaColumnCommodities);

	}

	@Override
	public void removeByCommodity(Integer commodityId) {
		List<AreaColumnCommodity> dbAreaColumnCommodities = areaColumnCommodityDao.findByCommodity_id(commodityId);
		areaColumnCommodityDao.deleteInBatch(dbAreaColumnCommodities);
	}
}
