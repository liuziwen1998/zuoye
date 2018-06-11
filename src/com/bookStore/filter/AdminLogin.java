package com.bookStore.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookStore.commons.beans.User;

public class AdminLogin implements Filter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		User login_user = (User) request.getSession().getAttribute("login_user");
		if(login_user != null){
			if("超级用户".equals(login_user.getRole())){
				arg2.doFilter(request, response);
			}else{
				response.sendRedirect(request.getContextPath()+"/client/error/privilege.jsp");
			}
		}else{
			response.sendRedirect(request.getContextPath()+"/client/error/privilege.jsp");
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
