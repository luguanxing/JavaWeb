<html>
	<head>
		<title>student</title>
	</head>
	<body>
		学生信息:<br>
		<table border="1">
			<tr>
				<th>学号:</th>
				<th>姓名:</th>
				<th>年龄:</th>
				<th>住址:</th>
			</tr>
			<#list stuList as stu>
				<tr>
					<td>${stu.id}</td>
					<td>${stu.name}</td>
					<td>${stu.age}</td>
					<td>${stu.address}</td>
				</tr>
			</#list>
		</table>
	</body>
</html>