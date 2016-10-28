package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class UserSessionFilter implements Filter {
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		HttpServletRequest httpReq =(HttpServletRequest)req;
		Object userObj = httpReq.getSession().getAttribute("user");
		
		if(userObj == null){
			req.getRequestDispatcher("Login.jsp").forward(req, resp);
			return;
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig filterCfg) throws ServletException {
	}

}
