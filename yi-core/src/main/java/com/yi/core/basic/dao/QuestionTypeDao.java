/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.QuestionType;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface QuestionTypeDao extends JpaRepository<QuestionType, Integer>, JpaSpecificationExecutor<QuestionType> {

}