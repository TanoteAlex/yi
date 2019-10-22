/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.data.domain.Page;

import com.yi.core.commodity.domain.bo.ProductBo;
import com.yi.core.commodity.domain.entity.Attribute;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.domain.vo.ProductListVo;
import com.yi.core.commodity.domain.vo.ProductVo;
import com.yihz.common.persistence.Pagination;

/**
 * 货品
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IProductService {

	/**
	 * 分页查询: Product
	 * 
	 * @param query
	 * @return
	 */
	Page<Product> query(Pagination<Product> query);

	/**
	 * 分页查询: ProductListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<ProductListVo> queryListVo(Pagination<Product> query);

	/**
	 * 根据Entity创建Product
	 * 
	 * @param product
	 * @return
	 */
	Product addProduct(Product product);

	/**
	 * 根据BO创建Product
	 * 
	 * @param productBo
	 * @return
	 */
	ProductVo addProduct(ProductBo productBo);

	/**
	 * 根据Entity更新Product
	 * 
	 * @param product
	 * @return
	 */
	Product updateProduct(Product product);

	/**
	 * 根据BO更新Product
	 * 
	 * @param productBo
	 * @return
	 */
	ProductVo updateProduct(ProductBo productBo);

	/**
	 * 删除Product
	 * 
	 * @param productId
	 */
	void removeProductById(int productId);

	/**
	 * 根据ID得到Product
	 * 
	 * @param productId
	 * @return
	 */
	Product getById(int productId);

	/**
	 * 根据ID得到ProductVo
	 * 
	 * @param productId
	 * @return
	 */
	ProductVo getVoById(int productId);

	/**
	 * 根据ID得到ProductVo
	 * 
	 * @param productId
	 * @return
	 */
	ProductVo getVoByIdForApp(int productId);

	/**
	 * 根据ID得到ProductListVo
	 * 
	 * @param productId
	 * @return
	 */
	ProductListVo getListVoById(int productId);

	/**
	 * 批量新增商品
	 * 
	 * @param commodity
	 * @param products
	 */
	List<Product> batchAddProduct(Commodity commodity, Collection<Product> products, Map<String, Attribute> attributeMap);

	/**
	 * 批量修改商品
	 * 
	 * @param commodity
	 * @param products
	 */
	List<Product> batchUpdateProduct(Commodity commodity, Collection<Product> products, Map<String, Attribute> attributeMap);

	/**
	 * 批量删除商品
	 * 
	 * @param commodity
	 * @param products
	 */
	void batchDeleteProduct(Collection<Product> products);

	/**
	 * 下单时检查货品
	 * 
	 * @param productId
	 * @return
	 */
	Product checkProductForOrder(int productId);

	/**
	 * 
	 * 修改商品时 根据需要 删除货品
	 * 
	 * @param commodityId
	 */
	void deleteByCommodity(Integer commodityId);

	/**
	 * 根据商品查询货品
	 * 
	 * @param commodityId
	 * @return
	 */
	List<Product> findByCommodity_idAndDeleted(Integer commodityId);


	EntityListVoBoSimpleConvert<Product, ProductBo, ProductVo, ProductSimple, ProductListVo> getProductConvert();
}
