/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.cms.domain.bo.BannerBo;
import com.yi.core.cms.domain.entity.Banner;
import com.yi.core.cms.domain.vo.BannerListVo;
import com.yi.core.cms.domain.vo.BannerVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IBannerService {

	/**
	 * 根据ID得到Banner
	 * 
	 * @param bannerId
	 * @return
	 */
	Banner getBannerById(int bannerId);

	/**
	 * 根据ID得到BannerVo
	 * 
	 * @param bannerId
	 * @return
	 */
	BannerVo getBannerVoById(int bannerId);

	/**
	 * 根据ID得到BannerListVo
	 * 
	 * @param bannerId
	 * @return
	 */
	BannerVo getBannerListVoById(int bannerId);

	/**
	 * 根据Entity创建Banner
	 * 
	 * @param banner
	 * @return
	 */
	BannerVo addBanner(Banner banner);

	/**
	 * 根据BO创建Banner
	 * 
	 * @param bannerBo
	 * @return
	 */
	BannerVo addBanner(BannerBo bannerBo);

	/**
	 * 根据Entity更新Banner
	 * 
	 * @param banner
	 * @return
	 */
	BannerVo updateBanner(Banner banner);

	/**
	 * 根据BO更新Banner
	 * 
	 * @param bannerBo
	 * @return
	 */
	BannerVo updateBanner(BannerBo bannerBo);

	/**
	 * 删除Banner
	 * 
	 * @param bannerId
	 */
	void removeBannerById(int bannerId);

	/**
	 * 分页查询: Banner
	 * 
	 * @param query
	 * @return
	 */
	Page<Banner> query(Pagination<Banner> query);

	/**
	 * 分页查询: BannerListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<BannerListVo> queryListVo(Pagination<Banner> query);

	List<Banner> getBannerByStateIsTrue();
}
