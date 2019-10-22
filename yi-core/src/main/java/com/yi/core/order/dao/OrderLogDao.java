/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.order.domain.entity.OrderLog;
import com.yihz.common.orm.BaseDao;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface OrderLogDao extends JpaRepository<OrderLog, Integer>, JpaSpecificationExecutor<OrderLog> {

}