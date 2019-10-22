/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.service.IProductService;
import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.gift.GiftEnum;
import com.yi.core.gift.dao.GiftBagDao;
import com.yi.core.gift.domain.bo.GiftBagBo;
import com.yi.core.gift.domain.entity.Gift;
import com.yi.core.gift.domain.entity.GiftBag;
import com.yi.core.gift.domain.entity.GiftBag_;
import com.yi.core.gift.domain.simple.GiftBagSimple;
import com.yi.core.gift.domain.vo.GiftBagListVo;
import com.yi.core.gift.domain.vo.GiftBagVo;
import com.yi.core.gift.service.IGiftBagService;
import com.yi.core.gift.service.IGiftService;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.service.IAfterSaleOrderService;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.payment.weChat.WeChatPayService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 礼包
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
//@CacheConfig(cacheNames = "giftBag")
@Service
@Transactional
public class GiftBagServiceImpl implements IGiftBagService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(GiftBagServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private GiftBagDao giftBagDao;

	@Resource
	private IGiftService giftService;

	@Resource
	private IMemberService memberService;

	@Resource
	private IProductService productService;

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private WeChatPayService weChatPayService;

	@Resource
	private IAfterSaleOrderService afterSaleOrderService;

	private EntityListVoBoSimpleConvert<GiftBag, GiftBagBo, GiftBagVo, GiftBagSimple, GiftBagListVo> giftBagConvert;

	/**
	 * 分页查询GiftBag
	 **/
	@Override
	public Page<GiftBag> query(Pagination<GiftBag> query) {
		query.setEntityClazz(GiftBag.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(GiftBag_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(GiftBag_.createTime)));
		}));
		Page<GiftBag> page = giftBagDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: GiftBag
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GiftBagListVo> queryListVo(Pagination<GiftBag> query) {
		Page<GiftBag> pages = this.query(query);
		List<GiftBagListVo> vos = giftBagConvert.toListVos(pages.getContent());
		return new PageImpl<GiftBagListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: GiftBag
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GiftBagListVo> queryListVoForApp(Pagination<GiftBag> query) {
		query.setEntityClazz(GiftBag.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// 礼包状态
			Path<Integer> path = root.get(GiftBag_.state);
			CriteriaBuilder.In<Integer> in = criteriaBuilder.in(path);
			in.value(GiftEnum.STATE_VALID.getCode());
			in.value(GiftEnum.STATE_INVALID.getCode());
			list.add(criteriaBuilder.and(in));

			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(GiftBag_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(GiftBag_.createTime)));
		}));
		Page<GiftBag> pages = giftBagDao.findAll(query, query.getPageRequest());
		List<GiftBagListVo> vos = giftBagConvert.toListVos(pages.getContent());
		return new PageImpl<GiftBagListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建GiftBag
	 **/
	@Override
	public GiftBag addGiftBag(GiftBag giftBag) {
		if (giftBag == null || giftBag.getMember() == null || giftBag.getProduct() == null || giftBag.getQuantity() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		// 检查会员
		Member dbMember = memberService.getMemberById(giftBag.getMember().getId());
		if (dbMember == null) {
			throw new BusinessException("提交数据不能为空");
		}
		// 检查货品
		Product dbProduct = productService.getById(giftBag.getProduct().getId());
		if (dbProduct == null) {
			throw new BusinessException("提交数据不能为空");
		}
		giftBag.setMember(dbMember);
		giftBag.setGiftBagNo(NumberGenerateUtils.generateGiftBagNo());
		giftBag.setCommodity(dbProduct.getCommodity());
		giftBag.setProduct(dbProduct);
		giftBag.setPrice(dbProduct.getCurrentPrice());
		giftBag.setTotal(dbProduct.getCurrentPrice().multiply(BigDecimal.valueOf(giftBag.getQuantity())).setScale(2, BigDecimal.ROUND_UP));
		giftBag.setGiftNum(giftBag.getQuantity());
		giftBag.setReceiveGiftNum(0);
		if (giftBag.getMysteryGift() == null) {
			giftBag.setMysteryGift(GiftEnum.MYSTERY_GIFT_NO.getCode());
		}
		giftBag.setState(GiftEnum.STATE_UNPAID.getCode());// 未支付
		giftBag.setCreateTime(new Date());
		// giftBag.setInvalidTime(DateUtils.addDays(giftBag.getCreateTime(), 3));
		GiftBag dbGiftBag = giftBagDao.save(giftBag);
		// 生成礼物
		giftService.batchCreateGift(dbGiftBag);
		return dbGiftBag;
	}

	/**
	 * 创建GiftBag
	 **/
	@Override
	public GiftBagVo addGiftBag(GiftBagBo giftBagBo) {
		return giftBagConvert.toVo(this.addGiftBag(giftBagConvert.toEntity(giftBagBo)));
	}

	/**
	 * 更新GiftBag
	 **/
	@Override
	public GiftBag updateGiftBag(GiftBag giftBag) {
		GiftBag dbGiftBag = giftBagDao.getOne(giftBag.getId());
		AttributeReplication.copying(giftBag, dbGiftBag, GiftBag_.saleOrder, GiftBag_.giftBagName, GiftBag_.giftNum, GiftBag_.receiveGiftNum, GiftBag_.mysteryGift,
				GiftBag_.blessWord, GiftBag_.state, GiftBag_.finishTime, GiftBag_.invalidTime);
		return dbGiftBag;
	}

	/**
	 * 更新GiftBag
	 **/
	@Override
	public GiftBagVo updateGiftBag(GiftBagBo giftBagBo) {
		GiftBag dbGiftBag = giftBagDao.getOne(giftBagBo.getId());
		AttributeReplication.copying(giftBagBo, dbGiftBag, GiftBag_.member, GiftBag_.saleOrder, GiftBag_.giftBagNo, GiftBag_.giftBagName, GiftBag_.commodity, GiftBag_.product,
				GiftBag_.price, GiftBag_.quantity, GiftBag_.total, GiftBag_.giftNum, GiftBag_.receiveGiftNum, GiftBag_.mysteryGift, GiftBag_.blessWord, GiftBag_.state,
				GiftBag_.finishTime, GiftBag_.invalidTime);
		return giftBagConvert.toVo(dbGiftBag);
	}

	/**
	 * 删除GiftBag
	 **/
	@Override
	public void removeGiftBagById(int id) {
		giftBagDao.deleteById(id);
	}

	/**
	 * 根据ID得到GiftBag
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GiftBag getById(int id) {
		return this.giftBagDao.getOne(id);
	}

	/**
	 * 根据ID得到GiftBagBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GiftBagBo getBoById(int id) {
		return giftBagConvert.toBo(this.giftBagDao.getOne(id));
	}

	/**
	 * 根据ID得到GiftBagVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GiftBagVo getVoById(int id) {
		return giftBagConvert.toVo(this.giftBagDao.getOne(id));
	}

	/**
	 * 根据ID得到GiftBagListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GiftBagListVo getListVoById(int id) {
		return giftBagConvert.toListVo(this.giftBagDao.getOne(id));
	}

	protected void initConvert() {
		this.giftBagConvert = new EntityListVoBoSimpleConvert<GiftBag, GiftBagBo, GiftBagVo, GiftBagSimple, GiftBagListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<GiftBag, GiftBagVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBag, GiftBagVo>(beanConvertManager) {
					@Override
					protected void postConvert(GiftBagVo giftBagVo, GiftBag giftBag) {
					}
				};
			}

			@Override
			protected BeanConvert<GiftBag, GiftBagListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBag, GiftBagListVo>(beanConvertManager) {
					@Override
					protected void postConvert(GiftBagListVo giftBagListVo, GiftBag giftBag) {
					}
				};
			}

			@Override
			protected BeanConvert<GiftBag, GiftBagBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBag, GiftBagBo>(beanConvertManager) {
					@Override
					protected void postConvert(GiftBagBo giftBagBo, GiftBag giftBag) {
					}
				};
			}

			@Override
			protected BeanConvert<GiftBagBo, GiftBag> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBagBo, GiftBag>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GiftBag, GiftBagSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBag, GiftBagSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GiftBagSimple, GiftBag> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBagSimple, GiftBag>(beanConvertManager) {
					@Override
					public GiftBag convert(GiftBagSimple giftBagSimple) throws BeansException {
						return giftBagDao.getOne(giftBagSimple.getId());
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
	 * 1生成礼包</br>
	 * 2生成礼包订单</br>
	 * 3生成预支付订单
	 * 
	 * @param giftBagBo
	 * @return
	 */
	@Override
	public SortedMap<String, String> createGiftBagForSp(GiftBagBo giftBagBo) throws Exception {
		// 生成礼包
		GiftBag dbGiftBag = this.addGiftBag(giftBagConvert.toEntity(giftBagBo));
		// 创建礼包订单
		SaleOrder dbSaleOrder = saleOrderService.addGiftOrderByGiftBag(dbGiftBag);
		dbGiftBag.setSaleOrder(dbSaleOrder);
		// 创建预支付订单
		SortedMap<String, String> resultMap = weChatPayService.createGiftBagPayOrderForSp(dbSaleOrder);
		resultMap.put("giftBagId", String.valueOf(dbGiftBag.getId()));
		return resultMap;
	}

	/**
	 * 1生成礼包</br>
	 * 2生成礼包订单</br>
	 * 3生成预支付订单
	 * 
	 * @param giftBagBo
	 * @return
	 */
	@Override
	public SortedMap<String, String> createGiftBagForMp(GiftBagBo giftBagBo) throws Exception {
		// 生成礼包
		GiftBag dbGiftBag = this.addGiftBag(giftBagConvert.toEntity(giftBagBo));
		// 创建礼包订单
		SaleOrder dbSaleOrder = saleOrderService.addGiftOrderByGiftBag(dbGiftBag);
		dbGiftBag.setSaleOrder(dbSaleOrder);
		// 创建预支付订单
		SortedMap<String, String> resultMap = weChatPayService.createGiftBagPayOrderForMp(dbSaleOrder);
		resultMap.put("giftBagId", String.valueOf(dbGiftBag.getId()));
		return resultMap;
	}

	/**
	 * 获取礼包详情
	 */
//	@Cacheable(key = "#giftBagId",unless = "#result eq null")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GiftBagVo getGiftBagDetail(Integer giftBagId) {
		if (giftBagId != null) {
			GiftBag dbGiftBag = this.getById(giftBagId);
			if (dbGiftBag != null) {
				dbGiftBag.setGifts(dbGiftBag.getGifts().stream().filter(e -> GiftEnum.RECEIVE_STATE_ALREADY.getCode().equals(e.getState())).collect(Collectors.toSet()));
				return giftBagConvert.toVo(dbGiftBag);
			}
		}
		return null;
	}

	/**
	 * 领取礼物
	 */
//	@Cacheable(key = "#giftBagBo.getId()",unless = "#result eq null")
	@Override
	public GiftBagListVo receiveGift(GiftBagBo giftBagBo) {
		if (giftBagBo == null || giftBagBo.getId() < 1 || giftBagBo.getMember() == null || giftBagBo.getMember().getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		// 检查礼包
		GiftBag dbGiftBag = this.getById(giftBagBo.getId());
		if (dbGiftBag == null) {
			throw new BusinessException("请选择领取的礼包");
		} // 非有效状态-未支付、失效
		if (!GiftEnum.STATE_VALID.getCode().equals(dbGiftBag.getState())) {
			throw new BusinessException("哎呀，这是一个无效礼包");
		}
		if (dbGiftBag.getReceiveGiftNum() + 1 > dbGiftBag.getGiftNum()) {
			throw new BusinessException("哎呀，已经被领完啦");
		}
		// 检查会员
		Member dbMember = memberService.getMemberById(giftBagBo.getMember().getId());
		if (dbMember == null) {
			throw new BusinessException("请注册会员");
		}
		// 获取未领取的礼包集合
		List<Gift> noReceiveGifts = dbGiftBag.getGifts().stream().filter(e -> GiftEnum.RECEIVE_STATE_WAIT.getCode().equals(e.getState())).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(noReceiveGifts)) {
			throw new BusinessException("哎呀，已经被领完啦");
		}
		// 获取一个礼物 供当前会员领取
		Gift gift = noReceiveGifts.get(0);
		giftService.receiveGift(giftBagBo, gift, dbMember);
		// 更新 礼包领取数量
		dbGiftBag.setReceiveGiftNum(dbGiftBag.getReceiveGiftNum() + 1);
		// 领取数==礼物数
		if (dbGiftBag.getReceiveGiftNum() == dbGiftBag.getGiftNum()) {
			dbGiftBag.setFinishTime(new Date());
		}
		return giftBagConvert.toListVo(dbGiftBag);
	}

	@Override
	public void autoCancelGiftBagByTask() {
		// 查询未作废的礼包
		List<GiftBag> dbGiftBags = giftBagDao.findByStateAndDeleted(GiftEnum.STATE_VALID.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isNotEmpty(dbGiftBags)) {
			Date now = new Date();
			dbGiftBags.forEach(tmp -> {
				// 过期时间 < 当前时间
				if (now.after(tmp.getInvalidTime())) {
					tmp.setState(GiftEnum.STATE_INVALID.getCode());
					if (tmp.getInvalidTime() == null) {
						tmp.setFinishTime(tmp.getInvalidTime());
					}
					// 生成退款单
					afterSaleOrderService.addByInvalidGiftBag(tmp);
				}
			});
		}
	}

	/**
	 * 支付时更新礼包失效时间
	 */
	@Override
	public void updateByAfterPay(SaleOrder saleOrder) {
		if (saleOrder != null && saleOrder.getId() > 0) {
			List<GiftBag> dbGiftBags = giftBagDao.findBySaleOrder_idAndDeleted(saleOrder.getId(), Deleted.DEL_FALSE);
			dbGiftBags.forEach(giftBag -> {
				if (giftBag != null && GiftEnum.STATE_UNPAID.getCode().equals(giftBag.getState())) {
					// TODO 更改礼包失效时间 暂定支付成功3天后失效
					giftBag.setState(GiftEnum.STATE_VALID.getCode());
					giftBag.setInvalidTime(DateUtils.addDays(new Date(), 3));
					// 批量更新礼物失效时间
					giftService.bathcUpdateByAfterPay(giftBag, giftBag.getGifts());

				}
			});
		}
	}

}
