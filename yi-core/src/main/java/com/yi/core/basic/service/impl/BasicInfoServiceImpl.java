/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service.impl;

import java.util.Date;
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
import org.springframework.util.CollectionUtils;

import com.yi.core.basic.dao.BasicInfoDao;
import com.yi.core.basic.domain.bo.BasicInfoBo;
import com.yi.core.basic.domain.entity.BasicInfo;
import com.yi.core.basic.domain.entity.BasicInfo_;
import com.yi.core.basic.domain.simple.BasicInfoSimple;
import com.yi.core.basic.domain.vo.BasicInfoListVo;
import com.yi.core.basic.domain.vo.BasicInfoVo;
import com.yi.core.basic.service.IBasicInfoService;
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
public class BasicInfoServiceImpl implements IBasicInfoService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(BasicInfoServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private BasicInfoDao basicInfoDao;

	private EntityListVoBoSimpleConvert<BasicInfo, BasicInfoBo, BasicInfoVo, BasicInfoSimple, BasicInfoListVo> basicInfoConvert;

	/**
	 * 分页查询BasicInfo
	 **/
	@Override
	public Page<BasicInfo> query(Pagination<BasicInfo> query) {
		query.setEntityClazz(BasicInfo.class);
		Page<BasicInfo> page = basicInfoDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 创建BasicInfo
	 **/
	@Override
	public BasicInfoVo addBasicInfo(BasicInfoBo basicInfoBo) {
		return basicInfoConvert.toVo(basicInfoDao.save(basicInfoConvert.toEntity(basicInfoBo)));
	}

	/**
	 * 更新BasicInfo
	 **/
	@Override
	public BasicInfoVo updateBasicInfo(BasicInfoBo basicInfoBo) {
		if (CollectionUtils.isEmpty(basicInfoDao.findAll())) {
			basicInfoBo.setCreateTime(new Date());
			return basicInfoConvert.toVo(basicInfoDao.save(basicInfoConvert.toEntity(basicInfoBo)));
		}
		BasicInfo dbBasicInfo = basicInfoDao.findAll().get(0);
		AttributeReplication.copying(basicInfoConvert.toEntity(basicInfoBo), dbBasicInfo, BasicInfo_.logoUrl, BasicInfo_.companyName, BasicInfo_.companyAddr, BasicInfo_.companyTel,
				BasicInfo_.companyMobile, BasicInfo_.companyMail, BasicInfo_.setupTime, BasicInfo_.contentProfile);
		return basicInfoConvert.toVo(dbBasicInfo);
	}

	/**
	 * 删除BasicInfo
	 **/
	@Override
	public void removeBasicInfoById(int id) {
		basicInfoDao.deleteById(id);
	}

	/**
	 * 根据ID得到BasicInfo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BasicInfoVo getBasicInfoVoById(int id) {

		return basicInfoConvert.toVo(this.basicInfoDao.getOne(id));
	}

	/**
	 * 根据ID得到BasicInfoListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BasicInfoVo getListVoById(int id) {
		return basicInfoConvert.toVo(this.basicInfoDao.getOne(id));
	}

	/**
	 * 分页查询: BasicInfo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BasicInfoListVo> queryListVo(Pagination<BasicInfo> query) {

		Page<BasicInfo> pages = this.query(query);

		List<BasicInfoListVo> vos = basicInfoConvert.toListVos(pages.getContent());
		return new PageImpl<BasicInfoListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.basicInfoConvert = new EntityListVoBoSimpleConvert<BasicInfo, BasicInfoBo, BasicInfoVo, BasicInfoSimple, BasicInfoListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<BasicInfo, BasicInfoVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicInfo, BasicInfoVo>(beanConvertManager) {
					@Override
					protected void postConvert(BasicInfoVo BasicInfoVo, BasicInfo BasicInfo) {

					}
				};
			}

			@Override
			protected BeanConvert<BasicInfo, BasicInfoListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicInfo, BasicInfoListVo>(beanConvertManager) {
					@Override
					protected void postConvert(BasicInfoListVo BasicInfoListVo, BasicInfo BasicInfo) {

					}
				};
			}

			@Override
			protected BeanConvert<BasicInfo, BasicInfoBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicInfo, BasicInfoBo>(beanConvertManager) {
					@Override
					protected void postConvert(BasicInfoBo BasicInfoBo, BasicInfo BasicInfo) {

					}
				};
			}

			@Override
			protected BeanConvert<BasicInfoBo, BasicInfo> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicInfoBo, BasicInfo>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<BasicInfo, BasicInfoSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicInfo, BasicInfoSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<BasicInfoSimple, BasicInfo> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BasicInfoSimple, BasicInfo>(beanConvertManager) {
					@Override
					public BasicInfo convert(BasicInfoSimple BasicInfoSimple) throws BeansException {
						return basicInfoDao.getOne(BasicInfoSimple.getId());
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
	 * 查询打开页面显示的数据
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BasicInfoVo getAll() {

		return basicInfoConvert.toVo(basicInfoDao.findAll().get(0));
	}
}
