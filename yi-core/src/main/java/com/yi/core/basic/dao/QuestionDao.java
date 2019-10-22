/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.Question;

import java.util.List;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface QuestionDao extends JpaRepository<Question, Integer>, JpaSpecificationExecutor<Question> {
    List<Question> findByQuestionTypeIdAndDeleted(Integer questionType, Integer deleted);
}