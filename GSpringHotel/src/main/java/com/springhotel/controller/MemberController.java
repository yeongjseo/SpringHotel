package com.springhotel.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springhotel.common.MessageDO;
import com.springhotel.dto.MemberDTO;
import com.springhotel.dto.ReserveDTO;
import com.springhotel.service.MemberService;
import com.springhotel.service.ReserveService;

@Controller
public class MemberController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	@Inject
	private MemberService memberService;

	@Inject
	private ReserveService reserveService;

	
	@RequestMapping(value = "/memberJoin.do", method = RequestMethod.GET)
	public ModelAndView memberJoin() {
		ModelAndView mv = getModelAndView("member/memberJoin");

		logger.debug("");
		
		return mv;
	}

	@RequestMapping(value = "/memberJoinCheck.do", method = RequestMethod.POST, 
			produces="application/x-json;charset=UTF-8")
	@ResponseBody
	public String memberJoinCheck(MemberDTO member, HttpServletRequest req, 
			HttpServletResponse res) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		map.put("result", "fail");

		try {
			MemberDTO user = memberService.selectByAccount(member.getAccount());

			if (user != null) {
				map.put("reason", "중복된 아이디입니다.");
			} else {
				map.put("result", "success");
			}

			logger.debug(String.format("result %s, reason %s", map.get("result"), map.get("reason")));

			return mapper.writeValueAsString(map);	
		} catch (Exception e) {
			logger.error(e.toString());
			return mapper.writeValueAsString(map);
		}

	}
	
	
	@RequestMapping(value = "/memberJoin.do", method = RequestMethod.POST)
	public ModelAndView memberJoin(MemberDTO dto, HttpServletRequest req, 
									HttpServletResponse res, RedirectAttributes rttr) {
		try {
			ModelAndView mv = getModelAndView("member/memberJoin");
			
			MemberDTO user = memberService.selectByAccount(dto.getAccount());
			
			if (user != null) {
				logger.debug("--user--");
				MessageDO messageDO = new MessageDO();
				messageDO.setResult("error");
				messageDO.setReason("중복된 아이디입니다.");
				mv.addObject("messageDO", messageDO);
				logger.debug("-----1--");
				// dto.makeBirthYearMonthDate();
				mv.addObject("dto", dto);
				
				return mv;
			}
			logger.debug("-----2--");
			
			logger.debug(dto.toString());
			
			dto.makeBirthday();
			memberService.insertMember(dto);
			
			return new ModelAndView("redirect:index.do");	
		} catch (Exception e) {
			return errorDebug(req, res, e);
		}
	}

	
	
	
	@RequestMapping(value = "/memberLogin.do", method = RequestMethod.GET)
	public ModelAndView memberLogin() {
		ModelAndView mv = getModelAndView("member/memberLogin");

		logger.debug("");
		
		return mv;
	}
	
	@RequestMapping(value = "/memberLogin.do", method = RequestMethod.POST, produces="application/x-json;charset=UTF-8")
	@ResponseBody
	public String memberLogin(MemberDTO dto, HttpServletRequest req, 
										HttpServletResponse res, RedirectAttributes rttr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		HttpSession session;
		map.put("result", "fail");
	
		try {
			MemberDTO memberDTO = memberService.selectByAccount(dto.getAccount());
			
			if (memberDTO == null) {
				map.put("reason", "존재하지 않는 아이디 입니다.");
			} else if (! memberDTO.getPassword().equals(dto.getPassword())) {
				map.put("reason", "비밀번호가 틀립니다.");
			} else {
				map.put("account", dto.getAccount());
				map.put("result", "success");
				
				session = req.getSession();
				session.setAttribute("loginDTO", memberDTO);
				
				/*
				 * to display welcome message after redirect
				 */
				MessageDO messageDO = new MessageDO();
				messageDO.setRedirect(true);
				messageDO.setResult("login");
				messageDO.setReason(dto.getAccount() + "님, 로그인되었습니다.");
				rttr.addFlashAttribute("messageDO", messageDO);
				
				
			}
			// {"result":"success","account":"admin"}
			return mapper.writeValueAsString(map);	
		} catch (Exception e) {
			logger.error(e.toString());
			return mapper.writeValueAsString(map);
		}
		
	}
	
	@RequestMapping(value = "/memberLogout.do", method = {RequestMethod.POST})
	public ModelAndView memberLogout(HttpServletRequest req, HttpServletResponse res, RedirectAttributes rttr) {
		
		try {
			
			HttpSession session = req.getSession(false);
			logger.debug("");
			
			MemberDTO loginDTO = (MemberDTO) session.getAttribute("loginDTO");
			
			if (loginDTO != null) {
				/*
				 * to display welcome message after redirect
				 */
				MessageDO messageDO = new MessageDO();
				messageDO.setRedirect(true);
				messageDO.setResult("logout");
				messageDO.setReason(loginDTO.getAccount() + "님, 로그아웃 되었습니다.");
				rttr.addFlashAttribute("messageDO", messageDO);
			}
			session.setAttribute("loginDTO", null);
			
			String lastPage = (String)session.getAttribute("lastPage");
			if (lastPage == null)
				lastPage = "index.do";
			
			return new ModelAndView("redirect:" + lastPage);
		
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}
	
	@RequestMapping(value = "/memberDetail.do", method = RequestMethod.GET)
	public ModelAndView memberDetail(HttpServletRequest req, HttpServletResponse res) {
		try {
			ModelAndView mv = getModelAndView("member/memberDetail");
			
			HttpSession session = req.getSession(false);
			MemberDTO login = (MemberDTO)session.getAttribute("loginDTO");
			
			MemberDTO member = memberService.selectById(login.getId());
			
			logger.debug(member.toString());
			member.makeBirthYearMonthDate();
			logger.debug(member.toString());
			
			mv.addObject("member", member);
			
			return mv;	
		} catch (Exception e) {
			return errorDebug(req, res, e);
		}
	}
	
	@RequestMapping(value = "/memberDetail.do", method = RequestMethod.POST)
	public ModelAndView memberDetail(MemberDTO member, HttpServletRequest req, HttpServletResponse res) {
		try {
			
			HttpSession session = req.getSession(false);
			MemberDTO login = (MemberDTO)session.getAttribute("loginDTO");
			
			member.setId(login.getId());
			member.makeBirthday();
			memberService.updateMember(member);
			
			session.setAttribute("loginDTO", member);
			
			return new ModelAndView("redirect:index.do");	
		} catch (Exception e) {
			return errorDebug(req, res, e);
		}
	}
	
	@RequestMapping(value = "/memberDelete.do", method = RequestMethod.POST)
	public ModelAndView memberDelete(HttpServletRequest req, HttpServletResponse res) {
		try {
			
			HttpSession session = req.getSession(false);
			MemberDTO login = (MemberDTO)session.getAttribute("loginDTO");
			
			memberService.deleteMember(login.getId());
			
			session.setAttribute("loginDTO", null);
			session.invalidate();
			
			return new ModelAndView("redirect:index.do");	
		} catch (Exception e) {
			return errorDebug(req, res, e);
		}
	}
	
	@RequestMapping(value = "/memberPasswordCheck.do", method = RequestMethod.POST, 
					produces="application/x-json;charset=UTF-8")
	@ResponseBody
	public String memberPasswordCheck(MemberDTO member, HttpServletRequest req, 
										HttpServletResponse res) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		map.put("result", "fail");
	
		try {
			HttpSession session = req.getSession(false);
			MemberDTO login = (MemberDTO)session.getAttribute("loginDTO");
			
			if (member == null) {
				map.put("reason", "존재하지 않는 아이디 입니다.");
			}
			else if (! login.getPassword().equals(member.getPassword())) {
				map.put("reason", "패스워드가 틀립니다.");
			} else {
				map.put("result", "success");
				map.put("reason", login.getAccount());
			}
			
			logger.debug(String.format("result %s, reason %s", map.get("result"), map.get("reason")));
			
			// {"result":"success","account":"admin"}
			return mapper.writeValueAsString(map);	
		} catch (Exception e) {
			logger.error(e.toString());
			return mapper.writeValueAsString(map);
		}
		
	}
	
	
	@RequestMapping(value = "/memberReserveDetail.do", method = {RequestMethod.GET,  RequestMethod.POST})
	public ModelAndView memberReserveList(HttpServletRequest req, 
			HttpServletResponse res) {
		try {
			ModelAndView mv = getModelAndView("member/memberReserveDetail");
			
			HttpSession session = req.getSession(false);
			MemberDTO member = (MemberDTO)session.getAttribute("loginDTO");
			
			List<ReserveDTO> reserves = reserveService.listReserveByMemberId(member.getId());
			
			req.setAttribute("reserveDTOList", reserves);
			
			
			
			return mv;	
		} catch (Exception e) {
			return errorDebug(req, res, e);
		}
	}
	
	
	
}
