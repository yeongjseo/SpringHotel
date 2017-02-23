<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${loginDTO != null}">
	<c:set var="account" value="${loginDTO.account}"/>
</c:if>

<div class="container">
 
 	<div class="col-sm-10 col-sm-offset-1">
		<form class="form-horizontal" method="post" action="boardInsert.do" id="form1" enctype="multipart/form-data">
			<input type="hidden" name="boardType" value="${boardType}">
			<%-- 
			<input type="hidden" name="boardId" value="${boardId}">
			<input type="hidden" name="memberId" value="${memberId}">
			 --%>
			<input type="hidden" name="pageNum" value="${pageNum}">
			<input type="hidden" name="searchKey" value="${searchKey}">
			<input type="hidden" name="searchVal" value="${searchVal}">
			
			<div class="panel panel-primary">
		
				<div class="panel-heading">
					<h3 class="text-center">
						${boardType}
					</h3>
				</div>
		
				<div class="panel-body">
					<div class="form-group">
						<label class="control-label col-sm-2" for="account">작성자:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="account" name="account" value="${account}" placeholder="계정명" readonly>
						</div>
						
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="titel">제목:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="title" name="title" value="${title}" placeholder="제목" maxlength="40">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="content">내용:</label>
						<div class="col-sm-10">
							<textarea class="form-control" id="content" name="content" placeholder="내용" rows="10" maxlength="400">${content}</textarea>
						</div>
					</div>
					<div class="form-group" id="fileDiv">
						<label class="control-label col-sm-2" for="file">첨부파일:</label>
						<div>
							<div class="col-sm-5">
								<input type="file" class="form-control" id="file" name="file_0" placeholder="파일"/>
							</div>
							<div class="col-sm-5">
								<button type="button" class="btn btn-danger" id="delete" name="delete">삭제</button>
							</div>
						</div>
						
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="btn btn-success" id="addFile">파일추가</button>
							<button type="submit" class="btn btn-primary" id="write">등록</button>
							<button type="button" class="btn btn-info" id="back">뒤로</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>


<script>
var form1 = $("#form1");
var fileCnt = 1;

form1.submit(function(e) {
	event.preventDefault();
	if ($("#title").val() == "") {
		openModalInfo("알림", "제목을 입력하세요.")
		return;
	}
	
	if ($("#content").val() == "") {
		openModalInfo("알림", "내용을 입력하세요.")
		return;
	}
	
	$(this).get(0).submit();
});

$('#back').on("click", function(e) {
	self.location = "boardList.do" +
					"?boardType=" + "${boardType}" +
					"&pageNum=" + "${pageNum}" + 
					"&searchKey=" + "${searchKey}" + 
					"&searchVal=" + "${searchVal}";
		
});

$('#addFile').on("click", function(e) {
	var offset = '';
	if ($("#fileDiv div").length != 0)
		offset = 'col-sm-offset-2 ';
	var str = 
		"<div>" + 
			"<div class='" + offset + " col-sm-5'>" + 
				"<input type='file' class='form-control' id='file' name='file_" + (fileCnt++) + "'/>" + 
			"</div>" + 
			"<div class='col-sm-5'>" + 
				"<button type='button' class='btn btn-danger' id='delete' name='delete'>삭제</button>" +
			"</div>" + 
		"</div>";
		
	$("#fileDiv").append(str);
	
	$("button[name='delete']").on("click", function(e) {
		e.preventDefault();
		
		$(this).parent().parent().remove();

	});

});

</script>



