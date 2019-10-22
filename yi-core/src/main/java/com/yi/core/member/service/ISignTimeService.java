/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service;

import org.springframework.data.domain.Page;

import com.yi.core.member.domain.bo.SignTimeBo;
import com.yi.core.member.domain.entity.SignTime;
import com.yi.core.member.domain.vo.SignTimeListVo;
import com.yi.core.member.domain.vo.SignTimeVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ISignTimeService {

	/**
	 * 根据ID得到SignTime
	 * 
	 * @param signTimeId
	 * @return
	 */
	SignTime getSignTimeById(int signTimeId);

	/**
	 * 根据ID得到SignTimeVo
	 * 
	 * @param signTimeId
	 * @return
	 */
	SignTimeVo getSignTimeVoById(int signTimeId);

	/**
	 * 根据ID得到SignTimeListVo
	 * 
	 * @param signTimeId
	 * @return
	 */
	SignTimeListVo getSignTimeListVoById(int signTimeId);

	/**
	 * 根据Entity创建SignTime
	 * 
	 * @param signTime
	 * @return
	 */
	SignTimeVo addSignTime(SignTime signTime);

	/**
	 * 根据BO创建SignTime
	 * 
	 * @param signTimeBo
	 * @return
	 */
	SignTimeListVo addSignTime(SignTimeBo signTimeBo);

	/**
	 * 根据Entity更新SignTime
	 * 
	 * @param signTime
	 * @return
	 */
	SignTimeVo updateSignTime(SignTime signTime);

	/**
	 * 根据BO更新SignTime
	 * 
	 * @param signTimeBo
	 * @return
	 */
	SignTimeListVo updateSignTime(SignTimeBo signTimeBo);

	/**
	 * 删除SignTime
	 * 
	 * @param signTimeId
	 */
	void removeSignTimeById(int signTimeId);

	/**
	 * 分页查询: SignTime
	 * 
	 * @param query
	 * @return
	 */
	Page<SignTime> query(Pagination<SignTime> query);

	/**
	 * 分页查询: SignTimeListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<SignTimeListVo> queryListVo(Pagination<SignTime> query);

	/**
	 * 签到信息
	 *
	 * @param memberId
	 * @return
	 */
	SignTimeVo getSignInfo(int memberId);

	/**
	 * 点击签到
	 *
	 * @param memberId
	 * @return
	 */
	SignTimeVo clickSign(int memberId);

	/**
	 * 校验是否签到
	 * 
	 * @param memberId
	 * @return
	 */
	Boolean checkSign(int memberId);

}
