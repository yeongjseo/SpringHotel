package com.springhotel.service.oracle;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.springhotel.dao.MemberDAO;
import com.springhotel.dto.MemberDTO;
import com.springhotel.dto.PagingDTO;
import com.springhotel.service.MemberService;

@Service
public class MemberServiceImpl extends MemberService {
	private final Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Inject
	private MemberDAO memberDAO;
	
	public void insertMember(MemberDTO member) throws Exception {
		memberDAO.insertMember(member);
	}
	
	public void updateMember(MemberDTO member) throws Exception {
		memberDAO.updateMember(member);
	}
	
	public void deleteMember(int memberId) throws Exception {
		memberDAO.deleteMember(memberId);
	}
	
	public int countMember(PagingDTO paging) throws Exception {
		return memberDAO.countMember(paging);
	}
	
	public List<MemberDTO> listMember(PagingDTO paging) throws Exception {
		return memberDAO.listMember(paging);
	}
	
	
	public MemberDTO selectByAccount(String account) throws Exception {
		return memberDAO.selectByAccount(account);
	}
	
	public MemberDTO selectById(int id) throws Exception {
		return memberDAO.selectById(id);
	}
}
