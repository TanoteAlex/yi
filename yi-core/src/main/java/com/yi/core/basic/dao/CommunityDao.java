/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.Community;

/**
 * 小区
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface CommunityDao extends JpaRepository<Community, Integer>, JpaSpecificationExecutor<Community> {

	/**
	 * 重复校验
	 * 
	 * @param memberId
	 * @param deleted
	 * @return
	 */
	Community findByMemberIdAndDeleted(int memberId, Integer deleted);

	/**
	 * 重复校验
	 * 
	 * @param memberId
	 * @param deleted
	 * @param communityId
	 * @return
	 */
	Community findByMemberIdAndDeletedAndIdNot(int memberId, Integer deleted, Integer communityId);

	/**
	 * 根据城市查询小区
	 * 
	 * @param City
	 * @param state
	 * @param deleted
	 * @return
	 */
	List<Community> findByCityAndStateAndDeleted(String city, Integer state, Integer deleted);

	/**
	 * 查询启用的小区
	 * 
	 * @param state
	 * @param deleted
	 * @return
	 */
	List<Community> findByStateAndDeleted(Integer state, Integer deleted);

}