package com.springhotel.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.springhotel.dto.MemberDTO;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		HttpSession session = req.getSession(false);

		if (session == null)
			return true;
		
		if (session.getAttribute("loginDTO") == null) {
			res.sendRedirect("memberLogin.do");
			/* Cannot call sendRedirect() after the response has been committed */
			return false;
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,
							ModelAndView modelAndView) throws Exception {

	}

	
}