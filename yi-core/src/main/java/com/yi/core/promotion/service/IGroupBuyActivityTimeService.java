/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.yi.core.promotion.domain.bo.GroupBuyActivityTimeBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.entity.GroupBuyActivityTime;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityTimeListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityTimeVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IGroupBuyActivityTimeService {

	/**
	 * 分页查询: GroupBuyActivityTime
	 * 
	 * @param query
	 * @return
	 */
	Page<GroupBuyActivityTime> query(Pagination<GroupBuyActivityTime> query);

	/**
	 * 分页查询: GroupBuyActivityTime
	 **/
	Page<GroupBuyActivityTimeListVo> queryListVo(Pagination<GroupBuyActivityTime> query);

	/**
	 * 创建GroupBuyActivityTime
	 **/
	GroupBuyActivityTimeListVo addGroupBuyActivityTime(GroupBuyActivityTimeBo groupBuyActivityTime);

	/**
	 * 创建GroupBuyActivityTime
	 **/
	GroupBuyActivityTime addGroupBuyActivityTime(GroupBuyActivityTime groupBuyActivityTime);

	/**
	 * 更新GroupBuyActivityTime
	 **/
	GroupBuyActivityTime updateGroupBuyActivityTime(GroupBuyActivityTime groupBuyActivityTime);

	/**
	 * 更新GroupBuyActivityTime
	 **/
	GroupBuyActivityTimeListVo updateGroupBuyActivityTime(GroupBuyActivityTimeBo groupBuyActivityTime);

	/**
	 * 删除GroupBuyActivityTime
	 **/
	void removeGroupBuyActivityTimeById(int groupBuyActivityTimeId);

	/**
	 * 根据ID得到GroupBuyActivityTimeBo
	 **/
	GroupBuyActivityTime getById(int groupBuyActivityTimeId);

	/**
	 * 根据ID得到GroupBuyActivityTimeBo
	 **/
	GroupBuyActivityTimeBo getBoById(int groupBuyActivityTimeId);

	/**
	 * 根据ID得到GroupBuyActivityTimeVo
	 **/
	GroupBuyActivityTimeVo getVoById(int groupBuyActivityTimeId);

	/**
	 * 根据ID得到GroupBuyActivityTimeListVo
	 **/
	GroupBuyActivityTimeListVo getListVoById(int groupBuyActivityTimeId);

	/**
	 * 批量新增团购时间
	 * 
	 * @param groupBuyActivity
	 * @param groupBuyActivityTimes
	 */
	void batchAddGroupBuyActivityTime(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityTime> groupBuyActivityTimes);

	/**
	 * 批量修改团购时间
	 * 
	 * @param groupBuyActivity
	 * @param groupBuyActivityTimes
	 */
	void batchUpdateGroupBuyActivityTime(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityTime> groupBuyActivityTimes);

}
