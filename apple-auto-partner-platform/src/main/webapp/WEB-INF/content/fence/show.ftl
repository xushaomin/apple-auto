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
		#allmap {height:500px; width: 500px;}
	</style>
	
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=BOtebfvLgBMG1u0qOyxBDA9T"></script>	
	
</head>
<body>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 6);
	map.enableScrollWheelZoom();
	
	var polygon = new BMap.Polygon([
	
		<#list points as point>
    		new BMap.Point(${point})<#if point_has_next>,</#if>
		</#list>		
		
	], {strokeColor:"blue", strokeWeight:4, strokeOpacity:1});  //创建多边形
	map.addOverlay(polygon);   //增加多边形
</script>