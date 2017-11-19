<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en" class="login-content" data-ng-app="materialAdmin">
	<head>
		<meta charset="UTF-8"> 
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="Generator" content="EditPlus®">
		<meta name="Author" content="">
		<meta name="Keywords" content="">
		<meta name="Description" content="">
		<title>管理员登录界面</title>
		<!-- Vendor CSS -->
		<link href="${pageContext.request.contextPath}/static/login/css/material-design-iconic-font/css/material-design-iconic-font.min.css" rel="stylesheet" type="text/css">
		<!-- CSS -->
		<link href="${pageContext.request.contextPath}/static/login/css/app.min.1.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
			function submitData() {
				var username = $("#username").val();
				var password = $("#password").val();
				if (username == "") {
					alert("用户名为空");
					return;
				}
				if (password == "") {
					alert("密码为空");
					return;
				}
				$.post(
					"${pageContext.request.contextPath}/management/login.do",
					{
						username: username,
						password: password
					},
					function(result) {
						if (result.success) {
							alert("登录成功");
						} else {
							alert(result.errMsg);
						}
					},
					"json"
				);
			}
		</script>
	</head>
	<body class="login-content" data-ng-controller="loginCtrl as lctrl">
	
		<div class="lc-block" id="l-login"
			data-ng-class="{'toggled':lctrl.login === 1}">
			<h1 class="lean">Login</h1>
	
			<div class="input-group m-b-20">
				<span class="input-group-addon"> <i class="zmdi zmdi-account"></i>
				</span>
				<div class="fg-line">
					<input type="text" id="username" name="username"
						class="form-control" placeholder="username" regex="^\w{3,16}$" />
				</div>
			</div>
	
			<div class="input-group m-b-20">
				<span class="input-group-addon"> <i class="zmdi zmdi-male"></i>
				</span>
				<div class="fg-line">
					<input type="password" id="password" name="password"
						class="form-control" placeholder="password" regex="^\w+" />
				</div>
			</div>
	
			<div class="clearfix"></div>
	
			<div>SSM-CMS管理系统 author@Luguanxing</div>
	
	
			<a href="javascript:submitData()" class="btn btn-login btn-danger btn-float">
				<i class="zmdi zmdi-arrow-forward"></i>
			</a>
	
		</div>
	
	
	</body>

	<script src="${pageContext.request.contextPath}/static/login/js/bower_components/jquery/dist/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/login/js/log.js"></script>
	
	<!-- Angular -->
	<script src="${pageContext.request.contextPath}/static/login/js/bower_components/angular/angular.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/login/js/bower_components/angular-resource/angular-resource.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/login/js/bower_components/angular-animate/angular-animate.min.js"></script>
	
	<!-- Angular Modules -->
	<script src="${pageContext.request.contextPath}/static/login/js/bower_components/angular-ui-router/release/angular-ui-router.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/login/js/bower_components/angular-loading-bar/src/loading-bar.js"></script>
	<script src="${pageContext.request.contextPath}/static/login/js/bower_components/oclazyload/dist/ocLazyLoad.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/login/js/bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>
	
	<!-- Common js -->
	<script src="${pageContext.request.contextPath}/static/login/js/bower_components/angular-nouislider/src/nouislider.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/login/js/bower_components/ng-table/dist/ng-table.min.js"></script>
	

	<script src="${pageContext.request.contextPath}/static/login/js/app.js"></script>
	<script src="${pageContext.request.contextPath}/static/login/js/controllers/main.js"></script>
	<script src="${pageContext.request.contextPath}/static/login/js/controllers/ui-bootstrap.js"></script>
	
	
	<!-- Template Modules -->
	<script src="${pageContext.request.contextPath}/static/login/js/modules/form.js"></script>
</html>