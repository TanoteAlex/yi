/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.yi.core.order.domain.entity.SaleOrder_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.cms.dao.BannerDao;
import com.yi.core.cms.domain.bo.BannerBo;
import com.yi.core.cms.domain.entity.Banner;
import com.yi.core.cms.domain.entity.Banner_;
import com.yi.core.cms.domain.simple.BannerSimple;
import com.yi.core.cms.domain.vo.BannerListVo;
import com.yi.core.cms.domain.vo.BannerVo;
import com.yi.core.cms.service.IBannerService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.ValueUtils;

/**
 * Banner
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class BannerServiceImpl implements IBannerService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(BannerServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private BannerDao bannerDao;

	private EntityListVoBoSimpleConvert<Banner, BannerBo, BannerVo, BannerSimple, BannerListVo> bannerConvert;

	@Override
	public Banner getBannerById(int bannerId) {
		return bannerDao.getOne(bannerId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BannerVo getBannerVoById(int bannerId) {
		return bannerConvert.toVo(this.bannerDao.getOne(bannerId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BannerVo getBannerListVoById(int bannerId) {
		return bannerConvert.toVo(this.bannerDao.getOne(bannerId));
	}

	@Override
	public BannerVo addBanner(Banner banner) {
		return bannerConvert.toVo(bannerDao.save(banner));
	}

	@Override
	public BannerVo addBanner(BannerBo bannerBo) {
		return bannerConvert.toVo(bannerDao.save(bannerConvert.toEntity(bannerBo)));
	}

	@Override
	public BannerVo updateBanner(Banner banner) {
		Banner dbBanner = bannerDao.getOne(banner.getId());
		AttributeReplication.copying(banner, dbBanner, Banner_.title, Banner_.content, Banner_.bannerType,
				Banner_.imgPath, Banner_.url, Banner_.sort, Banner_.state);
		return bannerConvert.toVo(dbBanner);
	}

	@Override
	public BannerVo updateBanner(BannerBo bannerBo) {
		Banner dbBanner = bannerDao.getOne(bannerBo.getId());
		AttributeReplication.copying(bannerBo, dbBanner, Banner_.title, Banner_.content, Banner_.bannerType,
				Banner_.imgPath, Banner_.url, Banner_.sort, Banner_.state);
		return bannerConvert.toVo(dbBanner);
	}

	@Override
	public void removeBannerById(int bannerId) {
		Banner dbBanner = bannerDao.getOne(bannerId);
		if (dbBanner != null) {
			dbBanner.setDeleted(Deleted.DEL_TRUE);
			dbBanner.setDelTime(new Date());
		}
	}

	@Override
	public Page<Banner> query(Pagination<Banner> query) {
		query.setEntityClazz(Banner.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Banner_.deleted), Deleted.DEL_FALSE)));
		/*	list1.add(criteriaBuilder.desc(root.get(Banner_.createTime)));*/
			list1.add(criteriaBuilder.desc(root.get(Banner_.sort)));
			list1.add(criteriaBuilder.desc(root.get(Banner_.createTime)));

			criteriaQuery.orderBy(list1);
		}));

		Page<Banner> page = bannerDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BannerListVo> queryListVo(Pagination<Banner> query) {

		Page<Banner> pages = this.query(query);

		List<BannerListVo> vos = bannerConvert.toListVos(pages.getContent());
		return new PageImpl<BannerListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Banner> getBannerByStateIsTrue() {
		return bannerDao.findByDeletedAndStateOrderBySort(0, 0);
	}

	protected void initConvert() {
		this.bannerConvert = new EntityListVoBoSimpleConvert<Banner, BannerBo, BannerVo, BannerSimple, BannerListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<Banner, BannerVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Banner, BannerVo>(beanConvertManager) {
					@Override
					protected void postConvert(BannerVo BannerVo, Banner Banner) {

					}
				};
			}

			@Override
			protected BeanConvert<Banner, BannerListVo> createEntityToListVoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Banner, BannerListVo>(beanConvertManager) {
					@Override
					protected void postConvert(BannerListVo BannerListVo, Banner Banner) {

					}
				};
			}

			@Override
			protected BeanConvert<Banner, BannerBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Banner, BannerBo>(beanConvertManager) {
					@Override
					protected void postConvert(BannerBo BannerBo, Banner Banner) {

					}
				};
			}

			@Override
			protected BeanConvert<BannerBo, Banner> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BannerBo, Banner>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Banner, BannerSimple> createEntityToSimpleConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Banner, BannerSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<BannerSimple, Banner> createSimpleToEntityConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BannerSimple, Banner>(beanConvertManager) {
					@Override
					public Banner convert(BannerSimple BannerSimple) throws BeansException {
						return bannerDao.getOne(BannerSimple.getId());
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
