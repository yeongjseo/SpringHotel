package com.springhotel.dao.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.springhotel.dao.ReserveDAO;
import com.springhotel.dto.PagingDTO;
import com.springhotel.dto.ReserveDTO;
import com.springhotel.dto.RoomDTO;
import com.springhotel.dto.RoomTypeDTO;
import com.springhotel.dto.SearchDTO;

@Repository
public class ReserveDAOImpl extends ReserveDAO {
	private final Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	private static String namespace = "reserveMapper";
	
	public int countReserve(PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowStart", paging.getRowStart());
		map.put("rowEnd", paging.getRowEnd());
		map.put("searchKey", paging.getSearchKey());
		map.put("searchVal", paging.getSearchVal());
		
		return (Integer)selectOne(namespace + ".countReserve", map);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ReserveDTO> listReserve(PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowStart", paging.getRowStart());
		map.put("rowEnd", paging.getRowEnd());
		map.put("searchKey", paging.getSearchKey());
		map.put("searchVal", paging.getSearchVal());
		
		return (List<ReserveDTO>)selectList(namespace + ".listReserve", map);
		
	}
	
	public ReserveDTO selectReserveById(int reserveId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("reserveId", reserveId);
		
		return (ReserveDTO)selectOne(namespace + ".selectReserveById", map);
	}
	
	public void insertReserve(ReserveDTO dto) throws Exception {
		insert(namespace + ".insertReserve", dto);
	}

	public int nextVal() throws Exception {
		return (Integer)selectOne(namespace + ".nextVal", 0);
	}
	
	public void insertReserveByNextVal(ReserveDTO dto) {
		selectOne(namespace + ".insertReserveByNextVal", dto);
	}
	
	public void updateReserve(ReserveDTO dto) throws Exception {
		update(namespace + ".updateReserve", dto);
	}
	
	public void deleteReserve(int reserveId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("reserveId", reserveId);
		
		delete(namespace + ".deleteReserve", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReserveDTO> listReserveByMemberId(int memberId) {
		return (List<ReserveDTO>)selectList(namespace + ".listReserveByMemberId", memberId);
	}

	
	@SuppressWarnings("unchecked")
	public List<RoomTypeDTO> listVacantRoomType(SearchDTO search) {
		return (List<RoomTypeDTO>)selectList(namespace + ".listVacantRoomType", search);
	}
	
	public RoomDTO selectAnyVacantRoom(SearchDTO search) {
		return (RoomDTO)selectOne(namespace + ".selectAnyVacantRoom", search);
	}
	
	@SuppressWarnings("unchecked")
	public List<RoomDTO> listRoomByReserve(SearchDTO search) {
		return (List<RoomDTO>)selectList(namespace + ".listRoomByReserve", search);
	}
	
		
	
}
