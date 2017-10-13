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
		<title>项目</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- 共同模块 -->
		<%@include file="commons-files.jsp"%>
	</head>
	<body>
		
		<%@include file="header.jsp"%>

		<div class="contact">
			<div class="container">

				<h3 class="mytitle">编程项目</h3>
				
				
				<div class="mag-bottom">
					<div class="grid">

						<c:forEach items="${pagebean.data}" var="project" varStatus="vs">
							<div class=" col-lg-4  col-md-4 col-sm-6   col-xs-12">
								<div class="myproject myarticle col-lg-10 col-lg-offset-1  col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1  col-xs-12">
									<h4 class="side" style="border-radius: 5pt 5pt 0 0;"><i class="glyphicon glyphicon-book" aria-hidden="true"></i>${project.title}</h4>
									<center>
										<a target="_blank" href="/project?method=detail&amp;pid=${project.pid}">
											<img class="img-responsive lot" src="${project.imagepath}" alt="">
										</a>
									</center>
									<div class="m-b-text">
										<p>日期:<span class="badge badge-primary pull-right" style="color: white">${project.date}</span></p>
										<p>点击量:<span class="badge badge-success  pull-right" style="color: white">${project.click}</span></p>
										<h5>
											<a target="_blank" style="margin: 3%;" class="more label label-primary" href="/project?method=detail&amp;pid=${project.pid}">详情</a>
										</h5>
									</div>
								</div>
							</div>
						</c:forEach>
						
						<div class="clearfix"></div>
						
					</div>
				</div>

				<center style="margin-bottom: 15px;">
					<ul class="pagination  pagination-lg">
						<c:if test="${pagebean.pageNumber == 1}">
							<li class="disabled"><a href="javascript:void(0)">前一页</a></li>
						</c:if>
						<c:if test="${pagebean.pageNumber != 1}">
							<li><a href="${pageContext.request.contextPath}/project?pageNumber=${pagebean.pageNumber-1}">前一页</a></li>
						</c:if>
						<c:forEach begin="1" end="${pagebean.totalPage}" var="n">
							<c:if test="${pagebean.pageNumber == n}">
								<li class="active"><a href="javascript:void(0)">${n}</a></li>
							</c:if>
							<c:if test="${pagebean.pageNumber != n}">
								<li><a href="${pageContext.request.contextPath}project?pageNumber=${n}">${n}</a></li>
							</c:if>
						</c:forEach>
						<c:if test="${pagebean.pageNumber == pagebean.totalPage}">
							<li class="disabled"><a href="javascript:void(0)">后一页</a></li>
						</c:if>
						<c:if test="${pagebean.pageNumber != pagebean.totalPage}">
							<li><a href="${pageContext.request.contextPath}/project?pageNumber=${pagebean.pageNumber+1}">后一页</a></li>
						</c:if>
					</ul>
				</center>
				
			</div>
		</div>

		
		<%@include file="footer.jsp"%>
		
	</body>
	
	<script>
		var sum = 500;
		$(".myproject").each(function(){
			$(this).animate({
				opacity: 1,
			}, sum);
			sum += 350;
		});
	</script>
	
</html>
