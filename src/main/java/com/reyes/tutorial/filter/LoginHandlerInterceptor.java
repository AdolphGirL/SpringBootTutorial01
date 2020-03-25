package com.reyes.tutorial.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 攔截器，防止未登入者可以查看其餘頁面
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
	
	private final static Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);
	
	public LoginHandlerInterceptor(){
		logger.info("[LoginHandlerInterceptor]-[加載]-[LoginHandlerInterceptor攔截器]");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object user = request.getSession().getAttribute("loginUser");
		if(user != null){
			return true;
		}
		
//		為登入者，導到登入頁面
		request.setAttribute("loginMsg", "請先登入");
		request.getRequestDispatcher("/login").forward(request, response);
		
		return false;
	}
	
}
