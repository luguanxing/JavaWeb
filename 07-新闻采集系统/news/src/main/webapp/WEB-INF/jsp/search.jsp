<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html data-dpr="1" style="font-size: 50px;">
	<head>
		<title>新闻采集系统</title>
		<%@ include file="/WEB-INF/tags/css_js"%>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/news/css/list.css">
		<style>
			em {
				color : red;
	    		font-size: .3rem;
	    		line-height: .4rem;
	    		font-weight: 400;
				font-style: normal;
			}
		</style>
	</head>
	<body>
		<%@ include file="/WEB-INF/tags/header.jsp"%>
		
		<div class="m-search">
			<div id="searchForm">
				<input type="text" value="${keyword}" id="searchText" placeholder="输入搜索内容..." />
				<button onclick="search()">搜索</button>
			</div>
		</div>
		
		<div class="m-zone">
			<div class="m-tit m-tit-1">
				<a href="${pageContext.request.contextPath}/index.html">搜索内容</a>
			</div>
			<c:if test="${empty searchNews}">
				<center>
					<p style="padding:20px; margin:0;">没有找到对应的内容</p>
				</center>
			</c:if>

			<c:if test="${not empty searchNews}">
				<c:forEach items="${searchNews}" var="newObj">
					<div class="m-list-1">
						<a class="j-link" target="_blank" href="${pageContext.request.contextPath}/new/${newObj.id}.html">
						<div class="itm">
							<h3>${newObj.title}</h3>
							<div class="m-meta">
								<span class="tag">${newObj.commentCount}条评论</span>
								<time class="nfw-time">
									${fn:substring(newObj.publishDateAndSrc, 0, 20)}
								</time>
							</div>								
						</div>
					  </a>
					</div>
				</c:forEach>
			</c:if>
		</div>
		
		<div class="m-pager">
			<center>
				<c:if test="${currentPage != 1}">
					<a class="link-next" style="background:white" href="${pageContext.request.contextPath}/search/${currentPage-1}.html">上一页</a>
				</c:if>
				<c:if test="${currentPage == 1}">
					<a class="link-next">上一页</a>
				</c:if>
				<span style="font-size:.3rem; color:#4883e1; margin-bottom: 3%;">第${currentPage}/${totalPages}页</span>
				<c:if test="${currentPage < totalPages}">
					<a class="link-next" style="background:white" href="${pageContext.request.contextPath}/search/${currentPage+1}.html">下一页</a>
				</c:if>
				<c:if test="${currentPage >= totalPages}">
					<a class="link-next">下一页</a>
				</c:if>
			</center>
		</div>

		<%@ include file="/WEB-INF/tags/footer.jsp"%>
		
	</body>
</html>