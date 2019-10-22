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
//import com.yi.core.commodity.domain.entity.Category_;
//import com.yi.core.common.deleteByIdd;
//import org.hibernate.Hibernate;
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
//import com.yi.core.commodity.dao.AttributeNameDao;
//import com.yi.core.commodity.domain.bo.AttributeNameBo;
//import com.yi.core.commodity.domain.entity.AttributeName;
//import com.yi.core.commodity.domain.entity.AttributeName_;
//import com.yi.core.commodity.domain.simple.AttributeNameSimple;
//import com.yi.core.commodity.domain.vo.AttributeNameListVo;
//import com.yi.core.commodity.domain.vo.AttributeNameVo;
//import com.yi.core.commodity.service.IAttributeNameService;
//import com.yihz.common.convert.AbstractBeanConvert;
//import com.yihz.common.convert.BeanConvert;
//import com.yihz.common.convert.BeanConvertManager;
//import com.yihz.common.convert.EntityListVoBoSimpleConvert;
//import com.yihz.common.persistence.AttributeReplication;
//import com.yihz.common.persistence.Pagination;
//
///**
// * 属性名
// *
// * @author lemosen
// * @version 1.0
// * @since 1.0
// **/
//@Service
//@Transactional
//public class AttributeNameServiceImpl implements IAttributeNameService, InitializingBean {
//
//    private final Logger LOG = LoggerFactory.getLogger(AttributeNameServiceImpl.class);
//
//    @Resource
//    private BeanConvertManager beanConvertManager;
//
//    @Resource
//    private AttributeNameDao attributeNameDao;
//
//    private EntityListVoBoSimpleConvert<AttributeName, AttributeNameBo, AttributeNameVo, AttributeNameSimple,
//			AttributeNameListVo> attributeNameConvert;
//
//    @Override
//    public AttributeName getAttributeNameById(int attributeNameId) {
//        AttributeName attributeName = attributeNameDao.getOne(attributeNameId);
//        Hibernate.initialize(attributeName.getAttributeGroup());
//        return attributeName;
//    }
//
//    @Override
//    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//    public AttributeNameVo getAttributeNameVoById(int attributeNameId) {
//
//        return attributeNameConvert.toVo(this.attributeNameDao.getOne(attributeNameId));
//    }
//
//    @Override
//    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//    public AttributeNameListVo getAttributeNameListVoById(int attributeNameId) {
//        return attributeNameConvert.toListVo(this.attributeNameDao.getOne(attributeNameId));
//    }
//
//    @Override
//    public AttributeName addAttributeName(AttributeName attributeName) {
//        attributeName.setCreateTime(new Date());
//        return attributeNameDao.save(attributeName);
//    }
//
//    @Override
//    public AttributeNameListVo addAttributeName(AttributeNameBo attributeNameBo) {
//        return attributeNameConvert.toListVo(attributeNameDao.save(attributeNameConvert.toEntity(attributeNameBo)));
//    }
//
//    @Override
//    public AttributeName updateAttributeName(AttributeName attributeName) {
//        AttributeName dbAttributeName = attributeNameDao.getOne(attributeName.getId());
//        dbAttributeName.setAttributeGroup(attributeName.getAttributeGroup());
//        AttributeReplication.copying(attributeName, dbAttributeName, AttributeName_.attrName);
//        return dbAttributeName;
//    }
//
//    @Override
//    public AttributeNameListVo updateAttributeName(AttributeNameBo attributeNameBo) {
//        AttributeName dbAttributeName = attributeNameDao.getOne(attributeNameBo.getId());
//        AttributeReplication.copying(attributeNameBo, dbAttributeName, AttributeName_.guid, AttributeName_.attrName,
//                AttributeName_.state, AttributeName_.createTime, AttributeName_.deleteByIdd, AttributeName_.delTime);
//        return attributeNameConvert.toListVo(dbAttributeName);
//    }
//
//    @Override
//    public void removeAttributeNameById(int attributeNameId) {
//        /*attributeNameDao.deleteById(attributeNameId);*/
//        attributeNameDao.getOne(attributeNameId).setdeleteByIdd(deleteByIdd.DEL_TRUE);
//    }
//
//    @Override
//    public Page<AttributeName> query(Pagination<AttributeName> query) {
//        query.setEntityClazz(AttributeName.class);
//        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
//            root.fetch(AttributeName_.attributeGroup, JoinType.LEFT);
//            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AttributeName_.deleteByIdd), deleteByIdd.DEL_FALSE)));
//        }));
//        Page<AttributeName> page = attributeNameDao.findAll(query, query.getPageRequest());
//        return page;
//    }
//
//    @Override
//    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//    public Page<AttributeNameListVo> queryListVo(Pagination<AttributeName> query) {
//
//        Page<AttributeName> pages = this.query(query);
//
//        List<AttributeNameListVo> vos = attributeNameConvert.toListVos(pages.getContent());
//        return new PageImpl<AttributeNameListVo>(vos, query.getPageRequest(), pages.getTotalElements());
//
//    }
//
//    protected void initConvert() {
//        this.attributeNameConvert = new EntityListVoBoSimpleConvert<AttributeName, AttributeNameBo, AttributeNameVo,
//				AttributeNameSimple, AttributeNameListVo>(
//                beanConvertManager) {
//            @Override
//            protected BeanConvert<AttributeName, AttributeNameVo> createEntityToVoConvert(
//                    BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<AttributeName, AttributeNameVo>(beanConvertManager) {
//                    @Override
//                    protected void postConvert(AttributeNameVo AttributeNameVo, AttributeName AttributeName) {
//
//                    }
//                };
//            }
//
//            @Override
//            protected BeanConvert<AttributeName, AttributeNameListVo> createEntityToListVoConvert(
//                    BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<AttributeName, AttributeNameListVo>(beanConvertManager) {
//                    @Override
//                    protected void postConvert(AttributeNameListVo AttributeNameListVo, AttributeName AttributeName) {
//
//                    }
//                };
//            }
//
//            @Override
//            protected BeanConvert<AttributeName, AttributeNameBo> createEntityToBoConvert(
//                    BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<AttributeName, AttributeNameBo>(beanConvertManager) {
//                    @Override
//                    protected void postConvert(AttributeNameBo AttributeNameBo, AttributeName AttributeName) {
//
//                    }
//                };
//            }
//
//            @Override
//            protected BeanConvert<AttributeNameBo, AttributeName> createBoToEntityConvert(
//                    BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<AttributeNameBo, AttributeName>(beanConvertManager) {
//                };
//            }
//
//            @Override
//            protected BeanConvert<AttributeName, AttributeNameSimple> createEntityToSimpleConvert(
//                    BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<AttributeName, AttributeNameSimple>(beanConvertManager) {
//                };
//            }
//
//            @Override
//            protected BeanConvert<AttributeNameSimple, AttributeName> createSimpleToEntityConvert(
//                    BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<AttributeNameSimple, AttributeName>(beanConvertManager) {
//                    @Override
//                    public AttributeName convert(AttributeNameSimple AttributeNameSimple) throws BeansException {
//                        return attributeNameDao.getOne(AttributeNameSimple.getId());
//                    }
//                };
//            }
//        };
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        initConvert();
//    }
//}
