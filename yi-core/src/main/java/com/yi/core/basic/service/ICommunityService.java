/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.CommunityBo;
import com.yi.core.basic.domain.entity.Community;
import com.yi.core.basic.domain.vo.CommissionSumVo;
import com.yi.core.basic.domain.vo.CommunityListVo;
import com.yi.core.basic.domain.vo.CommunityVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ICommunityService {

	/**
	 * 分页查询: Community
	 * 
	 * @param query
	 * @return
	 */
	Page<Community> query(Pagination<Community> query);

	/**
	 * 分页查询: CommunityListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CommunityListVo> queryListVo(Pagination<Community> query);

	/**
	 * 根据ID得到Community
	 * 
	 * @param communityId
	 * @return
	 */
	Community getCommunityById(int communityId);

	/**
	 * 根据ID得到CommunityVo
	 * 
	 * @param communityId
	 * @return
	 */
	CommunityVo getCommunityVoById(int communityId);

	/**
	 * 根据ID得到CommunityListVo
	 * 
	 * @param communityId
	 * @return
	 */
	CommunityListVo getCommunityListVoById(int communityId);

	/**
	 * 根据Entity创建Community
	 * 
	 * @param community
	 * @return
	 */
	Community addCommunity(Community community);

	/**
	 * 根据BO创建Community
	 * 
	 * @param communityBo
	 * @return
	 */
	CommunityVo addCommunity(CommunityBo communityBo);

	/**
	 * 根据Entity更新Community
	 * 
	 * @param community
	 * @return
	 */
	Community updateCommunity(Community community);

	/**
	 * 根据BO更新Community
	 * 
	 * @param communityBo
	 * @return
	 */
	CommunityVo updateCommunity(CommunityBo communityBo);

	/**
	 * 删除Community
	 * 
	 * @param communityId
	 */
	void removeCommunityById(int communityId);

	/**
	 * 查询提成总额
	 * 
	 * @return
	 */
	List<CommissionSumVo> commissionSum();

	/**
	 * 启用、禁用
	 * 
	 * @return
	 */
	CommunityListVo updateState(int communityId);

	/**
	 * communityId 查询小区信息
	 * 
	 * @return
	 */
	CommunityVo cellInformation(int communityId);

	/**
	 * 根据城市查询小区
	 * 
	 * @param city
	 * @return
	 */
	List<CommunityListVo> getCommunityByCity(String city);

	/**
	 * 查询小区的省市
	 * 
	 * @return
	 */
	Map<String, Set<String>> getCommunityProvinceCity();

	/**
	 * 获取小区信息
	 * 
	 * @param memberId
	 * @return
	 */
	CommunityVo getCommunityInfo(Integer memberId);
}
