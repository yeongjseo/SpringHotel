package com.springhotel.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.springhotel.dto.MemberDTO;
import com.springhotel.dto.PagingDTO;
import com.springhotel.dto.ReserveDTO;
import com.springhotel.dto.SearchDTO;

public abstract class ReserveService {
	
	public abstract Map<String, Object> listReserve(PagingDTO paging) throws Exception;
	
	public abstract ReserveDTO selectReserveById(Integer reserveId) throws Exception;
	
	public abstract void deleteReserve(Integer reserveId) throws Exception;
	
	public abstract List<ReserveDTO> listReserveByMemberId(int memberId);

	public abstract Map<String, Object> listVacantRoomType(SearchDTO search);
	
	public abstract ReserveDTO selectAnyVacantRoom(SearchDTO search, MemberDTO member) throws Exception;
	
	public abstract Map<String, Object> listReserveByDate(SearchDTO search) throws ParseException;
	
}
