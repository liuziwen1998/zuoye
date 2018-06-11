package com.bookStore.admin.product.service;

import java.util.List;

import com.bookStore.commons.beans.Product;
import com.bookStore.commons.beans.ProductList;

public interface IAdminProductService {

	List<Product> findProduct();

	List<Product> findProductByManyCondition(Product product, String minprice, String maxprice);

	int addProduct(Product product);

	Product findProductById(String id);

	int modifyProduct(Product product);

	int removeProduct(String id);

	List<ProductList> findProductList(String year, String month);

}
