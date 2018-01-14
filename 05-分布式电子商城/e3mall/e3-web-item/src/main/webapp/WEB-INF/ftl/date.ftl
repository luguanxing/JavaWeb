<html>
	<head>
		<title>student</title>
	</head>
	<body>
		当前日期:${date?date}<br>
		当前时间:${date?time}<br>
		当前日期日期:${date?datetime}<br>
		自定义当前日期:${date?string("yyyy/MM/DD HH:mm:ss")}<br>
		自定义当前日期:${date?string("yyyy年MM月DD日 HH时mm分ss秒")}<br>
	</body>
</html>