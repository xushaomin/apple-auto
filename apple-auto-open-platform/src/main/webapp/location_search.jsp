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
<div id="map"></div>

</body>
</html>
<script type="text/javascript">

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


var xmlDoc;
            if (window.ActiveXObject) {
                xmlDoc = new ActiveXObject('Microsoft.XMLDOM');//IE浏览器
                xmlDoc.async = false;
                xmlDoc.load("shuzi.xml");
            }
          else if (navigator.userAgent.indexOf("Firefox")>0) { //火狐浏览器
                xmlDoc = document.implementation.createDocument('', '', null);
                xmlDoc.async = false;
               xmlDoc.load("shuzi.xml");
            } 
           else{ //谷歌浏览器
              var xmlhttp = new window.XMLHttpRequest();
                xmlhttp.open("GET","shuzi.xml",false);
                xmlhttp.send(null);
                if(xmlhttp.readyState == 4){
               xmlDoc = xmlhttp.responseXML.documentElement;
               } 
            }
 var arrx=[];
 var arry=[];
var nodes;
nodes = xmlDoc.getElementsByTagName('zuobiao'); //读取XML文件中需要显示的数据
  for(ix=0;ix<nodes.length;ix++){
	  var obj={};
	  var obj1={};
	 // obj=nodes[ix].childNodes[0].nodeValue;
	 obj=nodes[ix].getElementsByTagName("x")[0].childNodes[0].nodeValue;
	 // obj1=nodes[ix].childNodes[1].nodeValue;
	 obj1=nodes[ix].getElementsByTagName("y")[0].childNodes[0].nodeValue;
	  arrx.push(obj);
	  arry.push(obj1);
  }


  for(i=0;i<nodes.length;i++){
	    for(j=0;j<nodes.length;j++){		
	 var p=new BMap.Point(arrx[i], arry[i]);
    j=new BMap.Marker(p); 
	
	  	

	 
	 
 var lab1 = new BMap.Label("张莹", { offset: new BMap.Size(20, -10) });
 
	 map.addOverlay(j);
	  var content1 = '<div class="infowin"><p><b>张莹</b></p><p>金泉家园</p><p><a href="test.avi">播放视频</a></p><p><a href="http://www.hao123.com" target="_blank">hao123链接</a></p><iframe src="http://www.hao123.com"></iframe></div>';
	  <!-- --><embed src="test.wmv" width="320" height="240" autostart="false" />
    var infowin1 = new BMap.InfoWindow(content1);
    j.addEventListener("click", function() {
        this.openInfoWindow(infowin1);
    });
		}
	}
 

 var arr=[];
  for(i=0;i<nodes.length;i++){
	  arr[i]=new BMap.Point(arrx[i], arry[i]);
  }
	 
 

var polyline = new BMap.Polyline( arr
														
, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5}); 
map.addOverlay(polyline); 
            setTimeout(function(){  
                map.setViewport(arr);          //调整到最佳视野  
            },1000);  


</script>