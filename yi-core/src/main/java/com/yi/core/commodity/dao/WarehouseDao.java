/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.commodity.domain.entity.Warehouse;

/**
 * 仓库
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public interface WarehouseDao extends JpaRepository<Warehouse, Integer>, JpaSpecificationExecutor<Warehouse> {

    Warehouse findByNameAndDeleted(String name, Integer deleted);

}