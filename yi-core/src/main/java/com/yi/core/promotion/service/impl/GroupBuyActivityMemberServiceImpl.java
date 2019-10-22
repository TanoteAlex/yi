/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.service.impl;

import java.util.Collection;
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

import com.yi.core.promotion.dao.GroupBuyActivityMemberDao;
import com.yi.core.promotion.domain.bo.GroupBuyActivityMemberBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.entity.GroupBuyActivityMember;
import com.yi.core.promotion.domain.entity.GroupBuyActivityMember_;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityMemberListVo;
import com.yi.core.promotion.domain.simple.GroupBuyActivityMemberSimple;
import com.yi.core.promotion.domain.vo.GroupBuyActivityMemberVo;
import com.yi.core.promotion.service.IGroupBuyActivityMemberService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class GroupBuyActivityMemberServiceImpl implements IGroupBuyActivityMemberService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityMemberServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private GroupBuyActivityMemberDao groupBuyActivityMemberDao;

	private EntityListVoBoSimpleConvert<GroupBuyActivityMember, GroupBuyActivityMemberBo, GroupBuyActivityMemberVo, GroupBuyActivityMemberSimple, GroupBuyActivityMemberListVo> groupBuyActivityMemberConvert;

	/**
	 * 分页查询GroupBuyActivityMember
	 **/
	@Override
	public Page<GroupBuyActivityMember> query(Pagination<GroupBuyActivityMember> query) {
		query.setEntityClazz(GroupBuyActivityMember.class);
		Page<GroupBuyActivityMember> page = groupBuyActivityMemberDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 创建GroupBuyActivityMember
	 **/
	@Override
	public GroupBuyActivityMemberListVo addGroupBuyActivityMember(GroupBuyActivityMemberBo groupBuyActivityMemberBo) {
		return groupBuyActivityMemberConvert.toListVo(groupBuyActivityMemberDao.save(groupBuyActivityMemberConvert.toEntity(groupBuyActivityMemberBo)));
	}

	/**
	 * 更新GroupBuyActivityMember
	 **/
	@Override
	public GroupBuyActivityMemberListVo updateGroupBuyActivityMember(GroupBuyActivityMember groupBuyActivityMember) {
		GroupBuyActivityMember dbGroupBuyActivityMember = groupBuyActivityMemberDao.getOne(groupBuyActivityMember.getId());
		AttributeReplication.copying(groupBuyActivityMember, dbGroupBuyActivityMember, GroupBuyActivityMember_.groupBuyActivity, GroupBuyActivityMember_.memberType,
				GroupBuyActivityMember_.memberLevel);
		return groupBuyActivityMemberConvert.toListVo(dbGroupBuyActivityMember);
	}

	/**
	 * 更新GroupBuyActivityMember
	 **/
	@Override
	public GroupBuyActivityMemberListVo updateGroupBuyActivityMember(GroupBuyActivityMemberBo groupBuyActivityMemberBo) {
		GroupBuyActivityMember dbGroupBuyActivityMember = groupBuyActivityMemberDao.getOne(groupBuyActivityMemberBo.getId());
		AttributeReplication.copying(groupBuyActivityMemberBo, dbGroupBuyActivityMember, GroupBuyActivityMember_.guid, GroupBuyActivityMember_.groupBuyActivity,
				GroupBuyActivityMember_.memberType, GroupBuyActivityMember_.memberLevel);
		return groupBuyActivityMemberConvert.toListVo(dbGroupBuyActivityMember);
	}

	/**
	 * 删除GroupBuyActivityMember
	 **/
	@Override
	public void removeGroupBuyActivityMemberById(int id) {
		groupBuyActivityMemberDao.deleteById(id);
	}

	/**
	 * 根据ID得到GroupBuyActivityMemberBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityMemberBo getGroupBuyActivityMemberBoById(int id) {
		return groupBuyActivityMemberConvert.toBo(this.groupBuyActivityMemberDao.getOne(id));
	}

	/**
	 * 根据ID得到GroupBuyActivityMemberVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityMemberVo getGroupBuyActivityMemberVoById(int id) {
		return groupBuyActivityMemberConvert.toVo(this.groupBuyActivityMemberDao.getOne(id));
	}

	/**
	 * 根据ID得到GroupBuyActivityMemberListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityMemberListVo getListVoById(int id) {
		return groupBuyActivityMemberConvert.toListVo(this.groupBuyActivityMemberDao.getOne(id));
	}

	/**
	 * 分页查询: GroupBuyActivityMember
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GroupBuyActivityMemberListVo> queryListVo(Pagination<GroupBuyActivityMember> query) {
		Page<GroupBuyActivityMember> pages = this.query(query);
		List<GroupBuyActivityMemberListVo> vos = groupBuyActivityMemberConvert.toListVos(pages.getContent());
		return new PageImpl<GroupBuyActivityMemberListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	public void batchAddGroupBuyActivityMember(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityMember> groupBuyActivityMembers) {
		if (groupBuyActivity != null && CollectionUtils.isNotEmpty(groupBuyActivityMembers)) {
			groupBuyActivityMembers.forEach(tmp -> {
				tmp.setGroupBuyActivity(groupBuyActivity);
			});
			groupBuyActivityMemberDao.saveAll(groupBuyActivityMembers);
		}
	}

	@Override
	public void batchUpdateGroupBuyActivityMember(GroupBuyActivity groupBuyActivity, GroupBuyActivityMember groupBuyActivityMember) {
		if (groupBuyActivity != null && groupBuyActivityMember != null) {
			// 查询当前活动的会员集合
			List<GroupBuyActivityMember> dbGroupBuyActivityMembers = groupBuyActivityMemberDao.findByGroupBuyActivity_Id(groupBuyActivity.getId());
			dbGroupBuyActivityMembers.forEach(e -> {
				if (e != null && e.getId() > 0) {
					e.setGroupBuyActivity(groupBuyActivity);
					e.setMemberType(groupBuyActivityMember.getMemberType());
					e.setMemberLevel(groupBuyActivityMember.getMemberLevel());
					this.updateGroupBuyActivityMember(e);
				}
			});
		}
	}

	protected void initConvert() {
		this.groupBuyActivityMemberConvert = new EntityListVoBoSimpleConvert<GroupBuyActivityMember, GroupBuyActivityMemberBo, GroupBuyActivityMemberVo, GroupBuyActivityMemberSimple, GroupBuyActivityMemberListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<GroupBuyActivityMember, GroupBuyActivityMemberVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityMember, GroupBuyActivityMemberVo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityMemberVo groupBuyActivityMemberVo, GroupBuyActivityMember groupBuyActivityMember) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityMember, GroupBuyActivityMemberListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityMember, GroupBuyActivityMemberListVo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityMemberListVo groupBuyActivityMemberListVo, GroupBuyActivityMember groupBuyActivityMember) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityMember, GroupBuyActivityMemberBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityMember, GroupBuyActivityMemberBo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityMemberBo groupBuyActivityMemberBo, GroupBuyActivityMember groupBuyActivityMember) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityMemberBo, GroupBuyActivityMember> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityMemberBo, GroupBuyActivityMember>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityMember, GroupBuyActivityMemberSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityMember, GroupBuyActivityMemberSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityMemberSimple, GroupBuyActivityMember> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityMemberSimple, GroupBuyActivityMember>(beanConvertManager) {
					@Override
					public GroupBuyActivityMember convert(GroupBuyActivityMemberSimple groupBuyActivityMemberSimple) throws BeansException {
						return groupBuyActivityMemberDao.getOne(groupBuyActivityMemberSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}
}
