/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.member.domain.bo.ShippingAddressBo;
import com.yi.core.member.domain.entity.ShippingAddress;
import com.yi.core.member.domain.simple.ShippingAddressSimple;
import com.yi.core.member.domain.vo.ShippingAddressListVo;
import com.yi.core.member.domain.vo.ShippingAddressVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IShippingAddressService {

	/**
	 * 根据ID得到ShippingAddress
	 * 
	 * @param shippingAddressId
	 * @return
	 */
	ShippingAddress getShippingAddressById(int shippingAddressId);

	/**
	 * 根据ID得到ShippingAddressVo
	 * 
	 * @param shippingAddressId
	 * @return
	 */
	ShippingAddressVo getShippingAddressVoById(int shippingAddressId);

	/**
	 * 根据ID得到ShippingAddressListVo
	 * 
	 * @param shippingAddressId
	 * @return
	 */
	ShippingAddressListVo getShippingAddressListVoById(int shippingAddressId);

	/**
	 * 根据Entity创建ShippingAddress
	 * 
	 * @param shippingAddress
	 * @return
	 */
	ShippingAddressVo addShippingAddress(ShippingAddress shippingAddress);

	/**
	 * 根据BO创建ShippingAddress
	 * 
	 * @param shippingAddressBo
	 * @return
	 */
	ShippingAddressListVo addShippingAddress(ShippingAddressBo shippingAddressBo);

	/**
	 * 根据Entity更新ShippingAddress
	 * 
	 * @param shippingAddress
	 * @return
	 */
	ShippingAddressVo updateShippingAddress(ShippingAddress shippingAddress);

	/**
	 * 根据BO更新ShippingAddress
	 * 
	 * @param shippingAddressBo
	 * @return
	 */
	ShippingAddressListVo updateShippingAddress(ShippingAddressBo shippingAddressBo);

	/**
	 * 删除ShippingAddress
	 * 
	 * @param shippingAddressId
	 */
	void removeShippingAddressById(int shippingAddressId);

	/**
	 * 分页查询: ShippingAddress
	 * 
	 * @param query
	 * @return
	 */
	Page<ShippingAddress> query(Pagination<ShippingAddress> query);

	/**
	 * 分页查询: ShippingAddressListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<ShippingAddressListVo> queryListVo(Pagination<ShippingAddress> query);

	/**
	 * 获取用户收货地址
	 * 
	 * @param memberId
	 * @return
	 */
	List<ShippingAddressListVo> getShippingAddressListVoByMemberId(int memberId);

	ShippingAddressVo setDefaultAddress(int memberId, int addressId);

	/**
	 * 获取地址详细信息
	 * 
	 * @param addressId
	 * @return
	 */
	ShippingAddressVo getShippingAddressDetail(int addressId);

	/**
	 * 获取默认收货地址 如果没有 拿最新的收货地址
	 * @param memberId
	 * @return
	 */
	ShippingAddress getDefaultAddressByMember(Integer memberId);
	
	/**
	 * 获取默认收货地址 如果没有 拿最新的收货地址
	 * @param memberId
	 * @return
	 */
	ShippingAddressVo getDefaultAddressVoByMember(Integer memberId);

}
