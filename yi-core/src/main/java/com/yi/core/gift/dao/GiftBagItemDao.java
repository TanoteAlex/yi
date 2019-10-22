/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.gift.domain.entity.GiftBagItem;

/**
 * 礼包项
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface GiftBagItemDao extends JpaRepository<GiftBagItem, Integer>, JpaSpecificationExecutor<GiftBagItem> {

}