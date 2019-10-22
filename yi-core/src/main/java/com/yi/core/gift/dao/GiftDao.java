/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.gift.domain.entity.Gift;

/**
 * 礼物
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface GiftDao extends JpaRepository<Gift, Integer>, JpaSpecificationExecutor<Gift> {

	/**
	 * 查询待领取的礼物
	 * 
	 * @param state
	 * @param deleted
	 * @return
	 */
	List<Gift> findByStateAndDeleted(Integer state, Integer deleted);

}