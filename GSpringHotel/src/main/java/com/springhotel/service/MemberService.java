package com.springhotel.service;

import java.util.List;

import com.springhotel.dto.MemberDTO;
import com.springhotel.dto.PagingDTO;

public abstract class MemberService {
	
	public abstract void insertMember(MemberDTO member) throws Exception;
	
	public abstract void updateMember(MemberDTO member) throws Exception;
	
	public abstract void deleteMember(int memberId) throws Exception;
	
	public abstract int countMember(PagingDTO paging) throws Exception;
	
	public abstract List<MemberDTO> listMember(PagingDTO paging) throws Exception;
	
	public abstract MemberDTO selectByAccount(String account) throws Exception;
	
	public abstract MemberDTO selectById(int id) throws Exception;
}
