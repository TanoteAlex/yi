/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.service;

import org.springframework.data.domain.Page;

import com.yi.core.gift.domain.bo.BlessWordBo;
import com.yi.core.gift.domain.entity.BlessWord;
import com.yi.core.gift.domain.vo.BlessWordListVo;
import com.yi.core.gift.domain.vo.BlessWordVo;
import com.yihz.common.persistence.Pagination;

/**
 * 祝福语
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IBlessWordService {

	/**
	 * 分页查询: BlessWord
	 **/
	Page<BlessWord> query(Pagination<BlessWord> query);

	/**
	 * 分页查询: BlessWord
	 **/
	Page<BlessWordListVo> queryListVo(Pagination<BlessWord> query);

	/**
	 * 创建BlessWord
	 **/
	BlessWord addBlessWord(BlessWord blessWord);

	/**
	 * 创建BlessWord
	 **/
	BlessWordVo addBlessWord(BlessWordBo blessWord);

	/**
	 * 更新BlessWord
	 **/
	BlessWord updateBlessWord(BlessWord blessWord);

	/**
	 * 更新BlessWord
	 **/
	BlessWordVo updateBlessWord(BlessWordBo blessWord);

	/**
	 * 删除BlessWord
	 **/
	void removeBlessWordById(int blessWordId);

	/**
	 * 根据ID得到BlessWordBo
	 **/
	BlessWordBo getBlessWordBoById(int blessWordId);

	/**
	 * 根据ID得到BlessWordVo
	 **/
	BlessWordVo getBlessWordVoById(int blessWordId);

	/**
	 * 根据ID得到BlessWordListVo
	 **/
	BlessWordListVo getListVoById(int blessWordId);

}
