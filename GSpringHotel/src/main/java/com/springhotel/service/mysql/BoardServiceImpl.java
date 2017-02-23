package com.springhotel.service.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.springhotel.common.Env;
import com.springhotel.common.FileUtil;
import com.springhotel.dao.BoardDAO;
import com.springhotel.dto.BoardDTO;
import com.springhotel.dto.BoardFileDTO;
import com.springhotel.dto.BoardReplyDTO;
import com.springhotel.dto.MemberDTO;
import com.springhotel.dto.PagingDTO;
import com.springhotel.service.BoardService;

@Service
public class BoardServiceImpl extends BoardService {
	private final Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Inject
	private BoardDAO boardDAO;
	
	
	public int countBoard(String boardType, PagingDTO paging) throws Exception {
		return boardDAO.countBoard(boardType, paging);
	}
	
	public List<BoardDTO> listBoard(String boardType, PagingDTO paging) throws Exception {
		return boardDAO.listBoard(boardType, paging);
	}
	

	public BoardDTO readBoard(String boardType, int boardId) throws Exception {
		
		boardDAO.increaseReadCount(boardType, boardId);
		
		return boardDAO.readBoard(boardType, boardId);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> insertBoard(HttpServletRequest req) throws Exception {
		FileUtil fileUtil = new FileUtil();
		BoardDTO dto = new BoardDTO();
		HttpSession session = req.getSession(false);
		MemberDTO loginDTO = (MemberDTO) session.getAttribute("loginDTO");
		String uploadPath = Env.get("uploadPath");
		BoardFileDTO fileDTO = new BoardFileDTO();
		
		Map<String, Object> map = fileUtil.parse(req, uploadPath, "boardType");
		List<Map<String, Object>> files = (List<Map<String, Object>>)map.get("files");
		dto.setType(BoardDTO.getBoardType((String)map.get("boardType")));
		dto.setTitle((String)map.get("title"));
		dto.setContent((String)map.get("content"));
		dto.setUserId(loginDTO.getId());
		
		logger.debug(dto.toString());

		int boardId = boardDAO.insertBoard(dto);
		
		logger.debug("boardId =" + dto.toString());
		logger.debug("boardId =" + boardId);
		
		boardId = dto.getId();
		
		for (Map<String, Object> file : files) {
			fileDTO.setBoardId(boardId);
			fileDTO.setFilename((String)file.get("filename"));
			fileDTO.setSavedFilename((String)file.get("savedFilename"));
			fileDTO.setFilesize(((Long)file.get("filesize")));

			boardDAO.insertFile(fileDTO);
		}
		
		
		return map;
		
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateBoard(HttpServletRequest req) throws Exception {
		FileUtil fileUtil = new FileUtil();
		HttpSession session = req.getSession(false);
		String uploadPath = Env.get("uploadPath");
		BoardFileDTO fileDTO = new BoardFileDTO();
		
		Map<String, Object> map = fileUtil.parse(req, uploadPath, "boardType");
		
		String boardType = (String)map.get("boardType");
		String boardId = (String)map.get("boardId");
		List<Map<String, Object>> files = (List<Map<String, Object>>)map.get("files");
		
		BoardDTO dto = boardDAO.readBoard(boardType, Integer.parseInt(boardId));
		if (dto == null) {
			logger.error("--------------------");
			throw new Exception("NO DTO");
		}

		dto.setTitle((String)map.get("title"));
		dto.setContent((String)map.get("content"));
		
		logger.debug(dto.toString());
		
		boardDAO.updateBoard(dto);
		
		//////////
		List<BoardFileDTO> ofiles = boardDAO.listFile(Integer.parseInt(boardId));
		
		List<Integer> delFileIds = new ArrayList<Integer>();
		
		for (BoardFileDTO file : ofiles) {
			String fileId = "fileId_" + file.getId();
			if (map.get(fileId) == null)
				delFileIds.add(file.getId());
		}
		
		for (Integer fileId : delFileIds) {
			boardDAO.deleteFile(fileId);
		}
		
		for (Map<String, Object> file : files) {
			fileDTO.setBoardId(dto.getId());
			fileDTO.setFilename((String)file.get("filename"));
			fileDTO.setSavedFilename((String)file.get("savedFilename"));
			fileDTO.setFilesize(((Long)file.get("filesize")));

			boardDAO.insertFile(fileDTO);
		}
		
		return map;
		
	}
	
	public void deleteBoard(String boardType, Integer boardId) throws Exception {
		
		boardDAO.deleteBoard(boardType, boardId);
	}
	
	
	public BoardFileDTO readFile(int boardId) throws Exception {
		
		return boardDAO.readFile(boardId);
	}

	
	public Map<String, Object> listReply(int boardId, PagingDTO paging) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		int replyCount = boardDAO.countReply(boardId, paging);
		paging.setRowCount(replyCount);
		
		List<BoardReplyDTO> list = boardDAO.listReply(boardId, paging);
		paging.calculate();
		
		map.put("replyCount", replyCount);
		map.put("list", list);
		
		return map;
		
	}
	
	public void insertReply(BoardReplyDTO dto) throws Exception {
		boardDAO.insertReply(dto);
	}
	
	public BoardReplyDTO readReply(int id) throws Exception {
		return boardDAO.readReply(id);
	}
	
	public void updateReply(BoardReplyDTO dto) throws Exception {
		BoardReplyDTO rdto = boardDAO.readReply(dto.getId());
		
		rdto.setContent(dto.getContent());

		boardDAO.updateReply(rdto);
	}
	
	public void deleteReply(int id) throws Exception {
		boardDAO.deleteReply(id);
	}
	
	
	
}

