/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.SmsRecordBo;
import com.yi.core.basic.domain.entity.SmsRecord;
import com.yi.core.basic.domain.vo.SmsRecordListVo;
import com.yi.core.basic.domain.vo.SmsRecordVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ISmsRecordService {

	/**
	 * 根据ID得到SmsRecord
	 * 
	 * @param smsRecordId
	 * @return
	 */
	SmsRecordVo getSmsRecordById(int smsRecordId);

	/**
	 * 根据ID得到SmsRecordVo
	 * 
	 * @param smsRecordId
	 * @return
	 */
	SmsRecordVo getSmsRecordVoById(int smsRecordId);

	/**
	 * 根据ID得到SmsRecordListVo
	 * 
	 * @param smsRecordId
	 * @return
	 */
	SmsRecordVo getSmsRecordListVoById(int smsRecordId);

	/**
	 * 根据Entity创建SmsRecord
	 * 
	 * @param smsRecord
	 * @return
	 */
	SmsRecordVo addSmsRecord(SmsRecord smsRecord);

	/**
	 * 根据BO创建SmsRecord
	 * 
	 * @param smsRecordBo
	 * @return
	 */
	SmsRecordVo addSmsRecord(SmsRecordBo smsRecordBo);

	/**
	 * 根据Entity更新SmsRecord
	 * 
	 * @param smsRecord
	 * @return
	 */
	SmsRecordVo updateSmsRecord(SmsRecord smsRecord);

	/**
	 * 根据BO更新SmsRecord
	 * 
	 * @param smsRecordBo
	 * @return
	 */
	SmsRecordVo updateSmsRecord(SmsRecordBo smsRecordBo);

	/**
	 * 删除SmsRecord
	 * 
	 * @param smsRecordId
	 */
	void removeSmsRecordById(int smsRecordId);

	/**
	 * 分页查询: SmsRecord
	 * 
	 * @param query
	 * @return
	 */
	Page<SmsRecord> query(Pagination<SmsRecord> query);

	/**
	 * 分页查询: SmsRecordListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<SmsRecordListVo> queryListVo(Pagination<SmsRecord> query);

	/**
	 * 查询大于 给定发送日期的 待回执 短信
	 * 
	 * @param SmsRecord.sendDate
	 * @param SmsRecord.smsState
	 * @return
	 */
	List<SmsRecord> queryWaitReportSmsRecords(SmsRecord smsRecord);

}
