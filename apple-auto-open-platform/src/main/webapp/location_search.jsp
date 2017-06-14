<%@ page language="java" pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<style type="text/css">
		body, html,#map {width: 100%;height: 100%;overflow: hidden;hidden;margin:0;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BOtebfvLgBMG1u0qOyxBDA9T"></script>	
	<title>地图</title>
</head>
<body>
	<div id="searchDiv">
		帐号：<input id="account" type="text" name="account" value="2017061401" />
		开始时间：<input id="startTime" name="startTime" onfocus="WdatePicker({dateFmt:'yyyyMMddHHmmss'})" value="20170614090000" />
		结束时间：<input id="endTime" name="endTime" onfocus="WdatePicker({dateFmt:'yyyyMMddHHmmss'})" value="20170614100000" />
		<button id="search" type="button" onclick="doShow();">提交</button>
	</div>
	<div id="map"></div>
	
	<script src="http://www.my97.net/dp/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
	<script type="text/javascript">

	function doShow() {
	
		var account = document.getElementById("account").value;
		var startTime = document.getElementById("startTime").value;
		var endTime = document.getElementById("endTime").value;
				
		// 百度地图API功能
		var map = new BMap.Map("map");
		var point = new BMap.Point(116.404, 39.915);
		map.centerAndZoom(point,14);
	
		//增加滚轮缩放地图
		map.enableScrollWheelZoom();
	
		//添加控件
		map.addControl(new BMap.NavigationControl());
		map.addControl(new BMap.ScaleControl());
		map.addControl(new BMap.OverviewMapControl({isOpen: true}));
		map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]}));
		
		var mmap = map;
		var url = "/router?method=apple.auto.location.list&appkey=apple-auto&v=1.0&mapType=1&format=xml&";
		url = url + "account=" + account + "&";
		url = url + "startTime=" + startTime + "&";
		url = url + "endTime=" + endTime;
		
		var xmlDoc;
	    if (window.ActiveXObject) {
	    	xmlDoc = new ActiveXObject('Microsoft.XMLDOM'); //IE浏览器
	     	xmlDoc.async = false;
	 		xmlDoc.load(url);
	  	} else if (navigator.userAgent.indexOf("Firefox")>0) { //火狐浏览器
	    	xmlDoc = document.implementation.createDocument('', '', null);
	     	xmlDoc.async = false;
	 		xmlDoc.load(url);
		} else { //谷歌浏览器
	    	var xmlhttp = new window.XMLHttpRequest();
	       	xmlhttp.open("GET", url, false);
	    	xmlhttp.send(null);
	      	if(xmlhttp.readyState == 4) {
	      		xmlDoc = xmlhttp.responseXML.documentElement;
	     	}
	 	}
	 	
		var arrx = [];
		var arry = [];
		var nodes;
		nodes = xmlDoc.getElementsByTagName('list'); //读取XML文件中需要显示的数据
	  	for(ix=0; ix<nodes.length; ix++){
			var objx = {};
			var objy = {};
		 	objx = nodes[ix].getElementsByTagName("longitude")[0].childNodes[0].nodeValue;
		 	objy = nodes[ix].getElementsByTagName("latitude")[0].childNodes[0].nodeValue;
		  	arrx.push(objx);
		 	arry.push(objy);
	  	}

		for(i=0; i<nodes.length; i++) {
		 	var p = new BMap.Point(arrx[i], arry[i]);
	    	var j = new BMap.Marker(p);
			map.addOverlay(j);
		  		
	    	j.addEventListener("click", function() {
				var myGeo = new BMap.Geocoder();
				myGeo.getLocation(p, function(result) {
					if (result) {						
						var addrContent = '<span id="arrx">经度：' + p.lng + '</span><br />';
		  				addrContent = addrContent + '<span id="arry">纬度：' + p.lat + '</span><br />';
	    				addrContent = addrContent + '<span id="addr">地址：' + result.address + '</span>';

	    				var addrInfoWin = new BMap.InfoWindow(addrContent);
	    				j.openInfoWindow(addrInfoWin);
					}
				});
	    	});
		}
	 
		var arr=[];
	  	for(i=0; i<nodes.length; i++) {
			arr[i] = new BMap.Point(arrx[i], arry[i]);
	  	}
		
		var polyline = new BMap.Polyline(arr, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5});
		map.addOverlay(polyline);
	    setTimeout(function() {
	    	map.setViewport(arr);//调整到最佳视野  
	   	}, 1000);
   	
   	
	}
	</script>
</body>
</html>