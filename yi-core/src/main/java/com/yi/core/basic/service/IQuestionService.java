package com.yi.core.basic.service;/*
									* Powered By [yihz-framework]
									* Web Site: yihz
									* Since 2018 - 2018
									*/

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.QuestionBo;
import com.yi.core.basic.domain.entity.Question;
import com.yi.core.basic.domain.vo.QuestionListVo;
import com.yi.core.basic.domain.vo.QuestionVo;
import com.yihz.common.persistence.Pagination;

import java.util.List;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface IQuestionService {

	Page<Question> query(Pagination<Question> query);

	/**
	 * 创建Question
	 **/
	QuestionVo addQuestion(QuestionBo question);

	/**
	 * 更新Question
	 **/
	QuestionVo updateQuestion(QuestionBo question);

	/**
	 * 删除Question
	 **/
	void removeQuestionById(int questionId);

	/**
	 * 根据ID得到QuestionVo
	 **/
	QuestionVo getQuestionVoById(int questionId);

	/**
	 * 根据ID得到QuestionListVo
	 **/
	QuestionVo getListVoById(int questionId);

	/**
	 * 分页查询: Question
	 **/
	Page<QuestionListVo> queryListVo(Pagination<Question> query);

	/**
	 * 启用对象
	 **/
	QuestionVo enable(int questionId);

	/**
	 * 禁用对象
	 **/
	QuestionVo disable(int questionId);

	/**
	 * 根据问题类型查询班助
	 * @param id
	 * @return
	 */
	List<QuestionListVo> getQuestion(Integer id);

}
