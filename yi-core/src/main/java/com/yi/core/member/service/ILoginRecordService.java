/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.member.service;

import org.springframework.data.domain.Page;

import com.yi.core.member.domain.bo.LoginRecordBo;
import com.yi.core.member.domain.entity.LoginRecord;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.vo.LoginRecordListVo;
import com.yi.core.member.domain.vo.LoginRecordVo;
import com.yihz.common.persistence.Pagination;

/**
 * 登录记录
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ILoginRecordService {

	/**
	 * 分页查询: LoginRecord
	 **/
	Page<LoginRecord> query(Pagination<LoginRecord> query);

	/**
	 * 分页查询: LoginRecord
	 **/
	Page<LoginRecordListVo> queryListVo(Pagination<LoginRecord> query);

	/**
	 * 分页查询: LoginRecord
	 **/
	Page<LoginRecordListVo> queryListVoForApp(Pagination<LoginRecord> query);

	/**
	 * 创建LoginRecord
	 **/
	LoginRecord addLoginRecord(LoginRecord loginRecord);

	/**
	 * 创建LoginRecord
	 **/
	LoginRecordListVo addLoginRecord(LoginRecordBo loginRecord);

	/**
	 * 更新LoginRecord
	 **/
	LoginRecord updateLoginRecord(LoginRecord loginRecord);

	/**
	 * 更新LoginRecord
	 **/
	LoginRecordListVo updateLoginRecord(LoginRecordBo loginRecord);

	/**
	 * 删除LoginRecord
	 **/
	void removeLoginRecordById(int loginRecordId);

	/**
	 * 根据ID得到LoginRecord
	 **/
	LoginRecord getLoginRecordById(int loginRecordId);

	/**
	 * 根据ID得到LoginRecordBo
	 **/
	LoginRecordBo getLoginRecordBoById(int loginRecordId);

	/**
	 * 根据ID得到LoginRecordVo
	 **/
	LoginRecordVo getLoginRecordVoById(int loginRecordId);

	/**
	 * 根据ID得到LoginRecordListVo
	 **/
	LoginRecordListVo getListVoById(int loginRecordId);

	/**
	 * 登录时 记录登录日志
	 * 
	 * @param member
	 * @param loginSource
	 */
	void addLoginRecordByLogin(Member member, String loginSource);

}
