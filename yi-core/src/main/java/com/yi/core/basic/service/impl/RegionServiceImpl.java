/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

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

import com.yi.core.basic.dao.RegionDao;
import com.yi.core.basic.domain.bo.RegionBo;
import com.yi.core.basic.domain.entity.Region;
import com.yi.core.basic.domain.entity.RegionGroup;
import com.yi.core.basic.domain.entity.Region_;
import com.yi.core.basic.domain.simple.RegionSimple;
import com.yi.core.basic.domain.vo.RegionListVo;
import com.yi.core.basic.domain.vo.RegionVo;
import com.yi.core.basic.service.IRegionService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 地区
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class RegionServiceImpl implements IRegionService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(RegionServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private RegionDao regionDao;

	private EntityListVoBoSimpleConvert<Region, RegionBo, RegionVo, RegionSimple, RegionListVo> regionConvert;

	@Override
	public Page<Region> query(Pagination<Region> query) {
		query.setEntityClazz(Region.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Region_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Region_.createTime)));
		}));
		Page<Region> page = regionDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RegionListVo> queryListVo(Pagination<Region> query) {
		Page<Region> pages = this.query(query);
		List<RegionListVo> vos = regionConvert.toListVos(pages.getContent());
		Page<RegionListVo> page = new PageImpl<RegionListVo>(vos, query.getPageRequest(), pages.getTotalElements());
		return page;

	}

	@Override
	public Region addRegion(Region region) {
		if (region == null || region.getRegionGroup() == null || region.getArea() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return regionDao.save(region);
	}

	@Override
	public RegionVo addRegion(RegionBo regionBo) {
		return regionConvert.toVo(this.addRegion(regionConvert.toEntity(regionBo)));
	}

	@Override
	public Region updateRegion(Region region) {
		if (region == null || region.getId() < 1 || region.getRegionGroup() == null || region.getArea() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		Region dbRegion = regionDao.getOne(region.getId());
		if (dbRegion != null) {
			AttributeReplication.copying(region, dbRegion, Region_.regionGroup, Region_.area);
		}
		return dbRegion;
	}

	@Override
	public RegionVo updateRegion(RegionBo regionBo) {
		return regionConvert.toVo(this.updateRegion(regionConvert.toEntity(regionBo)));
	}

	/**
	 * 进行逻辑删除
	 * 
	 * @param regionId
	 */
	@Override
	public void removeRegionById(int regionId) {
		Region dbRegion = regionDao.getOne(regionId);
		if (dbRegion != null) {
			dbRegion.setDeleted(Deleted.DEL_TRUE);
			dbRegion.setDelTime(new Date());
			// 删除中间表数据
			dbRegion.setCommodities(null);
			dbRegion.setCustomFreightTemplates(null);
			dbRegion.setFreeFreightTemplates(null);
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Region getById(int regionId) {
		return regionDao.getOne(regionId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RegionVo getVoById(int regionId) {
		return regionConvert.toVo(this.regionDao.getOne(regionId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RegionListVo getListVoById(int regionId) {
		return regionConvert.toListVo(this.regionDao.getOne(regionId));
	}

	@Override
	public void batchAddRegion(RegionGroup regionGroup) {
		if (regionGroup != null && CollectionUtils.isNotEmpty(regionGroup.getRegions())) {
			regionGroup.getRegions().forEach(e -> {
				e.setRegionGroup(regionGroup);
			});
			regionDao.saveAll(regionGroup.getRegions());
		}
	}

	@Override
	public void batchUpdateRegion(RegionGroup regionGroup) {
		if (regionGroup != null && CollectionUtils.isNotEmpty(regionGroup.getRegions())) {
			// 查询当前地区组拥有的区域
			Set<Region> dbRegions = regionDao.findByRegionGroup_idAndDeleted(regionGroup.getId(), Deleted.DEL_FALSE);
			// 需要保存的数据
			Set<Region> saveSets = regionGroup.getRegions().stream()
					.filter(e1 -> dbRegions.stream().noneMatch(e2 -> e1.getId() == e2.getId() || e1.getArea().getId() == e2.getArea().getId())).collect(Collectors.toSet());
			// 需要删除的数据
			Set<Region> deleteSets = dbRegions.stream()
					.filter(e1 -> regionGroup.getRegions().stream().noneMatch(e2 -> e1.getId() == e2.getId() || e1.getArea().getId() == e2.getArea().getId()))
					.collect(Collectors.toSet());
			// 赋值RegionGroup
			saveSets.forEach(e -> {
				e.setRegionGroup(regionGroup);
			});
			// 删除数据
			deleteSets.forEach(e -> {
				this.removeRegionById(e.getId());
			});
			// 保存数据
			regionDao.saveAll(saveSets);
		}
	}

	protected void initConvert() {
		this.regionConvert = new EntityListVoBoSimpleConvert<Region, RegionBo, RegionVo, RegionSimple, RegionListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Region, RegionVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Region, RegionVo>(beanConvertManager) {
					@Override
					protected void postConvert(RegionVo RegionVo, Region Region) {

					}
				};
			}

			@Override
			protected BeanConvert<Region, RegionListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Region, RegionListVo>(beanConvertManager) {
					@Override
					protected void postConvert(RegionListVo RegionListVo, Region Region) {

					}
				};
			}

			@Override
			protected BeanConvert<Region, RegionBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Region, RegionBo>(beanConvertManager) {
					@Override
					protected void postConvert(RegionBo RegionBo, Region Region) {

					}
				};
			}

			@Override
			protected BeanConvert<RegionBo, Region> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RegionBo, Region>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Region, RegionSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Region, RegionSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<RegionSimple, Region> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RegionSimple, Region>(beanConvertManager) {
					@Override
					public Region convert(RegionSimple RegionSimple) throws BeansException {
						return regionDao.getOne(RegionSimple.getId());
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
	 * 根据省查市
	 * 
	 * @param province
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RegionVo> checkCity(String province) {
		List<Region> regions = null;
		if (!province.equalsIgnoreCase("")) {
			regions = regionDao.findAll().stream().filter(e -> e.getDeleted() == 0).collect(Collectors.toList());
		} else {
			regions = regionDao.findAll().stream().filter(e -> e.getDeleted() == 0).collect(Collectors.toList());
		}
		return regionConvert.toVos(regions);
	}

	/**
	 * 添加一组数据
	 */
	@Override
	public List<RegionVo> addList(List<RegionBo> regionBos) {
		return regionConvert.toVos(regionDao.saveAll(regionConvert.toEntities(regionBos)));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Region> getRegionsByIds(List<Integer> ids) {
		return regionDao.findAllById(ids);
	}

}
