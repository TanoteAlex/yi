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
//import com.yi.core.commodity.domain.bo.AttributeValueBo;
//import com.yi.core.commodity.domain.entity.AttributeValue;
//import com.yi.core.commodity.domain.vo.AttributeValueListVo;
//import com.yi.core.commodity.domain.vo.AttributeValueVo;
//import com.yihz.common.persistence.Pagination;
//
///**
// *
// * @author lemosen
// * @version 1.0
// * @since 1.0
// **/
//public interface IAttributeValueService {
//
//	/**
//	 * 根据ID得到AttributeValue
//	 * 
//	 * @param attributeValueId
//	 * @return
//	 */
//	AttributeValue getAttributeValueById(int attributeValueId);
//
//	/**
//	 * 根据ID得到AttributeValueVo
//	 * 
//	 * @param attributeValueId
//	 * @return
//	 */
//	AttributeValueVo getAttributeValueVoById(int attributeValueId);
//
//	/**
//	 * 根据ID得到AttributeValueListVo
//	 * 
//	 * @param attributeValueId
//	 * @return
//	 */
//	AttributeValueListVo getAttributeValueListVoById(int attributeValueId);
//
//	/**
//	 * 根据Entity创建AttributeValue
//	 * 
//	 * @param attributeValue
//	 * @return
//	 */
//	AttributeValue addAttributeValue(AttributeValue attributeValue);
//
//	/**
//	 * 根据BO创建AttributeValue
//	 * 
//	 * @param attributeValueBo
//	 * @return
//	 */
//	AttributeValueListVo addAttributeValue(AttributeValueBo attributeValueBo);
//
//	/**
//	 * 根据Entity更新AttributeValue
//	 * 
//	 * @param attributeValue
//	 * @return
//	 */
//	AttributeValue updateAttributeValue(AttributeValue attributeValue);
//
//	/**
//	 * 根据BO更新AttributeValue
//	 * 
//	 * @param attributeValueBo
//	 * @return
//	 */
//	AttributeValueListVo updateAttributeValue(AttributeValueBo attributeValueBo);
//
//	/**
//	 * 删除AttributeValue
//	 * 
//	 * @param attributeValueId
//	 */
//	void removeAttributeValueById(int attributeValueId);
//
//	/**
//	 * 分页查询: AttributeValue
//	 * 
//	 * @param query
//	 * @return
//	 */
//	Page<AttributeValue> query(Pagination<AttributeValue> query);
//
//	/**
//	 * 分页查询: AttributeValueListVo
//	 * 
//	 * @param query
//	 * @return
//	 */
//	Page<AttributeValueListVo> queryListVo(Pagination<AttributeValue> query);
//
//}
