/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.SmsRecord;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface SmsRecordDao extends JpaRepository<SmsRecord, Integer>, JpaSpecificationExecutor<SmsRecord> {

	List<SmsRecord> findBySendDateAfterAndSmsStateIs(Date sendDate, Integer smsState);

}