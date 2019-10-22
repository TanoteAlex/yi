/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.yi.core.promotion.domain.bo.GroupBuyActivityRuleBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.entity.GroupBuyActivityRule;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityRuleListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityRuleVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IGroupBuyActivityRuleService {

	/**
	 * 分页查询: GroupBuyActivityRule
	 * 
	 * @param query
	 * @return
	 */
	Page<GroupBuyActivityRule> query(Pagination<GroupBuyActivityRule> query);

	/**
	 * 分页查询: GroupBuyActivityRule
	 * 
	 * @param query
	 * @return
	 */
	Page<GroupBuyActivityRuleListVo> queryListVo(Pagination<GroupBuyActivityRule> query);

	/**
	 * 创建GroupBuyActivityRule
	 **/
	GroupBuyActivityRule addGroupBuyActivityRule(GroupBuyActivityRule groupBuyActivityRule);

	/**
	 * 创建GroupBuyActivityRule
	 **/
	GroupBuyActivityRuleListVo addGroupBuyActivityRule(GroupBuyActivityRuleBo groupBuyActivityRule);

	/**
	 * 更新GroupBuyActivityRule
	 **/
	GroupBuyActivityRule updateGroupBuyActivityRule(GroupBuyActivityRule groupBuyActivityRule);

	/**
	 * 更新GroupBuyActivityRule
	 **/
	GroupBuyActivityRuleListVo updateGroupBuyActivityRule(GroupBuyActivityRuleBo groupBuyActivityRule);

	/**
	 * 删除GroupBuyActivityRule
	 **/
	void removeGroupBuyActivityRuleById(int groupBuyActivityRuleId);

	/**
	 * 根据ID得到GroupBuyActivityRule
	 **/
	GroupBuyActivityRule getById(int groupBuyActivityRuleId);

	/**
	 * 根据ID得到GroupBuyActivityRule
	 **/
	GroupBuyActivityRuleBo getBoById(int groupBuyActivityRuleId);

	/**
	 * 根据ID得到GroupBuyActivityRuleVo
	 **/
	GroupBuyActivityRuleVo getVoById(int groupBuyActivityRuleId);

	/**
	 * 根据ID得到GroupBuyActivityRuleListVo
	 **/
	GroupBuyActivityRuleListVo getListVoById(int groupBuyActivityRuleId);

	/**
	 * 批量新增 团购规则
	 * 
	 * @param groupBuyActivity
	 * @param groupBuyActivityRules
	 */
	void batchAddGroupBuyActivityRule(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityRule> groupBuyActivityRules);

	/**
	 * 批量 修改 团购规则
	 * 
	 * @param groupBuyActivity
	 * @param groupBuyActivityRules
	 */
	void batchUpdateGroupBuyActivityRule(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityRule> groupBuyActivityRules);

}
