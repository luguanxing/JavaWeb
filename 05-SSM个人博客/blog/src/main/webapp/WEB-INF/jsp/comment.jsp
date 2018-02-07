<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>${htmlTitle}</title>
	    <meta name="viewport" content="width=device-width">
		<%@ include file="/WEB-INF/tags/css"%>
		<%@ include file="/WEB-INF/tags/js"%>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/liuyanqiang/css/liuyanqiang.css">
		<script src="${pageContext.request.contextPath}/static/liuyanqiang/js/liuyanqiang.js"></script>
	</head>
	<body>
	
		<%@ include file="/WEB-INF/tags/header.jsp"%>
		
	    <div class="widewrapper main">
	    
	        <div class="container">
	            <div class="row">
	                <div class="col-md-9 blog-main">

						<article class="blog-teaser aside-widget">
		                    <header style="text-align:left">
					            <h3>
					            	<i class="fa fa-comments"></i>
					            	${indexTitle}
					            </h3>
					        </header>
							<div id="container" style="height:520px;">
							</div>
					    </article>
	                	<!-- 分页 -->
						<%@ include file="/WEB-INF/tags/paginator.jsp"%>
						
			            <div class="row">
			                <div class="col-md-10 col-md-offset-1 clean-superblock" id="contact">
			                    <h2>
			                    	<i class="fa fa-comment"></i>
			                  		  留言
			                    </h2>
			                    
			                    <form id="commentForm" action="${pageContext.request.contextPath}/comment/say" method="post" accept-charset="utf-8" class="contact-form">
			                        <input type="text" name="nickname" id="nickname" placeholder="昵称(10字以内)" class="form-control input-lg">
			                        <textarea style="resize:none;" rows="10" name="content" id="content" placeholder="留言内容(50字内)" class="form-control input-lg"></textarea>
			                        <div class="buttons clearfix">  
				                        <img type="button" onclick="changeCaptcha()" id="captchaImg" style="width:20%; margin-top:15px; cursor:pointer" src="${pageContext.request.contextPath}/captcha.html" />
				                        <input style="width:75%; float:right;" name="captcha" id="captcha" placeholder="验证码" class="form-control input-lg">
			                         </div>
			                        <div class="buttons clearfix">                    	
			                            <button type="button" class="btn btn-xlarge btn-clean-one" onclick="say()">提交</button>
			                        </div>                    
			                    </form>
			                </div>
			            </div>						
						
		        	</div>


	        	    <aside class="col-md-3 blog-aside">
						<!-- 博主信息 -->
						<%@ include file="/WEB-INF/tags/blogger.jsp"%>
						<!-- 推荐博客 -->
						<%@ include file="/WEB-INF/tags/priorityblog.jsp"%>
						<!-- 博客类型-->
						<%@ include file="/WEB-INF/tags/blogtype.jsp"%>
						<!-- 友情链接-->
						<%@ include file="/WEB-INF/tags/link.jsp"%>
	                </aside>
                </div>
            </div>
        </div>
	                
		<%@ include file="/WEB-INF/tags/footer.jsp"%>
		
	<script>
	
		  var comments = [
			<c:forEach items="${comments}" var="comment" varStatus="vs">
				{
					'commentDate' : '<fmt:formatDate value="${comment.commentDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>',
					'content' : '<c:out value="${comment.content}"></c:out>',
					'nickname' : '<c:out value="${comment.nickname}"></c:out>'
				}
				<c:if test="${not vs.last}">,</c:if>
			</c:forEach>
		  ];
	  
		function changeCaptcha() {
			//加上时间戳避免读缓冲
			var timestamp = (new Date()).valueOf();
			$("#captchaImg").attr("src", "${pageContext.request.contextPath}/captcha.html?t="+timestamp);
		}
		

		  function say() {
			  var nickname = $("#nickname").val();
			  var content = $("#content").val();
			  var captcha = $("#captcha").val();
			  if (isEmpty(nickname) || nickname.length > 10) {
				  alert("昵称格式有误");
				  return;
			  }
			  if (isEmpty(content) || content.length > 50) {
				  alert("留言内容格式有误");
				  return;
			  }
			  if (isEmpty(captcha) || captcha.length != 4) {
				  alert("验证码格式有误");
				  return;
			  }
		      $.ajax({
		          //几个参数需要注意一下
		          type: "POST",//方法类型
		          dataType: "json",//预期服务器返回的数据类型
		          url: "${pageContext.request.contextPath}/say.do" ,//url
		          data: $('#commentForm').serialize(),
		          success: function (result) {
		              console.log(result);//打印服务端返回的数据(调试用)
		              if (result.state == 200) {
		                  alert("提交成功");
		                  window.location.href = "${pageContext.request.contextPath}/comment.html"
		              } else {
		            	  alert("提交失败, 原因 : " + result.error);
		              }
		          },
		          error : function() {
		              alert("异常！");
		          }
		      });
		  }
	  
	</script>
		
	</body> 
	
	
</html>