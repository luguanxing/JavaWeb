<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>

		<%@include file="/jsp/head.jsp"%>

		<div class="container">
			<div class="row">
				<c:if test="${not empty bean.data}">
					<div style="margin:0 auto; margin-top:10px;width:950px;">
						<strong>我的订单</strong>
						<table class="table table-bordered">
							<c:forEach items="${bean.data}" var="order">
								<tbody>
									<tr class="success">
										<th colspan="2">订单编号:${order.oid}</th>
										<th colspan="1">
											<c:if test="${order.state == 0}"><a href="${pageContext.request.contextPath}/order?method=getById&oid=${order.oid}">去付款</a></c:if>
											<c:if test="${order.state == 1}"><a style="color:deeppink" href="${pageContext.request.contextPath}/order?method=getById&oid=${order.oid}">待审核</a></c:if>
											<c:if test="${order.state == 2}"><a style="color:blue" href="${pageContext.request.contextPath}/order?method=getById&oid=${order.oid}">待收货</a></c:if>
											<c:if test="${order.state == 3}"><a style="color:green" href="${pageContext.request.contextPath}/order?method=getById&oid=${order.oid}">已完成</a></c:if>
											<c:if test="${order.state == -1}"><a style="color:red" href="${pageContext.request.contextPath}/order?method=getById&oid=${order.oid}">审核未通过</a></c:if>
										</th>
										<th colspan="2">
											总金额: ${order.total} 元
										</th>
									</tr>
									<tr class="warning">
										<th>图片</th>
										<th>商品</th>
										<th>价格</th>
										<th>数量</th>
										<th>小计</th>
									</tr>
									<c:forEach items="${order.items}" var="orderitem">
										<tr class="active">
											<td width="60" width="40%">
												<input type="hidden" name="id" value="22">
												<img src="${pageContext.request.contextPath}/${orderitem.product.pimage}" width="70" height="60">
											</td>
											<td width="30%">
												<a target="_blank" href="${pageContext.request.contextPath}/product?method=getById&pid=${orderitem.product.pid}">${orderitem.product.pname}</a>
											</td>
											<td width="20%">
												￥${orderitem.product.shop_price}
											</td>
											<td width="10%">
													${orderitem.count}
											</td>
											<td width="15%">
												<span class="subtotal">￥${orderitem.subtotal}</span>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</c:forEach>
						</table>
					</div>
				</c:if>
				<c:if test="${empty bean.data}">
					<h3>订单是空的，快去买点东西呀~~~</h3>
				</c:if>
			</div>


			<c:if test="${not empty bean.data}">
				<!--分页 -->
				<div style="width:380px;margin:0 auto;margin-top:50px; text-align: center;">
					<ul class="pagination" style="text-align:center; margin-top:10px;">
	
						<%--判断是否是第一页--%>
						<c:if test="${bean.pageNumber == 1}">
							<li class="disabled">
								<a href="javascript:void(0)" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>
							</li>
						</c:if>
						<c:if test="${bean.pageNumber != 1}">
							<li>
								<a href="${pageContext.request.contextPath}/order?method=findMyOrderByPage&pageNumber=${bean.pageNumber-1}" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
								</a>
							</li>
						</c:if>
	
						<%--展示所有页码--%>
						<c:forEach begin="1" end="${bean.totalPage}" var="n">
							<c:if test="${bean.pageNumber == n}">
								<li class="active"><a href="javascript:void(0)">${n}</a></li>
							</c:if>
							<c:if test="${bean.pageNumber != n}">
								<li><a href="${pageContext.request.contextPath}/order?method=findMyOrderByPage&pageNumber=${n}">${n}</a></li>
							</c:if>
						</c:forEach>
	
						<%--判断是否是第最后一页--%>
						<c:if test="${bean.pageNumber == bean.totalPage}">
							<li class="disabled">
								<a href="javascript:void(0)" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>
						</c:if>
						<c:if test="${bean.pageNumber != bean.totalPage}">
							<li>
								<a href="${pageContext.request.contextPath}/order?method=findMyOrderByPage&pageNumber=${bean.pageNumber+1}" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>
						</c:if>
					</ul>
				</div>
				<!-- 分页结束 -->
			</c:if>
		</div>
			

		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>
	</body>

</html>