package com.springhotel.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {
	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("=============================================");
			logger.debug("RequestURI  : " + req.getRequestURI());
			if (req.getQueryString() != null)
				logger.debug("QueryStirng : " + req.getQueryString());
			logger.debug("Method      : " + req.getMethod());
			
		}
		return super.preHandle(req, res, handler);
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("=============================================");
		}
	}
}