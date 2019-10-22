/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service.impl;

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

import com.yi.core.activity.dao.AreaColumnDao;
import com.yi.core.activity.domain.bo.AreaColumnBo;
import com.yi.core.activity.domain.entity.AreaColumn;
import com.yi.core.activity.domain.entity.AreaColumn_;
import com.yi.core.activity.domain.simple.AreaColumnSimple;
import com.yi.core.activity.domain.vo.AreaColumnListVo;
import com.yi.core.activity.domain.vo.AreaColumnVo;
import com.yi.core.activity.service.IAreaColumnCommodityService;
import com.yi.core.activity.service.IAreaColumnService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
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
public class AreaColumnServiceImpl implements IAreaColumnService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AreaColumnServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AreaColumnDao areaColumnDao;

	@Resource
	private IAreaColumnCommodityService areaColumnCommodityService;

	private EntityListVoBoSimpleConvert<AreaColumn, AreaColumnBo, AreaColumnVo, AreaColumnSimple, AreaColumnListVo> areaColumnConvert;

	/**
	 * 分页查询AreaColumn
	 **/
	@Override
	public Page<AreaColumn> query(Pagination<AreaColumn> query) {
		query.setEntityClazz(AreaColumn.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AreaColumn_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.asc(root.get(AreaColumn_.sort)));
			list1.add(criteriaBuilder.desc(root.get(AreaColumn_.createTime)));
		}));
		Page<AreaColumn> page = areaColumnDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: AreaColumn
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AreaColumnListVo> queryListVo(Pagination<AreaColumn> query) {
		query.setEntityClazz(AreaColumn.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AreaColumn_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.asc(root.get(AreaColumn_.sort)));
			list1.add(criteriaBuilder.desc(root.get(AreaColumn_.createTime)));
		}));
		Page<AreaColumn> pages = areaColumnDao.findAll(query, query.getPageRequest());
		List<AreaColumnListVo> vos = areaColumnConvert.toListVos(pages.getContent());
		return new PageImpl<AreaColumnListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: AreaColumn
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AreaColumnListVo> queryListVoForApp(Pagination<AreaColumn> query) {
		query.setEntityClazz(AreaColumn.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AreaColumn_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.asc(root.get(AreaColumn_.sort)));
			list1.add(criteriaBuilder.desc(root.get(AreaColumn_.createTime)));
		}));
		Page<AreaColumn> pages = areaColumnDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.getSalesArea().setAreaColumns(null);
			tmp.getSalesArea().setSalesAreaCommodities(null);
			tmp.getAreaColumnCommodities().forEach(tmp2 -> {
				tmp2.setAreaColumnCommodityId(null);
				tmp2.getCommodity().setCategory(null);
				tmp2.getCommodity().setSupplier(null);
				tmp2.getCommodity().setComments(null);
			});
		});
		List<AreaColumnListVo> vos = areaColumnConvert.toListVos(pages.getContent());
		return new PageImpl<AreaColumnListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建AreaColumn
	 **/
	@Override
	public AreaColumn addAreaColumn(AreaColumn areaColumn) {
		if (areaColumn == null || areaColumn.getSalesArea() == null) {
			LOG.error("提交数据不能为空");
			throw new BusinessException("提交数据不能为空");
		}
		AreaColumn dbAreaColumn = areaColumnDao.save(areaColumn);
		// 保存专区栏目商品
		areaColumnCommodityService.batchAddAreaColumnCommodity(dbAreaColumn, areaColumn.getAreaColumnCommodities());
		return dbAreaColumn;
	}

	/**
	 * 创建AreaColumn
	 **/
	@Override
	public AreaColumnListVo addAreaColumn(AreaColumnBo areaColumnBo) {
		AreaColumnListVo dbAreaColumnListVo = areaColumnConvert.toListVo(this.addAreaColumn(areaColumnConvert.toEntity(areaColumnBo)));
		return dbAreaColumnListVo;
	}

	/**
	 * 更新AreaColumn
	 **/
	@Override
	public AreaColumn updateAreaColumn(AreaColumn areaColumn) {
		if (areaColumn == null || areaColumn.getSalesArea() == null) {
			LOG.error("提交数据不能为空");
			throw new BusinessException("提交数据不能为空");
		}
		AreaColumn dbAreaColumn = areaColumnDao.getOne(areaColumn.getId());
		AttributeReplication.copying(areaColumn, dbAreaColumn, AreaColumn_.salesArea, AreaColumn_.title, AreaColumn_.banner, AreaColumn_.showMode, AreaColumn_.sort,
				AreaColumn_.state);
		// 更新专区栏目商品
		areaColumnCommodityService.batchUpdateAreaColumnCommodity(dbAreaColumn, areaColumn.getAreaColumnCommodities());
		return dbAreaColumn;
	}

	/**
	 * 更新AreaColumn
	 **/
	@Override
	public AreaColumnListVo updateAreaColumn(AreaColumnBo areaColumnBo) {
		AreaColumn dbAreaColumn = this.updateAreaColumn(areaColumnConvert.toEntity(areaColumnBo));
		return areaColumnConvert.toListVo(dbAreaColumn);
	}

	/**
	 * 删除AreaColumn
	 **/
	@Override
	public void removeAreaColumnById(java.lang.Integer id) {
		AreaColumn dbAreaColumn = this.getAreaColumnById(id);
		if (dbAreaColumn != null) {
			dbAreaColumn.setDeleted(Deleted.DEL_TRUE);
			dbAreaColumn.setDelTime(new Date());
			// 删除专区栏目商品
			areaColumnCommodityService.removeByAreaColumn(id);
		}
	}

	/**
	 * 根据ID得到AreaColumn
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AreaColumn getAreaColumnById(java.lang.Integer id) {
		return this.areaColumnDao.getOne(id);
	}

	/**
	 * 根据ID得到AreaColumnBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AreaColumnBo getAreaColumnBoById(java.lang.Integer id) {
		return areaColumnConvert.toBo(this.areaColumnDao.getOne(id));
	}

	/**
	 * 根据ID得到AreaColumnVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AreaColumnVo getAreaColumnVoById(java.lang.Integer id) {
		return areaColumnConvert.toVo(this.areaColumnDao.getOne(id));
	}

	/**
	 * 根据ID得到AreaColumnListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AreaColumnListVo getListVoById(java.lang.Integer id) {
		return areaColumnConvert.toListVo(this.areaColumnDao.getOne(id));
	}

	@Override
	public EntityListVoBoSimpleConvert<AreaColumn, AreaColumnBo, AreaColumnVo, AreaColumnSimple, AreaColumnListVo> getAreaColumnConvert() {
		return areaColumnConvert;
	}

	protected void initConvert() {
		this.areaColumnConvert = new EntityListVoBoSimpleConvert<AreaColumn, AreaColumnBo, AreaColumnVo, AreaColumnSimple, AreaColumnListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<AreaColumn, AreaColumnVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumn, AreaColumnVo>(beanConvertManager) {
					@Override
					protected void postConvert(AreaColumnVo areaColumnVo, AreaColumn areaColumn) {
						areaColumnVo.setAreaColumnCommodities(areaColumnCommodityService.getAreaColumnCommodityConvert().toSimples(areaColumn.getAreaColumnCommodities()));
					}
				};
			}

			@Override
			protected BeanConvert<AreaColumn, AreaColumnListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumn, AreaColumnListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AreaColumnListVo areaColumnListVo, AreaColumn areaColumn) {
					}
				};
			}

			@Override
			protected BeanConvert<AreaColumn, AreaColumnBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumn, AreaColumnBo>(beanConvertManager) {
					@Override
					protected void postConvert(AreaColumnBo areaColumnBo, AreaColumn areaColumn) {
					}
				};
			}

			@Override
			protected BeanConvert<AreaColumnBo, AreaColumn> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumnBo, AreaColumn>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AreaColumn, AreaColumnSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumn, AreaColumnSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AreaColumnSimple, AreaColumn> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaColumnSimple, AreaColumn>(beanConvertManager) {
					@Override
					public AreaColumn convert(AreaColumnSimple areaColumnSimple) throws BeansException {
						return areaColumnDao.getOne(areaColumnSimple.getId());
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
