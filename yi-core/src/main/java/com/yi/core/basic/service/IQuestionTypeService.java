package com.yi.core.basic.service;/*
									* Powered By [yihz-framework]
									* Web Site: yihz
									* Since 2018 - 2018
									*/

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.QuestionTypeBo;
import com.yi.core.basic.domain.entity.QuestionType;
import com.yi.core.basic.domain.vo.QuestionTypeListVo;
import com.yi.core.basic.domain.vo.QuestionTypeVo;
import com.yihz.common.persistence.Pagination;

import java.util.List;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IQuestionTypeService {

	Page<QuestionType> query(Pagination<QuestionType> query);

	/**
	 * 创建QuestionType
	 **/
	QuestionTypeVo addQuestionType(QuestionTypeBo questionType);

	/**
	 * 更新QuestionType
	 **/
	QuestionTypeVo updateQuestionType(QuestionTypeBo questionType);

	/**
	 * 删除QuestionType
	 **/
	void removeQuestionTypeById(int questionTypeId);

	/**
	 * 根据ID得到QuestionTypeVo
	 **/
	QuestionTypeVo getQuestionTypeVoById(int questionTypeId);

	/**
	 * 根据ID得到QuestionTypeListVo
	 **/
	QuestionTypeVo getListVoById(int questionTypeId);

	/**
	 * 分页查询: QuestionType
	 **/
	Page<QuestionTypeListVo> queryListVo(Pagination<QuestionType> query);

	QuestionTypeVo enable(int questionTypeId);

	QuestionTypeVo disable(int questionTypeId);

	/**
	 * 问题类型列表
	 */
	List<QuestionTypeListVo> getAll();

}
