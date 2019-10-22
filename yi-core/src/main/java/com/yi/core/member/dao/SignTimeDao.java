/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.member.domain.entity.SignTime;


/**
*  *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
*/
public interface SignTimeDao extends JpaRepository<SignTime, Integer> ,JpaSpecificationExecutor<SignTime> {
   
	SignTime findByMemberId(int memberId);

    List<SignTime> findBySignTimeAfter(Date date);

    List<SignTime> findBySignTimeAfterAndMember_id(Date date, int memberId);
}