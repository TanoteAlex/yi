/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.dao;

import com.yi.core.promotion.domain.entity.GroupBuyActivityRule;
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
public interface GroupBuyActivityRuleDao extends JpaRepository<GroupBuyActivityRule, Integer> ,JpaSpecificationExecutor<GroupBuyActivityRule> {

    List<GroupBuyActivityRule> findByGroupBuyActivity_Id(int id);

}