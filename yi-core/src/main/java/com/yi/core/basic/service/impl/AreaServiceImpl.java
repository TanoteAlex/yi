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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.basic.dao.AreaDao;
import com.yi.core.basic.domain.bo.AreaBo;
import com.yi.core.basic.domain.entity.Area;
import com.yi.core.basic.domain.entity.Area_;
import com.yi.core.basic.domain.simple.AreaSimple;
import com.yi.core.basic.domain.vo.AreaListVo;
import com.yi.core.basic.domain.vo.AreaVo;
import com.yi.core.basic.service.IAreaService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@CacheConfig(cacheNames = "area")
@Service
@Transactional
public class AreaServiceImpl implements IAreaService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AreaServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AreaDao areaDao;

	private EntityListVoBoSimpleConvert<Area, AreaBo, AreaVo, AreaSimple, AreaListVo> areaConvert;

	@Override
	public Area getAreaById(int areaId) {
		return areaDao.getOne(areaId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AreaVo getAreaVoById(int areaId) {

		return areaConvert.toVo(this.areaDao.getOne(areaId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AreaVo getAreaListVoById(int areaId) {
		return areaConvert.toVo(this.areaDao.getOne(areaId));
	}

	@Override
	public Area addArea(Area area) {
		return areaDao.save(area);
	}

	@Override
	public AreaVo addArea(AreaBo areaBo) {
		return areaConvert.toVo(areaDao.save(areaConvert.toEntity(areaBo)));
	}

	@Override
	public Area updateArea(Area area) {
		Area dbArea = areaDao.getOne(area.getId());
		AttributeReplication.copying(area, dbArea, Area_.parent, Area_.level, Area_.areaCode, Area_.name, Area_.shortName, Area_.pinYin, Area_.jianPin, Area_.cityCode,
				Area_.zipCode, Area_.center, Area_.longitude, Area_.latitude);
		return dbArea;
	}

	@CacheEvict(key = "#areaBo.getId()")
	@Override
	public AreaVo updateArea(AreaBo areaBo) {
		Area dbArea = areaDao.getOne(areaBo.getId());
		AttributeReplication.copying(areaBo, dbArea, Area_.parent, Area_.level, Area_.areaCode, Area_.name, Area_.shortName, Area_.pinYin, Area_.jianPin, Area_.cityCode,
				Area_.zipCode, Area_.center, Area_.longitude, Area_.latitude);
		return areaConvert.toVo(dbArea);
	}

	@CacheEvict(key = "#areaId")
	@Override
	public void removeAreaById(int areaId) {
		areaDao.deleteById(areaId);
	}

	@Override
	public Page<Area> query(Pagination<Area> query) {
		query.setEntityClazz(Area.class);

		Page<Area> page = areaDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AreaListVo> queryListVo(Pagination<Area> query) {
		Page<Area> pages = this.query(query);

		List<AreaListVo> vos = areaConvert.toListVos(pages.getContent());
		return new PageImpl<AreaListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.areaConvert = new EntityListVoBoSimpleConvert<Area, AreaBo, AreaVo, AreaSimple, AreaListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Area, AreaVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Area, AreaVo>(beanConvertManager) {
					@Override
					protected void postConvert(AreaVo AreaVo, Area Area) {

					}
				};
			}

			@Override
			protected BeanConvert<Area, AreaListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Area, AreaListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AreaListVo AreaListVo, Area Area) {

					}
				};
			}

			@Override
			protected BeanConvert<Area, AreaBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Area, AreaBo>(beanConvertManager) {
					@Override
					protected void postConvert(AreaBo AreaBo, Area Area) {

					}
				};
			}

			@Override
			protected BeanConvert<AreaBo, Area> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaBo, Area>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Area, AreaSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Area, AreaSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AreaSimple, Area> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AreaSimple, Area>(beanConvertManager) {
					@Override
					public Area convert(AreaSimple AreaSimple) throws BeansException {
						return areaDao.getOne(AreaSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	@Cacheable(key = "#parentId", unless = "#result eq null")
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Area> getAreasByParentId(Integer parentId) {
		return areaDao.findByParent_id(parentId);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AreaListVo> getAllAreas() {
		return areaConvert.toListVos(areaDao.findByParent_id(null));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Area> getProvinces() {
		return areaDao.findByParent(null);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Area getByAreaCode(String areaCode) {
		return areaDao.findByAreaCode(areaCode);
	}

}
