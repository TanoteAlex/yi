/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.attachment.service.IAttachmentService;
import com.yi.core.cms.CmsEnum;
import com.yi.core.cms.dao.ArticleCommentDao;
import com.yi.core.cms.domain.bo.ArticleCommentBo;
import com.yi.core.cms.domain.entity.ArticleComment;
import com.yi.core.cms.domain.entity.ArticleComment_;
import com.yi.core.cms.domain.simple.ArticleCommentSimple;
import com.yi.core.cms.domain.vo.ArticleCommentListVo;
import com.yi.core.cms.domain.vo.ArticleCommentVo;
import com.yi.core.cms.service.IArticleCommentService;
import com.yi.core.common.Deleted;
import com.yi.core.common.FileType;
import com.yi.core.common.ObjectType;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 文章评论
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class ArticleCommentServiceImpl implements IArticleCommentService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(ArticleCommentServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private ArticleCommentDao articleCommentDao;

    @Resource
    private IAttachmentService attachmentService;

    private EntityListVoBoSimpleConvert<ArticleComment, ArticleCommentBo, ArticleCommentVo, ArticleCommentSimple,
			ArticleCommentListVo> articleCommentConvert;

    @Override
    public ArticleComment getArticleCommentById(int articleCommentId) {
        return articleCommentDao.getOne(articleCommentId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ArticleCommentVo getArticleCommentVoById(int articleCommentId) {
        ArticleCommentVo dbArticleCommentVo = articleCommentConvert.toVo(this.articleCommentDao.getOne
				(articleCommentId));
        List<AttachmentVo> attachmentVos = attachmentService.findByObjectIdAndObjectType(dbArticleCommentVo.getId(),
				ObjectType.ARTICLE_COMMENT);
        dbArticleCommentVo.setAttachmentVos(attachmentVos);
        return dbArticleCommentVo;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ArticleCommentListVo getArticleCommentListVoById(int articleCommentId) {
        return articleCommentConvert.toListVo(this.articleCommentDao.getOne(articleCommentId));
    }

    @Override
    public ArticleComment addArticleComment(ArticleComment articleComment) {
        if (articleComment.getState() == null) {
            articleComment.setState(CmsEnum.DISPLAY_NO.getCode());// 评论默认不显示
        }
        articleComment.setCommentTime(new Date());
        return articleCommentDao.save(articleComment);
    }

    @Override
    public ArticleCommentListVo addArticleComment(ArticleCommentBo articleCommentBo) {
        ArticleComment dbArticleComment = this.addArticleComment(articleCommentConvert.toEntity(articleCommentBo));
        // 图片
        if (CollectionUtils.isNotEmpty(articleCommentBo.getAttachmentVos())) {
            for (AttachmentVo tmp : articleCommentBo.getAttachmentVos()) {
                tmp.setObjectId(dbArticleComment.getId());
                tmp.setFileType(FileType.PICTURES);
                tmp.setObjectType(ObjectType.ARTICLE_COMMENT);
                tmp.setDescription(dbArticleComment.getCommentContent());
                tmp.setFilePath(tmp.getUrl());
            }
            attachmentService.saveAll(articleCommentBo.getAttachmentVos());
        }
        return articleCommentConvert.toListVo(dbArticleComment);
    }

    @Override
    public ArticleComment updateArticleComment(ArticleComment articleComment) {

        ArticleComment dbArticleComment = articleCommentDao.getOne(articleComment.getId());
        AttributeReplication.copying(articleComment, dbArticleComment, ArticleComment_.commentator, ArticleComment_
						.commentContent, ArticleComment_.replyContent,
                ArticleComment_.replyTime, ArticleComment_.state);
        return dbArticleComment;
    }

    @Override
    public ArticleCommentVo updateArticleComment(ArticleCommentBo articleCommentBo) {
        return articleCommentConvert.toVo(this.updateArticleComment(articleCommentConvert.toEntity(articleCommentBo)));
    }

    @Override
    public void removeArticleCommentById(int articleCommentId) {
        ArticleComment dbArticleComment = articleCommentDao.getOne(articleCommentId);
        if (dbArticleComment != null) {
            dbArticleComment.setDeleted(Deleted.DEL_TRUE);
            dbArticleComment.setDelTime(new Date());
        }
    }

    @Override
    public Page<ArticleComment> query(Pagination<ArticleComment> query) {
        query.setEntityClazz(ArticleComment.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(ArticleComment_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.desc(root.get(ArticleComment_.commentTime)));
        }));
        Page<ArticleComment> page = articleCommentDao.findAll(query, query.getPageRequest());
        return page;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<ArticleCommentListVo> queryListVo(Pagination<ArticleComment> query) {

        Page<ArticleComment> pages = this.query(query);

        List<ArticleCommentListVo> vos = articleCommentConvert.toListVos(pages.getContent());
        return new PageImpl<ArticleCommentListVo>(vos, query.getPageRequest(), pages.getTotalElements());

    }

    protected void initConvert() {
        this.articleCommentConvert = new EntityListVoBoSimpleConvert<ArticleComment, ArticleCommentBo,
				ArticleCommentVo, ArticleCommentSimple, ArticleCommentListVo>(
                beanConvertManager) {
            @Override
            protected BeanConvert<ArticleComment, ArticleCommentVo> createEntityToVoConvert(BeanConvertManager
																									beanConvertManager) {
                return new AbstractBeanConvert<ArticleComment, ArticleCommentVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(ArticleCommentVo ArticleCommentVo, ArticleComment ArticleComment) {

                    }
                };
            }

            @Override
            protected BeanConvert<ArticleComment, ArticleCommentListVo> createEntityToListVoConvert
					(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<ArticleComment, ArticleCommentListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(ArticleCommentListVo ArticleCommentListVo, ArticleComment
							ArticleComment) {

                    }
                };
            }

            @Override
            protected BeanConvert<ArticleComment, ArticleCommentBo> createEntityToBoConvert(BeanConvertManager
																									beanConvertManager) {
                return new AbstractBeanConvert<ArticleComment, ArticleCommentBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(ArticleCommentBo ArticleCommentBo, ArticleComment ArticleComment) {

                    }
                };
            }

            @Override
            protected BeanConvert<ArticleCommentBo, ArticleComment> createBoToEntityConvert(BeanConvertManager
																									beanConvertManager) {
                return new AbstractBeanConvert<ArticleCommentBo, ArticleComment>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<ArticleComment, ArticleCommentSimple> createEntityToSimpleConvert
					(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<ArticleComment, ArticleCommentSimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<ArticleCommentSimple, ArticleComment> createSimpleToEntityConvert
					(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<ArticleCommentSimple, ArticleComment>(beanConvertManager) {
                    @Override
                    public ArticleComment convert(ArticleCommentSimple ArticleCommentSimple) throws BeansException {
                        return articleCommentDao.getOne(ArticleCommentSimple.getId());
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
     * 爱生活评论
     *
     * @param id
     * @param replys
     * @return
     */
    @Override
    public ArticleCommentVo reply(int id, String replys) {
        ArticleComment articleComment = articleCommentDao.getOne(id);
        articleComment.setReplyContent(replys);
        articleComment.setReplyTime(new Date());
        return articleCommentConvert.toVo(articleComment);
    }

    /**
     * 显示爱生活评论
     *
     * @param id
     * @param state
     * @return
     */
    @Override
    public ArticleCommentVo display(int id, int state) {
        ArticleComment articleComment = articleCommentDao.getOne(id);
        articleComment.setState(CmsEnum.DISPLAY_YES.getCode());
        return articleCommentConvert.toVo(articleComment);
    }

    /**
     * 隐藏爱生活评论
     *
     * @param id
     * @param state
     * @return
     */
    @Override
    public ArticleCommentVo hide(int id, int state) {
        ArticleComment articleComment = articleCommentDao.getOne(id);
        articleComment.setState(CmsEnum.DISPLAY_NO.getCode());
        return articleCommentConvert.toVo(articleComment);
    }

}
