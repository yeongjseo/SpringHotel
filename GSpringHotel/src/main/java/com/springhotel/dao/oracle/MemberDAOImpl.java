package com.springhotel.dao.oracle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.springhotel.dao.MemberDAO;
import com.springhotel.dto.MemberDTO;
import com.springhotel.dto.PagingDTO;

@Repository
public class MemberDAOImpl extends MemberDAO {
	private final Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	private static String namespace = "memberMapper";
	
	public void insertMember(MemberDTO dto) throws Exception {
		insert(namespace + ".insertMember", dto);
	}
	
	public void updateMember(MemberDTO dto) throws Exception {
		insert(namespace + ".updateMember", dto);
	}
	
	public void deleteMember(int memberId) throws Exception {
		insert(namespace + ".deleteMember", memberId);
	}
	
	public int countMember(PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("paging", paging);	
		
		return (Integer)selectOne(namespace + ".countMember", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<MemberDTO> listMember(PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("paging", paging);	
		
		return (List<MemberDTO>)selectList(namespace + ".listMember", map);
	}
	
	public MemberDTO selectByAccount(String account) throws Exception {
		return (MemberDTO)selectOne(namespace + ".selectByAccount", account);
	}
	
	public MemberDTO selectById(int id) throws Exception {
		return (MemberDTO)selectOne(namespace + ".selectById", id);
	}
	
}
