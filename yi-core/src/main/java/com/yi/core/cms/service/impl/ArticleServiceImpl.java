/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.attachment.service.IAttachmentService;
import com.yi.core.cms.CmsEnum;
import com.yi.core.cms.dao.ArticleDao;
import com.yi.core.cms.domain.bo.ArticleBo;
import com.yi.core.cms.domain.entity.Article;
import com.yi.core.cms.domain.entity.Article_;
import com.yi.core.cms.domain.simple.ArticleSimple;
import com.yi.core.cms.domain.vo.ArticleListVo;
import com.yi.core.cms.domain.vo.ArticleVo;
import com.yi.core.cms.service.IArticleService;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.common.Deleted;
import com.yi.core.common.FileType;
import com.yi.core.common.ObjectType;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 文章
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
//@CacheConfig(cacheNames = "article")
@Service
@Transactional
public class ArticleServiceImpl implements IArticleService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private ArticleDao articleDao;

    @Resource
    private IAttachmentService attachmentService;

    private EntityListVoBoSimpleConvert<Article, ArticleBo, ArticleVo, ArticleSimple, ArticleListVo> articleConvert;

    @Override
    public Page<Article> query(Pagination<Article> query) {
        query.setEntityClazz(Article.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Article_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.asc(root.get(Article_.sort)));
            list1.add(criteriaBuilder.desc(root.get(Article_.createTime)));
        }));
        Page<Article> page = articleDao.findAll(query, query.getPageRequest());
        return page;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<ArticleListVo> queryListVo(Pagination<Article> query) {
        query.setEntityClazz(Article.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Article_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.asc(root.get(Article_.sort)));
            list1.add(criteriaBuilder.desc(root.get(Article_.createTime)));
        }));
        Page<Article> pages = articleDao.findAll(query, query.getPageRequest());
        List<ArticleListVo> vos = articleConvert.toListVos(pages.getContent());
        return new PageImpl<ArticleListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<ArticleListVo> queryListVoForApp(Pagination<Article> query) {
        query.setEntityClazz(Article.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Article_.deleted), Deleted.DEL_FALSE)));
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Article_.state), CmsEnum.DISPLAY_YES.getCode
					())));
            list1.add(criteriaBuilder.asc(root.get(Article_.sort)));
            list1.add(criteriaBuilder.desc(root.get(Article_.createTime)));
        }));
        Page<Article> pages = articleDao.findAll(query, query.getPageRequest());
        List<ArticleListVo> vos = articleConvert.toListVos(pages.getContent());
        return new PageImpl<ArticleListVo>(vos, query.getPageRequest(), pages.getTotalElements());

    }

    @Override
    public Article addArticle(Article article) {
        if (StringUtils.isBlank(article.getTitle())) {
            LOG.error("文章标题不能为空");
            throw new BusinessException("文章标题不能为空");
        }
        int checkTitle = articleDao.countByTitleAndDeleted(article.getTitle(), Deleted.DEL_FALSE);
        if (checkTitle > 0) {
            LOG.error("文章标题不能重复");
            throw new BusinessException("文章标题不能重复");
        }
        return articleDao.save(article);
    }

    @Override
    public ArticleVo addArticle(ArticleBo articleBo) {
        Article dbArticle = this.addArticle(articleConvert.toEntity(articleBo));
        // 保存图片
        if (CollectionUtils.isNotEmpty(articleBo.getAttachmentVos())) {
            for (AttachmentVo tmp : articleBo.getAttachmentVos()) {
                tmp.setObjectId(dbArticle.getId());
                tmp.setFileType(FileType.PICTURES);
                tmp.setObjectType(ObjectType.ARTICLE);
                tmp.setDescription(dbArticle.getTitle());
                tmp.setFilePath(tmp.getUrl());
            }
            attachmentService.saveAll(articleBo.getAttachmentVos());
        }
        return articleConvert.toVo(dbArticle);
    }

    @Override
    public Article updateArticle(Article article) {
        if (StringUtils.isBlank(article.getTitle())) {
            LOG.error("文章标题不能为空");
            throw new BusinessException("文章标题不能为空");
        }
        int checkTitle = articleDao.countByTitleAndDeletedAndIdNot(article.getTitle(), Deleted.DEL_FALSE, article
				.getId());
        if (checkTitle > 0) {
            LOG.error("文章标题不能重复");
            throw new BusinessException("文章标题不能重复");
        }
        Article dbArticle = articleDao.getOne(article.getId());
        AttributeReplication.copying(article, dbArticle, Article_.title, Article_.subtitle, Article_.author, Article_
						.content, Article_.url, Article_.state, Article_.sort,
                Article_.imgPath);
        dbArticle.setCommodities(article.getCommodities());
        return dbArticle;
    }

//    @CacheEvict(key = "#articleBo.getId()")
    @Override
    public ArticleVo updateArticle(ArticleBo articleBo) {
        // 更新数据
        Article dbArticle = this.updateArticle(articleConvert.toEntity(articleBo));
        // 保存图片
        if (CollectionUtils.isNotEmpty(articleBo.getAttachmentVos())) {
            // 获取图片集合 整理图片
            List<AttachmentVo> dbAttachmentVos = attachmentService.findByObjectIdAndObjectType(articleBo.getId(),
					ObjectType.ARTICLE);
            if (CollectionUtils.isEmpty(dbAttachmentVos)) {
                for (AttachmentVo tmp : articleBo.getAttachmentVos()) {
                    if (tmp != null) {
                        tmp.setObjectId(articleBo.getId());
                        tmp.setFileType(FileType.PICTURES);
                        tmp.setObjectType(ObjectType.ARTICLE);
                        tmp.setDescription(articleBo.getTitle());
                        tmp.setFilePath(tmp.getUrl());
                    }
                }
                attachmentService.saveAll(articleBo.getAttachmentVos());
            } else {
                // 需要新增的数据
                List<AttachmentVo> saveLists = articleBo.getAttachmentVos().stream().filter(e1 -> e1.getAttachId() ==
						null).collect(Collectors.toList());
                // 需要删除的数据
                List<AttachmentVo> deleteLists = dbAttachmentVos.stream()
                        .filter(e1 -> articleBo.getAttachmentVos().stream().noneMatch(e2 -> e1.getAttachId().equals
								(e2.getAttachId()))).collect(Collectors.toList());
                for (AttachmentVo tmp : saveLists) {
                    if (tmp != null) {
                        tmp.setObjectId(articleBo.getId());
                        tmp.setFileType(FileType.PICTURES);
                        tmp.setObjectType(ObjectType.ARTICLE);
                        tmp.setDescription(articleBo.getTitle());
                        tmp.setFilePath(tmp.getUrl());
                    }
                }
                attachmentService.saveAll(saveLists);
                attachmentService.deleteList(deleteLists);
            }
        }
        return articleConvert.toVo(dbArticle);
    }

    @Override
    public Article getArticleById(int articleId) {
        if (articleDao.existsById(articleId)) {
            return articleDao.getOne(articleId);
        }
        return null;
    }

//    @Cacheable(key = "#articleId", unless = "#result eq null")
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ArticleVo getArticleVoByIdForApp(int articleId) {
        Article dbArticle = this.getArticleById(articleId);
        if (dbArticle != null) {
            dbArticle.setCommodities(dbArticle.getCommodities().stream()
                    .filter(e -> CommodityEnum.AUDIT_STATE_PASS.getCode().equals(e.getAuditState()) && CommodityEnum.SHELF_STATE_ON
							.getCode().equals(e.getShelfState())).collect(Collectors.toList()));
            // 显示的评论
            dbArticle.setArticleComments(dbArticle.getArticleComments().stream().filter(e -> CmsEnum.DISPLAY_YES
					.getCode().equals(e.getState())).collect(Collectors.toList()));
            ArticleVo articleVo = articleConvert.toVo(dbArticle);
            articleVo.setAttachmentVos(attachmentService.findByObjectIdAndObjectType(articleId, ObjectType.ARTICLE));
            return articleVo;
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ArticleVo getArticleVoById(int articleId) {
        Article dbArticle = this.getArticleById(articleId);
        if (dbArticle != null) {
            dbArticle.setCommodities(dbArticle.getCommodities().stream()
                    .filter(e -> CommodityEnum.AUDIT_STATE_PASS.getCode().equals(e.getAuditState()) && CommodityEnum.SHELF_STATE_ON
							.getCode().equals(e.getShelfState())).collect(Collectors.toList()));
            // 显示的评论
            dbArticle.setArticleComments(dbArticle.getArticleComments().stream().filter(e -> CmsEnum.DISPLAY_YES
					.getCode().equals(e.getState())).collect(Collectors.toList()));
            ArticleVo articleVo = articleConvert.toVo(dbArticle);
            articleVo.setAttachmentVos(attachmentService.findByObjectIdAndObjectType(articleId, ObjectType.ARTICLE));
            return articleVo;
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ArticleListVo getArticleListVoById(int articleId) {
        return articleConvert.toListVo(this.getArticleById(articleId));
    }

//    @CacheEvict(key = "#articleId")
    @Override
    public void removeArticleById(int articleId) {
        Article dbArticle = articleDao.getOne(articleId);
        if (dbArticle != null) {
            dbArticle.setDeleted(Deleted.DEL_TRUE);
            dbArticle.setDelTime(new Date());
            // 删除商品中间表
            dbArticle.setCommodities(null);
        }
    }

    protected void initConvert() {
        this.articleConvert = new EntityListVoBoSimpleConvert<Article, ArticleBo, ArticleVo, ArticleSimple,
				ArticleListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<Article, ArticleVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<Article, ArticleVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(ArticleVo ArticleVo, Article Article) {

                    }
                };
            }

            @Override
            protected BeanConvert<Article, ArticleListVo> createEntityToListVoConvert(BeanConvertManager
																							  beanConvertManager) {
                return new AbstractBeanConvert<Article, ArticleListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(ArticleListVo ArticleListVo, Article Article) {

                    }
                };
            }

            @Override
            protected BeanConvert<Article, ArticleBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<Article, ArticleBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(ArticleBo ArticleBo, Article Article) {

                    }
                };
            }

            @Override
            protected BeanConvert<ArticleBo, Article> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<ArticleBo, Article>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<Article, ArticleSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<Article, ArticleSimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<ArticleSimple, Article> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<ArticleSimple, Article>(beanConvertManager) {
                    @Override
                    public Article convert(ArticleSimple ArticleSimple) throws BeansException {
                        return articleDao.getOne(ArticleSimple.getId());
                    }
                };
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initConvert();
    }

//    @CacheEvict(key = "#articleId")
    @Override
    public void updateDisplayState(int articleId) {
        Article dbArticle = this.getArticleById(articleId);
        if (dbArticle != null) {
            if (CmsEnum.DISPLAY_NO.getCode().equals(dbArticle.getState())) {
                dbArticle.setState(CmsEnum.DISPLAY_YES.getCode());
            } else if (CmsEnum.DISPLAY_YES.getCode().equals(dbArticle.getState())) {
                dbArticle.setState(CmsEnum.DISPLAY_NO.getCode());
            }
        }
    }

//    @CacheEvict(key = "#p0")
    @Override
    public void updateReadQuantity(int articleId, int amount) {
        Article dbArticle = this.getArticleById(articleId);
        if (dbArticle != null) {
            dbArticle.setReadQuantity(dbArticle.getReadQuantity() + amount);
        }
    }

//    @CacheEvict(key = "#p0")
    @Override
    public void updateCommentQuantity(int articleId, int amount) {
        Article dbArticle = this.getArticleById(articleId);
        if (dbArticle != null) {
            dbArticle.setCommentQuantity(dbArticle.getCommentQuantity() + amount);
        }
    }

}
