package com.springhotel.service.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.springhotel.dao.RoomDAO;
import com.springhotel.dto.PagingDTO;
import com.springhotel.dto.RoomDTO;
import com.springhotel.dto.RoomTypeDTO;
import com.springhotel.service.RoomService;

@Service
public class RoomServiceImpl extends RoomService {
	private final Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Inject
	private RoomDAO roomDAO;
	
	public Map<String, Object> listRoom(PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		paging.setRowCount(roomDAO.countRoom(paging));
		List<RoomDTO> list = roomDAO.listRoom(paging);
		paging.calculate();
		
		map.put("list", list);
		map.put("paging", paging);
		
		return map;
	}
	

	public RoomDTO readRoom(int roomId) throws Exception {
		
		return roomDAO.readRoom(roomId);
	}
	
	public void deleteRoom(Integer roomId) throws Exception {
		
		roomDAO.deleteRoom(roomId);
	}
	
	public Map<String, Object> listRoomType(PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		paging.setRowCount(roomDAO.countRoomType(paging));
		List<RoomTypeDTO> list = roomDAO.listRoomType(paging);
		paging.calculate();
		
		map.put("list", list);
		map.put("paging", paging);
		
		return map;
	}
	
	
}
