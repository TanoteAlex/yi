/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service.impl;

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

import com.yi.core.commodity.dao.BrandDao;
import com.yi.core.commodity.domain.bo.BrandBo;
import com.yi.core.commodity.domain.entity.Brand;
import com.yi.core.commodity.domain.entity.Brand_;
import com.yi.core.commodity.domain.simple.BrandSimple;
import com.yi.core.commodity.domain.vo.BrandListVo;
import com.yi.core.commodity.domain.vo.BrandVo;
import com.yi.core.commodity.service.IBrandService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 品牌
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class BrandServiceImpl implements IBrandService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(BrandServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private BrandDao brandDao;

	private EntityListVoBoSimpleConvert<Brand, BrandBo, BrandVo, BrandSimple, BrandListVo> brandConvert;

	@Override
	public Brand getBrandById(int brandId) {
		return brandDao.getOne(brandId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BrandVo getBrandVoById(int brandId) {

		return brandConvert.toVo(this.brandDao.getOne(brandId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BrandListVo getBrandListVoById(int brandId) {
		return brandConvert.toListVo(this.brandDao.getOne(brandId));
	}

	@Override
	public Brand addBrand(Brand brand) {
		brand.setCreateTime(new Date());
		return brandDao.save(brand);
	}

	@Override
	public BrandListVo addBrand(BrandBo brandBo) {
		brandBo.setCreateTime(new Date());
		return brandConvert.toListVo(brandDao.save(brandConvert.toEntity(brandBo)));
	}

	@Override
	public Brand updateBrand(Brand brand) {

		Brand dbBrand = brandDao.getOne(brand.getId());
		AttributeReplication.copying(brand, dbBrand, Brand_.brandNo, Brand_.cnName, Brand_.enName, Brand_.imgPath,
				Brand_.state);
		return dbBrand;
	}

	@Override
	public BrandListVo updateBrand(BrandBo brandBo) {
		Brand dbBrand = brandDao.getOne(brandBo.getId());
		AttributeReplication.copying(brandBo, dbBrand, Brand_.brandNo, Brand_.cnName, Brand_.enName, Brand_.imgPath,
				Brand_.state);
		return brandConvert.toListVo(dbBrand);
	}

	@Override
	public void removeBrandById(int brandId) {
		Brand dbBrand = brandDao.getOne(brandId);
		if (dbBrand != null) {
			dbBrand.setDeleted(Deleted.DEL_TRUE);
			dbBrand.setDelTime(new Date());
		}
		// brandDao.delete(brandId);
	}

	@Override
	public Page<Brand> query(Pagination<Brand> query) {
		query.setEntityClazz(Brand.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Brand_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<Brand> page = brandDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BrandListVo> queryListVo(Pagination<Brand> query) {

		Page<Brand> pages = this.query(query);

		List<BrandListVo> vos = brandConvert.toListVos(pages.getContent());
		return new PageImpl<BrandListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.brandConvert = new EntityListVoBoSimpleConvert<Brand, BrandBo, BrandVo, BrandSimple, BrandListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<Brand, BrandVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Brand, BrandVo>(beanConvertManager) {
					@Override
					protected void postConvert(BrandVo BrandVo, Brand Brand) {

					}
				};
			}

			@Override
			protected BeanConvert<Brand, BrandListVo> createEntityToListVoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Brand, BrandListVo>(beanConvertManager) {
					@Override
					protected void postConvert(BrandListVo BrandListVo, Brand Brand) {

					}
				};
			}

			@Override
			protected BeanConvert<Brand, BrandBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Brand, BrandBo>(beanConvertManager) {
					@Override
					protected void postConvert(BrandBo BrandBo, Brand Brand) {

					}
				};
			}

			@Override
			protected BeanConvert<BrandBo, Brand> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BrandBo, Brand>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Brand, BrandSimple> createEntityToSimpleConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Brand, BrandSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<BrandSimple, Brand> createSimpleToEntityConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BrandSimple, Brand>(beanConvertManager) {
					@Override
					public Brand convert(BrandSimple BrandSimple) throws BeansException {
						return brandDao.getOne(BrandSimple.getId());
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
