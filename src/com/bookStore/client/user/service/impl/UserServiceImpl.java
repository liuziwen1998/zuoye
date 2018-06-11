package com.bookStore.client.user.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.client.user.dao.IUserDao;
import com.bookStore.client.user.service.IUserService;
import com.bookStore.commons.beans.Order;
import com.bookStore.commons.beans.OrderItem;
import com.bookStore.commons.beans.User;
import com.bookStore.utils.MailUtils;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDao userDao;

	@Override
	public User findEmail(String email) {
		return userDao.selectEmail(email);
	}

	@Override
	public int addUser(User user, HttpServletRequest request) {
		String emailMsg = "感谢注册网上书城，点击<a href='http://localhost:8080"+
	    request.getContextPath()+"/client/user/activeUser.do?activeCode="+user.getActiveCode()+
	    "'>激活</a>后使用。";
		int rows = 0;
		try {
			MailUtils.sendMail(user.getEmail(), emailMsg);
			rows = userDao.insertUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public int activeUser(String activeCode) {
		return userDao.updateUserByActiveCode(activeCode);
	}

	@Override
	public User findUserByUsernameAndPassword(User user) {
		return userDao.selectUserByUsernameAndPassword(user);
	}

	@Override
	public int modifyUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public List<Order> findOrderByUser(Integer id) {
		return userDao.selectOrderByUser(id);
	}

	@Override
	public List<OrderItem> findOrderItemById(String id) {
		return userDao.selectOrderItemById(id);
	}

}
