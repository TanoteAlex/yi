/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.cms.domain.entity.Banner;
import com.yihz.common.orm.BaseDao;

import java.util.List;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface BannerDao extends JpaRepository<Banner, Integer>, JpaSpecificationExecutor<Banner>{



    List<Banner> findByDeletedAndStateOrderBySort(int i1,int i);
}