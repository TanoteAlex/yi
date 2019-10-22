/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.yi.core.commodity.service.ICommodityService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.commodity.CommodityEnum;
import com.yi.core.commodity.dao.StockDao;
import com.yi.core.commodity.domain.bo.StockBo;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.domain.entity.Stock;
import com.yi.core.commodity.domain.entity.Stock_;
import com.yi.core.commodity.domain.simple.StockSimple;
import com.yi.core.commodity.domain.vo.StockListVo;
import com.yi.core.commodity.domain.vo.StockVo;
import com.yi.core.commodity.service.IStockRecordService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.common.Deleted;
import com.yi.core.order.domain.entity.AfterSaleOrder;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 库存
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class StockServiceImpl implements IStockService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(StockServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private StockDao stockDao;

	@Resource
	private IStockRecordService stockRecordService;

	@Resource
	private ICommodityService commodityService;

	private EntityListVoBoSimpleConvert<Stock, StockBo, StockVo, StockSimple, StockListVo> stockConvert;

	/**
	 * 分页查询Stock
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<Stock> query(Pagination<Stock> query) {
		query.setEntityClazz(Stock.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Stock_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<Stock> page = stockDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: Stock
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<StockListVo> queryListVo(Pagination<Stock> query) {
		query.setEntityClazz(Stock.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Stock_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<Stock> pages = stockDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.getCommodity().setCategory(null);
			tmp.getCommodity().setSupplier(null);
			tmp.getProduct().setAttributes(null);
		});
		List<StockListVo> vos = stockConvert.toListVos(pages.getContent());
		return new PageImpl<StockListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建Stock
	 **/
	@Override
	public Stock addStock(Stock stock) {
		if (stock == null || stock.getProduct() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		// 检查该货品的库存是否存在 存在就更新 不存在就新增
		Stock dbStock = stockDao.findByProduct_IdAndDeleted(stock.getProduct().getId(), Deleted.DEL_FALSE);
		if (dbStock != null) {
			dbStock.setStockQuantity(stock.getProduct().getStockQuantity());
		} else {
			stock.setStockQuantity(stock.getProduct().getStockQuantity());
			stock.setLockQuantity(0);
			stock.setUseQuantity(0);
			stock.setRemark("新增库存");
			dbStock = stockDao.save(stock);
		}
		return dbStock;
	}

	/**
	 * 创建Stock
	 **/
	@Override
	public StockVo addStock(StockBo stockBo) {
		return stockConvert.toVo(this.addStock(stockConvert.toEntity(stockBo)));
	}

	/**
	 * 更新Stock
	 **/
	@Override
	public Stock updateStock(Stock stock) {
		if (stock == null || stock.getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		Stock dbStock = stockDao.getOne(stock.getId());
		AttributeReplication.copying(stock, dbStock, Stock_.stockQuantity, Stock_.lockQuantity, Stock_.useQuantity, Stock_.sort, Stock_.remark);
		return dbStock;
	}

	/**
	 * 更新Stock
	 **/
	@Override
	public StockVo updateStock(StockBo stockBo) {
		return stockConvert.toVo(this.updateStock(stockConvert.toEntity(stockBo)));
	}

	/**
	 * 删除Stock
	 **/
	@Override
	public void removeStockById(int id) {
		Stock dbStock = stockDao.getOne(id);
		if (dbStock != null) {
			dbStock.setDeleted(Deleted.DEL_TRUE);
			dbStock.setDelTime(new Date());
		}
	}

	/**
	 * 根据ID得到Stock
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Stock getStockById(int id) {
		if (stockDao.existsById(id)) {
			return this.stockDao.getOne(id);
		}
		return null;

	}

	/**
	 * 根据ID得到Stock
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public StockVo getStockVoById(int id) {
		Stock dbStock = this.getStockById(id);
		if (dbStock != null) {
			dbStock.getCommodity().setCategory(null);
			dbStock.getCommodity().setSupplier(null);
			dbStock.getProduct().setAttributes(null);
			return stockConvert.toVo(dbStock);
		}
		return null;
	}

	/**
	 * 根据ID得到StockListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public StockListVo getListVoById(int id) {
		return stockConvert.toListVo(this.stockDao.getOne(id));
	}

	protected void initConvert() {
		this.stockConvert = new EntityListVoBoSimpleConvert<Stock, StockBo, StockVo, StockSimple, StockListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Stock, StockVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Stock, StockVo>(beanConvertManager) {
					@Override
					protected void postConvert(StockVo stockVo, Stock stock) {

					}
				};
			}

			@Override
			protected BeanConvert<Stock, StockListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Stock, StockListVo>(beanConvertManager) {
					@Override
					protected void postConvert(StockListVo stockListVo, Stock stock) {

					}
				};
			}

			@Override
			protected BeanConvert<Stock, StockBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Stock, StockBo>(beanConvertManager) {
					@Override
					protected void postConvert(StockBo stockBo, Stock stock) {

					}
				};
			}

			@Override
			protected BeanConvert<StockBo, Stock> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<StockBo, Stock>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Stock, StockSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Stock, StockSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<StockSimple, Stock> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<StockSimple, Stock>(beanConvertManager) {
					@Override
					public Stock convert(StockSimple stockSimple) throws BeansException {
						return stockDao.getOne(stockSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	@Override
	public void batchAddStock(Collection<Product> products, Commodity commodity) {
		if (CollectionUtils.isNotEmpty(products) && commodity != null) {
			Set<Stock> stocks = new HashSet<>();
			for (Product tmp : products) {
				if (tmp != null) {
					Stock stock = new Stock();
					// stock.setSupplier(commodity.getSupplier());
					stock.setCommodity(commodity);
					stock.setProduct(tmp);
					stock.setStockQuantity(tmp.getStockQuantity());
					stock.setLockQuantity(0);
					stock.setUseQuantity(0);
					stock.setRemark("自动新增库存");
					stocks.add(stock);
				}
			}
			if (CollectionUtils.isNotEmpty(stocks)) {
				stockDao.saveAll(stocks);
			}
		}
	}

	@Override
	public void batchUpdateStock(Collection<Product> products, Commodity commodity) {
		if (CollectionUtils.isNotEmpty(products) && commodity != null) {
			// 查询当前商品下的库存
			Set<Stock> dbStocks = stockDao.findByCommodity_idAndDeleted(commodity.getId(), Deleted.DEL_FALSE);
			// 需要新增的数据
			Set<Product> saveSets = products.stream().filter(e1 -> dbStocks.stream().noneMatch(e2 -> e1.getId() == e2.getProduct().getId())).collect(Collectors.toSet());
			// 需要删除的数据
			Set<Stock> deleteSets = dbStocks.stream().filter(e1 -> products.stream().noneMatch(e2 -> e1.getProduct().getId() == e2.getId())).collect(Collectors.toSet());
			// 保存数据
			Set<Stock> saveStocks = new HashSet<>();
			for (Product tmp : saveSets) {
				if (tmp != null) {
					Stock stock = new Stock();
					stock.setSupplier(commodity.getSupplier());
					stock.setCommodity(commodity);
					stock.setProduct(tmp);
					stock.setStockQuantity(tmp.getStockQuantity());
					stock.setLockQuantity(0);
					stock.setUseQuantity(0);
					stock.setRemark("自动新增库存");
					saveStocks.add(stock);
				}
			}
			if (CollectionUtils.isNotEmpty(saveStocks)) {
				stockDao.saveAll(saveStocks);
			}
			// 删除数据
			deleteSets.forEach(tmp -> {
				if (tmp != null) {
					tmp.setDeleted(Deleted.DEL_TRUE);
					tmp.setDelTime(new Date());
				}
			});
			// 去除需要删除的库存
			dbStocks.removeAll(deleteSets);
			// 去除需要保存的货品
			products.removeAll(saveSets);
			// 处理需要更新的数据
			for (Stock stock : dbStocks) {
				for (Product product : products) {
					if (stock != null && product != null && stock.getProduct().getId() == product.getId()) {
						stock.setStockQuantity(product.getStockQuantity());
					}
				}
			}
		}
	}

	@Override
	public void deleteByCommodity(Integer commodityId) {
		if (commodityId != null && commodityId.intValue() > 0) {
			Set<Stock> stocks = stockDao.findByCommodity_idAndDeleted(commodityId, Deleted.DEL_FALSE);
			stocks.forEach(e -> {
				e.setDeleted(Deleted.DEL_TRUE);
				e.setDelTime(new Date());
			});
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkStock(Integer productId) {
		Stock stock = stockDao.findByProduct_IdAndDeleted(productId, Deleted.DEL_FALSE);
		if (stock == null) {
			LOG.error("productId={}，库存不存在");
			return false;
		}
		if (stock.getStockQuantity() < stock.getUseQuantity() + stock.getLockQuantity()) {
			LOG.error("productId={}，库存不足");
			return false;
		}
		return true;
	}

	/**
	 * 校验库存
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkStock(Integer productId, int quantity) {
		Stock stock = stockDao.findByProduct_IdAndDeleted(productId, Deleted.DEL_FALSE);
		if (stock == null) {
			LOG.error("productId={}，库存不足", productId);
			return false;
		}
		if (stock.getLockQuantity() + stock.getUseQuantity() + quantity > stock.getStockQuantity()) {
			LOG.error("productId={}，库存不足", productId);
			return false;
		}
		return true;
	}

	/**
	 * TODO 待优化
	 */
	@Override
	public void updateStockByCommodity(Commodity commodity) {
		if (commodity != null && CollectionUtils.isNotEmpty(commodity.getProducts())) {
			Set<Integer> productIds = new HashSet<>();
			for (Product tmp : commodity.getProducts()) {
				if (tmp != null && tmp.getId() > 0) {
					productIds.add(tmp.getId());
				}
			}
			// 查询当前商品的货品库存
			List<Stock> dbStocks = stockDao.findByCommodity_IdAndDeletedAndProduct_IdNotIn(commodity.getId(), Deleted.DEL_FALSE, productIds.toArray());
			// 新增的货品库存
			Set<Stock> addStocks = new HashSet<>();
			if (CollectionUtils.isNotEmpty(dbStocks)) {
				for (Product tmpProduct : commodity.getProducts()) {
					if (tmpProduct != null && !productIds.contains(tmpProduct.getId())) {
						Stock stock = new Stock();
						stock.setCommodity(commodity);
						stock.setProduct(tmpProduct);
						// stock.setStockQuantity(tmpProduct.getStock());
						stock.setRemark("修改商品时，自动新增库存");
						addStocks.add(stock);
					}
				}
				// 删除多余的库存数据
				stockDao.deleteAll(dbStocks);
			}
			if (CollectionUtils.isNotEmpty(addStocks)) {
				stockDao.saveAll(addStocks);
			}
		}
	}

	@Override
	public void useStockBySubmitOrder(List<SaleOrder> saleOrders) {
		if (CollectionUtils.isNotEmpty(saleOrders)) {
			for (SaleOrder tmpOrder : saleOrders) {
				if (tmpOrder != null && CollectionUtils.isNotEmpty(tmpOrder.getSaleOrderItems())) {
					tmpOrder.getSaleOrderItems().forEach(tmpItem -> {
						if (tmpItem != null) {
							Stock dbStock = stockDao.findByProduct_IdAndDeleted(tmpItem.getProduct().getId(), Deleted.DEL_FALSE);
							if (dbStock != null) {
								// 下单减库存
								if (CommodityEnum.STOCK_SET_ORDER.getCode().equals(tmpItem.getProduct().getCommodity().getStockSet())) {
									dbStock.setUseQuantity(dbStock.getUseQuantity() + tmpItem.getQuantity());
									// 添加使用记录
									stockRecordService.addStockRecordByOrder(tmpOrder.getMember(), tmpOrder, tmpItem.getProduct(), tmpItem.getQuantity(), "下单减库存");
									// 支付减库存 先锁定库存
								} else if (CommodityEnum.STOCK_SET_PAY.getCode().equals(tmpItem.getProduct().getCommodity().getStockSet())) {
									dbStock.setLockQuantity(dbStock.getLockQuantity() + tmpItem.getQuantity());
									// 添加 锁定记录
									stockRecordService.addStockRecordByOrder(tmpOrder.getMember(), tmpOrder, tmpItem.getProduct(), tmpItem.getQuantity(), "下单锁定库存");
								}
							}
						}
					});
				}
			}
		}
	}

	@Override
	public void useStockByPayOrder(SaleOrder... saleOrders) {
		if (ArrayUtils.isNotEmpty(saleOrders)) {
			for (SaleOrder tmpOrder : saleOrders) {
				if (tmpOrder != null && CollectionUtils.isNotEmpty(tmpOrder.getSaleOrderItems())) {
					tmpOrder.getSaleOrderItems().forEach(tmpItem -> {
						if (tmpItem != null) {
							Stock dbStock = stockDao.findByProduct_IdAndDeleted(tmpItem.getProduct().getId(), Deleted.DEL_FALSE);
							if (dbStock != null) {
								// 支付减库存
								if (CommodityEnum.STOCK_SET_PAY.getCode().equals(tmpItem.getProduct().getCommodity().getStockSet())) {
									// 减去锁定库存
									dbStock.setLockQuantity(dbStock.getLockQuantity() - tmpItem.getQuantity());
									// 增加使用库存量
									dbStock.setUseQuantity(dbStock.getUseQuantity() + tmpItem.getQuantity());
									// 添加使用记录
									stockRecordService.addStockRecordByOrder(tmpOrder.getMember(), tmpOrder, tmpItem.getProduct(), tmpItem.getQuantity(), "支付减库存");
								}
							}
						}
						// 修改商品销售数量
						commodityService.updateCommodityBySaleQuantity(tmpItem.getQuantity(), tmpItem.getCommodity().getId());
					});

				}
			}
		}
	}

	@Override
	public void returnStockByRefundOrder(AfterSaleOrder afterSaleOrder) {
		if (afterSaleOrder != null && afterSaleOrder.getSaleOrder() != null) {

		}
	}

}
