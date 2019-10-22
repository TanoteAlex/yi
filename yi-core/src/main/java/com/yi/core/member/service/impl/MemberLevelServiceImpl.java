/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.yi.core.commodity.service.ICommodityLevelDiscountService;
import com.yi.core.common.Deleted;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.dao.MemberLevelDao;
import com.yi.core.member.domain.bo.MemberLevelBo;
import com.yi.core.member.domain.entity.MemberLevel;
import com.yi.core.member.domain.entity.MemberLevel_;
import com.yi.core.member.domain.simple.MemberLevelSimple;
import com.yi.core.member.domain.vo.MemberLevelListVo;
import com.yi.core.member.domain.vo.MemberLevelVo;
import com.yi.core.member.service.IMemberLevelService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 会员等级
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class MemberLevelServiceImpl implements IMemberLevelService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(MemberLevelServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private MemberLevelDao memberLevelDao;

	@Resource
	private ICommodityLevelDiscountService commodityLevelDiscountService;

	private EntityListVoBoSimpleConvert<MemberLevel, MemberLevelBo, MemberLevelVo, MemberLevelSimple, MemberLevelListVo> memberLevelConvert;

	@Override
	public MemberLevel getMemberLevelById(int memberLevelId) {
		return memberLevelDao.getOne(memberLevelId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberLevelVo getMemberLevelVoById(int memberLevelId) {
		return memberLevelConvert.toVo(this.memberLevelDao.getOne(memberLevelId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberLevelListVo getMemberLevelListVoById(int memberLevelId) {
		return memberLevelConvert.toListVo(this.memberLevelDao.getOne(memberLevelId));
	}

	@Override
	public MemberLevelVo addMemberLevel(MemberLevel memberLevel) {
		if (memberLevel == null) {
			throw new BusinessException("提交数据不能为空");
		}
		int checkCount = memberLevelDao.countByNameOrRankAndDeleted(memberLevel.getName(), memberLevel.getRank(), Deleted.DEL_FALSE);
		if (checkCount > 0) {
			throw new BusinessException("该等级名称或级别已存在");
		}

		if (MemberEnum.DEFAULT_YES.getCode().equals(memberLevel.getDefaulted())) {
			// 查询默认 等级
			List<MemberLevel> dbMemberLevels = memberLevelDao.findByDefaultedAndDeleted(MemberEnum.DEFAULT_YES.getCode(), Deleted.DEL_FALSE);
			if (CollectionUtils.isNotEmpty(dbMemberLevels)) {
				// 如果有默认的 将默认的改为非默认
				dbMemberLevels.forEach(e -> {
					e.setDefaulted(MemberEnum.DEFAULT_NO.getCode());
				});
			}
		}
		return memberLevelConvert.toVo(memberLevelDao.save(memberLevel));
	}

	@Override
	public MemberLevelVo addMemberLevel(MemberLevelBo memberLevelBo) {
		return this.addMemberLevel(memberLevelConvert.toEntity(memberLevelBo));
	}

	@Override
	public MemberLevelVo updateMemberLevel(MemberLevel memberLevel) {
		if (memberLevel == null || !memberLevelDao.existsById(memberLevel.getId())) {
			throw new BusinessException("提交数据不能为空");
		}
		// 校验该等级是否存在- 级别或名字
		int checkCount = memberLevelDao.countByNameOrRankAndDeletedAndIdNot(memberLevel.getName(), memberLevel.getRank(), Deleted.DEL_FALSE, memberLevel.getId());
		if (checkCount > 0) {
			throw new BusinessException("该等级名称或级别已存在");
		}
		// 查询默认 等级
		List<MemberLevel> dbMemberLevels = memberLevelDao.findByDefaultedAndDeletedAndIdNot(MemberEnum.DEFAULT_YES.getCode(), Deleted.DEL_FALSE, memberLevel.getId());
		if (MemberEnum.DEFAULT_YES.getCode().equals(memberLevel.getDefaulted())) {
			if (CollectionUtils.isNotEmpty(dbMemberLevels)) {
				// 如果有默认的 将默认的改为非默认
				dbMemberLevels.forEach(e -> {
					e.setDefaulted(MemberEnum.DEFAULT_NO.getCode());
				});
			}
		} else {
			// 如果没有默认的 当前默认等级不能修改
			if (CollectionUtils.isEmpty(dbMemberLevels)) {
				throw new BusinessException("至少有一个默认等级");
			}
		}
		MemberLevel dbMemberLevel = memberLevelDao.getOne(memberLevel.getId());
		AttributeReplication.copying(memberLevel, dbMemberLevel, MemberLevel_.name, MemberLevel_.rank, MemberLevel_.growthValue, MemberLevel_.discount, MemberLevel_.defaulted);
		return memberLevelConvert.toVo(dbMemberLevel);
	}

	@Override
	public MemberLevelVo updateMemberLevel(MemberLevelBo memberLevelBo) {
		return this.updateMemberLevel(memberLevelConvert.toEntity(memberLevelBo));
	}

	/**
	 * TODO 该等级下如果有会员 必须先处理会员
	 */
	@Override
	public void removeMemberLevelById(int memberLevelId) {
		MemberLevel dbMemberLevel = memberLevelDao.getOne(memberLevelId);
		if (dbMemberLevel == null) {
			throw new BusinessException("请选择需要删除的数据");
		}
		if (MemberEnum.DEFAULT_YES.getCode().equals(dbMemberLevel.getDefaulted())) {
			throw new BusinessException("默认等级不允许删除");
		}
		dbMemberLevel.setDeleted(Deleted.DEL_TRUE);
		dbMemberLevel.setDelTime(new Date());
		// 删除等级折扣
		commodityLevelDiscountService.removeByMemberLevel(memberLevelId);
	}

	@Override
	public Page<MemberLevel> query(Pagination<MemberLevel> query) {
		query.setEntityClazz(MemberLevel.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(MemberLevel_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.asc(root.get(MemberLevel_.growthValue)));
		}));
		Page<MemberLevel> page = memberLevelDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MemberLevelListVo> queryListVo(Pagination<MemberLevel> query) {

		Page<MemberLevel> pages = this.query(query);

		List<MemberLevelListVo> vos = memberLevelConvert.toListVos(pages.getContent());
		return new PageImpl<MemberLevelListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.memberLevelConvert = new EntityListVoBoSimpleConvert<MemberLevel, MemberLevelBo, MemberLevelVo, MemberLevelSimple, MemberLevelListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<MemberLevel, MemberLevelVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberLevel, MemberLevelVo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberLevelVo MemberLevelVo, MemberLevel MemberLevel) {

					}
				};
			}

			@Override
			protected BeanConvert<MemberLevel, MemberLevelListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberLevel, MemberLevelListVo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberLevelListVo MemberLevelListVo, MemberLevel MemberLevel) {

					}
				};
			}

			@Override
			protected BeanConvert<MemberLevel, MemberLevelBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberLevel, MemberLevelBo>(beanConvertManager) {
					@Override
					protected void postConvert(MemberLevelBo MemberLevelBo, MemberLevel MemberLevel) {

					}
				};
			}

			@Override
			protected BeanConvert<MemberLevelBo, MemberLevel> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberLevelBo, MemberLevel>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<MemberLevel, MemberLevelSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberLevel, MemberLevelSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<MemberLevelSimple, MemberLevel> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<MemberLevelSimple, MemberLevel>(beanConvertManager) {
					@Override
					public MemberLevel convert(MemberLevelSimple MemberLevelSimple) throws BeansException {
						return memberLevelDao.getOne(MemberLevelSimple.getId());
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MemberLevelListVo> queryAll() {
		return memberLevelConvert.toListVos(memberLevelDao.findByDeleted(Deleted.DEL_FALSE));
	}

	@Override
	public MemberLevel calculateLevelByIntegral(int integral) {
		// 查询符合条件的会员等级
		List<MemberLevel> dbMemberLevels = memberLevelDao.findByGrowthValueLessThanEqualOrDefaultedAndDeletedOrderByGrowthValueDesc(integral, MemberEnum.DEFAULT_YES.getCode(),
				Deleted.DEL_FALSE);
		if (CollectionUtils.isNotEmpty(dbMemberLevels)) {
			return dbMemberLevels.get(0);
		}
		return null;
	}

	/**
	 * 查询默认的会员等级
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberLevel getDefaultMemberLevel() {
		return Optional.ofNullable(memberLevelDao.findByDefaultedAndDeleted(MemberEnum.DEFAULT_YES.getCode(), Deleted.DEL_FALSE)).map(e -> e.get(0)).orElse(null);
	}

	/**
	 * 查询VIP会员等级
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MemberLevel getVipMemberLevel() {
		return Optional.ofNullable(memberLevelDao.findByDeletedOrderByRankDesc(Deleted.DEL_FALSE)).map(e -> e.get(0)).orElse(null);
	}
}
