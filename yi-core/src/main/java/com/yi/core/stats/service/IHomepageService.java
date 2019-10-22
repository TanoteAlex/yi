package com.yi.core.stats.service;

import com.yi.core.stats.domain.vo.PlatformDataVo;

public interface IHomepageService {

	/**
	 * 查询供应商数据
	 *
	 * @param supplierId
	 * @return
	 */
	public PlatformDataVo getSupplierData(Integer supplierId);

	/**
	 * 查询平台数据
	 * 
	 * @return
	 */
	public PlatformDataVo getPlatformData();

}
