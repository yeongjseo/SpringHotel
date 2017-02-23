<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


    
<div class="container">
	<div class="col-sm-10 col-sm-offset-1">
		<div class="panel panel-primary">
		 	<div class="panel-heading">
		 		<h2 class="text-center">회사위치</h2>
		 	</div>
			<div class="panel-body">
				<div id="map" style="width:100%;height:400px;"></div>
			</div>
		</div>
	</div>
</div>

<script>
 
function myMap() {
	var myCenter = new google.maps.LatLng(37.552670, 126.937826);
	var mapCanvas = document.getElementById("map");
	var mapOptions = {center: myCenter, zoom: 15};
	var map = new google.maps.Map(mapCanvas, mapOptions);
	var marker = new google.maps.Marker({position:myCenter});
	marker.setMap(map);
}

/*
function myMap() {
	var mapProp= {
	    center:new google.maps.LatLng(37.552670, 126.937826),
	    zoom:15,
	};
	var map = new google.maps.Map(document.getElementById("map"), mapProp);
}
*/
</script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBiyrJJ0xGQAhaq4xUA2BuVpZHfE1Snk38&callback=myMap"></script>

