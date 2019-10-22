package com.yi.core.basic.domain.vo;/*
									* Powered By [yihz-framework]
									* Web Site: yihz
									* Since 2018 - 2018
									*/

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.basic.domain.simple.QuestionTypeSimple;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class QuestionListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 问题ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 问题类型(question_type表ID)
	 */
	private QuestionTypeSimple questionType;
	/**
	 * 提出问题
	 */
	@NotBlank
	@Length(max = 255)
	private String askQuestion;
	/**
	 * 回答问题
	 */
	@Length(max = 65535)
	private String answerQuestion;
	/**
	 * 排序
	 */
	@Max(127)
	private int sort;
	/**
	 * 显示（0显示1不显示）
	 */
	@Max(127)
	private int state;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	@Max(127)
	private int deleted;
	/**
	 * 删除时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public QuestionTypeSimple getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionTypeSimple questionType) {
		this.questionType = questionType;
	}

	public String getAskQuestion() {
		return this.askQuestion;
	}

	public void setAskQuestion(String askQuestion) {
		this.askQuestion = askQuestion;
	}

	public String getAnswerQuestion() {
		return this.answerQuestion;
	}

	public void setAnswerQuestion(String answerQuestion) {
		this.answerQuestion = answerQuestion;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDeleted() {
		return this.deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

}