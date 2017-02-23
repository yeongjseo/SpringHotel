package com.springhotel.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.springhotel.dto.PagingDTO;
import com.springhotel.dto.ReserveDTO;
import com.springhotel.dto.RoomDTO;
import com.springhotel.dto.RoomTypeDTO;
import com.springhotel.dto.SearchDTO;

public abstract class ReserveDAO extends BaseDAO {
	
	public abstract int countReserve(PagingDTO paging) throws Exception;	
	
	public abstract List<ReserveDTO> listReserve(PagingDTO paging) throws Exception;
	
	public abstract ReserveDTO selectReserveById(int reserveId) throws Exception;
	
	public abstract void insertReserve(ReserveDTO dto) throws Exception;
	
	public abstract int nextVal() throws Exception;
	
	public abstract void insertReserveByNextVal(ReserveDTO dto);
	
	public abstract void updateReserve(ReserveDTO dto) throws Exception;
	
	public abstract void deleteReserve(int reserveId) throws Exception;	

	public abstract List<ReserveDTO> listReserveByMemberId(int memberId);
	

	public abstract List<RoomTypeDTO> listVacantRoomType(SearchDTO search);
	
	public abstract RoomDTO selectAnyVacantRoom(SearchDTO search);
	

	public abstract List<RoomDTO> listRoomByReserve(SearchDTO search);
	
		
	
}
