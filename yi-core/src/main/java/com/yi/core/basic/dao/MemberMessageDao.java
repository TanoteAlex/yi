/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.MemberMessage;

/**
 * 会员消息
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface MemberMessageDao extends JpaRepository<MemberMessage, Integer>, JpaSpecificationExecutor<MemberMessage> {

	/**
	 * 根据消息ID 查询会员消息
	 * 
	 * @param messageId
	 * @return
	 */
	List<MemberMessage> findByMessage_id(Integer messageId);

	/**
	 * 根据查询日期 查询消息
	 * 
	 * @param date
	 * @return
	 */
	List<MemberMessage> findByCreateTimeGreaterThan(Date date);

}