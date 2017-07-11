<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>操作提示</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />
</head>
<body>
<script type="text/javascript">
$().ready( function() {
		
	<@compress single_line = true>
		pop_succeed("操作成功", "${(FLASH_MESSAGE.content)!'操作成功!'}", function() {
			art.dialog.close();
			art.dialog.parent.callback();
		}, false);
	</@compress>

});
</script>

</body>
</html>