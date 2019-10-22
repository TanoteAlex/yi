/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.dao.MessageDao;
import com.yi.core.basic.domain.bo.MessageBo;
import com.yi.core.basic.domain.entity.Message;
import com.yi.core.basic.domain.entity.Message_;
import com.yi.core.basic.domain.simple.MessageSimple;
import com.yi.core.basic.domain.vo.MessageListVo;
import com.yi.core.basic.domain.vo.MessageVo;
import com.yi.core.basic.service.IMemberMessageService;
import com.yi.core.basic.service.IMessageService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 消息
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class MessageServiceImpl implements IMessageService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private MessageDao messageDao;

	@Resource
	private IMemberMessageService memberMessageService;

	private EntityListVoBoSimpleConvert<Message, MessageBo, MessageVo, MessageSimple, MessageListVo> messageConvert;

	/**
	 * 分页查询Message
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<Message> query(Pagination<Message> query) {
		query.setEntityClazz(Message.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// 排序
			list1.add(criteriaBuilder.desc(root.get(Message_.createTime)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Message_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<Message> page = messageDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: Message
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MessageListVo> queryListVo(Pagination<Message> query) {
		query.setEntityClazz(Message.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// 排序
			list1.add(criteriaBuilder.desc(root.get(Message_.createTime)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Message_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<Message> pages = messageDao.findAll(query, query.getPageRequest());
		List<MessageListVo> vos = messageConvert.toListVos(pages.getContent());
		return new PageImpl<MessageListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	/**
	 * 创建Message
	 **/
	@Override
	public Message addMessage(Message message) {
		message.setMessageType(BasicEnum.MESSAGE_TYPE_SYSTEM.getCode());
		return messageDao.save(message);
	}

	/**
	 * 创建Message
	 **/
	@Override
	public MessageListVo addMessage(MessageBo messageBo) {
		if (BasicEnum.PUSH_SCOPE_PART.getCode().equals(messageBo.getPushScope()) && CollectionUtils.isEmpty(messageBo.getMembers())) {
			throw new BusinessException("请选择推送会员");
		}
		Message dbMessage = this.addMessage(messageConvert.toEntity(messageBo));
		// 全部会员
		if (BasicEnum.PUSH_SCOPE_ALL.getCode().equals(messageBo.getPushScope())) {
			memberMessageService.sendMessagesByAll(dbMessage);
			// 指定会员
		} else if (BasicEnum.PUSH_SCOPE_PART.getCode().equals(messageBo.getPushScope())) {
			memberMessageService.sendMessagesByPart(dbMessage, messageBo.getMembers());
		}
		return messageConvert.toListVo(dbMessage);
	}

	/**
	 * 更新Message
	 **/
	@Override
	public Message updateMessage(Message message) {
		Message dbMessage = messageDao.getOne(message.getId());
		AttributeReplication.copying(message, dbMessage, Message_.title, Message_.content, Message_.messageType, Message_.sort, Message_.state);
		return dbMessage;
	}

	/**
	 * 更新Message
	 **/
	@Override
	public MessageListVo updateMessage(MessageBo messageBo) {
		Message dbMessage = this.updateMessage(messageConvert.toEntity(messageBo));
		return messageConvert.toListVo(dbMessage);
	}

	/**
	 * 根据ID得到Message
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Message getById(int id) {
		return this.messageDao.getOne(id);
	}

	/**
	 * 根据ID得到Message
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MessageVo getVoById(int id) {
		return messageConvert.toVo(this.messageDao.getOne(id));
	}

	/**
	 * 根据ID得到MessageListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MessageListVo getListVoById(int id) {
		return messageConvert.toListVo(this.messageDao.getOne(id));
	}

	/**
	 * 删除Message
	 **/
	@Override
	public void removeMessageById(int messageId) {
		// 删除消息
		Message dbMessage = this.getById(messageId);
		if (dbMessage != null) {
			dbMessage.setDeleted(Deleted.DEL_TRUE);
			dbMessage.setDelTime(new Date());
			// 删除已经发送给会员的消息
			memberMessageService.removeByMessage(messageId);
		}
	}

	protected void initConvert() {
		this.messageConvert = new EntityListVoBoSimpleConvert<Message, MessageBo, MessageVo, MessageSimple, MessageListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Message, MessageVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Message, MessageVo>(beanConvertManager) {
					@Override
					protected void postConvert(MessageVo MessageVo, Message Message) {

					}
				};
			}

			@Override
			protected BeanConvert<Message, MessageListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Message, MessageListVo>(beanConvertManager) {
					@Override
					protected void postConvert(MessageListVo MessageListVo, Message Message) {

					}
				};
			}

			@Override
			protected BeanConvert<Message, MessageBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Message, MessageBo>(beanConvertManager) {
					@Override
					protected void postConvert(MessageBo MessageBo, Message Message) {

					}
				};
			}

			@Override
			protected BeanConvert<MessageBo, Message> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MessageBo, Message>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Message, MessageSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Message, MessageSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<MessageSimple, Message> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MessageSimple, Message>(beanConvertManager) {
					@Override
					public Message convert(MessageSimple MessageSimple) throws BeansException {
						return messageDao.getOne(MessageSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	/**
	 * 修改消息显示状态
	 */
	@Override
	public void updateState(int messageId) {
		Message dbMessage = this.getById(messageId);
		if (dbMessage != null) {
			if (BasicEnum.DISPLAY_NO.getCode().equals(dbMessage.getState())) {
				dbMessage.setState(BasicEnum.DISPLAY_YES.getCode());
			} else if (BasicEnum.DISPLAY_YES.getCode().equals(dbMessage.getState())) {
				dbMessage.setState(BasicEnum.DISPLAY_NO.getCode());
			}
		}
	}

}
