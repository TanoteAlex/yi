package com.yi.webserver.web.commodity;

import javax.annotation.Resource;

import com.yi.core.commodity.domain.simple.ProductSimple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.cms.service.IOperateCategoryService;
import com.yi.core.commodity.domain.entity.Comment;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.vo.CommentListVo;
import com.yi.core.commodity.domain.vo.CommodityListVo;
import com.yi.core.commodity.domain.vo.CommodityVo;
import com.yi.core.commodity.domain.vo.ProductVo;
import com.yi.core.commodity.service.ICommentService;
import com.yi.core.commodity.service.ICommodityService;
import com.yi.core.commodity.service.IProductService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 商品
 */
@Api(tags = "商品控制层")
@RestController
@RequestMapping("/commodity")
public class CommodityController {

	private final Logger LOG = LoggerFactory.getLogger(CommodityController.class);

	@Resource
	private ICommodityService commodityService;

	@Resource
	private ICommentService commentService;

	@Resource
	private IOperateCategoryService operateCategoryService;

	@Resource
	private IProductService productService;

	/**
	 * 分页查询 各个地方的商品列表查询 包括人气 首页
	 */
	@ApiOperation(value = "获取各个地方的商品列表查询 包括人气 首页", notes = "根据分页参数查询各个地方的商品列表查询 包括人气 首页")
	@RequestMapping(value = "queryForApp", method = RequestMethod.POST)
	public Page<CommodityListVo> queryCommodity(@RequestBody Pagination<Commodity> query) {
		Page<CommodityListVo> page = commodityService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 查询商品评论
	 */
	@ApiOperation(value = "查询商品评论", notes = "根据分页查询商品评论")
	@RequestMapping(value = "queryComment", method = RequestMethod.POST)
	public Page<CommentListVo> queryComment(@RequestBody Pagination<Comment> query) {
		Page<CommentListVo> page = commentService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 获取首页底部商品加载
	 * 
	 * @param query
	 * @return
	 */
	@ApiOperation(value = "获取首页底部商品加载", notes = "根据分页获取首页底部商品加载")
	@RequestMapping(value = "queryCommoditiesForBottomLoad", method = RequestMethod.POST)
	public Page<CommodityListVo> queryCommoditiesForBottomLoad(@RequestBody Pagination<Commodity> query) {
		Page<CommodityListVo> page = commodityService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 获取商品分类 包括两级分类
	 *
	 * @return
	 */
	@ApiOperation(value = "获取商品分类 包括两级分类", notes = "获取商品分类 包括两级分类")
	@RequestMapping(value = "getCommodityCategory", method = RequestMethod.GET)
	public RestResult getCommodityCategory() {
		try {
			return RestUtils.success(operateCategoryService.getOperateCategoryListVosForApp());
		} catch (Exception e) {
			LOG.error("getCommodityCategory error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取商品详情
	 *
	 * @param commodityId
	 * @return
	 */
	@ApiOperation(value = "获取商品详情", notes = "根据商品Id获取商品详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "commodityId", value = "商品Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getCommodity", method = RequestMethod.GET)
	public RestResult getCommodity(@RequestParam("commodityId") int commodityId) {
		try {
			CommodityVo commodityVo = commodityService.getVoByIdForApp(commodityId);
			return RestUtils.successWhenNotNull(commodityVo);
		} catch (Exception e) {
			LOG.error("getCommodity error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取货品详情
	 *
	 * @param productId
	 * @return
	 */
	@ApiOperation(value = "获取货品详情", notes = "根据货品Id获取货品详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "productId", value = "货品Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getProduct", method = RequestMethod.GET)
	public RestResult getProduct(@RequestParam("productId") int productId) {
		try {
			ProductVo productVo = productService.getVoByIdForApp(productId);

			return RestUtils.successWhenNotNull(productVo);
		} catch (Exception e) {
			LOG.error("getProduct error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	// /**
	// * 获取购物车可用优惠券
	// *
	// * @param saleOrderBo
	// * @return
	// */
	// @RequestMapping(value = "getCartCoupon", method = RequestMethod.POST)
	// public RestResult getCommodityCoupon(@RequestBody SaleOrderBo saleOrderBo) {
	// try {
	// return RestUtils.success(cartService.judgeCouponReceive(saleOrderBo));
	// } catch (Exception e) {
	// LOG.error("getCartCoupon error:", e.getMessage());
	// return RestUtils.error(e.getMessage());
	// }
	// }

	// /**
	// * 查询用户可用优惠券
	// *
	// * @param orderId
	// * @param memberId
	// * @return
	// */
	// @RequestMapping(value = "availableCoupon", method = RequestMethod.GET)
	// public RestResult availableCoupon(@RequestParam("memberId") int memberId,
	// @RequestParam("orderId") int orderId) {
	// try {
	// return RestUtils.success(cartService.availableCoupon(memberId, orderId));
	// } catch (Exception e) {
	// LOG.error("availableCoupon error:", e.getMessage());
	// return RestUtils.error(e.getMessage());
	// }
	// }

}
