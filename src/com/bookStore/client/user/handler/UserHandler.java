package com.bookStore.client.user.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bookStore.client.user.service.IUserService;
import com.bookStore.commons.beans.Order;
import com.bookStore.commons.beans.OrderItem;
import com.bookStore.commons.beans.User;
import com.bookStore.utils.ActiveCodeUtils;

@Controller
@RequestMapping("/client/user")
public class UserHandler {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/findEmail.do")
	@ResponseBody
	public String findEmail(String email){
		User user = userService.findEmail(email);
		if(user != null){
			return "EXIST";
		}else{
			return "OK";
		}
	}
	
	@RequestMapping("/register.do")    //用户注册
	public String register(User user, String checkcode, HttpServletRequest request, HttpSession session){
		String checkcode_session = (String) session.getAttribute("checkcode_session"); //获取CheckImageServlet中产生的验证码
		if(checkcode_session.equals(checkcode)){
			user.setActiveCode(ActiveCodeUtils.createActiveCode());
			int rows = userService.addUser(user, request);
			if(rows > 0){
				return "/client/registersuccess.jsp";
			}else{
				return "/client/register.jsp";
			}
		}else{
			request.setAttribute("checkcode_error", "验证码错误！");
			return "/client/register.jsp";
		}
	}
	
	@RequestMapping("/activeUser.do")  //账户激活
	public String activeUser(String activeCode){
		int rows = userService.activeUser(activeCode);
		if(rows > 0){
			return "/client/myAccount.jsp";
		}else{
			return "/client/register.jsp";
		}
	}
	
	@RequestMapping("/myAccount.do")
	public String myAccount(HttpSession session, HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		//获取session中的登陆信息
		User login_user = (User) session.getAttribute("login_user");
		//判断当前会话是否已经登陆过
		if(login_user != null){
			if(login_user.getRole().equals("超级用户")){
				return "/admin/login/home.jsp";
			}else{
				return "/client/myAccount.jsp";
			}
		}else{
			User login_user1 = autologin(request);
			if(login_user1.getUsername() != null && login_user1.getPassword() != null){
				session.setAttribute("login_user", login_user1);
				if(login_user1.getRole().equals("超级用户")){
					return "/admin/login/home.jsp";
				}else{
					return "/client/myAccount.jsp";
				}
			}else if(login_user1.getUsername() != null){
				model.addAttribute("username", login_user1.getUsername());
				return "/client/login.jsp";
			}else{
				return "/client/login.jsp";
			}
		}
		
	}
	
	@RequestMapping("/login.do")   //退出登陆
	public String login(User user, String remeber, String autologin, HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model)
        throws UnsupportedEncodingException{
		User login_user = userService.findUserByUsernameAndPassword(user);
		if(login_user != null){
			//用户名已激活
			if(login_user.getState()==1){
				session.setAttribute("login_user", login_user);
				//记住用户名
				if("1".equals(remeber)){
					this.addCookie(autologin, login_user, request, response);
					//记住用户名和密码，自动登录
				}else if("1".equals(autologin)){
					this.addCookie(autologin, login_user, request, response);
				}
				if(login_user.getRole().equals("超级用户")){
					return "/admin/login/home.jsp";
				}else{
					return "/client/myAccount.jsp";
				}
			}else{
				model.addAttribute("register_message", "账户未激活，请激活后使用！");
				return "/client/login.jsp";
			}
		}else{
			model.addAttribute("register_message", "用户名或密码错误！");
			return "/client/login.jsp";
		}
	}
	
	@RequestMapping("/modifyUser.do")
	public String modifyUser(User user, HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response){
		User login_user = (User) session.getAttribute("login_user");
		user.setId(login_user.getId());
		int rows = userService.modifyUser(user);
		if(rows>0){
			model.addAttribute("register_message", "用户信息修改成功，请重新登录！");
			this.logout1(session, request, response);
			return "/client/login.jsp";
		}else{
			model.addAttribute("register_message", "用户信息修改失败！");
			return "/client/modifyuserinfo.jsp";
		}
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		this.logout1(session, request, response);
		
		return "/client/login.jsp";
	}
	
	//退出登录后清除用户信息方法
	public void logout1(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		session.removeAttribute("login_user");
		Cookie cookie1 = new Cookie("bookstore_username", null);
		cookie1.setMaxAge(0);
		cookie1.setPath(request.getContextPath()+"/");
		Cookie cookie2 = new Cookie("bookstore_password", null);
		cookie2.setMaxAge(0);
		cookie2.setPath(request.getContextPath()+"/");
		response.addCookie(cookie1);
		response.addCookie(cookie2);
	}
	
	//将用户名和密码保存到cookie中
	public void addCookie(String flag, User user, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		Cookie cookie1 = new Cookie("bookstore_username", URLEncoder.encode(user.getUsername(), "utf-8"));
		cookie1.setMaxAge(60*60*24*30);
		cookie1.setPath(request.getContextPath()+"/");
		response.addCookie(cookie1);
		if("1".equals(flag)){
			Cookie cookie2 = new Cookie("bookstore_password", URLEncoder.encode(user.getPassword(), "utf-8"));
			cookie2.setMaxAge(60*60*24*30);
			cookie2.setPath(request.getContextPath()+"/");
			response.addCookie(cookie2);

		}
	}
	
	//自动登陆方法
	public User autologin(HttpServletRequest request) throws UnsupportedEncodingException{
		Cookie[] cookies = request.getCookies();
		String username = null;
		String password = null;
		User user = new User();
		//从cookie查找保存的用户名和密码
		for(Cookie cookie:cookies){
			if(cookie.getName().equals("bookstore_password")){
				password = URLDecoder.decode(cookie.getValue(), "utf-8");
			}
			if(cookie.getName().equals("bookstore_username")){
				username = URLDecoder.decode(cookie.getValue(), "utf-8");
			}
		}
		user.setUsername(username);
		user.setPassword(password);
		User user1= new User();
		user1.setUsername(username);
		//判断cookie中的用户名和密码
		if(user.getUsername() != null && user.getPassword() != null){
			//判定自动登陆的用户是否修改过
			User login_user = userService.findUserByUsernameAndPassword(user);
			if(login_user != null){
				return login_user;	
			}else{
				return user1;
			}
		}else {
			return user1;
		}
	}
	
	
	/*
	 * 用户订单查询
	 * 
	 * */
	@RequestMapping("/findOrderByUser.do")
	public String findOrderByUser(HttpSession session, Model model){
		User login_user = (User) session.getAttribute("login_user");
		List<Order> orders = userService.findOrderByUser(login_user.getId());
		model.addAttribute("orders", orders);
		return "/client/orderlist.jsp";
	}
	
	@RequestMapping("/findOrderById.do")
	public String findOrderById(String id, Model model){
		List<OrderItem> orderItems = userService.findOrderItemById(id);
		
		model.addAttribute("orderItems", orderItems);
		
		return "/client/orderInfo.jsp";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
