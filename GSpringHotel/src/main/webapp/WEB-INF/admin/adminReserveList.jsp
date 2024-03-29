<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="m" uri="/WEB-INF/mytags.tld" %>

<div class="container">

	<div class="panel panel-primary">
  		<div class="panel-heading">
  			<div class="text-center">
  				<h4>예약 리스트</h4>
  			</div>
  		</div>
  		
  		<div class="panel-body">
			<div class="row">
		   		<div class="col-sm-6">
		   			<c:if test="${loginDTO.isAdmin}">
		   				<button type="button" class="btn btn-primary" id="write">작성하기</button>
		   			</c:if>
		   		</div>
		   	
				<div class="col-sm-6" >
					<form class="form-inline pull-right">
				   		<select name="searchKey" class="form-control">
				  			<option value="" selected="selected">전체검색</option>
				 	   	  	<option value="account" <c:if test="${searchKey eq 'account'}"> selected="selected" </c:if>>예약자검색</option>
				 	   	  	<option value="roomNum" <c:if test="${searchKey eq 'roomNum'}"> selected="selected" </c:if>>룸번호검색</option>
				   		</select>
				   	
				   	
			   			<input type="text"  class="form-control" id="searchVal" name="searchVal" value="${searchVal}">
	
			   			<input type="button" class="form-control btn btn-warning" id="search" value="검색">
	
			 	  	</form>
				</div>
			</div>

	   	
			<table class="table table-striped table-hover table-responsive">
				<tr>
					<th>번호</th>
					<th>예약ID</th>
					<th>예약자</th>
					<th>룸타입</th>
					<th>룸번호</th>
					<th>시작일</th>
					<th>종료일</th>
					<th>예약일</th>
					<th>인원</th>
				</tr>
			
				<c:forEach items="${list}" var="dto">
					<tr>
						<td>${dto.rn}</td>
						<td><a href='adminReserveDetail.do${m:makeQuery(paging, paging.pageNum)}&reserveId=${dto.id}'>${dto.id}</a></td>
						<td>${dto.memberDTO.account}</td>
						<td>${dto.roomTypeDTO.typeDesc}</td>
						<td>${dto.roomDTO.roomNum}호</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${dto.dateStart}"/></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${dto.dateEnd}"/></td>
						<%-- <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.dateReserve}"/></td> --%>
						<td>${dto.timeReserve}</td>
						<td>${dto.pax}</td>
					</tr>
			
				</c:forEach>
			</table>

		</div>
		
		<div class="panel-footer">
			<div class="text-center">
				<ul class="pagination">
					<c:if test="${paging.pageStart > paging.pageMax}">
						<li><a href="adminReserveList.do${m:makeQuery(paging, paging.pageStart - paging.pageMax)}">&laquo;</a></li>
					</c:if>
			
					<c:forEach begin="${paging.pageStart}" end="${paging.pageEnd}" var="i">
						<li
							<c:out value="${paging.pageNum == i ? 'class=active' : ''}"/>>
							<a href="adminReserveList.do${m:makeQuery(paging, i)}">${i}</a>
						</li>
					</c:forEach>
			
					<c:if test="${paging.pageEnd < paging.pageCount}">
						<li><a href="adminReserveList.do${m:makeQuery(paging, paging.pageStart + paging.pageMax)}">&raquo;</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</div>

<script>
 
$('#search').on("click", function(event) {
	self.location = "adminReserveList.do" + 
					"?pageNum=1" +
					"&searchKey=" + $("select option:selected").val() + 
					"&searchVal=" + $('#searchVal').val();
});
</script>
