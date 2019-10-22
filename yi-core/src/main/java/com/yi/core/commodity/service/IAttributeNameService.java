package com.yi.core.commodity.service;///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.core.commodity.service;
//
//import org.springframework.data.domain.Page;
//
//import com.yi.core.commodity.domain.bo.AttributeNameBo;
//import com.yi.core.commodity.domain.entity.AttributeName;
//import com.yi.core.commodity.domain.vo.AttributeNameListVo;
//import com.yi.core.commodity.domain.vo.AttributeNameVo;
//import com.yihz.common.persistence.Pagination;
//
///**
// *
// * @author lemosen
// * @version 1.0
// * @since 1.0
// **/
//public interface IAttributeNameService {
//
//	/**
//	 * 根据ID得到AttributeName
//	 * 
//	 * @param attributeNameId
//	 * @return
//	 */
//	AttributeName getAttributeNameById(int attributeNameId);
//
//	/**
//	 * 根据ID得到AttributeNameVo
//	 * 
//	 * @param attributeNameId
//	 * @return
//	 */
//	AttributeNameVo getAttributeNameVoById(int attributeNameId);
//
//	/**
//	 * 根据ID得到AttributeNameListVo
//	 * 
//	 * @param attributeNameId
//	 * @return
//	 */
//	AttributeNameListVo getAttributeNameListVoById(int attributeNameId);
//
//	/**
//	 * 根据Entity创建AttributeName
//	 * 
//	 * @param attributeName
//	 * @return
//	 */
//	AttributeName addAttributeName(AttributeName attributeName);
//
//	/**
//	 * 根据BO创建AttributeName
//	 * 
//	 * @param attributeNameBo
//	 * @return
//	 */
//	AttributeNameListVo addAttributeName(AttributeNameBo attributeNameBo);
//
//	/**
//	 * 根据Entity更新AttributeName
//	 * 
//	 * @param attributeName
//	 * @return
//	 */
//	AttributeName updateAttributeName(AttributeName attributeName);
//
//	/**
//	 * 根据BO更新AttributeName
//	 * 
//	 * @param attributeNameBo
//	 * @return
//	 */
//	AttributeNameListVo updateAttributeName(AttributeNameBo attributeNameBo);
//
//	/**
//	 * 删除AttributeName
//	 * 
//	 * @param attributeNameId
//	 */
//	void removeAttributeNameById(int attributeNameId);
//
//	/**
//	 * 分页查询: AttributeName
//	 * 
//	 * @param query
//	 * @return
//	 */
//	Page<AttributeName> query(Pagination<AttributeName> query);
//
//	/**
//	 * 分页查询: AttributeNameListVo
//	 * 
//	 * @param query
//	 * @return
//	 */
//	Page<AttributeNameListVo> queryListVo(Pagination<AttributeName> query);
//
//}
