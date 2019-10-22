package com.yi.core.basic.service;/*
									* Powered By [yihz-framework]
									* Web Site: yihz
									* Since 2018 - 2018
									*/

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.MemberMessageBo;
import com.yi.core.basic.domain.entity.MemberMessage;
import com.yi.core.basic.domain.entity.Message;
import com.yi.core.basic.domain.vo.MemberMessageListVo;
import com.yi.core.basic.domain.vo.MemberMessageVo;
import com.yi.core.member.domain.bo.MemberBo;
import com.yihz.common.persistence.Pagination;

/**
 * 会员消息
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IMemberMessageService {

	/**
	 * 分页查询: MessageRead
	 **/
	Page<MemberMessage> query(Pagination<MemberMessage> query);

	/**
	 * 分页查询: MessageRead
	 **/
	Page<MemberMessageListVo> queryListVo(Pagination<MemberMessage> query);

	/**
	 * 分页查询: MessageRead
	 **/
	Page<MemberMessageListVo> queryListVoForApp(Pagination<MemberMessage> query);

	/**
	 * 创建MessageRead
	 **/
	MemberMessage addMemberMessage(MemberMessage messageRead);

	/**
	 * 创建MessageRead
	 **/
	MemberMessageListVo addMemberMessage(MemberMessageBo messageRead);

	/**
	 * 更新MessageRead
	 **/
	MemberMessage updateMemberMessage(MemberMessage messageRead);

	/**
	 * 更新MessageRead
	 **/
	MemberMessageListVo updateMemberMessage(MemberMessageBo messageRead);

	/**
	 * 删除MessageRead
	 **/
	void removeMemberMessageById(int messageReadId);

	/**
	 * 根据ID得到MessageReadVo
	 **/
	MemberMessage getById(int messageReadId);

	/**
	 * 根据ID得到MessageReadVo
	 **/
	MemberMessageVo getVoById(int messageReadId);

	/**
	 * 根据ID得到MessageReadListVo
	 **/
	MemberMessageListVo getListVoById(int messageReadId);

	/**
	 * 推送消息
	 * 
	 * @param message
	 */
	void sendMessagesByAll(Message message);

	/**
	 * 推送消息
	 * 
	 * @param message
	 * @param members
	 */
	void sendMessagesByPart(Message message, List<MemberBo> members);

	/**
	 * 根据消息ID 删除消息
	 * 
	 * @param messageId
	 */
	void removeByMessage(Integer messageId);

	/**
	 * 自动删除会员消息
	 */
	void autoRemoveMemberMessageByTask();

}
