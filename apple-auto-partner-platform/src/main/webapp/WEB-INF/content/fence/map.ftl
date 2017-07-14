    <style type="text/css">    
	    body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}    
	    #allmap {width: 100%; height:500px; overflow: hidden;}    
	    #result {width:100%; font-size:12px;}    
	    dl,dt,dd,ul,li {
	        margin:0;    
	        padding:0;    
	        list-style:none;    
	    }
	    p {font-size:12px;}
	    dt {
	    	font-size:14px;    
	        font-family:"微软雅黑";    
	        font-weight:bold;    
	        border-bottom:1px dotted #000;    
	        padding:5px 0 5px 5px;    
	        margin:5px 0;    
	    }    
	    dd {
	    	padding:5px 0 0 5px;    
	    }    
	    li {   
	        line-height:28px;    
	    }    
    </style>
    
 	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />    
	<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />    

	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=F0i6SrLmHquLVNLCqpExxPrj8mWVdFwx"></script>    
    <!--加载鼠标绘制工具-->    
    <script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>    
    <!--加载检索信息窗口-->    
    <script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>    

    <div id="allmap" style="overflow:hidden;zoom:1;position:relative;">       
 		<div id="map" style="height:100%;-webkit-transition: all 0.5s ease-in-out;transition: all 0.5s ease-in-out;"></div>    
    </div>  
    <div id="result">    
        <input type="button" value="清除所有覆盖物" onclick="clearAll()"/>    
        <input type="button" value="获取多边形的顶点" onclick="getPoint()"/>
        <!--   
        <input type="button" value="开启线、面编辑功能" onclick="Editing('enable')" />    
        <input type="button" value="关闭线、面编辑功能" onclick="Editing('disable')" />
        <input type="button" value="画线" onclick="draw(BMAP_DRAWING_POLYLINE)" />     
   		<input type="button" value="显示原有多边形" onclick="showPolygon(this)" />
   		<input type="button" value="画点" onclick="draw(BMAP_DRAWING_MARKER)" />
        -->    
        <input type="button" value="画多边形" onclick="draw(BMAP_DRAWING_POLYGON)" />    
      	<input type="button" value="画矩形" onclick="draw(BMAP_DRAWING_RECTANGLE)" />    
    	<input type="button" value="画圆" onclick="draw(BMAP_DRAWING_CIRCLE)" />
    </div>
    
	<script type="text/javascript">    
	    function $(id){    
	    	return document.getElementById(id);    
		}
	  
	  	var drawType;
	  	
	    // 百度地图API功能    
	    var map = new BMap.Map('map');    
	    var poi = new BMap.Point(113.948913,22.530844);    
	    map.centerAndZoom(poi, 16);    
	    map.enableScrollWheelZoom();      
	    var overlays = [];    
	    var overlaycomplete = function(e){    
	        overlays.push(e.overlay);     
	    };    
	    var styleOptions = {    
	        strokeColor:"red",    //边线颜色。    
	        fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。    
	        strokeWeight: 3,       //边线的宽度，以像素为单位。    
	        strokeOpacity: 0.8,    //边线透明度，取值范围0 - 1。    
	        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。    
	        strokeStyle: 'solid' //边线的样式，solid或dashed。    
	    }    
	  
	    //实例化鼠标绘制工具    
	    var drawingManager = new BMapLib.DrawingManager(map, {    
	        isOpen: false, //是否开启绘制模式    
	        //enableDrawingTool: true, //是否显示工具栏    
	        drawingToolOptions: {
	            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置    
	            offset: new BMap.Size(5, 5), //偏离值    
	        },
	        circleOptions: styleOptions, //圆的样式    
	        polylineOptions: styleOptions, //线的样式    
	        polygonOptions: styleOptions, //多边形的样式    
	        rectangleOptions: styleOptions //矩形的样式    
	    });      
	  
	     //添加鼠标绘制工具监听事件，用于获取绘制结果    
	    drawingManager.addEventListener('overlaycomplete', overlaycomplete);    
	  
	    map.addEventListener("rightclick",function(e) {
	  		if(confirm(e.point.lng + "," + e.point.lat)) {
	     		$("shape").innerHTML=$("shape").innerHTML+" <br/>("+e.point.lng+","+e.point.lat+")";
	     	}
	 	});
	 	
	    function draw(type) {
	    	drawType = type;
	        drawingManager.open();
	        drawingManager.setDrawingMode(type);
	       	clearAll();
	    }
	  
	    function clearAll() {
	        for(var i = 0; i < overlays.length; i++) {
	            map.removeOverlay(overlays[i]);
	        }
	        overlays.length = 0;
	    }
	    
	    
	    function getPoint() {
	    	$("parameter").innerHTML = '';
	    	if(drawType == 'circle') {
	    		var overlay = overlays[0];
	    		var radius = overlay.getRadius();
	    		var point = overlay.getCenter();
	    		
	    		alert(radius);
	    		
	    		
	    		//$(this).parent().('#radius').val(radius);
	    		 
	    		$("parameter").innerHTML = point.lng + "," + point.lat;
	    		
	    		
	    	} else {
	    		for(var i = 0; i < overlays.length; i++) {
		       		var overlay = overlays[i].getPath();
		            for(var j = 0; j < overlay.length; j++) {
		                var grid = overlay[j];
		                if(j == overlay.length - 1) {
		                	$("parameter").innerHTML = $("parameter").innerHTML + grid.lng + "," + grid.lat;
					     } else {
		 			   		$("parameter").innerHTML = $("parameter").innerHTML + grid.lng + "," + grid.lat + "|";
				         }
		  			}
		        }
	    	}
	    	
	    	if(drawType == 'circle') {
	    		$("input[name='fenceType']:eq(1)").attr("checked",'checked');
	    	}
	    	else if(drawType == 'rectangle') {
	    		$("input[name='fenceType']:eq(2)").attr("checked",'checked');
	    	}
	    	else if(drawType == 'polygon') {
	    		$("input[name='fenceType']:eq(3)").attr("checked",'checked');
	    	}
	    	else {
	    		alert('类型错误');
	    	}
	    }
	       
	    function Editing(state){    
	        for(var i = 0; i < overlays.length; i++){    
	            state=='enable'?overlays[i].enableEditing():overlays[i].disableEditing();    
	        }    
	    }    
	  
	    function  showPolygon(btn){    
	      var polygon = new BMap.Polygon([    
	          new BMap.Point(113.941853,22.530777),    
	          new BMap.Point(113.940487,22.527789),    
	          new BMap.Point(113.94788,22.527597),    
	          new BMap.Point(113.947925,22.530618)    
	      ], styleOptions);  //创建多边形    
	      map.addOverlay(polygon);   //增加多边形    
	      // overlays.push(polygon); //是否把该图像加入到编辑和删除行列    
	       btn.setAttribute('disabled','false');    
	       showText();    
	     }    
	  
	     function showText(){    
	        var opts = {    
	        position : new BMap.Point(113.941853,22.530777),    // 指定文本标注所在的地理位置    
	        offset   : new BMap.Size(30, 30)    //设置文本偏移量    
	       }    
	     var label = new BMap.Label("不可编辑", opts);  // 创建文本标注对象    
	        label.setStyle({    
	        color : "black",    
	         fontSize : "16px",    
	        fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。    
	        });    
	      map.addOverlay(label);      
	     }    
</script>