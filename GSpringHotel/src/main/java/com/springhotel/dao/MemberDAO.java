package com.springhotel.dao;

import java.util.List;

import com.springhotel.dto.MemberDTO;
import com.springhotel.dto.PagingDTO;


public abstract class MemberDAO extends BaseDAO {
	
	public abstract void insertMember(MemberDTO dto) throws Exception;
	
	public abstract void updateMember(MemberDTO dto) throws Exception;
	
	public abstract void deleteMember(int memberId) throws Exception;
	
	public abstract int countMember(PagingDTO paging) throws Exception;
	
	public abstract List<MemberDTO> listMember(PagingDTO paging) throws Exception;
	
	public abstract MemberDTO selectByAccount(String account) throws Exception;
	
	public abstract MemberDTO selectById(int id) throws Exception;
}
