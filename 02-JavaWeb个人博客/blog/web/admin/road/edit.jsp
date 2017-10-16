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

				<c:if test="${not empty roadItem}">
					<tr>
						<td width="20%" align="center" bgColor="#f5fafe" class="ta_01">
							路线点rid：
						</td>
						<td class="ta_01" bgColor="#ffffff" colspan="3">
							<input disabled="disabled" type="text" value="${roadItem.rid}" class="bg"/>
							<input type="hidden" name="rid" value="${roadItem.rid}" class="bg"/>
						</td>
					</tr>
				</c:if>
				
				<tr>
					<td width="20%" align="center" bgColor="#f5fafe" class="ta_01">
						路线点标题：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<c:if test="${not empty roadItem}">
							<input type="text" name="title" value="${roadItem.title}" class="bg"/>
						</c:if>
						<c:if test="${empty roadItem}">
							<input type="text" name="title" value="" class="bg"/>
						</c:if>
					</td>
				</tr>

				<tr>
					<td width="20%" align="center" bgColor="#f5fafe" class="ta_01">
						路线点内容：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<c:if test="${not empty roadItem}">
							<input style="width: 50%" type="text" name="content" value="${roadItem.content}" class="bg"/>
						</c:if>
						<c:if test="${empty roadItem}">
							<input style="width: 50%" type="text" name="content" value="" class="bg"/>
						</c:if>
					</td>
				</tr>

				<tr>
					<td width="20%" align="center" bgColor="#f5fafe" class="ta_01">
						路线点时间：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<c:if test="${not empty roadItem}">
							<input type="date" name="date" value="${roadItem.date}" class="bg"/>
						</c:if>
						<c:if test="${empty roadItem}">
							<input type="date" name="date" value="" class="bg"/>
						</c:if>
					</td>
				</tr>

				<tr>
					<td width="20%" align="center" bgColor="#f5fafe" class="ta_01">
						路线点年份：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<c:if test="${not empty roadItem}">
							<input type="text" name="year" value="${roadItem.year}" class="bg"/>
						</c:if>
						<c:if test="${empty roadItem}">
							<input type="text" name="year" value="" class="bg"/>
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