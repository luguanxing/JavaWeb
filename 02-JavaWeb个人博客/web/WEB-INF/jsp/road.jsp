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
		<title>路线</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- 先导入自定义模块 -->
		<!-- road模块css样式 -->
		<link rel="stylesheet" href="/road/css/screen.css" type="text/css" media="screen">
		<link rel="stylesheet" href="/road/css/responsive.css" type="text/css" media="screen">
		<link rel="stylesheet" href="/road/inc/colorbox.css" type="text/css" media="screen">
		<!-- 共同模块 -->
		<%@include file="commons-files.jsp"%>
		<!-- road模块js代码 -->
		<script type="text/javascript" src="/road/inc/colorbox.js"></script>
		<script type="text/javascript" src="/road/js/timeliner.min.js"></script>
		<script>
			$(document).ready(function() {
				$.timeliner({
					startOpen:[]
				});
				$.timeliner({
					timelineContainer: '#timelineContainer_2'
				});
				$(".CBmodal").colorbox({inline:true, initialWidth:100, maxWidth:682, initialHeight:100, transition:"elastic",speed:750});
			});
			window.onload = function() {
				$(".expandAll").click();
			}
		</script>
	</head>
	<body>
		
		<%@include file="header.jsp"%>
		
		<div class="contact">
			<div class="container">
				<h3 class="mytitle">学习路线图</h3>
				<h2>编程之路2015-∞</h2>
				<div id="timelineContainer" class="timelineContainer">

					<div class="timelineToggle">
						<p>
							<a class="expandAll">+ 全部展开</a>
						</p>
					</div>

					<br class="clear">

					<c:if test="${empty map}">
						<h3>没有路线。。。</h3>
					</c:if>

					<c:if test="${not empty map}">
						<%--获取所有年的信息--%>
						<c:forEach items="${map}" var="year_info">
							<div class="timelineMajor">
								<h2 class="timelineMajorMarker"><span>${year_info.key}</span></h2>
								<%--获取该年的信息列表--%>
								<c:forEach items="${year_info.value}" var="roaditem">
									<dl class="timelineMinor">
										<dt id="${roaditem.rid}"><a>${roaditem.title}</a></dt>
										<dd id="${roaditem.rid}EX" class="timelineEvent"  style="display:none;">
											<h3 style="width: auto; margin-left: 5%;">${roaditem.date}</h3>
											<p class="mycontent">${roaditem.content}</p>
											<br class="clear">
										</dd>
									</dl>
								</c:forEach>
							</div>
						</c:forEach>
					</c:if>
					
					<br class="clear">
				</div>
			</div>
		</div>
		
		<%@include file="footer.jsp"%>
		
	</body>
</html>
