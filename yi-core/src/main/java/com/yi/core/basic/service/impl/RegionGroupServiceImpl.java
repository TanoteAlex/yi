/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.Date;
import java.util.List;

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

import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.dao.RegionGroupDao;
import com.yi.core.basic.domain.bo.RegionGroupBo;
import com.yi.core.basic.domain.entity.RegionGroup;
import com.yi.core.basic.domain.entity.RegionGroup_;
import com.yi.core.basic.domain.simple.RegionGroupSimple;
import com.yi.core.basic.domain.vo.RegionGroupListVo;
import com.yi.core.basic.domain.vo.RegionGroupVo;
import com.yi.core.basic.service.IRegionGroupService;
import com.yi.core.basic.service.IRegionService;
import com.yi.core.cms.CmsEnum;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class RegionGroupServiceImpl implements IRegionGroupService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(RegionGroupServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private RegionGroupDao regionGroupDao;

	@Resource
	private IRegionService regionService;

	private EntityListVoBoSimpleConvert<RegionGroup, RegionGroupBo, RegionGroupVo, RegionGroupSimple, RegionGroupListVo> regionGroupConvert;

	/**
	 * 分页查询RegionGroup
	 **/
	@Override
	public Page<RegionGroup> query(Pagination<RegionGroup> query) {
		query.setEntityClazz(RegionGroup.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// 排序
			list1.add(criteriaBuilder.asc(root.get(RegionGroup_.sort)));
			// 非删除
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(RegionGroup_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<RegionGroup> page = regionGroupDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: RegionGroup
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RegionGroupListVo> queryListVo(Pagination<RegionGroup> query) {
		Page<RegionGroup> pages = this.query(query);
		List<RegionGroupListVo> listVos = regionGroupConvert.toListVos(pages.getContent());
		return new PageImpl<RegionGroupListVo>(listVos, query.getPageRequest(), pages.getTotalElements());

	}

	/**
	 * 创建RegionGroup
	 **/
	@Override
	public RegionGroup addRegionGroup(RegionGroup regionGroup) {
		if (regionGroup == null) {
			throw new BusinessException("提交数据不能为空");
		}
		if (CollectionUtils.isEmpty(regionGroup.getRegions())) {
			throw new BusinessException("地区数据不能为空");
		}
		regionGroup.setName(regionGroup.getName().trim());
		int checkName = regionGroupDao.countByNameAndDeleted(regionGroup.getName(), Deleted.DEL_FALSE);
		if (checkName > 0) {
			LOG.error("区域名称（{}）重复", regionGroup.getName());
			throw new BusinessException("区域名称不能重复");
		}
		RegionGroup dbRegionGroup = regionGroupDao.save(regionGroup);
		// 批量保存地区
		regionService.batchAddRegion(dbRegionGroup);
		return dbRegionGroup;
	}

	/**
	 * 创建RegionGroup
	 **/
	@Override
	public RegionGroupVo addRegionGroup(RegionGroupBo regionGroupBo) {
		return regionGroupConvert.toVo(this.addRegionGroup(regionGroupConvert.toEntity(regionGroupBo)));
	}

	/**
	 * 更新RegionGroup
	 **/
	@Override
	public RegionGroup updateRegionGroup(RegionGroup regionGroup) {
		if (regionGroup == null || regionGroup.getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		if (CollectionUtils.isEmpty(regionGroup.getRegions())) {
			throw new BusinessException("地区数据不能为空");
		}
		regionGroup.setName(regionGroup.getName().trim());
		int checkName = regionGroupDao.countByNameAndDeletedAndIdNot(regionGroup.getName(), Deleted.DEL_FALSE, regionGroup.getId());
		if (checkName > 0) {
			LOG.error("区域名称（{}）重复", regionGroup.getName());
			throw new BusinessException("区域名称不能重复");
		}
		RegionGroup dbRegionGroup = regionGroupDao.getOne(regionGroup.getId());
		if (dbRegionGroup != null) {
			AttributeReplication.copying(regionGroup, dbRegionGroup, RegionGroup_.name, RegionGroup_.sort, RegionGroup_.state);
			// 批量更新地区
			regionService.batchUpdateRegion(regionGroup);
		}
		return dbRegionGroup;
	}

	/**
	 * 更新RegionGroup
	 **/
	@Override
	public RegionGroupVo updateRegionGroup(RegionGroupBo regionGroupBo) {
		return regionGroupConvert.toVo(this.updateRegionGroup(regionGroupConvert.toEntity(regionGroupBo)));
	}

	/**
	 * 删除RegionGroup
	 **/
	@Override
	public void removeRegionGroupById(int id) {
		RegionGroup dbRegionGroup = regionGroupDao.getOne(id);
		if (dbRegionGroup != null) {
			dbRegionGroup.setDeleted(Deleted.DEL_TRUE);
			dbRegionGroup.setDelTime(new Date());
			// 删除地区
			dbRegionGroup.setRegions(null);
		}
	}

	/**
	 * 根据ID得到RegionGroup
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RegionGroup getById(int id) {
		return this.regionGroupDao.getOne(id);
	}

	/**
	 * 根据ID得到RegionGroup
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RegionGroupVo getVoById(int id) {
		return regionGroupConvert.toVo(this.regionGroupDao.getOne(id));
	}

	/**
	 * 根据ID得到RegionGroupListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RegionGroupVo getListVoById(int id) {
		return regionGroupConvert.toVo(this.regionGroupDao.getOne(id));
	}

	protected void initConvert() {
		this.regionGroupConvert = new EntityListVoBoSimpleConvert<RegionGroup, RegionGroupBo, RegionGroupVo, RegionGroupSimple, RegionGroupListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<RegionGroup, RegionGroupVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RegionGroup, RegionGroupVo>(beanConvertManager) {
					@Override
					protected void postConvert(RegionGroupVo RegionGroupVo, RegionGroup RegionGroup) {

					}
				};
			}

			@Override
			protected BeanConvert<RegionGroup, RegionGroupListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RegionGroup, RegionGroupListVo>(beanConvertManager) {
					@Override
					protected void postConvert(RegionGroupListVo RegionGroupListVo, RegionGroup RegionGroup) {

					}
				};
			}

			@Override
			protected BeanConvert<RegionGroup, RegionGroupBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RegionGroup, RegionGroupBo>(beanConvertManager) {
					@Override
					protected void postConvert(RegionGroupBo RegionGroupBo, RegionGroup RegionGroup) {

					}
				};
			}

			@Override
			protected BeanConvert<RegionGroupBo, RegionGroup> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RegionGroupBo, RegionGroup>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<RegionGroup, RegionGroupSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RegionGroup, RegionGroupSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<RegionGroupSimple, RegionGroup> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RegionGroupSimple, RegionGroup>(beanConvertManager) {
					@Override
					public RegionGroup convert(RegionGroupSimple RegionGroupSimple) throws BeansException {
						return regionGroupDao.getOne(RegionGroupSimple.getId());
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RegionGroupListVo> getRegionGroupListVos() {
		return regionGroupConvert.toListVos(regionGroupDao.findByStateAndDeletedOrderBySortAsc(BasicEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE));
	}

	@Override
	public void updateState(int regionGroupId) {
		RegionGroup dbRegionGroup = regionGroupDao.getOne(regionGroupId);
		if (dbRegionGroup != null) {
			if (BasicEnum.STATE_DISABLE.getCode().equals(dbRegionGroup.getState())) {
				dbRegionGroup.setState(CmsEnum.STATE_ENABLE.getCode());
			} else if (BasicEnum.STATE_ENABLE.getCode().equals(dbRegionGroup.getState())) {
				dbRegionGroup.setState(CmsEnum.STATE_DISABLE.getCode());
			}
		}
	}
}
