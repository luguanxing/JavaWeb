<%@ page language="java" pageEncoding="UTF-8" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>菜单</title>
		<link href="${pageContext.request.contextPath}/admin/css/left.css" rel="stylesheet" type="text/css"/>
		<link rel="StyleSheet" href="${pageContext.request.contextPath}/admin/css/dtree.css" type="text/css"/>
	</head>
	<body>
		<table width="100" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="12"></td>
			</tr>
		</table>
		<table width="100%" border="0">
			<tr>
				<td>
					<div class="dtree">

						<a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>

						<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/dtree.js"></script>
						<script type="text/javascript">

							d = new dTree('d');
							d.add('01', -1, '系统菜单树');
								d.add('0101', '01', '主页管理', '', '', 'mainFrame');
									d.add('010101', '0101', '主页内容编辑', '${pageContext.request.contextPath}/admin?method=editIndexUI', '', 'mainFrame');
								d.add('0102', '01', '路线管理');
									d.add('010201', '0102', '路线列表', '${pageContext.request.contextPath}/', '', 'mainFrame');
									d.add('010202', '0102', '路线添加', '${pageContext.request.contextPath}', '', 'mainFrame');
								d.add('0103', '01', '文章管理');
									d.add('010301', '0103', '文章列表', '${pageContext.request.contextPath}', '', 'mainFrame');
									d.add('010302', '0103', '文章添加', '${pageContext.request.contextPath}', '', 'mainFrame');
								d.add('0104', '01', '项目分类管理');
									d.add('010401', '0104', '项目分类列表', '${pageContext.request.contextPath}', '', 'mainFrame');
									d.add('010402', '0104', '项目分类添加', '${pageContext.request.contextPath}', '', 'mainFrame');									
								d.add('0105', '01', '项目管理');
									d.add('010501', '0105', '项目列表', '${pageContext.request.contextPath}', '', 'mainFrame');
									d.add('010502', '0105', '项目添加', '${pageContext.request.contextPath}', '', 'mainFrame');
								d.add('0106', '01', '评论管理');
									d.add('010601', '0106', '评论列表', '${pageContext.request.contextPath}', '', 'mainFrame');
							
							document.write(d);

						</script>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
