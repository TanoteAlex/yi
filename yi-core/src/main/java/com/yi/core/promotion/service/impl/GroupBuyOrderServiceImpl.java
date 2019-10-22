/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.promotion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.common.Deleted;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.Member_;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.service.IAfterSaleOrderService;
import com.yi.core.promotion.PromotionEnum;
import com.yi.core.promotion.dao.GroupBuyOrderDao;
import com.yi.core.promotion.domain.bo.GroupBuyOrderBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivityProduct;
import com.yi.core.promotion.domain.entity.GroupBuyActivityTime;
import com.yi.core.promotion.domain.entity.GroupBuyOrder;
import com.yi.core.promotion.domain.entity.GroupBuyOrder_;
import com.yi.core.promotion.domain.listVo.GroupBuyOrderListVo;
import com.yi.core.promotion.domain.simple.GroupBuyOrderSimple;
import com.yi.core.promotion.domain.vo.GroupBuyOrderVo;
import com.yi.core.promotion.service.IGroupBuyOrderService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.date.DateUtils;

/**
 * 开团
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
// @CacheConfig(cacheNames = "groupBuyOrder")
@Service
@Transactional
public class GroupBuyOrderServiceImpl implements IGroupBuyOrderService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyOrderServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private GroupBuyOrderDao groupBuyOrderDao;

	@Resource
	private IAfterSaleOrderService afterSaleOrderService;

	private EntityListVoBoSimpleConvert<GroupBuyOrder, GroupBuyOrderBo, GroupBuyOrderVo, GroupBuyOrderSimple, GroupBuyOrderListVo> groupBuyOrderConvert;

	/**
	 * 分页查询GroupBuyOrder
	 **/
	@Override
	public Page<GroupBuyOrder> query(Pagination<GroupBuyOrder> query) {
		query.setEntityClazz(GroupBuyOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(GroupBuyOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(GroupBuyOrder_.createTime)));
		}));
		Page<GroupBuyOrder> page = groupBuyOrderDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: GroupBuyOrder
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GroupBuyOrderListVo> queryListVo(Pagination<GroupBuyOrder> query) {
		query.setEntityClazz(GroupBuyOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(GroupBuyOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(GroupBuyOrder_.createTime)));
		}));
		Page<GroupBuyOrder> pages = groupBuyOrderDao.findAll(query, query.getPageRequest());
		List<GroupBuyOrderListVo> vos = groupBuyOrderConvert.toListVos(pages.getContent());
		return new PageImpl<GroupBuyOrderListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: GroupBuyOrder
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GroupBuyOrderListVo> queryListVoForApp(Pagination<GroupBuyOrder> query) {
		query.setEntityClazz(GroupBuyOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(GroupBuyOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(GroupBuyOrder_.createTime)));
			Object memberId = query.getParams().get("member.id");
			if (memberId != null) {
				list.add(criteriaBuilder.equal(root.get(GroupBuyOrder_.member).get(Member_.id), memberId));
			} else {
				list.add(criteriaBuilder.equal(root.get(GroupBuyOrder_.member).get(Member_.id), 0));
			}
		}));
		Page<GroupBuyOrder> pages = groupBuyOrderDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.getGroupBuyActivityProduct().setCommodity(null);
			tmp.getGroupBuyActivityProduct().setProduct(null);
		});
		List<GroupBuyOrderListVo> vos = groupBuyOrderConvert.toListVos(pages.getContent());
		return new PageImpl<GroupBuyOrderListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建GroupBuyOrder
	 **/
	@Override
	public GroupBuyOrder addGroupBuyOrder(GroupBuyOrder groupBuyOrder) {
		return groupBuyOrderDao.save(groupBuyOrder);
	}

	/**
	 * 创建GroupBuyOrder
	 **/
	@Override
	public GroupBuyOrderListVo addGroupBuyOrder(GroupBuyOrderBo groupBuyOrderBo) {
		return groupBuyOrderConvert.toListVo(groupBuyOrderDao.save(groupBuyOrderConvert.toEntity(groupBuyOrderBo)));
	}

	/**
	 * 更新GroupBuyOrder
	 **/
	@Override
	public GroupBuyOrder updateGroupBuyOrder(GroupBuyOrder groupBuyOrder) {
		GroupBuyOrder dbGroupBuyOrder = groupBuyOrderDao.getOne(groupBuyOrder.getId());
		AttributeReplication.copying(groupBuyOrder, dbGroupBuyOrder, GroupBuyOrder_.guid, GroupBuyOrder_.member, GroupBuyOrder_.groupBuyActivityProduct, GroupBuyOrder_.saleOrder,
				GroupBuyOrder_.state, GroupBuyOrder_.startTime, GroupBuyOrder_.endTime, GroupBuyOrder_.remark, GroupBuyOrder_.createTime, GroupBuyOrder_.deleted,
				GroupBuyOrder_.delTime);
		return dbGroupBuyOrder;
	}

	/**
	 * 更新GroupBuyOrder
	 **/
	// @CacheEvict(key = "#groupBuyOrderBo.getId()")
	@Override
	public GroupBuyOrderListVo updateGroupBuyOrder(GroupBuyOrderBo groupBuyOrderBo) {
		GroupBuyOrder dbGroupBuyOrder = groupBuyOrderDao.getOne(groupBuyOrderBo.getId());
		AttributeReplication.copying(groupBuyOrderBo, dbGroupBuyOrder, GroupBuyOrder_.guid, GroupBuyOrder_.member, GroupBuyOrder_.groupBuyActivityProduct, GroupBuyOrder_.saleOrder,
				GroupBuyOrder_.state, GroupBuyOrder_.startTime, GroupBuyOrder_.endTime, GroupBuyOrder_.remark, GroupBuyOrder_.createTime, GroupBuyOrder_.deleted,
				GroupBuyOrder_.delTime);
		return groupBuyOrderConvert.toListVo(dbGroupBuyOrder);
	}

	/**
	 * 删除GroupBuyOrder
	 **/
	// @CacheEvict(key = "#groupBuyOrderBo.getId()")
	@Override
	public void removeGroupBuyOrderById(int id) {
		groupBuyOrderDao.deleteById(id);
	}

	/**
	 * 根据ID得到GroupBuyOrder
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyOrder getById(int id) {
		if (groupBuyOrderDao.existsById(id)) {
			return this.groupBuyOrderDao.getOne(id);
		}
		return null;
	}

	/**
	 * 根据ID得到GroupBuyOrderBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyOrderBo getBoById(int id) {
		return groupBuyOrderConvert.toBo(this.getById(id));
	}

	/**
	 * 根据ID得到GroupBuyOrderVo
	 **/
	// @Cacheable(key = "#id",unless = "#result eq null")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyOrderVo getVoById(int id) {
		GroupBuyOrder dbGroupBuyOrder = this.getById(id);
//		if (dbGroupBuyOrder != null && CollectionUtils.isNotEmpty(dbGroupBuyOrder.getJoinGroupBuys())) {
//			dbGroupBuyOrder.setJoinGroupBuys(dbGroupBuyOrder.getJoinGroupBuys().stream()
//					.filter(tmp -> PromotionEnum.GROUP_BUY_STATE_IN_GROUP.getCode().equals(tmp.getState()) || PromotionEnum.GROUP_BUY_STATE_GROUP.getCode().equals(tmp.getState()))
//					.collect(Collectors.toList()));
//		}
		return groupBuyOrderConvert.toVo(dbGroupBuyOrder);
	}

	/**
	 * 根据ID得到GroupBuyOrderListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyOrderListVo getListVoById(int id) {
		return groupBuyOrderConvert.toListVo(this.getById(id));
	}

	protected void initConvert() {
		this.groupBuyOrderConvert = new EntityListVoBoSimpleConvert<GroupBuyOrder, GroupBuyOrderBo, GroupBuyOrderVo, GroupBuyOrderSimple, GroupBuyOrderListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<GroupBuyOrder, GroupBuyOrderVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyOrder, GroupBuyOrderVo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyOrderVo groupBuyOrderVo, GroupBuyOrder groupBuyOrder) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyOrder, GroupBuyOrderListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyOrder, GroupBuyOrderListVo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyOrderListVo groupBuyOrderListVo, GroupBuyOrder groupBuyOrder) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyOrder, GroupBuyOrderBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyOrder, GroupBuyOrderBo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyOrderBo groupBuyOrderBo, GroupBuyOrder groupBuyOrder) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyOrderBo, GroupBuyOrder> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyOrderBo, GroupBuyOrder>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GroupBuyOrder, GroupBuyOrderSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyOrder, GroupBuyOrderSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GroupBuyOrderSimple, GroupBuyOrder> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyOrderSimple, GroupBuyOrder>(beanConvertManager) {
					@Override
					public GroupBuyOrder convert(GroupBuyOrderSimple groupBuyOrderSimple) throws BeansException {
						return groupBuyOrderDao.getOne(groupBuyOrderSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	// @Cacheable(key = "#groupBuyProductId",unless = "#result eq null")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<GroupBuyOrderListVo> getByGroupBuyProductId(Integer groupBuyProductId) {
		if (groupBuyProductId == null) {
			return new ArrayList<>(0);
		}
		Pageable pageable = PageRequest.of(0, 3);
		List<GroupBuyOrder> dbGroupBuyOrders = groupBuyOrderDao.findByGroupBuyActivityProduct_idAndStateAndGroupTypeAndDeletedOrderByEndTimeAsc(groupBuyProductId,
				PromotionEnum.GROUP_BUY_STATE_IN_GROUP.getCode(), PromotionEnum.GROUP_TYPE_OPEN.getCode(), Deleted.DEL_FALSE, pageable);
		dbGroupBuyOrders.forEach(tmp -> {
			if (CollectionUtils.isNotEmpty(tmp.getJoinGroupBuys())) {
				tmp.setJoinGroupBuys(tmp.getJoinGroupBuys().stream().filter(tmp2 -> !OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(tmp2.getSaleOrder().getOrderState()))
						.collect(Collectors.toList()));
			}
		});
		return groupBuyOrderConvert.toListVos(dbGroupBuyOrders);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<GroupBuyOrderListVo> getAllByGroupBuyProductId(Integer groupBuyProductId) {
		if (groupBuyProductId == null) {
			return null;
		}
		return groupBuyOrderConvert.toListVos(groupBuyOrderDao.findByGroupBuyActivityProduct_idAndStateAndDeletedOrderByEndTimeAsc(groupBuyProductId,
				PromotionEnum.GROUP_BUY_STATE_IN_GROUP.getCode(), Deleted.DEL_FALSE));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyOrderVo getVoByMemberAndSaleOrder(Integer memberId, Integer saleOrderId) {
		if (memberId != null && saleOrderId != null) {
			GroupBuyOrder dbGroupBuyOrder = groupBuyOrderDao.findByMember_idAndSaleOrder_idAndDeleted(memberId, saleOrderId, Deleted.DEL_FALSE);
			// 开团单
			if (OrderEnum.GROUP_ORDER_TYPE_OPEN.getCode().equals(dbGroupBuyOrder.getGroupType())) {
				// dbGroupBuyOrder.setMember(null);
				// dbGroupBuyOrder.setGroupBuyActivityProduct(null);
				dbGroupBuyOrder.setSaleOrder(null);
				dbGroupBuyOrder.getJoinGroupBuys().forEach(tmp -> {
					// tmp.setMember(null);
					tmp.setGroupBuyActivityProduct(null);
					tmp.setSaleOrder(null);
				});
				dbGroupBuyOrder.setJoinGroupBuys(dbGroupBuyOrder.getJoinGroupBuys().stream().filter(
						tmp -> PromotionEnum.GROUP_BUY_STATE_IN_GROUP.getCode().equals(tmp.getState()) || PromotionEnum.GROUP_BUY_STATE_GROUP.getCode().equals(tmp.getState()))
						.collect(Collectors.toList()));
				return groupBuyOrderConvert.toVo(dbGroupBuyOrder);
				// 参团单
			} else if (OrderEnum.GROUP_ORDER_TYPE_JOIN.getCode().equals(dbGroupBuyOrder.getGroupType())) {
				GroupBuyOrder openGroupBuy = dbGroupBuyOrder.getOpenGroupBuy();
				// openGroupBuy.setMember(null);
				// openGroupBuy.setGroupBuyActivityProduct(null);
				openGroupBuy.setSaleOrder(null);
				openGroupBuy.getJoinGroupBuys().forEach(tmp -> {
					// tmp.setMember(null);
					tmp.setGroupBuyActivityProduct(null);
					tmp.setSaleOrder(null);
				});
				dbGroupBuyOrder.setJoinGroupBuys(dbGroupBuyOrder.getJoinGroupBuys().stream().filter(
						tmp -> PromotionEnum.GROUP_BUY_STATE_IN_GROUP.getCode().equals(tmp.getState()) || PromotionEnum.GROUP_BUY_STATE_GROUP.getCode().equals(tmp.getState()))
						.collect(Collectors.toList()));
				return groupBuyOrderConvert.toVo(openGroupBuy);
			}
		}
		return null;
	}

	/**
	 * 下单时 新增开团单
	 */
	@Override
	public GroupBuyOrder addOpenGroupByOrder(Member member, GroupBuyActivityProduct groupBuyProduct, SaleOrder saleOrder) {
		if (member != null && groupBuyProduct != null && saleOrder != null && OrderEnum.GROUP_ORDER_TYPE_OPEN.getCode().equals(saleOrder.getGroupOrderType())) {
			GroupBuyOrder groupBuyOrder = new GroupBuyOrder();
			groupBuyOrder.setMember(member);
			groupBuyOrder.setGroupBuyActivityProduct(groupBuyProduct);
			groupBuyOrder.setSaleOrder(saleOrder);
			groupBuyOrder.setState(PromotionEnum.GROUP_BUY_STATE_WAIT_PAY.getCode());
			groupBuyOrder.setGroupType(OrderEnum.GROUP_ORDER_TYPE_OPEN.getCode());
			groupBuyOrder.setGroupNum(groupBuyProduct.getGroupBuyQuantity());
			groupBuyOrder.setJoinNum(0);
			groupBuyOrder.setStartTime(new Date());
			groupBuyOrder.setRemark("开团");
			for (GroupBuyActivityTime tmpTime : groupBuyProduct.getGroupBuyActivity().getGroupBuyActivityTimes()) {
				if (tmpTime.getStartTime().before(new Date()) && tmpTime.getEndTime().after(new Date())) {
					groupBuyOrder.setEndTime(DateUtils.parseTimestamp(DateUtils.getFormatDate(tmpTime.getEndTime()) + " 23:59:59"));
					break;
				}
			}
			return this.groupBuyOrderDao.save(groupBuyOrder);
		}
		return null;
	}

	/**
	 * 下单时 新增参团单
	 */
	@Override
	public GroupBuyOrder addJoinGroupByOrder(Member member, GroupBuyActivityProduct groupBuyProduct, SaleOrder saleOrder, GroupBuyOrder groupBuyOrder) {
		if (member != null && groupBuyProduct != null && saleOrder != null && OrderEnum.GROUP_ORDER_TYPE_JOIN.getCode().equals(saleOrder.getGroupOrderType())
				&& groupBuyOrder != null) {
			GroupBuyOrder joinGroupBuy = new GroupBuyOrder();
			joinGroupBuy.setMember(member);
			joinGroupBuy.setOpenGroupBuy(groupBuyOrder);
			joinGroupBuy.setGroupBuyActivityProduct(groupBuyProduct);
			joinGroupBuy.setSaleOrder(saleOrder);
			joinGroupBuy.setState(PromotionEnum.GROUP_BUY_STATE_WAIT_PAY.getCode());
			joinGroupBuy.setGroupType(OrderEnum.GROUP_ORDER_TYPE_JOIN.getCode());
			joinGroupBuy.setStartTime(groupBuyOrder.getStartTime());
			joinGroupBuy.setEndTime(groupBuyOrder.getEndTime());
			joinGroupBuy.setRemark("参团");
			return this.groupBuyOrderDao.save(joinGroupBuy);
		}
		return null;
	}

	/**
	 * 支付后 开团单修改团购状态
	 */
	@Override
	public GroupBuyOrder updateOpenGroupByAfterPay(SaleOrder saleOrder) {
		if (saleOrder != null && saleOrder.getId() > 0) {
			GroupBuyOrder dbOpenGroupBuy = groupBuyOrderDao.findByMember_idAndSaleOrder_idAndDeleted(saleOrder.getMember().getId(), saleOrder.getId(), Deleted.DEL_FALSE);
			if (dbOpenGroupBuy != null && PromotionEnum.GROUP_BUY_STATE_WAIT_PAY.getCode().equals(dbOpenGroupBuy.getState())) {
				dbOpenGroupBuy.setState(PromotionEnum.GROUP_BUY_STATE_IN_GROUP.getCode());
				dbOpenGroupBuy.setJoinNum(1);
				dbOpenGroupBuy.getSaleOrder().setGroupState(PromotionEnum.GROUP_BUY_STATE_IN_GROUP.getCode());
			}
		}
		return null;
	}

	/**
	 * 支付后 参团单修改团购状态
	 */
	@Override
	public GroupBuyOrder updateJoinGroupByAfterPay(SaleOrder saleOrder) {
		if (saleOrder != null && saleOrder.getId() > 0) {
			GroupBuyOrder dbJoinGroupBuy = groupBuyOrderDao.findByMember_idAndSaleOrder_idAndDeleted(saleOrder.getMember().getId(), saleOrder.getId(), Deleted.DEL_FALSE);
			if (dbJoinGroupBuy != null && PromotionEnum.GROUP_BUY_STATE_WAIT_PAY.getCode().equals(dbJoinGroupBuy.getState())) {
				// 修改参团状态和参团订单状态
				dbJoinGroupBuy.setState(PromotionEnum.GROUP_BUY_STATE_IN_GROUP.getCode());
				dbJoinGroupBuy.getSaleOrder().setGroupState(PromotionEnum.GROUP_BUY_STATE_IN_GROUP.getCode());
				// 修改开团人数、开团状态和开团订单状态
				GroupBuyOrder openGroupBuy = this.updateJoinNumByAfterPay(dbJoinGroupBuy.getOpenGroupBuy(), 1);
				if (openGroupBuy == null) {
					dbJoinGroupBuy.setState(PromotionEnum.GROUP_BUY_STATE_INVALID.getCode());
					dbJoinGroupBuy.getSaleOrder().setGroupState(PromotionEnum.GROUP_BUY_STATE_INVALID.getCode());
					// 为失效参团单退款
					afterSaleOrderService.refundByInvalidJoinGroupBuy(dbJoinGroupBuy.getSaleOrder());
				} else {
					dbJoinGroupBuy.setState(openGroupBuy.getState());
					dbJoinGroupBuy.getSaleOrder().setGroupState(openGroupBuy.getSaleOrder().getGroupState());
				}
			}
		}
		return null;
	}

	/**
	 * 退款成功后 修改开团单状态
	 */
	@Override
	public GroupBuyOrder updateByAfterRefund(SaleOrder saleOrder) {
		if (saleOrder != null && saleOrder.getId() > 0) {
			GroupBuyOrder dbOpenGroupBuy = groupBuyOrderDao.findByMember_idAndSaleOrder_idAndDeleted(saleOrder.getMember().getId(), saleOrder.getId(), Deleted.DEL_FALSE);
			if (dbOpenGroupBuy != null && PromotionEnum.GROUP_BUY_STATE_INVALID.getCode().equals(dbOpenGroupBuy.getState())) {
				dbOpenGroupBuy.setState(PromotionEnum.GROUP_BUY_STATE_REFUND.getCode());
			}
		}
		return null;
	}

	/**
	 * 关闭订单时 修改开团单状态
	 */
	@Override
	public GroupBuyOrder updateByCloseOrder(SaleOrder saleOrder) {
		if (saleOrder != null && saleOrder.getId() > 0) {
			GroupBuyOrder dbGroupBuyOrder = groupBuyOrderDao.findByMember_idAndSaleOrder_idAndDeleted(saleOrder.getMember().getId(), saleOrder.getId(), Deleted.DEL_FALSE);
			if (dbGroupBuyOrder != null && PromotionEnum.GROUP_BUY_STATE_WAIT_PAY.getCode().equals(dbGroupBuyOrder.getState())) {
				dbGroupBuyOrder.setState(PromotionEnum.GROUP_BUY_STATE_INVALID.getCode());
			}
		}
		return null;
	}

	/**
	 * 参团成功后 修改开团单数据
	 */
	@Override
	public GroupBuyOrder updateJoinNumByAfterPay(GroupBuyOrder groupBuyOrder, Integer joinNum) {
		if (groupBuyOrder != null && joinNum != null) {
			GroupBuyOrder dbOpenGroupBuy = this.getById(groupBuyOrder.getId());
			if (dbOpenGroupBuy != null && dbOpenGroupBuy.getJoinNum() + joinNum <= dbOpenGroupBuy.getGroupNum()) {
				dbOpenGroupBuy.setJoinNum(dbOpenGroupBuy.getJoinNum() + joinNum);
				// 参团人数==成团人数
				if (dbOpenGroupBuy.getJoinNum() == dbOpenGroupBuy.getGroupNum()) {
					dbOpenGroupBuy.setState(PromotionEnum.GROUP_BUY_STATE_GROUP.getCode());
					dbOpenGroupBuy.getSaleOrder().setGroupState(PromotionEnum.GROUP_BUY_STATE_GROUP.getCode());
				}
				return dbOpenGroupBuy;
			}
		}
		// TODO 参团失败 生成退款单
		return null;
	}

	/**
	 * 未成团 自动失效并退款
	 */
	@Override
	@Transactional
	public void autoCancelGroupBuyOrderByTask() {
		List<GroupBuyOrder> dbGroupBuyOrders = groupBuyOrderDao.findByStateAndDeleted(PromotionEnum.GROUP_BUY_STATE_IN_GROUP.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(dbGroupBuyOrders)) {
			LOG.info("没有未成团的开团单");
		}
		dbGroupBuyOrders.forEach(openGroup -> {
			// 未成团 已过时的
			if (openGroup != null && openGroup.getEndTime().before(new Date())) {
				openGroup.setState(PromotionEnum.GROUP_BUY_STATE_INVALID.getCode());
				openGroup.getSaleOrder().setGroupState(PromotionEnum.GROUP_BUY_STATE_INVALID.getCode());
				// 为失效开团单退款
				afterSaleOrderService.refundByInvalidOpenGroupBuy(openGroup.getSaleOrder());
				// 失效参团单
				openGroup.getJoinGroupBuys().forEach(joinGroup -> {
					joinGroup.setState(PromotionEnum.GROUP_BUY_STATE_INVALID.getCode());
					joinGroup.getSaleOrder().setGroupState(PromotionEnum.GROUP_BUY_STATE_INVALID.getCode());
					// 为失效参团单退款
					afterSaleOrderService.refundByInvalidJoinGroupBuy(joinGroup.getSaleOrder());
				});
			}
		});
	}

}
