package com.springhotel.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springhotel.common.Env;
import com.springhotel.common.MediaUtil;
import com.springhotel.dto.BoardDTO;
import com.springhotel.dto.BoardFileDTO;
import com.springhotel.dto.BoardReplyDTO;
import com.springhotel.dto.MemberDTO;
import com.springhotel.dto.PagingDTO;
import com.springhotel.service.BoardService;

@Controller
public class BoardController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	@Inject
	private BoardService service;
	
	@RequestMapping(value = "/boardList.do", method = RequestMethod.GET)
	public ModelAndView boardList(String boardType, PagingDTO paging, HttpServletRequest req, 
									HttpServletResponse res) {
		ModelAndView mv = getModelAndView("board/boardList");
		
		try {
			if (boardType == null) {
				return errorAccess(req, res);
			}
			
			paging.setRowCount(service.countBoard(boardType, paging));
			
			
			List<BoardDTO> list = service.listBoard(boardType, paging);
			logger.debug("list.size=" + list.size());
			
			paging.calculate();
			
			mv.addObject("list", list);
			mv.addObject("boardType", boardType);
			mv.addObject("boardDTO", new BoardDTO());
			mv.addObject("paging", paging);
			return mv;
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}
	
	
	@RequestMapping(value = "/boardDetail.do", method = RequestMethod.GET)
	public ModelAndView boardDetail(String boardType, Integer boardId, PagingDTO paging, HttpServletRequest req, 
			HttpServletResponse res) {
		ModelAndView mv = getModelAndView("board/boardDetail");
		try {
			logger.debug(String.format("boardType %s, boardId %d", boardType, boardId));
			
			if (boardType == null) {
				return errorAccess(req, res);
			}
			
			BoardDTO board = service.readBoard(boardType, boardId);
			mv.addObject("board", board);
			mv.addObject("boardId", boardId);
			mv.addObject("boardType", boardType);
			mv.addObject("boardDTO", new BoardDTO());
			mv.addObject("paging", paging);
			mv.addObject("files", board.getBoardFileDTO());
			
			return mv;
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}
	
	@RequestMapping(value = "/boardDetail.do", method = RequestMethod.POST)
	public ModelAndView boardDetail(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			Map<String, Object> map = service.updateBoard(req);
			
			return new ModelAndView("redirect:/boardList.do?boardType=" + (String)map.get("boardType"));
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	}
	
	
	
	@RequestMapping(value = "/boardInsert.do", method = RequestMethod.GET)
	public ModelAndView boardInsert(String boardType, PagingDTO paging, HttpServletRequest req, 
			HttpServletResponse res) {
		try {
			
			ModelAndView mv = getModelAndView("board/boardInsert");
			logger.debug(String.format("boardType %s", boardType));
			
			if (boardType == null) {
				return errorAccess(req, res);
			}
			
			mv.addObject("boardType", boardType);
			mv.addObject("boardDTO", new BoardDTO());
			mv.addObject("paging", paging);
			
			return mv;
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
	
	}
	
	@RequestMapping(value = "/boardInsert.do", method = RequestMethod.POST)
	public ModelAndView boardInsert(HttpServletRequest req, HttpServletResponse res, RedirectAttributes rttr) {
		try {
			Map<String, Object> map = service.insertBoard(req);

			return new ModelAndView("redirect:/boardList.do?boardType=" + (String)map.get("boardType"));
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
		
	}
	
	@RequestMapping(value = "/boardDelete.do", method = RequestMethod.POST)
	public ModelAndView boardDelete(String boardType, Integer boardId, PagingDTO paging, 
					HttpServletRequest req, HttpServletResponse res, RedirectAttributes rttr) {
		try {
			ModelAndView mv = new ModelAndView("redirect:/boardList.do");
			
			logger.debug(String.format("boardType %s, boardId %d", boardType, boardId));
			logger.debug(paging.toString());
			
			service.deleteBoard(boardType, boardId);
				
			rttr.addAttribute("boardType", boardType);
			rttr.addAttribute("pageNum", paging.getPageNum());
			rttr.addAttribute("searchKey", paging.getSearchKey());
			rttr.addAttribute("searchVal", paging.getSearchVal());

			logger.debug("--------------------------");
			
			return mv;
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/boardFileDownload.do")
	public ResponseEntity<byte[]>  boardFileDownload(String type, Integer fileId) throws Exception{
		InputStream in = null; 
		ResponseEntity<byte[]> entity = null;
		BoardFileDTO dto;
		String filename;
		String savedFilename;
		logger.debug(String.format("type %s, fileId %d", type, fileId));

		try {
			HttpHeaders headers = new HttpHeaders();
			String uploadPath = Env.get("uploadPath");
			uploadPath += (File.separator + type);
			
			dto = service.readFile(fileId);
			filename = dto.getFilename();
			savedFilename = dto.getSavedFilename();
			
			in = new FileInputStream(uploadPath + File.separator + savedFilename);

			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add("Content-Disposition", "attachment; filename=\""+ 
								URLEncoder.encode(filename, "UTF-8")+"\";");
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch(Exception e){
			logger.error(e.toString());
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally{
			in.close();
		}
		return entity;    
	}
	
	
	@ResponseBody
	@RequestMapping("/boardFileDisplay.do")
	public ResponseEntity<byte[]>  boardFileDisplay(String type, Integer fileId) throws Exception{
		InputStream in = null; 
		ResponseEntity<byte[]> entity = null;
		BoardFileDTO dto;
		String filename;
		String savedFilename;
		logger.debug(String.format("type %s, fileId %d", type, fileId));

		try {
			HttpHeaders headers = new HttpHeaders();
			String uploadPath = Env.get("uploadPath");
			uploadPath += (File.separator + type);
			
			dto = service.readFile(fileId);
			filename = dto.getFilename();
			savedFilename = dto.getSavedFilename();
			
			String ext = filename.substring(filename.lastIndexOf(".")+1);
			MediaType mType = MediaUtil.getMediaType(ext);
			
			in = new FileInputStream(uploadPath + File.separator + savedFilename);

			headers.setContentType(mType);
			headers.add("Content-Disposition", "attachment; filename=\""+ 
								URLEncoder.encode(filename, "UTF-8")+"\";");
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch(Exception e){
			logger.error(e.toString());
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally{
			in.close();
		}
		return entity;    
	}
	
	
	
	
	@RequestMapping(value = "/boardReplyList.do", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> boardReplyList(Integer boardId, HttpServletRequest req, 
									HttpServletResponse res) {
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			PagingDTO paging = new PagingDTO();
			Map<String, Object> rmap = service.listReply(boardId, paging);
			
			map.put("replyCount", rmap.get("replyCount"));
			map.put("list", rmap.get("list"));
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error(e.toString());
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value = "/boardReplyInsert.do", method = RequestMethod.POST, produces="application/x-json;charset=UTF-8")
	@ResponseBody
	public String boardReplyInsert(@RequestBody BoardReplyDTO dto, 
							HttpServletRequest req, HttpServletResponse res) throws Exception {
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		HttpSession session;
		MemberDTO loginDTO;
		
		map.put("result", "fail");
		
		logger.debug(dto.toString());
		
		try {
			session = req.getSession(false);
			loginDTO = (MemberDTO)session.getAttribute("loginDTO");
			if (loginDTO == null) {
				map.put("reason", "로그인하고 예약하세요.");
				return mapper.writeValueAsString(map);
			}
			
			
			dto.setUserId(loginDTO.getId());
			service.insertReply(dto);

			map.put("result", "success");
			
			// {"result":"success"}
			// entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			
			// {result: "success"}
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			logger.error(e.toString());
			return mapper.writeValueAsString(map);
		}
	}
	
	
	@RequestMapping(value = "/boardReplyUpdate.do", method = RequestMethod.POST, produces="application/x-json;charset=UTF-8")
	@ResponseBody
	public String boardReplyUpdate(@RequestBody BoardReplyDTO dto, 
							HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		
		map.put("result", "fail");
		
		logger.debug(dto.toString());
		
		try {
			HttpSession session = req.getSession(false);
			MemberDTO loginDTO = (MemberDTO) session.getAttribute("loginDTO");
			if (loginDTO == null) {
				map.put("reason", "로그인하고 예약하세요.");
				return mapper.writeValueAsString(map);
			}
			
			service.updateReply(dto);
			
			map.put("result", "success");
			
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			logger.error(e.toString());
			return mapper.writeValueAsString(map);
		}
	}
	
	@RequestMapping(value = "/boardReplyDelete.do", method = RequestMethod.POST, produces="application/x-json;charset=UTF-8")
	@ResponseBody
	public String boardReplyDelete(@RequestBody BoardReplyDTO dto, 
							HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		
		map.put("result", "fail");
		
		try {
			HttpSession session = req.getSession(false);
			MemberDTO loginDTO = (MemberDTO) session.getAttribute("loginDTO");
			if (loginDTO == null) {
				map.put("reason", "로그인하고 예약하세요.");
				return mapper.writeValueAsString(map);
			}
			
			
			service.deleteReply(dto.getId());
			
			map.put("result", "success");

			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			logger.error(e.toString());
			return mapper.writeValueAsString(map);
		}
	}
	
	
	
	
}
