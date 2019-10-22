/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import com.yi.core.commodity.domain.entity.Comment;
import com.yihz.common.orm.BaseDao;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface CommentDao extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {

	@EntityGraph(attributePaths = { "member" })
	List<Comment> findByCommodity_id(int commodityId);


}