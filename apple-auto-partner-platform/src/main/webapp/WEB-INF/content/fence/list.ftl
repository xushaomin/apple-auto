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

</head>
<body>


<div class="container" style="margin-top: 10px;">
    
 	<#include "../commons/page_header.ftl" />
    
    <#include "../commons/page_js.ftl" />

	<div class="row">
	    <div class="col-md-10">
	        <form class="form-inline" role="form" action="" id="filterForm">
	            <div class="form-group">
	                <select name="fenceType" class="form-control">
	                    <option value="">所有类型</option>
	                	<option value="1" <#if so.fenceType?? && so.fenceType == 1>selected</#if>>圆形</option>
	                	<option value="2" <#if so.fenceType?? && so.fenceType == 2>selected</#if>>矩形</option>
	                	<option value="3" <#if so.fenceType?? && so.fenceType == 3>selected</#if>>多边形</option>
	                	<option value="4" <#if so.fenceType?? && so.fenceType == 4>selected</#if>>省</option>
	                </select>
	            </div>
	            <div class="form-group">
	                <select name="isEnable" class="form-control">
	                    <option value="">所有状态</option>
	                    <option value="false" <#if so.isEnable?? && so.isEnable == false>selected</#if>>启用</option>
	                	<option value="true" <#if so.isEnable?? && so.isEnable == true>selected</#if>>停用</option>
	                </select>
	            </div>
	            <div class="form-group">
	                <div class="input-group">
	                    <div class="input-group-addon">排序</div>
	                    <select name="orderItem" class="form-control">
	                            <option value="LOG_TIME" selected>日志时间</option>
	                            <option value="CONN_TIME" >连接用时</option>
	                    </select>
	                </div>
	            </div>
	            <button type="submit" class="btn"><i class="fui-search"></i></button>
	            <strong>${page.totalCount}</strong>条围栏记录
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
		                <th>围栏名称</th>
		                <th><abbr title='围栏类型分为：圆形，矩形，多边形和省围栏'>围栏类型</abbr></th>
		                <th>状态</th>
		                <th>创建时间</th>
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
	                        <td><a target="_blank" href="/fence/show?id=${info.id}">${info.id}</a></td>
	                        <td>${info.name}</td>
	                        <td>
								<#if info.fenceType == 1> 
								  圆形围栏
								<#elseif info.fenceType == 2> 
								 矩形围栏
								<#elseif info.fenceType == 3> 
								多边形围栏
								<#else> 
								省围栏
								</#if>
	                        </td>
	                        <td>
	                        	<em class="fui-stumbleupon text-warning" title="变为不正常"></em>
	                        	<em class="fui-checkbox-checked text-success" title="返回正常"></em>
	                        </td>
	                        <td>${(info.createTime?string('yyyy-MM-dd'))!''}</td>
	                        <td>
	                        	<button href="/fence/show?id=${info.id}" class="btn btn-primary" data-toggle="modal" data-target="#myModal">查看</button>
	                        	
	                        	<a data-toggle="modal" href="/fence/show?id=${info.id}" data-target="#myModal">查看</a>  
	                        	
	                        </td>
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