/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.order.domain.entity.AfterSaleProcess;

/**
 * 售后处理
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface AfterSaleProcessDao extends JpaRepository<AfterSaleProcess, Integer>, JpaSpecificationExecutor<AfterSaleProcess> {

}