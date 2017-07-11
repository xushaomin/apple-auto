<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>添加评论</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<!--<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>-->


<script type="text/javascript" src="/js/jQuery/jquery-ui.js"></script>
<script type="text/javascript" src="/js/timepicker/jquery-ui-timepicker-addon.js"></script>
<link type="text/css" rel="stylesheet" href="/css/jQuery/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="/css/jQuery/jquery.bt.css" />
<style type="text/css">
.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; } .ui-timepicker-div dl { text-align: left; } .ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; } .ui-timepicker-div dl dd { margin: 0 10px 10px 65px; } .ui-timepicker-div td { font-size: 90%; } .ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; } .ui_tpicker_hour_label,.ui_tpicker_minute_label,.ui_tpicker_second_label, .ui_tpicker_millisec_label,.ui_tpicker_time_label{padding-left:20px} 
</style>
</head>
<body>
<form id="inputForm" method="post" action="/comment/save">
	<input type="hidden" value="${(comment.commentId)!0}"  name="commentId" />	
	<input type="hidden" value="${comment.modelId}"  name="modelId" />	
	<input type="hidden" value="${(comment.bussinessId)!0}"  name="bussinessId" />	
	<input type="hidden" value="${(comment.isSelfDelete)!0}"  name="isSelfDelete" />	
	<input type="hidden" value="${(comment.view)!1}"  name="view" />	
	<input type="hidden" value="${(comment.hot)!0}"  name="hot" />	
	<input type="hidden" value="${(comment.bak)!''}"  name="bak" />	
	<input type="hidden" value="${(comment.userId)!''}"  name="userId" />	
	
    <div id="auditTab" class="pop_main" style="border: 0px;">
       <div class="pop_grouprmation_mod">
            <ul class="search_list_n clearfix">
            	<li class="clear" style="visibility:hidden">
                    <label class="n">是否匿名：</label>
                    <span class="c_check_n">
	           			<input type="checkbox" id="anonymous" name="anonymous" value="1" <#if comment.anonymous?? && comment.anonymous == 1>checked</#if>/>
						<label for="anonymous">匿名</label>
					</span>
            	</li>
            	<li class="clear">
                    <label class="n"><span class="red">*</span> 评论时间：</label>
                    <#if publishTime??>
	        			<input class="c_input_text_n required" type="text" name="commentDate" id="commentTime" readonly size="40" maxlength="255" value="${(publishTime?string('yyyy-MM-dd HH:mm:ss'))!''}" />
                    <#else>
	        			<input class="c_input_text_n required" type="text" name="commentDate" id="commentTime" readonly size="40" maxlength="255" value="${(comment.commentDate?string('yyyy-MM-dd HH:mm:ss'))!''}" />
                    </#if>
            		<span style="color:red">晚于当前时间将进入待发列表</span>
            	</li>
            	<li class="clear">
                	<label class="n"><span class="red">*</span> 评论内容：</label>
                	<span class="textarea_show_num">
                		<textarea class="c_textarea_n wordCount" name="commentContent" id="content" maxlength="200" showCount="contentLen">${(comment.commentContent)!''}</textarea>
                		<span class="in_num_text" style="color:red; position:absolute; right:5px; bottom:-5px;" id="contentLen">0/200</span>
                	</span>
                </li>
            </ul>
        </div>
    </div>
    <div class="pop_footer">
        <a id="btn_pop_submitA" class="btn_pop_submitA" href="javascript:void(0)">确定</a>
        <a id="btn_pop_submitB" class="btn_pop_submitB" href="javascript:void(0)" onclick="art.dialog.close();">取消</a>
    </div>
   
</form>
<script>

	$(function() {
	jQuery("#commentTime").datetimepicker({
			changeYear : true,
			changeMonth : true,
			showSecond : true,
			timeFormat : "HH:mm:ss",
			dateFormat : "yy-mm-dd",
			stepHour : 1,
			stepMinute : 1,
			stepSecond : 1
		});
	});
		


    var $inputForm = $("#inputForm");
		
	// 表单验证
	$inputForm.validate({
		rules: {
			
		},
		messages: {
			
		},
		submitHandler:function(form){
            form.submit();
        }
	});
	
	$("#btn_pop_submitA").click(function(){
 		//$inputForm.submit();
 		var  cont = $("#content").val();
 		var commentTime = $("#commentTime").val();
 		
 		if(cont==''){
 		  pop_error("表单验证", "评论内容不能为空!",function() {} ,false);
 		  return false;
 		}
 		if(commentTime==''){
 		  pop_error("表单验证", "发布时间不能为空!",function() {} ,false);
 		  return false;
 		}
 		
 		
 		if($inputForm.valid()){
 			 $("#btn_pop_submitA").attr("disabled",true);
 			 var sss = $inputForm.serialize();
	 		 $.ajax({
				type : "post",
				url : "/comment/save",
				data : $inputForm.serialize(),
				dataType: "json",
				cache : false,
				success: function(data){
					if(data.type == 'success') {
						pop_succeed("操作成功", "操作成功", function() {
							art.dialog.close();
							art.dialog.opener.callback();
						}, false);
					}
					else {
						pop_error("操作失败", data.content,function() {
							 $("#btn_pop_submitA").attr("disabled",false);
						} ,false);
					}
				}					
			});
 		}
 		
	});
</script>

</body>
</html>
