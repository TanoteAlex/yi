/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.finance.domain.entity.SupplierCheckAccount;
import com.yihz.common.orm.BaseDao;


/**
*  *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
*/
public interface SupplierCheckAccountDao extends JpaRepository<SupplierCheckAccount, Integer> ,JpaSpecificationExecutor<SupplierCheckAccount>{

}