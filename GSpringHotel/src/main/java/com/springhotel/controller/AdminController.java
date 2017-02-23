package com.springhotel.controller;

import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springhotel.dto.MemberDTO;
import com.springhotel.dto.PagingDTO;
import com.springhotel.dto.ReserveDTO;
import com.springhotel.dto.SearchDTO;
import com.springhotel.service.MemberService;
import com.springhotel.service.ReserveService;
import com.springhotel.service.RoomService;

@Controller
public class AdminController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	@Inject
	private RoomService roomService;
	
	@Inject
	private MemberService memberService;
	
	@Inject
	private ReserveService reserveService;
	
	
	
	@RequestMapping(value = "/adminMemberList.do", method = RequestMethod.GET)
	public ModelAndView adminMemberList(PagingDTO paging, HttpServletRequest req, 
									HttpServletResponse res) {
		ModelAndView mv = getModelAndView("admin/adminMemberList");
		try {
	
			paging.setRowCount(memberService.countMember(paging));
			
			List<MemberDTO> list = memberService.listMember(paging);
			
			paging.calculate();
			
			mv.addObject("list", list);
			mv.addObject("paging", paging);
			return mv;
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}
	
	@RequestMapping(value = "/adminMemberDelete.do", method = RequestMethod.POST)
	public ModelAndView adminMemberDelete(Integer id, PagingDTO paging, HttpServletRequest req, 
									HttpServletResponse res, RedirectAttributes rttr) {
		try {
			memberService.deleteMember(id);
			
			rttr.addAttribute("pageNum", paging.getPageNum());
			rttr.addAttribute("searchKey", paging.getSearchKey());
			rttr.addAttribute("searchVal", paging.getSearchVal());
			
			return new ModelAndView("redirect:/adminMemberList.do");
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}
	
	@RequestMapping(value = "/adminMemberDetail.do", method = RequestMethod.GET)
	public ModelAndView adminMemberDetail(Integer id, PagingDTO paging, 
									HttpServletRequest req, HttpServletResponse res) {
		try {
			ModelAndView mv = getModelAndView("admin/adminMemberDetail");
			
			MemberDTO member = memberService.selectById(id);
			
			logger.debug(paging.toString());
			
			member.makeBirthYearMonthDate();
			
			mv.addObject("member", member);
			mv.addObject("paging", paging);
			
			return mv;	
		} catch (Exception e) {
			return errorDebug(req, res, e);
		}
	}
	
	@RequestMapping(value = "/adminMemberDetail.do", method = RequestMethod.POST)
	public ModelAndView adminMemberDetail(MemberDTO member, PagingDTO paging, HttpServletRequest req, 
							HttpServletResponse res, RedirectAttributes rttr) {
		try {
			member.makeBirthday();
			memberService.updateMember(member);
			
			rttr.addAttribute("pageNum", paging.getPageNum());
			rttr.addAttribute("searchKey", paging.getSearchKey());
			rttr.addAttribute("searchVal", paging.getSearchVal());
			
			return new ModelAndView("redirect:/adminMemberList.do");	
		} catch (Exception e) {
			return errorDebug(req, res, e);
		}
	}
	
	@RequestMapping(value = "/adminRoomList.do", method = RequestMethod.GET)
	public ModelAndView adminRoomList(PagingDTO paging, HttpServletRequest req, 
									HttpServletResponse res) {
		ModelAndView mv = getModelAndView("admin/adminRoomList");
		try {
			Map<String, Object> map = roomService.listRoom(paging);
			
			mv.addObject("list", map.get("list"));
			mv.addObject("paging", map.get("paging"));
			return mv;
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}
	
	@RequestMapping(value = "/adminReserveList.do", method = RequestMethod.GET)
	public ModelAndView adminReserveList(PagingDTO paging, HttpServletRequest req, 
									HttpServletResponse res) {
		
		ModelAndView mv = getModelAndView("admin/adminReserveList");
		try {
			Map<String, Object> map = reserveService.listReserve(paging);
			
			mv.addObject("list", map.get("list"));
			mv.addObject("paging", map.get("paging"));
			return mv;
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}
	
	@RequestMapping(value = "/adminReserveDetail.do", method = RequestMethod.GET)
	public ModelAndView adminReserveDetail(Integer reserveId, PagingDTO paging, HttpServletRequest req, 
									HttpServletResponse res) {
		
		ModelAndView mv = getModelAndView("admin/adminReserveDetail");
		try {
			ReserveDTO reserve = reserveService.selectReserveById(reserveId);
			
			mv.addObject("reserveDTO", reserve);
			
			mv.addObject("ignoreLastPage", true);
			
			return mv;
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}
	
	
	@RequestMapping(value = "/adminReserveCalendar.do", method = RequestMethod.GET)
	public ModelAndView adminReserveCalendar(SearchDTO search, HttpServletRequest req, 
			HttpServletResponse res) {

		ModelAndView mv = getModelAndView("admin/adminReserveCalendar");
		try {
			Map<String, Object> map = reserveService.listReserveByDate(search);

			mv.addObject("list", map.get("list"));
			mv.addObject("search", map.get("search"));
			return mv;
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}

	
}
