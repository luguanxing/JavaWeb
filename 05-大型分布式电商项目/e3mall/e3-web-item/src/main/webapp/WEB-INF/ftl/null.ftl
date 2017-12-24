<html>
	<head>
		<title>student</title>
	</head>
	<body>
		空值显示:${var!}<br>
		空值默认显示:${var!"i am null"}<br>
		判断空值:
			<#if var??>
				var有值->val
			<#else>
				var无值->null
			</#if>
	</body>
</html>