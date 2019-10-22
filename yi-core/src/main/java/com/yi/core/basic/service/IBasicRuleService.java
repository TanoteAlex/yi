package com.yi.core.basic.service;/*
									* Powered By [yihz-framework]
									* Web Site: yihz
									* Since 2018 - 2018
									*/

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.BasicRuleBo;
import com.yi.core.basic.domain.entity.BasicRule;
import com.yi.core.basic.domain.vo.BasicRuleListVo;
import com.yi.core.basic.domain.vo.BasicRuleVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface IBasicRuleService {

	Page<BasicRule> query(Pagination<BasicRule> query);

	/**
	 * 创建BasicRule
	 **/
	BasicRuleVo addBasicRule(BasicRuleBo basicRule);

	/**
	 * 更新BasicRule
	 **/
	BasicRuleVo updateBasicRule(BasicRuleBo basicRule);

	/**
	 * 删除BasicRule
	 **/
	void removeBasicRuleById(int basicRuleId);

	/**
	 * 根据ID得到BasicRuleVo
	 **/
	BasicRuleVo getBasicRuleVoById(int basicRuleId);

	/**
	 * 根据ID得到BasicRuleListVo
	 **/
	BasicRuleVo getListVoById(int basicRuleId);

	/**
	 * 分页查询: BasicRule
	 **/
	Page<BasicRuleListVo> queryListVo(Pagination<BasicRule> query);

	BasicRuleVo getAll();
}
