<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>网络错误</title>
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${pageContext.request.contextPath}/static/error/css/style.css" rel="stylesheet" type="text/css" media="all" />
		<script type="application/x-javascript"> 
			addEventListener("load", function() { 
				setTimeout(hideURLbar, 0); 
			}, false); 
			function hideURLbar() { 
				window.scrollTo(0,1); 
			} 
		</script>
	</head>
	<body>
		<div class="w3layouts-bg">
		
			<h1 class="header-w3ls">网络错误</h1>
			
			<div class="agileits-content">
				<h2><span>错误</span></h2>
			</div>
			
			<div class="w3layouts-right">
				<div class="w3ls-text">
					<h3>出错啦!</h3>
					<h4 class="w3-agileits2">访问页面时出错了</h4>
					<p>错误信息已记录</p>
					<p class="copyright">
						<a href="${pageContext.request.contextPath}/index.html">返回主页</a>
					</p>
				</div>
			</div>
			
			<div class="clearfix"></div>
		</div>
	</body>
</html>