package com.bookStore.client.products.service;

import java.util.List;

import com.bookStore.commons.beans.Notice;
import com.bookStore.commons.beans.Product;
import com.bookStore.utils.PageModel;

public interface IProductService {

	int findProductCount(Product product);

	List<Product> findProducts(Product product, PageModel pageModel);

	Product findProductById(String id);

	Notice findRecentNotice();

	List<Product> findWeekHostProduct();

}
