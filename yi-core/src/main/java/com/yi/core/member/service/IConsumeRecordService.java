/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service;

import org.springframework.data.domain.Page;

import com.yi.core.member.domain.bo.ConsumeRecordBo;
import com.yi.core.member.domain.entity.ConsumeRecord;
import com.yi.core.member.domain.vo.ConsumeRecordListVo;
import com.yi.core.member.domain.vo.ConsumeRecordVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IConsumeRecordService {

	/**
	 * 根据ID得到ConsumeRecord
	 * 
	 * @param consumeRecordId
	 * @return
	 */
	ConsumeRecord getConsumeRecordById(int consumeRecordId);

	/**
	 * 根据ID得到ConsumeRecordVo
	 * 
	 * @param consumeRecordId
	 * @return
	 */
	ConsumeRecordVo getConsumeRecordVoById(int consumeRecordId);

	/**
	 * 根据ID得到ConsumeRecordListVo
	 * 
	 * @param consumeRecordId
	 * @return
	 */
	ConsumeRecordListVo getConsumeRecordListVoById(int consumeRecordId);

	/**
	 * 根据Entity创建ConsumeRecord
	 * 
	 * @param consumeRecord
	 * @return
	 */
	ConsumeRecordVo addConsumeRecord(ConsumeRecord consumeRecord);

	/**
	 * 根据BO创建ConsumeRecord
	 * 
	 * @param consumeRecordBo
	 * @return
	 */
	ConsumeRecordListVo addConsumeRecord(ConsumeRecordBo consumeRecordBo);

	/**
	 * 根据Entity更新ConsumeRecord
	 * 
	 * @param consumeRecord
	 * @return
	 */
	ConsumeRecordVo updateConsumeRecord(ConsumeRecord consumeRecord);

	/**
	 * 根据BO更新ConsumeRecord
	 * 
	 * @param consumeRecordBo
	 * @return
	 */
	ConsumeRecordListVo updateConsumeRecord(ConsumeRecordBo consumeRecordBo);

	/**
	 * 删除ConsumeRecord
	 * 
	 * @param consumeRecordId
	 */
	void removeConsumeRecordById(int consumeRecordId);

	/**
	 * 分页查询: ConsumeRecord
	 * 
	 * @param query
	 * @return
	 */
	Page<ConsumeRecord> query(Pagination<ConsumeRecord> query);

	/**
	 * 分页查询: ConsumeRecordListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<ConsumeRecordListVo> queryListVo(Pagination<ConsumeRecord> query);

}
