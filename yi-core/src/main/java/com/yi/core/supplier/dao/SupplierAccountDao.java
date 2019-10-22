/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.supplier.domain.entity.SupplierAccount;

/**
 * 供应商账户
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface SupplierAccountDao extends JpaRepository<SupplierAccount, Integer>, JpaSpecificationExecutor<SupplierAccount> {

	SupplierAccount findBySupplierId(Integer supplier);

	List<SupplierAccount> findBySupplier_id(Integer supplierId);
}