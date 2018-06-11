package com.bookStore.admin.order.dao;

import java.util.List;

import com.bookStore.commons.beans.Order;
import com.bookStore.commons.beans.OrderItem;

public interface IAdminOrderDao {

	List<Order> selectOrders();

	List<OrderItem> selectOrderItemById(String id);

	void deleteOrderById(String id);

	void deleteOrderItemById(String id);

	List<Order> selectOrderByManyCondition(Order order);

}
