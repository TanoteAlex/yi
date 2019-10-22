/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.supplier.domain.entity.SupplierAccountRecord;

/**
 * 供应商账户记录
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface SupplierAccountRecordDao extends JpaRepository<SupplierAccountRecord, Integer>, JpaSpecificationExecutor<SupplierAccountRecord> {

}