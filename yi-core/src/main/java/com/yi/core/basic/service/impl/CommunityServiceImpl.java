/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.yi.core.basic.dao.CommunityDao;
import com.yi.core.basic.domain.bo.CommunityBo;
import com.yi.core.basic.domain.entity.Community;
import com.yi.core.basic.domain.entity.Community_;
import com.yi.core.basic.domain.simple.CommunitySimple;
import com.yi.core.basic.domain.vo.CommissionSumVo;
import com.yi.core.basic.domain.vo.CommunityListVo;
import com.yi.core.basic.domain.vo.CommunityVo;
import com.yi.core.basic.service.ICommunityService;
import com.yi.core.cms.CmsEnum;
import com.yi.core.common.Deleted;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.utils.PinyinUtils;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class CommunityServiceImpl implements ICommunityService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CommunityServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CommunityDao communityDao;

	@Resource
	private IMemberService memberService;

	@Resource
	private ISaleOrderService saleOrderService;

	private EntityListVoBoSimpleConvert<Community, CommunityBo, CommunityVo, CommunitySimple, CommunityListVo> communityConvert;

	@Override
	public Community getCommunityById(int communityId) {
		if (communityDao.existsById(communityId)) {
			return communityDao.getOne(communityId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommunityVo getCommunityVoById(int communityId) {
		return communityConvert.toVo(this.getCommunityById(communityId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommunityListVo getCommunityListVoById(int communityId) {
		return communityConvert.toListVo(this.getCommunityById(communityId));
	}

	@Override
	public Community addCommunity(Community community) {
		if (community == null) {
			throw new BusinessException("提交数据不能为空");
		}
		if (community.getMember().getId() < 1) {
			throw new BusinessException("请选择小区管理员");
		}
		// 校验当前管理员是否有其他小区
		Community checkAdmin = communityDao.findByMemberIdAndDeleted(community.getMember().getId(), Deleted.DEL_FALSE);
		if (checkAdmin != null) {
			throw new BusinessException("该管理员已有管理小区，请重新选择");
		}
		community.setInitials(PinyinUtils.getPinYinHeaderChar(community.getCity()));
		Community dbCommunity = communityDao.save(community);
		// 修改小区时 更新小区管理
		memberService.updateMemberTypeByUpdateCommunity(dbCommunity.getMember().getId(), dbCommunity);
		return dbCommunity;
	}

	@Override
	public CommunityVo addCommunity(CommunityBo communityBo) {
		return communityConvert.toVo(this.addCommunity(communityConvert.toEntity(communityBo)));
	}

	@Override
	public Community updateCommunity(Community community) {
		if (community == null || community.getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		if (community.getMember().getId() < 1) {
			throw new BusinessException("请选择小区管理员");
		}
		// 校验当前管理员是否有其他小区
		Community checkAdmin = communityDao.findByMemberIdAndDeletedAndIdNot(community.getMember().getId(), Deleted.DEL_FALSE, community.getId());
		if (checkAdmin != null) {
			throw new BusinessException("该管理员已有小区，请重新选择");
		}
		community.setInitials(PinyinUtils.getPinYinHeaderChar(community.getCity()));
		Community dbCommunity = communityDao.getOne(community.getId());
		AttributeReplication.copying(community, dbCommunity, Community_.member, Community_.province, Community_.city, Community_.district, Community_.address, Community_.state,
				Community_.commissionRate, Community_.initials, Community_.receivingAddress, Community_.imgPath, Community_.description);
		// 修改小区时 更新小区管理
		memberService.updateMemberTypeByUpdateCommunity(dbCommunity.getMember().getId(), dbCommunity);
		return dbCommunity;
	}

	@Override
	public CommunityVo updateCommunity(CommunityBo communityBo) {
		return communityConvert.toVo(this.updateCommunity(communityConvert.toEntity(communityBo)));
	}

	@Override
	public void removeCommunityById(int communityId) {
		Community dbCommunity = communityDao.getOne(communityId);
		if (dbCommunity != null) {
			dbCommunity.setDeleted(Deleted.DEL_TRUE);
			dbCommunity.setDelTime(new Date());
			// 修改会员的小区管理员状态
			memberService.updateMemberTypeByDeleteCommunity(dbCommunity.getMember().getId());
			// 删除会员的小区
			dbCommunity.setMembers(null);
		}
	}

	@Override
	public Page<Community> query(Pagination<Community> query) {
		query.setEntityClazz(Community.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Community_.deleted), Deleted.DEL_FALSE)));
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Community_.id)));
			list1.add(criteriaBuilder.desc(root.get(Community_.createTime)));
		}));
		Page<Community> page = communityDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override // setParameter
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CommunityListVo> queryListVo(Pagination<Community> query) {
		Page<Community> pages = this.query(query);
		List<CommunityListVo> vos = communityConvert.toListVos(pages.getContent());
		return new PageImpl<CommunityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	protected void initConvert() {
		this.communityConvert = new EntityListVoBoSimpleConvert<Community, CommunityBo, CommunityVo, CommunitySimple, CommunityListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Community, CommunityVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Community, CommunityVo>(beanConvertManager) {
					@Override
					protected void postConvert(CommunityVo CommunityVo, Community Community) {
					}
				};
			}

			@Override
			protected BeanConvert<Community, CommunityListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Community, CommunityListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CommunityListVo CommunityListVo, Community Community) {
					}
				};
			}

			@Override
			protected BeanConvert<Community, CommunityBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Community, CommunityBo>(beanConvertManager) {
					@Override
					protected void postConvert(CommunityBo CommunityBo, Community Community) {
					}
				};
			}

			@Override
			protected BeanConvert<CommunityBo, Community> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommunityBo, Community>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Community, CommunitySimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Community, CommunitySimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CommunitySimple, Community> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommunitySimple, Community>(beanConvertManager) {
					@Override
					public Community convert(CommunitySimple CommunitySimple) throws BeansException {
						return communityDao.getOne(CommunitySimple.getId());
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
	 * 小区业绩列表
	 * 
	 * @return
	 */
	public List<CommissionSumVo> commissionSum() {
		// 创建一个集合返回数据集
		List<CommissionSumVo> list = new ArrayList<>();
		// 获取小区列表
		List<Community> community = communityDao.findAll();
		// for (Community tmp : community) {
		// // 只查询可用状态的小区 0为可用
		// if (tmp.getDeleted() == 0) {
		// CommissionSumVo commissionSumVo = new CommissionSumVo();
		// // 获取小区名称
		// commissionSumVo.setAddress(tmp.getAddress());
		// // 获取提成比例
		// commissionSumVo.setProportions(tmp.getCommissionRate());
		// // 获取提成总额
		// if (accountRecordDao.tradeAmount(tmp.getMember().getId()) != null) {
		// commissionSumVo.setCommission(accountRecordDao.tradeAmount(tmp.getMember().getId()));
		// } else {
		// commissionSumVo.setCommission(new BigDecimal(0));
		// }
		// if (commissionSumVo != null) {
		// list.add(commissionSumVo);
		// }
		// }
		// }
		return list;
	}

	@Override
	public CommunityListVo updateState(int communityId) {
		Community dbCommunity = this.getCommunityById(communityId);
		if (dbCommunity != null) {
			if (CmsEnum.STATE_DISABLE.getCode().equals(dbCommunity.getState())) {
				dbCommunity.setState(CmsEnum.STATE_ENABLE.getCode());
			} else if (CmsEnum.STATE_ENABLE.getCode().equals(dbCommunity.getState())) {
				dbCommunity.setState(CmsEnum.STATE_DISABLE.getCode());
			}
			return communityConvert.toListVo(dbCommunity);
		}
		return null;
	}

	@Override
	public CommunityVo cellInformation(int communityId) {
		return communityConvert.toVo(communityDao.getOne(communityId));
	}

	/**
	 * communityId 根据城市查询小区
	 * 
	 * @return
	 */
	@Override
	public List<CommunityListVo> getCommunityByCity(String city) {
		return communityConvert.toListVos(communityDao.findByCityAndStateAndDeleted(city, BasicEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE));
	}

	/**
	 * 查询小区的省市
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, Set<String>> getCommunityProvinceCity() {
		Map<String, Set<String>> result = new HashMap<>();
		List<Community> dbCommunities = communityDao.findByStateAndDeleted(BasicEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isNotEmpty(dbCommunities)) {
			for (Community tmp : dbCommunities) {
				if (tmp != null) {
					if (result.containsKey(tmp.getProvince())) {
						result.get(tmp.getProvince()).add(tmp.getCity());
					} else {
						Set<String> cities = new HashSet<>();
						cities.add(tmp.getCity());
						result.put(tmp.getProvince(), cities);
					}
				}
			}
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommunityVo getCommunityInfo(Integer memberId) {
		Member dbMember = memberService.getMemberById(memberId);
		if (dbMember.getCommunity() != null) {
			Community dbCommunity = this.getCommunityById(dbMember.getCommunity().getId());
			CommunityVo dbCommunityVo = communityConvert.toVo(dbCommunity);
			// 如果当前会员是小区管理员 查询小区业绩
			// if (dbCommunity.getMember().getId() == dbMember.getId()) {
			// saleOrderService.get
			// }
			return dbCommunityVo;
		}
		return null;
	}

}
