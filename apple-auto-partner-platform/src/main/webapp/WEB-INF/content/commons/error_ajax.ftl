<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<title>操作提示</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />
</head>
<body>
<script type="text/javascript">
$().ready( function() {
	<@compress single_line = true>
		pop_error("操作失败", "${(FLASH_MESSAGE.content)!'操作失败'}", function() {
			window.history.back();
		}, false);
	</@compress>
});
</script>

</body>
</html>