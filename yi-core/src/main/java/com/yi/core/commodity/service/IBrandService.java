/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service;

import org.springframework.data.domain.Page;

import com.yi.core.commodity.domain.bo.BrandBo;
import com.yi.core.commodity.domain.entity.Brand;
import com.yi.core.commodity.domain.vo.BrandListVo;
import com.yi.core.commodity.domain.vo.BrandVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IBrandService {

	/**
	 * 根据ID得到Brand
	 * 
	 * @param brandId
	 * @return
	 */
	Brand getBrandById(int brandId);

	/**
	 * 根据ID得到BrandVo
	 * 
	 * @param brandId
	 * @return
	 */
	BrandVo getBrandVoById(int brandId);

	/**
	 * 根据ID得到BrandListVo
	 * 
	 * @param brandId
	 * @return
	 */
	BrandListVo getBrandListVoById(int brandId);

	/**
	 * 根据Entity创建Brand
	 * 
	 * @param brand
	 * @return
	 */
	Brand addBrand(Brand brand);

	/**
	 * 根据BO创建Brand
	 * 
	 * @param brandBo
	 * @return
	 */
	BrandListVo addBrand(BrandBo brandBo);

	/**
	 * 根据Entity更新Brand
	 * 
	 * @param brand
	 * @return
	 */
	Brand updateBrand(Brand brand);

	/**
	 * 根据BO更新Brand
	 * 
	 * @param brandBo
	 * @return
	 */
	BrandListVo updateBrand(BrandBo brandBo);

	/**
	 * 删除Brand
	 * 
	 * @param brandId
	 */
	void removeBrandById(int brandId);

	/**
	 * 分页查询: Brand
	 * 
	 * @param query
	 * @return
	 */
	Page<Brand> query(Pagination<Brand> query);

	/**
	 * 分页查询: BrandListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<BrandListVo> queryListVo(Pagination<Brand> query);

}
