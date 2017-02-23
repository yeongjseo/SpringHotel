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
public class RoomController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	@Inject
	private RoomService service;
	
	@RequestMapping(value = "/roomTypeShow.do", method = RequestMethod.GET)
	public ModelAndView roomTypeShow(PagingDTO paging, HttpServletRequest req, 
									HttpServletResponse res) {
		ModelAndView mv = getModelAndView("room/roomTypeShow");
		try {
			Map<String, Object> map = service.listRoomType(paging);
			
			CommonUtil.printMap(map);
			
			mv.addObject("list", map.get("list"));
			mv.addObject("paging", map.get("paging"));
			return mv;
			
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}
	
}
