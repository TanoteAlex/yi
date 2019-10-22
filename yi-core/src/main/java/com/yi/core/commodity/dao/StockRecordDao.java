package com.yi.core.commodity.dao;/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */


import com.yi.core.commodity.domain.entity.StockRecord;
import com.yihz.common.orm.BaseDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
*  *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
*/
public interface StockRecordDao extends JpaRepository<StockRecord, Integer> ,JpaSpecificationExecutor<StockRecord>{

}