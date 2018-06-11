package com.bookStore.client.cart.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.client.cart.dao.ICartDao;
import com.bookStore.client.cart.service.ICartService;
import com.bookStore.commons.beans.Order;
import com.bookStore.commons.beans.OrderItem;
import com.bookStore.commons.beans.Product;

@Service
public class CartServiceImpl implements ICartService {
	
	@Autowired
	private ICartDao cartDao;


	@Override
	public void paySuccess(String out_trade_no) {
		cartDao.updateOrderPayState(out_trade_no);
		
	}

	@Override
	public void createOrder(Order order, Map<Product, Integer> cart) {
		for(Product p:cart.keySet()){
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(p);
			orderItem.setBuynum(cart.get(p));
			
			cartDao.updateProducePnum(orderItem);
			
			cartDao.insertOrderItem(orderItem);
			
		}
		cartDao.insertOrder(order);
		
	}

	@Override
	public void delOrderById(String id) {
		
		cartDao.deleteOrderByid(id);
		
		cartDao.deleteOrderItemById(id);
		
	}

}
