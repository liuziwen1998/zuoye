package com.bookStore.client.cart.dao;

import com.bookStore.commons.beans.Order;
import com.bookStore.commons.beans.OrderItem;

public interface ICartDao {

	int insertOrderItem(OrderItem orderItem);

	int insertOrder(Order order);

	void updateOrderPayState(String out_trade_no);

	void updateProducePnum(OrderItem orderItem);

	void deleteOrderByid(String id);

	void deleteOrderItemById(String id);

}
