/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.member.domain.entity.ShippingAddress;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface ShippingAddressDao
		extends JpaRepository<ShippingAddress, Integer>, JpaSpecificationExecutor<ShippingAddress> {

	List<ShippingAddress> findByMember_Id(int memberId);

	List<ShippingAddress> findByMember_IdAndDeleted(int memberId, int deleted);

	/**
	 * 查询收货地址 并倒序
	 * 
	 * @param memberId
	 * @param deleted
	 * @return
	 */
	List<ShippingAddress> findByMember_IdAndDeletedOrderByIdDesc(Integer memberId, Integer deleted);
}