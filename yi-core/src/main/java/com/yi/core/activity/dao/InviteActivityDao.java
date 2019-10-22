/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.activity.domain.entity.InviteActivity;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface InviteActivityDao extends JpaRepository<InviteActivity, Integer>, JpaSpecificationExecutor<InviteActivity> {

	InviteActivity findFirstByStartTimeLessThanAndEndTimeGreaterThanAndStateAndDeletedOrderByCreateTimeDesc(Date now1, Date now2, Integer state, Integer deleted);

}