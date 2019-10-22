/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import org.springframework.data.domain.Page;

import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.domain.bo.IntegralRecordBo;
import com.yi.core.basic.domain.entity.IntegralRecord;
import com.yi.core.basic.domain.entity.IntegralTask;
import com.yi.core.basic.domain.vo.IntegralRecordListVo;
import com.yi.core.basic.domain.vo.IntegralRecordVo;
import com.yi.core.member.domain.entity.Member;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IIntegralRecordService {

	Page<IntegralRecord> query(Pagination<IntegralRecord> query);

	/**
	 * 创建IntegralRecordBo
	 **/
	IntegralRecordVo addIntegralRecord(IntegralRecordBo integralRecord);

	/**
	 * 创建IntegralRecord
	 **/
	IntegralRecordVo addIntegralRecord(IntegralRecord integralRecord);

	/**
	 * 更新IntegralRecord
	 **/
	IntegralRecordVo updateIntegralRecord(IntegralRecordBo integralRecord);

	/**
	 * 删除IntegralRecord
	 **/
	void removeIntegralRecordById(int integralRecordId);

	/**
	 * 根据ID得到IntegralRecordVo
	 **/
	IntegralRecordVo getIntegralRecordVoById(int integralRecordId);

	/**
	 * 根据ID得到IntegralRecordListVo
	 **/
	IntegralRecordVo getListVoById(int integralRecordId);

	/**
	 * 分页查询: IntegralRecord
	 **/
	Page<IntegralRecordListVo> queryListVo(Pagination<IntegralRecord> query);

	/**
	 * 根据任务类型新增积分记录
	 * 
	 * @param member
	 * @param integralTask
	 */
	void addByTaskType(Member member, IntegralTask integralTask);

	/**
	 * 订单相关 增加积分记录
	 * 
	 * @param member
	 * @param integral
	 * @param operateType
	 */
	void addByOrder(Member member, int integral, BasicEnum operateType);

	/**
	 * 奖励相关 增加积分记录
	 * 
	 * @param member
	 * @param integral
	 */
	void addByRewrd(Member member, int integral);

	/**
	 * 邀请有礼 领取奖品-积分
	 * 
	 * @param member
	 * @param integral
	 * @param prizeId
	 */
	void addByReceivePrize(Member member, int integral, Integer prizeId);

	/**
	 * 检查是否领取过 奖品
	 * 
	 * @param memberId
	 * @param integral
	 * @param prizeId
	 */
	boolean checkReceivePrize(Integer memberId, int integral, Integer prizeId);

}
