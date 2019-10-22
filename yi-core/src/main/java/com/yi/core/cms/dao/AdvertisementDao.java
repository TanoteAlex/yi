/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.cms.domain.entity.Advertisement;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface AdvertisementDao
		extends JpaRepository<Advertisement, Integer>, JpaSpecificationExecutor<Advertisement> {

	List<Advertisement> findByPosition_idAndStateAndDeleted(Integer positionId, Integer state, Integer deleted);

	List<Advertisement> findByLinkIdAndLinkType(Integer id, Integer linkType);

}