/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.cms.domain.bo.AdvertisementBo;
import com.yi.core.cms.domain.entity.Advertisement;
import com.yi.core.cms.domain.vo.AdvertisementListVo;
import com.yi.core.cms.domain.vo.AdvertisementVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IAdvertisementService {

	/**
	 * 根据ID得到Advertisement
	 * 
	 * @param advertisementId
	 * @return
	 */
	Advertisement getAdvertisementById(int advertisementId);

	/**
	 * 根据ID得到AdvertisementVo
	 * 
	 * @param advertisementId
	 * @return
	 */
	AdvertisementVo getAdvertisementVoById(int advertisementId);

	/**
	 * 根据ID得到AdvertisementListVo
	 * 
	 * @param advertisementId
	 * @return
	 */
	AdvertisementListVo getAdvertisementListVoById(int advertisementId);

	/**
	 * 根据Entity创建Advertisement
	 * 
	 * @param advertisement
	 * @return
	 */
	Advertisement addAdvertisement(Advertisement advertisement);

	/**
	 * 根据BO创建Advertisement
	 * 
	 * @param advertisementBo
	 * @return
	 */
	AdvertisementVo addAdvertisement(AdvertisementBo advertisementBo);

	/**
	 * 根据Entity更新Advertisement
	 * 
	 * @param advertisement
	 * @return
	 */
	Advertisement updateAdvertisement(Advertisement advertisement);

	/**
	 * 根据BO更新Advertisement
	 * 
	 * @param advertisementBo
	 * @return
	 */
	AdvertisementVo updateAdvertisement(AdvertisementBo advertisementBo);

	/**
	 * 删除Advertisement
	 * 
	 * @param advertisementId
	 */
	void removeAdvertisementById(int advertisementId);

	/**
	 * 分页查询: Advertisement
	 * 
	 * @param query
	 * @return
	 */
	Page<Advertisement> query(Pagination<Advertisement> query);

	/**
	 * 分页查询: AdvertisementListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<AdvertisementListVo> queryListVo(Pagination<Advertisement> query);

	/**
	 * 移动端查询轮播图
	 * 
	 * @return
	 */
	List<AdvertisementListVo> getAdvertisementListVoForApp(Integer positionType);

	/**
	 * 禁用
	 *
	 * @return
	 */
	AdvertisementVo updateStateDisable(int advertisementId);

	/**
	 * 启用
	 *
	 * @return
	 */
	AdvertisementVo updateStateEnable(int advertisementId);

}
