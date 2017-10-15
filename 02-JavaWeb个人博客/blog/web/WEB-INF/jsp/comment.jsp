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
		<title>评论</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- 共同模块 -->
		<%@include file="commons-files.jsp"%>
		<script src="/main/js/jquery.color-2.1.2.min.js" type="text/javascript" />
	</head>
	<body>

		<%-- 使用了jquery.color就会"吞掉"一个@include file，我也不知道为什么，所以再引入一个commons-files算了 --%>
		<%@include file="header.jsp"%>
		<%@include file="header.jsp"%>
		
		<div class="single" style="padding-bottom: 0">
			<div class="container">
				<div class="single-grids">
					<div class="col-md-12 mag-innert-left" style="border-radius: 5pt;">
						<div class="post">
							<center><h3>留言墙</h3></center>
							<c:forEach items="${pagebean.data}" var="comment">
								<div class="post-grids">
									<div class="col-md-10 col-md-offset-1">
										<p class="text"><c:out value="${comment.comment}"/></p>
										<p class="comments" style="float: right"><c:out value="${comment.date}"/> —— <a><c:out value="${comment.nickname}"/></a></p>
									</div>
									<div class="clearfix"> </div>
								</div>
							</c:forEach>
						</div>
						<center style="margin-bottom: 15px;">
							<ul class="pagination  pagination-lg">
								<c:if test="${pagebean.pageNumber == 1}">
									<li class="disabled"><a href="javascript:void(0)">前一页</a></li>
								</c:if>
								<c:if test="${pagebean.pageNumber != 1}">
									<li><a href="${pageContext.request.contextPath}/comment?pageNumber=${pagebean.pageNumber-1}">前一页</a></li>
								</c:if>
								<c:forEach begin="1" end="${pagebean.totalPage}" var="n">
									<c:if test="${pagebean.pageNumber == n}">
										<li class="active"><a href="javascript:void(0)">${n}</a></li>
									</c:if>
									<c:if test="${pagebean.pageNumber != n}">
										<li><a href="${pageContext.request.contextPath}comment?pageNumber=${n}">${n}</a></li>
									</c:if>
								</c:forEach>
								<c:if test="${pagebean.pageNumber == pagebean.totalPage}">
									<li class="disabled"><a href="javascript:void(0)">后一页</a></li>
								</c:if>
								<c:if test="${pagebean.pageNumber != pagebean.totalPage}">
									<li><a href="${pageContext.request.contextPath}/comment?pageNumber=${pagebean.pageNumber+1}">后一页</a></li>
								</c:if>
							</ul>
						</center>
					</div>
					<div class="clearfix"> </div>
				</div>
			</div>
		</div>
		<div class="single"  style="padding-top: 20px">
			<div class="container">
				<div class="single-grids">
					<div class="col-md-12 mag-innert-left" style="border-radius: 5pt;">
						<div class="post">
							<div class="leave">
								<center>
									<h3>留言</h3>
									<h4><span style="color: red" id="commenthint">${requestScope.errorcomment}</span> </h4>
								</center>
								<form id="commentform" action="/comment?method=addComment" method="post">
									<input type="hidden" name="pageNumber" value="${pagebean.pageNumber}">
									<p class="comment-form-comment">
										<label class="comment">留言内容：</label>
										<textarea name="comment" placeholder="请写下您的留言内容">${comment}</textarea>
									</p>
									<p class="comment-form-author-name"><label for="author">昵称：</label>
										<input name="nickname" id="author" type="text" value="${nickname}" size="30" aria-required="true" placeholder="请写下您的昵称">
									</p>
									<p class="comment-form-author-name"><label for="captcha">验证码：</label>
										<input name="inputCaptcha" style="width: 50%; float: left" id="captcha" type="text" value="" size="30" aria-required="true" placeholder="请输入验证码">
										<a style="cursor: pointer" onclick="changeCaptcha()">
											<img id="captchaimg" style="height: 38px; float: left; margin-left: 3%" src="/getCaptcha"/>
										</a>
									</p>
									<div class="clearfix"></div>
									<p class="form-submit">
										<input style="width: 100%" type="submit" id="submit" value="Send">
									</p>
									<div class="clearfix"></div>
								</form>

							</div>
						</div>
					</div>
					<div class="clearfix"> </div>
				</div>
			</div>
		</div>

		<%@include file="footer.jsp"%>
	</body>
	<script type="text/javascript">

		function changeCaptcha() {
			//加上时间戳避免读缓冲
			var timestamp = (new Date()).valueOf();
			$("#captchaimg").attr("src", "/getCaptcha?t="+timestamp);
		}

		function getRandomColor() {
			return  '#' +
				(function(color){
					return (color +=  'abcdef'[Math.floor(Math.random()*6)])
					&& (color.length == 6) ?  color : arguments.callee(color);
				})('');
		}

		window.onload = function () {
			if ($("#commenthint").html() != "") {
				alert($("#commenthint").html());
			}
			var sum = 1000;
			$(".post-grids").each(function(){
				$(this).animate({
					backgroundColor: getRandomColor(),
					opacity: 1,
				}, sum);
				sum += 300;
			});
		}

	</script>
	
</html>
