/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.member.domain.entity.LoginRecord;

/**
 * 登录记录
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface LoginRecordDao extends JpaRepository<LoginRecord, Integer>, JpaSpecificationExecutor<LoginRecord> {

}