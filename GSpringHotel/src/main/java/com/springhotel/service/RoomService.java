package com.springhotel.service;

import java.util.Map;

import com.springhotel.dto.PagingDTO;
import com.springhotel.dto.RoomDTO;

public abstract class RoomService {
	
	public abstract Map<String, Object> listRoom(PagingDTO paging) throws Exception;

	public abstract RoomDTO readRoom(int roomId) throws Exception;
	
	public abstract void deleteRoom(Integer roomId) throws Exception;
	
	public abstract Map<String, Object> listRoomType(PagingDTO paging) throws Exception;

}
