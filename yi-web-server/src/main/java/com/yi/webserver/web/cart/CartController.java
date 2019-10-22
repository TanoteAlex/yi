/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.webserver.web.cart;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.cart.domain.bo.CartBo;
import com.yi.core.cart.service.ICartService;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 购物车
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api(tags = "购物车控制层")
@RestController
@RequestMapping(value = "/cart")
public class CartController {

	private final Logger LOG = LoggerFactory.getLogger(CartController.class);

	@Resource
	private ICartService cartService;

	/**
	 * 获取购物车信息
	 *
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "根据会员id获取购物车信息", notes = "根据会员id获取购物车信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getShopCarts", method = RequestMethod.GET)
	public RestResult getShopCarts(@RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(cartService.getShopCarts(memberId));
		} catch (Exception e) {
			LOG.error("getShopCarts error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 添加商品进购物车
	 *
	 * @param memberId
	 * @param productId
	 * @return
	 */
	@ApiOperation(value = "添加商品进购物车", notes = "添加商品进购物车")
	@ApiImplicitParams(@ApiImplicitParam(name = "cart", value = "购物车对象", required = true, dataType = "CartBo"))
	@RequestMapping(value = "addShopCart", method = RequestMethod.POST)
	public RestResult addShopCart(@RequestBody CartBo cartBo) {
		try {
			cartService.addProductToCart(cartBo);
			return RestUtils.success(Boolean.TRUE);
		} catch (Exception e) {
			LOG.error("addShopCart error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 删除商品从购物车
	 *
	 * @param cartId
	 * @return
	 */
	@ApiOperation(value = "删除购物车", notes = "根据购物车ID删除购物车")
	@ApiImplicitParams(@ApiImplicitParam(name = "cart", value = "购物车对象", required = true, dataType = "CartBo"))
	@RequestMapping(value = "removeShopCart", method = RequestMethod.POST)
	public RestResult removeShopCart(@RequestBody CartBo cartBo) {
		try {
			cartService.removeCart(cartBo);
			return RestUtils.success(Boolean.TRUE);
		} catch (Exception e) {
			LOG.error("removeShopCart error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 修改商品数量从购物车
	 *
	 * @param cartId
	 * @return
	 */
	@ApiOperation(value = "修改购物车商品数量", notes = "修改购物车商品数量")
	@ApiImplicitParams(@ApiImplicitParam(name = "cart", value = "购物车对象", required = true, dataType = "CartBo"))
	@RequestMapping(value = "changeShopCartNum", method = RequestMethod.POST)
	public RestResult changeShopCartNum(@RequestBody CartBo cartBo) {
		try {
			cartService.changeShopCartNum(cartBo);
			return RestUtils.success(Boolean.TRUE);
		} catch (Exception e) {
			LOG.error("change ShopCart number error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

}