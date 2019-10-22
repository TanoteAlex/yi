/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service;

import com.yi.core.activity.domain.simple.InviteActivitySimple;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.InviteActivityBo;
import com.yi.core.activity.domain.entity.InviteActivity;
import com.yi.core.activity.domain.vo.InviteActivityListVo;
import com.yi.core.activity.domain.vo.InviteActivityVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IInviteActivityService {

	/**
	 * 分页查询: InviteActivity
	 **/
	Page<InviteActivity> query(Pagination<InviteActivity> query);

	/**
	 * 分页查询: InviteActivity
	 **/
	Page<InviteActivityListVo> queryListVo(Pagination<InviteActivity> query);

	/**
	 * 分页查询: InviteActivity
	 **/
	Page<InviteActivityListVo> queryListVoForApp(Pagination<InviteActivity> query);

	/**
	 * 创建InviteActivity
	 **/
	InviteActivity addInviteActivity(InviteActivity inviteActivity);

	/**
	 * 创建InviteActivity
	 **/
	InviteActivityListVo addInviteActivity(InviteActivityBo inviteActivity);

	/**
	 * 更新InviteActivity
	 **/
	InviteActivity updateInviteActivity(InviteActivity inviteActivity);

	/**
	 * 更新InviteActivity
	 **/
	InviteActivityListVo updateInviteActivity(InviteActivityBo inviteActivity);

	/**
	 * 删除InviteActivity
	 **/
	void removeInviteActivityById(Integer inviteActivityId);

	/**
	 * 根据ID得到InviteActivity
	 **/
	InviteActivity getInviteActivityById(Integer inviteActivityId);

	/**
	 * 根据ID得到InviteActivityBo
	 **/
	InviteActivityBo getInviteActivityBoById(Integer inviteActivityId);

	/**
	 * 根据ID得到InviteActivityVo
	 **/
	InviteActivityVo getInviteActivityVoById(Integer inviteActivityId);

	/**
	 * 根据ID得到InviteActivityListVo
	 **/
	InviteActivityListVo getListVoById(Integer inviteActivityId);

	/**
	 * 获取最新的邀请有礼活动
	 **/
	InviteActivityVo getLatestInviteActivity(Integer memberId);

	EntityListVoBoSimpleConvert<InviteActivity, InviteActivityBo, InviteActivityVo, InviteActivitySimple, InviteActivityListVo> getInviteActivityConvert();

}
