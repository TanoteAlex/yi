/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

import javax.annotation.Resource;

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

import com.yi.core.basic.domain.bo.AddressBo;
import com.yi.core.basic.domain.entity.Address;
import com.yi.core.basic.domain.vo.AddressListVo;
import com.yi.core.basic.domain.vo.AddressVo;
import com.yi.core.basic.service.IAddressService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 地址
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("地址控制层")
@RestController
@RequestMapping(value = "/address")
public class AddressController {

	private final Logger LOG = LoggerFactory.getLogger(AddressController.class);

	@Resource
	private IAddressService addressService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取地址列表", notes = "根据分页参数查询地址列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<Address> queryAddress(@RequestBody Pagination<Address> query) {
		Page<Address> page = addressService.query(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看地址信息", notes = "根据地址Id获取地址信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "地址Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewAddress(@RequestParam("id") int addressId) {
		try {
			AddressVo address = addressService.getAddressVoById(addressId);
			return RestUtils.successWhenNotNull(address);
		} catch (BusinessException ex) {
			LOG.error("get Address failure : id=addressId", ex);
			return RestUtils.error("get Address failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增地址", notes = "添加地址")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "address", value = "地址对象", required = true, dataType = "AddressBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAddress(@RequestBody AddressBo address) {
		try {
			AddressVo dbAddress = addressService.addAddress(address);
			return RestUtils.successWhenNotNull(dbAddress);
		} catch (BusinessException ex) {
			LOG.error("add Address failure : address", address, ex);
			return RestUtils.error("add Address failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新地址", notes = "修改地址")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "address", value = "地址对象", required = true, dataType = "AddressBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAddress(@RequestBody AddressBo address) {
		try {
			AddressVo dbAddress = addressService.updateAddress(address);
			return RestUtils.successWhenNotNull(dbAddress);
		} catch (BusinessException ex) {
			LOG.error("update Address failure : address", address, ex);
			return RestUtils.error("update Address failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除地址", notes = "删除地址")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "地址Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAddressById(@RequestParam("id") int addressId) {
		try {
			addressService.removeAddressById(addressId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Address failure : id=addressId", ex);
			return RestUtils.error("remove Address failure : " + ex.getMessage());
		}
	}
}