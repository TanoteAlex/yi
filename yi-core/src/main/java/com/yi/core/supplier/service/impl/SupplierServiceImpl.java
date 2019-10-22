/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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

import com.yi.core.auth.dao.DeptDao;
import com.yi.core.cms.CmsEnum;
import com.yi.core.commodity.dao.ProductDao;
import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.finance.dao.SupplierWithdrawDao;
import com.yi.core.member.dao.MemberDao;
import com.yi.core.order.dao.SaleOrderDao;
import com.yi.core.order.dao.SaleOrderItemDao;
import com.yi.core.supplier.dao.SupplierDao;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yi.core.supplier.domain.entity.SupplierAccount;
import com.yi.core.supplier.domain.entity.Supplier_;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import com.yi.core.supplier.domain.vo.SupplierListVo;
import com.yi.core.supplier.domain.vo.SupplierVo;
import com.yi.core.supplier.service.ISupplierAccountService;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 供应商
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SupplierServiceImpl implements ISupplierService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SupplierDao supplierDao;

	@Resource
	private SaleOrderDao saleOrderDao;

	@Resource
	private ISupplierAccountService supplierAccountService;

	@Resource
	private SupplierWithdrawDao supplierWithdrawDao;

	@Resource
	private DeptDao deptDao;

	@Resource
	private MemberDao memberDao;

	@Resource
	private SaleOrderItemDao saleOrderItemDao;

	@Resource
	private ProductDao productDao;

	private EntityListVoBoSimpleConvert<Supplier, SupplierBo, SupplierVo, SupplierSimple, SupplierListVo> supplierConvert;

	@Override
	public Supplier getSupplierById(int supplierId) {
		return supplierDao.getOne(supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierVo getSupplierVoById(int supplierId) {
		return supplierConvert.toVo(this.supplierDao.getOne(supplierId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierListVo getSupplierListVoById(int supplierId) {
		return supplierConvert.toListVo(this.supplierDao.getOne(supplierId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierBo getSupplierBoById(int supplierId) {
		return supplierConvert.toBo(this.supplierDao.getOne(supplierId));
	}

	@Override
	public Supplier addSupplier(Supplier supplier) {
		if (supplier == null || StringUtils.isBlank(supplier.getSupplierName())) {
			LOG.error("提交数据为空");
			throw new BusinessException("提交数据不能为空");
		}
		supplier.setSupplierNo(NumberGenerateUtils.generateSupplierNo());
		supplier.setSupplierName(supplier.getSupplierName().trim());
		// 校验重复
		int checkName = supplierDao.countBySupplierNameAndPhoneAndDeleted(supplier.getSupplierName(), supplier.getPhone(), Deleted.DEL_FALSE);
		if (checkName > 0) {
			LOG.error("供应商名称已存在，supplierName={}", supplier.getSupplierName());
			throw new BusinessException("供应商名称不能重复");
		}
		Supplier dbSupplier = supplierDao.save(supplier);
		// 新增供应商账户
		SupplierAccount supplierAccount = new SupplierAccount();
		supplierAccount.setSupplier(dbSupplier);
		supplierAccountService.addSupplierAccount(supplierAccount);
		return dbSupplier;
	}

	@Override
	public SupplierVo addSupplier(SupplierBo supplierBo) {
		return supplierConvert.toVo(this.addSupplier(supplierConvert.toEntity(supplierBo)));
	}

	@Override
	public Supplier updateSupplier(Supplier supplier) {
		if (supplier == null || supplier.getId() < 1 || StringUtils.isBlank(supplier.getSupplierName())) {
			LOG.error("提交数据为空");
			throw new BusinessException("提交数据不能为空");
		}
		supplier.setSupplierName(supplier.getSupplierName().trim());
		// 校验重复
		int checkName = supplierDao.countBySupplierNameAndDeletedAndIdNot(supplier.getSupplierName(), Deleted.DEL_FALSE, supplier.getId());
		if (checkName > 0) {
			LOG.error("供应商名称已存在，supplierName={}", supplier.getSupplierName());
			throw new BusinessException("供应商名称不能重复");
		}
		Supplier dbSupplier = supplierDao.getOne(supplier.getId());
		AttributeReplication.copying(supplier, dbSupplier, Supplier_.supplierName, Supplier_.phone, Supplier_.password, Supplier_.state, Supplier_.settlementCycle,
				Supplier_.contact, Supplier_.contactPhone, Supplier_.province, Supplier_.city, Supplier_.district, Supplier_.address, Supplier_.remark);
		return dbSupplier;
	}

	@Override
	public SupplierVo updateSupplier(SupplierBo supplierBo) {
		return supplierConvert.toVo(this.updateSupplier(supplierConvert.toEntity(supplierBo)));
	}

	/**
	 * 逻辑删除
	 * 
	 * @param supplierId
	 */
	@Override
	public void removeSupplierById(int supplierId) {
		Supplier dbSupplier = supplierDao.getOne(supplierId);
		if (dbSupplier != null) {
			dbSupplier.setDelTime(new Date());
			dbSupplier.setDeleted(Deleted.DEL_TRUE);
		}
	}

	@Override
	public Page<Supplier> query(Pagination<Supplier> query) {
		query.setEntityClazz(Supplier.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Supplier_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Supplier_.createTime)));
		}));
		Page<Supplier> page = supplierDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierListVo> queryListVo(Pagination<Supplier> query) {

		Page<Supplier> pages = this.query(query);

		List<SupplierListVo> vos = supplierConvert.toListVos(pages.getContent());
		return new PageImpl<SupplierListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	public EntityListVoBoSimpleConvert<Supplier, SupplierBo, SupplierVo, SupplierSimple, SupplierListVo> getSupplierConvert() {
		return supplierConvert;
	}

	protected void initConvert() {
		this.supplierConvert = new EntityListVoBoSimpleConvert<Supplier, SupplierBo, SupplierVo, SupplierSimple, SupplierListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Supplier, SupplierVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Supplier, SupplierVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierVo SupplierVo, Supplier Supplier) {

					}
				};
			}

			@Override
			protected BeanConvert<Supplier, SupplierListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Supplier, SupplierListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierListVo SupplierListVo, Supplier Supplier) {

					}
				};
			}

			@Override
			protected BeanConvert<Supplier, SupplierBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Supplier, SupplierBo>(beanConvertManager) {
					@Override
					protected void postConvert(SupplierBo SupplierBo, Supplier Supplier) {

					}
				};
			}

			@Override
			protected BeanConvert<SupplierBo, Supplier> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierBo, Supplier>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Supplier, SupplierSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Supplier, SupplierSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SupplierSimple, Supplier> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SupplierSimple, Supplier>(beanConvertManager) {
					@Override
					public Supplier convert(SupplierSimple SupplierSimple) throws BeansException {
						return supplierDao.getOne(SupplierSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	// /**
	// * 获取供应商概况
	// *
	// * @return
	// */
	// public int[] getSupplierProfile() {
	// int[] supplierProfile = new int[5];
	// // 获取供应商总数
	// supplierProfile[0] = (int) supplierDao.count();
	// // 获取待发货订单总数(2为待发货)
	// supplierProfile[1] = saleOrderDao.countByState("2");
	// // 提现申请数量
	// supplierProfile[2] = (int) supplierWithdrawDao.count();
	// // 查询商品数量（1已上架 2未上架）
	// supplierProfile[3] = commodityDao.countByShelf(1);
	// supplierProfile[4] = commodityDao.countByShelf(2);
	// return supplierProfile;
	// }

	// /**
	// * 平台数据
	// *
	// * @return
	// */
	// public PlatformDataVo getPlatformdata() {
	// BigDecimal bigDecimal = new BigDecimal(0);
	// PlatformDataVo platformDataVo = new PlatformDataVo();
	// // 获取会员总数
	// platformDataVo.setMemberCount((int) memberDao.count());
	// // 获取供应商总数
	// platformDataVo.setSupplierCount((int) supplierDao.count());
	// // 获取订单数
	// platformDataVo.setOrderCount((int) saleOrderDao.count());
	// // 查询成交额
	// if (saleOrderDao.amount() == null) {
	// platformDataVo.setAmountSum(bigDecimal);
	// } else {
	// platformDataVo.setAmountSum(saleOrderDao.amount());
	// }
	// // 查询今天的订单数
	// platformDataVo.setNowOrderCount(getDateOrder(1));
	// // 查询近七天的订单数
	// platformDataVo.setWeekOrderCount(getDateOrder(-6));
	// // 查询近30天的订单数
	// platformDataVo.setMonthOrderCount(getDateOrder(-29));
	// return platformDataVo;
	// }
	//
	// /**
	// * 查询规定日期的订单数
	// *
	// * @param day
	// * @return
	// */
	// public int getDateOrder(int day) {
	// Date start = new Date();// 开始时间
	// Date end = new Date();// 创建结束时间
	// Calendar calendar = new GregorianCalendar();
	// calendar.setTime(end);
	// // 不是查询当天的也需要往后推一天（计算到当前天）
	// if (day != 1) {
	// calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
	// start = calendar.getTime();
	// }
	// calendar.add(calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
	// end = calendar.getTime(); // 这个时间就是日期往后推一天的结果
	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	// Date ends = java.sql.Date.valueOf(formatter.format(end));
	// Date starts = java.sql.Date.valueOf(formatter.format(start));
	//
	// // 判断是不是查询今日的，查询当天的和过去式的算法不一样
	// if (day != 1) {
	// return saleOrderDao.timeRange(ends, starts);
	// }
	// return saleOrderDao.timeRange(starts, ends);
	//
	// }

	// /**
	// * 供应商销售列表
	// *
	// * @param id
	// * @return
	// */
	// public List<SalesList> getSalesList(int id) {
	// List<SalesList> list = new ArrayList<>();
	// //获取商品名称
	// Supplier supplier = supplierDao.getOne(id);
	// if(supplier!=null){
	// //获取商品
	// for (Commodity i : supplier.getCommoditys()) {
	// SalesList salesList = new SalesList();
	// salesList.setCommodityName(i.getCommodityName());
	// //获取订单数
	// salesList.setOrderCount(saleOrderItemDao.countByProduct(productDao.findByCommodity(i)));
	// //获取销量
	// if (productDao.findByCommodity(i) != null) {
	// salesList.setTotalSales(saleOrderItemDao.findByProducts(productDao.findByCommodity(i)));
	// } else {
	// salesList.setTotalSales(0);
	// }
	// list.add(salesList);
	// }
	// }
	// return list;
	// }
	@Override
	public SupplierListVo updateState(int supplierId) {
		Supplier dbSupplier = this.getSupplierById(supplierId);
		if (dbSupplier != null) {
			if (CmsEnum.STATE_DISABLE.getCode().equals(dbSupplier.getState())) {
				dbSupplier.setState(CmsEnum.STATE_ENABLE.getCode());
			} else if (CmsEnum.STATE_ENABLE.getCode().equals(dbSupplier.getState())) {
				dbSupplier.setState(CmsEnum.STATE_DISABLE.getCode());
			}
			return supplierConvert.toListVo(dbSupplier);
		}
		return null;
	}

	@Override
	public List<SupplierListVo> querySupplierList() {
		List<Supplier> resultList = supplierDao.findAll();
		return supplierConvert.toListVos(resultList);
	}

	/**
	 * 供应商登录
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierVo login(String username, String password) {
		return supplierConvert.toVo(supplierDao.findByPhoneAndPasswordAndDeleted(username, password, Deleted.DEL_FALSE));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSupplierNum() {
		return supplierDao.countByDeleted(Deleted.DEL_FALSE);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getDailyAddNumByDate(Date startDate, Date endDate) {
		return supplierDao.findDailyAddNumByDate(Deleted.DEL_FALSE, startDate, endDate);
	}

}
