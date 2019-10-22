/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.AddressBo;
import com.yi.core.basic.domain.entity.Address;
import com.yi.core.basic.domain.vo.AddressListVo;
import com.yi.core.basic.domain.vo.AddressVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IAddressService {

	/**
	 * 根据ID得到Address
	 * 
	 * @param addressId
	 * @return
	 */
	Address getAddressById(int addressId);

	/**
	 * 根据ID得到AddressVo
	 * 
	 * @param addressId
	 * @return
	 */
	AddressVo getAddressVoById(int addressId);

	/**
	 * 根据ID得到AddressListVo
	 * 
	 * @param addressId
	 * @return
	 */
	AddressListVo getAddressListVoById(int addressId);

	/**
	 * 根据Entity创建Address
	 * 
	 * @param address
	 * @return
	 */
	Address addAddress(Address address);

	/**
	 * 根据BO创建Address
	 * 
	 * @param addressBo
	 * @return
	 */
	AddressVo addAddress(AddressBo addressBo);

	/**
	 * 根据Entity更新Address
	 * 
	 * @param address
	 * @return
	 */
	Address updateAddress(Address address);

	/**
	 * 根据BO更新Address
	 * 
	 * @param addressBo
	 * @return
	 */
	AddressVo updateAddress(AddressBo addressBo);

	/**
	 * 删除Address
	 * 
	 * @param addressId
	 */
	void removeAddressById(int addressId);

	/**
	 * 分页查询: Address
	 * 
	 * @param query
	 * @return
	 */
	Page<Address> query(Pagination<Address> query);

	/**
	 * 分页查询: AddressListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<AddressListVo> queryListVo(Pagination<Address> query);

}
