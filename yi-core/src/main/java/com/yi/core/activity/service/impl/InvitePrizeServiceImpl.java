/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.yi.core.activity.service.ICouponService;
import com.yi.core.activity.service.IInviteActivityService;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.service.ICommodityService;
import com.yi.core.commodity.service.IProductService;
import com.yi.core.member.service.IMemberService;
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

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.InvitePrizeDao;
import com.yi.core.activity.domain.bo.InvitePrizeBo;
import com.yi.core.activity.domain.entity.InviteActivity;
import com.yi.core.activity.domain.entity.InvitePrize;
import com.yi.core.activity.domain.entity.InvitePrize_;
import com.yi.core.activity.domain.simple.InvitePrizeSimple;
import com.yi.core.activity.domain.vo.InvitePrizeListVo;
import com.yi.core.activity.domain.vo.InvitePrizeVo;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.activity.service.IInvitePrizeService;
import com.yi.core.common.Deleted;
import com.yi.core.member.service.IAccountService;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
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
public class InvitePrizeServiceImpl implements IInvitePrizeService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(InvitePrizeServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private InvitePrizeDao invitePrizeDao;

	@Resource
	private IInviteActivityService inviteActivityService;

	@Resource
	private IAccountService accountService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private ICommodityService commodityService;

	@Resource
	private IProductService productService;

	@Resource
	private ICouponService couponService;

	@Resource
	private IMemberService memberService;

	private EntityListVoBoSimpleConvert<InvitePrize, InvitePrizeBo, InvitePrizeVo, InvitePrizeSimple, InvitePrizeListVo> invitePrizeConvert;

	/**
	 * 分页查询InvitePrize
	 **/
	@Override
	public Page<InvitePrize> query(Pagination<InvitePrize> query) {
		query.setEntityClazz(InvitePrize.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(InvitePrize_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(InvitePrize_.createTime)));
		}));
		Page<InvitePrize> page = invitePrizeDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: InvitePrize
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<InvitePrizeListVo> queryListVo(Pagination<InvitePrize> query) {
		query.setEntityClazz(InvitePrize.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(InvitePrize_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(InvitePrize_.createTime)));
		}));
		Page<InvitePrize> pages = invitePrizeDao.findAll(query, query.getPageRequest());
		List<InvitePrizeListVo> vos = invitePrizeConvert.toListVos(pages.getContent());
		return new PageImpl<InvitePrizeListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: InvitePrize
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<InvitePrizeListVo> queryListVoForApp(Pagination<InvitePrize> query) {
		query.setEntityClazz(InvitePrize.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(InvitePrize_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(InvitePrize_.createTime)));
		}));
		Page<InvitePrize> pages = invitePrizeDao.findAll(query, query.getPageRequest());
		List<InvitePrizeListVo> vos = invitePrizeConvert.toListVos(pages.getContent());
		return new PageImpl<InvitePrizeListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建InvitePrize
	 **/
	@Override
	public InvitePrize addInvitePrize(InvitePrize invitePrize) {
		if (invitePrize.getState() == null) {
			invitePrize.setState(ActivityEnum.STATE_ENABLE.getCode());
		}
		return invitePrizeDao.save(invitePrize);
	}

	/**
	 * 创建InvitePrize
	 **/
	@Override
	public InvitePrizeListVo addInvitePrize(InvitePrizeBo invitePrizeBo) {
		return invitePrizeConvert.toListVo(this.addInvitePrize(invitePrizeConvert.toEntity(invitePrizeBo)));
	}

	/**
	 * 更新InvitePrize
	 **/
	@Override
	public InvitePrize updateInvitePrize(InvitePrize invitePrize) {
		InvitePrize dbInvitePrize = invitePrizeDao.getOne(invitePrize.getId());
		AttributeReplication.copying(invitePrize, dbInvitePrize, InvitePrize_.inviteActivity, InvitePrize_.prizeNo, InvitePrize_.prizeName, InvitePrize_.inviteNum,
				InvitePrize_.prizeType, InvitePrize_.integral, InvitePrize_.commodity, InvitePrize_.product, InvitePrize_.coupon, InvitePrize_.sort, InvitePrize_.state);
		return dbInvitePrize;
	}

	/**
	 * 更新InvitePrize
	 **/
	@Override
	public InvitePrizeListVo updateInvitePrize(InvitePrizeBo invitePrizeBo) {
		InvitePrize dbInvitePrize = this.updateInvitePrize(invitePrizeConvert.toEntity(invitePrizeBo));
		return invitePrizeConvert.toListVo(dbInvitePrize);
	}

	/**
	 * 删除InvitePrize
	 **/
	@Override
	public void removeInvitePrizeById(java.lang.Integer id) {
		if (invitePrizeDao.existsById(id)) {
			InvitePrize dbInvitePrize = this.getInvitePrizeById(id);
			dbInvitePrize.setDeleted(Deleted.DEL_TRUE);
			dbInvitePrize.setDelTime(new Date());
		}
	}

	/**
	 * 根据ID得到InvitePrize
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InvitePrize getInvitePrizeById(java.lang.Integer id) {
		if (invitePrizeDao.existsById(id)) {
			return this.invitePrizeDao.getOne(id);
		}
		return null;
	}

	/**
	 * 根据ID得到InvitePrizeBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InvitePrizeBo getInvitePrizeBoById(java.lang.Integer id) {
		return invitePrizeConvert.toBo(this.invitePrizeDao.getOne(id));
	}

	/**
	 * 根据ID得到InvitePrizeVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InvitePrizeVo getInvitePrizeVoById(java.lang.Integer id) {
		return invitePrizeConvert.toVo(this.invitePrizeDao.getOne(id));
	}

	/**
	 * 根据ID得到InvitePrizeListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public InvitePrizeListVo getListVoById(java.lang.Integer id) {
		return invitePrizeConvert.toListVo(this.invitePrizeDao.getOne(id));
	}

	protected void initConvert() {
		this.invitePrizeConvert = new EntityListVoBoSimpleConvert<InvitePrize, InvitePrizeBo, InvitePrizeVo, InvitePrizeSimple, InvitePrizeListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<InvitePrize, InvitePrizeVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<InvitePrize, InvitePrizeVo>(beanConvertManager) {
					@Override
					protected void postConvert(InvitePrizeVo invitePrizeVo, InvitePrize invitePrize) {
						if(invitePrize.getCommodity() != null && invitePrize.getProduct() !=null) {
							invitePrizeVo.setCommodity(commodityService.getCommodityConvert().toSimple(invitePrize.getCommodity()));
							invitePrizeVo.setProduct(productService.getProductConvert().toSimple(invitePrize.getProduct()));
						}
						if (invitePrize.getCoupon() != null) {
							invitePrizeVo.setCoupon(couponService.getCouponConvert().toSimple(invitePrize.getCoupon()));
						}

					}
				};
			}

			@Override
			protected BeanConvert<InvitePrize, InvitePrizeListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<InvitePrize, InvitePrizeListVo>(beanConvertManager) {
					@Override
					protected void postConvert(InvitePrizeListVo invitePrizeListVo, InvitePrize invitePrize) {

					}
				};
			}

			@Override
			protected BeanConvert<InvitePrize, InvitePrizeBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<InvitePrize, InvitePrizeBo>(beanConvertManager) {
					@Override
					protected void postConvert(InvitePrizeBo invitePrizeBo, InvitePrize invitePrize) {

					}
				};
			}

			@Override
			protected BeanConvert<InvitePrizeBo, InvitePrize> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<InvitePrizeBo, InvitePrize>(beanConvertManager) {
					@Override
					protected void postConvert(InvitePrize invitePrize, InvitePrizeBo invitePrizeBo) {
						// 获取所属任务
						if(invitePrizeBo.getCommodity() != null && invitePrizeBo.getProduct() !=null){
							invitePrize.setCommodity(commodityService.getById(invitePrizeBo.getCommodity().getId()));
							invitePrize.setProduct(productService.getById(invitePrizeBo.getProduct().getId()));
						}
						if(invitePrizeBo.getCoupon()!=null){
							invitePrize.setCoupon(couponService.getById(invitePrizeBo.getCoupon().getId()));
						}

					}
				};
			}

			@Override
			protected BeanConvert<InvitePrize, InvitePrizeSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<InvitePrize, InvitePrizeSimple>(beanConvertManager) {
					@Override
					protected void postConvert(InvitePrizeSimple invitePrizeSimple, InvitePrize invitePrize) {

					}
				};
			}

			@Override
			protected BeanConvert<InvitePrizeSimple, InvitePrize> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<InvitePrizeSimple, InvitePrize>(beanConvertManager) {
					@Override
					public InvitePrize convert(InvitePrizeSimple invitePrizeSimple) throws BeansException {
						return invitePrizeDao.getOne(invitePrizeSimple.getId());
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
	public void batchAddInvitePrize(InviteActivity inviteActivity, Collection<InvitePrize> invitePrizes) {
		if (inviteActivity != null && CollectionUtils.isNotEmpty(invitePrizes)) {
			invitePrizes.forEach(tmp -> {
				tmp.setInviteActivity(inviteActivity);
				tmp.setState(ActivityEnum.STATE_ENABLE.getCode());
			});
			invitePrizeDao.saveAll(invitePrizes);
		}
	}

	@Override
	public void batchUpdateInvitePrize(InviteActivity inviteActivity, Collection<InvitePrize> invitePrizes) {
		if (inviteActivity != null && CollectionUtils.isNotEmpty(invitePrizes)) {
			invitePrizes.forEach(tmp -> {
				tmp.setId(null);
				tmp.setInviteActivity(inviteActivity);
				tmp.setState(ActivityEnum.STATE_ENABLE.getCode());
			});
			// 删除数据 从新保存
			List<InvitePrize> dbInvitePrizes = invitePrizeDao.findByInviteActivity_id(inviteActivity.getId());
			dbInvitePrizes.forEach(tmp -> {
				invitePrizeDao.delete(tmp);
			});
			// 保存数据
			invitePrizeDao.saveAll(invitePrizes);
		}
	}

	@Override
	public void batchDeletedInvitePrize(Collection<InvitePrize> invitePrizes) {
		invitePrizeDao.deleteInBatch(invitePrizes);
	}

	@Override
	public void receiveIntegral(InvitePrizeBo invitePrizeBo) {
		if (invitePrizeBo == null || invitePrizeBo.getMember() == null || invitePrizeBo.getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		InvitePrize dbInvitePrize = this.getInvitePrizeById(invitePrizeBo.getId());
		if (dbInvitePrize == null) {
			throw new BusinessException("奖品领取失败，请联系客服处理！");
		}
		int inviteNum = memberService.getInviteNumByMemberId(invitePrizeBo.getMember().getId());
		if(inviteNum < dbInvitePrize.getInviteNum()){
			throw new BusinessException("邀请人数不够或领取其他奖品已被扣除！");
		}
		accountService.receiveInvitePrize(invitePrizeBo.getMember().getId(), dbInvitePrize.getIntegral(), dbInvitePrize.getId());
		memberService.subtractInviteNumByMemberId(invitePrizeBo.getMember().getId(),dbInvitePrize.getInviteNum());
	}

	@Override
	public void receiveCoupon(InvitePrizeBo invitePrizeBo) {
		if (invitePrizeBo == null || invitePrizeBo.getMember() == null || invitePrizeBo.getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		InvitePrize dbInvitePrize = this.getInvitePrizeById(invitePrizeBo.getId());
		if (dbInvitePrize == null) {
			throw new BusinessException("奖品领取失败，请联系客服处理！");
		}
		int inviteNum = memberService.getInviteNumByMemberId(invitePrizeBo.getMember().getId());
		if(inviteNum < dbInvitePrize.getInviteNum()){
			throw new BusinessException("邀请人数不够或领取其他奖品已被扣除！");
		}
		couponReceiveService.receiveInvitePrize(invitePrizeBo.getMember().getId(), dbInvitePrize.getCoupon().getId());
		memberService.subtractInviteNumByMemberId(invitePrizeBo.getMember().getId(),dbInvitePrize.getInviteNum());
	}

	@Override
	public void receiveCommodity(InvitePrizeBo invitePrizeBo) {
		if (invitePrizeBo == null || invitePrizeBo.getMember() == null || invitePrizeBo.getId() < 1 || invitePrizeBo.getShippingAddress() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		InvitePrize dbInvitePrize = this.getInvitePrizeById(invitePrizeBo.getId());
		if (dbInvitePrize == null) {
			throw new BusinessException("奖品领取失败，请联系客服处理！");
		}
		int inviteNum = memberService.getInviteNumByMemberId(invitePrizeBo.getMember().getId());
		if(inviteNum < dbInvitePrize.getInviteNum()){
			throw new BusinessException("邀请人数不够或领取其他奖品已被扣除！");
		}
		saleOrderService.receiveInvitePrize(invitePrizeBo.getMember().getId(), dbInvitePrize.getProduct().getId(), invitePrizeBo.getShippingAddress());
		memberService.subtractInviteNumByMemberId(invitePrizeBo.getMember().getId(),dbInvitePrize.getInviteNum());
	}
}
