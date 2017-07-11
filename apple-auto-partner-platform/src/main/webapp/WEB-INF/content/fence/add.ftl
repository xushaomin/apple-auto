<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>添加渠道</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<link rel="stylesheet" type="text/css" href="/js/swfupload/css/default.css" />

<script type="text/javascript" src="/js/swfupload/swfupload.js"></script>
<script type="text/javascript" src="/js/swfupload/plugins/fileprogress.js"></script>
<script type="text/javascript" src="/js/swfupload/plugins/handlers.js"></script>

<script type="text/javascript">

$().ready(function() {

	var $inputForm = $("#inputForm");
	
	// 表单验证
	$inputForm.validate({
		rules: {
			"channelCode": {
				required: true,
				remote: "check_channel_code"
			}
		},
		messages: {
			"channelCode": {
				required: "必填",
				remote: "编码已存在"
			}
		},
		submitHandler:function(form){
            form.submit();
        }
	});
	
	$("#btn_pop_submitA").click(function(){
 		$inputForm.submit();
	});
	
});

</script>


</head>


<body>
<form id="inputForm" method="post" action="save">
	
    <div id="auditTab" class="pop_main" style="width:400px;border: 0px solid;">

       <div class="pop_information_mod">
            <ul class="pop_list merchant_type_add">
            
                	<li class="clearfix">
                		<label for="modelId require" class="tit">名称：<span class=" red">*</span></label>
              			<input id="modelId" class="c_input_text required" type="text" maxlength="30" name="modelId" style="width:215px;"></input>
               		</li>
               		
               		<li class="clearfix">
                		<label for="channelCode require" class="tit">编码：<span class=" red">*</span></label>
              			<input id="bussinessId" class="c_input_text required" type="text" maxlength="20" name="bussinessId" style="width:215px;"></input>
               		</li>
               		
               		<li class="clearfix">
                		<label for="channelCode require" class="tit">编码：<span class=" red">*</span></label>
              			<input id="commentId" class="c_input_text required" type="text" maxlength="20" name="commentId" style="width:215px;"></input>
               		</li>
                	
            </ul>

        </div>

    </div>
    <div class="pop_footer">
        <a id="btn_pop_submitA" class="btn_pop_submitA" href="javascript:void(0)">确定</a>
        <a id="btn_pop_submitB" class="btn_pop_submitB" href="javascript:void(0)" onclick="art.dialog.close();">取消</a>
    </div>


</form>


</body>
</html>