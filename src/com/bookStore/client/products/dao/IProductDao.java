package com.bookStore.client.products.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bookStore.commons.beans.Notice;
import com.bookStore.commons.beans.Product;

public interface IProductDao {

	List<Product> selectProduct(HashMap<Object, Object> map);

	int selectProductCount(Product product);

	Product selectProductById(String id);

	Notice selectNotice();

	List<Product> selectWeekHostProduct();

}
