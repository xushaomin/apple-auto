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
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">

	//根据经纬极值计算绽放级别。
	function getZoom (maxLng, minLng, maxLat, minLat) {
		var zoom = ["50","100","200","500","1000","2000","5000","10000","20000","25000","50000","100000","200000","500000","1000000","2000000"]//级别18到3。
		var pointA = new BMap.Point(maxLng, maxLat);  // 创建点坐标A
		var pointB = new BMap.Point(minLng, minLat);  // 创建点坐标B
		var distance = map.getDistance(pointA, pointB).toFixed(1);  //获取两点距离,保留小数点后两位
		for (var i = 0,zoomLen = zoom.length; i < zoomLen; i++) {
			if(zoom[i] - distance > 0) {
				return 18-i+3;//之所以会多3，是因为地图范围常常是比例尺距离的10倍以上。所以级别会增加3。
			}
		};
	}

	//根据原始数据计算中心坐标和缩放级别，并为地图设置中心坐标和缩放级别。
	function setZoom(points) {
		if(points.length>0) {
			var maxLng = points[0].lng;
			var minLng = points[0].lng;
			var maxLat = points[0].lat;
			var minLat = points[0].lat;
			var res;
			for (var i = points.length - 1; i >= 0; i--) {
				res = points[i];
				if(res.lng > maxLng) maxLng = res.lng;
				if(res.lng < minLng) minLng = res.lng;
				if(res.lat > maxLat) maxLat = res.lat;
				if(res.lat < minLat) minLat = res.lat;
			};
			var cenLng = (parseFloat(maxLng)+parseFloat(minLng))/2;
			var cenLat = (parseFloat(maxLat)+parseFloat(minLat))/2;
			var zoom = getZoom(maxLng, minLng, maxLat, minLat);
			map.centerAndZoom(new BMap.Point(cenLng, cenLat), zoom);  
		} else {
			//没有坐标，显示全中国
			map.centerAndZoom(new BMap.Point(103.388611,35.563611), 5);
		} 
	}
	
	var polygonString = "${info.parameter}";
	var pointArray = polygonString.split("|");
	var points = [];
	var mapsPoint = [];
	for(i=0; i<pointArray.length; i++) {
		var lonlat = pointArray[i].split(",");
  		points[i] = new Object();
		points[i].lng = lonlat[0];
		points[i].lat = lonlat[1];
		mapsPoint[i] = new BMap.Point(lonlat[0], lonlat[1]);
	}
	
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	setZoom(points);
	map.enableScrollWheelZoom();
	
	var overlay;
	<#if info.fenceType == 1> 
		overlay = new BMap.Circle(mapsPoint[0], ${info.radius}, {
            strokeColor: "${info.color!'#fc261d'}",
            strokeWeight: 4,
            fillColor: "#E2E8F1",
            fillOpacity: 0.6
		});
	<#else>
		overlay = new BMap.Polygon(mapsPoint, {
			strokeColor: "${info.color!'#fc261d'}",
            strokeWeight: 4,
            fillColor: "#E2E8F1",
            fillOpacity: 0.6
		});
	</#if>
	map.addOverlay(overlay);
	
	var index = 0;
	
	//在轨迹点上创建图标，并添加点击事件，显示轨迹点信息。pointss,数组。
	function addMarker(lng, lat){
		var point = new BMap.Point(lng, lat);
		var marker = new BMap.Marker(point);
		map.addOverlay(marker); 
	}    
		
	//添加线
	function addLine(index){
		if(index == 0){
			return;
		}
		var linePoints = [];
	
		// 创建标注对象并添加到地图   
		//for(i = 0; i <= index; i++){
		//	linePoints.push(new BMap.Point(points[i].lng, points[i].lat));
		//}
		linePoints.push(new BMap.Point(points[index-1].lng, points[index-1].lat));
		linePoints.push(new BMap.Point(points[index].lng, points[index].lat));
		
		polyline = new BMap.Polyline(linePoints, {strokeColor:"white", strokeWeight:1, strokeOpacity:0.5});   //创建折线
		map.addOverlay(polyline);   //增加折线
	}
		
	var makerPointss = [];
	var newLinePointss = [];	
		
	function dynamicLine(){
		var lng = points[index].lng;
		var lat = points[index].lat;
			
		//addMarker(lng, lat);//增加对应该的轨迹点
		addLine(index);
		index = index + 1;
		
		setTimeout(dynamicLine, 1000);
	}
	
    setTimeout(dynamicLine, 1000);//动态生成新的点。
    
</script>