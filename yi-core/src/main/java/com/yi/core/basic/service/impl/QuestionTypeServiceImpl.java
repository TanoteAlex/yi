/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.dao.QuestionTypeDao;
import com.yi.core.basic.domain.bo.QuestionTypeBo;
import com.yi.core.basic.domain.entity.QuestionType;
import com.yi.core.basic.domain.entity.QuestionType_;
import com.yi.core.basic.domain.simple.QuestionTypeSimple;
import com.yi.core.basic.domain.vo.QuestionTypeListVo;
import com.yi.core.basic.domain.vo.QuestionTypeVo;
import com.yi.core.basic.service.IQuestionTypeService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class QuestionTypeServiceImpl implements IQuestionTypeService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(QuestionTypeServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private QuestionTypeDao questionTypeDao;

    private EntityListVoBoSimpleConvert<QuestionType, QuestionTypeBo, QuestionTypeVo, QuestionTypeSimple, QuestionTypeListVo> questionTypeConvert;

    /**
     * 分页查询QuestionType
     **/
    @Override
    public Page<QuestionType> query(Pagination<QuestionType> query) {
        query.setEntityClazz(QuestionType.class);
        Page<QuestionType> page = questionTypeDao.findAll(query, query.getPageRequest());
        return page;
    }

    /**
     * 创建QuestionType
     **/
    @Override
    public QuestionTypeVo addQuestionType(QuestionTypeBo questionTypeBo) {
        return questionTypeConvert.toVo(questionTypeDao.save(questionTypeConvert.toEntity(questionTypeBo)));
    }

    /**
     * 更新QuestionType
     **/
    @Override
    public QuestionTypeVo updateQuestionType(QuestionTypeBo questionTypeBo) {
        QuestionType dbQuestionType = questionTypeDao.getOne(questionTypeBo.getId());
        AttributeReplication.copying(questionTypeConvert.toEntity(questionTypeBo), dbQuestionType, QuestionType_.state, QuestionType_.typeName, QuestionType_.sort);
        return questionTypeConvert.toVo(dbQuestionType);
    }

    /**
     * 删除QuestionType
     **/
    @Override
    public void removeQuestionTypeById(int id) {
        questionTypeDao.deleteById(id);
    }

    /**
     * 根据ID得到QuestionType
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public QuestionTypeVo getQuestionTypeVoById(int id) {

        return questionTypeConvert.toVo(this.questionTypeDao.getOne(id));
    }

    /**
     * 根据ID得到QuestionTypeListVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public QuestionTypeVo getListVoById(int id) {
        return questionTypeConvert.toVo(this.questionTypeDao.getOne(id));
    }

    /**
     * 分页查询: QuestionType
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<QuestionTypeListVo> queryListVo(Pagination<QuestionType> query) {

        Page<QuestionType> pages = this.query(query);

        List<QuestionTypeListVo> vos = questionTypeConvert.toListVos(pages.getContent());
        return new PageImpl<QuestionTypeListVo>(vos, query.getPageRequest(), pages.getTotalElements());

    }

    protected void initConvert() {
        this.questionTypeConvert = new EntityListVoBoSimpleConvert<QuestionType, QuestionTypeBo, QuestionTypeVo, QuestionTypeSimple, QuestionTypeListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<QuestionType, QuestionTypeVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<QuestionType, QuestionTypeVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(QuestionTypeVo QuestionTypeVo, QuestionType QuestionType) {

                    }
                };
            }

            @Override
            protected BeanConvert<QuestionType, QuestionTypeListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<QuestionType, QuestionTypeListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(QuestionTypeListVo QuestionTypeListVo, QuestionType QuestionType) {

                    }
                };
            }

            @Override
            protected BeanConvert<QuestionType, QuestionTypeBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<QuestionType, QuestionTypeBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(QuestionTypeBo QuestionTypeBo, QuestionType QuestionType) {

                    }
                };
            }

            @Override
            protected BeanConvert<QuestionTypeBo, QuestionType> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<QuestionTypeBo, QuestionType>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<QuestionType, QuestionTypeSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<QuestionType, QuestionTypeSimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<QuestionTypeSimple, QuestionType> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<QuestionTypeSimple, QuestionType>(beanConvertManager) {
                    @Override
                    public QuestionType convert(QuestionTypeSimple QuestionTypeSimple) throws BeansException {
                        return questionTypeDao.getOne(QuestionTypeSimple.getId());
                    }
                };
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initConvert();
    }

    /**
     * 启用
     *
     * @param questionTypeId
     * @return
     */
    @Override
    public QuestionTypeVo enable(int questionTypeId) {
        QuestionType questionType = questionTypeDao.getOne(questionTypeId);
        questionType.setState(BasicEnum.STATE_ENABLE.getCode());
        return questionTypeConvert.toVo(questionType);
    }

    /**
     * 禁用
     *
     * @param questionTypeId
     * @return
     */
    @Override
    public QuestionTypeVo disable(int questionTypeId) {

        QuestionType questionType = questionTypeDao.getOne(questionTypeId);
        questionType.setState(BasicEnum.STATE_DISABLE.getCode());
        return questionTypeConvert.toVo(questionType);
    }

    /**
     * 问题类型列表
     */
    @Override
    public List<QuestionTypeListVo> getAll() {
        List<QuestionType> questionTypes = questionTypeDao.findAll();
        return questionTypeConvert.toListVos(questionTypeDao.findAll());
    }

}
