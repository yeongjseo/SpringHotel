package com.springhotel.dto;

import java.sql.Date;

import com.springhotel.common.CommonUtil;

public class SearchDTO {
	String dateStart;
	String dateEnd;
	int dateCount;
	int guestNum;

	String yearCur;
	String monthCur;
	String dayLast;
	
	int roomType;
	int reserveId;
	
	public String getDateStart() {
		return dateStart;
	}
	
	private void calcDateCount() {
		try {
			Date start = CommonUtil.parseStringToDate(dateStart);
			Date end = CommonUtil.parseStringToDate(dateEnd);
			
			long timeStart = start.getTime();
			long timeEnd = end.getTime();
			
			long dateCount = (timeEnd - timeStart) / (1000 * 60 * 60 * 24);
			if (dateCount > 0)
				setDateCount((int)dateCount);
			
		} catch (Exception e) {}
	}
	
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
		
		calcDateCount();

	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
		
		calcDateCount();
	}
	
	public int getDateCount() {
		return dateCount;
	}
	public void setDateCount(int dateCount) {
		if (dateCount == 0)
			return;
		this.dateCount = dateCount;
	}
	public int getGuestNum() {
		return guestNum;
	}
	public void setGuestNum(int guestNum) {
		this.guestNum = guestNum;
	}
	
	public int getRoomType() {
		return roomType;
	}
	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}
	
	@Override
	public String toString() {
		return String.format("SearchDTO [dateStart %s, dateEnd %s dateCount %d, yearCur %s, monthCur %s, dateLast %s, guestNum %d, roomType %d]", 
					dateStart, dateEnd, dateCount, yearCur, monthCur, dayLast, guestNum, roomType);
	}
	public int getReserveId() {
		return reserveId;
	}
	public void setReserveId(int reserveId) {
		this.reserveId = reserveId;
	}

	public String getYearCur() {
		return yearCur;
	}

	public void setYearCur(String yearCur) {
		this.yearCur = yearCur;
	}

	public String getMonthCur() {
		return monthCur;
	}

	public void setMonthCur(String monthCur) {
		if (monthCur.length() == 1)
			this.monthCur = "0" + monthCur;
		else
			this.monthCur = monthCur;
	}

	public String getDayLast() {
		return dayLast;
	}

	public void setDayLast(String dateLast) {
		this.dayLast = dateLast;
	}
	
	
}
