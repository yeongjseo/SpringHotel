package com.springhotel.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springhotel.common.Env;
import com.springhotel.dto.EmailDTO;
import com.springhotel.service.common.EmailService;

@Controller
public class CompanyController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());		

	@Inject
	private EmailService emailUtil;

	@Resource(name="env")
	private Env env;
	
	@RequestMapping(value = "/companyIntro.do", method = RequestMethod.GET)
	public ModelAndView companyIntro(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return getModelAndView("company/companyIntro");
	}
	
	@RequestMapping(value = "/companyMap.do", method = RequestMethod.GET)
	public ModelAndView companyMap(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return getModelAndView("company/companyMap");
	}
	
	@RequestMapping(value = "/companyContact.do", method = RequestMethod.GET)
	public ModelAndView companyContact(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return getModelAndView("company/companyContact");
	}
	
	@RequestMapping(value = "/companyContact.do", method = RequestMethod.POST, 
					produces="application/x-json;charset=UTF-8")
	@ResponseBody
	public String companyContact(EmailDTO email, HttpServletRequest req, 
										HttpServletResponse res, RedirectAttributes rttr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		HttpSession session;
		map.put("result", "fail");
	
		try {
			
			email.setToEmailAddress((String)env.get("emailAddress"));
			email.setToEmailPassword((String)env.get("emailPassword"));
			
			logger.debug(email.toString());
			
			emailUtil.send(email);
			map.put("result", "success");
			return mapper.writeValueAsString(map);	
		} catch (Exception e) {
			logger.error(e.toString());
			return mapper.writeValueAsString(map);
		}
	}

	
	
}

