<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="keywords" content="车联网开放平台 "/>
    <meta name="description" content="车联网开放平台 "/>
    <meta name="author" content="andaily.com"/>

    <title>围栏管理</title>

    <link href="/flat_ui/css/vendor/bootstrap.min.css" rel="stylesheet"/>
    <link href="/flat_ui/css/flat-ui.min.css" rel="stylesheet"/>
    <link rel="shortcut icon" href="/favicon.ico"/>

	<style type="text/css">
		#allmap {height:600px; width: 800px;}
	</style>
		
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BOtebfvLgBMG1u0qOyxBDA9T"></script>
	
	
</head>
<body>
	<div id="allmap">dddddddddd</div>
</body>
</html>
<script type="text/javascript">

	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(116.404, 39.915);
	map.centerAndZoom(point,14);
		
	//增加滚轮缩放地图
	map.enableScrollWheelZoom();
		
	//添加控件
	map.addControl(new BMap.NavigationControl());
	map.addControl(new BMap.ScaleControl());
	map.addControl(new BMap.OverviewMapControl({isOpen: true}));
	map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]}));
	
		 	
	var arrx = [];
	var arry = [];
	var nodes = new Array();
	
	<#list list as info>
	nodes[${info_index}] = new Object();
	nodes[${info_index}].longitude = '${info.longitude}';
	nodes[${info_index}].latitude = '${info.latitude}';
	</#list>
	
	for(ix=0; ix<nodes.length; ix++){
		var objx = {};
		var objy = {};
	 	objx = nodes[ix].longitude;
	 	objy = nodes[ix].latitude;
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
	
</script>