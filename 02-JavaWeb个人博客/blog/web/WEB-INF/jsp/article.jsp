<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/6
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>文章</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- 共同模块 -->
		<%@include file="commons-files.jsp"%>
	</head>
	<body>
		
		<%@include file="header.jsp"%>

		<div class="contact">
			<div class="container">

				<h3 class="mytitle">文章和笔记</h3>
				
				
				<div class="mag-bottom">
					<div class="grid">

						
						<center>
							<c:forEach items="${pagebean.data}" var="article" varStatus="vs">
								<div class="col-md-10 col-md-offset-1 about-grid-left myempty" style="opacity: 0">
									<div class="history">
										<h2>${article.title}</h2>
										<div>
											<p>点击量:<span class="badge badge-success  pull-right" style="color: white">${article.click}</span></p>
											<p>日期:<span class="badge badge-warning pull-right" style="color: white">${article.date}</span></p>
										</div>
										<iframe scrolling="no" width="100%" id="content" frameborder="0" src="/article?method=readContent&aid=${article.aid}">
										</iframe>
										<div style="margin:3.5% 0; height: 1%">
											<p style="margin: 0">
												<a style="margin:0; float: right;" target="_blank" class="more label label-primary" href="${pageContext.request.contextPath}/article?method=read&aid=${article.aid}">详细内容</a>
											</p>
										</div>
									</div>
								</div>
							</c:forEach>
						</center>
						

						<div class="clearfix"></div>
					</div>
				</div>

				<center style="margin-bottom: 15px;">
					<ul class="pagination  pagination-lg">
						<c:if test="${pagebean.pageNumber == 1}">
							<li class="disabled"><a href="javascript:void(0)">前一页</a></li>
						</c:if>
						<c:if test="${pagebean.pageNumber != 1}">
							<li><a href="${pageContext.request.contextPath}/article?pageNumber=${pagebean.pageNumber-1}">前一页</a></li>
						</c:if>
						<c:forEach begin="1" end="${pagebean.totalPage}" var="n">
							<c:if test="${pagebean.pageNumber == n}">
								<li class="active"><a href="javascript:void(0)">${n}</a></li>
							</c:if>
							<c:if test="${pagebean.pageNumber != n}">
								<li><a href="${pageContext.request.contextPath}article?pageNumber=${n}">${n}</a></li>
							</c:if>
						</c:forEach>
						<c:if test="${pagebean.pageNumber == pagebean.totalPage}">
							<li class="disabled"><a href="javascript:void(0)">后一页</a></li>
						</c:if>
						<c:if test="${pagebean.pageNumber != pagebean.totalPage}">
							<li><a href="${pageContext.request.contextPath}/article?pageNumber=${pagebean.pageNumber+1}">后一页</a></li>
						</c:if>
					</ul>
				</center>
				
			</div>
		</div>

		
		<%@include file="footer.jsp"%>
		
	</body>
	
	<script>
		var sum = 700;
		$(".myempty").each(function(){
			$(this).animate({
				opacity: 1,
			}, sum);
			sum += 700;
		});
	</script>
	
</html>
