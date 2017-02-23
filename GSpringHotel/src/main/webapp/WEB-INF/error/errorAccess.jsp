<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
textarea { resize:none; height:200px;}
</style>

<div class="container">
	<div class="col-sm-10 col-sm-offset-1">
		<form class="form-horizontal" method="post" action="boardDetail.do" id="form1">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="text-center">
						접근 에러
					</h3>
				</div>
				<div class="panel-body">
					<div class="form-group">
						<div class="col-sm-12 ">
							<textarea class="form-control" name="errorParam" rows="10" readonly>
잘못된 접속을 하였습니다.
정상적인 접속을 하십시오.
							</textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>