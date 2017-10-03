<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

				<div style="margin:0 auto;margin-top:10px; width: 80%; word-break: break-all;">
					<strong>订单详情</strong>
					<table class="table table-bordered table-responsive">
						<tbody>
							<tr class="warning">
								<th colspan="2">订单编号:${bean.oid}</th>
								<th colspan="1">
									<c:if test="${bean.state == 0}">未付款</c:if>
									<c:if test="${bean.state == 1}"><span style="color:deeppink">待审核</span></c:if>
									<c:if test="${bean.state == 2}"><span style="color:blue">待收获</span></c:if>
									<c:if test="${bean.state == 3}"><span style="color:green">已完成</span></c:if>
									<c:if test="${bean.state == -1}"><span style="color:red">审核未通过</span></c:if>
								</th>
								<th colspan="2">
									下单时间: <fmt:formatDate value="${bean.ordertime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
								</th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach items="${bean.items}" var="oi">
								<tr class="active">
									<td width="60" width="40%">
										<input type="hidden" name="id" value="22">
										<img src="${pageContext.request.contextPath}/${oi.product.pimage}" width="70" height="60">
									</td>
									<td width="30%">
										<a target="_blank" href="${pageContext.request.contextPath}/product?method=getById&pid=${oi.product.pid}">${oi.product.pname}</a>
									</td>
									<td width="20%">
										￥${oi.product.shop_price}
									</td>
									<td width="10%">
										${oi.count}
									</td>
									<td width="15%">
										<span class="subtotal">￥${oi.subtotal}</span>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div style="text-align:right;margin-right:120px;">
					商品金额: <strong style="color:#ff6600;">￥${bean.total}元</strong>
				</div>

			</div>

			<div>
				<hr/>
				<c:choose>
					<c:when test="${bean.state == 0}">
						<form id="orderForm" class="form-horizontal" style="margin-top:5px; margin-left: 8%;" action="${pageContext.request.contextPath}/order" method="post">
							<input type="hidden" name="method" value="pay">
							<input type="hidden" name="oid" value="${bean.oid}">
							<div class="form-group">
								<label for="username" class="col-sm-1 control-label" style="word-break: keep-all;">收货地址</label>
								<div class="col-sm-5">
									<input name="address" type="text" class="form-control" id="username" placeholder="请输入收货地址或收货邮箱">
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-1 control-label" style="word-break: keep-all;">收货人</label>
								<div class="col-sm-5">
									<input name="name" type="text" class="form-control" id="inputPassword3" placeholder="请输收货人">
								</div>
							</div>
							<div class="form-group">
								<label for="confirmpwd" class="col-sm-1 control-label" style="word-break: keep-all;">联系方式</label>
								<div class="col-sm-5">
									<input name="telephone" type="text" class="form-control" id="confirmpwd" placeholder="请输入联系方式或其它备注">
								</div>
							</div>
						</form>

						<hr/>

						<div style="margin-top:5px; margin-left: 8%;;" >
							<strong>将订单金额转到支付宝同时备注<span style="color: blue">姓名、手机号、地址和订单主要信息</span>或<span style="color: blue">订单编号</span>等待管理员后台审核后即可发货</strong>
							<img src="${pageContext.request.contextPath}/img/pay.jpg" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;

							<hr/>
							<p style="text-align:right;margin-right:10%;">
								<a href="javascript:document.getElementById('orderForm').submit();">
									<img src="${pageContext.request.contextPath}/images/finalbutton.gif" width="204" height="51" border="0" />
								</a>
							</p>
							<hr/>

						</div>
					</c:when>
					<c:otherwise>
						<form id="orderForm" class="form-horizontal" style="margin-top:5px; margin-left: 8%;" action="${pageContext.request.contextPath}/order" method="post">
							<input type="hidden" name="method" value="pay">
							<input type="hidden" name="oid" value="${bean.oid}">
							<div class="form-group">
								<label for="username" class="col-sm-1 control-label" style="word-break: keep-all;">收货地址</label>
								<div class="col-sm-5">
									<input name="address" type="text" class="form-control" placeholder="请输入收货地址或收货邮箱" disabled="disabled" value="${bean.address}">
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-1 control-label" style="word-break: keep-all;">收货人</label>
								<div class="col-sm-5">
									<input name="name" type="text" class="form-control" placeholder="请输收货人" disabled="disabled" value="${bean.name}">
								</div>
							</div>
							<div class="form-group">
								<label for="confirmpwd" class="col-sm-1 control-label" style="word-break: keep-all;">联系方式</label>
								<div class="col-sm-5">
									<input name="telephone" type="text" class="form-control" placeholder="请输入联系方式或其它备注" disabled="disabled" value="${bean.address}">
								</div>
							</div>
							<c:if test="${bean.state == 1}">
								<h1 style="color: deeppink">订单审核中，如果您已经付款，则审核通过后即会发货</h1>
							</c:if>
							<c:if test="${bean.state == 2}">
								<h1 style="color: blue">订单审核通过，后台即将或已经发货，请做好收货准备</h1>
							</c:if>
							<c:if test="${bean.state == 3}">
								<h1 style="color: green">订单已完成</h1>
							</c:if>
							<c:if test="${bean.state == -1}">
								<h1 style="color: red">订单审核未通过</h1
							</c:if>
						</form>
					</c:otherwise>
				</c:choose>
			</div>

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