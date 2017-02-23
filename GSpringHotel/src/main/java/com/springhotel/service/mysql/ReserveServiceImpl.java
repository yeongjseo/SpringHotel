package com.springhotel.service.mysql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.springhotel.common.CommonUtil;
import com.springhotel.dao.ReserveDAO;
import com.springhotel.dto.MemberDTO;
import com.springhotel.dto.PagingDTO;
import com.springhotel.dto.ReserveDTO;
import com.springhotel.dto.RoomDTO;
import com.springhotel.dto.RoomTypeDTO;
import com.springhotel.dto.SearchDTO;
import com.springhotel.service.ReserveService;

@Service
public class ReserveServiceImpl extends ReserveService {
	private final Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Inject
	private ReserveDAO reserveDAO;
	
	public Map<String, Object> listReserve(PagingDTO paging) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
 
		paging.setRowCount(reserveDAO.countReserve(paging));
		List<ReserveDTO> list = reserveDAO.listReserve(paging);
		paging.calculate();
		
		map.put("list", list);
		map.put("paging", paging);

		return map;
	}
	

	public ReserveDTO selectReserveById(Integer reserveId) throws Exception {
		
		return reserveDAO.selectReserveById(reserveId);
	}
	
	public void deleteReserve(Integer reserveId) throws Exception {
		
		reserveDAO.deleteReserve(reserveId);
	}
	
	public List<ReserveDTO> listReserveByMemberId(int memberId) {
		return reserveDAO.listReserveByMemberId(memberId);
	}
	

	public Map<String, Object> listVacantRoomType(SearchDTO search) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<RoomTypeDTO> list = reserveDAO.listVacantRoomType(search);
		
		map.put("list", list);
		map.put("search", search);
		return map;
		
	}
	
	public ReserveDTO selectAnyVacantRoom(SearchDTO search, MemberDTO member) throws Exception {
		// ReserveDTO reserve;
		RoomDTO room = reserveDAO.selectAnyVacantRoom(search);
		if (room == null)
			return null;
		
		ReserveDTO reserve = new ReserveDTO();
		reserve.setUserId(member.getId());
		reserve.setUserId(member.getId());
		reserve.setRoomId(room.getId());
		reserve.setDateStart(CommonUtil.parseStringToDate(search.getDateStart()));
		reserve.setDateEnd(CommonUtil.parseStringToDate(search.getDateEnd()));
		reserve.setPax(0);
		reserve.setBreakfast(0);
		
		reserveDAO.insertReserve(reserve);
		
		return reserve;
		
	}
	
	public Map<String, Object> listReserveByDate(SearchDTO search) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		String dateStart, dateEnd, dateLast;
		
		if (search.getYearCur() == null) {
			search.setYearCur(new SimpleDateFormat("yyyy").format(calendar.getTime()));
		}
		
		if (search.getMonthCur() == null) {
			search.setMonthCur(new SimpleDateFormat("MM").format(calendar.getTime()));
		}
		
		dateStart = search.getDateStart();
		dateEnd = search.getDateEnd();
		
		 
		if (dateStart == null || dateEnd == null) {
			dateStart = CommonUtil.getDateString(search.getYearCur(), search.getMonthCur(), "1");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			java.util.Date date = sdf.parse(dateStart);
			
			calendar.setTime(date);
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
			dateEnd =  new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
			dateLast = new SimpleDateFormat("dd").format(calendar.getTime());
			
			search.setDateStart(dateStart);
			search.setDateEnd(dateEnd);
			search.setDayLast(dateLast);
		}
		
		logger.debug(search.toString());
		
		
		List<RoomDTO> list = reserveDAO.listRoomByReserve(search);
		
		for (RoomDTO room : list) {
			logger.debug(room.toString());
		}
		
		map.put("list", list);
		map.put("search", search);
		return map;
		
	}
	
}
