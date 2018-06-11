package com.bookStore.admin.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bookStore.commons.beans.Product;
import com.bookStore.commons.beans.ProductList;

public interface IAdminProductDao {

	List<Product> selectProduct();

	List<Product> selectProductByManyCondition(Map map);

	int insertProduct(Product product);

	Product selectProductById(String id);

	int updateProduct(Product product);

	int deleteProduct(String id);

	List<ProductList> selectProductList(Map map);

}
