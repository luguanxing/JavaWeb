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
		<title>项目详情</title>
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

				<div class="technology myarticleinfo">
					
					<h2 class="tittle" style="border-radius: 5pt 5pt 0 0; margin-bottom: 0;">
						<i class="glyphicon glyphicon-file" aria-hidden="true"></i>
						${project.title}
					</h2>

					<div class="issues" style="margin: 0;">
						<center>
							<div class="col-md-12 mag-innert-left">
								<div class="banner-bottom-left-grids">
									<div class="single-left-grid">
										<div class="single-bottom">
											<ul style="margin: 0; padding: 0; border: 0">
												<img class="img-responsive lot" style="width: 50%; margin-bottom: 10px;" src="${project.imagepath}" alt="">
												<li>
													日期:<div class="badge badge-primary">${project.date}</div>
												</li>
												<li>
													点击量:<div class="badge badge-success">${project.click}</div>
												</li>
												<li>
													<a target="_blank" href=" ${project.link}">
														<div style="background-color: #4a75c3;" class="badge badge-success">查看源码</div>
													</a>
												</li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</center>
						
						<iframe width="100%" id="content" frameborder="0" src="/project?method=readDescription&pid=${project.pid}"></iframe>
					</div>
					
					<div class="clearfix"></div>
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
