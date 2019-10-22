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
//import com.yi.core.commodity.domain.entity.AttributeName;
//import com.yi.core.commodity.service.IAttributeNameService;
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
//@RequestMapping(value = "/attributeName")
//public class AttributeNameController {
//
//	private final Logger LOG = LoggerFactory.getLogger(AttributeNameController.class);
//
//	@Resource
//	private IAttributeNameService attributeNameService;
//
//	/**
//	 * 分页查询
//	 */
//	@RequestMapping(value = "query", method = RequestMethod.POST)
//	public Page<AttributeName> queryAttributeName(@RequestBody Pagination<AttributeName> query) {
//		Page<AttributeName> page = attributeNameService.query(query);
//		return page;
//	}
//
//	/**
//	 * 查看对象
//	 **/
//	@RequestMapping(value = "getById", method = RequestMethod.GET)
//	public RestResult viewAttributeName(@RequestParam("id") int attributeNameId) {
//		try {
//			AttributeName attributeName = attributeNameService.getAttributeNameById(attributeNameId);
//			return RestUtils.successWhenNotNull(attributeName);
//		} catch (BusinessException ex) {
//			LOG.error("get AttributeName failure : id=attributeNameId", ex);
//			return RestUtils.error("get AttributeName failure : " + ex.getMessage());
//		}
//	}
//
//	/**
//	 * 保存新增对象
//	 **/
//	@RequestMapping(value = "add", method = RequestMethod.POST)
//	public RestResult addAttributeName(@RequestBody AttributeName attributeName) {
//		try {
//			AttributeName dbAttributeName = attributeNameService.addAttributeName(attributeName);
//			return RestUtils.successWhenNotNull(dbAttributeName);
//		} catch (BusinessException ex) {
//			LOG.error("add AttributeName failure : attributeName", attributeName, ex);
//			return RestUtils.error("add AttributeName failure : " + ex.getMessage());
//		}
//	}
//
//	/**
//	 * 保存更新对象
//	 **/
//	@RequestMapping(value = "update", method = RequestMethod.POST)
//	public RestResult updateAttributeName(@RequestBody AttributeName attributeName) {
//		try {
//			AttributeName dbAttributeName = attributeNameService.updateAttributeName(attributeName);
//			return RestUtils.successWhenNotNull(dbAttributeName);
//		} catch (BusinessException ex) {
//			LOG.error("update AttributeName failure : attributeName", attributeName, ex);
//			return RestUtils.error("update AttributeName failure : " + ex.getMessage());
//		}
//	}
//
//	/**
//	 * 删除对象
//	 **/
//	@RequestMapping(value = "removeById", method = RequestMethod.GET)
//	public RestResult removeAttributeNameById(@RequestParam("id") int attributeNameId) {
//		try {
//			attributeNameService.removeAttributeNameById(attributeNameId);
//			return RestUtils.success(true);
//		} catch (Exception ex) {
//			LOG.error("remove AttributeName failure : id=attributeNameId", ex);
//			return RestUtils.error("remove AttributeName failure : " + ex.getMessage());
//		}
//	}
//}