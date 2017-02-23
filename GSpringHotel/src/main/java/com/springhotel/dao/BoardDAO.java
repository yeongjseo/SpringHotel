package com.springhotel.dao;

import java.util.List;

import com.springhotel.dto.BoardDTO;
import com.springhotel.dto.BoardFileDTO;
import com.springhotel.dto.BoardReplyDTO;
import com.springhotel.dto.PagingDTO;


public abstract class BoardDAO extends BaseDAO {
	
	public abstract int countBoard(String boardType, PagingDTO paging) throws Exception;
	
	public abstract List<BoardDTO> listBoard(String boardType, PagingDTO paging) throws Exception;
	
	public abstract BoardDTO readBoard(String boardType, int boardId) throws Exception;
	
	public abstract void increaseReadCount(String boardType, int boardId) throws Exception;
	
	public abstract int insertBoard(BoardDTO dto) throws Exception;
	
	public abstract void insertBoardByNextVal(BoardDTO dto) throws Exception;
	
	public abstract void updateBoard(BoardDTO dto) throws Exception;
	
	public abstract void deleteBoard(String boardType, int boardId) throws Exception;	
	
	public abstract int nextVal() throws Exception;
	
	public abstract void insertFile(BoardFileDTO dto) throws Exception;
	
	public abstract BoardFileDTO readFile(int fileId) throws Exception;
	
	public abstract List<BoardFileDTO> listFile(int boardId) throws Exception;

	public abstract void deleteFile(int fileId) throws Exception;
	
	public abstract int countReply(int boardId, PagingDTO paging) throws Exception;
	
	public abstract List<BoardReplyDTO> listReply(int boardId, PagingDTO paging) throws Exception;
	
	public abstract void insertReply(BoardReplyDTO dto) throws Exception;
	
	public abstract BoardReplyDTO readReply(int id) throws Exception;
	
	public abstract void updateReply(BoardReplyDTO dto) throws Exception;	
	
	public abstract void deleteReply(int id) throws Exception;
	
}
