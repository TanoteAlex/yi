/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class Question implements java.io.Serializable {

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
	@NotNull
	private QuestionType questionType;
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

	private Date delTime;
	// columns END

	public Question() {
	}

	public Question(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 255)
	public String getAskQuestion() {
		return this.askQuestion;
	}

	public void setAskQuestion(String askQuestion) {
		this.askQuestion = askQuestion;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 65535)
	public String getAnswerQuestion() {
		return this.answerQuestion;
	}

	public void setAnswerQuestion(String answerQuestion) {
		this.answerQuestion = answerQuestion;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 3)
	public int getDeleted() {
		return this.deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "QUESTION_TYPE_ID", nullable = false) })
	public QuestionType getQuestionType() {
		return questionType;
	}

}