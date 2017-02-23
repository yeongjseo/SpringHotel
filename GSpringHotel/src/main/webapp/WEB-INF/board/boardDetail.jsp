<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="m" uri="/WEB-INF/mytags.tld" %>

<c:set var="readonly" value="readonly"/>
<c:if test="${loginDTO != null}">
	<c:if test="${loginDTO.id == board.userId}">
		<c:set var="readonly" value=""/>
	</c:if>
	<c:if test="${loginDTO.id == 1}">
		<c:set var="readonly" value=""/>
	</c:if>
</c:if>

<style type="text/css">
.image_popup {
	position: absolute;
}

.image_back {
	background-color: gray;
	opacity: 0.5;
	width: 100%;
	height: 300%;
	overflow: hidden;
	z-index: 1101;
}

.image_front {
	z-index: 1110;
	opacity: 1;
	boarder: 1px;
	margin: auto;
}

.image_show {
	position: relative;
	max-width: 1200px;
	max-height: 800px;
	overflow: auto;
}
</style>

<div class='image_popup image_back' style="display:none;"></div>
<div id="image_popup_front" class='image_popup image_front' style="display: none;">
	<img id="image_popup_img">
</div>


<div class="container">
 	<div class="col-sm-10 col-sm-offset-1">
 		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="text-center">
					<h4><c:out value="${m:getBoardTypeLongDesc(boardType)}"/></h4>
				</div>
			</div>
				
			<div class="panel-body">
			
				<form class="form-horizontal" method="post" action="boardDetail.do" name="form1" id="form1" enctype="multipart/form-data">
					<input type="hidden" name="boardId" id="boardId" value="${board.id}">
					<input type="hidden" name="boardType" id="boardType" value="${boardType}">
					<input type="hidden" name="pageNum" id="pageNum" value="${paging.pageNum}">
					<input type="hidden" name="searchKey" id="searchKey" value="${paging.searchKey}">
					<input type="hidden" name="searchVal" id="searchVal" value="${paging.searchVal}">
					
					<div class="form-group">
						<label class="control-label col-sm-2" for="account">작성자:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="account" name="account" value="${board.memberDTO.account}" placeholder="계정명" readonly>
						</div>
						
					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-2" for="title">작성일:</label>
						<label class="control-label col-sm-4 pull-left">
							<span class="label label-primary lable-lg pull-left">
								<%-- <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${board.writeDate}"/> --%>
								${board.writeTime}
							</span>
						</label>
						<label class="control-label col-sm-2" for="title">조회수:</label>
						<label class="control-label col-sm-4">
							<span class="badge bg-red pull-left">${board.readCount}</span>
						</label>
					</div>
					
					<div class="form-group">
						<label class="control-label col-sm-2" for="title">제목:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="title" name="title" value="${board.title}" <c:out value="${readonly}"/> placeholder="제목">
						</div>
					</div>
					<div class="form-group" id="images">
						<label class="control-label col-sm-2" for="content">내용:</label>
						<div class="col-sm-10">
							<div class="row">
								<c:forEach items="${files}" var="file" begin="0" varStatus="var">
									<c:if test="${m:isImageFile(file.filename) == true}">
										<div class="col-sm-4">
											<img class="img-responsive img-rounded"	
												src="boardFileDisplay.do?fileId=${file.id}&type=${boardType}" 
												alt="Image">
										</div>
									</c:if>
								</c:forEach>
							</div>
							
							<textarea class="form-control" id="content" name="content" placeholder="내용" <c:out value="${readonly}"/> rows="10">${board.content}</textarea>
						</div>
					</div>
					<br>
				
					<div class="form-group" id="fileDiv">
						<label class="control-label col-sm-2" for="files">첨부파일:</label>
						
						<c:forEach var="file" items="${files}" begin="0" varStatus="var">
						<div>
							<input type="hidden" id="fileId_${file.id}" name="fileId_${file.id}" value="${file.id}">
							<c:if test="${var.index == '0'}">
								<div class="col-sm-5">
									<button type="button" class="btn btn-block btn-default" id="file_${var.index}" name="file_${var.index}" 
										data-id="${file.id}" data-type="${boardType}">
										${file.filename} (${file.filesize} Byte)
									</button>
								</div>
							</c:if>
							<c:if test="${var.index != '0'}">
								<div class="col-sm-offset-2 col-sm-5">
									<button type="button" class="btn btn-block btn-default" id="file_${var.index}" name="file_${var.index}" 
										data-id="${file.id}" data-type="${boardType}">
										${file.filename} (${file.filesize} Byte)
									</button>
								</div>
							</c:if>
							<div class="col-sm-5">
								<c:if test="${readonly eq '' }">
									<button type="button" class="btn btn-danger" id="delete_${var.index}" name="delete_${var.index}">삭제</button>
								</c:if>
							</div>
						</div>
						</c:forEach>
					</div>
					
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<c:if test="${readonly eq '' }">
								<button type="button" class="btn btn-success" id="addFile">파일추가</button>
								
							</c:if>
							
							<div class="pull-right">
								<button type="submit" class="btn btn-warning">수정</button>
								<button type="button" class="btn btn-danger" id="delete">삭제</button>
								<button type="button"  class="btn btn-info" id="back">뒤로</button>
							</div>
						</div>
							
					</div>
				</form>
				
				<div class="col-sm-offset-2 col-sm-10">
					<jsp:include page="../board/boardReplyList.jsp"/>
				</div>
			</div>
		</div>		
	</div>
</div>


<script>
var form1 = $("#form1");
var fileCnt = '${fn:length(files) + 1}';

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


$("#images").on("click", "img", function(e) {
	event.preventDefault();
	
	var src = $(this).attr("src");

	var img = $("#image_popup_img");
	img.attr("src", src);

	// console.log(imgTag.attr("src"));

	$(".image_popup").show('slow');
	img.addClass("image_show");
});

$("#image_popup_img").on("click", function() {

	$(".image_popup").hide('slow');

});







$('#back').on("click", function(ev) {
	self.location = "boardList.do" +
					"?boardType=" + "${boardType}" +
					"&pageNum=" + "${paging.pageNum}" + 
					"&searchKey=" + "${paging.searchKey}" + 
					"&searchVal=" + "${paging.searchVal}";
		
});


$("#delete").on("click", function(event) {
	
	var frm = new commonForm(); 
	frm.setUrl("boardDelete.do");
	frm.addParam("boardType", form1.find("#boardType").val());
	frm.addParam("boardId", form1.find("#boardId").val());
	frm.addParam("pageNum", form1.find("#pageNum").val());
	frm.addParam("searchKey", form1.find("#searchKey").val());
	frm.addParam("searchVal", form1.find("#searchVal").val());
	frm.submit();
	
})

$("button[name^='file']").on("click", function(e) {
	e.preventDefault();
	
	var obj = $(this);
	var idx = obj.attr("data-id");
	var type = obj.attr("data-type");

	var frm = new commonForm();
    frm.setUrl("boardFileDownload.do");
    frm.addParam("type", type);
    frm.addParam("fileId", idx);
    frm.submit();
	
});


$("button[name^='delete']").on("click", function(e){
	e.preventDefault();
	var isFirst = false;
	if ($("#fileDiv button[name^=delete]").first().is($(this)))
		isFirst = true;		
	
	$(this).parent().parent().remove();
	
	if (isFirst && $("#fileDiv div > div:nth-child(2)"))
		$("#fileDiv div > div:nth-child(2)").first().removeClass("col-sm-offset-2")
	
});


$('#addFile').on("click", function(e) {
	var offset = '';
	if ($("#fileDiv div").length != 0)
		offset = 'col-sm-offset-2 ';
	var str = 
		"<div>" + 
			"<div class='" + offset + " col-sm-5'>" + 
				"<input type='file' class='form-control' id='file_" + fileCnt + "' name='file_" + fileCnt + "'/>" + 
			"</div>" + 
			"<div class='col-sm-5'>" + 
				"<button type='button' class='btn btn-danger' id='delete_" + fileCnt + "' name='delete_" + fileCnt + "'>삭제</button>" +
			"</div>" + 
		"</div>";
		
	$("#fileDiv").append(str);
	
	$("button[name='delete_" + (fileCnt++) + "']").on("click", function(e) {
		e.preventDefault();
		
		var isFirst = false;
		if ($("#fileDiv button[name^=delete]").first().is($(this)))
			isFirst = true;		
		
		$(this).parent().parent().remove();
		
		if (isFirst && $("#fileDiv div > div:nth-child(1)"))
			$("#fileDiv div > div:nth-child(1)").removeClass("col-sm-offset-2")
		

	});

});


</script>







