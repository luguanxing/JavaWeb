<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html data-dpr="1" style="font-size: 50px;">
	<head>
		<title>新闻采集系统</title>
		<%@ include file="/WEB-INF/tags/css_js"%>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/news/css/newStyle.css">
	</head>
	<body>
		<%@ include file="/WEB-INF/tags/header.jsp"%>
		
		<div style="overflow-x: auto; background: #fff; padding: .4rem .3rem;">
			<h1 class="new_title">${newObj.title}</h1>
			<div class="m-meta">
				<time class="time_src">${newObj.publishDateAndSrc}</time>
				<span class="tag" style="float:right; margin-top: 3%;">${newObj.commentCount}条评论</span>
			</div>
			<div class="m-meta">
				<time class="time_src">
					抓取网页时间 : 
					<fmt:formatDate value="${newObj.crawlerDate}" type="date" pattern="yyyy年MM月dd日  HH:mm:ss"/>
					<a target="_blank" style="color:#4883e1" href="${newObj.url}">查看源网页</a>
				</time>
			</div>
			<div class="newContent">
				${newObj.content}
			</div>
		</div>

		<%@ include file="/WEB-INF/tags/footer.jsp"%>
	</body>
</html>