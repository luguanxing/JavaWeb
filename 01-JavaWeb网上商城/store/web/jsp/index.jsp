<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>WEB01</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	</head>

	<body>
		<div class="container-fluid">
			
			<%--包含和转发都用内部路径--%>
			<%@include file="/jsp/head.jsp"%>
				
			<!--
            	作者：ci2713@163.com
            	时间：2015-12-30
            	描述：商品显示
            -->
				
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>热门商品&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/img/title2.jpg"/></h2>
				</div>
				<div class="col-md-12">
					<c:forEach items="${hotList}" var="product">
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${pageContext.request.contextPath}/product?method=getById&pid=${product.pid}">
								<img src="${pageContext.request.contextPath}/${product.pimage}" width="130" height="130" style="display: inline-block;">
							</a>
							<p><a href="${pageContext.request.contextPath}/product?method=getById&pid=${product.pid}" style='color:#666'>${fn:substring(product.pname, 0, 12)}...</a></p>
							<p><font color="#E4393C" style="font-size:16px">&yen;${product.shop_price}</font></p>
						</div>
					</c:forEach>
					
				</div>
			</div>
			
			<!--
            	作者：ci2713@163.com
            	时间：2015-12-30
            	描述：商品显示
            -->
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>最新商品&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/img/title2.jpg"/></h2>
				</div>
				<div class="col-md-12">

					<c:forEach items="${newList}" var="product">
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${pageContext.request.contextPath}/product?method=getById&pid=${product.pid}">
								<img src="${pageContext.request.contextPath}/${product.pimage}" width="130" height="130" style="display: inline-block;">
							</a>
							<p><a href="${pageContext.request.contextPath}/product?method=getById&pid=${product.pid}" style='color:#666'>${fn:substring(product.pname, 0, 12)}...</a></p>
							<p><font color="#E4393C" style="font-size:16px">&yen;${product.shop_price}</font></p>
						</div>
					</c:forEach>
				</div>
			</div>
				
			<%@include file="/jsp/foot.jsp"%>
			
		</div>
	</body>

</html>