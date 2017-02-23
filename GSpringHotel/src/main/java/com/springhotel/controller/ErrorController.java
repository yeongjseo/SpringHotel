package com.springhotel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	@RequestMapping(value = "/errorDebug.do", method = RequestMethod.GET)
	public ModelAndView errorDebug() {
		ModelAndView mv = getModelAndView("error/errorDebug");
		mv.addObject("ignoreLastPage", true);
		return mv;
	}
	
	@RequestMapping(value = "/errorAccess.do", method = RequestMethod.GET)
	public ModelAndView errorAccess() {
		ModelAndView mv = getModelAndView("error/errorDebug");
		mv.addObject("ignoreLastPage", true);
		return mv;
	}
	
	@RequestMapping(value = "/errorAuth.do", method = RequestMethod.GET)
	public ModelAndView errorAuth() {
		ModelAndView mv = getModelAndView("error/errorAuth");
		mv.addObject("ignoreLastPage", true);
		return mv;
	}
	
	
}
