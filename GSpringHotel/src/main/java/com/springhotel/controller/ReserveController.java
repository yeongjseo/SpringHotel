package com.springhotel.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springhotel.common.CommonUtil;
import com.springhotel.dto.MemberDTO;
import com.springhotel.dto.PagingDTO;
import com.springhotel.dto.ReserveDTO;
import com.springhotel.dto.RoomTypeDTO;
import com.springhotel.dto.SearchDTO;
import com.springhotel.service.ReserveService;
import com.springhotel.service.RoomService;

@Controller
public class ReserveController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	@Inject
	private ReserveService service;
	
	@Inject
	private RoomService roomService;
	
	@RequestMapping(value = "/reserveSearch.do", method = RequestMethod.GET)
	public ModelAndView reserveSearch(SearchDTO search, HttpServletRequest req, 
									HttpServletResponse res) {
		ModelAndView mv = getModelAndView("reserve/reserveSearch");
		try {
			if (search.getDateStart() == null || search.getDateEnd() == null) {
				PagingDTO paging = new PagingDTO();
				Map<String, Object> map = roomService.listRoomType(paging);
				mv.addObject("roomTypes", map.get("list"));
				return mv;
			}
			
			Map<String, Object> map = service.listVacantRoomType(search);
			
			mv.addObject("list", map.get("list"));
			mv.addObject("search", map.get("search"));

			return mv;
			
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}
	
	
	@RequestMapping(value="/reserveInsert.do", method = RequestMethod.POST, 
					produces="application/x-json;charset=UTF-8")
	@ResponseBody
	public String reserveInsert(String jsonData, HttpServletRequest req, HttpServletResponse res) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		map.put("result", "fail");

		try {
			HttpSession session = req.getSession(false);
			MemberDTO member = (MemberDTO) session.getAttribute("loginDTO");
			if (member == null) {
				map.put("reason", "로그인하고 예약하세요.");
				return mapper.writeValueAsString(map);
			}

			SearchDTO search = new ObjectMapper().readValue(jsonData, SearchDTO.class);
			logger.debug(search.toString());

			ReserveDTO reserve = service.selectAnyVacantRoom(search, member);

			if (reserve == null) {
				map.put("reason", "해당 룸이 모두 예약되었습니다.");
				return mapper.writeValueAsString(map);
			}
			
			map.put("result", "success");
			map.put("reserveId", reserve.getId());
			RoomTypeDTO roomType = new RoomTypeDTO();
			roomType.setType(search.getRoomType());
			map.put("roomTypeDesc", roomType.getTypeDesc());
			
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			logger.error(e.toString());
			map.put("reason", "예약을 실패했습니다.");
			return mapper.writeValueAsString(map);
		}
	}

	@RequestMapping(value="/reserveDelete.do", method = RequestMethod.POST, 
			produces="application/x-json;charset=UTF-8")
	@ResponseBody
	public String reserveDelete(String jsonData, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		map.put("result", "fail");
		
		try {
			HttpSession session = req.getSession(false);
			MemberDTO member = (MemberDTO) session.getAttribute("loginDTO");
			if (member == null) {
				map.put("reason", "로그인하고 예약하세요.");
				return mapper.writeValueAsString(map);
			}
		
			SearchDTO search = new ObjectMapper().readValue(jsonData, SearchDTO.class);
			logger.debug("reserveId: " + search.getReserveId());
		
			service.deleteReserve(search.getReserveId());
		
			map.put("result", "success");
		
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			logger.error(e.toString());
			map.put("reason", "예약을 실패했습니다.");
			return mapper.writeValueAsString(map);
		}
	}

	
	
	
}
