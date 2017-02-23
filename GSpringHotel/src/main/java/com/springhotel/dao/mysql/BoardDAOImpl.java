package com.springhotel.dao.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.springhotel.dao.BoardDAO;
import com.springhotel.dto.BoardDTO;
import com.springhotel.dto.BoardFileDTO;
import com.springhotel.dto.BoardReplyDTO;
import com.springhotel.dto.PagingDTO;

@Repository
public class BoardDAOImpl extends BoardDAO {
	private final Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	private static String namespace = "boardMapper";
	
	
	public int countBoard(String boardType, PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("boardType", BoardDTO.getBoardType(boardType));
		map.put("rowStart", paging.getRowStart());
		map.put("rowEnd", paging.getRowEnd());
		map.put("searchKey", paging.getSearchKey());
		map.put("searchVal", paging.getSearchVal());
		
		
		return (Integer)selectOne(namespace + ".countBoard", map);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<BoardDTO> listBoard(String boardType, PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("boardType", BoardDTO.getBoardType(boardType));
		map.put("rowStart", paging.getRowStart());
		map.put("rowEnd", paging.getRowEnd());
		map.put("searchKey", paging.getSearchKey());
		map.put("searchVal", paging.getSearchVal());
		
		return (List<BoardDTO>)selectList(namespace + ".listBoard", map);
	}
	
	public BoardDTO readBoard(String boardType, int boardId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("boardType", BoardDTO.getBoardType(boardType));
		map.put("boardId", boardId);
		
		BoardDTO dto = (BoardDTO)selectOne(namespace + ".readBoard", map);
		
		if (dto != null) {
			logger.debug(dto.toString());
			logger.debug(dto.getMemberDTO().toString());
		}
		
		return dto;
	}
	
	public void increaseReadCount(String boardType, int boardId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("boardType", BoardDTO.getBoardType(boardType));
		map.put("boardId", boardId);
		
		update(namespace + ".increaseReadCount", map);

	}
	
	public int insertBoard(BoardDTO dto) throws Exception {
		return (Integer)insert(namespace + ".insertBoard", dto);
	}
	
	public void insertBoardByNextVal(BoardDTO dto) throws Exception {
		insert(namespace + ".insertBoardByNextVal", dto);
	}
	
	public void updateBoard(BoardDTO dto) throws Exception {
		update(namespace + ".updateBoard", dto);
	}
	
	public void deleteBoard(String boardType, int boardId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("boardType", BoardDTO.getBoardType(boardType));
		map.put("boardId", boardId);
		
		delete(namespace + ".deleteBoard", map);
	}
	
	
	public int nextVal() throws Exception {
		return (Integer)selectOne(namespace + ".nextVal", 0);
	}
	
	public void insertFile(BoardFileDTO dto) throws Exception {
		insert(namespace + ".insertFile", dto);
	}
	
	public BoardFileDTO readFile(int fileId) throws Exception {
		return (BoardFileDTO)selectOne(namespace + ".readFile", fileId);
	}
	
	@SuppressWarnings("unchecked")
	public List<BoardFileDTO> listFile(int boardId) throws Exception {
		return (List<BoardFileDTO>)selectList(namespace + ".listFile", boardId);
	}

	public void deleteFile(int fileId) throws Exception {
		delete(namespace + ".deleteFile", fileId);
	}
	
	public int countReply(int boardId, PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("boardId", boardId);
		map.put("rowStart", paging.getRowStart());
		map.put("rowEnd", paging.getRowEnd());
		map.put("searchKey", paging.getSearchKey());
		map.put("searchVal", paging.getSearchVal());
		
		return (Integer)selectOne(namespace + ".countReply", map);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<BoardReplyDTO> listReply(int boardId, PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("boardId", boardId);
		map.put("rowStart", paging.getRowStart());
		map.put("rowEnd", paging.getRowEnd());
		map.put("searchKey", paging.getSearchKey());
		map.put("searchVal", paging.getSearchVal());
		
		return (List<BoardReplyDTO>)selectList(namespace + ".listReply", map);
	}
	
	public void insertReply(BoardReplyDTO dto) throws Exception {
		insert(namespace + ".insertReply", dto);
	}	
	
	public BoardReplyDTO readReply(int id) throws Exception {
		return (BoardReplyDTO) selectOne(namespace + ".readReply", id);
	}
	
	public void updateReply(BoardReplyDTO dto) throws Exception {
		update(namespace + ".updateReply", dto);
	}	
	
	public void deleteReply(int id) throws Exception {
		update(namespace + ".deleteReply", id);
	}


	
}
