/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.dao;

import com.yi.core.cms.domain.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
*  *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
*/
public interface ArticleCommentDao extends JpaRepository<ArticleComment, Integer> ,JpaSpecificationExecutor<ArticleComment> {

}