package com.springhotel.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springhotel.common.CommonUtil;
import com.springhotel.dto.PagingDTO;
import com.springhotel.service.RoomService;

@Controller
public class MainController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	@Inject
	private RoomService roomService;
	
	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ModelAndView mv = getModelAndView("main/mainBody");
		
		PagingDTO paging = new PagingDTO();
		Map<String, Object> map = roomService.listRoomType(paging);
		
		CommonUtil.printMap(map);
		
		mv.addObject("roomTypes", map.get("list"));
		mv.addObject("paging", map.get("paging"));
		
		
		return mv;
	}
	
}
