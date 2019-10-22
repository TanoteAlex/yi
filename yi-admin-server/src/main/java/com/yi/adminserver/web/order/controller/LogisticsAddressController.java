/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.order.controller;


import com.yi.adminserver.web.auth.jwt.JwtSupplierToken;
import com.yi.adminserver.web.auth.jwt.SupplierToken;
import com.yi.core.order.domain.bo.LogisticsAddressBo;
import com.yi.core.order.domain.entity.LogisticsAddress;
import com.yi.core.order.domain.vo.LogisticsAddressListVo;
import com.yi.core.order.domain.vo.LogisticsAddressVo;
import com.yi.core.order.service.ILogisticsAddressService;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 物流地址
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("物流地址控制层")
@RestController
@RequestMapping(value = "/logisticsAddress")
public class LogisticsAddressController {

    private final Logger LOG = LoggerFactory.getLogger(LogisticsAddressController.class);


    @Resource
    private ILogisticsAddressService logisticsAddressService;

    @Resource
    private ISupplierService supplierService;

    /**
     * 分页查询
     */
    @ApiOperation(value = "获取物流地址列表(供应商)", notes = "根据分页参数查询物流地址列表(供应商)")
    @RequestMapping(value = "queryForSupplier", method = RequestMethod.POST)
    public Page<LogisticsAddressListVo> queryLogisticsAddressForSupplier(@RequestBody Pagination<LogisticsAddress> query,
                                                              HttpServletRequest request) {
        SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
        query.getParams().put("supplier.id", supplierToken.getId());
        Page<LogisticsAddressListVo> page = logisticsAddressService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @ApiOperation(value = "查看物流地址信息(供应商)", notes = "根据物流地址Id获取物流地址信息(供应商)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "物流地址Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "getByIdForSupplier", method = RequestMethod.GET)
    public RestResult viewLogisticsAddressForSupplier(@RequestParam("id") int logisticsAddressId) {
        try {
            LogisticsAddressVo logisticsAddress = logisticsAddressService.getLogisticsAddressVoById(logisticsAddressId);
            return RestUtils.successWhenNotNull(logisticsAddress);
        } catch (BusinessException ex) {
            LOG.error("get LogisticsAddress failure : id=logisticsAddressId", ex);
            return RestUtils.error("get LogisticsAddress failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @ApiOperation(value = "新增物流地址(供应商)", notes = "添加物流地址(供应商)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logisticsAddress", value = "物流地址对象", required = true, dataType = "LogisticsAddressBo")
    })
    @RequestMapping(value = "addForSupplier", method = RequestMethod.POST)
    public RestResult addLogisticsAddressForSupplier(@RequestBody LogisticsAddressBo logisticsAddress, HttpServletRequest request) {
        try {
            SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
            SupplierBo supplierBo = supplierService.getSupplierBoById(supplierToken.getId());
            logisticsAddress.setSupplier(supplierBo);
            LogisticsAddressListVo dbLogisticsAddress = logisticsAddressService.addLogisticsAddress(logisticsAddress);
            return RestUtils.successWhenNotNull(dbLogisticsAddress);
        } catch (BusinessException ex) {
            LOG.error("add LogisticsAddress failure : logisticsAddress", logisticsAddress, ex);
            return RestUtils.error("add LogisticsAddress failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @ApiOperation(value = "更新物流地址(供应商)", notes = "修改物流地址(供应商)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logisticsAddress", value = "物流地址对象", required = true, dataType = "LogisticsAddressBo")
    })
    @RequestMapping(value = "updateForSupplier", method = RequestMethod.POST)
    public RestResult updateLogisticsAddressForSupplier(@RequestBody LogisticsAddressBo logisticsAddress) {
        try {
            LogisticsAddressListVo dbLogisticsAddress = logisticsAddressService.updateLogisticsAddress
                    (logisticsAddress);
            return RestUtils.successWhenNotNull(dbLogisticsAddress);
        } catch (BusinessException ex) {
            LOG.error("update LogisticsAddress failure : logisticsAddress", logisticsAddress, ex);
            return RestUtils.error("update LogisticsAddress failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @ApiOperation(value = "删除物流地址(供应商)", notes = "删除物流地址(供应商)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "物流地址Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeLogisticsAddressById(@RequestParam("id") int logisticsAddressId) {
        try {
            logisticsAddressService.removeLogisticsAddressById(logisticsAddressId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove LogisticsAddress failure : id=logisticsAddressId", ex);
            return RestUtils.error("remove LogisticsAddress failure : " + ex.getMessage());
        }
    }
}