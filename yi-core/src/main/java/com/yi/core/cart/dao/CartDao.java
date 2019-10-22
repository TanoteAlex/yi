/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cart.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.cart.domain.entity.Cart;

/**
 * 购物车
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface CartDao extends JpaRepository<Cart, Integer>, JpaSpecificationExecutor<Cart> {

	/**
	 * 查询会员的购物车数据
	 * 
	 * @param memberId
	 * @return
	 */
	@EntityGraph(attributePaths = { "product" })
	List<Cart> findByMember_idOrderByCreateTimeDesc(int memberId);

	/**
	 * 
	 * @param collections
	 * @return
	 */
	List<Cart> findByIdIn(Collection<?> collections);

	/**
	 * 根据会员和货品查询 购物车
	 * 
	 * @param memberId
	 * @param productId
	 * @return
	 */
	Cart findByMember_idAndProduct_id(int memberId, int productId);

	/**
	 * 
	 * @param productId
	 * @return
	 */
	List<Cart> findByProduct_Id(int productId);

	/**
	 * 统计会员添加进购物车的货品种类数量
	 * 
	 * @param memberId
	 * @return
	 */
	int countByMember_Id(Integer memberId);
}