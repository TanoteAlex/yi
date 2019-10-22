package com.yi.core.basic.service;/*
									* Powered By [yihz-framework]
									* Web Site: yihz
									* Since 2018 - 2018
									*/

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.MessageBo;
import com.yi.core.basic.domain.entity.Message;
import com.yi.core.basic.domain.vo.MessageListVo;
import com.yi.core.basic.domain.vo.MessageVo;
import com.yihz.common.persistence.Pagination;

/**
 * 消息
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface IMessageService {

	Page<Message> query(Pagination<Message> query);

	/**
	 * 分页查询: Message
	 **/
	Page<MessageListVo> queryListVo(Pagination<Message> query);

	/**
	 * 创建Message
	 **/
	Message addMessage(Message message);
	
	/**
	 * 创建Message
	 **/
	MessageListVo addMessage(MessageBo message);

	/**
	 * 更新Message
	 **/
	Message updateMessage(Message message);
	
	/**
	 * 更新Message
	 **/
	MessageListVo updateMessage(MessageBo message);

	/**
	 * 删除Message
	 **/
	void removeMessageById(int messageId);

	/**
	 * 根据ID得到Message
	 **/
	Message getById(int messageId);

	/**
	 * 根据ID得到MessageVo
	 **/
	MessageVo getVoById(int messageId);

	/**
	 * 根据ID得到MessageListVo
	 **/
	MessageListVo getListVoById(int messageId);

	/**
	 * 修改消息显示状态
	 * 
	 * @param messageId
	 */
	void updateState(int messageId);

}
