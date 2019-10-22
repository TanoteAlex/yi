/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.cms.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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

import com.yi.core.cms.dao.RecommendCommodityDao;
import com.yi.core.cms.domain.bo.RecommendCommodityBo;
import com.yi.core.cms.domain.entity.Recommend;
import com.yi.core.cms.domain.entity.RecommendCommodity;
import com.yi.core.cms.domain.entity.RecommendCommodityId;
import com.yi.core.cms.domain.entity.RecommendCommodity_;
import com.yi.core.cms.domain.simple.RecommendCommoditySimple;
import com.yi.core.cms.domain.vo.RecommendCommodityListVo;
import com.yi.core.cms.domain.vo.RecommendCommodityVo;
import com.yi.core.cms.service.IRecommendCommodityService;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.domain.entity.Stock;
import com.yi.core.common.Deleted;
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
public class RecommendCommodityServiceImpl implements IRecommendCommodityService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(RecommendCommodityServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private RecommendCommodityDao recommendCommodityDao;

	private EntityListVoBoSimpleConvert<RecommendCommodity, RecommendCommodityBo, RecommendCommodityVo, RecommendCommoditySimple, RecommendCommodityListVo> recommendCommodityConvert;

	/**
	 * 分页查询RecommendCommodity
	 **/
	@Override
	public Page<RecommendCommodity> query(Pagination<RecommendCommodity> query) {
		query.setEntityClazz(RecommendCommodity.class);
		Page<RecommendCommodity> page = recommendCommodityDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: RecommendCommodity
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RecommendCommodityListVo> queryListVo(Pagination<RecommendCommodity> query) {
		Page<RecommendCommodity> pages = this.query(query);
		List<RecommendCommodityListVo> vos = recommendCommodityConvert.toListVos(pages.getContent());
		return new PageImpl<RecommendCommodityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建RecommendCommodity
	 **/
	@Override
	public RecommendCommodity addRecommendCommodity(RecommendCommodity recommendCommodity) {
		return recommendCommodityDao.save(recommendCommodity);
	}

	/**
	 * 创建RecommendCommodity
	 **/
	@Override
	public RecommendCommodityListVo addRecommendCommodity(RecommendCommodityBo recommendCommodityBo) {
		return recommendCommodityConvert.toListVo(recommendCommodityDao.save(recommendCommodityConvert.toEntity(recommendCommodityBo)));
	}

	/**
	 * 更新RecommendCommodity
	 **/
	@Override
	public RecommendCommodity updateRecommendCommodity(RecommendCommodity recommendCommodity) {
		RecommendCommodity dbRecommendCommodity = recommendCommodityDao.getOne(recommendCommodity.getRecommendCommodityId());
		AttributeReplication.copying(recommendCommodity, dbRecommendCommodity, RecommendCommodity_.sort);
		return dbRecommendCommodity;
	}

	/**
	 * 更新RecommendCommodity
	 **/
	@Override
	public RecommendCommodityListVo updateRecommendCommodity(RecommendCommodityBo recommendCommodityBo) {
		RecommendCommodity dbRecommendCommodity = recommendCommodityDao.getOne(recommendCommodityBo.getRecommendCommodityId());
		AttributeReplication.copying(recommendCommodityBo, dbRecommendCommodity, RecommendCommodity_.sort);
		return recommendCommodityConvert.toListVo(dbRecommendCommodity);
	}

	/**
	 * 删除RecommendCommodity
	 **/
	@Override
	public void removeRecommendCommodityById(RecommendCommodityId recommendId) {
		recommendCommodityDao.deleteById(recommendId);
	}

	/**
	 * 根据ID得到RecommendCommodityBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RecommendCommodityBo getRecommendCommodityBoById(RecommendCommodityId recommendId) {
		return recommendCommodityConvert.toBo(this.recommendCommodityDao.getOne(recommendId));
	}

	/**
	 * 根据ID得到RecommendCommodityVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RecommendCommodityVo getRecommendCommodityVoById(RecommendCommodityId recommendId) {
		return recommendCommodityConvert.toVo(this.recommendCommodityDao.getOne(recommendId));
	}

	/**
	 * 根据ID得到RecommendCommodityListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RecommendCommodityListVo getListVoById(RecommendCommodityId recommendId) {
		return recommendCommodityConvert.toListVo(this.recommendCommodityDao.getOne(recommendId));
	}

	protected void initConvert() {
		this.recommendCommodityConvert = new EntityListVoBoSimpleConvert<RecommendCommodity, RecommendCommodityBo, RecommendCommodityVo, RecommendCommoditySimple, RecommendCommodityListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<RecommendCommodity, RecommendCommodityVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RecommendCommodity, RecommendCommodityVo>(beanConvertManager) {
					@Override
					protected void postConvert(RecommendCommodityVo recommendCommodityVo, RecommendCommodity recommendCommodity) {
					}
				};
			}

			@Override
			protected BeanConvert<RecommendCommodity, RecommendCommodityListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RecommendCommodity, RecommendCommodityListVo>(beanConvertManager) {
					@Override
					protected void postConvert(RecommendCommodityListVo recommendCommodityListVo, RecommendCommodity recommendCommodity) {
					}
				};
			}

			@Override
			protected BeanConvert<RecommendCommodity, RecommendCommodityBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RecommendCommodity, RecommendCommodityBo>(beanConvertManager) {
					@Override
					protected void postConvert(RecommendCommodityBo recommendCommodityBo, RecommendCommodity recommendCommodity) {
					}
				};
			}

			@Override
			protected BeanConvert<RecommendCommodityBo, RecommendCommodity> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RecommendCommodityBo, RecommendCommodity>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<RecommendCommodity, RecommendCommoditySimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RecommendCommodity, RecommendCommoditySimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<RecommendCommoditySimple, RecommendCommodity> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RecommendCommoditySimple, RecommendCommodity>(beanConvertManager) {
					@Override
					public RecommendCommodity convert(RecommendCommoditySimple recommendCommoditySimple) throws BeansException {
						return recommendCommodityDao.getOne(recommendCommoditySimple.getRecommendCommodityId());
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
	public void batchAddRecommendCommodity(Recommend recommend, Collection<RecommendCommodity> recommendCommodities) {
		if (recommend != null && CollectionUtils.isNotEmpty(recommendCommodities)) {
			recommendCommodities.forEach(tmp -> {
				tmp.setRecommendCommodityId(new RecommendCommodityId(recommend.getId(), tmp.getCommodity().getId()));
				tmp.setRecommend(recommend);
			});
			recommendCommodityDao.saveAll(recommendCommodities);
		}

	}

	@Override
	public void batchUpdateRecommendCommodity(Recommend recommend, Collection<RecommendCommodity> recommendCommodities) {
		if (recommend != null && CollectionUtils.isNotEmpty(recommendCommodities)) {
			// 整理数据
			recommendCommodities.forEach(tmp -> {
				tmp.setRecommendCommodityId(new RecommendCommodityId(recommend.getId(), tmp.getCommodity().getId()));
				tmp.setRecommend(recommend);
			});
			// 删除旧数据
			List<RecommendCommodity> dbRecommendCommodities = recommendCommodityDao.findByRecommend_id(recommend.getId());
			dbRecommendCommodities.forEach(tmp -> {
				this.removeRecommendCommodityById(tmp.getRecommendCommodityId());
			});
			// 保存数据
			recommendCommodityDao.saveAll(recommendCommodities);
		}
	}

	@Override
	public void removeByRecommend(Integer recommendId) {
		List<RecommendCommodity> dbRecommendCommodities = recommendCommodityDao.findByRecommend_id(recommendId);
		recommendCommodityDao.deleteInBatch(dbRecommendCommodities);

	}

	@Override
	public void removeByCommodity(Integer commodityId) {
		List<RecommendCommodity> dbRecommendCommodities = recommendCommodityDao.findByCommodity_id(commodityId);
		recommendCommodityDao.deleteInBatch(dbRecommendCommodities);
	}
}
