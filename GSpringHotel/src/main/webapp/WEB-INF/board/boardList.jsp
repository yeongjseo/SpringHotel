<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- <%@ taglib prefix="m" uri="/WEB-INF/mytags.tld" %> --%>
<%@ taglib prefix="m" uri="/WEB-INF/mytags.tld" %>

<%@ page import="com.springhotel.dto.BoardDTO"%>

<div class="container">
	<div class="panel panel-primary">
  		<div class="panel-heading">
  			<div class="text-center">
  				<h4><c:out value="${m:getBoardTypeLongDesc(boardType)}"/></h4>
  			</div>
  		</div>
  		
  		<div class="panel-body">
		   	<div class="row">
		   		<div class="col-sm-6">
		   			<c:if test="${boardType == 'guest'}">
			   			<c:if test="${loginDTO != null}">
			   				<button type="button" class="btn btn-primary" id="write">작성하기</button>
			   			</c:if>
			   		</c:if>
			   		<c:if test="${boardType == 'notice' || boardType == 'event'}">
			   			<c:if test="${loginDTO != null && loginDTO.isAdmin}">
			   				<button type="button" class="btn btn-primary" id="write">작성하기</button>
			   			</c:if>
			   		</c:if>
		   		</div>
		   	
				<div class="col-sm-6" >
					<form class="form-inline pull-right">
				   		<select name="searchKey" class="form-control">
				  			<option value="" selected="selected">전체검색</option>
				 	   	  	<option value="title" <c:if test="${paging.searchKey eq 'title'}"> selected="selected" </c:if>>제목검색</option>
				 	   	  	<option value="account" <c:if test="${paging.searchKey eq 'account'}"> selected="selected" </c:if>>작성자검색</option>
				 	   	  	<option value="content" <c:if test="${paging.searchKey eq 'content'}"> selected="selected" </c:if>>내용검색</option>
				   		</select>
				   	
				   	
			   			<input type="text"  class="form-control" id="searchVal" name="searchVal" value="${paging.searchVal}">
	
			   			<input type="button" class="form-control btn btn-warning" id="search" value="검색">
	
			 	  	</form>
				</div>
			</div>
	
		
		
			<table class="table table-striped table-hover table-responsive">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>내용</th>
					<th>작성자</th>
					<th>등록일</th>
					<th>조회수</th>
				</tr>
			
				<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.rn}</td>
						<td>
							<a href='boardDetail.do?boardType=${boardType}&boardId=${dto.id}&pageNum=${paging.pageNum}&searchKey=${paging.searchKey}&searchVal=${paging.searchVal}'>
								${fn:substring(dto.title, 0, 20)}
							</a>
						</td>
						<td>${fn:substring(dto.content, 0, 40)}</td>
						<td>${dto.account}</td>
						
						<%-- <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.writeDate}"/></td> --%>
						<td>${dto.writeTime}</td>
						<td><span class="badge bg-red">${dto.readCount}</span></td>
					</tr>
			
				</c:forEach>
			</table>
		
		
			<div class="panel-footer">
		
				<div class="text-center">
					<ul class="pagination">
						<c:if test="${paging.pageStart > paging.pageMax}">
							<%-- <li><a href="boardList.do?boardType=${boardType}&pageNum=${paging.pageStart - paging.pageMax}&searchKey=${searchKey}&searchVal=${paging.searchVal}">&laquo;</a></li> --%>
							<li><a href="boardList.do${m:makeQuery(paging, paging.pageStart - paging.pageMax)}&boardType=${boardType}">&laquo;</a></li>
							
							
						</c:if>
				
						<c:forEach begin="${paging.pageStart}" end="${paging.pageEnd}" var="i">
							<li
								<c:out value="${paging.pageNum == i ? 'class=active' : ''}"/>>
								<%-- <a href="boardList.do?boardType=${boardType}&pageNum=${i}&searchKey=${paging.searchKey}&searchVal=${paging.searchVal}" class="page" >${i}</a> --%>
								<a href="boardList.do${m:makeQuery(paging, i)}&boardType=${boardType}">${i}</a>
							</li>
						</c:forEach>
				
						<c:if test="${paging.pageEnd < paging.pageCount}">
							<%-- <li><a href="boardList.do?boardType=${boardType}&pageNum=${paging.pageStart + paging.pageMax}&searchKey=${paging.searchKey}&searchVal=${paging.searchVal}" class="page" >&raquo;</a></li> --%>
							<li><a href="boardList.do${m:makeQuery(paging, paging.pageStart + paging.pageMax)}&boardType=${boardType}">&raquo;</a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
</div>

<script>

$('#search').on("click", function(ev) {
	self.location = "boardList.do" +
					"?pageNum=1" +
					"&boardType=" + "${boardType}" + 
					"&searchKey=" + $("select option:selected").val() + 
					"&searchVal=" + $('#searchVal').val();
});

$('#write').on("click", function(event) {
	self.location = "boardInsert.do" + 
					"?boardType=" + "${boardType}" +
					"&searchKey=" + "${pagingsearchKey}" + 
					"&searchVal=" + "${pagingsearchVal}";
});

</script>

