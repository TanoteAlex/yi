/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.basic.domain.entity.Region;
import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.dao.FreightTemplateConfigDao;
import com.yi.core.order.domain.bo.FreightTemplateConfigBo;
import com.yi.core.order.domain.entity.CustomFreightTemplate;
import com.yi.core.order.domain.entity.FreeFreightTemplate;
import com.yi.core.order.domain.entity.FreightTemplateConfig;
import com.yi.core.order.domain.entity.FreightTemplateConfig_;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.domain.simple.FreightTemplateConfigSimple;
import com.yi.core.order.domain.vo.FreightTemplateConfigListVo;
import com.yi.core.order.domain.vo.FreightTemplateConfigVo;
import com.yi.core.order.service.ICustomFreightTemplateService;
import com.yi.core.order.service.IFreeFreightTemplateService;
import com.yi.core.order.service.IFreightTemplateConfigService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 运费模板配置
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class FreightTemplateConfigServiceImpl implements IFreightTemplateConfigService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(FreightTemplateConfigServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private FreightTemplateConfigDao freightTemplateConfigDao;

	@Resource
	private ICustomFreightTemplateService customFreightTemplateService;

	@Resource
	private IFreeFreightTemplateService freeFreightTemplateService;

	private EntityListVoBoSimpleConvert<FreightTemplateConfig, FreightTemplateConfigBo, FreightTemplateConfigVo, FreightTemplateConfigSimple, FreightTemplateConfigListVo> freightTemplateConfigConvert;

	/**
	 * 分页查询FreightTemplateConfig
	 **/
	@Override
	public Page<FreightTemplateConfig> query(Pagination<FreightTemplateConfig> query) {
		query.setEntityClazz(FreightTemplateConfig.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, sortList, orderList) -> {
			sortList.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(FreightTemplateConfig_.deleted), Deleted.DEL_FALSE)));
			orderList.add(criteriaBuilder.desc(root.get(FreightTemplateConfig_.createTime)));

		}));
		Page<FreightTemplateConfig> page = freightTemplateConfigDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: FreightTemplateConfig
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<FreightTemplateConfigListVo> queryListVo(Pagination<FreightTemplateConfig> query) {
		Page<FreightTemplateConfig> pages = this.query(query);
		List<FreightTemplateConfigListVo> vos = freightTemplateConfigConvert.toListVos(pages.getContent());
		return new PageImpl<FreightTemplateConfigListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建FreightTemplateConfig
	 **/
	@Override
	public FreightTemplateConfig addFreightTemplateConfig(FreightTemplateConfig freightTemplateConfig) {
		if (freightTemplateConfig == null || StringUtils.isAnyBlank(freightTemplateConfig.getConfigName())) {
			throw new BusinessException("提交数据不能为空");
		}
		freightTemplateConfig.setConfigName(freightTemplateConfig.getConfigName().trim());
		// 校验模板名称重复
		int checkName = freightTemplateConfigDao.countByConfigNameAndSupplierAndDeleted(freightTemplateConfig.getConfigName(), freightTemplateConfig.getSupplier(),
				Deleted.DEL_FALSE);
		if (checkName > 0) {
			LOG.error("运费模板名称（{}）重复", freightTemplateConfig.getConfigName());
			throw new BusinessException("模板名称不能重复");
		}
		// 自定义运费模板
		if (OrderEnum.FREIGHT_TYPE_CUSTOM.getCode().equals(freightTemplateConfig.getFreightType())) {
			if (CollectionUtils.isEmpty(freightTemplateConfig.getCustomFreightTemplates())) {
				throw new BusinessException("自定义运费模板数据不能为空");
			}
			// 卖家承担运费
		} else {
			freightTemplateConfig.setCustomFreightTemplates(null);
			freightTemplateConfig.setFreeFreightTemplates(null);
		}
		// 指定条件包邮
		if (OrderEnum.FREE_CONDITION_CHECKED.getCode().equals(freightTemplateConfig.getFreeCondition())) {
			if (CollectionUtils.isEmpty(freightTemplateConfig.getFreeFreightTemplates())) {
				throw new BusinessException("指定条件包邮数据不能为空");
			}
		} else {
			freightTemplateConfig.setFreeFreightTemplates(null);
		}
		freightTemplateConfig.setConfigNo(NumberGenerateUtils.generateFreightTemplateNo());
		FreightTemplateConfig dbFreightTemplateConfig = freightTemplateConfigDao.save(freightTemplateConfig);
		// 保存自定义运费
		customFreightTemplateService.batchAddCustomFreightTemplate(dbFreightTemplateConfig);
		// 保存指定条件包邮
		freeFreightTemplateService.batchAddFreeFreightTemplate(dbFreightTemplateConfig);
		return dbFreightTemplateConfig;
	}

	/**
	 * 创建FreightTemplateConfig
	 **/
	@Override
	public FreightTemplateConfigVo addFreightTemplateConfig(FreightTemplateConfigBo freightTemplateConfigBo) {
		return freightTemplateConfigConvert.toVo(this.addFreightTemplateConfig(freightTemplateConfigConvert.toEntity(freightTemplateConfigBo)));
	}

	/**
	 * 更新FreightTemplateConfig
	 **/
	@Override
	public FreightTemplateConfig updateFreightTemplateConfig(FreightTemplateConfig freightTemplateConfig) {
		if (freightTemplateConfig == null || freightTemplateConfig.getId() < 0 || StringUtils.isAnyBlank(freightTemplateConfig.getConfigName())) {
			throw new BusinessException("提交数据不能为空");
		}
		freightTemplateConfig.setConfigName(freightTemplateConfig.getConfigName().trim());
		// 校验模板名称重复
		int checkName = freightTemplateConfigDao.countByConfigNameAndSupplierAndDeletedAndIdNot(freightTemplateConfig.getConfigName(), freightTemplateConfig.getSupplier(),
				Deleted.DEL_FALSE, freightTemplateConfig.getId());
		if (checkName > 0) {
			throw new BusinessException("模板名称不能重复");
		}
		// 自定义运费模板
		if (OrderEnum.FREIGHT_TYPE_CUSTOM.getCode().equals(freightTemplateConfig.getFreightType())) {
			if (CollectionUtils.isEmpty(freightTemplateConfig.getCustomFreightTemplates())) {
				throw new BusinessException("自定义运费模板数据不能为空");
			}
			// 指定条件包邮
			if (OrderEnum.FREE_CONDITION_CHECKED.getCode().equals(freightTemplateConfig.getFreeCondition())) {
				if (CollectionUtils.isEmpty(freightTemplateConfig.getFreeFreightTemplates())) {
					throw new BusinessException("指定条件包邮数据不能为空");
				}
			} else {
				freightTemplateConfig.setFreeFreightTemplates(null);
			}
			// 卖家承担运费
		} else {
			freightTemplateConfig.setCustomFreightTemplates(null);
			freightTemplateConfig.setFreeFreightTemplates(null);
		}

		FreightTemplateConfig dbFreightTemplateConfig = freightTemplateConfigDao.getOne(freightTemplateConfig.getId());
		AttributeReplication.copying(freightTemplateConfig, dbFreightTemplateConfig, FreightTemplateConfig_.configName, FreightTemplateConfig_.supplier,
				FreightTemplateConfig_.deliveryTime, FreightTemplateConfig_.timeUnit, FreightTemplateConfig_.freightType, FreightTemplateConfig_.chargeMode,
				FreightTemplateConfig_.deliveryMode, FreightTemplateConfig_.freeCondition, FreightTemplateConfig_.state);
		// 更新自定义运费
		customFreightTemplateService.batchUpdateCustomFreightTemplate(freightTemplateConfig);
		// 更新指定条件包邮
		freeFreightTemplateService.batchUpdateFreeFreightTemplate(freightTemplateConfig);
		return dbFreightTemplateConfig;
	}

	/**
	 * 更新FreightTemplateConfig
	 **/
	@Override
	public FreightTemplateConfigVo updateFreightTemplateConfig(FreightTemplateConfigBo freightTemplateConfigBo) {
		return freightTemplateConfigConvert.toVo(this.updateFreightTemplateConfig(freightTemplateConfigConvert.toEntity(freightTemplateConfigBo)));
	}

	/**
	 * 删除FreightTemplateConfig
	 **/
	@Override
	public void removeFreightTemplateConfigById(int id) {
		FreightTemplateConfig dbFreightTemplateConfig = freightTemplateConfigDao.getOne(id);
		if (dbFreightTemplateConfig != null) {
			if (CollectionUtils.isNotEmpty(dbFreightTemplateConfig.getCommodities())) {
				throw new BusinessException("该运费模板有商品使用，请处理相关数据后删除");
			}
			dbFreightTemplateConfig.setDeleted(Deleted.DEL_TRUE);
			dbFreightTemplateConfig.setDelTime(new Date());
			// 删除子数据
			customFreightTemplateService.batchDeleteCustomFreightTemplate(dbFreightTemplateConfig.getCustomFreightTemplates());
			freeFreightTemplateService.batchDeleteFreeFreightTemplate(dbFreightTemplateConfig.getFreeFreightTemplates());
		}
	}

	/**
	 * 根据ID得到FreightTemplateConfig
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FreightTemplateConfig getById(int id) {
		return this.freightTemplateConfigDao.getOne(id);
	}

	/**
	 * 根据ID得到FreightTemplateConfigVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FreightTemplateConfigVo getVoById(int id) {
		return freightTemplateConfigConvert.toVo(this.freightTemplateConfigDao.getOne(id));
	}

	/**
	 * 根据ID得到FreightTemplateConfigListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FreightTemplateConfigListVo getListVoById(int id) {
		return freightTemplateConfigConvert.toListVo(this.freightTemplateConfigDao.getOne(id));
	}

	protected void initConvert() {
		this.freightTemplateConfigConvert = new EntityListVoBoSimpleConvert<FreightTemplateConfig, FreightTemplateConfigBo, FreightTemplateConfigVo, FreightTemplateConfigSimple, FreightTemplateConfigListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<FreightTemplateConfig, FreightTemplateConfigVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreightTemplateConfig, FreightTemplateConfigVo>(beanConvertManager) {
					@Override
					protected void postConvert(FreightTemplateConfigVo freightTemplateConfigVo, FreightTemplateConfig freightTemplateConfig) {
					}
				};
			}

			@Override
			protected BeanConvert<FreightTemplateConfig, FreightTemplateConfigListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreightTemplateConfig, FreightTemplateConfigListVo>(beanConvertManager) {
					@Override
					protected void postConvert(FreightTemplateConfigListVo freightTemplateConfigListVo, FreightTemplateConfig freightTemplateConfig) {
					}
				};
			}

			@Override
			protected BeanConvert<FreightTemplateConfig, FreightTemplateConfigBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreightTemplateConfig, FreightTemplateConfigBo>(beanConvertManager) {
					@Override
					protected void postConvert(FreightTemplateConfigBo freightTemplateConfigBo, FreightTemplateConfig freightTemplateConfig) {
					}
				};
			}

			@Override
			protected BeanConvert<FreightTemplateConfigBo, FreightTemplateConfig> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreightTemplateConfigBo, FreightTemplateConfig>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<FreightTemplateConfig, FreightTemplateConfigSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreightTemplateConfig, FreightTemplateConfigSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<FreightTemplateConfigSimple, FreightTemplateConfig> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreightTemplateConfigSimple, FreightTemplateConfig>(beanConvertManager) {
					@Override
					public FreightTemplateConfig convert(FreightTemplateConfigSimple freightTemplateConfigSimple) throws BeansException {
						return freightTemplateConfigDao.getOne(freightTemplateConfigSimple.getId());
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
	 * 计算运费
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal calculateFreight(FreightTemplateConfig freightTemplateConfig, SaleOrderItem saleOrderItem, String province, String city) {
		if (freightTemplateConfig == null || saleOrderItem == null || StringUtils.isAnyBlank(province, city)) {
			return BigDecimal.ZERO;
		}
		// 1--运费类型-2卖家承担运费
		if (OrderEnum.FREIGHT_TYPE_SELLER.getCode().equals(freightTemplateConfig.getFreightType())) {
			return BigDecimal.ZERO;
		}
		// 2--指定条件包邮
		if (OrderEnum.FREE_CONDITION_CHECKED.getCode().equals(freightTemplateConfig.getFreeCondition())) {
			// 遍历找到对应地区的包邮模板
			for (FreeFreightTemplate tmpFreeTpl : freightTemplateConfig.getFreeFreightTemplates()) {
				if (tmpFreeTpl != null) {
					// 检查地区
					boolean regionFlag = this.checkRegion(tmpFreeTpl.getRegions(), province, city);
					// 包邮地区符合，检查是否满足包邮条件
					if (regionFlag) {
						// 包邮条件-件数
						if (OrderEnum.FREE_TYPE_PIECE.getCode().equals(tmpFreeTpl.getFreeType())
								&& saleOrderItem.getQuantity() >= tmpFreeTpl.getFullQuantity().intValue()) {
							return BigDecimal.ZERO;
							// 包邮条件-金额
						} else if (OrderEnum.FREE_TYPE_AMOUNT.getCode().equals(tmpFreeTpl.getFreeType()) && saleOrderItem.getSubtotal().compareTo(tmpFreeTpl.getFullAmount()) >= 0) {
							return BigDecimal.ZERO;
							// 包邮条件-件数+金额
						} else if (OrderEnum.FREE_TYPE_PIECE_AND_AMOUNT.getCode().equals(tmpFreeTpl.getFreeType())
								&& saleOrderItem.getQuantity() >= tmpFreeTpl.getFullQuantity().intValue()
								&& saleOrderItem.getSubtotal().compareTo(tmpFreeTpl.getFullAmount()) >= 0) {
							return BigDecimal.ZERO;
						}
					}
				}
			}
		}
		// 3--自定义运费-整理出非默认和默认模板
		Set<CustomFreightTemplate> nonDefaultFreightTpls = new HashSet<>(0);
		Set<CustomFreightTemplate> defaultFreightTpls = new HashSet<>(0);
		for (CustomFreightTemplate tmpCustomTpl : freightTemplateConfig.getCustomFreightTemplates()) {
			if (tmpCustomTpl != null) {
				if (OrderEnum.DEFAULT_YES.getCode().equals(tmpCustomTpl.getDefaulted())) {
					defaultFreightTpls.add(tmpCustomTpl);
				} else if (OrderEnum.DEFAULT_NO.getCode().equals(tmpCustomTpl.getDefaulted())) {
					nonDefaultFreightTpls.add(tmpCustomTpl);
				}
			}
		}
		// 3-1计算件数 重量 体积
		BigDecimal pieces = BigDecimal.valueOf(saleOrderItem.getQuantity());
		BigDecimal weights = BigDecimal.valueOf(saleOrderItem.getQuantity()).multiply(Optional.ofNullable(saleOrderItem.getProduct().getCommodity().getWeight()).orElse(BigDecimal.ZERO));
		BigDecimal volumes = BigDecimal.valueOf(saleOrderItem.getQuantity()).multiply(Optional.ofNullable(saleOrderItem.getProduct().getCommodity().getVolume()).orElse(BigDecimal.ZERO));
		// 3-2先计算 非默认模板运费
		for (CustomFreightTemplate tmpCustomTpl : nonDefaultFreightTpls) {
			if (tmpCustomTpl != null) {
				// 检查地区
				boolean regionFlag = this.checkRegion(tmpCustomTpl.getRegions(), province, city);
				if (regionFlag) {
					BigDecimal freight = calculateFreightByCustomTemplate(tmpCustomTpl, freightTemplateConfig.getChargeMode(), pieces, weights, volumes);
					if (freight != null) {
						return freight;
					}
				}
			}
		}
		// 3-3再计算 默认模板运费
		for (CustomFreightTemplate tmpCustomTpl : defaultFreightTpls) {
			if (tmpCustomTpl != null) {
				BigDecimal freight = calculateFreightByCustomTemplate(tmpCustomTpl, freightTemplateConfig.getChargeMode(), pieces, weights, volumes);
				if (freight != null) {
					return freight;
				}
			}
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 检查地区
	 * 
	 * @param regions
	 * @param province
	 * @param city
	 * @return true 符合 false不符合
	 */
	private boolean checkRegion(Set<Region> regions, String province, String city) {
		if (CollectionUtils.isEmpty(regions) || StringUtils.isAnyBlank(province, city)) {
			return false;
		}
		for (Region tmp : regions) {
			if (tmp != null && tmp.getArea() != null && (province.equals(tmp.getArea().getName()) || province.equals(tmp.getArea().getAreaCode()))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 计算自定义模板运费
	 * 
	 * @param customFreightTemplate
	 * @param chargeMode
	 * @param pieces
	 * @param weights
	 * @param volumes
	 * @return
	 */
	private BigDecimal calculateFreightByCustomTemplate(CustomFreightTemplate customFreightTemplate, Integer chargeMode, BigDecimal pieces, BigDecimal weights,
			BigDecimal volumes) {
		if (customFreightTemplate == null || chargeMode == null || pieces == null || weights == null || volumes == null) {
			return null;
		}
		// 按件数
		if (OrderEnum.CHARGE_MODE_PIECE.getCode().equals(chargeMode)) {
			// 小于或等于首件 或者续件的续费为0
			if (pieces.compareTo(customFreightTemplate.getFirstQuantity()) <= 0 || BigDecimal.ZERO.compareTo(customFreightTemplate.getExtraFee()) == 0) {
				return customFreightTemplate.getFirstFee();
			}
			// 如果不是 梯级计算运费
			// 购买件数<= 首件+续件
			if (pieces.compareTo(customFreightTemplate.getFirstQuantity().add(customFreightTemplate.getExtraQuantity())) <= 0) {
				return customFreightTemplate.getFirstFee().add(customFreightTemplate.getExtraFee());
			}
			// 购买件数> 首件+续件
			// 首费 + 续费 * (件数-首件)/续件
			BigDecimal freight = customFreightTemplate.getFirstFee().add(customFreightTemplate.getExtraFee()
					.multiply((pieces.subtract(customFreightTemplate.getFirstQuantity())).divide(customFreightTemplate.getExtraQuantity()).setScale(0, BigDecimal.ROUND_UP)));
			return freight;
			// 按重量
		} else if (OrderEnum.CHARGE_MODE_WEIGHT.getCode().equals(chargeMode)) {
			// 小于或等于首重 或者续重的续费为0
			if (weights.compareTo(customFreightTemplate.getFirstQuantity()) <= 0 || BigDecimal.ZERO.compareTo(customFreightTemplate.getExtraFee()) == 0) {
				return customFreightTemplate.getFirstFee();
			}
			// 如果不是 梯级计算运费
			// 购买重量<= 首重+续重
			if (weights.compareTo(customFreightTemplate.getFirstQuantity().add(customFreightTemplate.getExtraQuantity())) <= 0) {
				return customFreightTemplate.getFirstFee().add(customFreightTemplate.getExtraFee());
			}
			// 购买重量> 首重+续重
			// 首费 + 续费 * (购买重量-首重)/续重
			BigDecimal freight = customFreightTemplate.getFirstFee().add(customFreightTemplate.getExtraFee()
					.multiply((weights.subtract(customFreightTemplate.getFirstQuantity())).divide(customFreightTemplate.getExtraQuantity()).setScale(0, BigDecimal.ROUND_UP)));
			return freight;
			// 按体积
		} else if (OrderEnum.CHARGE_MODE_VOLUME.getCode().equals(chargeMode)) {
			// 小于或等于首体积 或者续体积的续费为0
			if (volumes.compareTo(customFreightTemplate.getFirstQuantity()) <= 0 || BigDecimal.ZERO.compareTo(customFreightTemplate.getExtraFee()) == 0) {
				return customFreightTemplate.getFirstFee();
			}
			// 如果不是 梯级计算运费
			// 购买体积<= 首体积+续体积
			if (volumes.compareTo(customFreightTemplate.getFirstQuantity().add(customFreightTemplate.getExtraQuantity())) <= 0) {
				return customFreightTemplate.getFirstFee().add(customFreightTemplate.getExtraFee());
			}
			// 购买体积 > 首体积+续体积
			// 首费 + 续费 * (购买体积-首体积)/续体积
			BigDecimal freight = customFreightTemplate.getFirstFee().add(customFreightTemplate.getExtraFee()
					.multiply((volumes.subtract(customFreightTemplate.getFirstQuantity())).divide(customFreightTemplate.getExtraQuantity()).setScale(0, BigDecimal.ROUND_UP)));
			return freight;
		}
		return null;
	}

}
