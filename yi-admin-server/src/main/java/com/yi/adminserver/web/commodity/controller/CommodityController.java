/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.commodity.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.yi.core.commodity.dao.CommodityDao;
import com.yi.core.commodity.domain.entity.Attribute;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.search.restful.RestfulFactory;
import com.yi.core.search.service.SearchRestService;
import com.yi.core.search.vo.ResponseResult;
import com.yi.core.search.vo.meta.Result;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.adminserver.web.auth.jwt.JwtSupplierToken;
import com.yi.adminserver.web.auth.jwt.SupplierToken;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.vo.AttributeGroupListVo;
import com.yi.core.commodity.domain.vo.CommodityListVo;
import com.yi.core.commodity.domain.vo.CommodityVo;
import com.yi.core.commodity.service.IAttributeGroupService;
import com.yi.core.commodity.service.ICommodityService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import retrofit2.Call;

/**
 * 商品
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("商品控制层")
@RestController
@RequestMapping(value = "/commodity")
public class CommodityController {

	private final Logger LOG = LoggerFactory.getLogger(CommodityController.class);

	@Resource
	private ICommodityService commodityService;

	@Resource
	private IAttributeGroupService attributeGroupService;

	@Resource
	private ISupplierService supplierService;


	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取商品列表(总后台)", notes = "根据分页参数查询商品列表(总后台)")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CommodityListVo> queryCommodity(@RequestBody Pagination<Commodity> query) {
		//CommodityService  //keywordSearch
		Page<CommodityListVo> page = commodityService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看商品信息(总后台)", notes = "根据商品Id获取商品信息(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "商品Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewCommodity(@RequestParam("id") int commodityId) {
		try {
			CommodityVo commodity = commodityService.getVoById(commodityId);

			return RestUtils.successWhenNotNull(commodity);
		} catch (BusinessException ex) {
			LOG.error("get Commodity failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}



	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增商品(总后台)", notes = "添加商品(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "commodity", value = "商品对象", required = true, dataType = "CommodityBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCommodity(@RequestBody CommodityBo commodity) {
		try {

			CommodityVo dbCommodityVo = commodityService.addCommodity(commodity);
			return RestUtils.successWhenNotNull(dbCommodityVo);
		} catch (BusinessException ex) {
			LOG.error("add Commodity failure : {}", commodity, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新商品(总后台)", notes = "修改商品(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "commodity", value = "商品对象", required = true, dataType = "CommodityBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCommodity(@RequestBody CommodityBo commodity) {
		try {
			CommodityVo commodityVo = commodityService.updateCommodity(commodity);
			return RestUtils.successWhenNotNull(commodityVo);
		} catch (BusinessException ex) {
			LOG.error("update Commodity failure : {}", commodity, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除商品(总后台)", notes = "删除商品(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "商品Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCommodityById(@RequestParam("id") int commodityId) {
		try {
			commodityService.removeCommodityById(commodityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Commodity failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商分页查询
	 */
	@ApiOperation(value = "获取商品列表(供应商)", notes = "根据分页参数查询商品列表(供应商)")
	@RequestMapping(value = "queryForSupplier", method = RequestMethod.POST)
	public Page<CommodityListVo> queryCommodityForSupplier(@RequestBody Pagination<Commodity> query, HttpServletRequest request) {
		SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
		query.getParams().put("supplier.id", supplierToken.getId());
		Page<CommodityListVo> page = commodityService.queryListVoForSupplier(query);
		return page;
	}

	/**
	 * 供应商查看对象
	 **/
	@ApiOperation(value = "查看商品信息(供应商)", notes = "根据商品Id获取商品信息(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "商品Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getByIdForSupplier", method = RequestMethod.GET)
	public RestResult viewCommodityForSupplier(@RequestParam("id") int commodityId) {
		try {
			CommodityVo commodity = commodityService.getVoById(commodityId);
			return RestUtils.successWhenNotNull(commodity);
		} catch (BusinessException ex) {
			LOG.error("get Commodity failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商保存新增对象
	 **/
	@ApiOperation(value = "新增商品(供应商)", notes = "添加商品(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "commodity", value = "商品对象", required = true, dataType = "CommodityBo") })
	@RequestMapping(value = "addForSupplier", method = RequestMethod.POST)
	public RestResult addCommodityForSupplier(@RequestBody CommodityBo commodity, HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			SupplierBo supplierBo = supplierService.getSupplierBoById(supplierToken.getId());
			commodity.setSupplier(supplierBo);
			commodity.setAuditState(CommodityEnum.AUDIT_STATE_WAIT.getCode());
			CommodityVo dbCommodityVo = commodityService.addCommodity(commodity);
			return RestUtils.successWhenNotNull(dbCommodityVo);
		} catch (BusinessException ex) {
			LOG.error("add Commodity failure : {}", commodity, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商保存更新对象
	 **/
	@ApiOperation(value = "更新商品(供应商)", notes = "修改商品(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "commodity", value = "商品对象", required = true, dataType = "CommodityBo") })
	@RequestMapping(value = "updateForSupplier", method = RequestMethod.POST)
	public RestResult updateCommodityForSupplier(@RequestBody CommodityBo commodity, HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			SupplierBo supplierBo = supplierService.getSupplierBoById(supplierToken.getId());
			commodity.setSupplier(supplierBo);
			commodity.setAuditState(CommodityEnum.AUDIT_STATE_WAIT.getCode());
			CommodityVo commodityVo = commodityService.updateCommodity(commodity);
			return RestUtils.successWhenNotNull(commodityVo);
		} catch (BusinessException ex) {
			LOG.error("update Commodity failure : {}", commodity, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商删除对象
	 **/
	@ApiOperation(value = "删除商品(供应商)", notes = "删除商品(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "商品Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeByIdForSupplier", method = RequestMethod.GET)
	public RestResult removeCommodityByIdForSupplier(@RequestParam("id") int commodityId) {
		try {
			commodityService.removeCommodityById(commodityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Commodity failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 商品上下架
	 **/
	@ApiOperation(value = "商品上下架(总后台)", notes = "商品上下架(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "commodityId", value = "商品Id", required = true, dataType = "Int") })
	@RequestMapping(value = "upOrDownShelf", method = RequestMethod.GET)
	public RestResult upOrDownShelf(@RequestParam("commodityId") int commodityId) {
		try {
			commodityService.upOrDownShelf(commodityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("up or down shelf failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商商品上下架
	 **/
	@ApiOperation(value = "商品上下架(供应商)", notes = "商品上下架(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "commodityId", value = "商品Id", required = true, dataType = "Int") })
	@RequestMapping(value = "upOrDownShelfForSupplier", method = RequestMethod.GET)
	public RestResult upOrDownForSupplier(@RequestParam("commodityId") int commodityId) {
		try {
			commodityService.upOrDownShelf(commodityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("up or down shelf failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 审核拒绝
	 */
	@ApiOperation(value = "审核拒绝", notes = "审核拒绝")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "商品Id", required = true, dataType = "Int") })
	@RequestMapping(value = "auditReject", method = RequestMethod.GET)
	public RestResult auditReject(@RequestParam("id") int commodityId) {
		try {
			commodityService.updateAuditState(commodityId, CommodityEnum.AUDIT_STATE_REJECT.getCode());
			return RestUtils.success(Boolean.TRUE);
		} catch (Exception ex) {
			LOG.error("update Commodity failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 审核通过
	 */
	@ApiOperation(value = "审核通过", notes = "审核通过")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "商品Id", required = true, dataType = "Int") })
	@RequestMapping(value = "auditPass", method = RequestMethod.GET)
	public RestResult auditPass(@RequestParam("id") int commodityId) {
		try {
			commodityService.updateAuditState(commodityId, CommodityEnum.AUDIT_STATE_PASS.getCode());
			return RestUtils.success(Boolean.TRUE);
		} catch (Exception ex) {
			LOG.error("update Commodity failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查询没有被买送券添加的商品
	 **/
	@ApiOperation(value = "查询没有被买送券添加的商品", notes = "查询没有被买送券添加的商品")
	@RequestMapping(value = "getAll", method = RequestMethod.POST)
	public RestResult getAllNotInId() {
		try {
			List<Commodity> dbCouponListVo = commodityService.getAllNotInId();
			return RestUtils.successWhenNotNull(dbCouponListVo);
		} catch (BusinessException ex) {
			LOG.error("add Coupon failure : {}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查询属性属性组，属性值
	 *
	 * @param categoryId
	 * @return
	 */
	@ApiOperation(value = "查询属性属性组，属性值", notes = "查询属性属性组，属性值")
	@ApiImplicitParams({ @ApiImplicitParam(name = "categoryId", value = "商品分类Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getAttributeGroupsByCategoryId", method = RequestMethod.GET)
	public RestResult getAttributeGroupsByCategoryId(@RequestParam("categoryId") int categoryId) {
		try {
			List<AttributeGroupListVo> attributeGroup = attributeGroupService.getAttrGroupsByCategory(categoryId);
			return RestUtils.successWhenNotNull(attributeGroup);
		} catch (BusinessException ex) {
			LOG.error("add AttributeGroup failure :", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 批量上架
	 */
	@ApiOperation(value = "批量上架", notes = "批量上架")
	@RequestMapping(value = "batchUpShelf", method = RequestMethod.POST)
	public RestResult batchUpShelf(@RequestBody List<Integer> ids) {
		try {

			commodityService.batchUpShelf(ids);
			return RestUtils.success(true);
		} catch (BusinessException ex) {
			LOG.error("batch Up Shelf failure :ids={} ", ids, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 批量删除商品Test
	 */
	@ApiOperation(value = "批量删除商品Test", notes = "批量删除商品Test")
	@RequestMapping(value = "test", method = RequestMethod.POST)
	public RestResult test(@RequestBody List<Integer> ids) {
		try {

			commodityService.test(ids);
			return RestUtils.success(true);
		} catch (BusinessException ex) {
			LOG.error("batch Up Shelf failure :ids={} ", ids, ex);
			return RestUtils.error(ex.getMessage());
		}
	}


	/**
	 * 批量下架
	 */
	@ApiOperation(value = "批量下架", notes = "批量下架")
	@RequestMapping(value = "batchDownShelf", method = RequestMethod.POST)
	public RestResult batchDownShelf(@RequestBody List<Integer> ids) {
		try {
			commodityService.batchDownShelf(ids);
			return RestUtils.success(true);
		} catch (BusinessException ex) {
			LOG.error("batch Down Shelf failure :ids={}", ids, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商商品重新申请上架
	 * 
	 * @param commodityId
	 * @return
	 */
	@ApiOperation(value = "商品重新申请上架(供应商)", notes = "商品重新申请上架(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "commodityId", value = "商品Id", required = true, dataType = "Int") })
	@RequestMapping(value = "reapplyUpShelfForSupplier", method = RequestMethod.GET)
	public RestResult reapplyUpShelfForSupplier(@RequestParam("commodityId") int commodityId) {
		try {
			commodityService.reapplyUpShelf(commodityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("reapply Up Shelf failure : id={}", commodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商商品重新申请上架
	 *
	 * @return
	 */
	@ApiOperation(value = "同步商品至搜索引擎", notes = "同步商品至搜索引擎")
	@ApiImplicitParams({ })
	@RequestMapping(value = "syncCommoditiesToEs", method = RequestMethod.GET)
	public RestResult syncCommoditiesToEs() {
		try {
			ResponseResult result =commodityService.syncCommoditiesToEs();
			if(result!=null){
				if(result.getResult() == Result.SUCCESS){
					LOG.info("[elasticsearch] syncCommoditiesToEs success state:" + result.getResult());
					return new RestResult(com.yihz.common.json.Result.SUCCESS, result.getMessage(), null);
				}else {
					LOG.info("[elasticsearch] syncCommoditiesToEs failure state:" + result.getResult());
					return new RestResult(com.yihz.common.json.Result.FAILURE, result.getMessage(), null);
				}
			}
			return new RestResult(com.yihz.common.json.Result.FAILURE, "[elasticsearch] syncCommoditiesToEs failure", null);
		} catch (Exception ex) {
			LOG.error("[elasticsearch] syncCommoditiesToEs error", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}