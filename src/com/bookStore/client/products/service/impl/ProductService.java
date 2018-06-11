package com.bookStore.client.products.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.client.products.dao.IProductDao;
import com.bookStore.client.products.service.IProductService;
import com.bookStore.commons.beans.Notice;
import com.bookStore.commons.beans.Product;
import com.bookStore.utils.PageModel;

@Service
public class ProductService implements IProductService {
	@Autowired
	private IProductDao productDao;

	@Override
	public int findProductCount(Product product) {
		return productDao.selectProductCount(product);
	}

	@Override
	public List<Product> findProducts(Product product, PageModel pageModel) {
		HashMap<Object, Object> map = new HashMap<>();
		map.put("product", product);
		map.put("start", pageModel.getFirstLimitParam());
		map.put("pageSize", pageModel.getPageSize());
		return productDao.selectProduct(map);
	}

	@Override
	public Product findProductById(String id) {
		return productDao.selectProductById(id);
	}

	@Override
	public Notice findRecentNotice() {
		return productDao.selectNotice();
	}

	@Override
	public List<Product> findWeekHostProduct() {
		return productDao.selectWeekHostProduct();
	}

	

}
