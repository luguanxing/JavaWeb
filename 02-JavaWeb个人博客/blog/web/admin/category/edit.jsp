<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</HEAD>
	
	<body>
		<form id="userAction_save_do" name="Form1" action="${pageContext.request.contextPath}/admin?method=${method}" method="post">
			<input type="hidden" name="method" value="addCategory">
			&nbsp;
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>${type}</STRONG>
						</strong>
					</td>
				</tr>

				<c:if test="${not empty category}">
					<tr>
						<td width="20%" align="center" bgColor="#f5fafe" class="ta_01">
							项目分类cid：
						</td>
						<td class="ta_01" bgColor="#ffffff" colspan="3">
							<input disabled="disabled" type="text" value="${category.cid}" class="bg"/>
							<input type="hidden" name="cid" value="${category.cid}" class="bg"/>
						</td>
					</tr>
				</c:if>
				
				<tr>
					<td width="20%" align="center" bgColor="#f5fafe" class="ta_01">
						项目分类名称：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<c:if test="${not empty category}">
							<input type="text" name="name" value="${category.name}" class="bg"/>
						</c:if>
						<c:if test="${empty category}">
							<input type="text" name="name" value="" class="bg"/>
						</c:if>
					</td>
				</tr>

				<tr>
					<td width="20%" align="center" bgColor="#f5fafe" class="ta_01">
						项目分类权重：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<c:if test="${not empty category}">
							<input type="number" name="order" value="${category.order}" class="bg"/>
						</c:if>
						<c:if test="${empty category}">
							<input type="number" name="order" value="" class="bg"/>
						</c:if>
					</td>
				</tr>
				
				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<button type="submit" id="userAction_save_do_submit" value="确定" class="button_ok">
							&#30830;&#23450;
						</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<INPUT class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
						<span id="Label1"></span>
					</td>
				</tr>
			</table>
		</form>
	</body>
</HTML>