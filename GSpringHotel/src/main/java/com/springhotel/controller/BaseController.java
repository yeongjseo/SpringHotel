package com.springhotel.controller;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.springhotel.common.Env;

public class BaseController {
	private static final String PATH_INDEX = "common/index.jsp";
	
	@Resource(name="env")
	private Env env;
	
	
	public ModelAndView getModelAndView(String page) {
		ModelAndView mv = new ModelAndView(PATH_INDEX);
		mv.addObject("page", page + ".jsp");
		return mv;
	}
	
	public StringBuffer parseReq(HttpServletRequest req, StringBuffer sb) {
		@SuppressWarnings("unchecked")
		Enumeration<String> names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = req.getParameter(name);
			sb.append(name + " : " + value + "\n");
		}
		return sb;
	}
	
	public ModelAndView errorDebug(HttpServletRequest req, HttpServletResponse res, Exception e) {
		try {
			HttpSession session = req.getSession(false);
			if (session == null)
				return null;
			ModelAndView mv = new ModelAndView("redirect:errorDebug.do");
			StringBuffer sb = new StringBuffer();
			String msg = Thread.currentThread().getStackTrace()[2].getClassName() + ":" +  
					Thread.currentThread().getStackTrace()[2].getLineNumber();
			System.out.println("[" + msg + "] " + e.toString());
			sb = parseReq(req, sb);
			session.setAttribute("errorLoc", msg);
			session.setAttribute("errorReason", e.toString());
			session.setAttribute("errorParam", sb.toString());
			return mv;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public ModelAndView errorAccess(HttpServletRequest req, HttpServletResponse res) {
		return new ModelAndView("redirect:errorAccess.do");
	}
	
	public ModelAndView errorAuth(HttpServletRequest req, HttpServletResponse res) {
		return new ModelAndView("redirect:errorAuth.do");
	}

	
}
