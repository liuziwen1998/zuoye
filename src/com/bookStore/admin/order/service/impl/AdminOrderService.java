package com.bookStore.admin.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.admin.order.dao.IAdminOrderDao;
import com.bookStore.admin.order.service.IAdminOrderService;
import com.bookStore.commons.beans.Order;
import com.bookStore.commons.beans.OrderItem;

@Service
public class AdminOrderService implements IAdminOrderService {
	
	@Autowired
	private IAdminOrderDao adminOrderDao;

	@Override
	public List<Order> findOrders() {
		return adminOrderDao.selectOrders();
	}

	@Override
	public List<OrderItem> findOrderItemById(String id) {
		return adminOrderDao.selectOrderItemById(id);
	}

	@Override
	public void removeOrderById(String id) {
		adminOrderDao.deleteOrderById(id);
		adminOrderDao.deleteOrderItemById(id);
		
	}

	@Override
	public List<Order> findOrderByManyCondition(Order order) {
		return adminOrderDao.selectOrderByManyCondition(order);
	}

}
