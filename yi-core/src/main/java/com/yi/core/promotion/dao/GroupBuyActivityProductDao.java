/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.dao;

import com.yi.core.promotion.domain.entity.GroupBuyActivityProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


/**
*  *
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
*/
public interface GroupBuyActivityProductDao extends JpaRepository<GroupBuyActivityProduct, Integer> ,JpaSpecificationExecutor<GroupBuyActivityProduct> {

    List<GroupBuyActivityProduct> findByGroupBuyActivity_Id(int id);

}