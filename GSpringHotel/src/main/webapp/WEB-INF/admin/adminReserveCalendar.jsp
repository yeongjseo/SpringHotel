<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="m" uri="/WEB-INF/mytags.tld" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.sql.Date"%>


<div class="container">
	<div class="panel panel-primary">
  		<div class="panel-heading">
  			<div class="text-center">
  				<h4>룸 예약 관리</h4>
  			</div>
  		</div>
  		<div class="panel-body">
			<div class="row">
				<div class="col-sm-6">
					<div class="btn-group pull-right">
		  				<button type="button" class="btn btn-default" id="yearBefore">
		  					<span class="glyphicon glyphicon-chevron-left"></span>
		  				</button>
						<button type="button" class="btn btn-primary disabled" id="yearCur" data-year="${search.yearCur}">
							${search.yearCur}년
						</button>
						<button type="button" class="btn btn-default" id="yearNext">
							<span class="glyphicon glyphicon-chevron-right"></span>
						</button>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="btn-group">
						<button type="button" class="btn btn-default" id="monthBefore">
							<span class="glyphicon glyphicon-chevron-left"></span>
						</button>
						<button type="button" class="btn btn-primary disabled" id="monthCur" data-month="${search.monthCur}">
							${search.monthCur}월
						</button>
						<button type="button" class="btn btn-default" id="monthNext">
							<span class="glyphicon glyphicon-chevron-right"></span>
						</button>
					</div>
				</div>
			</div>
			<br>

			<table class="table table-bordered table-striped table-hover">
				<thead>
					<tr>
						<th>룸</th>
						<c:forEach var="day" begin="1" end="${search.dayLast}">
							<th>${day}</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="dto" begin="0" varStatus="row">
						<tr data-roomId="${dto.id}">
							<td> 
								<c:if test="${dto.roomTypeDTO != null}">
									${dto.roomTypeDTO.typeDesc} ${dto.roomNum}호
								</c:if>
								<c:if test="${dto.roomTypeDTO == null}">
									${dto.roomNum}호
								</c:if>
							</td>
		
							<c:forEach var="day" begin="1" end="${search.dayLast}">
								<%-- <td class="item-${dto.id}-${day}" data-id="${dto.id}"> --%>
								<td class="item-${dto.id}-${day}" data-id="-1">
									${day}
								</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>		
			</table>
		</div>
	</div>

</div>



<script>

function getRandomColor() {
	var letters = '0123456789ABCDEF';
	var color = '#';
	for (var i = 0; i < 6; i++ ) {
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}

function getColor(id) {
	// get bright random color but it's fixed by id.
	var r = (id * 123 + 128).toString(16).substr(-2);
	var g = (id * 234 + 128).toString(16).substr(-2);
	var b = (id * 345 + 128).toString(16).substr(-2);
	return '#' + r + g + b;
}


<c:forEach items="${list}" var="dto" varStatus="row">
	<c:if test="${dto.reserveDTO != null}">
		<c:forEach items="${dto.reserveDTO}" var="reserve">
			<c:set var="monStart" value="${m:monFormatter(reserve.dateStart)}"></c:set>
			<c:set var="monEnd" value="${m:monFormatter(reserve.dateEnd)}"/>
			<c:set var="dayStart" value="${m:dayFormatter(reserve.dateStart)}"/>
			<c:set var="dayEnd" value="${m:dayFormatter(reserve.dateEnd)}"/>
			
			
			<c:if test="${monStart < search.monthCur}">
				<c:set var="dayStart" value="1"/>
			</c:if>
			<c:if test="${search.monthCur < monEnd}">
				<c:set var="dayEnd" value="${search.dayLast}"/>
			</c:if>
			
			/* 
			var color = getRandomColor();
			<c:forEach var="day"  begin="${dayStart}" end="${dayEnd}" varStatus="var">
				var item = $(".item-${dto.id}-${day}");
				item.css("background-color", color);
			</c:forEach>
			 */
			// var color = getRandomColor();
			 
			var color = getColor("${reserve.id}");
			
			<c:forEach var="day"  begin="${dayStart}" end="${dayEnd}">
				var item = $(".item-${dto.id}-${day}");
				
				item.attr("data-id", "${reserve.id}");
				
				<c:if test="${day == dayStart}">
					item.css("background", "linear-gradient(to right, transparent 50%, " + color + " 51%)");
 
					
				</c:if>
				<c:if test="${day > dayStart && day < dayEnd}">
					item.css("background-color", color);
				</c:if>
				<c:if test="${day == dayEnd}">
					item.css("background", "linear-gradient(to right, " + color + " 50%, " + " transparent 51%)");
				</c:if>
				
			</c:forEach>
			
		</c:forEach>
	</c:if>
</c:forEach>
 

$("td[class^='item-']").on("click", function(e) {
	e.preventDefault();
	
	var obj = $(this);
	var id = obj.attr("data-id");
	console.log(id);

	if (id == '-1')
		return;
	
	var frm = new commonForm();
    frm.setUrl("adminReserveDetail.do");
    frm.addParam("reserveId", id);
    frm.send();
});


$("#yearBefore").on("click", function(e) {
	var yearCur = $("#yearCur").attr("data-year");
	var monthCur = $("#monthCur").attr("data-month");	
	var frm = new commonForm();
	yearCur--;
	frm.addParam("yearCur", yearCur);
	frm.addParam("monthCur", monthCur);
	frm.send();
});

$("#yearNext").on("click", function(e) {
	var yearCur = $("#yearCur").attr("data-year");
	var monthCur = $("#monthCur").attr("data-month");	
	var frm = new commonForm();
	yearCur++;
	frm.addParam("yearCur", yearCur);
	frm.addParam("monthCur", monthCur);
	frm.send();
});

$("#monthBefore").on("click", function(e) {
	var yearCur = $("#yearCur").attr("data-year");
	var monthCur = $("#monthCur").attr("data-month");
	monthCur--;
	if (monthCur == 0) {
		yearCur--;
		monthCur = 12;
	}
	
	var frm = new commonForm();
	frm.addParam("yearCur", yearCur);
	frm.addParam("monthCur", monthCur);
	frm.send();
});

$("#monthNext").on("click", function(e) {
	var yearCur = $("#yearCur").attr("data-year");
	var monthCur = $("#monthCur").attr("data-month");	
	monthCur++;
	if (monthCur > 12) {
		yearCur++;
		monthCur = 1;
	}
	
	var frm = new commonForm();
	frm.addParam("yearCur", yearCur);
	frm.addParam("monthCur", monthCur);
	frm.send();
});












</script>

