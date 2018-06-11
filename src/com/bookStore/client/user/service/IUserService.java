package com.bookStore.client.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bookStore.commons.beans.Order;
import com.bookStore.commons.beans.OrderItem;
import com.bookStore.commons.beans.User;

public interface IUserService {

	User findEmail(String email);

	int addUser(User user, HttpServletRequest request);

	int activeUser(String activeCode);

	User findUserByUsernameAndPassword(User user);

	int modifyUser(User user);

	List<Order> findOrderByUser(Integer id);

	List<OrderItem> findOrderItemById(String id);

}
