/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.yi.core.activity.domain.bo.CouponReceiveBo;
import com.yi.core.activity.domain.entity.Coupon;
import com.yi.core.activity.domain.simple.CouponGrantRecordSimple;
import com.yi.core.activity.domain.simple.CouponReceiveSimple;
import com.yi.core.activity.domain.simple.CouponSimple;
import com.yi.core.activity.domain.vo.CouponReceiveListVo;
import com.yi.core.activity.domain.vo.CouponReceiveVo;
import com.yi.core.basic.dao.IntegralCashDao;
import com.yi.core.basic.domain.entity.IntegralCash;
import com.yi.core.basic.domain.simple.IntegralCashSimple;
import com.yi.core.commodity.domain.entity.Attribute;
import com.yi.core.commodity.domain.simple.AttributeSimple;
import com.yi.core.commodity.domain.simple.CategorySimple;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yi.core.order.dao.SaleOrderItemDao;
import com.yi.core.order.domain.bo.SaleOrderItemBo;
import com.yi.core.order.domain.simple.SaleOrderItemSimple;
import com.yi.core.order.domain.vo.SaleOrderItemListVo;
import com.yi.core.order.domain.vo.SaleOrderItemVo;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.CouponDao;
import com.yi.core.activity.dao.CouponReceiveDao;
import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.basic.domain.entity.Community_;
import com.yi.core.basic.service.IIntegralCashService;
import com.yi.core.cart.domain.vo.CartVo;
import com.yi.core.cart.service.ICartService;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.domain.entity.Product_;
import com.yi.core.commodity.service.ICommentService;
import com.yi.core.commodity.service.IProductService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.finance.domain.vo.SupplierSaleStatsVo;
import com.yi.core.gift.domain.entity.Gift;
import com.yi.core.gift.domain.entity.GiftBag;
import com.yi.core.gift.service.IGiftBagService;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.dao.ConsumeRecordDao;
import com.yi.core.member.domain.bo.ShippingAddressBo;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.Member_;
import com.yi.core.member.domain.entity.ShippingAddress;
import com.yi.core.member.domain.vo.ShippingAddressVo;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberLevelService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.member.service.IShippingAddressService;
import com.yi.core.member.service.ISignTimeService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.dao.SaleOrderDao;
import com.yi.core.order.domain.bo.SaleOrderBo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.domain.entity.SaleOrderItem_;
import com.yi.core.order.domain.entity.SaleOrder_;
import com.yi.core.order.domain.simple.SaleOrderSimple;
import com.yi.core.order.domain.vo.SaleOrderListVo;
import com.yi.core.order.domain.vo.SaleOrderVo;
import com.yi.core.order.service.IFreightTemplateConfigService;
import com.yi.core.order.service.IOrderLogService;
import com.yi.core.order.service.IOrderSettingService;
import com.yi.core.order.service.IPayRecordService;
import com.yi.core.order.service.ISaleOrderItemService;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.payment.alipay.AlipayService;
import com.yi.core.payment.weChat.WeChatNotifyService;
import com.yi.core.promotion.PromotionEnum;
import com.yi.core.promotion.domain.entity.GroupBuyActivityProduct;
import com.yi.core.promotion.domain.entity.GroupBuyOrder;
import com.yi.core.promotion.domain.vo.GroupBuyOrderVo;
import com.yi.core.promotion.service.IGroupBuyActivityProductService;
import com.yi.core.promotion.service.IGroupBuyOrderService;
import com.yi.core.supplier.domain.entity.Supplier_;
import com.yi.core.supplier.service.ISupplierAccountService;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.FilterDescriptor;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.ValueUtils;
import com.yihz.common.utils.date.DateUtils;

/**
 * 销售订单
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SaleOrderServiceImpl implements ISaleOrderService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SaleOrderServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager; // 注入EntityManager

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SaleOrderDao saleOrderDao;

	@Resource
	private SaleOrderItemDao saleOrderItemDao;

	@Resource
	private CouponDao couponDao;

	@Resource
	private ConsumeRecordDao consumeRecordDao;

	@Resource
	private CouponReceiveDao couponReceiveDao;

	@Resource
	private ICartService cartService;

	@Resource
	private IStockService stockService;

	@Resource
	private IProductService productService;

	@Resource
	private IFreightTemplateConfigService freightTemplateService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IShippingAddressService shippingAddressService;

	@Resource
	private IMemberService memberService;

	@Resource
	private IMemberLevelService memberLevelService;

	@Resource
	private ISupplierService supplierService;

	@Resource
	private BaseOrderService baseOrderService;

	@Resource
	private IOrderLogService orderLogService;

	@Resource
	private ISupplierAccountService supplierAccountService;

	@Resource
	private IOrderSettingService orderSettingService;

	@Resource
	private ICommentService commentService;

	@Resource
	private ISaleOrderItemService saleOrderItemService;

	@Resource
	private IGroupBuyActivityProductService groupBuyActivityProductService;

	@Resource
	private IGroupBuyOrderService groupBuyOrderService;

	@Resource
	private WeChatNotifyService weChatNotifyService;

	@Resource
	private AlipayService alipayService;

	@Resource
	private IGiftBagService giftBagService;

	@Resource
	private IAccountRecordService accountRecordService;

	@Resource
	private IPayRecordService payRecordService;

	@Resource
	private IAccountService accountService;

	@Resource
	private ISignTimeService signTimeService;

	@Resource
	private IIntegralCashService integralCashService;

	@Resource
	private IntegralCashDao integralCashDao;
	private EntityListVoBoSimpleConvert<CouponReceive, CouponReceiveBo, CouponReceiveVo,CouponReceiveSimple, CouponReceiveListVo> couponReceiveConvert;
	private EntityListVoBoSimpleConvert<SaleOrder, SaleOrderBo, SaleOrderVo, SaleOrderSimple, SaleOrderListVo> orderConvert;
	private EntityListVoBoSimpleConvert<SaleOrderItem, SaleOrderItemBo, SaleOrderItemVo, SaleOrderItemSimple, SaleOrderItemListVo> saleOrderItemConvert;
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SaleOrder> query(Pagination<SaleOrder> query) {
		query.setEntityClazz(SaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SaleOrder_.orderTime)));
			Object memberId = query.getParams().get("member.id");
			if (memberId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.member).get(Member_.id), memberId));
			}
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.supplier).get(Supplier_.id), supplierId));
			}
		}));
		Page<SaleOrder> page = saleOrderDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SaleOrderListVo> queryListVo(Pagination<SaleOrder> query) {
		if (query.getFilter() != null && CollectionUtils.isNotEmpty(query.getFilter().getFilters())) {
			for (FilterDescriptor filter : query.getFilter().getFilters()) {
				if ("orderType".equals(filter.getField()) && OrderEnum.ORDER_TYPE_GROUP.getCode().equals(filter.getValue())) {
					query.getParams().put("groupOrder", Boolean.TRUE);
				}
			}
		}
		query.setEntityClazz(SaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SaleOrder_.orderTime)));
			Object memberId = query.getParams().get("member.id");
			if (memberId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.member).get(Member_.id), memberId));
			}
			Object commodityName = query.getParams().get("commodityName");
			if (commodityName != null) {
				ListJoin<SaleOrder, SaleOrderItem> orderItemJoin = root.join(root.getModel().getList("saleOrderItems", SaleOrderItem.class), JoinType.LEFT);
				list.add(criteriaBuilder
						.and(criteriaBuilder.like(orderItemJoin.get(SaleOrderItem_.product).get(Product_.productShortName), "%" + commodityName.toString().trim() + "%")));
			}
			Object groupOrder = query.getParams().get("groupOrder");
			if (groupOrder != null && Boolean.valueOf(groupOrder.toString())) {
				// 团购单 必须成团
				list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.groupState), PromotionEnum.GROUP_BUY_STATE_GROUP.getCode())));
			}
		}));

		Page<SaleOrder> pages = saleOrderDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.setCoupons(null);
			tmp.setVouchers(null);
			tmp.getSaleOrderItems().forEach(tmp2 -> {
				tmp2.setSaleOrder(null);
				tmp2.setMember(null);
				tmp2.setSupplier(null);
				tmp2.getCommodity().setCommodityLevelDiscounts(null);
				tmp2.getCommodity().setCategory(null);
				tmp2.getCommodity().setFreightTemplate(null);
				tmp2.getCommodity().setSupplier(null);
				tmp2.getCommodity().setProducts(null);
				tmp2.getCommodity().setComments(null);
				tmp2.getCommodity().setCouponGrantConfig(null);
				tmp2.getProduct().setCommodity(null);
				tmp2.getProduct().setAttributes(null);
				tmp2.getProduct().setSupplier(null);
			});
		});
		List<SaleOrderListVo> vos = orderConvert.toListVos(pages.getContent());
		return new PageImpl<SaleOrderListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SaleOrderListVo> queryListVoForSupplier(Pagination<SaleOrder> query) {
		if (query.getFilter() != null && CollectionUtils.isNotEmpty(query.getFilter().getFilters())) {
			for (FilterDescriptor filter : query.getFilter().getFilters()) {
				if ("orderType".equals(filter.getField()) && OrderEnum.ORDER_TYPE_GROUP.getCode().equals(filter.getValue())) {
					query.getParams().put("groupOrder", Boolean.TRUE);
				}
			}
		}
		query.setEntityClazz(SaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SaleOrder_.orderTime)));
			Object memberId = query.getParams().get("member.id");
			if (memberId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.member).get(Member_.id), memberId));
			}
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.supplier).get(Supplier_.id), supplierId));
			} else {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.supplier).get(Supplier_.id), null));
			}
			Object commodityName = query.getParams().get("commodityName");
			if (commodityName != null) {
				ListJoin<SaleOrder, SaleOrderItem> orderItemJoin = root.join(root.getModel().getDeclaredList("saleOrderItems", SaleOrderItem.class), JoinType.LEFT);
				list.add(criteriaBuilder
						.and(criteriaBuilder.like(orderItemJoin.get(SaleOrderItem_.product).get(Product_.productShortName), "%" + commodityName.toString().trim() + "%")));
			}
			Object groupOrder = query.getParams().get("groupOrder");
			if (groupOrder != null && Boolean.valueOf(groupOrder.toString())) {
				// 团购单 必须成团
				list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.groupState), PromotionEnum.GROUP_BUY_STATE_GROUP.getCode())));
			}
		}));
		Page<SaleOrder> pages = saleOrderDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.setCoupons(null);
			tmp.setVouchers(null);
			tmp.getSaleOrderItems().forEach(tmp2 -> {
				tmp2.setSaleOrder(null);
				tmp2.setMember(null);
				tmp2.setSupplier(null);
				tmp2.getCommodity().setCommodityLevelDiscounts(null);
				tmp2.getCommodity().setCategory(null);
				tmp2.getCommodity().setFreightTemplate(null);
				tmp2.getCommodity().setSupplier(null);
				tmp2.getCommodity().setProducts(null);
				tmp2.getCommodity().setComments(null);
				tmp2.getCommodity().setCouponGrantConfig(null);
				tmp2.getProduct().setCommodity(null);
				tmp2.getProduct().setAttributes(null);
				tmp2.getProduct().setSupplier(null);
			});
		});
		List<SaleOrderListVo> vos = orderConvert.toListVos(pages.getContent());
		return new PageImpl<SaleOrderListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SaleOrderListVo> queryListVoForApp(Pagination<SaleOrder> query) {
		if (query.getFilter() != null && CollectionUtils.isNotEmpty(query.getFilter().getFilters())) {
			for (FilterDescriptor filter : query.getFilter().getFilters()) {
				if ("orderType".equals(filter.getField()) && OrderEnum.ORDER_TYPE_GROUP.getCode().equals(filter.getValue())) {
					query.getParams().put("groupOrder", Boolean.TRUE);
				}
			}
		}
		query.setEntityClazz(SaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SaleOrder_.orderTime)));
			Object memberId = query.getParams().get("member.id");
			if (memberId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.member).get(Member_.id), memberId));
			} else {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.member).get(Member_.id), 0));
			}
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.supplier).get(Supplier_.id), supplierId));
			}
			Object groupOrder = query.getParams().get("groupOrder");
			if (groupOrder != null && Boolean.valueOf(groupOrder.toString())) {
				// 团购单 必须成团
				list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.groupState), PromotionEnum.GROUP_BUY_STATE_GROUP.getCode())));
			}
		}));
		Page<SaleOrder> pages = saleOrderDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.setCoupons(null);
			tmp.setVouchers(null);
			tmp.getSaleOrderItems().forEach(tmp2 -> {
				tmp2.setSaleOrder(null);
				tmp2.setMember(null);
				tmp2.setSupplier(null);
				tmp2.getCommodity().setCommodityLevelDiscounts(null);
				tmp2.getCommodity().setCategory(null);
				tmp2.getCommodity().setFreightTemplate(null);
				tmp2.getCommodity().setSupplier(null);
				tmp2.getCommodity().setProducts(null);
				tmp2.getCommodity().setComments(null);
				tmp2.getCommodity().setCouponGrantConfig(null);
				tmp2.getProduct().setAttributes(null);
				tmp2.getProduct().setSupplier(null);
			});
		});
		List<SaleOrderListVo> vos = orderConvert.toListVos(pages.getContent());
		return new PageImpl<SaleOrderListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SaleOrderListVo> queryListVoForCommunity(Pagination<SaleOrder> query) {
		if (query.getFilter() != null && CollectionUtils.isNotEmpty(query.getFilter().getFilters())) {
			for (FilterDescriptor filter : query.getFilter().getFilters()) {
				if ("orderType".equals(filter.getField()) && OrderEnum.ORDER_TYPE_GROUP.getCode().equals(filter.getValue())) {
					query.getParams().put("groupOrder", Boolean.TRUE);
				}
			}
		}
		query.setEntityClazz(SaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.orderState), OrderEnum.ORDER_STATE_COMPLETED.getCode())));
			list1.add(criteriaBuilder.desc(root.get(SaleOrder_.orderTime)));
			// 小区
			Object communityId = query.getParams().get("community.id");
			if (communityId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.community).get(Community_.id), communityId));
			} else {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.community).get(Community_.id), 0));
			}
			Object groupOrder = query.getParams().get("groupOrder");
			if (groupOrder != null && Boolean.valueOf(groupOrder.toString())) {
				// 团购单 必须成团
				list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.groupState), PromotionEnum.GROUP_BUY_STATE_GROUP.getCode())));
			}
		}));
		Page<SaleOrder> pages = saleOrderDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.setCoupons(null);
			tmp.setVouchers(null);
			tmp.getSaleOrderItems().forEach(tmp2 -> {
				tmp2.setSaleOrder(null);
				tmp2.setMember(null);
				tmp2.setSupplier(null);
				tmp2.getCommodity().setCommodityLevelDiscounts(null);
				tmp2.getCommodity().setCategory(null);
				tmp2.getCommodity().setFreightTemplate(null);
				tmp2.getCommodity().setSupplier(null);
				tmp2.getCommodity().setProducts(null);
				tmp2.getCommodity().setComments(null);
				tmp2.getCommodity().setCouponGrantConfig(null);
				tmp2.getProduct().setAttributes(null);
				tmp2.getProduct().setSupplier(null);
			});
		});
		List<SaleOrderListVo> vos = orderConvert.toListVos(pages.getContent());
		return new PageImpl<SaleOrderListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	public SaleOrder addOrder(SaleOrder order) {
		if (order == null || order.getMember() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return saleOrderDao.save(order);
	}

	@Override
	public SaleOrderVo addOrder(SaleOrderBo order) {
		return orderConvert.toVo(this.addOrder(orderConvert.toEntity(order)));
	}

	@Override
	public SaleOrder updateOrder(SaleOrder salerOrder) {
		SaleOrder dbSalerOrder = saleOrderDao.getOne(salerOrder.getId());
		AttributeReplication.copying(salerOrder, dbSalerOrder, SaleOrder_.payTradeNo, SaleOrder_.tradeType, SaleOrder_.payMode, SaleOrder_.orderType, SaleOrder_.orderState,
				SaleOrder_.commentState, SaleOrder_.afterSaleState, SaleOrder_.refundAmount, SaleOrder_.buyerMessage, SaleOrder_.consignee, SaleOrder_.consigneePhone,
				SaleOrder_.consigneeAddr, SaleOrder_.deliveryMode, SaleOrder_.expressCompany, SaleOrder_.expressNo, SaleOrder_.payInvalidTime, SaleOrder_.returnInvalidTime,
				SaleOrder_.exchangeInvalidTime, SaleOrder_.paymentTime, SaleOrder_.deliveryTime, SaleOrder_.receiptTime, SaleOrder_.finishTime, SaleOrder_.closeTime,
				SaleOrder_.readState, SaleOrder_.orderSource, SaleOrder_.paymentChannel, SaleOrder_.remark);
		return dbSalerOrder;
	}

	@Override
	public SaleOrderVo updateOrder(SaleOrderBo saleOrderBo) {
		return orderConvert.toVo(this.updateOrder(orderConvert.toEntity(saleOrderBo)));
	}

	@Override
	public void removeOrderById(Integer orderId) {
		SaleOrder dbSaleOrder = saleOrderDao.getOne(orderId);
		if (dbSaleOrder != null) {
			dbSaleOrder.setDeleted(Deleted.DEL_TRUE);
			dbSaleOrder.setDelTime(new Date());
		}
	}

	@Override
	public void piliangDeleteOrderByIdTest(String  biaoName,List<Integer> ids) {


	    if (biaoName.equals("sale_order")){
			for (int id:ids) {
				if (id==0){
					continue;
				}
				SaleOrder dbSaleOrder = saleOrderDao.getOne(id);
				if (dbSaleOrder != null) {
					saleOrderDao.deletesale_orderTest(dbSaleOrder.getId());
				}
			}
        }else if (biaoName.equals("sale_order_item")){
			for (int id:ids) {
				if (id==1){
					continue;
				}
				SaleOrderItem dbSaleOrderItem = saleOrderItemDao.getOne(id);
				if (dbSaleOrderItem != null) {
					saleOrderItemDao.deletesale_order_itemTest(dbSaleOrderItem.getId());
				}
			}
        }
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SaleOrder getOrderById(Integer orderId) {
		if (saleOrderDao.existsById(orderId)) {
			return saleOrderDao.getOne(orderId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SaleOrderVo getOrderVoById(Integer saleOrderId) {
		SaleOrder orderById = this.getOrderById(saleOrderId);
		SaleOrderVo saleOrderVo = changeVOTest(orderById);
		//	SaleOrderVo saleOrderVo = orderConvert.toVo(orderById);
		if (saleOrderVo != null) {
			// 团购订单 查询团购信息
			if (OrderEnum.ORDER_TYPE_GROUP.getCode().equals(saleOrderVo.getOrderType())) {
				GroupBuyOrderVo groupBuyOrderVo = groupBuyOrderService.getVoByMemberAndSaleOrder(saleOrderVo.getMember().getId(), saleOrderId);
				saleOrderVo.setGroupBuyProductId(groupBuyOrderVo.getGroupBuyActivityProduct().getId());
				groupBuyOrderVo.setGroupBuyActivityProduct(null);
				saleOrderVo.setGroupBuyOrder(groupBuyOrderVo);
			}
		}
		// if
		// (!OrderEnum.ORDER_TYPE_ORDINARY.getCode().equals(saleOrderVo.getOrderType()))
		// {
		// if
		// (OrderEnum.ORDER_TYPE_GROUP_BUY.getCode().equals(saleOrderVo.getOrderType()))
		// {
		// saleOrderVo.setNationalGroupRecord(nationalGroupRecordService.getNationalGroupRecordVoById(saleOrderVo.getNationalGroupRecordId()));
		// } else if
		// (OrderEnum.ORDER_TYPE_GROUP_GINSENG.getCode().equals(saleOrderVo.getOrderType()))
		// {
		// NationalGroupMember nationalGroupMember =
		// nationalGroupMemberDao.getOne(saleOrderVo.getNationalGroupRecordId());
		// nationalGroupMember.getNationalGroupRecord().getNationalGroupMembers().stream().filter(e
		// -> OrderEnum.ALREADY_PAID.getCode().equals(e.getPay()));
		// saleOrderVo.setNationalGroupRecord(nationalGroupRecordService.entityTurnVo(nationalGroupMember.getNationalGroupRecord()));
		// }
		// }
		// saleOrderVo.setCloseTime(orderSettingService.getCloseTimeByOrder(saleOrderVo.getOrderTime(),
		// saleOrderVo.getOrderType()));
		return saleOrderVo;
	}
   public  SaleOrderVo changeVOTest( SaleOrder saleOrder ){
	   ArrayList<SaleOrderVo> saleOrderVos = new ArrayList<>();//	List<SaleOrderVo> saleOrderVos = orderConvert.toVos(orderMap.values());
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    MemberSimple memberSimple = new MemberSimple();  SupplierSimple supplierSimple = new SupplierSimple();   CommoditySimple commoditySimple = new CommoditySimple();
	     SaleOrderVo saleOrderVo = new SaleOrderVo();  SaleOrderItemSimple saleOrderItemSimple = new SaleOrderItemSimple();ProductSimple productSimple = new ProductSimple();
	   CouponReceiveSimple couponReceiveSimple = new CouponReceiveSimple();  	CouponSimple couponSimple = new CouponSimple();  CouponGrantRecordSimple couponGrantRecordSimple = new CouponGrantRecordSimple();
	   SaleOrderSimple saleOrderSimple = new SaleOrderSimple();  List<CouponReceiveSimple> couponReceiveSimples = new ArrayList<CouponReceiveSimple>();
	   CategorySimple categorySimple = new CategorySimple();
	   AttributeSimple attributeSimple = new AttributeSimple();
	   List<CouponReceiveSimple> vocherSimples = new ArrayList<CouponReceiveSimple>();
	   CouponReceiveSimple couponReceiveSimple1 = new CouponReceiveSimple();

	   List<SaleOrderItemSimple> saleOrderItemSimples = new ArrayList<SaleOrderItemSimple>();
	     saleOrder.setVouchers(null);
	   if (saleOrder.getMember()!=null){BeanUtils.copyProperties(saleOrder.getMember(),memberSimple);  saleOrderVo.setMember(memberSimple); }
	   if (saleOrder.getSupplier()!=null){BeanUtils.copyProperties(saleOrder.getSupplier(),supplierSimple);  saleOrderVo.setSupplier(supplierSimple);  }
	   if (saleOrder.isUseIntegralCash()){ IntegralCashSimple integralCashSimple = new IntegralCashSimple();BeanUtils.copyProperties(integralCashDao.getOne(8),integralCashSimple);saleOrderVo.setIntegralCash(integralCashSimple);if (integralCashDao.getOne(8).getState()==1){saleOrderVo.setIntegralCash(null);}}
	   BeanUtils.copyProperties(saleOrder,saleOrderVo);

	   List<SaleOrderItem> saleOrderItems = saleOrder.getSaleOrderItems();//添加saleOrderItems属性
	   for (SaleOrderItem saleOrderItem:saleOrderItems) {
		   BeanUtils.copyProperties(saleOrderItem,saleOrderItemSimple);
		   if(saleOrderItem.getMember()!=null){BeanUtils.copyProperties(saleOrderItem.getMember(),memberSimple); saleOrderItemSimple.setMember(memberSimple);}
		   if(saleOrderItem.getSupplier()!=null){BeanUtils.copyProperties(saleOrderItem.getSupplier(),supplierSimple); saleOrderItemSimple.setSupplier(supplierSimple);}
		   if(saleOrderItem.getCommodity()!=null){BeanUtils.copyProperties(saleOrderItem.getCommodity(),commoditySimple);
			   if (saleOrderItem.getCommodity().getSupplier()!=null){BeanUtils.copyProperties(saleOrderItem.getCommodity().getSupplier(),supplierSimple);commoditySimple.setSupplier(supplierSimple);};if(saleOrderItem.getCommodity().getCategory()!=null){BeanUtils.copyProperties(saleOrderItem.getCommodity().getCategory(),categorySimple);commoditySimple.setCategory(categorySimple);}saleOrderItemSimple.setCommodity(commoditySimple);}
		   if(saleOrderItem.getProduct()!=null){BeanUtils.copyProperties(saleOrderItem.getProduct(),productSimple); if (saleOrderItem.getProduct().getAttributes()!=null){Set<AttributeSimple> attributeSimpleSet = new HashSet<AttributeSimple>();
			   for (Attribute att:saleOrderItem.getProduct().getAttributes()) {
				   BeanUtils.copyProperties(att,attributeSimple);  attributeSimpleSet.add(attributeSimple);
			   }productSimple.setAttributes(attributeSimpleSet);
		   }saleOrderItemSimple.setProduct(productSimple);  }
		   saleOrderItemSimples.add(saleOrderItemSimple);
	   }
	   List<CouponReceive> coupons = saleOrder.getCoupons();
	   for (CouponReceive couponReceive: coupons) {
		   BeanUtils.copyProperties(couponReceive,couponReceiveSimple);
		   if (couponReceive.getCoupon()!=null){BeanUtils.copyProperties(couponReceive.getCoupon(),couponSimple);couponReceiveSimple.setCoupon(couponSimple); }
		   if (couponReceive.getMember()!=null){ BeanUtils.copyProperties(couponReceive.getMember(),memberSimple);couponReceiveSimple.setMember(memberSimple);}
		   if (couponReceive.getSaleOrder()!=null){BeanUtils.copyProperties(couponReceive.getSaleOrder(),saleOrderSimple);couponReceiveSimple.setSaleOrder(saleOrderSimple);}
		   couponReceiveSimples.add(couponReceiveSimple);
	   }

	   List<CouponReceive> vouchers = saleOrder.getVouchers();
	   if (vouchers!=null) {
		   for (CouponReceive vocher : vouchers) {
			   BeanUtils.copyProperties(vocher, couponReceiveSimple1);
			   if (vocher.getSaleOrder() != null) {
				   BeanUtils.copyProperties(vocher.getSaleOrder(), saleOrderSimple);
				   couponReceiveSimple1.setSaleOrder(saleOrderSimple);
			   }
			   if (vocher.getMember() != null) {
				   BeanUtils.copyProperties(vocher.getMember(), memberSimple);
				   couponReceiveSimple1.setMember(memberSimple);
			   }
			   if (vocher.getCoupon() != null) {
				   BeanUtils.copyProperties(vocher.getCoupon(), couponSimple);
				   couponReceiveSimple1.setCoupon(couponSimple);
			   }
			   vocherSimples.add(couponReceiveSimple1);
		   }
	   }

	   saleOrderVo.setSaleOrderItems(saleOrderItemSimples);
	   saleOrderVo.setCoupons(couponReceiveSimples);
	   saleOrderVo.setVouchers(vocherSimples);
	   return saleOrderVo;
   }
	// @Override
	// @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	// public SaleOrderVo getOrderVoByGiftId(Integer giftId) {
	// SaleOrderVo saleOrderVo =
	// orderConvert.toVo(saleOrderDao.findByGift_id(giftId));
	// saleOrderVo.setCloseTime(orderSettingService.getInvalidTimeBySetType(saleOrderVo.getOrderTime(),
	// OrderEnum.ORDER_SET_TYPE_NORMAL));
	// return saleOrderVo;
	// }

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SaleOrderListVo getOrderListVoById(Integer orderId) {
		return orderConvert.toListVo(this.saleOrderDao.getOne(orderId));
	}

	protected void initConvert() {
		this.orderConvert = new EntityListVoBoSimpleConvert<SaleOrder, SaleOrderBo, SaleOrderVo, SaleOrderSimple, SaleOrderListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<SaleOrder, SaleOrderVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrder, SaleOrderVo>(beanConvertManager) {
					@Override
					protected void postConvert(SaleOrderVo OrderVo, SaleOrder Order) {

					}
				};
			}

			@Override
			protected BeanConvert<SaleOrder, SaleOrderListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrder, SaleOrderListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SaleOrderListVo OrderListVo, SaleOrder Order) {

					}
				};
			}

			@Override
			protected BeanConvert<SaleOrder, SaleOrderBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrder, SaleOrderBo>(beanConvertManager) {
					@Override
					protected void postConvert(SaleOrderBo OrderBo, SaleOrder Order) {

					}
				};
			}

			@Override
			protected BeanConvert<SaleOrderBo, SaleOrder> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrderBo, SaleOrder>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SaleOrder, SaleOrderSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrder, SaleOrderSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SaleOrderSimple, SaleOrder> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrderSimple, SaleOrder>(beanConvertManager) {
					@Override
					public SaleOrder convert(SaleOrderSimple OrderSimple) throws BeansException {
						return saleOrderDao.getOne(OrderSimple.getId());
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
	 * 关闭、恢复订单等操作
	 */
	public void updateOrderState(Integer orderId, Integer orderState) {
		SaleOrder dbSaleOrder = this.getOrderById(orderId);
		if (dbSaleOrder != null && orderState != null) {
			// 关闭的订单恢复
			if (OrderEnum.ORDER_STATE_CLOSED.getCode().equals(dbSaleOrder.getOrderState()) && OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(orderState)) {
				dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_PAY.getCode());
				dbSaleOrder.setCloseTime(null);
			}
			// 待付款的订单关闭
			if (OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(dbSaleOrder.getOrderState()) && OrderEnum.ORDER_STATE_CLOSED.getCode().equals(orderState)) {
				dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_CLOSED.getCode());
				dbSaleOrder.setCloseTime(new Date());
			}
		}
	}

	/**
	 * 取消订单
	 */
	@Override
	public void cancelOrder(Integer orderId) {
		SaleOrder dbSaleOrder = saleOrderDao.getOne(orderId);
		if (dbSaleOrder != null) {
			// 退还余额
			accountService.returnBalanceByRefund(dbSaleOrder.getMember(), dbSaleOrder.getBalance(), dbSaleOrder);
			// 退还积分   先获取积分抵扣比例
			IntegralCash dbIntegralCash = integralCashDao.getOne(8);
			if (dbIntegralCash!=null) {
				BigDecimal integral = new BigDecimal(dbIntegralCash.getIntegral());
				int i = dbSaleOrder.getIntegralCashAmount().multiply(integral).intValue();
				accountService.updateByOrder(dbSaleOrder.getMember().getId(), i);
			}
			dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_CLOSED.getCode());
			dbSaleOrder.setCloseTime(new Date());
		}
	}

	/**
	 * 修改订单地址
	 */
	@Override
	public SaleOrderVo updateProvince(SaleOrderBo Order) {
		SaleOrder saleOrder = saleOrderDao.getOne(Order.getId());
		saleOrder.setConsignee(Order.getConsignee());
		saleOrder.setConsigneeAddr(Order.getConsigneeAddr());
		saleOrder.setConsigneePhone(Order.getConsigneePhone());
		return orderConvert.toVo(saleOrder);
	}

	/**
	 * 修改订单金额
	 */
	@Override
	public SaleOrderVo updatePrice(SaleOrderBo saleOrderBo) {
		SaleOrder saleOrder = saleOrderDao.getOne(saleOrderBo.getId());
		if (CollectionUtils.isNotEmpty(saleOrder.getSaleOrderItems())) {
			Set<SaleOrderItem> items = new HashSet<>();
			for (SaleOrderItem tmp : orderConvert.toEntity(saleOrderBo).getSaleOrderItems()) {
				if (tmp != null) {
					SaleOrderItem dbSaleOrderItem = saleOrderItemService.getSaleOrderItemById(tmp.getId());
					AttributeReplication.copying(tmp, dbSaleOrderItem, SaleOrderItem_.price);
				}
			}
		}
		saleOrder.setFreight(saleOrderBo.getFreight());
		return orderConvert.toVo(saleOrder);
	}

	/**
	 * 发货
	 */
	@Override
	public SaleOrderVo delivery(Integer id, String trackingNo, String logisticsCompany) {
		SaleOrder dbSaleOrder = saleOrderDao.getOne(id);
		// 已付款待发货的状态 改为已发货待收货
		if (dbSaleOrder != null && OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode().equals(dbSaleOrder.getOrderState())) {
			dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode());
			dbSaleOrder.setExpressCompany(logisticsCompany);
			dbSaleOrder.setExpressNo(trackingNo);
			dbSaleOrder.setDeliveryTime(new Date());
			// 发货
			orderLogService.addLogByOrder(dbSaleOrder, OrderEnum.ORDER_LOG_STATE_DISTRIBUTION, "开始配送");
			return orderConvert.toVo(dbSaleOrder);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOrderNumByDate(Date date) {
		if (date == null) {
			return 0;
		}
		return saleOrderDao.countByDeletedAndCreateTime(Deleted.DEL_FALSE, date);
	}

	/**
	 * 根据时间 统计供应商订单数
	 *
	 * @param date
	 * @return
	 */

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOrderNumByDateAndSupplier(Date date, Integer supplierId) {
		if (date == null) {
			return 0;
		}
		return saleOrderDao.countByDeletedAndCreateTimeAndSupplier(Deleted.DEL_FALSE, date, supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWaitDeliveryNum() {
		return saleOrderDao.countByDeletedAndOrderState(Deleted.DEL_FALSE, OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());
	}

	/**
	 * 获取供应商待发货订单数
	 *
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWaitDeliveryNumSupplier(Integer supplierId) {
		return saleOrderDao.countByDeletedAndOrderStateAndSupplier_id(Deleted.DEL_FALSE, OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode(), supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal getTradeAmountByDate(Date date, Integer[] state) {
		if (date == null) {
			return BigDecimal.ZERO;
		}
		// 订单状态 2-已付款待发货 3-已发货待收货 4-已收货已完成
		return saleOrderDao.sumTradeAmountByDeletedAndStateAndCreateTime(Deleted.DEL_FALSE, state, date);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal getTradeAmountByDateAndSupplier(Date date, Integer[] state, Integer supplierId) {
		if (date == null) {
			return BigDecimal.ZERO;
		}
		// 订单状态 2-已付款待发货 3-已发货待收货 4-已收货已完成
		return saleOrderDao.sumTradeAmountByDeletedAndStateAndCreateTimeAndSupplier(Deleted.DEL_FALSE, state, date, supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAfterSaleOrderNum() {
		return saleOrderDao.countByDeletedAndAfterSaleState(Deleted.DEL_FALSE, OrderEnum.AFTER_SALE_STATE_APPLY.getCode());
	}

	/**
	 * 统计 供应商售后订单数量
	 *
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAfterSaleOrderNumBySupplier(Integer supplierId) {
		return saleOrderDao.countByDeletedAndAfterSaleStateAndSupplier_id(Deleted.DEL_FALSE, OrderEnum.AFTER_SALE_STATE_APPLY.getCode(), supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getMemberConsumesByDate(Pageable pageable, Date startDate, Date endDate, Integer[] state) {
		return saleOrderDao.findMemberConsumesByDate(pageable, startDate, endDate, state);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getCommoditySalesByDate(Pageable pageable, Date startDate, Date endDate, Integer[] state) {
		return saleOrderDao.findCommoditySalesByDate(pageable, startDate, endDate, state);
	}

	/**
	 * 供应商获取会员消费排行
	 *
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getMemberConsumesByDateAndSupplier(Pageable pageable, Date startDate, Date endDate, Integer[] state, Integer supplierId) {
		return saleOrderDao.findMemberConsumesByDateAndSupplier(pageable, startDate, endDate, state, supplierId);
	}

	/**
	 * 供应商获取商品销量排行
	 *
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getCommoditySalesByDateAndSupplier(Pageable pageable, Date startDate, Date endDate, Integer[] state, Integer supplierId) {
		return saleOrderDao.findCommoditySalesByDateAndSupplier(pageable, startDate, endDate, state, supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getDailyAddNumByDate(Date startDate, Date endDate) {
		return saleOrderDao.findDailyAddNumByDate(Deleted.DEL_FALSE, startDate, endDate);
	}

	/**
	 * 统计供应商每天新增数量
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getDailyAddNumByDateAndSupplierId(Date startDate, Date endDate, Integer supplierId) {
		return saleOrderDao.findDailyAddNumByDateAndSupplierId(Deleted.DEL_FALSE, startDate, endDate, supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByDeletedAndState(Integer deleted, Integer state) {
		return saleOrderDao.countByDeletedAndOrderState(deleted, state);
	}

	/**
	 * 供应商业绩排名
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getSupplierAchievements(Pageable pageable, Date startDate, Date endDate, Integer[] state) {
		return saleOrderDao.findSupplierAchievements(pageable, startDate, endDate, state);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SaleOrder> getPayOrderByIds(Object orderIds) {
		if (orderIds != null) {
			if (orderIds instanceof Collection) {
				return saleOrderDao.findAllById((Iterable<Integer>) orderIds);
			} else if (orderIds instanceof String) {
				Set<Integer> idSets = Arrays.stream(orderIds.toString().split(",")).map(e -> Integer.valueOf(e.trim())).collect(Collectors.toSet());
				return saleOrderDao.findAllById(idSets);
			}
		}
		return null;
	}

	/**
	 * 支付成功以后处理方法
	 */
	@Override
	public void afterPayByWeChat(SortedMap<String, String> resultMap) {
		if (MapUtils.isNotEmpty(resultMap)) {
			List<SaleOrder> dbSaleOrders = this.getPayOrderByIds(resultMap.get("attach"));
			if (CollectionUtils.isNotEmpty(dbSaleOrders)) {
				for (SaleOrder tmp : dbSaleOrders) {
					if (tmp != null && OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(tmp.getOrderState())) {
						tmp.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());
					}
				}
			}
		}

	}

	/**
	 * TODO 待修正优化
	 * <p>
	 * 用户订单状态数量
	 *
	 * @param memberId
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, Object> getOrderState(Integer memberId) {
		Map<String, Object> result = new HashMap<>();
		// int waitPayNum =
		// saleOrderDao.countByOrderStateAndMemberIdAndDeleted(OrderEnum.ORDER_STATE_WAIT_PAY.getCode(),
		// memberId, Deleted.DEL_FALSE);
		// int waitDeliveryNum =
		// saleOrderDao.countByOrderStateAndMemberIdAndDeleted(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode(),
		// memberId, Deleted.DEL_FALSE);
		// int waitReceiptNum =
		// saleOrderDao.countByOrderStateAndMemberIdAndDeleted(OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode(),
		// memberId, Deleted.DEL_FALSE);
		int waitPayNum = saleOrderDao.countOrderNumByState(OrderEnum.ORDER_STATE_WAIT_PAY.getCode(), memberId, Deleted.DEL_FALSE);
		int waitDeliveryNum = saleOrderDao.countOrderNumByState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode(), memberId, Deleted.DEL_FALSE);
		int waitReceiptNum = saleOrderDao.countOrderNumByState(OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode(), memberId, Deleted.DEL_FALSE);
		int waitCommentNum = saleOrderDao.countByCommentStateAndMemberIdAndDeleted(OrderEnum.COMMENT_STATE_WAIT.getCode(), memberId, Deleted.DEL_FALSE);
		int couponNum = couponReceiveService.countByMemberId(memberId);
		Boolean signState = signTimeService.checkSign(memberId);
		// 封装返回数据
		result.put("waitPayNum", waitPayNum);
		result.put("waitReceiptNum", waitDeliveryNum + waitReceiptNum);
		result.put("waitCommentNum", waitCommentNum);
		result.put("couponNum", couponNum);
		result.put("signState", signState);
		return result;
	}

	/**
	 * 获取待支付的订单
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SaleOrder> getWaitPayOrdersForWeChat() {
		return saleOrderDao.findByOrderStateAndDeleted(OrderEnum.ORDER_STATE_WAIT_PAY.getCode(), Deleted.DEL_FALSE);
	}

	/**
	 * 评价更新状态
	 */
	@Override
	public void updateSaleOrderByComment(Integer orderId) {
		SaleOrder dbSaleOrder = this.getOrderById(orderId);
		if (dbSaleOrder != null) {
			dbSaleOrder.setCommentState(OrderEnum.COMMENT_STATE_ALREADY.getCode());
		}
	}

	/**
	 * 确认收货 计算积分和佣金
	 */
	@Override
	public List<Object> confirmReceipt(Integer orderId) {
		// 待收货的 确认收货
		SaleOrder dbSaleOrder = this.getOrderById(orderId);
		if (dbSaleOrder != null && OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode().equals(dbSaleOrder.getOrderState())) {
			dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_COMPLETED.getCode());
			dbSaleOrder.setCommentState(OrderEnum.COMMENT_STATE_WAIT.getCode());
			dbSaleOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_CAN_APPLY.getCode());
			dbSaleOrder.setReceiptTime(new Date());
			dbSaleOrder.setReturnInvalidTime(DateUtils.addDays(dbSaleOrder.getReceiptTime(), 7));
			dbSaleOrder.setExchangeInvalidTime(DateUtils.addDays(dbSaleOrder.getReceiptTime(), 15));
			// 增加订单日志
			orderLogService.addLogByOrder(dbSaleOrder, OrderEnum.ORDER_LOG_STATE_CONFIRM_RECEIPT, "确认收货");
			// 计算上级佣金 和 小区管理员提成
			memberService.calculateCommissionForDistribution(dbSaleOrder, dbSaleOrder.getMember());
			// 计算会员积分
			memberService.calculateOrderIntegral(dbSaleOrder, dbSaleOrder.getMember());
			// 计算供应商账户数据
			supplierAccountService.updateSupplierAccountByConfirmReceipt(dbSaleOrder);
			// 分步发放优惠券
			List<Object> grantAmounts = couponReceiveService.grantVoucherByStep(dbSaleOrder.getMember(), dbSaleOrder, ActivityEnum.GRANT_NODE_RECEIPT);
			// 解冻代金券
			couponReceiveService.thawVoucherByStep(dbSaleOrder.getMember(), dbSaleOrder, ActivityEnum.GRANT_NODE_RECEIPT);
			return grantAmounts;
		}
		return null;
	}

	/**
	 * 自动关闭未支付的订单
	 */
	@Override
	public void closeOrderByTask() throws Exception {
		List<SaleOrder> saleOrders = saleOrderDao.findByOrderStateAndDeleted(OrderEnum.ORDER_STATE_WAIT_PAY.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(saleOrders)) {
			LOG.info("没有超时未付款的订单");
			return;
		}
		LOG.info("超时未付款的订单个数为{}个", saleOrders.size());
		// 根据订单设置 校验是否超时
		for (SaleOrder tmpOrder : saleOrders) {
			if (tmpOrder != null && OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(tmpOrder.getOrderState())) {
				// 超时状态
				boolean flag = false;
				// 普通订单
				if (OrderEnum.ORDER_TYPE_ORDINARY.getCode().equals(tmpOrder.getOrderType())) {
					// 根据订单设置 校验是否超时
					boolean flag1 = orderSettingService.checkTimeout(tmpOrder.getOrderTime(), new Date(), OrderEnum.ORDER_SET_TYPE_NORMAL);
					if (flag1) {
						flag = true;
					}
					// 拼团订单
				} else {
					// 根据订单设置 校验是否超时
					boolean flag2 = orderSettingService.checkTimeout(tmpOrder.getOrderTime(), new Date(), OrderEnum.ORDER_SET_TYPE_SECKILL);
					if (flag2) {
						flag = true;
					}
				}
				if (flag) {
					// 向微信查询订单 查看是否支付过
					boolean wechatPayFlag = weChatNotifyService.orderQueryForWeChat(tmpOrder);
					// 向支付宝查询订单 查看是否支付过
					boolean alipayFlag = alipayService.orderQueryForAlipay(tmpOrder);
					if (!wechatPayFlag || !alipayFlag) {
						tmpOrder.setOrderState(OrderEnum.ORDER_STATE_CLOSED.getCode());
						tmpOrder.setCloseTime(new Date());
						// 团购订单 修改团购状态
						if (OrderEnum.ORDER_TYPE_GROUP.getCode().equals(tmpOrder.getOrderType())) {
							tmpOrder.setGroupState(PromotionEnum.GROUP_BUY_STATE_INVALID.getCode());
							groupBuyOrderService.updateByCloseOrder(tmpOrder);
						}
						// 释放已经使用的代金券
						couponReceiveService.returnUseVoucherByRefund(tmpOrder.getMember(), tmpOrder);
						// 增加订单日志
						orderLogService.addLogByOrder(tmpOrder, OrderEnum.ORDER_LOG_STATE_CLOSE_ORDER, "超时关闭订单");
					}
				}
			}
		}
	}

	/**
	 * 自动收货
	 */
	@Override
	public void autoReceiptByTask() throws Exception {
		List<SaleOrder> dbSaleOrders = saleOrderDao.findByOrderStateAndDeleted(OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.info("没有超时未收货的订单");
			return;
		}
		LOG.info("超时未收货的订单数为{}个", dbSaleOrders.size());
		for (SaleOrder tmpOrder : dbSaleOrders) {
			// 根据订单设置 校验是否超时
			boolean flag = orderSettingService.checkTimeout(tmpOrder.getDeliveryTime(), new Date(), OrderEnum.ORDER_SET_TYPE_RECEIPT);
			// 超时且未收货的
			if (flag && OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode().equals(tmpOrder.getOrderState())) {
				tmpOrder.setOrderState(OrderEnum.ORDER_STATE_COMPLETED.getCode());
				tmpOrder.setCommentState(OrderEnum.COMMENT_STATE_WAIT.getCode());
				if (tmpOrder.getAfterSaleState() == null) {
					tmpOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_CAN_APPLY.getCode());
				}
				tmpOrder.setReceiptTime(new Date());
				tmpOrder.setReturnInvalidTime(DateUtils.addDays(tmpOrder.getReceiptTime(), 7));
				tmpOrder.setExchangeInvalidTime(DateUtils.addDays(tmpOrder.getReceiptTime(), 15));
				// 增加订单日志
				orderLogService.addLogByOrder(tmpOrder, OrderEnum.ORDER_LOG_STATE_CONFIRM_RECEIPT, "超时确认收货");
				// 计算上级佣金 和 小区管理员提成
				memberService.calculateCommissionForDistribution(tmpOrder, tmpOrder.getMember());
				// 计算会员积分
				memberService.calculateOrderIntegral(tmpOrder, tmpOrder.getMember());
				// 计算供应商账户数据
				supplierAccountService.updateSupplierAccountByConfirmReceipt(tmpOrder);
				// 分步发放优惠券
				couponReceiveService.grantVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_RECEIPT);
				// 解冻代金券
				couponReceiveService.thawVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_COMMENT);
			}
		}
	}

	/**
	 * 自动好评订单
	 */
	@Override
	public void autoCommentByTask() throws Exception {
		List<SaleOrder> dbSaleOrders = saleOrderDao.findByCommentStateAndDeleted(OrderEnum.COMMENT_STATE_WAIT.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.info("没有超时未评价的订单");
			return;
		}
		LOG.info("未评价的订单数为{}", dbSaleOrders.size());
		// 需要评价的订单
		Set<SaleOrder> needCommentOrders = new HashSet<>();
		for (SaleOrder tmpOrder : dbSaleOrders) {
			// 根据订单设置 校验是否超时
			boolean flag = orderSettingService.checkTimeout(tmpOrder.getReceiptTime(), new Date(), OrderEnum.ORDER_SET_TYPE_COMMENT);
			if (flag && OrderEnum.COMMENT_STATE_WAIT.getCode().equals(tmpOrder.getCommentState())) {
				tmpOrder.setCommentState(OrderEnum.COMMENT_STATE_ALREADY.getCode());
				needCommentOrders.add(tmpOrder);
				// 分步发放优惠券
				couponReceiveService.grantVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_COMMENT);
				// 解冻代金券
				couponReceiveService.thawVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_COMMENT);
			}
		}
		LOG.info("超时未评价的订单数为{}", needCommentOrders.size());
		// 批量评价订单
		commentService.autoCommentByOrders(needCommentOrders);
	}

	/**
	 * 收货超过三包时效 自动完成交易且不能申请售后
	 */
	@Override
	public void autoTradeByTask() throws Exception {
		// 查询已经收货的订单
		List<SaleOrder> dbSaleOrders = saleOrderDao.findByOrderStateAndDeleted(OrderEnum.ORDER_STATE_COMPLETED.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.info("没有需要自动完成交易的订单");
			return;
		}
		for (SaleOrder tmpOrder : dbSaleOrders) {
			// 根据订单设置 校验是否超时
			boolean flag = orderSettingService.checkTimeout(tmpOrder.getReceiptTime(), new Date(), OrderEnum.ORDER_SET_TYPE_TRADE);
			if (flag && OrderEnum.AFTER_SALE_STATE_CAN_APPLY.getCode().equals(tmpOrder.getAfterSaleState())) {
				tmpOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_EXPIRE.getCode());
				tmpOrder.setCheckState(OrderEnum.CHECK_STATE_WAIT.getCode());
				tmpOrder.setSettlementState(OrderEnum.SETTLEMENT_STATE_WAIT.getCode());
				tmpOrder.setFinishTime(new Date());
				// 分步发放代金券
				couponReceiveService.grantVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_OVER_15_DAY);
				// 解冻代金券
				couponReceiveService.thawVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_OVER_15_DAY);
			}
		}
	}

	/**
	 * 获取支付宝待支付订单
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SaleOrder> getWaitPayOrdersForAlipay(String parentOrderNo) {
		if (StringUtils.isBlank(parentOrderNo)) {
			return null;
		}
		return saleOrderDao.findByPayOrderNoAndOrderStateAndDeleted(parentOrderNo, OrderEnum.ORDER_STATE_WAIT_PAY.getCode(), Deleted.DEL_FALSE);
	}

	/**
	 * 通过礼包 创建礼包订单
	 */
	@Override
	public SaleOrder addGiftOrderByGiftBag(GiftBag giftBag) {
		if (giftBag != null) {
			// 生成订单
			SaleOrder tmpOrder = new SaleOrder();
			tmpOrder.setMember(giftBag.getMember());
			tmpOrder.setCommunity(giftBag.getMember().getCommunity());
			tmpOrder.setSupplier(giftBag.getCommodity().getSupplier());
			tmpOrder.setOrderNo(NumberGenerateUtils.generateOrderNo());
			tmpOrder.setPayOrderNo(ValueUtils.generateGUID());
			tmpOrder.setOrderType(OrderEnum.ORDER_TYPE_GIFT.getCode());
			tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_PAY.getCode());
			tmpOrder.setGiftOrderType(OrderEnum.GIFT_ORDER_TYPE_SEND.getCode());
			tmpOrder.setOrderAmount(giftBag.getTotal());
			tmpOrder.setFreight(BigDecimal.ZERO);
			tmpOrder.setPayAmount(giftBag.getTotal());
			tmpOrder.setOrderTime(new Date());
			tmpOrder.setPayInvalidTime(orderSettingService.getInvalidTimeBySetType(tmpOrder.getOrderTime(), OrderEnum.ORDER_SET_TYPE_NORMAL));
			tmpOrder.setReadState(OrderEnum.READ_STATE_UNREAD.getCode());
			tmpOrder.setRemark("送礼订单");
			// 生成订单项
			SaleOrderItem tmpItem = new SaleOrderItem();
			tmpItem.setSaleOrder(tmpOrder);
			tmpItem.setMember(giftBag.getMember());
			tmpItem.setSupplier(tmpOrder.getSupplier());
			tmpItem.setCommodity(giftBag.getCommodity());
			tmpItem.setProduct(giftBag.getProduct());
			tmpItem.setPrice(giftBag.getPrice());
			tmpItem.setQuantity(giftBag.getQuantity());
			tmpItem.setSubtotal(BigDecimal.ZERO);
			// 添加订单项
			tmpOrder.getSaleOrderItems().add(tmpItem);
			// 保存订单数据
			return saleOrderDao.save(tmpOrder);
		}
		return null;
	}

	/**
	 * 通过礼物 创建礼物订单
	 */
	@Override
	public SaleOrder addGiftOrderByGift(Gift gift) {
		if (gift != null) {
			SaleOrder tmpOrder = new SaleOrder();
			tmpOrder.setMember(gift.getMember());
			tmpOrder.setCommunity(gift.getMember().getCommunity());
			tmpOrder.setSupplier(gift.getCommodity().getSupplier());
			tmpOrder.setOrderNo(NumberGenerateUtils.generateOrderNo());
			tmpOrder.setPayOrderNo(ValueUtils.generateGUID());
			tmpOrder.setOrderType(OrderEnum.ORDER_TYPE_GIFT.getCode());
			tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());
			tmpOrder.setGiftOrderType(OrderEnum.GIFT_ORDER_TYPE_RECEIPT.getCode());
			tmpOrder.setOrderAmount(gift.getTotal());
			tmpOrder.setFreight(BigDecimal.ZERO);
			tmpOrder.setPayAmount(BigDecimal.ZERO);
			tmpOrder.setConsignee(gift.getConsignee());
			tmpOrder.setConsigneePhone(gift.getConsigneePhone());
			tmpOrder.setConsigneeAddr(gift.getConsigneeAddr());
			tmpOrder.setOrderTime(new Date());
			tmpOrder.setPaymentTime(new Date());
			tmpOrder.setReadState(OrderEnum.READ_STATE_UNREAD.getCode());
			tmpOrder.setRemark("收礼订单");
			// 生成订单项
			SaleOrderItem tmpItem = new SaleOrderItem();
			tmpItem.setSaleOrder(tmpOrder);
			tmpItem.setMember(gift.getMember());
			tmpItem.setSupplier(tmpOrder.getSupplier());
			tmpItem.setCommodity(gift.getCommodity());
			tmpItem.setProduct(gift.getProduct());
			tmpItem.setPrice(gift.getPrice());
			tmpItem.setQuantity(gift.getQuantity());
			tmpItem.setSubtotal(BigDecimal.ZERO);
			// 添加订单项
			tmpOrder.getSaleOrderItems().add(tmpItem);
			// 保存订单数据
			return saleOrderDao.save(tmpOrder);
		}
		return null;
	}

	// /**
	// * 获取送礼订单
	// */
	// @Override
	// @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	// public SaleOrder getByGiftBag(Integer giftBagId) {
	// return
	// saleOrderDao.findByOrderTypeAndGiftOrderTypeAndGiftBag_id(OrderEnum.ORDER_TYPE_GIFT.getCode(),
	// OrderEnum.GIFT_ORDER_TYPE_SEND.getCode(), giftBagId);
	// }

	/**
	 * 退款时关闭订单 TODO
	 */
	@Override
	public void closeOrderByRefund(SaleOrder saleOrder) {
		if (saleOrder != null && saleOrder.getId() > 0) {
			LOG.info("退款时关闭订单");
			SaleOrder dbSaleOrder = this.getOrderById(saleOrder.getId());
			// 非已完成、已关闭的订单 关闭
			if (dbSaleOrder != null && !OrderEnum.ORDER_STATE_CLOSED.getCode().equals(dbSaleOrder.getOrderState())
					&& !OrderEnum.ORDER_STATE_COMPLETED.getCode().equals(dbSaleOrder.getOrderState())) {
				dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_CLOSED.getCode());
				dbSaleOrder.setCloseTime(new Date());
			}
		}
	}

	/**
	 * 确认订单 </br>
	 * 根据供应商 进行拆单 不会保存到数据库
	 */
	@Override
	public SaleOrderVo confirmOrder(SaleOrderBo saleOrderBo) {
		if (saleOrderBo == null || saleOrderBo.getMember() == null || CollectionUtils.isEmpty(saleOrderBo.getCartVos())) {
			LOG.error("提交数据为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 获取 会员信息
		Member dbMember = memberService.getMemberById(saleOrderBo.getMember().getId());
		if (dbMember == null) {
			LOG.error("会员数据为空");
			throw new BusinessException("会员数据不能为空");
		}
		// +++1 根据供应商进行拆单
		Map<Integer, SaleOrder> orderMap = new HashMap<>();
		for (CartVo cartVo : saleOrderBo.getCartVos()) {
			if (cartVo != null) {
				// 校验货品
				Product dbProduct = productService.checkProductForOrder(cartVo.getProduct().getId());
				if (dbProduct == null) {
					LOG.error("货品{}不存在", cartVo.getProduct().getId());
					throw new BusinessException("购物车数据不能为空");
				}
				// 生成订单 校验库存
				orderMap = baseOrderService.createOrderByCart(orderMap, dbMember, dbProduct, dbProduct.getSupplier(), cartVo);
			}
		}
		// +++2 计算订单的运费、优惠券、代金券等 并封装返回前端的数据
		SaleOrder pageOrder = new SaleOrder();
		// 如果前台传入的收货地址不为空
		ShippingAddressVo shippingAddressVo = null;
		if (saleOrderBo.getShippingAddress() != null && saleOrderBo.getShippingAddress().getId() > 0) {
			shippingAddressVo = shippingAddressService.getShippingAddressVoById(saleOrderBo.getShippingAddress().getId());
		} else {
			// 查询会员的默认收货地址 如果没有查询最近添加的收货地址
			shippingAddressVo = shippingAddressService.getDefaultAddressVoByMember(dbMember.getId());
		}
		if (shippingAddressVo != null) {
			pageOrder.setConsignee(shippingAddressVo.getFullName());
			pageOrder.setConsigneePhone(shippingAddressVo.getPhone());
			pageOrder.setConsigneeAddr(shippingAddressVo.getProvince() + shippingAddressVo.getCity() + shippingAddressVo.getDistrict() + shippingAddressVo.getAddress());
		}
		if (MapUtils.isNotEmpty(orderMap)) {
			// 可用余额
			BigDecimal usableBalance = Optional.ofNullable(dbMember.getAccount().getBalance()).orElse(BigDecimal.ZERO);
			// 可用积分抵现金额
			BigDecimal usableIntegralCashAmount = baseOrderService.getUsableIntegralCashAmount(dbMember);
			// 查询可用的优惠券
			List<CouponReceive> usableCoupons = couponReceiveService.getMemberCoupons(dbMember.getId(), orderMap.values().stream().toArray(SaleOrder[]::new));
			// 查询可用的代金券
			List<CouponReceive> usableVouchers = couponReceiveService.getMemberVouchers(dbMember.getId());
			// 锁定过的优惠券
			Set<CouponReceive> lockCoupons = new HashSet<>(0);
			// 锁定过的代金券
			Set<CouponReceive> lockVouchers = new HashSet<>(0);
			// 遍历订单
			for (SaleOrder tmpOrder : orderMap.values()) {
				if (tmpOrder != null) {
					// 运费不参与优惠券计算
					// ++2-1-----计算适合的优惠券
					List<CouponReceive> conformCoupons = baseOrderService.calculateCoupons(tmpOrder, lockCoupons, usableCoupons);
					List<CouponReceive> useCoupons = conformCoupons.stream().filter(e -> e.isChecked()).collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(useCoupons)) {
						// 添加到锁定优惠券
						lockCoupons.addAll(useCoupons);
						// 从可用的优惠券中去除
						usableCoupons.removeAll(useCoupons);
						// 该笔订单可用的优惠券
						tmpOrder.setCoupons(conformCoupons);
						// 优惠券金额
						BigDecimal couponAmount = useCoupons.stream().filter(e -> e.isChecked()).map(CouponReceive::getParValue).reduce(BigDecimal.ZERO, BigDecimal::add);
						tmpOrder.setCouponAmount(couponAmount);
						// 支付金额=当前支付金额-优惠券金额
						tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(couponAmount));
					}
					// +++ 2-2-----计算适合的代金券
					List<CouponReceive> useVouchers = baseOrderService.calculateVouchers(tmpOrder, lockVouchers, usableVouchers);
					if (CollectionUtils.isNotEmpty(useVouchers)) {
						// 添加到锁定代金券
						lockVouchers.addAll(useVouchers);
						// 从可用的代金券中去除
						usableVouchers.removeAll(useVouchers);
						// 该笔订单使用的代金券
						tmpOrder.setVouchers(useVouchers);
						// 代金券金额
						BigDecimal voucherAmount = useVouchers.stream().filter(e -> e.isChecked()).map(CouponReceive::getParValue).reduce(BigDecimal.ZERO, BigDecimal::add);
						tmpOrder.setVoucherAmount(voucherAmount);
						// 支付金额=当前支付金额-代金券金额
						tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(voucherAmount));
					}
					// +++2-3收货地址不为空 计算该笔订单的运费
					if (shippingAddressVo != null) {
						BigDecimal tmpFreight = baseOrderService.calculateFreightByOrder(tmpOrder, shippingAddressVo.getProvince(), shippingAddressVo.getCity());
						// 运费
						tmpOrder.setFreight(tmpFreight);
						// 支付金额=当前支付金额+运费
						tmpOrder.setPayAmount(tmpOrder.getPayAmount().add(tmpFreight));
						// 订单金额=当前订单金额+运费
						tmpOrder.setOrderAmount(tmpOrder.getOrderAmount().add(tmpFreight));
					}
					// +++2-4可用积分抵现金额 大于0
					if (usableIntegralCashAmount.compareTo(BigDecimal.ZERO) > 0) {
						// 积分抵现金额 大于或等于 支付金额
						if (usableIntegralCashAmount.compareTo(tmpOrder.getPayAmount()) >= 0) {
							tmpOrder.setIntegralCashAmount(tmpOrder.getPayAmount());
							tmpOrder.setPayAmount(BigDecimal.ZERO);
							tmpOrder.setUseIntegralCash(Boolean.TRUE);
							// 当前剩余 积分抵现金额
							usableIntegralCashAmount = usableIntegralCashAmount.subtract(tmpOrder.getIntegralCashAmount());
						} // 积分抵现金额 小于支付金额
						else if (usableIntegralCashAmount.compareTo(tmpOrder.getPayAmount()) < 0) {
							tmpOrder.setIntegralCashAmount(usableIntegralCashAmount);
							tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(usableIntegralCashAmount));
							tmpOrder.setUseIntegralCash(Boolean.TRUE);
							// 当前剩余余额
							usableIntegralCashAmount = BigDecimal.ZERO;
						}
					}
					// +++2-5可用余额 大于0
					if (usableBalance.compareTo(BigDecimal.ZERO) > 0) {
						// 余额 大于或等于 支付金额
						if (usableBalance.compareTo(tmpOrder.getPayAmount()) >= 0) {
							tmpOrder.setBalance(tmpOrder.getPayAmount());
							tmpOrder.setPayAmount(BigDecimal.ZERO);
							tmpOrder.setUseBalance(Boolean.TRUE);
							// 当前剩余余额
							usableBalance = usableBalance.subtract(tmpOrder.getBalance());
						} // 余额 小于支付金额
						else if (usableBalance.compareTo(tmpOrder.getPayAmount()) < 0) {
							tmpOrder.setBalance(usableBalance);
							tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(usableBalance));
							tmpOrder.setUseBalance(Boolean.TRUE);
							// 当前剩余余额
							usableBalance = BigDecimal.ZERO;
						}
					}
					// 配送方式
					tmpOrder.setDeliveryMode("快递配送");
					// 计算总单相关金额
					pageOrder.setOrderAmount(Optional.ofNullable(pageOrder.getOrderAmount()).orElse(BigDecimal.ZERO).add(tmpOrder.getOrderAmount()));
					pageOrder.setPayAmount(Optional.ofNullable(pageOrder.getPayAmount()).orElse(BigDecimal.ZERO).add(tmpOrder.getPayAmount()));
					pageOrder.setFreight(
							Optional.ofNullable(pageOrder.getFreight()).orElse(BigDecimal.ZERO).add(Optional.ofNullable(tmpOrder.getFreight()).orElse(BigDecimal.ZERO)));
					pageOrder.setCouponAmount(
							Optional.ofNullable(pageOrder.getCouponAmount()).orElse(BigDecimal.ZERO).add(Optional.ofNullable(tmpOrder.getCouponAmount()).orElse(BigDecimal.ZERO)));
					pageOrder.setVoucherAmount(Optional.ofNullable(pageOrder.getVoucherAmount()).orElse(BigDecimal.ZERO)
							.add(Optional.ofNullable(tmpOrder.getVoucherAmount()).orElse(BigDecimal.ZERO)));
					pageOrder.setBalance(
							Optional.ofNullable(pageOrder.getBalance()).orElse(BigDecimal.ZERO).add(Optional.ofNullable(tmpOrder.getBalance()).orElse(BigDecimal.ZERO)));
					pageOrder.setIntegralCashAmount(Optional.ofNullable(pageOrder.getIntegralCashAmount()).orElse(BigDecimal.ZERO)
							.add(Optional.ofNullable(tmpOrder.getIntegralCashAmount()).orElse(BigDecimal.ZERO)));
				}
			}
			// 返回数据
			pageOrder.setMember(dbMember);
			pageOrder.setOrderType(OrderEnum.ORDER_TYPE_ORDINARY.getCode());
		}


		// 优化拆单数据
		SaleOrderVo pageOrderVo = orderConvert.toVo(pageOrder);
		pageOrderVo.setCartVos(saleOrderBo.getCartVos());

		//List<SaleOrderVo> saleOrderVos = orderConvert.toVos(orderMap.values());
		Iterator<SaleOrder> saleOrderIterator = orderMap.values().iterator();
		ArrayList<SaleOrderVo> saleOrderVos = new ArrayList<>();
		while (saleOrderIterator.hasNext()){
			SaleOrder saleOrder = saleOrderIterator.next();
			SaleOrderVo saleOrderVo = new SaleOrderVo();

			//转换saleOrder
			if (saleOrder.getMember()!=null){ MemberSimple memberSimple = new MemberSimple();BeanUtils.copyProperties(saleOrder.getMember(),memberSimple);  saleOrderVo.setMember(memberSimple); }
			if (saleOrder.getSupplier()!=null){ SupplierSimple supplierSimple = new SupplierSimple();BeanUtils.copyProperties(saleOrder.getSupplier(),supplierSimple);  saleOrderVo.setSupplier(supplierSimple);  }
			if (saleOrder.isUseIntegralCash()){ IntegralCashSimple integralCashSimple = new IntegralCashSimple();BeanUtils.copyProperties(integralCashDao.getOne(8),integralCashSimple);saleOrderVo.setIntegralCash(integralCashSimple);if (integralCashDao.getOne(8).getState()==1){saleOrderVo.setIntegralCash(null);}}
			BeanUtils.copyProperties(saleOrder,saleOrderVo);

			//转换saleOrderItem
			List<SaleOrderItemSimple> saleOrderItemSimples = new ArrayList<>();
			List<SaleOrderItem> saleOrderItems = saleOrder.getSaleOrderItems();
			for (int i = 0; i <saleOrderItems.size(); i++) {
				MemberSimple memberSimple = new MemberSimple();  SupplierSimple supplierSimple = new SupplierSimple();
				CategorySimple categorySimple = new CategorySimple();	CommoditySimple commoditySimple = new CommoditySimple(); SaleOrderItemSimple saleOrderItemSimple = new SaleOrderItemSimple();
				SaleOrderItem saleOrderItem = saleOrderItems.get(i);  AttributeSimple attributeSimple = new AttributeSimple();
				BeanUtils.copyProperties(saleOrderItem,saleOrderItemSimple);saleOrderItemSimple.setCommodity(null);
				if(saleOrderItem.getMember()!=null){BeanUtils.copyProperties(saleOrderItem.getMember(),memberSimple); saleOrderItemSimple.setMember(memberSimple);}
				if(saleOrderItem.getSupplier()!=null){BeanUtils.copyProperties(saleOrderItem.getSupplier(),supplierSimple); saleOrderItemSimple.setSupplier(supplierSimple);}
				if(saleOrderItem.getCommodity()!=null){BeanUtils.copyProperties(saleOrderItem.getCommodity(),commoditySimple);
					if (saleOrderItem.getCommodity().getSupplier()!=null){
						BeanUtils.copyProperties(saleOrderItem.getCommodity().getSupplier(),supplierSimple);commoditySimple.setSupplier(supplierSimple);};if(saleOrderItem.getCommodity().getCategory()!=null){BeanUtils.copyProperties(saleOrderItem.getCommodity().getCategory(),categorySimple);commoditySimple.setCategory(categorySimple);} saleOrderItemSimple.setCommodity(commoditySimple);}//
				if(saleOrderItem.getProduct()!=null){
					ProductSimple productSimple = new ProductSimple();
					BeanUtils.copyProperties(saleOrderItem.getProduct(),productSimple); if (saleOrderItem.getProduct().getAttributes()!=null){Set<AttributeSimple> attributeSimpleSet = new HashSet<AttributeSimple>();
						for (Attribute att:saleOrderItem.getProduct().getAttributes()) {
							BeanUtils.copyProperties(att,attributeSimple);  attributeSimpleSet.add(attributeSimple);
						}productSimple.setAttributes(attributeSimpleSet);
					}saleOrderItemSimple.setProduct(productSimple);  }
				saleOrderItemSimples.add(saleOrderItemSimple);

			}

			saleOrderVo.setSaleOrderItems(saleOrderItemSimples);

			//转换 List<CouponReceiveSimple> coupons  优惠券
			List<CouponReceive> coupons = saleOrder.getCoupons();
			if (coupons!=null) {
				List<CouponReceiveSimple> couponReceiveSimples = new ArrayList<>();
				for (CouponReceive couponReceive : coupons) {
					SaleOrderSimple saleOrderSimple_cou = new SaleOrderSimple();
					CouponReceiveSimple couponReceiveSimple_cou = new CouponReceiveSimple();
					BeanUtils.copyProperties(couponReceive, couponReceiveSimple_cou);
					if (couponReceive.getCoupon() != null) {
						CouponSimple couponSimple = new CouponSimple();
						BeanUtils.copyProperties(couponReceive.getCoupon(), couponSimple);
						couponReceiveSimple_cou.setCoupon(couponSimple);
					}
					if (couponReceive.getMember() != null) {
						MemberSimple memberSimple = new MemberSimple();
						BeanUtils.copyProperties(couponReceive.getMember(), memberSimple);
						couponReceiveSimple_cou.setMember(memberSimple);
					}
					if (couponReceive.getSaleOrder() != null) {
						BeanUtils.copyProperties(couponReceive.getSaleOrder(), saleOrderSimple_cou);
						couponReceiveSimple_cou.setSaleOrder(saleOrderSimple_cou);
					}
					couponReceiveSimples.add(couponReceiveSimple_cou);
				}
				saleOrderVo.setCoupons(couponReceiveSimples);
			}

			//转换 List<CouponReceiveSimple> vouchers 代金券
			List<CouponReceive> vouchers = saleOrder.getVouchers();
			if (vouchers!=null) {
				List<CouponReceiveSimple> voucherReceiveSimples = new ArrayList<>();
				for (CouponReceive voucher:vouchers) {
					SaleOrderSimple saleOrderSimple_vou = new SaleOrderSimple();
					CouponReceiveSimple couponReceiveSimple = new CouponReceiveSimple();
					BeanUtils.copyProperties(voucher, couponReceiveSimple);
					if (voucher.getCoupon() != null) {
						CouponSimple couponSimple = new CouponSimple();
						BeanUtils.copyProperties(voucher.getCoupon(), couponSimple);
						couponReceiveSimple.setCoupon(couponSimple);
					}
					if (voucher.getMember() != null) {
						MemberSimple memberSimple = new MemberSimple();
						BeanUtils.copyProperties(voucher.getMember(), memberSimple);
						couponReceiveSimple.setMember(memberSimple);
					}
					if (voucher.getSaleOrder() != null) {
						BeanUtils.copyProperties(voucher.getSaleOrder(), saleOrderSimple_vou);
						couponReceiveSimple.setSaleOrder(saleOrderSimple_vou);
					}
					voucherReceiveSimples.add(couponReceiveSimple);
				}
				saleOrderVo.setVouchers(voucherReceiveSimples);
			}
			saleOrderVos.add(saleOrderVo);
		}


		pageOrderVo.setSplitOrders(saleOrderVos);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**	Collection<SaleOrder> saleOrders = orderMap.values();
		Iterator<SaleOrder> iterator = saleOrders.iterator();
	 ArrayList<SaleOrderVo> saleOrderVos = new ArrayList<>();
		 List<CouponReceiveSimple> couponReceiveSimples = new ArrayList<CouponReceiveSimple>();

		AttributeSimple attributeSimple = new AttributeSimple();
		List<CouponReceiveSimple> vocherSimples = new ArrayList<CouponReceiveSimple>();
		SaleOrderVo saleOrderVo = new SaleOrderVo();
		CommoditySimple commoditySimple1 = new CommoditySimple();


		List<SaleOrderItemSimple> saleOrderItemSimples = new ArrayList<SaleOrderItemSimple>();
		while (iterator.hasNext()){
			MemberSimple memberSimple = new MemberSimple();  SupplierSimple supplierSimple = new SupplierSimple();
				SaleOrder saleOrder = iterator.next();      saleOrder.setVouchers(null);
			if (saleOrder.getMember()!=null){BeanUtils.copyProperties(saleOrder.getMember(),memberSimple);  saleOrderVo.setMember(memberSimple); }
			if (saleOrder.getSupplier()!=null){BeanUtils.copyProperties(saleOrder.getSupplier(),supplierSimple);  saleOrderVo.setSupplier(supplierSimple);  }
			if (saleOrder.isUseIntegralCash()){ IntegralCashSimple integralCashSimple = new IntegralCashSimple();BeanUtils.copyProperties(integralCashDao.getOne(8),integralCashSimple);saleOrderVo.setIntegralCash(integralCashSimple);if (integralCashDao.getOne(8).getState()==1){saleOrderVo.setIntegralCash(null);}}
			BeanUtils.copyProperties(saleOrder,saleOrderVo);
			List<SaleOrderItem> saleOrderItems = saleOrder.getSaleOrderItems();//添加saleOrderItems属性
			for (int i = 0; i <saleOrderItems.size(); i++) {
				CategorySimple categorySimple = new CategorySimple();	CommoditySimple commoditySimple = new CommoditySimple(); SaleOrderItemSimple saleOrderItemSimple = new SaleOrderItemSimple();
				SaleOrderItem saleOrderItem = saleOrderItems.get(i);
				BeanUtils.copyProperties(saleOrderItem,saleOrderItemSimple);saleOrderItemSimple.setCommodity(null);
				if(saleOrderItem.getMember()!=null){BeanUtils.copyProperties(saleOrderItem.getMember(),memberSimple); saleOrderItemSimple.setMember(memberSimple);}
				if(saleOrderItem.getSupplier()!=null){BeanUtils.copyProperties(saleOrderItem.getSupplier(),supplierSimple); saleOrderItemSimple.setSupplier(supplierSimple);}
				if(saleOrderItem.getCommodity()!=null){BeanUtils.copyProperties(saleOrderItem.getCommodity(),commoditySimple);
				if (saleOrderItem.getCommodity().getSupplier()!=null){
					BeanUtils.copyProperties(saleOrderItem.getCommodity().getSupplier(),supplierSimple);commoditySimple.setSupplier(supplierSimple);};if(saleOrderItem.getCommodity().getCategory()!=null){BeanUtils.copyProperties(saleOrderItem.getCommodity().getCategory(),categorySimple);commoditySimple.setCategory(categorySimple);} saleOrderItemSimple.setCommodity(commoditySimple);}//
				if(saleOrderItem.getProduct()!=null){
					ProductSimple productSimple = new ProductSimple();
					BeanUtils.copyProperties(saleOrderItem.getProduct(),productSimple); if (saleOrderItem.getProduct().getAttributes()!=null){Set<AttributeSimple> attributeSimpleSet = new HashSet<AttributeSimple>();
					for (Attribute att:saleOrderItem.getProduct().getAttributes()) {
						BeanUtils.copyProperties(att,attributeSimple);  attributeSimpleSet.add(attributeSimple);
					}productSimple.setAttributes(attributeSimpleSet);
				}saleOrderItemSimple.setProduct(productSimple);  }
				saleOrderItemSimples.add(saleOrderItemSimple);

			}
			List<CouponReceive> coupons = saleOrder.getCoupons();
			for (CouponReceive couponReceive: coupons) {
				SaleOrderSimple saleOrderSimple = new SaleOrderSimple(); CouponSimple couponSimple = new CouponSimple();  CouponReceiveSimple couponReceiveSimple = new CouponReceiveSimple();
				BeanUtils.copyProperties(couponReceive,couponReceiveSimple);
				if (couponReceive.getCoupon()!=null){BeanUtils.copyProperties(couponReceive.getCoupon(),couponSimple);couponReceiveSimple.setCoupon(couponSimple); }
				if (couponReceive.getMember()!=null){ BeanUtils.copyProperties(couponReceive.getMember(),memberSimple);couponReceiveSimple.setMember(memberSimple);}
				if (couponReceive.getSaleOrder()!=null){BeanUtils.copyProperties(couponReceive.getSaleOrder(),saleOrderSimple);couponReceiveSimple.setSaleOrder(saleOrderSimple);}
				couponReceiveSimples.add(couponReceiveSimple);
			}

			List<CouponReceive> vouchers = saleOrder.getVouchers();
			if (vouchers!=null) {
				for (CouponReceive vocher : vouchers) {
					SaleOrderSimple saleOrderSimple = new SaleOrderSimple(); 	CouponSimple couponSimple = new CouponSimple();  CouponReceiveSimple couponReceiveSimple1 = new CouponReceiveSimple();
					BeanUtils.copyProperties(vocher, couponReceiveSimple1);
					if (vocher.getSaleOrder() != null) {
						BeanUtils.copyProperties(vocher.getSaleOrder(), saleOrderSimple);
						couponReceiveSimple1.setSaleOrder(saleOrderSimple);
					}
					if (vocher.getMember() != null) {
						BeanUtils.copyProperties(vocher.getMember(), memberSimple);
						couponReceiveSimple1.setMember(memberSimple);
					}
					if (vocher.getCoupon() != null) {
						BeanUtils.copyProperties(vocher.getCoupon(), couponSimple);
						couponReceiveSimple1.setCoupon(couponSimple);
					}
					vocherSimples.add(couponReceiveSimple1);
				}
			}


			saleOrderVo.setCoupons(couponReceiveSimples);
		saleOrderVo.setVouchers(vocherSimples);
			saleOrderVo.setSaleOrderItems(saleOrderItemSimples);
			saleOrderVos.add(saleOrderVo);
		}
*/

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



		pageOrderVo.setShippingAddress(shippingAddressVo);

		pageOrderVo.setAccountBalance(Optional.ofNullable(dbMember.getAccount().getBalance()).orElse(BigDecimal.ZERO));

		pageOrderVo.setIntegralAmount(baseOrderService.getUsableIntegralCashAmount(dbMember));
		pageOrderVo.setAvailableIntegral(dbMember.getAccount().getAvailableIntegral());
		pageOrderVo.setIntegralCash(integralCashService.getLatestIntegralCashSimple());

		return pageOrderVo;
	}


	/**
	 * 提交订单-去支付
	 */
	@Override
	public SaleOrderVo submitOrder(SaleOrderBo saleOrderBo) {
		if (saleOrderBo == null || saleOrderBo.getMember() == null || CollectionUtils.isEmpty(saleOrderBo.getSplitOrders()) || saleOrderBo.getShippingAddress() == null) {
			LOG.error("提交数据为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 收货地址
		ShippingAddress dbShippingAddress = shippingAddressService.getShippingAddressById(saleOrderBo.getShippingAddress().getId());
		if (dbShippingAddress == null) {
			LOG.error("收货地址为空");
			throw new BusinessException("请选择收货地址");
		}
		// 获取会员信息
		Member dbMember = memberService.getMemberById(saleOrderBo.getMember().getId());
		if (dbMember == null) {
			LOG.error("会员数据为空");
			throw new BusinessException("会员不能为空");
		}
		// +++1 计算需要支付的金额 并保存订单
		SaleOrderVo pageOrderVo = new SaleOrderVo();
		// 可用余额
		BigDecimal usableBalance = Optional.ofNullable(dbMember.getAccount().getBalance()).orElse(BigDecimal.ZERO);
		// 可用积分抵现金额
		BigDecimal usableIntegralCashAmount = baseOrderService.getUsableIntegralCashAmount(dbMember);
		// 总单号
		pageOrderVo.setPayOrderNo(ValueUtils.generateGUID());
		// 需要保存的订单
		List<SaleOrder> saveOrders = orderConvert.toEntities(saleOrderBo.getSplitOrders());
		// +++2 遍历订单 -整理 订单数据和订单项数据
		for (SaleOrder tmpOrder : saveOrders) {
			if (tmpOrder != null && CollectionUtils.isNotEmpty(tmpOrder.getSaleOrderItems())) {
				// 清空前台传入金额 从新计算订单金额
				tmpOrder.setOrderAmount(BigDecimal.ZERO);
				tmpOrder.setPayAmount(BigDecimal.ZERO);
				tmpOrder.setFreight(BigDecimal.ZERO);
				tmpOrder.setCouponAmount(BigDecimal.ZERO);
				tmpOrder.setVoucherAmount(BigDecimal.ZERO);
				tmpOrder.setBalance(BigDecimal.ZERO);
				tmpOrder.setIntegralCashAmount(BigDecimal.ZERO);
				tmpOrder.setCostAmount(BigDecimal.ZERO);
				// +++2-1遍历订单项
				for (SaleOrderItem tmpItem : tmpOrder.getSaleOrderItems()) {
					if (tmpItem != null) {
						if (tmpItem.getProduct() == null) {
							LOG.error("货品为空");
							throw new BusinessException("商品数据不能为空");
						}
						// 核验货品
						Product dbProduct = productService.checkProductForOrder(tmpItem.getProduct().getId());
						if (dbProduct == null) {
							LOG.error("货品为空");
							throw new BusinessException("商品数据不能为空");
						}
						// 检查是否在销售区域内
						boolean flag = baseOrderService.checkSaleRegion(dbProduct.getCommodity(), dbShippingAddress);
						if (!flag) {
							LOG.error("不在销售地区内");
							throw new BusinessException(dbProduct.getProductShortName() + " 不在销售范围内，请重新选择");
						}
						// 核验库存
						boolean stockFlag = stockService.checkStock(dbProduct.getId(), tmpItem.getQuantity());
						if (!stockFlag) {
							LOG.error("productId={}，库存不足", dbProduct.getId());
							throw new BusinessException(dbProduct.getProductShortName() + " 库存不足");
						}
						// 订单项 数据
						tmpItem.setSaleOrder(tmpOrder);
						tmpItem.setMember(dbMember);
						tmpItem.setSupplier(dbProduct.getCommodity().getSupplier());
						tmpItem.setCommodity(dbProduct.getCommodity());
						tmpItem.setProduct(dbProduct);
						// 计算会员价格 四舍五入
						BigDecimal tmpPrice = baseOrderService.calculatePriceByLevel(dbProduct, dbMember).setScale(2, BigDecimal.ROUND_UP);
						// 小计金额= 价格*数量
						BigDecimal subTotal = tmpPrice.multiply(BigDecimal.valueOf(tmpItem.getQuantity()));
						// 成本
						BigDecimal costAmount = Optional.ofNullable(dbProduct.getSupplyPrice()).orElse(BigDecimal.ZERO).multiply(BigDecimal.valueOf(tmpItem.getQuantity()));
						tmpItem.setPrice(tmpPrice);// 修改为计算后的价格
						tmpItem.setSupplyPrice(dbProduct.getSupplyPrice());// 供货价
						tmpItem.setSubtotal(subTotal);// 小计
						tmpItem.setCostAmount(costAmount);// 成本
						// +++订单 供应商
						tmpOrder.setSupplier(dbProduct.getCommodity().getSupplier());
						tmpOrder.setSupplierName(dbProduct.getCommodity().getSupplier().getSupplierName());
						// tmpOrder.setsu
						// 订单金额
						tmpOrder.setOrderAmount(tmpOrder.getOrderAmount().add(subTotal));
						tmpOrder.setPayAmount(tmpOrder.getPayAmount().add(subTotal));
						tmpOrder.setCostAmount(tmpOrder.getCostAmount().add(costAmount));
					}
				}
				// +++2-2 订单数据
				tmpOrder.setMember(dbMember);
				tmpOrder.setCommunity(dbMember.getCommunity());
				tmpOrder.setOrderNo(NumberGenerateUtils.generateOrderNo());
				tmpOrder.setPayOrderNo(pageOrderVo.getPayOrderNo());
				tmpOrder.setOrderType(OrderEnum.ORDER_TYPE_ORDINARY.getCode());
				tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_PAY.getCode());
				// 收货地址
				tmpOrder.setConsignee(dbShippingAddress.getFullName());
				tmpOrder.setConsigneePhone(dbShippingAddress.getPhone());
				tmpOrder.setConsigneeAddr(dbShippingAddress.getProvince() + dbShippingAddress.getCity() + dbShippingAddress.getDistrict() + dbShippingAddress.getAddress());
				tmpOrder.setDeliveryMode("快递配送");
				// 运费不参与优惠券计算
				// +++2-4--核算优惠券
				List<CouponReceive> usedCoupons = baseOrderService.checkCoupons(tmpOrder, tmpOrder.getCoupons());
				if (CollectionUtils.isNotEmpty(usedCoupons)) {
					// 优惠券金额
					BigDecimal couponAmount = usedCoupons.stream().map(CouponReceive::getParValue).reduce(BigDecimal.ZERO, BigDecimal::add);
					tmpOrder.setCouponAmount(couponAmount);
					// 支付金额=当前支付金额-优惠券金额
					tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(couponAmount));
				}
				tmpOrder.setCoupons(usedCoupons);
				// +++2-3--核算代金券
				List<CouponReceive> usedVouchers = baseOrderService.checkVouchers(tmpOrder, tmpOrder.getVouchers());
				if (CollectionUtils.isNotEmpty(usedVouchers)) {
					// 代金券金额
					BigDecimal voucherAmount = usedVouchers.stream().map(CouponReceive::getParValue).reduce(BigDecimal.ZERO, BigDecimal::add);
					tmpOrder.setVoucherAmount(voucherAmount);
					// 支付金额=当前支付金额-代金券金额
					tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(voucherAmount));
				}
				tmpOrder.setVouchers(usedVouchers);
				// +++2-5--核算运费
				BigDecimal tmpFreight = baseOrderService.calculateFreightByOrder(tmpOrder, dbShippingAddress.getProvince(), dbShippingAddress.getCity());
				// 运费
				tmpOrder.setFreight(tmpFreight);
				// 订单金额=当前订单金额+运费
				tmpOrder.setOrderAmount(tmpOrder.getOrderAmount().add(tmpFreight));
				// 支付金额=当前支付金额+运费
				tmpOrder.setPayAmount(tmpOrder.getPayAmount().add(tmpFreight));

				// +++2-6使用积分抵现金额 ，积分抵扣金额大于0且确认使用积分抵现
				if (usableIntegralCashAmount.compareTo(BigDecimal.ZERO) > 0 && tmpOrder.isUseIntegralCash()) {
					// 积分抵现金额 大于或等于 支付金额
					if (usableIntegralCashAmount.compareTo(tmpOrder.getPayAmount()) >= 0) {
						tmpOrder.setIntegralCashAmount(tmpOrder.getPayAmount());
						tmpOrder.setPayAmount(BigDecimal.ZERO);
						tmpOrder.setUseIntegralCash(Boolean.TRUE);
						// 当前剩余积分抵现金额
						usableIntegralCashAmount = usableIntegralCashAmount.subtract(tmpOrder.getIntegralCashAmount());
					} // 积分抵现金额 小于支付金额
					else if (usableIntegralCashAmount.compareTo(tmpOrder.getPayAmount()) < 0) {
						tmpOrder.setIntegralCashAmount(usableIntegralCashAmount);
						tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(usableIntegralCashAmount));
						tmpOrder.setUseIntegralCash(Boolean.TRUE);
						// 当前剩余积分抵现金额
						usableIntegralCashAmount = BigDecimal.ZERO;
					}
				}

				// +++2-7使用余额抵扣 -余额大于0且确认使用余额
				if (usableBalance.compareTo(BigDecimal.ZERO) > 0 && tmpOrder.isUseBalance()) {
					// 余额 大于或等于 支付金额
					if (usableBalance.compareTo(tmpOrder.getPayAmount()) >= 0) {
						tmpOrder.setBalance(tmpOrder.getPayAmount());
						tmpOrder.setPayAmount(BigDecimal.ZERO);
						tmpOrder.setUseBalance(Boolean.TRUE);
						// 当前剩余余额
						usableBalance = usableBalance.subtract(tmpOrder.getBalance());
					} // 余额 小于支付金额
					else if (usableBalance.compareTo(tmpOrder.getPayAmount()) < 0) {
						tmpOrder.setBalance(usableBalance);
						tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(usableBalance));
						tmpOrder.setUseBalance(Boolean.TRUE);
						// 当前剩余余额
						usableBalance = BigDecimal.ZERO;
					}
				}
				// 封装订单数据
				tmpOrder.setOrderTime(new Date());
				tmpOrder.setPayInvalidTime(orderSettingService.getInvalidTimeBySetType(tmpOrder.getOrderTime(), OrderEnum.ORDER_SET_TYPE_NORMAL));
				// +++2.6--计算总单金额数据
				pageOrderVo.setOrderAmount(Optional.ofNullable(pageOrderVo.getOrderAmount()).orElse(BigDecimal.ZERO).add(tmpOrder.getOrderAmount()));
				pageOrderVo.setPayAmount(Optional.ofNullable(pageOrderVo.getPayAmount()).orElse(BigDecimal.ZERO).add(tmpOrder.getPayAmount()));
				pageOrderVo.setFreight(Optional.ofNullable(pageOrderVo.getFreight()).orElse(BigDecimal.ZERO).add(tmpOrder.getFreight()));
				pageOrderVo.setCouponAmount(
						Optional.ofNullable(pageOrderVo.getCouponAmount()).orElse(BigDecimal.ZERO).add(Optional.ofNullable(tmpOrder.getCouponAmount()).orElse(BigDecimal.ZERO)));
				pageOrderVo.setVoucherAmount(
						Optional.ofNullable(pageOrderVo.getVoucherAmount()).orElse(BigDecimal.ZERO).add(Optional.ofNullable(tmpOrder.getVoucherAmount()).orElse(BigDecimal.ZERO)));
				pageOrderVo
						.setBalance(Optional.ofNullable(pageOrderVo.getBalance()).orElse(BigDecimal.ZERO).add(Optional.ofNullable(tmpOrder.getBalance()).orElse(BigDecimal.ZERO)));
			}
		}
		// 保存订单
		List<SaleOrder> dbSaleOrders = saleOrderDao.saveAll(saveOrders);
		// 保存 订单日志
		orderLogService.batchAddByOrders(OrderEnum.ORDER_LOG_STATE_CREATE_ORDER.getCode(), dbSaleOrders.stream().toArray(SaleOrder[]::new));
		// 使用优惠券
		couponReceiveService.useCouponsByOrder(dbSaleOrders.stream().toArray(SaleOrder[]::new));
		// 删除购物车
		cartService.batchDeleteByOrder(saleOrderBo.getCartVos(), dbMember);
		// 使用库存
		stockService.useStockBySubmitOrder(dbSaleOrders);
		// 消费次数
		memberService.updateConsumeNum(dbMember.getId(), 1);
		pageOrderVo.setOrderIds(dbSaleOrders.stream().map(e -> e.getId()).collect(Collectors.toList()));
		// 如果支付金额为0 不触发支付 默认修改支付状态
		if (pageOrderVo.getPayAmount().compareTo(BigDecimal.ZERO) == 0) {
			// 默认执行支付回调方法
			this.afterBalancePayment(dbSaleOrders.stream().toArray(SaleOrder[]::new));
		}else {
			for (SaleOrder saleOrder :dbSaleOrders) {
				// 使用余额
				accountService.useBalanceByAfterPayment(saleOrder.getMember(), saleOrder.getBalance(), saleOrder);
				// 使用积分抵现
				accountService.useIntegralCashByAfterPayment(saleOrder.getMember(), saleOrder.getIntegralCashAmount(), saleOrder);
			}
		}
		return pageOrderVo;
	}

	/**
	 * 确认订单 -拼团
	 */
	@Override
	public SaleOrderVo confirmGroupOrder(SaleOrderBo saleOrderBo) {
		if (saleOrderBo == null || saleOrderBo.getMember() == null || saleOrderBo.getGroupBuyProduct() == null || saleOrderBo.getGroupOrderType() == null
				|| saleOrderBo.getQuantity() < 1) {
			LOG.error("提交数据为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 参团订单-开团单数据不能为空
		GroupBuyOrder dbOpenGroupBuy = null;
		if (OrderEnum.GROUP_ORDER_TYPE_JOIN.getCode().equals(saleOrderBo.getGroupOrderType())) {
			if (saleOrderBo.getOpenGroupBuy() == null || saleOrderBo.getOpenGroupBuy().getId() < 1) {
				LOG.error("参团订单，开团单数据为空");
				throw new BusinessException("请选择加入的拼团");
			}
			dbOpenGroupBuy = groupBuyOrderService.getById(saleOrderBo.getOpenGroupBuy().getId());
			if (dbOpenGroupBuy == null) {
				LOG.error("参团订单，开团单数据为空");
				throw new BusinessException("请选择加入的拼团");
			}
			if (PromotionEnum.GROUP_BUY_STATE_GROUP.getCode().equals(dbOpenGroupBuy.getState()) || dbOpenGroupBuy.getJoinNum() == dbOpenGroupBuy.getGroupNum()) {
				LOG.error("参团订单，开团单已成团");
				throw new BusinessException("该拼团已成团，请重新选择");
			}
			if (PromotionEnum.GROUP_BUY_STATE_INVALID.getCode().equals(dbOpenGroupBuy.getState())) {
				LOG.error("参团订单，开团订单已失效");
				throw new BusinessException("该拼团已失效，请重新选择");
			}
			// 参团人==开团人
			if (saleOrderBo.getMember().getId() == dbOpenGroupBuy.getMember().getId()) {
				LOG.error("参团订单，无法参与自己的开团");
				throw new BusinessException("无法参与自己的开团");
			}
		}
		// 获取 会员信息
		Member dbMember = memberService.getMemberById(saleOrderBo.getMember().getId());
		if (dbMember == null) {
			LOG.error("会员数据为空");
			throw new BusinessException("会员数据不能为空");
		}
		// 获取团购商品信息
		GroupBuyActivityProduct dbGroupBuyProduct = groupBuyActivityProductService.getById(saleOrderBo.getGroupBuyProduct().getId());
		if (dbGroupBuyProduct == null || dbGroupBuyProduct.getCommodity() == null || dbGroupBuyProduct.getProduct() == null) {
			LOG.error("团购商品为空");
			throw new BusinessException("团购商品不能为空");
		}
		// 检验活动库存数量
		if (dbGroupBuyProduct.getBoughtQuantity() + saleOrderBo.getQuantity() > dbGroupBuyProduct.getStockUpQuantity()) {
			LOG.error("库存不足");
			throw new BusinessException("库存不足");
		}
		// +++1计算订单的运费、优惠券、代金券等 并封装返回前端的数据
		SaleOrder pageOrder = new SaleOrder();
		// 可用余额
		BigDecimal usableBalance = Optional.ofNullable(dbMember.getAccount().getBalance()).orElse(BigDecimal.ZERO);
		// +++1-1收货地址
		ShippingAddressVo shippingAddressVo = null;
		if (saleOrderBo.getShippingAddress() != null && saleOrderBo.getShippingAddress().getId() > 0) {
			shippingAddressVo = shippingAddressService.getShippingAddressVoById(saleOrderBo.getShippingAddress().getId());
		} else {
			// 查询会员的默认收货地址 如果没有查询最近添加的收货地址
			shippingAddressVo = shippingAddressService.getDefaultAddressVoByMember(dbMember.getId());
		}
		if (shippingAddressVo != null) {
			pageOrder.setConsignee(shippingAddressVo.getFullName());
			pageOrder.setConsigneePhone(shippingAddressVo.getPhone());
			pageOrder.setConsigneeAddr(shippingAddressVo.getProvince() + shippingAddressVo.getCity() + shippingAddressVo.getDistrict() + shippingAddressVo.getAddress());
		}
		// +++2 封装订单数据
		// +++2-1支付金额
		BigDecimal payAmount = dbGroupBuyProduct.getGroupBuyPrice().multiply(BigDecimal.valueOf(saleOrderBo.getQuantity()));
		// +++2-2封装订单项数据
		SaleOrderItem pageOrderItem = new SaleOrderItem();
		pageOrderItem.setMember(dbMember);
		pageOrderItem.setSupplier(dbGroupBuyProduct.getCommodity().getSupplier());
		pageOrderItem.setCommodity(dbGroupBuyProduct.getCommodity());
		pageOrderItem.setProduct(dbGroupBuyProduct.getProduct());
		pageOrderItem.setPrice(dbGroupBuyProduct.getGroupBuyPrice());
		pageOrderItem.setQuantity(saleOrderBo.getQuantity());
		pageOrderItem.setSubtotal(payAmount);
		// +++2-3封装订单数据
		pageOrder.getSaleOrderItems().add(pageOrderItem);
		pageOrder.setOrderType(OrderEnum.ORDER_TYPE_GROUP.getCode());
		pageOrder.setOrderAmount(payAmount);
		pageOrder.setPayAmount(payAmount);
		pageOrder.setFreight(BigDecimal.ZERO);
		pageOrder.setCouponAmount(BigDecimal.ZERO);
		pageOrder.setVoucherAmount(BigDecimal.ZERO);
		pageOrder.setBalance(BigDecimal.ZERO);
		// +++2-4 如果团购支持优惠券 计算优惠券
		if (PromotionEnum.COUPON_DEDUCTION_YES.getCode().equals(dbGroupBuyProduct.getGroupBuyActivity().getHasCoupon())) {
			// 查询可用的优惠券
			List<CouponReceive> usableCoupons = couponReceiveService.getMemberCoupons(dbMember.getId(), pageOrder);
			// 查询可用的代金券
			List<CouponReceive> usableVouchers = couponReceiveService.getMemberVouchers(dbMember.getId());
			// 锁定过的优惠券
			Set<CouponReceive> lockCoupons = new HashSet<>(0);
			// 锁定过的代金券
			Set<CouponReceive> lockVouchers = new HashSet<>(0);
			// ++2-4-2-----计算适合的优惠券
			List<CouponReceive> conformCoupons = baseOrderService.calculateCoupons(pageOrder, lockCoupons, usableCoupons);
			List<CouponReceive> useCoupons = conformCoupons.stream().filter(e -> e.isChecked()).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(useCoupons)) {
				// 添加到锁定优惠券
				lockCoupons.addAll(useCoupons);
				// 从可用的优惠券中去除
				usableCoupons.removeAll(useCoupons);
				// 该笔订单可用的优惠券
				pageOrder.setCoupons(conformCoupons);
				// 优惠券金额
				BigDecimal couponAmount = useCoupons.stream().filter(e -> e.isChecked()).map(CouponReceive::getParValue).reduce(BigDecimal.ZERO, BigDecimal::add);
				pageOrder.setCouponAmount(couponAmount);
				// 支付金额=当前支付金额-优惠券金额
				pageOrder.setPayAmount(pageOrder.getPayAmount().subtract(couponAmount));
			}
			// +++ 2-4-1-----计算代金券
			// 计算适合的代金券
			List<CouponReceive> useVouchers = baseOrderService.calculateVouchers(pageOrder, lockVouchers, usableVouchers);
			if (CollectionUtils.isNotEmpty(useVouchers)) {
				// 添加到锁定代金券
				lockVouchers.addAll(useVouchers);
				// 从可用的代金券中去除
				usableVouchers.removeAll(useVouchers);
				// 该笔订单使用的代金券
				pageOrder.setVouchers(useVouchers);
				// 代金券金额
				BigDecimal voucherAmount = useVouchers.stream().filter(e -> e.isChecked()).map(CouponReceive::getParValue).reduce(BigDecimal.ZERO, BigDecimal::add);
				pageOrder.setVoucherAmount(voucherAmount);
				// 支付金额=当前支付金额-代金券金额
				pageOrder.setPayAmount(pageOrder.getPayAmount().subtract(voucherAmount));
			}
		}
		// +++2-5不包邮 计算运费
		if (PromotionEnum.FREE_TYPE_BUYER.getCode().equals(dbGroupBuyProduct.getGroupBuyActivity().getHasPost())) {
			if (shippingAddressVo != null) {
				BigDecimal freightAmount = baseOrderService.calculateFreightByOrder(pageOrder, shippingAddressVo.getProvince(), shippingAddressVo.getCity());
				pageOrder.setFreight(freightAmount);
				// 支付金额=当前支付金额+运费
				pageOrder.setPayAmount(pageOrder.getPayAmount().add(freightAmount));
				// 订单金额=当前订单金额+运费
				pageOrder.setOrderAmount(pageOrder.getOrderAmount().add(freightAmount));
			}
		}
		// +++2-6使用余额抵扣 -余额大于0
		if (usableBalance.compareTo(BigDecimal.ZERO) > 0) {
			// 余额 大于或等于 支付金额
			if (usableBalance.compareTo(pageOrder.getPayAmount()) >= 0) {
				pageOrder.setBalance(pageOrder.getPayAmount());
				pageOrder.setPayAmount(BigDecimal.ZERO);
				pageOrder.setUseBalance(Boolean.TRUE);
				// 当前剩余余额
				usableBalance = usableBalance.subtract(pageOrder.getBalance());
			} // 余额 小于支付金额
			else if (usableBalance.compareTo(pageOrder.getPayAmount()) < 0) {
				pageOrder.setBalance(usableBalance);
				pageOrder.setPayAmount(pageOrder.getPayAmount().subtract(usableBalance));
				pageOrder.setUseBalance(Boolean.TRUE);
				// 当前剩余余额
				usableBalance = BigDecimal.ZERO;
			}
		}
		// 封装返回到前台的数据
		SaleOrderVo pageOrderVo = orderConvert.toVo(pageOrder);
		pageOrderVo.setShippingAddress(shippingAddressVo);
		pageOrderVo.setGroupBuyProductId(dbGroupBuyProduct.getId());
		pageOrderVo.setOpenGroupBuyId(saleOrderBo.getOpenGroupBuy().getId());
		pageOrderVo.setAccountBalance(Optional.ofNullable(dbMember.getAccount().getBalance()).orElse(BigDecimal.ZERO));
		pageOrderVo.setIntegralAmount(baseOrderService.getUsableIntegralCashAmount(dbMember));
		return pageOrderVo;
	}

	/**
	 * 提交订单 -拼团
	 *
	 * @param saleOrderBo
	 * @return
	 */
	@Override
	public SaleOrderVo submitGroupOrder(SaleOrderBo saleOrderBo) {
		if (saleOrderBo == null || saleOrderBo.getMember() == null || saleOrderBo.getShippingAddress() == null || saleOrderBo.getGroupBuyProduct() == null
				|| CollectionUtils.isEmpty(saleOrderBo.getSaleOrderItems()) || saleOrderBo.getGroupOrderType() == null) {
			LOG.error("提交数据为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 参团订单-开团单数据不能为空
		GroupBuyOrder dbOpenGroupBuy = null;
		if (OrderEnum.GROUP_ORDER_TYPE_JOIN.getCode().equals(saleOrderBo.getGroupOrderType())) {
			if (saleOrderBo.getOpenGroupBuy() == null || saleOrderBo.getOpenGroupBuy().getId() < 1) {
				LOG.error("参团订单，开团单数据为空");
				throw new BusinessException("请选择加入的拼团");
			}
			dbOpenGroupBuy = groupBuyOrderService.getById(saleOrderBo.getOpenGroupBuy().getId());
			if (dbOpenGroupBuy == null) {
				LOG.error("参团订单，开团单数据为空");
				throw new BusinessException("请选择加入的拼团");
			}
			if (PromotionEnum.GROUP_BUY_STATE_GROUP.getCode().equals(dbOpenGroupBuy.getState()) || dbOpenGroupBuy.getJoinNum() == dbOpenGroupBuy.getGroupNum()) {
				LOG.error("参团订单，开团单已成团");
				throw new BusinessException("该拼团已成团，请重新选择");
			}
			if (PromotionEnum.GROUP_BUY_STATE_INVALID.getCode().equals(dbOpenGroupBuy.getState())) {
				LOG.error("参团订单，开团订单已失效");
				throw new BusinessException("该拼团已失效，请重新选择");
			}
			// 参团人==开团人
			if (saleOrderBo.getMember().getId() == dbOpenGroupBuy.getMember().getId()) {
				LOG.error("参团订单，无法参与自己的开团");
				throw new BusinessException("无法参与自己的开团");
			}
		}
		// 获取 会员信息
		Member dbMember = memberService.getMemberById(saleOrderBo.getMember().getId());
		if (dbMember == null) {
			LOG.error("会员数据为空");
			throw new BusinessException("会员数据不能为空");
		}
		// 收货地址
		ShippingAddress dbShippingAddress = shippingAddressService.getShippingAddressById(saleOrderBo.getShippingAddress().getId());
		if (dbShippingAddress == null) {
			LOG.error("收货地址为空");
			throw new BusinessException("请选择收货地址");
		}
		// 获取团购商品信息
		GroupBuyActivityProduct dbGroupBuyProduct = groupBuyActivityProductService.getById(saleOrderBo.getGroupBuyProduct().getId());
		if (dbGroupBuyProduct == null || dbGroupBuyProduct.getCommodity() == null || dbGroupBuyProduct.getProduct() == null) {
			LOG.error("团购商品为空");
			throw new BusinessException("团购商品不能为空");
		}
		// +++1 计算需要支付的金额 并保存订单
		// 需要保存的订单
		SaleOrder saleOrder = orderConvert.toEntity(saleOrderBo);
		// 可用余额
		BigDecimal usableBalance = Optional.ofNullable(dbMember.getAccount().getBalance()).orElse(BigDecimal.ZERO);
		// +++2 遍历订单项 -整理 订单数据和订单项数据
		// 清空前台传入金额 从新计算订单金额
		saleOrder.setOrderAmount(BigDecimal.ZERO);
		saleOrder.setPayAmount(BigDecimal.ZERO);
		saleOrder.setFreight(BigDecimal.ZERO);
		saleOrder.setCouponAmount(BigDecimal.ZERO);
		saleOrder.setVoucherAmount(BigDecimal.ZERO);
		saleOrder.setBalance(BigDecimal.ZERO);
		saleOrder.setCostAmount(BigDecimal.ZERO);
		// +++2-1遍历订单项
		for (SaleOrderItem tmpItem : saleOrder.getSaleOrderItems()) {
			if (tmpItem != null) {
				if (tmpItem.getProduct() == null) {
					LOG.error("货品为空");
					throw new BusinessException("商品数据不能为空");
				}
				// 核验货品
				Product dbProduct = productService.checkProductForOrder(tmpItem.getProduct().getId());
				if (dbProduct == null) {
					LOG.error("货品为空");
					throw new BusinessException("商品数据不能为空");
				}
				// 检查是否在销售区域内
				boolean flag = baseOrderService.checkSaleRegion(dbProduct.getCommodity(), dbShippingAddress);
				if (!flag) {
					LOG.error("不在销售地区内");
					throw new BusinessException(dbProduct.getProductShortName() + " 不在销售范围内，请重新选择");
				}
				// 检验活动库存数量
				if (dbGroupBuyProduct.getBoughtQuantity() + tmpItem.getQuantity() > dbGroupBuyProduct.getStockUpQuantity()) {
					LOG.error("库存不足");
					throw new BusinessException(dbGroupBuyProduct.getProduct().getProductShortName() + " 库存不足");
				}
				// 订单项 数据
				tmpItem.setSaleOrder(saleOrder);
				tmpItem.setMember(dbMember);
				tmpItem.setSupplier(dbProduct.getCommodity().getSupplier());
				tmpItem.setCommodity(dbProduct.getCommodity());
				tmpItem.setProduct(dbProduct);
				// 小计金额= 价格*数量
				BigDecimal subTotal = dbGroupBuyProduct.getGroupBuyPrice().multiply(BigDecimal.valueOf(tmpItem.getQuantity()));
				// 成本
				BigDecimal costAmount = Optional.ofNullable(dbProduct.getSupplyPrice()).orElse(BigDecimal.ZERO).multiply(BigDecimal.valueOf(tmpItem.getQuantity()));
				tmpItem.setPrice(dbGroupBuyProduct.getGroupBuyPrice());// 修改为计算后的价格
				tmpItem.setSupplyPrice(dbProduct.getSupplyPrice());// 供货价
				tmpItem.setSubtotal(subTotal);// 小计
				tmpItem.setCostAmount(costAmount);// 成本
				// +++订单 供应商
				saleOrder.setSupplier(dbProduct.getCommodity().getSupplier());
				saleOrder.setSupplierName(dbProduct.getCommodity().getSupplier().getSupplierName());
				// 订单金额
				saleOrder.setOrderAmount(subTotal);
				saleOrder.setPayAmount(subTotal);
				saleOrder.setCostAmount(costAmount);
			}
		}
		// +++2-2 订单数据
		saleOrder.setMember(dbMember);
		saleOrder.setCommunity(dbMember.getCommunity());
		saleOrder.setOrderNo(NumberGenerateUtils.generateOrderNo());
		saleOrder.setPayOrderNo(ValueUtils.generateGUID());
		saleOrder.setOrderType(OrderEnum.ORDER_TYPE_GROUP.getCode());
		saleOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_PAY.getCode());
		saleOrder.setGroupState(PromotionEnum.GROUP_BUY_STATE_WAIT_PAY.getCode());
		// 收货地址
		saleOrder.setConsignee(dbShippingAddress.getFullName());
		saleOrder.setConsigneePhone(dbShippingAddress.getPhone());
		saleOrder.setConsigneeAddr(dbShippingAddress.getProvince() + dbShippingAddress.getCity() + dbShippingAddress.getDistrict() + dbShippingAddress.getAddress());
		saleOrder.setDeliveryMode("快递配送");
		// 运费不参与优惠券计算
		// +++2-4--核算优惠券
		List<CouponReceive> usedCoupons = baseOrderService.checkCoupons(saleOrder, saleOrder.getCoupons());
		if (CollectionUtils.isNotEmpty(usedCoupons)) {
			// 优惠券金额
			BigDecimal couponAmount = usedCoupons.stream().map(CouponReceive::getParValue).reduce(BigDecimal.ZERO, BigDecimal::add);
			saleOrder.setCouponAmount(couponAmount);
			// 支付金额=当前支付金额-优惠券金额
			saleOrder.setPayAmount(saleOrder.getPayAmount().subtract(couponAmount));
		}
		saleOrder.setCoupons(usedCoupons);
		// +++2-3--核算代金券
		List<CouponReceive> usedVouchers = baseOrderService.checkVouchers(saleOrder, saleOrder.getVouchers());
		if (CollectionUtils.isNotEmpty(usedVouchers)) {
			// 代金券金额
			BigDecimal voucherAmount = usedVouchers.stream().map(CouponReceive::getParValue).reduce(BigDecimal.ZERO, BigDecimal::add);
			saleOrder.setVoucherAmount(voucherAmount);
			// 支付金额=当前支付金额-代金券金额
			saleOrder.setPayAmount(saleOrder.getPayAmount().subtract(voucherAmount));
		}
		saleOrder.setVouchers(usedVouchers);
		// +++2-5--核算运费
		BigDecimal tmpFreight = baseOrderService.calculateFreightByOrder(saleOrder, dbShippingAddress.getProvince(), dbShippingAddress.getCity());
		// 运费
		saleOrder.setFreight(tmpFreight);
		// 订单金额=当前订单金额+运费
		saleOrder.setOrderAmount(saleOrder.getOrderAmount().add(tmpFreight));
		// 支付金额=当前支付金额+运费
		saleOrder.setPayAmount(saleOrder.getPayAmount().add(tmpFreight));
		// +++2-6使用余额抵扣 -余额大于0 且选择余额抵扣
		if (usableBalance.compareTo(BigDecimal.ZERO) > 0 && saleOrder.isUseBalance()) {
			// 余额 大于或等于 支付金额
			if (usableBalance.compareTo(saleOrder.getPayAmount()) >= 0) {
				saleOrder.setBalance(saleOrder.getPayAmount());
				saleOrder.setPayAmount(BigDecimal.ZERO);
				saleOrder.setUseBalance(Boolean.TRUE);
				// 当前剩余余额
				usableBalance = usableBalance.subtract(saleOrder.getBalance());
			} // 余额 小于支付金额
			else if (usableBalance.compareTo(saleOrder.getPayAmount()) < 0) {
				saleOrder.setBalance(usableBalance);
				saleOrder.setPayAmount(saleOrder.getPayAmount().subtract(usableBalance));
				saleOrder.setUseBalance(Boolean.TRUE);
				// 当前剩余余额
				usableBalance = BigDecimal.ZERO;
			}
		}
		saleOrder.setOrderTime(new Date());
		saleOrder.setPayInvalidTime(orderSettingService.getInvalidTimeBySetType(saleOrder.getOrderTime(), OrderEnum.ORDER_SET_TYPE_SECKILL));
		// 保存订单
		SaleOrder dbSaleOrder = saleOrderDao.save(saleOrder);
		// 保存参团单
		if (OrderEnum.GROUP_ORDER_TYPE_JOIN.getCode().equals(saleOrderBo.getGroupOrderType())) {
			groupBuyOrderService.addJoinGroupByOrder(dbMember, dbGroupBuyProduct, dbSaleOrder, dbOpenGroupBuy);
		} // 保存开团单
		else {
			groupBuyOrderService.addOpenGroupByOrder(dbMember, dbGroupBuyProduct, dbSaleOrder);
		}
		// 保存 订单日志
		orderLogService.batchAddByOrders(OrderEnum.ORDER_LOG_STATE_CREATE_ORDER.getCode(), dbSaleOrder);
		// 使用优惠券
		couponReceiveService.useCouponsByOrder(dbSaleOrder);
		// 消费次数
		memberService.updateConsumeNum(dbMember.getId(), 1);
		// 如果支付金额为0 默认不触发支付 自动修改订单状态
		if (dbSaleOrder.getPayAmount().compareTo(BigDecimal.ZERO) != 0) {
			this.afterBalancePayment(dbSaleOrder);
		}
		// 返回前端数据
		SaleOrderVo pageOrderVo = orderConvert.toVo(dbSaleOrder);
		pageOrderVo.getOrderIds().add(dbSaleOrder.getId());
		return pageOrderVo;
	}

	/**
	 * 余额支付后 修改订单状态
	 * 
	 * @param saleOrders
	 */
	@Transactional
	public void afterBalancePayment(SaleOrder... saleOrders) {
		if (ArrayUtils.isNotEmpty(saleOrders)) {
			for (SaleOrder tmpOrder : saleOrders) {
				// 1.礼物订单
				if (OrderEnum.ORDER_TYPE_GIFT.getCode().equals(tmpOrder.getOrderType())) {
					// 订单状态 已完成
					tmpOrder.setOrderState(OrderEnum.ORDER_STATE_COMPLETED.getCode());
					giftBagService.updateByAfterPay(tmpOrder);
				} // 2.团购订单
				else if (OrderEnum.ORDER_TYPE_GROUP.getCode().equals(tmpOrder.getOrderType())) {
					// 订单状态 待发货
					tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());
					// 修改开/参团单状态
					if (OrderEnum.GROUP_ORDER_TYPE_OPEN.getCode().equals(tmpOrder.getGroupOrderType())) {
						groupBuyOrderService.updateOpenGroupByAfterPay(tmpOrder);
					} else if (OrderEnum.GROUP_ORDER_TYPE_JOIN.getCode().equals(tmpOrder.getGroupOrderType())) {
						groupBuyOrderService.updateJoinGroupByAfterPay(tmpOrder);
					}
				} // 3.普通订单
				else {
					tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());// 订单状态 待发货
				}
				// 修正订单状态
				tmpOrder.setPayTradeNo("");// 微信支付订单号
				tmpOrder.setTradeType("余额");// 交易类型JSAPI、NATIVE、APP
				tmpOrder.setPayMode(OrderEnum.PAY_MODE_BALANCE.getCode());
				tmpOrder.setPaymentTime(new Date());
				tmpOrder.setPaymentChannel(OrderEnum.PAYMENT_CHANNEL_BALANCE.getCode());
				tmpOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_CAN_APPLY.getCode());
				tmpOrder.setRemark("支付完成");
				// 库存修正+库存记录
				stockService.useStockByPayOrder(tmpOrder);
				// 支付记录
				payRecordService.addPayRecordByOrderForBalance(tmpOrder);
				// 订单记录
				orderLogService.addLogByOrder(tmpOrder, OrderEnum.ORDER_LOG_STATE_PAY_SUCCESS, "支付成功");
				// 会员资金账户记录
				if (OrderEnum.ORDER_TYPE_GIFT.getCode().equals(tmpOrder.getOrderType())) {
					accountRecordService.addAccountRecordByTradeType(tmpOrder, tmpOrder.getMember(), tmpOrder.getPayAmount(), MemberEnum.TRADE_TYPE_GIFT_PAYMENT,
							tmpOrder.getMember());
				} else {
					accountRecordService.addAccountRecordByTradeType(tmpOrder, tmpOrder.getMember(), tmpOrder.getPayAmount(), MemberEnum.TRADE_TYPE_ONLINE_PAYMENT,
							tmpOrder.getMember());
				}
				// 供应商账户
				supplierAccountService.updateSupplierAccountByPayOrder(tmpOrder);
				// 分步发放优惠券
				couponReceiveService.grantVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_ORDER);
				// 解冻代金券
				couponReceiveService.thawVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_ORDER);

				// 使用余额
				accountService.useBalanceByAfterPayment(tmpOrder.getMember(), tmpOrder.getBalance(), tmpOrder);
				// 使用积分抵现
				accountService.useIntegralCashByAfterPayment(tmpOrder.getMember(), tmpOrder.getIntegralCashAmount(), tmpOrder);

			}
		}

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SaleOrderListVo> getSaleOrdersByCommunity(Integer communityId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查询 等待对账的订单
	 */
	@Override
	public List<SaleOrder> getWaitCheckOrders() {
		return saleOrderDao.findByOrderStateAndAfterSaleStateAndCheckStateAndDeleted(OrderEnum.ORDER_STATE_COMPLETED.getCode(), OrderEnum.AFTER_SALE_STATE_EXPIRE.getCode(),
				OrderEnum.CHECK_STATE_WAIT.getCode(), Deleted.DEL_FALSE);
	}

	/**
	 * 批量 修正订单的对账状态
	 */
	@Override
	public void updateOrderCheckState(Collection<SaleOrder> saleOrders) {
		if (CollectionUtils.isNotEmpty(saleOrders)) {
			saleOrders.forEach(tmpOrder -> {
				if (OrderEnum.CHECK_STATE_WAIT.getCode().equals(tmpOrder.getCheckState())) {
					tmpOrder.setCheckState(OrderEnum.CHECK_STATE_ALREADY.getCode());
				}
			});
		}
	}

	/**
	 * 供应商销售合计
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierSaleStatsVo getSupplierSaleTotal(SupplierSaleStatsVo supplierSaleStats) {
		// 获取查询
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<SaleOrder> root = criteriaQuery.from(SaleOrder.class);

		// 设置查询结果
		criteriaQuery.multiselect(criteriaBuilder.countDistinct(root.get(SaleOrder_.id)), criteriaBuilder.sum(root.get(SaleOrder_.orderAmount)),
				criteriaBuilder.sum(root.get(SaleOrder_.costAmount)), criteriaBuilder.sum(root.get(SaleOrder_.freight)));

		// 设置查询条件
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
		predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.orderState), OrderEnum.ORDER_STATE_COMPLETED.getCode())));
		if (supplierSaleStats != null) {
			if (StringUtils.isNotBlank(supplierSaleStats.getStartTime())) {
				predicates.add(criteriaBuilder
						.and(criteriaBuilder.greaterThanOrEqualTo(root.get(SaleOrder_.createTime), DateUtils.parseDate(supplierSaleStats.getStartTime(), "yyyy-MM-dd HH:mm:ss"))));
			}
			if (StringUtils.isNotBlank(supplierSaleStats.getEndTime())) {
				predicates.add(criteriaBuilder
						.and(criteriaBuilder.lessThanOrEqualTo(root.get(SaleOrder_.createTime), DateUtils.parseDate(supplierSaleStats.getEndTime(), "yyyy-MM-dd HH:mm:ss"))));
			}
			if (StringUtils.isNotBlank(supplierSaleStats.getSupplierName())) {
				predicates.add(criteriaBuilder.like(root.get(SaleOrder_.supplierName), "%" + supplierSaleStats.getSupplierName().trim() + "%"));
			}
		}
		criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));

		// 执行查询并处理结果
		Object[] result = entityManager.createQuery(criteriaQuery).getSingleResult();
		SupplierSaleStatsVo supplierSaleStatsVo = new SupplierSaleStatsVo();
		if (ArrayUtils.isNotEmpty(result)) {
			supplierSaleStatsVo.setOrderNum(Integer.valueOf(String.valueOf(Optional.ofNullable(result[0]).orElse("0"))).intValue());
			supplierSaleStatsVo.setSaleAmount(new BigDecimal(String.valueOf(Optional.ofNullable(result[1]).orElse(BigDecimal.ZERO))));
			supplierSaleStatsVo.setCostAmount(new BigDecimal(String.valueOf(Optional.ofNullable(result[2]).orElse(BigDecimal.ZERO))));
			supplierSaleStatsVo.setFreightAmount((new BigDecimal(String.valueOf(Optional.ofNullable(result[3]).orElse(BigDecimal.ZERO)))));
			supplierSaleStatsVo.setProfitAmount(supplierSaleStatsVo.getSaleAmount().subtract(supplierSaleStatsVo.getCostAmount()));
		} else {
			supplierSaleStatsVo.setSaleAmount(BigDecimal.ZERO);
			supplierSaleStatsVo.setCostAmount(BigDecimal.ZERO);
			supplierSaleStatsVo.setProfitAmount(BigDecimal.ZERO);
		}
		return supplierSaleStatsVo;
	}

	/**
	 * 供应商销售统计
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierSaleStatsVo> querySupplierSaleList(Pagination<SupplierSaleStatsVo> pageQuery) {
		// 获取查询
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<SaleOrder> root = criteriaQuery.from(SaleOrder.class);

		// 设置查询结果
		criteriaQuery.multiselect(root.get(SaleOrder_.supplier).get(Supplier_.id), root.get(SaleOrder_.supplierName), criteriaBuilder.countDistinct(root.get(SaleOrder_.id)),
				criteriaBuilder.sum(root.get(SaleOrder_.orderAmount)), criteriaBuilder.sum(root.get(SaleOrder_.costAmount)), criteriaBuilder.sum(root.get(SaleOrder_.freight)));

		// 设置查询条件
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
		predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.orderState), OrderEnum.ORDER_STATE_COMPLETED.getCode())));
		if (pageQuery.getParams() != null) {
			Object startTime = pageQuery.getParams().get("startTime");
			if (startTime != null) {
				predicates.add(criteriaBuilder
						.and(criteriaBuilder.greaterThanOrEqualTo(root.get(SaleOrder_.createTime), DateUtils.parseDate(startTime.toString(), "yyyy-MM-dd HH:mm:ss"))));
			}
			Object endTime = pageQuery.getParams().get("endTime");
			if (endTime != null) {
				predicates.add(
						criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get(SaleOrder_.createTime), DateUtils.parseDate(endTime.toString(), "yyyy-MM-dd HH:mm:ss"))));
			}
			Object supplierName = pageQuery.getParams().get("supplierName");
			if (supplierName != null) {
				predicates.add(criteriaBuilder.like(root.get(SaleOrder_.supplierName), "%" + supplierName.toString().trim() + "%"));
			}
		}
		criteriaQuery.where(predicates.stream().toArray(Predicate[]::new));

		// 设置分组统计条件
		criteriaQuery.groupBy(root.get(SaleOrder_.supplier).get(Supplier_.id), root.get(SaleOrder_.supplierName));

		// 设置排序条件
		criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(root.get(SaleOrder_.orderAmount))));

		// 总条数
		List<Object[]> countList = entityManager.createQuery(criteriaQuery).getResultList();
		// 分页查询数据
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		// 设置分页
		typedQuery.setFirstResult(pageQuery.getPage() - 1);
		typedQuery.setMaxResults(pageQuery.getPageSize());

		// 执行查询并处理结果
		List<Object[]> dataList = typedQuery.getResultList();
		List<SupplierSaleStatsVo> returnList = new ArrayList<>(0);
		if (CollectionUtils.isNotEmpty(dataList)) {
			for (Object[] result : dataList) {
				if (ArrayUtils.isNotEmpty(result)) {
					SupplierSaleStatsVo tmp = new SupplierSaleStatsVo();
					tmp.setSupplierId(Integer.valueOf(String.valueOf(Optional.ofNullable(result[0]).orElse("0"))).intValue());
					tmp.setSupplierName(Optional.ofNullable(result[1]).orElse("").toString());
					tmp.setOrderNum(Integer.valueOf(String.valueOf(Optional.ofNullable(result[2]).orElse("0"))).intValue());
					tmp.setSaleAmount(new BigDecimal(String.valueOf(Optional.ofNullable(result[3]).orElse("0.00"))));
					tmp.setCostAmount(new BigDecimal(String.valueOf(Optional.ofNullable(result[4]).orElse("0.00"))));
					tmp.setFreightAmount(new BigDecimal(String.valueOf(Optional.ofNullable(result[5]).orElse("0.00"))));
					tmp.setProfitAmount(tmp.getSaleAmount().subtract(tmp.getCostAmount()));
					returnList.add(tmp);
				}
			}
		}
		return new PageImpl<SupplierSaleStatsVo>(returnList, pageQuery.getPageRequest(), Optional.ofNullable(countList).map(tmp -> tmp.size()).orElse(0));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SaleOrder> getExportOrders(Pagination<SaleOrder> query) {
		query.setEntityClazz(SaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SaleOrder_.orderTime)));
			Object commodityName = query.getParams().get("commodityName");
			if (commodityName != null) {
				ListJoin<SaleOrder, SaleOrderItem> orderItemJoin = root.join(root.getModel().getDeclaredList("saleOrderItems", SaleOrderItem.class), JoinType.LEFT);
				list.add(criteriaBuilder
						.and(criteriaBuilder.like(orderItemJoin.get(SaleOrderItem_.product).get(Product_.productShortName), "%" + commodityName.toString().trim() + "%")));
			}
		}));
		List<SaleOrder> saleOrders = saleOrderDao.findAll(query);
		return saleOrders;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SaleOrder> getExportOrdersForSupplier(Pagination<SaleOrder> query) {
		query.setEntityClazz(SaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SaleOrder_.orderTime)));
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.supplier).get(Supplier_.id), supplierId));
			} else {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.supplier).get(Supplier_.id), null));
			}
			Object commodityName = query.getParams().get("commodityName");
			if (commodityName != null) {
				ListJoin<SaleOrder, SaleOrderItem> orderItemJoin = root.join(root.getModel().getDeclaredList("saleOrderItems", SaleOrderItem.class), JoinType.LEFT);
				list.add(criteriaBuilder
						.and(criteriaBuilder.like(orderItemJoin.get(SaleOrderItem_.product).get(Product_.productShortName), "%" + commodityName.toString().trim() + "%")));
			}
		}));
		List<SaleOrder> saleOrders = saleOrderDao.findAll(query);
		return saleOrders;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getInviteSuccessNum(List<Integer> memberIds) {
		if (CollectionUtils.isNotEmpty(memberIds)) {
			return saleOrderDao.countInviteNum(OrderEnum.ORDER_STATE_COMPLETED.getCode(), Deleted.DEL_FALSE, memberIds);
		}
		return 0;
	}

	/**
	 * 获取邀请会员的订单
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SaleOrder> getInviteMemberOrders(List<Integer> memberIds) {
		return saleOrderDao.findInviteMemberOrders(OrderEnum.ORDER_STATE_COMPLETED.getCode(), Deleted.DEL_FALSE, memberIds);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, Long> getOrderNum() {
		Map<String, Long> orderNumMap = new HashMap<>();
		orderNumMap.put("waitPayNum", 0L);
		orderNumMap.put("waitDeliveryNum", 0L);
		orderNumMap.put("waitReceiptNum", 0L);
		orderNumMap.put("alreadyFinishNum", 0L);
		orderNumMap.put("alreadyCloseNum", 0L);
		orderNumMap.put("afterSaleNum", 0L);
		List<Object[]> orderNums = saleOrderDao.countOrderNum();
		if (CollectionUtils.isNotEmpty(orderNums)) {
			for (Object[] object : orderNums) {
				if (ArrayUtils.isNotEmpty(object)) {
					long num = Long.valueOf(object[1].toString()).longValue();
					if (OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(object[0])) {
						orderNumMap.put("waitPayNum", num);
					} else if (OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode().equals(object[0])) {
						orderNumMap.put("waitDeliveryNum", num);
					} else if (OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode().equals(object[0])) {
						orderNumMap.put("waitReceiptNum", num);
					} else if (OrderEnum.ORDER_STATE_COMPLETED.getCode().equals(object[0])) {
						orderNumMap.put("alreadyFinishNum", num);
					} else if (OrderEnum.ORDER_STATE_CLOSED.getCode().equals(object[0])) {
						orderNumMap.put("alreadyCloseNum", num);
					} else if (OrderEnum.ORDER_STATE_AFTER_SALE.getCode().equals(object[0])) {
						orderNumMap.put("afterSaleNum", num);
					}
				}
			}
		}
		long total = orderNumMap.values().stream().mapToLong(tmp -> tmp).sum();
		orderNumMap.put("totalOrderNum", total);
		return orderNumMap;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, Long> getOrderNumForSupplier(Integer supplierId) {
		Map<String, Long> orderNumMap = new HashMap<>();
		orderNumMap.put("waitPayNum", 0L);
		orderNumMap.put("waitDeliveryNum", 0L);
		orderNumMap.put("waitReceiptNum", 0L);
		orderNumMap.put("alreadyFinishNum", 0L);
		orderNumMap.put("alreadyCloseNum", 0L);
		orderNumMap.put("afterSaleNum", 0L);
		orderNumMap.put("totalOrderNum", 0L);
		List<Object[]> orderNums = saleOrderDao.countOrderNumByForSupplier(supplierId);
		if (CollectionUtils.isNotEmpty(orderNums)) {
			for (Object[] object : orderNums) {
				if (ArrayUtils.isNotEmpty(object)) {
					long num = Long.valueOf(object[1].toString()).longValue();
					if (OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(object[0])) {
						orderNumMap.put("waitPayNum", num);
					} else if (OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode().equals(object[0])) {
						orderNumMap.put("waitDeliveryNum", num);
					} else if (OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode().equals(object[0])) {
						orderNumMap.put("waitReceiptNum", num);
					} else if (OrderEnum.ORDER_STATE_COMPLETED.getCode().equals(object[0])) {
						orderNumMap.put("alreadyFinishNum", num);
					} else if (OrderEnum.ORDER_STATE_CLOSED.getCode().equals(object[0])) {
						 orderNumMap.put("alreadyCloseNum", num);
					} else if (OrderEnum.ORDER_STATE_AFTER_SALE.getCode().equals(object[0])) {
						orderNumMap.put("afterSaleNum", num);
					}
				}
			}
		}
		long total = orderNumMap.values().stream().mapToLong(tmp -> tmp).sum();
		orderNumMap.put("totalOrderNum", total);
		return orderNumMap;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long countInviteNumByMemberAndInviteTime(List<Integer> inviteMemberIds, Date startTime, Date endTime) {
		if (CollectionUtils.isNotEmpty(inviteMemberIds) && startTime != null && endTime != null) {
			return saleOrderDao.countInviteNumByMember(inviteMemberIds, OrderEnum.ORDER_STATE_COMPLETED.getCode(), Deleted.DEL_FALSE, startTime, endTime);
		}
		return 0L;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkReceivePrizeForProduct(Integer memberId, Integer productId){
		long count = saleOrderDao.count((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE));
			predicates.add(criteriaBuilder.equal(root.get(SaleOrder_.member).get(Member_.id), memberId));
			ListJoin<SaleOrder, SaleOrderItem> orderItemJoin = root.join(root.getModel().getDeclaredList("saleOrderItems", SaleOrderItem.class), JoinType.LEFT);
			predicates.add(criteriaBuilder.and(criteriaBuilder.equal(orderItemJoin.get(SaleOrderItem_.product).get(Product_.id), productId)));
			return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 领取奖品
	 */
	@Override
	public SaleOrder receiveInvitePrize(Integer memberId, Integer productId, ShippingAddressBo shippingAddressBo) {
		boolean flag = checkReceivePrizeForProduct(memberId,productId);
		if (flag) {
			throw new BusinessException("您已领取过该奖品，请勿重复领取！");
		}
		Member dbMember = memberService.getMemberById(memberId);
		Product dbProduct = productService.getById(productId);
		if (dbMember == null || dbProduct == null) {
			throw new BusinessException("奖品领取失败，请联系客服处理！");
		}
		SaleOrder tmpOrder = new SaleOrder();
		tmpOrder.setMember(dbMember);
		tmpOrder.setCommunity(dbMember.getCommunity());
		tmpOrder.setSupplier(dbProduct.getSupplier());
		tmpOrder.setSupplierName(dbProduct.getSupplier().getSupplierName());
		tmpOrder.setOrderNo(NumberGenerateUtils.generateOrderNo());
		tmpOrder.setOrderType(OrderEnum.ORDER_TYPE_PRIZE.getCode());
		tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());
		// saleOrder.setCheckState(OrderEnum.CHECK_STATE_WAIT.getCode());
		// saleOrder.setSettlementState(OrderEnum.SETTLEMENT_STATE_WAIT.getCode());
		tmpOrder.setOrderAmount(BigDecimal.ZERO);
		tmpOrder.setFreight(BigDecimal.ZERO);
		tmpOrder.setVoucherAmount(BigDecimal.ZERO);
		tmpOrder.setCouponAmount(BigDecimal.ZERO);
		tmpOrder.setBalance(BigDecimal.ZERO);
		tmpOrder.setIntegralCashAmount(BigDecimal.ZERO);
		tmpOrder.setPayAmount(BigDecimal.ZERO);
		tmpOrder.setCostAmount(dbProduct.getCostPrice());
		tmpOrder.setBuyerMessage(null);
		tmpOrder.setConsignee(shippingAddressBo.getFullName());
		tmpOrder.setConsigneePhone(shippingAddressBo.getPhone());
		tmpOrder.setConsigneeAddr(shippingAddressBo.getProvince() + shippingAddressBo.getCity() + shippingAddressBo.getDistrict() + shippingAddressBo.getAddress());
		tmpOrder.setDeliveryMode("快递配送");
		tmpOrder.setOrderTime(new Date());
		tmpOrder.setPaymentTime(new Date());
		tmpOrder.setReadState(OrderEnum.READ_STATE_UNREAD.getCode());
		// 生成订单项
		SaleOrderItem tmpItem = new SaleOrderItem();
		tmpItem.setSaleOrder(tmpOrder);
		tmpItem.setMember(dbMember);
		tmpItem.setSupplier(tmpOrder.getSupplier());
		tmpItem.setCommodity(dbProduct.getCommodity());
		tmpItem.setProduct(dbProduct);
		tmpItem.setPrice(dbProduct.getCurrentPrice());
		tmpItem.setSupplyPrice(dbProduct.getSupplyPrice());
		tmpItem.setQuantity(1);
		tmpItem.setDiscount(BigDecimal.ZERO);
		tmpItem.setCostAmount(dbProduct.getCostPrice());
		tmpItem.setSubtotal(BigDecimal.ZERO);
		// 添加订单项
		tmpOrder.getSaleOrderItems().add(tmpItem);
		return saleOrderDao.save(tmpOrder);
	}

}