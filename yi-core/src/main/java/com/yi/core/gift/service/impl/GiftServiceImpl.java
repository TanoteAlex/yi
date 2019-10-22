/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.gift.GiftEnum;
import com.yi.core.gift.dao.GiftDao;
import com.yi.core.gift.domain.bo.GiftBagBo;
import com.yi.core.gift.domain.bo.GiftBo;
import com.yi.core.gift.domain.entity.Gift;
import com.yi.core.gift.domain.entity.GiftBag;
import com.yi.core.gift.domain.entity.GiftBag_;
import com.yi.core.gift.domain.entity.Gift_;
import com.yi.core.gift.domain.simple.GiftSimple;
import com.yi.core.gift.domain.vo.GiftListVo;
import com.yi.core.gift.domain.vo.GiftVo;
import com.yi.core.gift.service.IGiftService;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 礼物
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class GiftServiceImpl implements IGiftService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(GiftServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private GiftDao giftDao;

	@Resource
	private ISaleOrderService saleOrderService;

	private EntityListVoBoSimpleConvert<Gift, GiftBo, GiftVo, GiftSimple, GiftListVo> giftConvert;

	/**
	 * 分页查询Gift
	 **/
	@Override
	public Page<Gift> query(Pagination<Gift> query) {
		query.setEntityClazz(Gift.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Gift_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Gift_.createTime)));
		}));
		Page<Gift> page = giftDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: Gift
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GiftListVo> queryListVo(Pagination<Gift> query) {
		Page<Gift> pages = this.query(query);
		List<GiftListVo> vos = giftConvert.toListVos(pages.getContent());
		return new PageImpl<GiftListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: Gift
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GiftListVo> queryListVoForApp(Pagination<Gift> query) {
		query.setEntityClazz(Gift.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Gift_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Gift_.createTime)));
		}));
		Page<Gift> pages = giftDao.findAll(query, query.getPageRequest());
		List<GiftListVo> vos = giftConvert.toListVos(pages.getContent());
		return new PageImpl<GiftListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建Gift
	 **/
	@Override
	public Gift addGift(Gift gift) {
		return giftDao.save(gift);
	}

	/**
	 * 创建Gift
	 **/
	@Override
	public GiftVo addGift(GiftBo giftBo) {
		return giftConvert.toVo(giftDao.save(giftConvert.toEntity(giftBo)));
	}

	/**
	 * 更新Gift
	 **/
	@Override
	public Gift updateGift(Gift gift) {
		Gift dbGift = giftDao.getOne(gift.getId());
		AttributeReplication.copying(gift, dbGift, Gift_.member, Gift_.saleOrder, Gift_.consignee, Gift_.consigneePhone, Gift_.consigneeAddr, Gift_.state, Gift_.invalidTime,
				Gift_.receiveTime);
		return dbGift;
	}

	/**
	 * 更新Gift
	 **/
	@Override
	public GiftVo updateGift(GiftBo giftBo) {
		Gift dbGift = giftDao.getOne(giftBo.getId());
		AttributeReplication.copying(giftBo, dbGift, Gift_.member, Gift_.saleOrder, Gift_.consignee, Gift_.consigneePhone, Gift_.consigneeAddr, Gift_.state, Gift_.receiveTime);
		return giftConvert.toVo(dbGift);
	}

	/**
	 * 删除Gift
	 **/
	@Override
	public void removeGiftById(int id) {
		giftDao.deleteById(id);
	}

	/**
	 * 根据ID得到GiftBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GiftBo getGiftBoById(int id) {
		return giftConvert.toBo(this.giftDao.getOne(id));
	}

	/**
	 * 根据ID得到GiftVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GiftVo getGiftVoById(int id) {
		return giftConvert.toVo(this.giftDao.getOne(id));
	}

	/**
	 * 根据ID得到GiftListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GiftListVo getListVoById(int id) {
		return giftConvert.toListVo(this.giftDao.getOne(id));
	}

	protected void initConvert() {
		this.giftConvert = new EntityListVoBoSimpleConvert<Gift, GiftBo, GiftVo, GiftSimple, GiftListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Gift, GiftVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Gift, GiftVo>(beanConvertManager) {
					@Override
					protected void postConvert(GiftVo giftVo, Gift gift) {
					}
				};
			}

			@Override
			protected BeanConvert<Gift, GiftListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Gift, GiftListVo>(beanConvertManager) {
					@Override
					protected void postConvert(GiftListVo giftListVo, Gift gift) {
					}
				};
			}

			@Override
			protected BeanConvert<Gift, GiftBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Gift, GiftBo>(beanConvertManager) {
					@Override
					protected void postConvert(GiftBo giftBo, Gift gift) {
					}
				};
			}

			@Override
			protected BeanConvert<GiftBo, Gift> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBo, Gift>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Gift, GiftSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Gift, GiftSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GiftSimple, Gift> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftSimple, Gift>(beanConvertManager) {
					@Override
					public Gift convert(GiftSimple giftSimple) throws BeansException {
						return giftDao.getOne(giftSimple.getId());
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
	 * 批量生成礼物
	 */
	@Override
	public void batchCreateGift(GiftBag giftBag) {
		if (giftBag != null) {
			// 根据购买数量生成礼物
			Set<Gift> gifts = new HashSet<>();
			for (int i = 0; i < giftBag.getQuantity(); i++) {
				Gift tmp = new Gift();
				tmp.setGiftNo(NumberGenerateUtils.generateGiftNo());
				tmp.setGiftBag(giftBag);
				tmp.setCommodity(giftBag.getCommodity());
				tmp.setProduct(giftBag.getProduct());
				tmp.setPrice(giftBag.getPrice());
				tmp.setQuantity(1);
				tmp.setTotal(giftBag.getPrice());
				tmp.setBlessWord(giftBag.getBlessWord());
				tmp.setState(GiftEnum.RECEIVE_STATE_WAIT.getCode());
				// tmp.setInvalidTime(giftBag.getInvalidTime());//支付后生成失效时间
				tmp.setCreateTime(giftBag.getCreateTime());
				gifts.add(tmp);
			}
			giftDao.saveAll(gifts);
		}
	}

	/**
	 * 领取礼物
	 */
	@Override
	public void receiveGift(GiftBagBo giftBagBo, Gift gift, Member member) {
		if (giftBagBo != null && gift != null && member != null) {
			// 领取时间
			Date receiveTime = new Date();
			if (receiveTime.after(gift.getInvalidTime())) {
				throw new BusinessException("失效啦，下次早点来哦");
			}
			gift.setMember(member);
			gift.setConsignee(giftBagBo.getConsignee());
			gift.setConsigneePhone(giftBagBo.getConsigneePhone());
			gift.setConsigneeAddr(giftBagBo.getConsigneeAddr());
			gift.setState(GiftEnum.RECEIVE_STATE_ALREADY.getCode());
			gift.setReceiveTime(receiveTime);
			// 更新礼物领取数据
			Gift dbGift = this.updateGift(gift);
			// 生成 礼物订单
			SaleOrder dbSaleOrder = saleOrderService.addGiftOrderByGift(dbGift);
			dbGift.setSaleOrder(dbSaleOrder);
		}
	}

	/**
	 * 自动作废礼物
	 */
	@Override
	public void autoCancelGiftByTask() {
		// 查询待领取的礼物
		List<Gift> dbGifts = giftDao.findByStateAndDeleted(GiftEnum.RECEIVE_STATE_WAIT.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isNotEmpty(dbGifts)) {
			Date now = new Date();
			dbGifts.forEach(tmp -> {
				// 过期时间 < 当前时间
				if (now.after(Optional.ofNullable(tmp.getInvalidTime()).orElse(now))) {
					tmp.setState(GiftEnum.RECEIVE_STATE_EXPIRE.getCode());
				}
			});
		}
	}

	/**
	 * 支付时 批量更新礼物失效时间
	 */
	@Override
	public void bathcUpdateByAfterPay(GiftBag giftBag, Collection<Gift> gifts) {
		if (giftBag != null && CollectionUtils.isNotEmpty(gifts)) {
			gifts.forEach(gift -> {
				if (gift != null) {
					// 礼物的失效时间 = 礼包的失效时间
					gift.setInvalidTime(giftBag.getInvalidTime());
				}
			});
		}
	}

}
