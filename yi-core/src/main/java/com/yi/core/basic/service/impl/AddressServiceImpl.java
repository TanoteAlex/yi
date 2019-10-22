/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.basic.dao.AddressDao;
import com.yi.core.basic.domain.bo.AddressBo;
import com.yi.core.basic.domain.entity.Address;
import com.yi.core.basic.domain.entity.Address_;
import com.yi.core.basic.domain.simple.AddressSimple;
import com.yi.core.basic.domain.vo.AddressListVo;
import com.yi.core.basic.domain.vo.AddressVo;
import com.yi.core.basic.service.IAddressService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class AddressServiceImpl implements IAddressService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AddressDao addressDao;

	private EntityListVoBoSimpleConvert<Address, AddressBo, AddressVo, AddressSimple, AddressListVo> addressConvert;

	@Override
	public Address getAddressById(int addressId) {
		return addressDao.getOne(addressId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AddressVo getAddressVoById(int addressId) {

		return addressConvert.toVo(this.addressDao.getOne(addressId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AddressListVo getAddressListVoById(int addressId) {
		return addressConvert.toListVo(this.addressDao.getOne(addressId));
	}

	@Override
	public Address addAddress(Address address) {
		return addressDao.save(address);
	}

	@Override
	public AddressVo addAddress(AddressBo addressBo) {
		return addressConvert.toVo(addressDao.save(addressConvert.toEntity(addressBo)));
	}

	@Override
	public Address updateAddress(Address address) {

		Address dbAddress = addressDao.getOne(address.getId());
		AttributeReplication.copying(address, dbAddress, Address_.guid, Address_.businessId, Address_.businessType, Address_.fullName, Address_.phone, Address_.province,
				Address_.city, Address_.district, Address_.longitude, Address_.latitude, Address_.zipCode, Address_.addr, Address_.fullAddr, Address_.defaulted,
				Address_.createTime, Address_.deleted, Address_.delTime);
		return dbAddress;
	}

	@Override
	public AddressVo updateAddress(AddressBo addressBo) {
		Address dbAddress = addressDao.getOne(addressBo.getId());
		AttributeReplication.copying(addressBo, dbAddress, Address_.guid, Address_.businessId, Address_.businessType, Address_.fullName, Address_.phone, Address_.province,
				Address_.city, Address_.district, Address_.longitude, Address_.latitude, Address_.zipCode, Address_.addr, Address_.fullAddr, Address_.defaulted,
				Address_.createTime, Address_.deleted, Address_.delTime);
		return addressConvert.toVo(dbAddress);
	}

	@Override
	public void removeAddressById(int addressId) {
		addressDao.deleteById(addressId);
	}

	@Override
	public Page<Address> query(Pagination<Address> query) {
		query.setEntityClazz(Address.class);
		Page<Address> page = addressDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AddressListVo> queryListVo(Pagination<Address> query) {

		Page<Address> pages = this.query(query);

		List<AddressListVo> vos = addressConvert.toListVos(pages.getContent());
		return new PageImpl<AddressListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.addressConvert = new EntityListVoBoSimpleConvert<Address, AddressBo, AddressVo, AddressSimple, AddressListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Address, AddressVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Address, AddressVo>(beanConvertManager) {
					@Override
					protected void postConvert(AddressVo AddressVo, Address Address) {

					}
				};
			}

			@Override
			protected BeanConvert<Address, AddressListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Address, AddressListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AddressListVo AddressListVo, Address Address) {

					}
				};
			}

			@Override
			protected BeanConvert<Address, AddressBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Address, AddressBo>(beanConvertManager) {
					@Override
					protected void postConvert(AddressBo AddressBo, Address Address) {

					}
				};
			}

			@Override
			protected BeanConvert<AddressBo, Address> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AddressBo, Address>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Address, AddressSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Address, AddressSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AddressSimple, Address> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AddressSimple, Address>(beanConvertManager) {
					@Override
					public Address convert(AddressSimple AddressSimple) throws BeansException {
						return addressDao.getOne(AddressSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}
}
