/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.gift.domain.entity.BlessWord;

/**
 * 祝福语
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface BlessWordDao extends JpaRepository<BlessWord, Integer>, JpaSpecificationExecutor<BlessWord> {

}