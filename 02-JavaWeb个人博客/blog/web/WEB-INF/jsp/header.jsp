<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/6
  Time: 20:18
  顶部模块
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="header" id="home">
		<div class="content white">
			<nav class="navbar navbar-default" role="navigation">
				<div class="container" style="padding: 0 15px;">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
							<span class="sr-only"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="/">Luguanxing</a>
					</div>
	
					<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav">
							<c:if test="${index == 1}">
								<li class="active"><a href="/index">主页</a></li>
							</c:if>
							<c:if test="${index != 1}">
								<li><a href="/index">主页</a></li>
							</c:if>
							
							<c:if test="${index == 2}">
								<li class="active"><a href="/road">路线</a></li>
							</c:if>
							<c:if test="${index != 2}">
								<li><a href="/road">路线</a></li>
							</c:if>
							
							<c:if test="${index == 3}">
								<li class="active"><a href="/article">文章</a></li>
							</c:if>
							<c:if test="${index != 3}">
								<li><a href="/article">文章</a></li>
							</c:if>

							<c:if test="${index == 4}">
								<li class="dropdown active">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">项目<b class="caret"></b></a>
									<ul id="category" class="dropdown-menu">
									</ul>
								</li>
							</c:if>
							<c:if test="${index != 4}">
								<li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">项目<b class="caret"></b></a>
									<ul id="category" class="dropdown-menu">
									</ul>
								</li>
							</c:if>

							<c:if test="${index == 5}">
								<li class="active"><a href="/comment">留言</a></li>
							</c:if>
							<c:if test="${index != 5}">
								<li><a href="/comment">留言</a></li>
							</c:if>
						</ul>
					</div>
					<!--/.navbar-collapse-->
					<!--/.navbar-->
				</div>
			</nav>
		</div>
	</div>

<script type="text/javascript">
	//查询分类,因为是每页都有，所以使用异步获取数据
	$(function () {
		$.post(
			"${pageContext.request.contextPath}/category",
			{"method" : "getCategoryListJson"},
			function (data) {
				$(data).each(function () {
					$("#category").append("<li><a href='${pageContext.request.contextPath}/project?type=" + this.cid + "'>" + this.cname + "</a></li>");
					$("#category").append("<li class='divider'></li>");
				});
			},
			"json"
		);
	})
</script>