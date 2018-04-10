package com.cehome.apimanager.filter;

import com.cehome.apimanager.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserLoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();
		if(WebUtils.isLogin(session)){
			chain.doFilter(request, response);
		} else {
			httpRequest.getRequestDispatcher("/login.html").forward(request,response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}
	
	public boolean isValidHeader(String headerName){
		return false;
	}
}
