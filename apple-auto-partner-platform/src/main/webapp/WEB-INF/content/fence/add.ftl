<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="keywords" content="车联网开放平台 "/>
    <meta name="description" content="车联网开放平台 "/>
    <meta name="author" content="andaily.com"/>
    <title>新增围栏</title>
    <#include "../commons/page_css.ftl" />
    
    <link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />    
    <link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />    
    
 	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=F0i6SrLmHquLVNLCqpExxPrj8mWVdFwx"></script>    
    <!--加载鼠标绘制工具-->    
    <script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>    
    <!--加载检索信息窗口-->    
    <script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>    
    
</head>
<body>


<div class="container" style="margin-top: 10px;">
    
 	<#include "../commons/page_header.ftl" />
    
    <#include "../commons/page_js.ftl" />

	<div>
	    <div class="row">
	        <div class="col-md-12">
	            <h4>添加 围栏</h4>
	            <form id="formDto" class="form-horizontal" action="/fence/save" method="post">
	                <div class="form-group">
	                    <label for="name" class="col-sm-2 control-label">围栏名称</label>
	                    <div class="col-sm-8">
	                        <input id="name" name="name" class="form-control" placeholder="请输入实例名称" required="true" type="text" value="" maxlength="255"/>
	                        <p class="help-block">对该围栏的称呼</p>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-sm-2 control-label">围栏类型</label>
	                    <div class="col-sm-8">
	                        <label class="toggle-radio">
	                            <input type="radio" name="fenceType" value="1" checked/> 圆形
	                        </label>
	                        &nbsp;
	                        <label class="toggle-radio">
	                            <input type="radio" name="fenceType" value="2" /> 矩形
	                        </label>
	                        &nbsp;
	                        <label class="toggle-radio">
	                            <input type="radio" name="fenceType" value="3" /> 多边形
	                        </label>
	                        <!--
	                        &nbsp;
	                        <label class="toggle-radio">
	                            <input type="radio" name="fenceType" value="4" /> 省
	                        </label>
	                        -->
	                        <p class="help-block">指定围栏类型. 默认: 圆形</p>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label for="parameter" class="col-sm-2 control-label">经纬度</label>
	                    <div class="col-sm-8">
	                        <textarea id="parameter" name="parameter" class="form-control" placeholder="请输入关于该围栏的经纬度信息" rows="3" required="true"></textarea>
	                        <p class="help-block">关于该围栏的经纬度信息; (比选)
	                        	<a href="/fence/map" class="addParam" title="地图选点" data-toggle="modal" data-target="#myModal"><em class="fui-plus-circle"></em></a>
	                        </p>
	                    </div>
	                </div>
		
	                <div class="form-group">
	                    <label for="radius" class="col-sm-2 control-label"><abbr title='半径(m)'>半径</abbr></label>
	                    <div class="col-sm-8">
	                        <input id="radius" name="radius" class="form-control" placeholder="请输入半径(m)" required="true" type="text" value="500"/>
	                        <p class="help-block">圆形围栏的半径, 默认为500, 单位:米</p>
	                    </div>
	                </div>

					<div class="form-group">
	                    <label class="col-sm-2 control-label">提醒类型</label>
	                    <div class="col-sm-8">
	                        <label class="toggle-radio">
	                            <input type="radio" name="dealType" value="GET" checked/> 出围栏
	                        </label>
	                        &nbsp;
	                        <label class="toggle-radio">
	                            <input type="radio" name="dealType" value="POST" /> 入围栏
	                        </label>
	                        &nbsp;
	                        <label class="toggle-radio">
	                            <input type="radio" name="dealType" value="POST" /> 出+入
	                        </label>
	                        <p class="help-block">指定围栏提醒类型. 默认: 出围栏</p>
	                    </div>
	                </div>
	                
	                <div class="form-group">
	                    <label for="maxConnectionSeconds" class="col-sm-2 control-label"><abbr title='提醒时间间隔(m)'>提醒时间间隔</abbr></label>
	                    <div class="col-sm-8">
	                        <input id="maxConnectionSeconds" name="maxConnectionSeconds" class="form-control" placeholder="请输入提醒时间间隔(s)" required="true" type="text" value="600"/>
	                        <p class="help-block">提醒时间间隔, 默认为600, 单位:秒</p>
	                    </div>
	                </div>
	
	                <div class="form-group">
	                    <div class="col-sm-offset-2 col-sm-10">
	                        <button type="submit" class="btn btn-primary"><em class="fui-check-circle"></em> 保存</button>
	                        &nbsp;<a href="list.hb">取消</a>
	                    </div>
	                </div>
	            </form>
	        </div>
	    </div>
	</div>

	
    
</div>

<!-- Modal -->
<#include "../commons/page_modal.ftl" />

</body>
</html>