package com.yi.core.basic.service;/*
									* Powered By [yihz-framework]
									* Web Site: yihz
									* Since 2018 - 2018
									*/

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.BasicInfoBo;
import com.yi.core.basic.domain.entity.BasicInfo;
import com.yi.core.basic.domain.vo.BasicInfoListVo;
import com.yi.core.basic.domain.vo.BasicInfoVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IBasicInfoService {

	Page<BasicInfo> query(Pagination<BasicInfo> query);

	/**
	 * 创建BasicInfo
	 **/
	BasicInfoVo addBasicInfo(BasicInfoBo basicInfo);

	/**
	 * 更新BasicInfo
	 **/
	BasicInfoVo updateBasicInfo(BasicInfoBo basicInfo);

	/**
	 * 删除BasicInfo
	 **/
	void removeBasicInfoById(int basicInfoId);

	/**
	 * 根据ID得到BasicInfoVo
	 **/
	BasicInfoVo getBasicInfoVoById(int basicInfoId);

	/**
	 * 根据ID得到BasicInfoListVo
	 **/
	BasicInfoVo getListVoById(int basicInfoId);

	/**
	 * 分页查询: BasicInfo
	 **/
	Page<BasicInfoListVo> queryListVo(Pagination<BasicInfo> query);

	/**
	 * 查询全部数据
	 **/
	BasicInfoVo getAll();
}
