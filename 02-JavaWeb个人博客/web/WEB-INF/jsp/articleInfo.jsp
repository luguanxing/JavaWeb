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
		<title>文章详情</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<!-- 共同模块 -->
		<%@include file="commons-files.jsp"%>
		<script src="/utf8/ueditor.parse.js"></script>

	</head>
	<body>
		
		<%@include file="header.jsp"%>
		
		<div class="contact">
			<div class="container">

				<h3 class="mytitle">文章详情</h3>
				
				<div class="mag-bottom">
					<div class="grid">
						<center>
							<div class="col-md-10 col-md-offset-1 about-grid-left">
								<div class="history">
									<h2>${article.title}</h2>
									<div>
										<p>点击量:<span class="badge badge-success  pull-right" style="color: white">${article.click}</span></p>
										<p>日期:<span class="badge badge-warning pull-right" style="color: white">${article.date}</span></p>
									</div>
									<iframe width="100%" id="content" frameborder="0" src="/article?method=readContent&aid=${article.aid}"></iframe>
								</div>
								<div class="clearfix"></div>
						</center>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
		
		<%@include file="footer.jsp"%>
		
	</body>
	
	<script>

		function autoFixFrame() {
			var ifm= document.getElementById("content");
			var subWeb = document.frames ? document.frames["content"].document : ifm.contentDocument;
			if(ifm != null && subWeb != null) {
				ifm.height = subWeb.body.scrollHeight;
			}
		}

		window.onload = function () {
			autoFixFrame();
		}

		$(window).resize(function () {
			autoFixFrame();
		});

	</script>
	
	
</html>
