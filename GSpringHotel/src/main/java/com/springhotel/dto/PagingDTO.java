package com.springhotel.dto;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PagingDTO {
	private static final int ROW_MAX = 10; /* row max per page */
	private static final int PAGE_MAX = 10; /* page max per pagination */
	
	int rowStart;
	int rowEnd;
	int rowCount;
	int rowMax;
	int pageNum; 
	int pageStart;
	int pageEnd;
	int pageCount;
	int pageMax;
	
	public String searchKey;
	public String searchVal;
	
	public PagingDTO() {
		rowMax = ROW_MAX;
		pageMax = PAGE_MAX;
		searchKey = "";
		searchVal = "";
		
		pageNum = 1;
		rowStart = (pageNum - 1) * rowMax + 1;
		rowEnd = (pageNum * rowMax);
	}
	
	public PagingDTO(int rowMax, int pageMax) {
		this();
		this.rowMax = rowMax;
		this.pageMax = pageMax;
	}
	
	public int getRowStart() {
		return rowStart;
	}
	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}
	public int getRowEnd() {
		return rowEnd;
	}
	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		
		rowStart = (pageNum - 1) * rowMax + 1;
		rowEnd = (pageNum * rowMax);
		
	}
	public int getPageStart() {
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
		
	}
	public int getPageEnd() {
		return pageEnd;
	}
	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
		if (searchKey == null)
			this.searchKey = "";
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
		if (searchVal == null)
			this.searchVal = "";
	}
	
	public int getRowMax() {
		return rowMax;
	}

	public void setRowMax(int rowMax) {
		this.rowMax = rowMax;
	}

	public int getPageMax() {
		return pageMax;
	}

	public void setPageMax(int pageMax) {
		this.pageMax = pageMax;
	}
	
	public void open(HttpServletRequest req) {
		String page;

		page = req.getParameter("pageNum");
		if (page == null || page == "") 
			page = "1";

		searchKey = req.getParameter("searchKey");
		searchVal = req.getParameter("searchVal");
		if (searchKey == null || searchKey == "" || searchVal == null || searchVal == null) {
			searchKey = ""; 
			searchVal = "";
		}
		System.out.printf("pageNum %s, searchKey %s, searchVal %s\n", page, searchKey, searchVal);

		pageNum = Integer.parseInt(page);
		rowStart = (pageNum - 1) * rowMax + 1;
		rowEnd = (pageNum * rowMax);
	}
	
	public void close(HttpServletRequest req) {
		if (rowCount % rowMax == 0)
			pageCount = rowCount / rowMax; 
		else
			pageCount = (rowCount / rowMax) + 1;
		
		pageStart = pageNum - ((pageNum - 1) % pageMax);
		pageEnd = pageStart + pageMax - 1;
		if (pageEnd > pageCount)
			pageEnd = pageCount;
	
	}
	
	public void calculate() {
		if (rowCount % rowMax == 0)
			pageCount = rowCount / rowMax; 
		else
			pageCount = (rowCount / rowMax) + 1;
		
		pageStart = pageNum - ((pageNum - 1) % pageMax);
		pageEnd = pageStart + pageMax - 1;
		if (pageEnd > pageCount)
			pageEnd = pageCount;
	
	}

	
	
	public void setAttribute(HttpServletRequest req) {
		/*
		 * DO NOT PUT PAGING ITSELF
		 * TOO LONG CODE AT JSP 
		 */
		req.setAttribute("rowCount", rowCount);
		req.setAttribute("rowStart", rowStart);
		req.setAttribute("rowEnd", rowEnd);
		req.setAttribute("rowMax", rowMax);
		
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("pageStart", pageStart);
		req.setAttribute("pageEnd", pageEnd);
		req.setAttribute("pageMax", pageMax);
		req.setAttribute("searchKey", searchKey);
		req.setAttribute("searchVal", searchVal);
		
		System.out.printf("rowCount %d, rowStart %d, rowEnd %d, rowMax %d\n", rowCount, rowStart, rowEnd, rowMax);
		System.out.printf("pageNum %d, pageCount %d, pageStart %d, pageEnd %d\n", pageNum, pageCount, pageStart, pageEnd);

		
	}
	
	public String makeQuery(int page) {
		UriComponents uriComponents =
	            UriComponentsBuilder.newInstance()
	            .queryParam("pageNum", page)
	            .queryParam("searchKey", searchKey)
	            .queryParam("searchVal", searchVal)
	            .build();	            
		
		return uriComponents.toUriString();
	}
	
	public static String makeQuery(PagingDTO paging, int page) {
		return paging.makeQuery(page);
	}
	
	@Override
	public String toString() {
		return String.format("PagingDTO [rowStart %d, rowEnd %d, rowCount %d, rowMax %d, " + 
							"pageNum %d, pageStart %d, pageEnd %d, pageCount %d, pageMax %d, " + 
							"searchKey %s, searchVal %s]", 
							rowStart, rowEnd, rowCount, rowMax, pageNum, pageStart, pageEnd, 
							pageCount, pageMax, searchKey, searchVal);
		
	}


	
	
}
