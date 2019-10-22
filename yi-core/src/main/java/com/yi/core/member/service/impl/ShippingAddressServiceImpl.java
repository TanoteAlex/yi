/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.common.Deleted;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.dao.ShippingAddressDao;
import com.yi.core.member.domain.bo.ShippingAddressBo;
import com.yi.core.member.domain.entity.ShippingAddress;
import com.yi.core.member.domain.entity.ShippingAddress_;
import com.yi.core.member.domain.simple.ShippingAddressSimple;
import com.yi.core.member.domain.vo.ShippingAddressListVo;
import com.yi.core.member.domain.vo.ShippingAddressVo;
import com.yi.core.member.service.IShippingAddressService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class ShippingAddressServiceImpl implements IShippingAddressService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(ShippingAddressServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private ShippingAddressDao shippingAddressDao;

	private EntityListVoBoSimpleConvert<ShippingAddress, ShippingAddressBo, ShippingAddressVo, ShippingAddressSimple, ShippingAddressListVo> shippingAddressConvert;

	@Override
	public ShippingAddress getShippingAddressById(int shippingAddressId) {
		return shippingAddressDao.getOne(shippingAddressId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShippingAddressVo getShippingAddressVoById(int shippingAddressId) {

		return shippingAddressConvert.toVo(this.shippingAddressDao.getOne(shippingAddressId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShippingAddressListVo getShippingAddressListVoById(int shippingAddressId) {
		return shippingAddressConvert.toListVo(this.shippingAddressDao.getOne(shippingAddressId));
	}

	@Override
	public ShippingAddressVo addShippingAddress(ShippingAddress shippingAddress) {
		return shippingAddressConvert.toVo(shippingAddressDao.save(shippingAddress));
	}

	@Override
	public ShippingAddressListVo addShippingAddress(ShippingAddressBo shippingAddressBo) {
		if (shippingAddressBo.getDefaulted() == 1) {
			shippingAddressDao.findByMember_Id(shippingAddressBo.getMember().getId()).forEach(e -> {
				e.setDefaulted(0);
				e.setDefaulted(MemberEnum.ADDRESS_NON_DEFAULT.getCode());
			});
		}
		shippingAddressBo.setCreateTime(new Date());
		return shippingAddressConvert
				.toListVo(shippingAddressDao.save(shippingAddressConvert.toEntity(shippingAddressBo)));
	}

	@Override
	public ShippingAddressVo updateShippingAddress(ShippingAddress shippingAddress) {

		ShippingAddress dbShippingAddress = shippingAddressDao.getOne(shippingAddress.getId());
		AttributeReplication.copying(shippingAddress, dbShippingAddress, ShippingAddress_.fullName,
				ShippingAddress_.phone, ShippingAddress_.province, ShippingAddress_.city, ShippingAddress_.district,
				ShippingAddress_.address, ShippingAddress_.defaulted, ShippingAddress_.createTime);
		return shippingAddressConvert.toVo(dbShippingAddress);
	}

	@Override
	public ShippingAddressListVo updateShippingAddress(ShippingAddressBo shippingAddressBo) {
		// 如果修改默认地址 其他修改为非默认地址
		if (shippingAddressBo.getDefaulted() == 1) {
			shippingAddressDao.findByMember_Id(shippingAddressBo.getMember().getId()).forEach(e -> {
				e.setDefaulted(0);
				e.setDefaulted(MemberEnum.ADDRESS_NON_DEFAULT.getCode());
			});
		}
		ShippingAddress dbShippingAddress = shippingAddressDao.getOne(shippingAddressBo.getId());
		AttributeReplication.copying(shippingAddressConvert.toEntity(shippingAddressBo), dbShippingAddress,
				ShippingAddress_.fullName, ShippingAddress_.phone, ShippingAddress_.province, ShippingAddress_.city,
				ShippingAddress_.district, ShippingAddress_.address, ShippingAddress_.defaulted,
				ShippingAddress_.zipCode);
		return shippingAddressConvert.toListVo(dbShippingAddress);
	}

	@Override
	public void removeShippingAddressById(int shippingAddressId) {
		shippingAddressDao.getOne(shippingAddressId).setDeleted(1);
	}

	@Override
	public Page<ShippingAddress> query(Pagination<ShippingAddress> query) {
		query.setEntityClazz(ShippingAddress.class);
		Page<ShippingAddress> page = shippingAddressDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ShippingAddressListVo> queryListVo(Pagination<ShippingAddress> query) {

		Page<ShippingAddress> pages = this.query(query);

		List<ShippingAddressListVo> vos = shippingAddressConvert.toListVos(pages.getContent());
		return new PageImpl<ShippingAddressListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShippingAddressListVo> getShippingAddressListVoByMemberId(int memberId) {
		return shippingAddressConvert.toListVos(shippingAddressDao.findByMember_IdAndDeleted(memberId, Deleted.DEL_FALSE));
	}

	@Override
	public ShippingAddressVo setDefaultAddress(int memberId, int addressId) {
		shippingAddressDao.findByMember_Id(memberId).forEach(e -> {
			e.setDefaulted(MemberEnum.ADDRESS_NON_DEFAULT.getCode());
		});
		ShippingAddress shippingAddress = shippingAddressDao.getOne(addressId);
		shippingAddress.setDefaulted(MemberEnum.ADDRESS_DEFAULT.getCode());
		return shippingAddressConvert.toVo(shippingAddress);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShippingAddressVo getShippingAddressDetail(int addressId) {
		return shippingAddressConvert.toVo(shippingAddressDao.getOne(addressId));
	}

	protected void initConvert() {
		this.shippingAddressConvert = new EntityListVoBoSimpleConvert<ShippingAddress, ShippingAddressBo, ShippingAddressVo, ShippingAddressSimple, ShippingAddressListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<ShippingAddress, ShippingAddressVo> createEntityToVoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ShippingAddress, ShippingAddressVo>(beanConvertManager) {
					@Override
					protected void postConvert(ShippingAddressVo ShippingAddressVo, ShippingAddress ShippingAddress) {

					}
				};
			}

			@Override
			protected BeanConvert<ShippingAddress, ShippingAddressListVo> createEntityToListVoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ShippingAddress, ShippingAddressListVo>(beanConvertManager) {
					@Override
					protected void postConvert(ShippingAddressListVo ShippingAddressListVo,
							ShippingAddress ShippingAddress) {

					}
				};
			}

			@Override
			protected BeanConvert<ShippingAddress, ShippingAddressBo> createEntityToBoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ShippingAddress, ShippingAddressBo>(beanConvertManager) {
					@Override
					protected void postConvert(ShippingAddressBo ShippingAddressBo, ShippingAddress ShippingAddress) {

					}
				};
			}

			@Override
			protected BeanConvert<ShippingAddressBo, ShippingAddress> createBoToEntityConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ShippingAddressBo, ShippingAddress>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<ShippingAddress, ShippingAddressSimple> createEntityToSimpleConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ShippingAddress, ShippingAddressSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<ShippingAddressSimple, ShippingAddress> createSimpleToEntityConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ShippingAddressSimple, ShippingAddress>(beanConvertManager) {
					@Override
					public ShippingAddress convert(ShippingAddressSimple ShippingAddressSimple) throws BeansException {
						return shippingAddressDao.getOne(ShippingAddressSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	/**
	 * 获取默认收货地址 如果没有 那最新的收货数据
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShippingAddress getDefaultAddressByMember(Integer memberId) {
		List<ShippingAddress> addresses = shippingAddressDao.findByMember_IdAndDeletedOrderByIdDesc(memberId,
				Deleted.DEL_FALSE);
		if (CollectionUtils.isNotEmpty(addresses)) {
			for (ShippingAddress tmp : addresses) {
				if (tmp != null && MemberEnum.ADDRESS_DEFAULT.getCode().equals(tmp.getDefaulted())) {
					return tmp;
				}
			}
			return addresses.get(0);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShippingAddressVo getDefaultAddressVoByMember(Integer memberId) {
		return shippingAddressConvert.toVo(this.getDefaultAddressByMember(memberId));
	}
}
