<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css"/>
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}

			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}

			.container .row div {
				/* position:relative;
				float:left; */
			}

			font {
				color: #666;
				font-size: 22px;
				font-weight: normal;
				padding-right: 17px;
			}
		</style>
	</head>
	<body>


		<%@include file="/jsp/head.jsp"%>


		<div class="container"
			 style="width:100%;height:380px;background:#fff url('${pageContext.request.contextPath}/images/loginbg.jpg') no-repeat; background-size: contain;">
			<div class="row">
				<div class="col-md-5"></div>

				<div class="col-md-6">
					<div style="opacity: 0.95; width:100%;border:1px solid #E7E7E7;padding:20px 0 20px 30px;border-radius:5px;margin-top:30px;background:#fff;">
						<font>会员登录</font> <span style='color:red'>${msg }</span>

						<div>&nbsp;</div>
						<form class="form-horizontal" action="${pageContext.request.contextPath }/user" method="post">
							<input type="hidden" name="method" value="login">

							<div class="form-group">
								<label for="username" class="col-sm-2 control-label">用户名</label>
								<div class="col-sm-6">
									<input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username">
								</div>
							</div>
							<div class="form-group">
								<label for="password" class="col-sm-2 control-label">密码</label>
								<div class="col-sm-6">
									<input type="password" class="form-control" id="password" placeholder="请输入密码" name="password">
								</div>
							</div>
							<div class="form-group">
								<label for="captacha" class="col-sm-2 control-label">验证码</label>
								<div class="col-sm-3">
									<input name="inputCaptcha" type="text" class="form-control" id="captacha" placeholder="请输入验证码">
								</div>
								<div class="col-sm-3">
									<a style="cursor: pointer" onclick="changeCaptcha()">
										<img id="captcha" src="${pageContext.request.contextPath}/getCaptcha"/>
									</a>
								</div>

							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<div class="checkbox">
										<label>
											<input type="checkbox"> 自动登录
										</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label>
											<input type="checkbox" name="savename" value="ok"> 记住用户名
										</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<input type="submit" width="100" value="登录" name="submit" border="0"
										   style="background: url('${pageContext.request.contextPath}/images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
												   height:35px;width:100px;color:white;">
								</div>
							</div>
						</form>
					</div>
				</div>

				<div class="col-md-1"></div>
			</div>
		</div>

		<%@include file="/jsp/foot.jsp"%>
	</body>
	<script type="text/javascript">
		var cookieUsername = "${cookie.saveName.value}";
		document.getElementById("username").value = decodeURI(cookieUsername);
		
		function changeCaptcha() {
			//加上时间戳避免读缓冲
			var timestamp = (new Date()).valueOf();
			$("#captcha").attr("src", "/getCaptcha?t="+timestamp);
		}
		
	</script>
</html>