/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cart.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.cart.CartEnum;
import com.yi.core.cart.dao.CartDao;
import com.yi.core.cart.domain.bo.CartBo;
import com.yi.core.cart.domain.entity.Cart;
import com.yi.core.cart.domain.entity.Cart_;
import com.yi.core.cart.domain.simple.CartSimple;
import com.yi.core.cart.domain.vo.CartListVo;
import com.yi.core.cart.domain.vo.CartVo;
import com.yi.core.cart.service.ICartService;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.commodity.domain.entity.CommodityLevelDiscount;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.service.IProductService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.common.Deleted;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IMemberService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 购物车
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@CacheConfig(cacheNames = "cart")
@Service
@Transactional
public class CartServiceImpl implements ICartService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CartServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CartDao cartDao;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IStockService stockService;

	@Resource
	private IMemberService memberService;

	@Resource
	private IProductService productService;

	private EntityListVoBoSimpleConvert<Cart, CartBo, CartVo, CartSimple, CartListVo> cartConvert;

	@Override
	public Page<Cart> query(Pagination<Cart> query) {
		query.setEntityClazz(Cart.class);
		Page<Cart> page = cartDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CartListVo> queryListVo(Pagination<Cart> query) {
		query.setEntityClazz(Cart.class);
		Page<Cart> pages = cartDao.findAll(query, query.getPageRequest());
		List<CartListVo> vos = cartConvert.toListVos(pages.getContent());
		return new PageImpl<CartListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Cart getCartById(int cartId) {
		if (cartDao.existsById(cartId)) {
			return cartDao.getOne(cartId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CartVo getCartVoById(int cartId) {
		return cartConvert.toVo(this.cartDao.getOne(cartId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CartListVo getCartListVoById(int cartId) {
		return cartConvert.toListVo(this.cartDao.getOne(cartId));
	}

	@Override
	public Cart addCart(Cart cart) {
		if (cart == null || cart.getMember() == null || cart.getProduct() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return cartDao.save(cart);
	}

	@Override
	public CartListVo addCart(CartBo cartBo) {
		return cartConvert.toListVo(this.addCart(cartConvert.toEntity(cartBo)));
	}

	@Override
	public Cart updateCart(Cart cart) {
		if (cart == null || cart.getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		Cart dbCart = this.getCartById(cart.getId());
		if (dbCart != null) {
			AttributeReplication.copying(cart, dbCart, Cart_.quantity, Cart_.price, Cart_.discount, Cart_.discountInfo);
		}
		return dbCart;
	}

	@CacheEvict(key = "#cartBo.getMember().getId()")
	@Override
	public CartListVo updateCart(CartBo cartBo) {
		return cartConvert.toListVo(this.updateCart(cartConvert.toEntity(cartBo)));
	}

	@CacheEvict(allEntries = true)
	@Override
	public void removeCartById(int cartId) {
		cartDao.deleteById(cartId);
	}

	@CacheEvict(key = "'member:'+#cartBo.getMember().getId()")
	@Override
	public void removeCart(CartBo cartBo) {
		cartDao.deleteById(cartBo.getId());
	}

	@Cacheable(key = "'member:'+#memberId", unless = "#result eq null")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CartListVo> getShopCarts(int memberId) {
		List<Cart> dbCarts = cartDao.findByMember_idOrderByCreateTimeDesc(memberId);
		for (Cart tmpCart : dbCarts) {
			// 如果商品设置了会员等级价格折扣
			if (CollectionUtils.isNotEmpty(tmpCart.getProduct().getCommodity().getCommodityLevelDiscounts())) {
				for (CommodityLevelDiscount tmpDiscount : tmpCart.getProduct().getCommodity().getCommodityLevelDiscounts()) {
					if (tmpDiscount.getDiscount() != null && tmpCart.getMember().getMemberLevel().getId() == tmpDiscount.getMemberLevel().getId()) {
						tmpCart.getProduct().setVipPrice(
								tmpDiscount.getDiscount().multiply(tmpCart.getProduct().getCurrentPrice()).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP));
					}
				}
			}
			// 商品的等级折扣没有当前会员的等级 按照会员等级折扣计算
			if (tmpCart.getProduct().getVipPrice() == null) {
				tmpCart.getProduct().setVipPrice(tmpCart.getMember().getMemberLevel().getDiscount().multiply(tmpCart.getProduct().getCurrentPrice()).divide(BigDecimal.valueOf(100))
						.setScale(2, RoundingMode.HALF_UP));
			}
			tmpCart.setMember(null);
			tmpCart.getProduct().setAttributes(null);
			if (Deleted.DEL_FALSE.equals(tmpCart.getProduct().getDeleted()) && CommodityEnum.SHELF_STATE_ON.getCode().equals(tmpCart.getProduct().getCommodity().getShelfState())
					&& CommodityEnum.AUDIT_STATE_PASS.getCode().equals(tmpCart.getProduct().getCommodity().getAuditState())) {
				tmpCart.setState(CartEnum.STATE_VALID.getCode());
			} else {
				tmpCart.setState(CartEnum.STATE_INVALID.getCode());
			}
		}
		return cartConvert.toListVos(dbCarts);
	}

	/**
	 * 更新购物车数据
	 */
	@CacheEvict(key = "'member:'+#cartBo.getMember().getId()")
	@Override
	public void changeShopCartNum(CartBo cartBo) {
		if (cartBo.getNum() < 1) {
			throw new BusinessException("请选择正确的购买数量");
		}
		// 获取购物车信息
		Cart dbCart = this.getCartById(cartBo.getId());
		if (dbCart == null) {
			throw new BusinessException("系统异常，请联系客服处理");
		}
		// 核验库存
		boolean stockFlag = stockService.checkStock(dbCart.getProduct().getId(), cartBo.getNum() - dbCart.getQuantity());
		if (!stockFlag) {
			LOG.error("productId={}，库存不足", dbCart.getProduct().getId());
			throw new BusinessException(dbCart.getProduct().getProductShortName() + " 库存不足");
		}
		dbCart.setQuantity(cartBo.getNum());
	}

	/**
	 * 添加数据到购物车
	 */
	@CacheEvict(key = "'member:'+#cartBo.getMember().getId()")
	@Override
	public void addProductToCart(CartBo cartBo) {
		if (cartBo.getNum() < 1) {
			throw new BusinessException("请选择正确的购买数量");
		}
		// 校验 会员添加购物车最大数量
		int count = cartDao.countByMember_Id(cartBo.getMember().getId());
		if (count > 99) {
			throw new BusinessException("最多添加99件商品");
		}
		// 核验库存
		boolean stockFlag = stockService.checkStock(cartBo.getProduct().getId(), cartBo.getNum());
		if (!stockFlag) {
			LOG.error("productId={}，库存不足", cartBo.getProduct().getId());
			throw new BusinessException(" 库存不足");
		}
		// 校验购物车是否有该商品 有就更新物车数量 没有就新建
		Cart dbCart = cartDao.findByMember_idAndProduct_id(cartBo.getMember().getId(), cartBo.getProduct().getId());
		if (dbCart != null) {
			// 上架商品
			if (Deleted.DEL_FALSE.equals(dbCart.getProduct().getDeleted()) && CommodityEnum.SHELF_STATE_ON.getCode().equals(dbCart.getProduct().getCommodity().getShelfState())
					&& CommodityEnum.AUDIT_STATE_PASS.getCode().equals(dbCart.getProduct().getCommodity().getAuditState())) {
				dbCart.setQuantity(dbCart.getQuantity() + cartBo.getNum());
			} else {
				throw new BusinessException(dbCart.getProduct().getProductShortName() + " 已经下架");
			}
		} else {
			Product dbProduct = productService.getById(cartBo.getProduct().getId());
			if (dbProduct == null) {
				throw new BusinessException("商品不能为空");
			}
			// 上架商品
			if (Deleted.DEL_FALSE.equals(dbProduct.getDeleted()) && CommodityEnum.SHELF_STATE_ON.getCode().equals(dbProduct.getCommodity().getShelfState())
					&& CommodityEnum.AUDIT_STATE_PASS.getCode().equals(dbProduct.getCommodity().getAuditState())) {

				Member dbMember = memberService.getMemberById(cartBo.getMember().getId());
				if (dbMember == null) {
					throw new BusinessException("会员不能为空");
				}
				Cart cart = new Cart();
				cart.setMember(dbMember);
				cart.setProduct(dbProduct);
				cart.setQuantity(cartBo.getNum());
				dbCart = cartDao.save(cart);
			} else {
				throw new BusinessException(dbProduct.getProductShortName() + " 已经下架");
			}
		}
	}

	/**
	 * 下单时批量删除购物车
	 */
	@CacheEvict(key = "'member:'+#p1.getId()")
	@Override
	public void batchDeleteByOrder(List<CartVo> cartVos, Member member) {
		if (CollectionUtils.isNotEmpty(cartVos)) {
			cartVos.forEach(cartVo -> {
				this.removeCartById(cartVo.getId());
			});
		}
	}

	protected void initConvert() {
		this.cartConvert = new EntityListVoBoSimpleConvert<Cart, CartBo, CartVo, CartSimple, CartListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Cart, CartVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Cart, CartVo>(beanConvertManager) {
					@Override
					protected void postConvert(CartVo cartVo, Cart cart) {

					}
				};
			}

			@Override
			protected BeanConvert<Cart, CartListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Cart, CartListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CartListVo cartListVo, Cart cart) {
						cartListVo.setCommodityId(cart.getProduct().getCommodity().getId());
					}
				};
			}

			@Override
			protected BeanConvert<Cart, CartBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Cart, CartBo>(beanConvertManager) {
					@Override
					protected void postConvert(CartBo cartBo, Cart cart) {

					}
				};
			}

			@Override
			protected BeanConvert<CartBo, Cart> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CartBo, Cart>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Cart, CartSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Cart, CartSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CartSimple, Cart> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CartSimple, Cart>(beanConvertManager) {
					@Override
					public Cart convert(CartSimple cartSimple) throws BeansException {
						return cartDao.getOne(cartSimple.getId());
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
