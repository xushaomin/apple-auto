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

	<div class="row">
	    <div class="col-md-10">
	        <form class="form-inline" role="form" action="" id="filterForm">
	        	<div class="input-group">
	                <input type="text" name="account" value="${account!''}" class="form-control" placeholder="围栏关键字"/>
	            </div>
	            <button type="submit" class="btn"><i class="fui-search"></i></button>
	            <strong>${page.totalCount}</strong>条行程记录
	        </form>
	    </div>
	    <div class="col-md-2">
	        &nbsp;
	    </div>
	</div>



	<div class="row">
	    <div class="col-md-12">
	        <table class="table table-striped table-hover">
	            <thead>
		            <tr>
		                <th>编号</th>
		                <th>开始时间</th>
		                <th>结束时间</th>
		                <!--
		                <th>状态</th>
		                <th>创建时间</th>
		                -->
		                <th>操作</th>
		            </tr>
	            </thead>
	            <tbody>
	            	<!--
	                <tr>
	                    <td colspan="5">
	                        <strong class="text-info">2017-07-11</strong>
	                    </td>
	                </tr>
	                -->
	                <#list page.list as info>
	                    <tr class="text-danger">
	                        <td>
	                        	<a target="_blank" href="/fence/show?id=">--</a>
	                        </td>
	                        <td>${(info.startTime?string('yyyy-MM-dd HH:mm:ss'))!''}</td>
	                       	<td>${(info.endTime?string('yyyy-MM-dd HH:mm:ss'))!''}</td>
	                       	<td>
	                       		<button data-remote="/location/show?account=${info.account}&startTime=${info.startTime}&endTime=${info.endTime}" class="btn btn-primary" data-toggle="modal" data-target="#myModal">查看</button>
	                       	<td>
	                    </tr>
	                 </#list>
	            </tbody>
	        </table>
	
			<@paging pageNumber = page.pageNo totalPages = page.totalPage>
				<#include "../commons/pager.ftl">
			</@paging>
					
			<p class="help-block">
				<strong>提示</strong>: 系统将自动清理超出30天的监控日志
			</p>
	    </div>
	</div>
    
    <#include "../commons/page_bottom.ftl" />
    
</div>

<!-- Modal -->
<#include "../commons/page_modal.ftl" />

</body>
</html>