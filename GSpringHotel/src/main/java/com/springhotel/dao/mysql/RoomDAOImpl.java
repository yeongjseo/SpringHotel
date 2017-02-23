package com.springhotel.dao.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.springhotel.dao.RoomDAO;
import com.springhotel.dto.PagingDTO;
import com.springhotel.dto.RoomDTO;
import com.springhotel.dto.RoomTypeDTO;

@Repository
public class RoomDAOImpl extends RoomDAO {
	private final Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	private static String namespace = "roomMapper";
	
	
	public int countRoom(PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowStart", paging.getRowStart());
		map.put("rowEnd", paging.getRowEnd());
		map.put("searchKey", paging.getSearchKey());
		map.put("searchVal", paging.getSearchVal());
		
		return (Integer)selectOne(namespace + ".countRoom", map);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<RoomDTO> listRoom(PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowStart", paging.getRowStart());
		map.put("rowEnd", paging.getRowEnd());
		map.put("searchKey", paging.getSearchKey());
		map.put("searchVal", paging.getSearchVal());
		
		return (List<RoomDTO>)selectList(namespace + ".listRoom", map);
		
	}
	
	public RoomDTO readRoom(int RoomId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("RoomId", RoomId);
		
		return (RoomDTO)selectOne(namespace + ".readRoom", map);
	}
	
	public void insertRoom(RoomDTO dto) throws Exception {
		insert(namespace + ".insertRoom", dto);
	}
	
	
	public void updateRoom(RoomDTO dto) throws Exception {
		update(namespace + ".updateRoom", dto);
	}
	
	public void deleteRoom(int RoomId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("RoomId", RoomId);
		
		delete(namespace + ".deleteRoom", map);
	}
	
	
	public int countRoomType(PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowStart", paging.getRowStart());
		map.put("rowEnd", paging.getRowEnd());
		map.put("searchKey", paging.getSearchKey());
		map.put("searchVal", paging.getSearchVal());
		
		return (Integer)selectOne(namespace + ".countRoomType", map);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<RoomTypeDTO> listRoomType(PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowStart", paging.getRowStart());
		map.put("rowEnd", paging.getRowEnd());
		map.put("searchKey", paging.getSearchKey());
		map.put("searchVal", paging.getSearchVal());
		
		return (List<RoomTypeDTO>)selectList(namespace + ".listRoomType", map);
		
	}
	
	
	
	
		
	
}
