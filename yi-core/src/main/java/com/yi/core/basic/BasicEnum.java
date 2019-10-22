package com.yi.core.basic;

/**
 * 基础相关枚举
 * 
 * @author xuyh
 *
 */
public enum BasicEnum {

	/** 启用 */
	STATE_ENABLE("启动", 0),
	/** 禁用 */
	STATE_DISABLE("禁用", 1),

	/** 等待回执 */
	SEND_STATUS_WAIT_REPORT("等待回执", 1),
	/** 发送失败 */
	SEND_STATUS_FAILURE("发送失败", 2),
	/** 发送成功 */
	SEND_STATUS_SUCCESS("发送成功", 3),

	/** 系统消息 */
	MESSAGE_TYPE_SYSTEM("系统消息", 0),

	/** 积分任务类型-1签到 */
	TASK_TYPE_SIGN("签到", 1),
	/** 积分任务类型-2邀请好友 */
	TASK_TYPE_INVITE("邀请好友", 2),
	/** 积分任务类型-3评论 */
	TASK_TYPE_COMMENT("评论", 3),
	/** 积分任务类型-4订单 */
	TASK_TYPE_ORDER("订单", 4),
	/** 积分任务类型-5奖励 */
	TASK_TYPE_REWARD("奖励", 5),
	/** 积分任务类型-6邀请有礼 */
	TASK_TYPE_INVITE_PRIZE("邀请有礼", 6),

	/** 增加积分 */
	OPERATE_TYPE_ADD("新增", 0),
	/** 减少积分 */
	OPERATE_TYPE_SUBTRACT("减少", 1),

	/** 显示 */
	DISPLAY_YES("显示", 0),
	/** 隐藏 */
	DISPLAY_NO("隐藏", 1),

	/** 推送范围-全部 */
	PUSH_SCOPE_ALL("全部", 0),
	/** 推送范围-部分 */
	PUSH_SCOPE_PART("部分", 1),

	/** 阅读状态-未读 */
	READ_STATE_UNREAD("未读", 0),
	/** 阅读状态-已读 */
	READ_STATE_READ("已读", 1),

	;

	// 成员变量
	private String value;
	private Integer code;

	// 构造方法
	private BasicEnum(String value, Integer code) {
		this.code = code;
		this.value = value;
	}

	// get set 方法
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
