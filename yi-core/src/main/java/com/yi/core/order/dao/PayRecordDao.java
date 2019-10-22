/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.dao;

import com.yi.core.order.domain.entity.PayRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
*  *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
*/
public interface PayRecordDao extends JpaRepository<PayRecord, Integer>,JpaSpecificationExecutor<PayRecord> {

}