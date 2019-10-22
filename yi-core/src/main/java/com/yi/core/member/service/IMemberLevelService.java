/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.member.domain.bo.MemberLevelBo;
import com.yi.core.member.domain.entity.MemberLevel;
import com.yi.core.member.domain.vo.MemberLevelListVo;
import com.yi.core.member.domain.vo.MemberLevelVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IMemberLevelService {

	/**
	 * 根据ID得到MemberLevel
	 * 
	 * @param memberLevelId
	 * @return
	 */
	MemberLevel getMemberLevelById(int memberLevelId);

	/**
	 * 根据ID得到MemberLevelVo
	 * 
	 * @param memberLevelId
	 * @return
	 */
	MemberLevelVo getMemberLevelVoById(int memberLevelId);

	/**
	 * 根据ID得到MemberLevelListVo
	 * 
	 * @param memberLevelId
	 * @return
	 */
	MemberLevelListVo getMemberLevelListVoById(int memberLevelId);

	/**
	 * 根据Entity创建MemberLevel
	 * 
	 * @param memberLevel
	 * @return
	 */
	MemberLevelVo addMemberLevel(MemberLevel memberLevel);

	/**
	 * 根据BO创建MemberLevel
	 * 
	 * @param memberLevelBo
	 * @return
	 */
	MemberLevelVo addMemberLevel(MemberLevelBo memberLevelBo);

	/**
	 * 根据Entity更新MemberLevel
	 * 
	 * @param memberLevel
	 * @return
	 */
	MemberLevelVo updateMemberLevel(MemberLevel memberLevel);

	/**
	 * 根据BO更新MemberLevel
	 * 
	 * @param memberLevelBo
	 * @return
	 */
	MemberLevelVo updateMemberLevel(MemberLevelBo memberLevelBo);

	/**
	 * 删除MemberLevel
	 * 
	 * @param memberLevelId
	 */
	void removeMemberLevelById(int memberLevelId);

	/**
	 * 分页查询: MemberLevel
	 * 
	 * @param query
	 * @return
	 */
	Page<MemberLevel> query(Pagination<MemberLevel> query);

	/**
	 * 分页查询: MemberLevelListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<MemberLevelListVo> queryListVo(Pagination<MemberLevel> query);

	/**
	 * 查询会员等级全部数据
	 * 
	 * @return
	 */
	List<MemberLevelListVo> queryAll();

	/**
	 * 根据积分 计算会员等级
	 * 
	 * @param integral
	 * @return
	 */
	MemberLevel calculateLevelByIntegral(int integral);

	/**
	 * 获取默认的会员等级
	 * 
	 * @return
	 */
	MemberLevel getDefaultMemberLevel();

	/**
	 * 查询VIP会员等级
	 * 
	 * @return
	 */
	MemberLevel getVipMemberLevel();

}
