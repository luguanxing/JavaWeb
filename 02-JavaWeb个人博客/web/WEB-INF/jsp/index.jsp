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
									<h3>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</h3>
									<p>Lorem ipsum dolor sit amet</p>
								</div>
							</li>
							<li class="" style="display: block; float: none; position: absolute; opacity: 0; z-index: 1; transition: opacity 500ms ease-in-out;" id="callbacks1_s1">
								<div class="banner-info">
									<h3>Ut enim ad minima veniam, quis nostrum exercitationem</h3>
									<p>Lorem ipsum dolor sit amet</p>
								</div>
							</li>
							<li class="callbacks1_on" style="display: block; float: left; position: relative; opacity: 1; z-index: 2; transition: opacity 500ms ease-in-out;" id="callbacks1_s2">
								<div class="banner-info">
									<h3>At vero eos et accusamus et iusto odio dignissimos.</h3>
									<p>Lorem ipsum dolor sit amet</p>
								</div>
							</li>
						</ul><ul class="callbacks_tabs callbacks1_tabs"><li class="callbacks1_s1"><a href="#" class="callbacks1_s1">1</a></li><li class="callbacks1_s2"><a href="#" class="callbacks1_s2">2</a></li><li class="callbacks1_s3 callbacks_here"><a href="#" class="callbacks1_s3">3</a></li></ul>
					</div>
					<!--banner-Slider-->
					<script src="js/responsiveslides.min.js"></script>
					<script>
						// You can also use "$(window).load(function() {"
						$(function () {
							// Slideshow 4
							$("#slider4").responsiveSlides({
								auto: true,
								pager: true,
								nav:false,
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
							<h3><a href="#">Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet,
								consectetur, adipisci velit</a></h3>
							<p>Quis autem vel eum iure reprehenderit qui in ea voluptate velit
								esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat
								quo voluptas nulla pariatur autem vel eum iure reprehenderit qui in ea voluptate velit
								esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat
								quo voluptas nulla pariatur quis autem vel eum iure reprehenderit qui in ea voluptate velit
								esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat
								quo voluptas nulla pariatur autem vel eum iure reprehenderit qui in ea voluptate velit
								esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat
								quo voluptas nulla pariatur vel eum iure reprehenderit qui in ea voluptate velit
								esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat.</p>
						</div>
					</div>

					<div style="margin-bottom: 5%;" class="col-md-4 col-sm-6 col-xs-12 mag-inner-right">
						<div class="connect">
							<h4 class="side"><i class="glyphicon glyphicon-book" aria-hidden="true"></i>项目代码</h4>
							<ul class="stay">
								<li class="c5-element-facebook"><a href="#"><span class="icon"></span><h5>700</h5><span class="text">Followers</span></a></li>
								<li class="c5-element-twitter"><a href="#"><span class="icon1"></span><h5>201</h5><span class="text">Followers</span></a></li>
								<li class="c5-element-gg"><a href="#"><span class="icon2"></span><h5>111</h5><span class="text">Followers</span></a></li>
								<li class="c5-element-dribble"><a href="#"><span class="icon3"></span><h5>99</h5><span class="text">Followers</span></a></li>
								<div class="side">
									<div class="gallery-images">
										<div class="course_demo1">
											<div class="nbs-flexisel-container">
												<div class="nbs-flexisel-inner">
													<div class="nbs-flexisel-container">
														<div class="nbs-flexisel-inner">
															<ul id="flexiselDemo1" class="nbs-flexisel-ul" style="left: -72.3333px;">
																<li class="nbs-flexisel-item" style="width: 72.3333px;">
																	<a href="single.html"><img src="/images/trump2.jpg" alt=""></a>
																</li>
																<li class="nbs-flexisel-item" style="width: 72.3333px;">
																	<a href="single.html"><img src="/images/trump.jpg" alt=""></a>
																</li>
																<li class="nbs-flexisel-item" style="width: 72.3333px;">
																	<a href="single.html"><img src="/images/trump2.jpg" alt=""></a>
																</li>
																<li class="nbs-flexisel-item" style="width: 72.3333px;">
																	<a href="single.html"><img src="/images/trump.jpg" alt=""></a>
																</li>
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
							<h4 class="side"><i class="glyphicon glyphicon-comment" aria-hidden="true"></i>联系方式</h4>
							<ul class="stay">
								<div class="editor-pics">
									<div class="col-md-3 item-pic">
										<img src="/images/trump.jpg.jpg" class="img-responsive" alt="">
									</div>
									<div class="col-md-9 item-details">
										<h5 class="inner two"><a href="single.html">Barack Hussein Obama , President Of America.</a></h5>
										<div class="td-post-date two">Combined with a handful.</div>
										<div class="td-post-date two">Combined with a handful.</div>
										<div class="td-post-date two">Combined with a handful.</div>
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
</html>
