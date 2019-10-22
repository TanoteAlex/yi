/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.service;

import org.springframework.data.domain.Page;

import com.yi.core.auth.domain.bo.RescBo;
import com.yi.core.auth.domain.entity.Resc;
import com.yi.core.auth.domain.vo.RescListVo;
import com.yi.core.auth.domain.vo.RescVo;
import com.yihz.common.persistence.Pagination;

import java.util.List;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IRescService {

	/**
	 * 根据ID得到Resc
	 * 
	 * @param rescId
	 * @return
	 */
	Resc getRescById(int rescId);

	/**
	 * 根据ID得到RescVo
	 * 
	 * @param rescId
	 * @return
	 */
	RescVo getRescVoById(int rescId);

	/**
	 * 根据ID得到RescListVo
	 * 
	 * @param rescId
	 * @return
	 */
	RescListVo getRescListVoById(int rescId);

	/**
	 * 根据Entity创建Resc
	 * 
	 * @param resc
	 * @return
	 */
	Resc addResc(Resc resc);

	/**
	 * 根据BO创建Resc
	 * 
	 * @param rescBo
	 * @return
	 */
	RescVo addResc(RescBo rescBo);

	/**
	 * 根据Entity更新Resc
	 * 
	 * @param resc
	 * @return
	 */
	Resc updateResc(Resc resc);

	/**
	 * 根据BO更新Resc
	 * 
	 * @param rescBo
	 * @return
	 */
	RescVo updateResc(RescBo rescBo);

	/**
	 * 删除Resc
	 * 
	 * @param rescId
	 */
	void removeRescById(int rescId);

	/**
	 * 分页查询: Resc
	 * 
	 * @param query
	 * @return
	 */
	Page<Resc> query(Pagination<Resc> query);

	/**
	 * 分页查询: RescListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<RescListVo> queryListVo(Pagination<Resc> query);

	/**
	 * 获取一级资源树
	 * 
	 * @return
	 */
	List<RescListVo> getRescTree();
}
