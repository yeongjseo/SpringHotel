package com.springhotel.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springhotel.dto.BoardDTO;
import com.springhotel.dto.PagingDTO;
import com.springhotel.service.BoardService;

@Controller
public class CommonController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());		
	
	@Inject
	private BoardService boardService;
	
	
	
	@RequestMapping(value = "/popup.do", method = RequestMethod.GET)
	public ModelAndView index(String boardType, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ModelAndView mv = getModelAndView("common/popup");
		try {
			if (boardType == null) {
				return errorAccess(req, res);
			}
			PagingDTO paging = new PagingDTO();
			
			paging.setRowCount(boardService.countBoard(boardType, paging));
			
			List<BoardDTO> list = boardService.listBoard(boardType, paging);
			logger.debug("list.size=" + list.size());
			
			if (list.size() == 0)
				return null;
			
			BoardDTO board = (BoardDTO)list.get(0);
			board = boardService.readBoard(boardType, board.getId());
			
			mv.addObject("board", board);
			mv.addObject("boardType", boardType);
			mv.addObject("pagePopup", "true");
			
			mv.addObject("ignoreLastPage", true);
			
			return mv;
			
		} catch (Exception e) {
			logger.error(e.toString());
			return errorDebug(req, res, e);
		}
		
	}
	
}
