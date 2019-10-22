/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.activity.domain.entity.InvitePrize;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface InvitePrizeDao extends JpaRepository<InvitePrize, Integer>, JpaSpecificationExecutor<InvitePrize> {
	
	List<InvitePrize> findByInviteActivity_id(Integer inviteActivityId);
	
}