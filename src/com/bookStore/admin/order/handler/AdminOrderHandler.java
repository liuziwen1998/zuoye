package com.bookStore.admin.order.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookStore.admin.order.service.IAdminOrderService;
import com.bookStore.commons.beans.Order;
import com.bookStore.commons.beans.OrderItem;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderHandler {

	@Autowired
	private IAdminOrderService adminOrderService;
	
	@RequestMapping("/findOrders.do")
	public String findOrders(Model model){
		List<Order> orders = adminOrderService.findOrders();
		model.addAttribute("orders", orders);
		return "/admin/orders/list.jsp";
	}
	
	@RequestMapping("/findOrderByManyCondition.do")
	public String findOrderByManyCondition(Order order, Model model){
		List<Order> orders = adminOrderService.findOrderByManyCondition(order);
		model.addAttribute("orders", orders);
		return "/admin/orders/list.jsp";
	}
	
	@RequestMapping("/findOrderById.do")
	public String findOrderById(String id, Model model){
		List<OrderItem> items = adminOrderService.findOrderItemById(id);
		model.addAttribute("items", items);
		return "/admin/orders/view.jsp";
	}
	
	@RequestMapping("/delOrderById.do")
	public String delOrderById(String id){
        adminOrderService.removeOrderById(id);
		
		return "/admin/order/findOrders.do";
	}
	
	
	
	
	
}
