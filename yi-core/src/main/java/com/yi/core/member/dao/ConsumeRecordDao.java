/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.member.domain.entity.ConsumeRecord;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface ConsumeRecordDao
		extends JpaRepository<ConsumeRecord, Integer>, JpaSpecificationExecutor<ConsumeRecord> {

}