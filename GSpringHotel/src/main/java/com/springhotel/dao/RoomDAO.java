package com.springhotel.dao;

import java.util.List;

import com.springhotel.dto.PagingDTO;
import com.springhotel.dto.RoomDTO;
import com.springhotel.dto.RoomTypeDTO;


public abstract class RoomDAO extends BaseDAO {
	
	public abstract int countRoom(PagingDTO paging) throws Exception;	
	
	public abstract List<RoomDTO> listRoom(PagingDTO paging) throws Exception;
	
	public abstract RoomDTO readRoom(int RoomId) throws Exception;
	
	public abstract void insertRoom(RoomDTO dto) throws Exception;
	
	public abstract void updateRoom(RoomDTO dto) throws Exception;
	
	public abstract void deleteRoom(int RoomId) throws Exception;	
	
	public abstract int countRoomType(PagingDTO paging) throws Exception;
	
	public abstract List<RoomTypeDTO> listRoomType(PagingDTO paging) throws Exception;	
	
}
