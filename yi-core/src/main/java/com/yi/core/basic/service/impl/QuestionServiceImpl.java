/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.yi.core.common.Deleted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.dao.QuestionDao;
import com.yi.core.basic.domain.bo.QuestionBo;
import com.yi.core.basic.domain.entity.Question;
import com.yi.core.basic.domain.entity.Question_;
import com.yi.core.basic.domain.simple.QuestionSimple;
import com.yi.core.basic.domain.vo.QuestionListVo;
import com.yi.core.basic.domain.vo.QuestionVo;
import com.yi.core.basic.service.IQuestionService;
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
//@CacheConfig(cacheNames = "question")
@Service
@Transactional
public class QuestionServiceImpl implements IQuestionService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private QuestionDao questionDao;

    private EntityListVoBoSimpleConvert<Question, QuestionBo, QuestionVo, QuestionSimple, QuestionListVo>
            questionConvert;

    /**
     * 分页查询Question
     **/
    @Override
    public Page<Question> query(Pagination<Question> query) {
        query.setEntityClazz(Question.class);
        Page<Question> page = questionDao.findAll(query, query.getPageRequest());
        return page;
    }

    /**
     * 创建Question
     **/
    @Override
    public QuestionVo addQuestion(QuestionBo questionBo) {
        questionBo.setCreateTime(new Date());
        Question question = questionConvert.toEntity(questionBo);
        return questionConvert.toVo(questionDao.save(question));
    }

    /**
     * 更新Question
     **/
//    @CacheEvict(key = "#questionBo.getId()")
    @Override
    public QuestionVo updateQuestion(QuestionBo questionBo) {
        Question dbQuestion = questionDao.getOne(questionBo.getId());
        AttributeReplication.copying(questionConvert.toEntity(questionBo), dbQuestion, Question_.state, Question_
                .questionType, Question_.askQuestion, Question_.answerQuestion, Question_.sort);
        return questionConvert.toVo(dbQuestion);
    }

    /**
     * 删除Question
     **/
//    @CacheEvict(key = "#id")
    @Override
    public void removeQuestionById(int id) {
        questionDao.deleteById(id);
    }

    /**
     * 根据ID得到Question
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public QuestionVo getQuestionVoById(int id) {

        return questionConvert.toVo(this.questionDao.getOne(id));
    }

    /**
     * 根据ID得到QuestionListVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public QuestionVo getListVoById(int id) {
        return questionConvert.toVo(this.questionDao.getOne(id));
    }

    /**
     * 分页查询: Question
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<QuestionListVo> queryListVo(Pagination<Question> query) {

        Page<Question> pages = this.query(query);

        List<QuestionListVo> vos = questionConvert.toListVos(pages.getContent());
        return new PageImpl<QuestionListVo>(vos, query.getPageRequest(), pages.getTotalElements());

    }

    protected void initConvert() {
        this.questionConvert = new EntityListVoBoSimpleConvert<Question, QuestionBo, QuestionVo, QuestionSimple,
                QuestionListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<Question, QuestionVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<Question, QuestionVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(QuestionVo QuestionVo, Question Question) {

                    }
                };
            }

            @Override
            protected BeanConvert<Question, QuestionListVo> createEntityToListVoConvert(BeanConvertManager
                                                                                                beanConvertManager) {
                return new AbstractBeanConvert<Question, QuestionListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(QuestionListVo QuestionListVo, Question Question) {

                    }
                };
            }

            @Override
            protected BeanConvert<Question, QuestionBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<Question, QuestionBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(QuestionBo QuestionBo, Question Question) {

                    }
                };
            }

            @Override
            protected BeanConvert<QuestionBo, Question> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<QuestionBo, Question>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<Question, QuestionSimple> createEntityToSimpleConvert(BeanConvertManager
                                                                                                beanConvertManager) {
                return new AbstractBeanConvert<Question, QuestionSimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<QuestionSimple, Question> createSimpleToEntityConvert(BeanConvertManager
                                                                                                beanConvertManager) {
                return new AbstractBeanConvert<QuestionSimple, Question>(beanConvertManager) {
                    @Override
                    public Question convert(QuestionSimple QuestionSimple) throws BeansException {
                        return questionDao.getOne(QuestionSimple.getId());
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
     * 启用对象
     **/
    @Override
    public QuestionVo enable(int questionId) {
        Question question = questionDao.getOne(questionId);
        question.setState(BasicEnum.STATE_ENABLE.getCode());
        return questionConvert.toVo(question);
    }

    /**
     * 禁用对象
     **/
    @Override
    public QuestionVo disable(int questionId) {
        Question question = questionDao.getOne(questionId);
        question.setState(BasicEnum.STATE_DISABLE.getCode());
        return questionConvert.toVo(question);
    }

    /**
     * 根据问题类型查询班助
     *
     * @param id
     * @return
     */
//    @Cacheable(key = "#id", unless = "#result eq null")
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<QuestionListVo> getQuestion(Integer id) {
        return questionConvert.toListVos(questionDao.findByQuestionTypeIdAndDeleted(id, Deleted.DEL_FALSE));
    }

}
