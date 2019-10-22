package com.yi.core.commodity.service.impl;///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.core.commodity.service.impl;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.persistence.criteria.JoinType;
//
//import com.yi.core.commodity.domain.entity.AttributeName_;
//import com.yi.core.commodity.domain.entity.AttributeValue_;
//import com.yi.core.commodity.domain.entity.Category_;
//import com.yi.core.common.deleteByIdd;
//import org.hibernate.Hibernate;
//import org.omg.PortableServer.POAManagerPackage.State;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.yi.core.commodity.dao.AttributeValueDao;
//import com.yi.core.commodity.domain.bo.AttributeValueBo;
//import com.yi.core.commodity.domain.entity.AttributeValue;
//import com.yi.core.commodity.domain.simple.AttributeValueSimple;
//import com.yi.core.commodity.domain.vo.AttributeValueListVo;
//import com.yi.core.commodity.domain.vo.AttributeValueVo;
//import com.yi.core.commodity.service.IAttributeValueService;
//import com.yihz.common.convert.AbstractBeanConvert;
//import com.yihz.common.convert.BeanConvert;
//import com.yihz.common.convert.BeanConvertManager;
//import com.yihz.common.convert.EntityListVoBoSimpleConvert;
//import com.yihz.common.persistence.AttributeReplication;
//import com.yihz.common.persistence.Pagination;
//
///**
// * 属性值
// * 
// * @author lemosen
// * @version 1.0
// * @since 1.0
// **/
//@Service
//@Transactional
//public class AttributeValueServiceImpl implements IAttributeValueService, InitializingBean {
//
//	private final Logger LOG = LoggerFactory.getLogger(AttributeValueServiceImpl.class);
//
//	@Resource
//	private BeanConvertManager beanConvertManager;
//
//	@Resource
//	private AttributeValueDao attributeValueDao;
//
//	private EntityListVoBoSimpleConvert<AttributeValue, AttributeValueBo, AttributeValueVo, AttributeValueSimple, AttributeValueListVo> attributeValueConvert;
//
//	@Override
//	public AttributeValue getAttributeValueById(int attributeValueId) {
//		AttributeValue attributeValue = attributeValueDao.getOne(attributeValueId);
//		Hibernate.initialize(attributeValue.getAttributeName());
//		return attributeValue;
//	}
//
//	@Override
//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	public AttributeValueVo getAttributeValueVoById(int attributeValueId) {
//		return attributeValueConvert.toVo(this.attributeValueDao.getOne(attributeValueId));
//	}
//
//	@Override
//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	public AttributeValueListVo getAttributeValueListVoById(int attributeValueId) {
//		return attributeValueConvert.toListVo(this.attributeValueDao.getOne(attributeValueId));
//	}
//
//	@Override
//	public AttributeValue addAttributeValue(AttributeValue attributeValue) {
//		attributeValue.setCreateTime(new Date());
//		return attributeValueDao.save(attributeValue);
//	}
//
//	@Override
//	public AttributeValueListVo addAttributeValue(AttributeValueBo attributeValueBo) {
//		return attributeValueConvert.toListVo(attributeValueDao.save(attributeValueConvert.toEntity(attributeValueBo)));
//	}
//
//	@Override
//	public AttributeValue updateAttributeValue(AttributeValue attributeValue) {
//		
//		AttributeValue dbAttributeValue = attributeValueDao.getOne(attributeValue.getId());
////		dbAttributeValue.setAttributeName(attributeValue.getAttributeName());
//		AttributeReplication.copying(attributeValue, dbAttributeValue,AttributeValue_.attributeName, AttributeValue_
//				.attrValue);
//		return dbAttributeValue;
//	}
//
//	@Override
//	public AttributeValueListVo updateAttributeValue(AttributeValueBo attributeValueBo) {
//		AttributeValue dbAttributeValue = attributeValueDao.getOne(attributeValueBo.getId());
//		AttributeReplication.copying(attributeValueBo, dbAttributeValue, AttributeValue_.guid,
//				AttributeValue_.attrValue, AttributeValue_.state, AttributeValue_.createTime, AttributeValue_.deleteByIdd,
//				AttributeValue_.delTime);
//		return attributeValueConvert.toListVo(dbAttributeValue);
//	}
//
//	@Override
//	public void removeAttributeValueById(int attributeValueId) {
//		/*if(attributeValueDao.existsById(attributeValueId)) {
//			attributeValueDao.deleteById(attributeValueId);
//			
//		}*/
//		attributeValueDao.getOne(attributeValueId).setdeleteByIdd(deleteByIdd.DEL_TRUE);
//	}
//
//	@Override
//	public Page<AttributeValue> query(Pagination<AttributeValue> query) {
//		query.setEntityClazz(AttributeValue.class);
//		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
//			root.fetch(AttributeValue_.attributeName, JoinType.LEFT);
//			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AttributeValue_.deleteByIdd), deleteByIdd.DEL_FALSE)));
//		}));
//		Page<AttributeValue> page = attributeValueDao.findAll(query, query.getPageRequest());
//		return page;
//	}
//
//	@Override
//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	public Page<AttributeValueListVo> queryListVo(Pagination<AttributeValue> query) {
//
//		Page<AttributeValue> pages = this.query(query);
//
//		List<AttributeValueListVo> vos = attributeValueConvert.toListVos(pages.getContent());
//		return new PageImpl<AttributeValueListVo>(vos, query.getPageRequest(), pages.getTotalElements());
//
//	}
//
//	protected void initConvert() {
//		this.attributeValueConvert = new EntityListVoBoSimpleConvert<AttributeValue, AttributeValueBo, AttributeValueVo, AttributeValueSimple, AttributeValueListVo>(
//				beanConvertManager) {
//			@Override
//			protected BeanConvert<AttributeValue, AttributeValueVo> createEntityToVoConvert(
//					BeanConvertManager beanConvertManager) {
//				return new AbstractBeanConvert<AttributeValue, AttributeValueVo>(beanConvertManager) {
//					@Override
//					protected void postConvert(AttributeValueVo AttributeValueVo, AttributeValue AttributeValue) {
//
//					}
//				};
//			}
//
//			@Override
//			protected BeanConvert<AttributeValue, AttributeValueListVo> createEntityToListVoConvert(
//					BeanConvertManager beanConvertManager) {
//				return new AbstractBeanConvert<AttributeValue, AttributeValueListVo>(beanConvertManager) {
//					@Override
//					protected void postConvert(AttributeValueListVo AttributeValueListVo,
//							AttributeValue AttributeValue) {
//
//					}
//				};
//			}
//
//			@Override
//			protected BeanConvert<AttributeValue, AttributeValueBo> createEntityToBoConvert(
//					BeanConvertManager beanConvertManager) {
//				return new AbstractBeanConvert<AttributeValue, AttributeValueBo>(beanConvertManager) {
//					@Override
//					protected void postConvert(AttributeValueBo AttributeValueBo, AttributeValue AttributeValue) {
//
//					}
//				};
//			}
//
//			@Override
//			protected BeanConvert<AttributeValueBo, AttributeValue> createBoToEntityConvert(
//					BeanConvertManager beanConvertManager) {
//				return new AbstractBeanConvert<AttributeValueBo, AttributeValue>(beanConvertManager) {
//				};
//			}
//
//			@Override
//			protected BeanConvert<AttributeValue, AttributeValueSimple> createEntityToSimpleConvert(
//					BeanConvertManager beanConvertManager) {
//				return new AbstractBeanConvert<AttributeValue, AttributeValueSimple>(beanConvertManager) {
//				};
//			}
//
//			@Override
//			protected BeanConvert<AttributeValueSimple, AttributeValue> createSimpleToEntityConvert(
//					BeanConvertManager beanConvertManager) {
//				return new AbstractBeanConvert<AttributeValueSimple, AttributeValue>(beanConvertManager) {
//					@Override
//					public AttributeValue convert(AttributeValueSimple AttributeValueSimple) throws BeansException {
//						return attributeValueDao.getOne(AttributeValueSimple.getId());
//					}
//				};
//			}
//		};
//	}
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		initConvert();
//	}
//}
