package com.springhotel.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.springhotel.dto.BoardDTO;
import com.springhotel.dto.BoardFileDTO;
import com.springhotel.dto.BoardReplyDTO;
import com.springhotel.dto.PagingDTO;


public abstract class BoardService {
	
	public abstract int countBoard(String boardType, PagingDTO paging) throws Exception;
	
	public abstract List<BoardDTO> listBoard(String boardType, PagingDTO paging) throws Exception;

	public abstract BoardDTO readBoard(String boardType, int boardId) throws Exception;
	
	public abstract Map<String, Object> insertBoard(HttpServletRequest req) throws Exception;
	
	public abstract Map<String, Object> updateBoard(HttpServletRequest req) throws Exception;
	
	public abstract void deleteBoard(String boardType, Integer boardId) throws Exception;
	
	public abstract BoardFileDTO readFile(int boardId) throws Exception;

	public abstract Map<String, Object> listReply(int boardId, PagingDTO paging) throws Exception;
	
	public abstract void insertReply(BoardReplyDTO dto) throws Exception;
	
	public abstract BoardReplyDTO readReply(int id) throws Exception;
	
	public abstract void updateReply(BoardReplyDTO dto) throws Exception;
	
	public abstract void deleteReply(int id) throws Exception;

	
}

