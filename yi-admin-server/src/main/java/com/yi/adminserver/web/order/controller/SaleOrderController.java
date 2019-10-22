/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.order.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yi.adminserver.web.auth.jwt.JwtSupplierToken;
import com.yi.adminserver.web.auth.jwt.SupplierToken;
import com.yi.core.common.FileService;
import com.yi.core.order.domain.bo.SaleOrderBo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.vo.SaleOrderListVo;
import com.yi.core.order.domain.vo.SaleOrderVo;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 销售订单
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("订单控制层")
@RestController
@RequestMapping(value = "/saleOrder")
public class SaleOrderController {

	private final Logger LOG = LoggerFactory.getLogger(SaleOrderController.class);

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private FileService fileService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取订单列表(总后台)", notes = "根据分页参数查询订单列表(总后台)")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SaleOrderListVo> queryOrder(@RequestBody Pagination<SaleOrder> query) {
		Page<SaleOrderListVo> page = saleOrderService.queryListVo(query);
		return page;
	}

	/**
	 * 供应商分页查询
	 */
	@ApiOperation(value = "获取订单列表(供应商)", notes = "根据分页参数查询订单列表(供应商)")
	@RequestMapping(value = "queryForSupplier", method = RequestMethod.POST)
	public Page<SaleOrderListVo> queryOrderForSupplier(@RequestBody Pagination<SaleOrder> query, HttpServletRequest request) {
		SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
		query.getParams().put("supplier.id", supplierToken.getId());
		Page<SaleOrderListVo> page = saleOrderService.queryListVoForSupplier(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看订单信息(总后台)", notes = "根据订单Id获取订单信息(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "订单Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewOrder(@RequestParam("id") int orderId) {
		try {
			SaleOrderVo order = saleOrderService.getOrderVoById(orderId);
			return RestUtils.successWhenNotNull(order);
		} catch (BusinessException ex) {
			LOG.error("get Order failure : id=orderId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商查看对象
	 **/
	@ApiOperation(value = "查看订单信息(供应商)", notes = "根据订单Id获取订单信息(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "订单Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getByIdForSupplier", method = RequestMethod.GET)
	public RestResult viewOrderForSupplier(@RequestParam("id") int orderId) {
		try {
			SaleOrderVo order = saleOrderService.getOrderVoById(orderId);
			return RestUtils.successWhenNotNull(order);
		} catch (BusinessException ex) {
			LOG.error("get Order failure : id=orderId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	// /**
	// * 查看对象
	// **/
	// @RequestMapping(value = "getByGiftId", method = RequestMethod.GET)
	// public RestResult viewOrderByGiftId(@RequestParam("giftId") int giftId) {
	// try {
	// SaleOrderVo order = saleOrderService.getOrderVoByGiftId(giftId);
	// return RestUtils.successWhenNotNull(order);
	// } catch (BusinessException ex) {
	// LOG.error("get Order failure : id=orderId", ex);
	// return RestUtils.error(ex.getMessage());
	// }
	// }

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增订单", notes = "添加订单")
	@ApiImplicitParams({ @ApiImplicitParam(name = "order", value = "订单对象", required = true, dataType = "SaleOrderBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addOrder(@RequestBody SaleOrderBo order) {
		try {
			SaleOrderVo dbOrder = saleOrderService.addOrder(order);
			return RestUtils.successWhenNotNull(dbOrder);
		} catch (BusinessException ex) {
			LOG.error("add Order failure : order", order, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新订单", notes = "修改订单")
	@ApiImplicitParams({ @ApiImplicitParam(name = "order", value = "订单对象", required = true, dataType = "SaleOrderBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateOrder(@RequestBody SaleOrderBo order) {
		try {
			SaleOrderVo dbOrder = saleOrderService.updateOrder(order);
			return RestUtils.successWhenNotNull(dbOrder);
		} catch (BusinessException ex) {
			LOG.error("update Order failure : order", order, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除订单", notes = "删除订单")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "订单Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeOrderById(@RequestParam("id") int orderId) {
		try {
			saleOrderService.removeOrderById(orderId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Order failure : id=orderId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 修改订单状态
	 */
	@ApiOperation(value = "修改订单状态(总后台)", notes = "修改订单状态(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "订单Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "state", value = "订单状态", required = true, dataType = "Int") })
	@RequestMapping(value = "updateState", method = RequestMethod.GET)
	public RestResult updateOrderState(@RequestParam("id") int id, @RequestParam("state") Integer state) {
		try {
			saleOrderService.updateOrderState(id, state);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("修改订单状态异常", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商修改订单状态
	 */
	@ApiOperation(value = "修改订单状态(供应商)", notes = "修改订单状态(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "订单Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "state", value = "订单状态", required = true, dataType = "Int") })
	@RequestMapping(value = "updateStateForSupplier", method = RequestMethod.GET)
	public RestResult updateOrderStateForSupplier(@RequestParam("id") int id, @RequestParam("state") Integer state) {
		try {
			saleOrderService.updateOrderState(id, state);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("修改订单状态异常", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 修改订单地址
	 */
	@ApiOperation(value = "修改订单地址(总后台)", notes = "修改订单地址(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "orderBo", value = "订单对象", required = true, dataType = "SaleOrderBo"), })
	@RequestMapping(value = "updateProvince", method = RequestMethod.POST)
	public RestResult updateProvince(@RequestBody SaleOrderBo orderBo) {
		try {
			return RestUtils.success(saleOrderService.updateProvince(orderBo));
		} catch (Exception ex) {
			LOG.error("修改订单状态异常", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 修改订单地址
	 */
	@ApiOperation(value = "修改订单地址(供应商)", notes = "修改订单地址(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "orderBo", value = "订单对象", required = true, dataType = "SaleOrderBo"), })
	@RequestMapping(value = "updateProvinceForSupplier", method = RequestMethod.POST)
	public RestResult updateProvinceForSupplier(@RequestBody SaleOrderBo orderBo) {
		try {
			return RestUtils.success(saleOrderService.updateProvince(orderBo));
		} catch (Exception ex) {
			LOG.error("修改订单状态异常", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 修改订单金额
	 */
	@ApiOperation(value = "修改订单金额(总后台)", notes = "修改订单金额(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "orderBo", value = "订单对象", required = true, dataType = "SaleOrderBo"), })
	@RequestMapping(value = "updatePrice", method = RequestMethod.POST)
	public RestResult updatePrice(@RequestBody SaleOrderBo orderBo) {
		try {
			return RestUtils.success(saleOrderService.updatePrice(orderBo));
		} catch (Exception ex) {
			LOG.error("修改订单状态异常", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商修改订单金额
	 */
	@ApiOperation(value = "修改订单金额(供应商)", notes = "修改订单金额(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "orderBo", value = "订单对象", required = true, dataType = "SaleOrderBo"), })
	@RequestMapping(value = "updatePriceForSupplier", method = RequestMethod.POST)
	public RestResult updatePriceForSupplier(@RequestBody SaleOrderBo orderBo) {
		try {
			return RestUtils.success(saleOrderService.updatePrice(orderBo));
		} catch (Exception ex) {
			LOG.error("修改订单状态异常", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 发货
	 */
	@ApiOperation(value = "发货(总后台)", notes = "修改订单金额(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "订单Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "trackingNo", value = "快递单号", required = true, dataType = "String"),
			@ApiImplicitParam(name = "logisticsCompany", value = "物流公司", required = true, dataType = "String") })
	@RequestMapping(value = "delivery", method = RequestMethod.GET)
	public RestResult delivery(@RequestParam("id") int id, @RequestParam("trackingNo") String trackingNo, @RequestParam("logisticsCompany") String logisticsCompany) {
		try {
			return RestUtils.success(saleOrderService.delivery(id, trackingNo, logisticsCompany));
		} catch (Exception ex) {
			LOG.error("修改订单状态异常", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商发货
	 */
	@ApiOperation(value = "发货(供应商)", notes = "修改订单金额(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "订单Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "trackingNo", value = "快递单号", required = true, dataType = "String"),
			@ApiImplicitParam(name = "logisticsCompany", value = "物流公司", required = true, dataType = "String") })
	@RequestMapping(value = "deliveryForSupplier", method = RequestMethod.GET)
	public RestResult deliveryForSupplier(@RequestParam("id") int id, @RequestParam("trackingNo") String trackingNo, @RequestParam("logisticsCompany") String logisticsCompany) {
		try {
			return RestUtils.success(saleOrderService.delivery(id, trackingNo, logisticsCompany));
		} catch (Exception ex) {
			LOG.error("修改订单状态异常", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查询各订单状态数量
	 */
	@ApiOperation(value = "查询各订单状态数量", notes = "查询各订单状态数量")
	@RequestMapping(value = "getOrderNum", method = RequestMethod.GET)
	public RestResult getOrderNum(HttpServletRequest request) {
		try {
			return RestUtils.success(saleOrderService.getOrderNum());
		} catch (Exception ex) {
			LOG.error("查询各订单状态数量", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查询各订单状态数量（供应商）
	 */
	@ApiOperation(value = "查询各订单状态数量", notes = "查询各订单状态数量")
	@RequestMapping(value = "getOrderNumForSupplier", method = RequestMethod.GET)
	public RestResult getOrderNumForSupplier(HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			return RestUtils.success(saleOrderService.getOrderNumForSupplier(supplierToken.getId()));
		} catch (Exception ex) {
			LOG.error("查询各订单状态数量", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 订单导出
	 */
	@ApiOperation(value = "销售订单导出", notes = "销售订单导出")
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			String queryJson = URLDecoder.decode(request.getParameter("requests"), "UTF-8");
			JSONObject queryJsonObject = JSONObject.parseObject(queryJson);
			Pagination<SaleOrder> paginationQuery = JSONObject.toJavaObject(queryJsonObject, Pagination.class);
			List<SaleOrder> dbSaleOrders = saleOrderService.getExportOrders(paginationQuery);
			Workbook workbook = fileService.createOrderExcel(dbSaleOrders);
			fileService.exportExcel(workbook, request, response);
		} catch (Exception e) {
			LOG.error("销售订单导出异常", e);
		}
	}

	/**
	 * 订单导出
	 */
	@ApiOperation(value = "销售订单导出", notes = "销售订单导出")
	@RequestMapping(value = "exportExcelForSupplier")
	public void exportExcelForSupplier(HttpServletRequest request, HttpServletResponse response) {
		try {
			String queryJson = URLDecoder.decode(request.getParameter("requests"), "UTF-8");
			JSONObject queryJsonObject = JSONObject.parseObject(queryJson);
			Pagination<SaleOrder> paginationQuery = JSONObject.toJavaObject(queryJsonObject, Pagination.class);
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			paginationQuery.getParams().put("supplier.id", Optional.ofNullable(supplierToken).map(tmp -> tmp.getId()).orElse(null));
			List<SaleOrder> dbSaleOrders = saleOrderService.getExportOrdersForSupplier(paginationQuery);
			Workbook workbook = fileService.createOrderExcel(dbSaleOrders);
			fileService.exportExcel(workbook, request, response);
		} catch (Exception e) {
			LOG.error("销售订单导出异常", e);
		}
	}

	/**
	 * 订单导出
	 */
	@ApiOperation(value = "批量删除订单表或者订单项", notes = "批量删除订单表或者订单项")
	@RequestMapping(value = "piliangdeleteOrderTest", method = RequestMethod.POST)
	public RestResult piliangdeleteOrderTest(@RequestBody List<Integer> ids) {
		try {
			if (ids.get(0)==0){
				String biaoName = "sale_order";
				saleOrderService.piliangDeleteOrderByIdTest(biaoName,ids);
			}else if (ids.get(0)==1){
				String biaoName = "sale_order_item";
				saleOrderService.piliangDeleteOrderByIdTest(biaoName,ids);
			}
			return RestUtils.success(true);
		} catch (BusinessException ex) {
			return RestUtils.error(ex.getMessage());
		}
	}

}