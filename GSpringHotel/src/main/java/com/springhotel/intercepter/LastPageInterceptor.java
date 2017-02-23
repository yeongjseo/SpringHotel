package com.springhotel.intercepter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LastPageInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,
							ModelAndView mv) throws Exception {
		HttpSession session = req.getSession(false);

		if (session == null)
			return;
		
		
		String uri = req.getRequestURI();
		if (uri.indexOf(req.getContextPath()) == 0) {
			uri = uri.substring(req.getContextPath().length());
		}
		if (uri.substring(0, 1).equals("/"))
			uri = uri.substring(1);
		
		if (uri.equals(""))
			return;
		
		String query = req.getQueryString();
		if (query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}
		
		if (mv != null) {
			Map<String, Object> map = mv.getModel();
			if (map != null) {
				if (map.get("ignoreLastPage") != null)
					return;
			}
			else
				return;
		}
		else
			return;
		
		if (! req.getMethod().equals("GET"))
			return;

		logger.info("lastPage: " + (uri + query));
			
		session.setAttribute("lastPage", uri + query);
	}

	
}