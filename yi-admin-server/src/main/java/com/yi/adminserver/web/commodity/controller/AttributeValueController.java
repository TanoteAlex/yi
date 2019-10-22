package com.yi.adminserver.web.commodity.controller;
///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.admin.web.commodity.controller;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yi.core.commodity.domain.entity.AttributeValue;
//import com.yi.core.commodity.service.IAttributeValueService;
//import com.yihz.common.exception.BusinessException;
//import com.yihz.common.json.RestResult;
//import com.yihz.common.persistence.Pagination;
//import com.yihz.common.utils.web.RestUtils;
//
///**
// *
// * @author lemosen
// * @version 1.0
// * @since 1.0
// **/
//@RestController
//@RequestMapping(value = "/attributeValue")
//public class AttributeValueController {
//
//	private final Logger LOG = LoggerFactory.getLogger(AttributeValueController.class);
//
//	@Resource
//	private IAttributeValueService attributeValueService;
//
//	/**
//	 * 分页查询
//	 */
//	@RequestMapping(value = "query", method = RequestMethod.POST)
//	public Page<AttributeValue> queryAttributeValue(@RequestBody Pagination<AttributeValue> query) {
//		Page<AttributeValue> page = attributeValueService.query(query);
//		return page;
//	}
//
//	/**
//	 * 查看对象
//	 **/
//	@RequestMapping(value = "getById", method = RequestMethod.GET)
//	public RestResult viewAttributeValue(@RequestParam("id") int attributeValueId) {
//		try {
//			AttributeValue attributeValue = attributeValueService.getAttributeValueById(attributeValueId);
//			return RestUtils.successWhenNotNull(attributeValue);
//		} catch (BusinessException ex) {
//			LOG.error("get AttributeValue failure : id=attributeValueId", ex);
//			return RestUtils.error("get AttributeValue failure : " + ex.getMessage());
//		}
//	}
//
//	/**
//	 * 保存新增对象
//	 **/
//	@RequestMapping(value = "add", method = RequestMethod.POST)
//	public RestResult addAttributeValue(@RequestBody AttributeValue attributeValue) {
//		try {
//			AttributeValue dbAttributeValue = attributeValueService.addAttributeValue(attributeValue);
//			return RestUtils.successWhenNotNull(dbAttributeValue);
//		} catch (BusinessException ex) {
//			LOG.error("add AttributeValue failure : attributeValue", attributeValue, ex);
//			return RestUtils.error("add AttributeValue failure : " + ex.getMessage());
//		}
//	}
//
//	/**
//	 * 保存更新对象
//	 **/
//	@RequestMapping(value = "update", method = RequestMethod.POST)
//	public RestResult updateAttributeValue(@RequestBody AttributeValue attributeValue) {
//		try {
//			AttributeValue dbAttributeValue = attributeValueService.updateAttributeValue(attributeValue);
//			return RestUtils.successWhenNotNull(dbAttributeValue);
//		} catch (BusinessException ex) {
//			LOG.error("update AttributeValue failure : attributeValue", attributeValue, ex);
//			return RestUtils.error("update AttributeValue failure : " + ex.getMessage());
//		}
//	}
//
//	/**
//	 * 删除对象
//	 **/
//	@RequestMapping(value = "removeById", method = RequestMethod.GET)
//	public RestResult removeAttributeValueById(@RequestParam("id") int attributeValueId) {
//		try {
//			attributeValueService.removeAttributeValueById(attributeValueId);
//			return RestUtils.success(true);
//		} catch (Exception ex) {
//			LOG.error("remove AttributeValue failure : id=attributeValueId", ex);
//			return RestUtils.error("remove AttributeValue failure : " + ex.getMessage());
//		}
//	}
//}