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

</head>
<body>


<div class="container" style="margin-top: 10px;">
    
    <div class="row">
        <div class="col-xs-12">
            <nav class="navbar navbar-inverse navbar-embossed" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-01">
                        <span class="sr-only">Toggle navigation</span>
                    </button>
                    <a class="navbar-brand" href="/">HB</a>
                </div>
                <div class="collapse navbar-collapse" id="navbar-collapse-01">
                    <ul class="nav navbar-nav navbar-left" id="mainMenu">
                        <li><a href="/">Monitoring</a></li>
                        <li class="dropdown" id="instanceMenu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Instance <b class="caret"></b></a>
                            <span class="dropdown-arrow"></span>
                            <ul class="dropdown-menu">
                                <li><a href="/instance/instance_form.hb">New Instance</a></li>
                                <li><a href="/instance/list.hb">Instances</a></li>
                            </ul>
                        </li>
                        <li class="dropdown" id="logMenu">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Log <b class="caret"></b></a>
                            <span class="dropdown-arrow"></span>
                            <ul class="dropdown-menu">
                                <li><a href="/log/list.hb">Monitor Log</a></li>
                                <li><a href="/log/reminder_list.hb">Reminder Log</a></li>
                            </ul>
                        </li>
                        <li class="dropdown" id="systemMenu">
                       		<a href="#" class="dropdown-toggle" data-toggle="dropdown">System <b class="caret"></b></a>
                     		<span class="dropdown-arrow"></span>
                         	<ul class="dropdown-menu">
	                            <li><a href="/user/list.hb">Users</a></li>
                                <li><a href="/user/weixin_list.hb">WeChat Users</a></li>
                                <li><a href="/system/setting.hb">Setting</a></li>
                      		</ul>
                     	</li>
                        <li id="userProfileMenu">
                       		<a href="/user_profile.hb">hb</a>
                     	</li>
                        <li>
                        	<a href="/logout" title="退出"><em class="fui-exit text-danger"></em></a>
                        </li>
                    </ul>
                    <form class="navbar-form navbar-right" action="/search.hb" role="search">
                        <div class="form-group">
                            <div class="input-group">
                                <input class="form-control" id="navbarInput-01" name="key" type="search"
                                       placeholder="Search"/>
                                <span class="input-group-btn">
                                    <button type="submit" class="btn"><span class="fui-search"></span></button>
                                </span>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- /.navbar-collapse -->
            </nav>
            <!-- /navbar -->
        </div>
    </div>
    <!-- /row -->

    
    <script src="/flat_ui/js/vendor/jquery.min.js"></script>
    <script src="/flat_ui/js/flat-ui.min.js"></script>
    <script src="/js/heart_beat.js"></script>


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
                        <td><a href="/monitoring/ac85eac37c5549f78e619a24494dc916.hb">${info.id}</a></td>
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
                        <td></td>
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



    
    <div class="row">
        <div class="col-md-12">
            <hr/>
            <div class="pull-right" style="margin-top: -10px;">
                <a target="_blank" href="https://git.oschina.net/mkk/HeartBeat/"
                   style="font-size: 14px;text-decoration: underline;color: #555555;">HeartBeat&nbsp;
                    1.0.1</a>
            </div>
            <div style="font-size: 14px;margin-top: -10px;">
                <a href="?__locale=zh_CN">中文</a>&nbsp;
                <a href="?__locale=en_US">EN</a>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span></button>
                <h4 class="modal-title" id="myModalLabel"></h4>
            </div>
            <div class="modal-body">
                <div id="modalContainer"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="modalConfirmBtn">确认</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>