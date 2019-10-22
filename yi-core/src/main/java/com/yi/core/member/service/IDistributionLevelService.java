/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import com.yi.core.member.domain.bo.DistributionLevelBo;
import com.yi.core.member.domain.entity.DistributionLevel;
import com.yi.core.member.domain.vo.DistributionLevelListVo;
import com.yi.core.member.domain.vo.DistributionLevelVo;
import com.yihz.common.persistence.Pagination;

/**
 * 分销等级
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IDistributionLevelService {

	/**
	 * 分页查询: DistributionLevel
	 **/
	Page<DistributionLevel> query(Pagination<DistributionLevel> query);

	/**
	 * 分页查询: DistributionLevel
	 **/
	Page<DistributionLevelListVo> queryListVo(Pagination<DistributionLevel> query);

	/**
	 * 创建DistributionLevel
	 **/
	DistributionLevel addDistributionLevel(DistributionLevel distributionLevel);

	/**
	 * 创建DistributionLevel
	 **/
	DistributionLevelVo addDistributionLevel(DistributionLevelBo distributionLevel);

	/**
	 * 更新DistributionLevel
	 **/
	DistributionLevel updateDistributionLevel(DistributionLevel distributionLevel);

	/**
	 * 更新DistributionLevel
	 **/
	DistributionLevelVo updateDistributionLevel(DistributionLevelBo distributionLevel);

	/**
	 * 删除DistributionLevel
	 **/
	void removeDistributionLevelById(int distributionLevelId);

	/**
	 * 根据ID得到DistributionLevel
	 **/
	DistributionLevel getById(int distributionLevelId);

	/**
	 * 根据ID得到DistributionLevelBo
	 **/
	DistributionLevelBo getBoById(int distributionLevelId);

	/**
	 * 根据ID得到DistributionLevelVo
	 **/
	DistributionLevelVo getVoById(int distributionLevelId);

	/**
	 * 根据ID得到DistributionLevelListVo
	 **/
	DistributionLevelListVo getListVoById(int distributionLevelId);

	/**
	 * 获取一级佣金比例
	 * 
	 * @return
	 */
	BigDecimal getFirstLevelCommissionRate();

	/**
	 * 获取二级佣金比例
	 * 
	 * @return
	 */
	BigDecimal getSecondLevelCommissionRate();

}
