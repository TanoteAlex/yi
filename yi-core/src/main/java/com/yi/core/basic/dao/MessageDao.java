/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.Message;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface MessageDao extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {

}