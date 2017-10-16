<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/admin/css/Style1.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript">
			function addArticleUI() {
				window.location.href = "${pageContext.request.contextPath}/admin?method=addArticleUI";
			}
		</script>
	</HEAD>
	<body>
		<br>
		<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3">
						<strong>文章列表</strong>
					</TD>
				</tr>
				<tr>
					<td class="ta_01" align="right">
						<button type="button" id="add" name="add" value="添加" class="button_add" onclick="addArticleUI()">
							&#28155;&#21152;
						</button>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all" bordercolor="gray" border="1" id="DataGrid1" style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">
								<td align="center" width="15%">
									时间
								</td>
								<td align="center" width="15%">
									标题
								</td>
								<td align="center" width="40%">
									点击量
								</td>
								<td width="15%" align="center">
									编辑
								</td>
								<td width="15%" align="center">
									删除
								</td>
							</tr>
							<c:forEach items="${pagebean.data}" var="article">
								<tr onmouseover="this.style.backgroundColor = 'white'" onmouseout="this.style.backgroundColor = '#F5FAFE';">
									<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="15%">
										${article.date}
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="15%">
										<c:out value="${article.title}"/>
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="40%">
											<c:out value="${article.click}"/>
									</td>
									<td align="center" style="HEIGHT: 22px">
										<a href="${ pageContext.request.contextPath }/admin?method=editArticleUI&aid=${article.aid}">
											<img src="${pageContext.request.contextPath}/admin/images/i_edit.gif" width="16" height="16" border="0" style="CURSOR: hand">
										</a>
									</td>
									<td align="center" style="HEIGHT: 22px">
										<a href="${ pageContext.request.contextPath }/admin?method=deleteArticle&aid=${article.aid}">
											<img src="${pageContext.request.contextPath}/admin/images/i_del.gif" width="16" height="16" border="0" style="CURSOR: hand">
										</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr align="center">
					<td colspan="4">
						第${pagebean.pageNumber}/${pagebean.totalPage}页
						<c:if test="${pagebean.pageNumber != 1}">
							<a href="${ pageContext.request.contextPath }/admin?method=listArticle">首页</a>|
							<a href="${ pageContext.request.contextPath}/admin?method=listArticle&pageNumber=${pagebean.pageNumber-1}">上一页</a>
						</c:if>
						<c:if test="${pagebean.pageNumber != pagebean.totalPage}">
							<a href="${ pageContext.request.contextPath }/admin?method=listArticle&pageNumber=${pagebean.pageNumber+1}">下一页</a>
							<a href="${ pageContext.request.contextPath }/admin?method=listArticle&pageNumber=${pagebean.totalPage}">尾页</a>
						</c:if>
					</td>
				</tr>
			</TBODY>
		</table>
	</body>
</HTML>

