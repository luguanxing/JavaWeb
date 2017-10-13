<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/6
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
	<head>
		<title>主页</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<!-- 共同模块 -->
		<%@include file="commons-files.jsp" %>
		<script src="/main/js/owl.carousel.js" type="text/javascript"></script>
		<script src="/main/js/responsiveslides.min.js" type="text/javascript"></script>
		<script src="/main/js/jquery.flexisel.js" type="text/javascript"></script>
		<script type="text/javascript">
			jQuery(document).ready(function ($) {
				$(".scroll").click(function (event) {
					event.preventDefault();
					$('html,body').animate({scrollTop: $(this.hash).offset().top}, 900);
				});
			});
		</script>
	</head>
	<body>

		<%@include file="header.jsp" %>

		<div class="banner">
			<div class="container">
				<div class="banner-inner">
					<div class="callbacks_container">
						<ul class="rslides callbacks callbacks1" id="slider4">
							<li class="" style="display: block; float: none; position: absolute; opacity: 0; z-index: 1; transition: opacity 500ms ease-in-out;" id="callbacks1_s0">
								<div class="banner-info">
									<h3 style="color: white">疾如风，徐如林，侵掠如火，不动如山.</h3>
									<p>《孙子兵法》</p>
								</div>
							</li>
							<li class="" style="display: block; float: none; position: absolute; opacity: 0; z-index: 1; transition: opacity 500ms ease-in-out;" id="callbacks1_s2">
								<div class="banner-info">
									<h3 style="color: white">强者的独裁便成为最强者</h3>
									<p>希特勒</p>
								</div>
							</li>
						</ul>
						<ul class="callbacks_tabs callbacks1_tabs">
							<li class="callbacks1_s1"></li>
							<li class="callbacks1_s2"></li>
						</ul>
					</div>

					<script>
						// You can also use "$(window).load(function() {"
						$(function () {
							// Slideshow 4
							$("#slider4").responsiveSlides({
								auto: true,
								pager: true,
								nav: false,
								speed: 500,
								namespace: "callbacks",
								before: function () {
									$('.events').append("<li>before event fired.</li>");
								},
								after: function () {
									$('.events').append("<li>after event fired.</li>");
								}
							});

						});
					</script>
				</div>
			</div>
		</div>

		<div class="main-content">
			<div class="container">
				<div class="mag-inner">
					<div style="margin-bottom: 5%;" class="col-md-8 mag-innert-left jghdl">
						<div class="issues">
							<iframe width="100%" id="content" frameborder="0" src="/index?method=readIndexContent"></iframe>
						</div>
					</div>

					<div style="margin-bottom: 5%;" class="col-md-4 col-sm-6 col-xs-12 mag-inner-right">
						<div class="connect">
							<h4 class="side"><i class="glyphicon glyphicon-book" aria-hidden="true"></i>最新项目</h4>
							<ul class="stay">
								<c:forEach items="${projects}" var="project" varStatus="vs">
									<c:if test="${vs.count%5==1}"><li class="c5-element-facebook"></c:if>
									<c:if test="${vs.count%5==2}"><li class="c5-element-dribble"></c:if>
									<c:if test="${vs.count%5==3}"><li class="c5-element-gg"></c:if>
									<c:if test="${vs.count%5==4}"><li class="c5-element-twitter"></c:if>
										<a href="#">
											<span class="icon"></span>
											<h5>${project.date}</h5>
											<span class="text">${project.title}</span>
										</a>
									</li>
								</c:forEach>
								<div class="side">
									<div class="gallery-images">
										<div class="course_demo1">
											<div class="nbs-flexisel-container">
												<div class="nbs-flexisel-inner">
													<div class="nbs-flexisel-container">
														<div class="nbs-flexisel-inner">
															<ul id="flexiselDemo1" class="nbs-flexisel-ul" style="left: -72.3333px;">
																<c:forEach items="${projects}" var="project" varStatus="vs">
																	<li class="nbs-flexisel-item" style="width: 72.3333px;">
																		<a target="_blank" href="/project?method=detail&pid=${project.pid}">
																			<img src="${project.imagepath}" alt="${project.title}">
																		</a>
																	</li>
																</c:forEach>
															</ul>
															<div class="nbs-flexisel-nav-left" style="top: -5px;"></div>
															<div class="nbs-flexisel-nav-right" style="top: -5px;"></div>
														</div>
													</div>
													<div class="nbs-flexisel-nav-left" style="top: 60px;"></div>
													<div class="nbs-flexisel-nav-right" style="top: 60px;"></div>
												</div>
											</div>
										</div>
										<script type="text/javascript">
											$(window).load(function () {
												$("#flexiselDemo1").flexisel({
													visibleItems: 3,
													animationSpeed: 1000,
													autoPlay: true,
													autoPlaySpeed: 3000,
													pauseOnHover: true,
													enableResponsiveBreakpoints: true,
													responsiveBreakpoints: {
														portrait: {
															changePoint: 480,
															visibleItems: 2
														},
														landscape: {
															changePoint: 640,
															visibleItems: 2
														},
														tablet: {
															changePoint: 768,
															visibleItems: 3
														}
													}
												});

											});
										</script>
									</div>
								</div>
							</ul>
						</div>
					</div>

					<div style="margin-bottom: 5%;" class="col-md-4 col-sm-6 col-xs-12 mag-inner-right pull-right">
						<div class="connect">
							<h4 class="side"><i class="glyphicon glyphicon-comment" aria-hidden="true"></i>个人简介</h4>
							<ul class="stay">
								<div class="editor-pics" style="width: 90%; margin-left: 5%">
									<div class="item-details" style="width: 100%">
										<center>
											<div class="glyphicon glyphicon-link">GitHub：<a target="_blank" href="https://github.com/luguanxing">链接</a></div>
											<br/><br/>
											<div class="glyphicon glyphicon-bookmark">兴趣：JavaEE、云计算</div>
											<br/><br/>
											<div class="glyphicon glyphicon-star">所在地：中国</div>
										</center>
									</div>
									<div class="clearfix"></div>
								</div>
							</ul>
						</div>
					</div>

					<div class="clearfix"></div>
				</div>
			</div>
		</div>

		<%@include file="footer.jsp" %>

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
