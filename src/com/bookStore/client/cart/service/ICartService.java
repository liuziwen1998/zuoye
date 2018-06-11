package com.bookStore.client.cart.service;

import java.util.Map;

import com.bookStore.commons.beans.Order;
import com.bookStore.commons.beans.Product;

public interface ICartService {

	void paySuccess(String out_trade_no);

	void createOrder(Order order, Map<Product, Integer> cart);

	void delOrderById(String id);

}
