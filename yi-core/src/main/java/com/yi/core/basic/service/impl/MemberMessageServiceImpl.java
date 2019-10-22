/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.ArrayList;
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
import com.yi.core.basic.dao.MemberMessageDao;
import com.yi.core.basic.domain.bo.MemberMessageBo;
import com.yi.core.basic.domain.entity.MemberMessage;
import com.yi.core.basic.domain.entity.MemberMessage_;
import com.yi.core.basic.domain.entity.Message;
import com.yi.core.basic.domain.entity.Message_;
import com.yi.core.basic.domain.simple.MemberMessageSimple;
import com.yi.core.basic.domain.vo.MemberMessageListVo;
import com.yi.core.basic.domain.vo.MemberMessageVo;
import com.yi.core.basic.service.IMemberMessageService;
import com.yi.core.common.Deleted;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IMemberService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.date.DateUtils;

/**
 * 会员消息
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class MemberMessageServiceImpl implements IMemberMessageService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(MemberMessageServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private MemberMessageDao memberMessageDao;

	@Resource
	private IMemberService memberService;

	private EntityListVoBoSimpleConvert<MemberMessage, MemberMessageBo, MemberMessageVo, MemberMessageSimple, MemberMessageListVo> memberMessageConvert;

	/**
	 * 分页查询MemberMessage
	 **/
	@Override
	public Page<MemberMessage> query(Pagination<MemberMessage> query) {
		query.setEntityClazz(MemberMessage.class);
		Page<MemberMessage> page = memberMessageDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: MemberMessage
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MemberMessageListVo> queryListVo(Pagination<MemberMessage> query) {
		query.setEntityClazz(MemberMessage.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// 排序
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(MemberMessage_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.asc(root.get(MemberMessage_.sort)));
			list1.add(criteriaBuilder.desc(root.get(MemberMessage_.createTime)));
		}));
		Page<MemberMessage> pages = memberMessageDao.findAll(query, query.getPageRequest());
		List<MemberMessageListVo> vos = memberMessageConvert.toListVos(pages.getContent());
		return new PageImpl<MemberMessageListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: MemberMessage
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MemberMessageListVo> queryListVoForApp(Pagination<MemberMessage> query) {
		query.setEntityClazz(MemberMessage.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// 排序
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(MemberMessage_.deleted), Deleted.DEL_FALSE)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(MemberMessage_.message).get(Message_.deleted), Deleted.DEL_FALSE)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(MemberMessage_.message).get(Message_.state), BasicEnum.DISPLAY_YES.getCode())));
			list1.add(criteriaBuilder.asc(root.get(MemberMessage_.sort)));
			list1.add(criteriaBuilder.desc(root.get(MemberMessage_.createTime)));
		}));
		Page<MemberMessage> pages = memberMessageDao.findAll(query, query.getPageRequest());
		List<MemberMessageListVo> vos = memberMessageConvert.toListVos(pages.getContent());
		return new PageImpl<MemberMessageListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建MemberMessage
	 **/
	@Override
	public MemberMessage addMemberMessage(MemberMessage memberMessage) {
		return memberMessageDao.save(memberMessage);
	}

	/**
	 * 创建MemberMessage
	 **/
	@Override
	public MemberMessageListVo addMemberMessage(MemberMessageBo memberMessageBo) {
		return memberMessageConvert.toListVo(this.addMemberMessage(memberMessageConvert.toEntity(memberMessageBo)));
	}

	/**
	 * 更新MemberMessage
	 **/
	@Override
	public MemberMessage updateMemberMessage(MemberMessage memberMessage) {
		MemberMessage dbMemberMessagee = memberMessageDao.getOne(memberMessage.getId());
		AttributeReplication.copying(memberMessage, dbMemberMessagee, MemberMessage_.title, MemberMessage_.content, MemberMessage_.messageType, MemberMessage_.sort,
				MemberMessage_.readState, MemberMessage_.readTime);
		return dbMemberMessagee;
	}

	/**
	 * 更新MemberMessage
	 **/
	@Override
	public MemberMessageListVo updateMemberMessage(MemberMessageBo memberMessageBo) {
		MemberMessage dbMemberMessage = this.updateMemberMessage(memberMessageConvert.toEntity(memberMessageBo));
		return memberMessageConvert.toListVo(dbMemberMessage);
	}

	/**
	 * 删除dbMemberMessage
	 **/
	@Override
	public void removeMemberMessageById(int id) {
		memberMessageDao.deleteById(id);
	}

	/**
	 * 根据ID得到MemberMessage
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberMessage getById(int id) {
		return this.memberMessageDao.getOne(id);
	}

	/**
	 * 根据ID得到MemberMessageListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberMessageVo getVoById(int id) {
		return memberMessageConvert.toVo(this.memberMessageDao.getOne(id));
	}

	/**
	 * 根据ID得到MemberMessageListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberMessageListVo getListVoById(int id) {
		return memberMessageConvert.toListVo(this.memberMessageDao.getOne(id));
	}

	protected void initConvert() {
		this.memberMessageConvert = new EntityListVoBoSimpleConvert<MemberMessage, MemberMessageBo, MemberMessageVo, MemberMessageSimple, MemberMessageListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<MemberMessage, MemberMessageVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberMessage, MemberMessageVo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberMessageVo memberMessageVo, MemberMessage memberMessage) {

					}
				};
			}

			@Override
			protected BeanConvert<MemberMessage, MemberMessageListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberMessage, MemberMessageListVo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberMessageListVo memberMessageListVo, MemberMessage memberMessage) {

					}
				};
			}

			@Override
			protected BeanConvert<MemberMessage, MemberMessageBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberMessage, MemberMessageBo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberMessageBo memberMessageBo, MemberMessage memberMessage) {

					}
				};
			}

			@Override
			protected BeanConvert<MemberMessageBo, MemberMessage> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberMessageBo, MemberMessage>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<MemberMessage, MemberMessageSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberMessage, MemberMessageSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<MemberMessageSimple, MemberMessage> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberMessageSimple, MemberMessage>(beanConvertManager) {
					@Override
					public MemberMessage convert(MemberMessageSimple memberMessageSimple) throws BeansException {
						return memberMessageDao.getOne(memberMessageSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	@Override
	public void sendMessagesByAll(Message message) {
		if (BasicEnum.DISPLAY_YES.getCode().equals(message.getState())) {
			List<Member> dbMembers = memberService.getMembersForSendAll();
			if (CollectionUtils.isNotEmpty(dbMembers)) {
				List<MemberMessage> memberMessages = new ArrayList<>();
				for (Member tmpMember : dbMembers) {
					if (tmpMember != null) {
						MemberMessage memberMessage = new MemberMessage();
						memberMessage.setMember(tmpMember);
						memberMessage.setMessage(message);
						memberMessage.setMessageType(BasicEnum.MESSAGE_TYPE_SYSTEM.getCode());
						memberMessage.setTitle(message.getTitle());
						memberMessage.setContent(message.getContent());
						memberMessage.setSort(message.getSort());
						memberMessage.setState(message.getState());
						memberMessage.setReadState(BasicEnum.READ_STATE_UNREAD.getCode());
						memberMessages.add(memberMessage);
					}
				}
				memberMessageDao.saveAll(memberMessages);
			}
		}
	}

	@Override
	public void sendMessagesByPart(Message message, List<MemberBo> members) {
		if (BasicEnum.DISPLAY_YES.getCode().equals(message.getState())) {
			List<Member> dbMembers = memberService.getMembersForSendPart(members);
			if (CollectionUtils.isNotEmpty(dbMembers)) {
				List<MemberMessage> memberMessages = new ArrayList<>();
				for (Member tmpMember : dbMembers) {
					if (tmpMember != null) {
						MemberMessage memberMessage = new MemberMessage();
						memberMessage.setMember(tmpMember);
						memberMessage.setMessage(message);
						memberMessage.setMessageType(BasicEnum.MESSAGE_TYPE_SYSTEM.getCode());
						memberMessage.setTitle(message.getTitle());
						memberMessage.setContent(message.getContent());
						memberMessage.setSort(message.getSort());
						memberMessage.setState(message.getState());
						memberMessage.setReadState(BasicEnum.READ_STATE_UNREAD.getCode());
						memberMessages.add(memberMessage);
					}
				}
				memberMessageDao.saveAll(memberMessages);
			}
		}
	}

	@Override
	public void removeByMessage(Integer messageId) {
		List<MemberMessage> dbMemberMessages = memberMessageDao.findByMessage_id(messageId);
		if (CollectionUtils.isNotEmpty(dbMemberMessages)) {
			memberMessageDao.deleteInBatch(dbMemberMessages);
		}
	}

	/**
	 * 自动删除会员消息
	 */
	@Override
	public void autoRemoveMemberMessageByTask() {
		// 查询三个月以上的会员消息
		Date date = DateUtils.addMonths(new Date(), 3);
		List<MemberMessage> dbMemberMessages = memberMessageDao.findByCreateTimeGreaterThan(date);
		if (CollectionUtils.isNotEmpty(dbMemberMessages)) {
			memberMessageDao.deleteInBatch(dbMemberMessages);
		}
	}
}
